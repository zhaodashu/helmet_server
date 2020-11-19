package com.zmm.helmet.controller;

import com.zmm.helmet.common.HeartBeatInfo;
import com.zmm.helmet.component.OnlineCounter;
import com.zmm.helmet.entity.Device;
import com.zmm.helmet.service.DeviceService;
import com.zmm.helmet.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/device")
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private OnlineCounter onlineCounter;

    @RequestMapping("/listDevice")
    public Map<String, Object> getUserList(@RequestParam Integer pagenum,
                                           @RequestParam Integer pagesize,
                                           @RequestParam String name,
                                           @RequestParam String type,
                                           @RequestParam String status){

        System.out.println(type);
        System.out.println(name);
        Map<String, Object> map = new LinkedHashMap<String, Object>();

        List<Device> tmp = deviceService.getByQuery(type, status, name);
        System.out.println(tmp);
        if (tmp == null) {
            map.put("code", 400);
            map.put("msg", "");
            map.put("page", pagenum);
            map.put("totalpage", 0);
            map.put("data", null);
            return map;
        }

        onlineCounter.getOnlineCount();

        List<Device> DeviceList = new ArrayList<>();
        int i;
        for (i = (pagenum - 1) * pagesize; i < pagenum * pagesize && i < tmp.size(); i++) {
            Device obj = tmp.get(i);

            if(obj.getStatus().equals("2")){
                obj.setStatus("注册&存活");
            } else if (obj.getStatus().equals("1")){
                obj.setStatus("注册&失联");
            }else {
                obj.setStatus("未注册&null");
            }
            DeviceList.add(obj);
        }
        System.out.println(DeviceList);

        map.put("code", 200);
        map.put("msg", "");
        map.put("page", pagenum);
        map.put("totalpage", tmp.size());
        map.put("data", DeviceList);

        return map;

    }

    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable("id") Long id){
        Map<String,Object> map = new HashMap<>();

        Device byId = deviceService.getById(id);
        if(byId == null) {
            map.put("code",400);
            map.put("msg","获取设备信息失败");
        }
        else {
            map.put("data",byId);
            map.put("code", 200);
            map.put("msg","获取设备信息成功");
        }

        return map;
    }

    @PutMapping("/edit")
    public Map<String, Object> editUser(@RequestBody Device device) {

        System.out.println(device.getId());
        Map<String,Object> map = new HashMap<>();
        Device byId = deviceService.getById(device.getId());
        byId.setName(device.getName());
        Device save = deviceService.save(byId);
        if(save == null ){
            map.put("code",400);
            map.put("msg","修改设备失败");
        }
        else {
            map.put("data",save);
            map.put("code", 200);
            map.put("msg","修改设备信息成功");
        }
        return map;
    }

    @RequestMapping(value = "/heartbeat")
    public Map<String, Object> HeartBeat(@RequestParam String heartbeat){


        Map<String,Object> map = new HashMap<>();
//        String heartbeat = heartBeatInfo.getHeartbeat();
        System.out.println(heartbeat);
        int num = heartbeat.length();

        String register_info = heartbeat.substring(0, 7);

        String ip_info = heartbeat.substring(9,heartbeat.indexOf("IPEnd"));
        String gps_info = heartbeat.substring(heartbeat.indexOf("IPEnd")+8,num-6);

        Map<String,String> payload = new HashMap<>();
        payload.put("register_info",register_info);
        payload.put("ip_info",ip_info);
        payload.put("gps_info",gps_info);

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND,300); //默认300秒过期

        //生成JWT的令牌
        String token1 = JWTUtils.getToken(payload, instance.getTime());
        onlineCounter.insertToken(token1);

        Device byRegister = deviceService.getByRegister(register_info);
        if(byRegister == null){
            Device device = new Device();
            device.setName(register_info);
            device.setType("camera");
            device.setStatus("2");
            device.setRegisterInfo(register_info);
            device.setIp_info(ip_info);
            device.setGps_info(gps_info);
            Device save = deviceService.save(device);
            if(save == null ){
                map.put("code",400);
                map.put("msg","传输心跳包失败");
            }
            else {
                map.put("code", 200);
                map.put("msg","传输心跳包成功");
            }
        } else {
            byRegister.setIp_info(ip_info);
            byRegister.setGps_info(gps_info);
            Device save = deviceService.save(byRegister);
            if(save == null ){
                map.put("code",400);
                map.put("msg","传输心跳包失败");
            }
            else {
                map.put("code", 200);
                map.put("msg","传输心跳包成功");
            }
        }
        map.put("data",token1);
        return map;

    }
}
