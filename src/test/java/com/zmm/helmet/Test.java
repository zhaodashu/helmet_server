package com.zmm.helmet;

public class Test {
    public static void main(String[] args) {
        String heartbeat = "NumAC09IP192.14.198.90IPEndGPS54.031757E11.295782NGPSEnd";
        int num = heartbeat.length();

        String register_info = heartbeat.substring(0, 7);
        System.out.println(register_info+"\n");

        String ip_info = heartbeat.substring(9,heartbeat.indexOf("IPEnd"));
        String gps_info = heartbeat.substring(heartbeat.indexOf("IPEnd")+8,num-6);
        System.out.println(ip_info);
        System.out.println(gps_info);
    }
}
