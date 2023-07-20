package server.economy_fac;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class Data {

    private File file;
    private FileConfiguration config;

    public Data(String name){
        file = new File(Economy_fac.getInstance().getDataFolder(), name);
        try {
            if(!file.exists() && !file.createNewFile()) throw new IOException();
        } catch (IOException e){
            throw new RuntimeException("Failed to create data.yml",e);
        }

        config = YamlConfiguration.loadConfiguration(file);
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save(){
        try {
            config.save(file);
        } catch (IOException e){
            throw new RuntimeException("Failed to save data.yml",e);
        }
    }
}
