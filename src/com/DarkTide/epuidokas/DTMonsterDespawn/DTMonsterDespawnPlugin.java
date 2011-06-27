package com.DarkTide.epuidokas.DTMonsterDespawn;

import java.io.*;
import java.util.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.event.Event;

public class DTMonsterDespawnPlugin extends JavaPlugin{

    private static DTMonsterDespawnPlugin plugin;
    private final Properties config = new Properties();
    private final DTMonsterDespawnEntityListener entityListener = new DTMonsterDespawnEntityListener();
    public static boolean LogDespawn = true;
    public static Integer UnitSize = 10;
    public static Integer MaxMonstersPerUnit = 4;

    public void onEnable() {
        
        plugin = this;

        // Set up plugin directory
        getDataFolder().mkdir();
        getDataFolder().setWritable(true);
        getDataFolder().setExecutable(true);

        // Extract software license
        extractFile("/README.txt");

        // Load config
        extractFile("/config.properties");
        try {
            config.load(new FileInputStream(this.getDataFolder() + File.separator +"config.properties"));
        }
        catch(IOException e) {
            e.printStackTrace();
            log("loading FAILED");
            return;
        }

        LogDespawn = (config.getProperty("LOG_DESPAWN").equals("yes")) ? true : false;
        UnitSize = Integer.parseInt(config.getProperty("UNIT_SIZE"));
        MaxMonstersPerUnit = Integer.parseInt(config.getProperty("MAX_MONSTERS_PER_UNIT"));

        // Register our events
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvent(Event.Type.CREATURE_SPAWN, entityListener, Event.Priority.Low, this);

        // Load successful
        log("loaded");
    }
    public void onDisable() {
        log("disabled");
    }

    private void extractFile(String name) {
        File actual = new File(getDataFolder(), name);
        if (!actual.exists()) {
            InputStream input = this.getClass().getResourceAsStream( name);
            if (input != null) {
                FileOutputStream output = null;

                try {
                    output = new FileOutputStream(actual);
                    byte[] buf = new byte[8192];
                    int length = 0;

                    while ((length = input.read(buf)) > 0) {
                        output.write(buf, 0, length);
                    }

                    log("Extracted file: " + actual.getAbsolutePath());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }
                    } catch (Exception e) { }
                    try {
                        if (output != null) {
                            output.close();
                        }
                    } catch (Exception e) { }
                }
            }
        }
    }

    public static void log(String message) {
        PluginDescriptionFile pdfFile = plugin.getDescription();
        System.out.println("["+pdfFile.getName()+"]["+pdfFile.getVersion()+"] " + message);
    }
}
