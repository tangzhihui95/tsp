package com.modern.exinterface.cache;

import com.modern.entity.VehicleIntegrate;
import com.modern.exinterface.command.VehicleCommand;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @Author：tzh
 * @Package：cache
 * @Project：tsp
 * @name：VehicleRedisCache
 * @Date：2024/11/28 14:17
 * @Filename：VehicleRedisCache
 */
@Component
public class VehicleRedisCache {

    public static final String VEHICLE_REALTIME_DATA_KEY_PREFIX = "VehicleRealtimeData";

    public static final String TSP_TABLE = "TspTable";

    //private final RedisTemplate<String, Object> vehicleRedisTemplate;

    private ObjectMapper objectMapper;

  /*   @Autowired
    public VehicleRedisCache(RedisTemplate<String, Object> vehicleRedisTemplate) {
        this.objectMapper = (JsonMapper.builder().findAndAddModules()).build();
        this.vehicleRedisTemplate = vehicleRedisTemplate;
    }

    public VehicleIntegrate readVehicleIntegrateCache(String vin) {
        return readRealtimeDataFromCache(vin, "VehicleIntegrate", VehicleIntegrate.class);
    }

   public VehicleAlert readVehicleAlertCache(String vin) {
        return readRealtimeDataFromCache(vin, "VehicleAlert", VehicleAlert.class);
    }

    public VehicleGps readVehicleGpsCache(String vin) {
        return readRealtimeDataFromCache(vin, "VehicleGps", VehicleGps.class);
    }

    public VehicleExtreme readVehicleExtremeCache(String vin) {
        return readRealtimeDataFromCache(vin, "VehicleExtreme", VehicleExtreme.class);
    }

    public VehicleLogin readVehicleLoginCache(String vin) {
        return readRealtimeDataFromCache(vin, "VehicleLogin", VehicleLogin.class);
    }

    public VehicleLogout readVehicleLogoutCache(String vin) {
        return readRealtimeDataFromCache(vin, "VehicleLogout", VehicleLogout.class);
    }

    public List<VehicleDriveMotor> readVehicleDriveMotorListCache(String vin) {
        return readRealtimeDataListFromCache(vin, "VehicleDriveMotor", VehicleDriveMotor.class);
    }

    public List<VehicleEss> readVehicleEssListCache(String vin) {
        return readRealtimeDataListFromCache(vin, "VehicleEss", VehicleEss.class);
    }

    public List<VehicleEssTemperature> readVehicleEssTemperatureListCache(String vin) {
        return readRealtimeDataListFromCache(vin, "VehicleEssTemperature", VehicleEssTemperature.class);
    }*/

    /*public VehicleCommand readVehicleCommandCache(String vin) {
        return readFromCache("VehicleCommand:" + vin, "VehicleCommand", VehicleCommand.class);
    }

    private <T> T readRealtimeDataFromCache(String key, String hashKey, Class<T> valueType) {
        return readFromCache("VehicleRealtimeData:" + key, hashKey, valueType);
    }

    private <T> T readFromCache(String key, String hashKey, Class<T> valueType) {
        try {
            Object o = this.vehicleRedisTemplate.opsForHash().get(key, hashKey);
            return (T)this.objectMapper.readValue((String)o, valueType);
        } catch (Exception exception) {
            return null;
        }
    }

    private <T> List<T> readRealtimeDataListFromCache(String key, String hashKey, Class<T> valueType) {
        return readListFromCache("VehicleRealtimeData:" + key, hashKey, valueType);
    }

    private <T> List<T> readListFromCache(String key, String hashKey, Class<T> valueType) {
        try {
            Object o = this.vehicleRedisTemplate.opsForHash().get(key, hashKey);
            JavaType type = this.objectMapper.getTypeFactory().constructParametricType(List.class, new Class[] { valueType });
            return (List<T>)this.objectMapper.readValue((String)o, type);
        } catch (Exception exception) {
            return null;
        }
    }

    private boolean writeToCache(String key, String hashKey, Object o) {
        try {
            this.vehicleRedisTemplate.opsForHash().put(key, hashKey, this.objectMapper.writeValueAsString(o));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
*/
/*    public TspVehicleLicense readTspVehicleLicenseByTspVehicleId(Long tspVehicleId) {
        return readFromCache("TspTable:TspVehicleLicense", tspVehicleId + "", TspVehicleLicense.class);
    }

    public boolean writeTspVehicleLicenseByTspVehicleId(TspVehicleLicense tspVehicleLicense) {
        return writeToCache("TspTable:TspVehicleLicense", tspVehicleLicense.getTspVehicleId() + "", tspVehicleLicense);
    }*/

}
