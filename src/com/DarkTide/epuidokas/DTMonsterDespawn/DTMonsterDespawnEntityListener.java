package com.DarkTide.epuidokas.DTMonsterDespawn;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Monster;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityListener;

import java.util.Iterator;
import java.util.List;

public class DTMonsterDespawnEntityListener extends EntityListener {
    public void onCreatureSpawn	( CreatureSpawnEvent event ) {
        Integer count = 0;
        List<Entity> entities = event.getEntity().getNearbyEntities(DTMonsterDespawnPlugin.UnitSize,DTMonsterDespawnPlugin.UnitSize,DTMonsterDespawnPlugin.UnitSize);
        if (entities.size() >= DTMonsterDespawnPlugin.MaxMonstersPerUnit) {
            Iterator<Entity> iterator = entities.iterator();
            while ( iterator.hasNext() ){
                if (iterator.next() instanceof Monster) {
                    count++;
                }
            }
            if (count >= DTMonsterDespawnPlugin.MaxMonstersPerUnit) {
                if (DTMonsterDespawnPlugin.LogDespawn) {
                    DTMonsterDespawnPlugin.log("Too many monsters near each other. Monster despawned at x" +event.getEntity().getLocation().getX()+ ", y"+event.getEntity().getLocation().getY()+", z" + event.getEntity().getLocation().getZ());
                }
                event.setCancelled(true);
            }
        }
    }
}
