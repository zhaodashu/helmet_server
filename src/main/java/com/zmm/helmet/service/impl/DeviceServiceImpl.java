package com.zmm.helmet.service.impl;

import com.zmm.helmet.entity.Device;
import com.zmm.helmet.repository.DeviceRepository;
import com.zmm.helmet.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;


    @Override
    public List<Device> getByQuery(String type, String status, String name) {
        if(type != "" || status != "" || name != ""){
            if(type != "" && status !="" && name!= ""){
                return deviceRepository.findByQuery5(type, status, name);
            }
            if(type != "" && status != ""){
                return deviceRepository.findByQuery2(type, status);
            }
            if(type != "" && name!= ""){
                return deviceRepository.findByQuery3(type, name);
            }
            if(status !="" && name!= ""){
                return deviceRepository.findByQuery4(status, name);
            }
            if(type != "" || status!=""){
                return deviceRepository.findByQuery1(type, status, name);
            }
            if(name != ""){
                return deviceRepository.findByQuery6(type, status, name);
            }

        }
        return deviceRepository.findAll();
    }

    @Override
    public Device getById(Long id) {
        return deviceRepository.getById(id);
    }

    @Override
    public <S extends Device> S save(S device) {
        return deviceRepository.save(device);
    }

    @Override
    public Device getByRegister(String register_info) {
        return deviceRepository.findByRegisterInfo(register_info);
    }
}
