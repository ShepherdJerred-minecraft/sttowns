package com.shepherdjerred.sttowns.config;

import com.shepherdjerred.riotbase.config.Config;
import org.bukkit.configuration.file.FileConfiguration;

public class SpigotTownsConfig implements Config, TownsConfig {

    private final FileConfiguration config;

    public SpigotTownsConfig(FileConfiguration config) {
        this.config = config;
    }

    public boolean isDebugEnabled() {
        return config.getBoolean("debug");
    }

    public boolean isVaultEnabled() {
        return config.getBoolean("vault");
    }

}
