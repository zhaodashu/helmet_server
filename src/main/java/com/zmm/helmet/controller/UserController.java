package com.zmm.helmet.controller;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.zmm.helmet.common.Menu;
import com.zmm.helmet.common.UserList;
import com.zmm.helmet.common.UserLogin;
import com.zmm.helmet.entity.User;
import com.zmm.helmet.service.UserService;
import com.zmm.helmet.utils.JWTUtils;
import com.zmm.helmet.utils.MenuUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.acl.LastOwnerException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@RestController
@CrossOrigin    //允许跨域
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/listUser")
    public Map<String, Object> getUserList(@RequestParam String query,
                                           @RequestParam Integer pagenum,
                                           @RequestParam Integer pagesize,
                                           @RequestParam String access_level) throws JsonProcessingException {

        Map<String, Object> map = new LinkedHashMap<String, Object>();

        List<Object> tmp = userService.getByQuery(access_level, query);

//        List<User> tmp = userService.findAll();
        if (tmp == null) {
            map.put("code", 400);
            map.put("msg", "");
            map.put("count", 0);
            map.put("data", null);
            return map;
        }
        List<Object> userList = new ArrayList<>();
        int i;
        for (i = (pagenum - 1) * pagesize; i < pagenum * pagesize && i < tmp.size(); i++) {
            UserList userList1 = new UserList();
            Object[] obj = (Object[])tmp.get(i);
            userList1.setUserid(Long.valueOf(obj[0].toString()));
            userList1.setUsername(obj[1].toString());
            userList1.setAccess_level(obj[2].toString());

            if(obj[3] != null) {
                userList1.setCreate_time(obj[3].toString());
            }

            userList.add(userList1);

        }

        System.out.println(userList);

        map.put("code", 200);
        map.put("msg", "");
        map.put("page", pagenum);
        map.put("totalpage", tmp.size());
        map.put("data", userList);

        return map;
    }

//    /**
//     * 退出登录
//     */
//    @PostMapping("logout")
//    public String logout(){
//        Subject subject = SecurityUtils.getSubject();
//        subject.logout();  //退出用户
//        return "redirect:/login.jsp";
//    }
//
//
    /**
     * 用来处理身份认证
     * @param userLogin
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Map<String,Object> login(@RequestBody UserLogin userLogin){

        Map<String,Object> map = new HashMap<>();

        String username = userLogin.getUsername();
        String password = userLogin.getPassword();


        //获取主体对象
        Subject subject = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, password);
            subject.login(token);
            User user = userService.getByName(username);

            Map<String,String> payload = new HashMap<>();
            payload.put("user_id",user.getUserid().toString());
            payload.put("username",user.getUsername());
            payload.put("access_level",user.getAccess_level().toString());

            Calendar instance = Calendar.getInstance();
            instance.add(Calendar.DATE,7); //默认7天过期

            //生成JWT的令牌
            String token1 = JWTUtils.getToken(payload, instance.getTime());

            map.put("code",200);
            map.put("msg","登录成功");
            map.put("data",token1);

        } catch (UnknownAccountException e) {
            e.printStackTrace();
//            System.out.println("用户名错误");
            map.put("code",400);
            map.put("msg","用户名错误");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
//            System.out.println("密码错误");
            map.put("code",400);
            map.put("msg","密码错误");
        }
        return map;
    }


    @GetMapping("/menu")
    public Map<String, Object> test(HttpServletRequest request){

        Map<String,Object> map = new HashMap<>();
        System.out.println("===========");
        System.out.println(request);


        //处理自己的业务逻辑
        String token = request.getHeader("Authorization");
        System.out.println("token:"+token);
        DecodedJWT verify = JWTUtils.verify(token);
        System.out.println("verify:" + verify);

        Set<Menu> menus = new HashSet<>();


        String access_level = verify.getClaim("access_level").asString();
        String id = verify.getClaim("id").asString();
        String username = verify.getClaim("username").asString();



        System.out.println("level:"+access_level);
        System.out.println("username:"+username);
        System.out.println("id:"+id);


        if("super".equals(access_level)){
            //超级管理员用户组
            menus.add(MenuUtils.menu_super());
        } else if ("admin".equals(access_level)){
            menus.add(MenuUtils.menu_admin());
        }
        menus.add(MenuUtils.menu_device());

        map.put("code",200);
        map.put("msg","菜单获取成功");
        map.put("data",menus);


        log.info("用户id:[{}]",verify.getClaim("user_id").asString());
        log.info("用户name:[{}]",verify.getClaim("username").asString());
        log.info("用户权限等级:[{}]",verify.getClaim("access_level").asString());

        return map;
    }

    @RequestMapping("/add/{access_level}")
    public Map<String, Object> createUser(HttpServletRequest request,
                                          @PathVariable("access_level") String access_level,
                                          @RequestBody UserLogin userLogin) throws Exception{
        Map<String,Object> map = new HashMap<>();

        String token = request.getHeader("Authorization");
        DecodedJWT verify = JWTUtils.verify(token);
//        String create_by = verify.getClaim("username").asString();

        User u = userService.getByName(userLogin.getUsername());
        System.out.println(u);
        if (u != null){
            map.put("code",400);
            map.put("msg","用户名已存在");
            return map;
        }
        User userModel=new User();
        userModel.setUsername(userLogin.getUsername());

//        String md5Password = DigestUtils.md5DigestAsHex(slr.getPassword().getBytes());
        userModel.setPassword(userLogin.getPassword());
        userModel.setAccess_level(access_level);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
//        System.out.println(date);
        userModel.setCreate_time(date);
        if(userService.addUser(userModel)){
            map.put("data",userModel);
            map.put("code", 201);
            map.put("msg","添加用户成功");
        }else {
            map.put("code",400);
            map.put("msg","添加用户失败");
        }
        return map;
    }

    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable("id") Long id){
        Map<String,Object> map = new HashMap<>();

        User byId = userService.getById(id);
        if(byId == null) {
            map.put("code",400);
            map.put("msg","获取用户信息失败");
        }
        else {
            map.put("data",byId);
            map.put("code", 200);
            map.put("msg","获取用户信息成功");
        }

        return map;
    }

    @PutMapping("/edit")
    public Map<String, Object> editUser(@RequestBody UserList userList) {

        System.out.println(userList.getUserid());
        System.out.println(userList.getAccess_level());
        Map<String,Object> map = new HashMap<>();
        User byId = userService.getById(userList.getUserid());
        byId.setAccess_level(userList.getAccess_level());
        User save = userService.save(byId);
        if(save == null ){
            map.put("code",400);
            map.put("msg","修改用户失败");
        }
        else {
            map.put("data",save);
            map.put("code", 200);
            map.put("msg","修改用户信息成功");
        }
        return map;
    }

    @DeleteMapping("/{id}")
    public Map<String, Object> DeleteById(@PathVariable("id") Long id) {
        Map<String, Object> map = new HashMap<>();
        userService.deleteById(id);
        User userDB = userService.getById(id);
        if (userDB == null) {
            map.put("data", null);
            map.put("code", 200);
            map.put("msg", "成功删除用户");
        } else {
            map.put("code", 400);
            map.put("msg", "删除用户失败");
        }
        return map;
    }
}
