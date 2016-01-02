package org.to2mbn.basiscommands.configuration;

import cn.nukkit.utils.Utils;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Configuration {
    private final File configFile;
    private final Map<String, Object> datas;
    private final Yaml yaml;

    public Configuration(File _configFile) throws IOException {
        configFile = _configFile;
        datas = new HashMap<>();

        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        yaml = new Yaml(dumperOptions);
        datas.putAll(yaml.loadAs(Utils.readFile(configFile), LinkedHashMap.class));
    }

    public Integer getInteger(String key) {
        return get(key);
    }

    public Double getDouble(String key) {
        return get(key);
    }

    public String getString(String key) {
        return get(key);
    }

    public Boolean getBoolean(String key) {
        return get(key);
    }

    public <T> T get(String key) {
        if (!datas.containsKey(key)) {
            return null;
        }
        return (T) datas.get(key);
    }

    public boolean has(String key) {
        return datas.containsKey(key);
    }

    public <T> void set(String key, T val) {
        datas.put(key, val);
    }

    public void save() throws IOException {
        Utils.writeFile(configFile, yaml.dump(datas));
    }
}
