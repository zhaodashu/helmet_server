package com.zmm.helmet.service;

import com.zmm.helmet.entity.Device;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface DeviceService {

    List<Device> getByQuery(String type, String status, String name);
    Device getById(Long id);
    public <S extends Device> S save(S device);

    Device getByRegister(String register_info);
}
