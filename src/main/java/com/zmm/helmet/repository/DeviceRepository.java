package com.zmm.helmet.repository;

import com.zmm.helmet.entity.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {

    // 单一条件查询
    @Query(nativeQuery = true, value = "SELECT d.id, d.name, d.type, d.status, d.ip_info, d.gps_info, d.register_info FROM device d "+
            "WHERE d.type = ?1 OR d.status =?2 AND d.name LIKE %?3% ")
    List<Device> findByQuery1(String type, String status, String name);

    @Query(nativeQuery = true, value = "SELECT d.id, d.name, d.type, d.status, d.ip_info, d.gps_info, d.register_info FROM device d "+
            "WHERE d.type = ?1 OR d.status =?2 OR d.name LIKE %?3% ")
    List<Device> findByQuery6(String type, String status, String name);


    // 两个条件查询
    @Query(nativeQuery = true, value = "SELECT d.id, d.name, d.type, d.status, d.ip_info, d.gps_info, d.register_info FROM device d "+
            "WHERE d.type = ?1 AND d.status =?2 ")
    List<Device> findByQuery2(String type, String status);

    @Query(nativeQuery = true, value = "SELECT d.id, d.name, d.type, d.status, d.ip_info, d.gps_info, d.register_info FROM device d "+
            "WHERE d.type = ?1 AND d.name LIKE %?2% ")
    List<Device> findByQuery3(String type, String name);

    @Query(nativeQuery = true, value = "SELECT d.id, d.name, d.type, d.status, d.ip_info, d.gps_info, d.register_info FROM device d "+
            "WHERE d.status =?1 AND d.name LIKE %?2% ")
    List<Device> findByQuery4(String status, String name);

    // 三个条件查询
    @Query(nativeQuery = true, value = "SELECT d.id, d.name, d.type, d.status, d.ip_info, d.gps_info, d.register_info FROM device d "+
            "WHERE d.type = ?1 AND d.status =?2 AND d.name LIKE %?3% ")
    List<Device> findByQuery5(String type, String status, String name);

    Device getById(Long id);

    <S extends Device> S save(S device);


    Device findByRegisterInfo(String register_info);

}
