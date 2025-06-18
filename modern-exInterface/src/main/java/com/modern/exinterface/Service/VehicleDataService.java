package com.modern.exinterface.Service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author：tzh
 * @Package：Service
 * @Project：tsp
 * @name：VehicleDataService
 * @Date：2024/11/28 15:09
 * @Filename：VehicleDataService
 */
@Service
public class VehicleDataService {

    public Map<String, Object> getLatestData(String vin) {
        Map<String, Object> map = new HashMap<>();
/*        VehicleLogin vehicleLogin = this.vehicleRedisCache.readVehicleLoginCache(vin);
        if (vehicleLogin != null) {
            VehicleLoginParsedDTO vehicleLoginParsedDTO = VehicleLoginParsedDTO.create(vehicleLogin);
            map.put("vehicleLogin", vehicleLoginParsedDTO);
        }
        VehicleIntegrate vehicleIntegrate = this.vehicleRedisCache.readVehicleIntegrateCache(vin);
        if (vehicleIntegrate != null) {
            VehicleIntegrateParsedDTO vehicleIntegrateParsedDTO = VehicleIntegrateParsedDTO.create(vehicleIntegrate);
            map.put("vehicleIntegrate", vehicleIntegrateParsedDTO);
        }
        List<VehicleDriveMotor> vehicleDriveMotorList = this.vehicleRedisCache.readVehicleDriveMotorListCache(vin);
        if (vehicleDriveMotorList != null && vehicleDriveMotorList.size() > 0) {
            VehicleDriveMotorParsedDTO vehicleDriveMotorParsedDTO = VehicleDriveMotorParsedDTO.create(vehicleDriveMotorList.get(0));
            map.put("vehicleDriveMotor", vehicleDriveMotorParsedDTO);
        }
        VehicleGps vehicleGps = this.vehicleRedisCache.readVehicleGpsCache(vin);
        if (vehicleGps != null) {
            VehicleGpsParsedDTO vehicleGpsParsedDTO = VehicleGpsParsedDTO.create(vehicleGps);
            map.put("vehicleGps", vehicleGpsParsedDTO);
        }
        VehicleExtreme vehicleExtreme = this.vehicleRedisCache.readVehicleExtremeCache(vin);
        if (vehicleExtreme != null) {
            VehicleExtremeParsedDTO vehicleExtremeDataPageListDTO = VehicleExtremeParsedDTO.create(vehicleExtreme);
            map.put("vehicleExtreme", vehicleExtremeDataPageListDTO);
        }
        VehicleAlert vehicleAlert = this.vehicleRedisCache.readVehicleAlertCache(vin);
        if (vehicleAlert != null) {
            VehicleAlertParsedDTO vehicleAlertParsedDTO = VehicleAlertParsedDTO.create(vehicleAlert);
            map.put("vehicleAlert", vehicleAlertParsedDTO);
        }
        List<VehicleEss> vehicleEssList = this.vehicleRedisCache.readVehicleEssListCache(vin);
        if (vehicleEssList != null && vehicleEssList.size() > 0) {
            VehicleEssParsedDTO vehicleEssParsedDTO = VehicleEssParsedDTO.create(vehicleEssList.get(0));
            map.put("vehicleEss", vehicleEssParsedDTO);
        }
        List<VehicleEssTemperature> vehicleEssTemperatureList = this.vehicleRedisCache.readVehicleEssTemperatureListCache(vin);
        if (vehicleEssTemperatureList != null && vehicleEssTemperatureList.size() > 0) {
            VehicleEssTemperatureParsedDTO vehicleEssTemperatureParsedDTO = VehicleEssTemperatureParsedDTO.create(vehicleEssTemperatureList.get(0));
            map.put("vehicleEssTemperature", vehicleEssTemperatureParsedDTO);
        }
        VehicleLogout vehicleLogout = this.vehicleRedisCache.readVehicleLogoutCache(vin);
        if (vehicleLogout != null) {
            VehicleLogoutParsedDTO vehicleLogoutParsedDTO = VehicleLogoutParsedDTO.create(vehicleLogout);
            map.put("vehicleLogout", vehicleLogoutParsedDTO);
        }*/
        return map;
    }

}
