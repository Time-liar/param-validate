package date.timeliar.validate.core;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: TimeLiar
 * @CreateTime: 2016-12-11 11:55
 * @Description: auth-center
 */
public class ParamContainer implements Serializable {
    private Map<String, Integer> intMap;
    private Map<String, String> stringMap;
    private Map<String, Double> floatMap;
    private Map<String, Boolean> booleanMap;
    private Map<String, Object> objectMap;
    private Map<String, String[]> arrayMap;

    ParamContainer() {
        intMap = new HashMap<>();
        stringMap = new HashMap<>();
        floatMap = new HashMap<>();
        booleanMap = new HashMap<>();
        objectMap = new HashMap<>();
        arrayMap = new HashMap<>();
    }

    void setInt(String key, int value) {
        this.intMap.put(key, value);
    }

    void setString(String key, String value) {
        this.stringMap.put(key, value);
    }

    void setFloat(String key, double value) {
        this.floatMap.put(key, value);
    }

    void setBoolean(String key, boolean value) {
        this.booleanMap.put(key, value);
    }

    void setObject(String key, Object value) {
        this.objectMap.put(key, value);
    }

    void setArray(String key, String[] value) {
        this.arrayMap.put(key, value);
    }

    public String getString(String key) {
        return stringMap.get(key);
    }

    public Integer getInt(String key) {
        return intMap.get(key);
    }

    public Double getFloat(String key) {
        return floatMap.get(key);
    }

    public Boolean getBoolean(String key) {
        return booleanMap.get(key);
    }

    public Object getObject(String key) {
        return objectMap.get(key);
    }

    public String[] getArray(String key) {
        return arrayMap.get(key);
    }

    @Override
    public String toString() {
        return "ParamContainer{" +
                "intMap=" + intMap +
                ", stringMap=" + stringMap +
                ", floatMap=" + floatMap +
                ", booleanMap=" + booleanMap +
                ", objectMap=" + objectMap +
                ", arrayMap=" + arrayMap +
                '}';
    }
}
