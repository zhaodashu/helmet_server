package com.zmm.helmet.component;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.zmm.helmet.entity.Device;
import com.zmm.helmet.service.DeviceService;
import com.zmm.helmet.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class OnlineCounter {
    //每次打开此类只初始化一次countMap
    private static Map map = new HashMap();

    @Autowired
    private DeviceService deviceService;

    /**
     * @auther: Arong
     * @description: 解析token并且将数据插入CountMap中
     * @param token
     * @return: void
     * @date: 2019/1/22 17:44
     */
    public void insertToken(String token){
        //获得当前时间(毫秒)
        long currentTime = System.currentTimeMillis();
        //解析token，获得签发时间
        DecodedJWT verify = JWTUtils.verify(token);
        try {

        } catch (Exception e) {
            throw new RuntimeException("token不存在或已过期");
        }
        Date issuedAt = verify.getExpiresAt();
        System.out.println("issuedAt String" + issuedAt.toString());
        System.out.println("issuedAt Time " + issuedAt.getTime());
        String register_info = verify.getClaim("register_info").asString();

        //以签发时间为key。当前时间+300s为value存入countMap中
        map.put(register_info,issuedAt.getTime());
        System.out.println("==========");
        System.out.println(map);
    }

    /**
     * @auther: Arong
     * @description: 获取当前在线用户数
     * @param
     * @return: java.lang.Integer
     * @date: 2019/1/22 17:51
     */
    public void getOnlineCount(){
        int onlineCount = 0;
        //获取map的迭代器
        Iterator iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String,Object>  entry = (Map.Entry<String, Object>) iterator.next();
            String register_info = entry.getKey();
            Long value = (Long) entry.getValue();
            System.out.println("===============");
            System.out.println("value:"+ value);
            System.out.println("currentTime" +System.currentTimeMillis() );

            if(value < System.currentTimeMillis()){
                onlineCount = 0;
            }else if(value -200 *1000 > System.currentTimeMillis()){
                onlineCount = 2;
            }else if(value > System.currentTimeMillis()){
                onlineCount = 1;
            }

            Device byRegister = deviceService.getByRegister(register_info);
            byRegister.setStatus(String.valueOf(onlineCount));
            deviceService.save(byRegister);

        }

    }

}
