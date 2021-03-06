package net.mcplots.helpers;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;

public class WorldListener implements Listener {

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent event) {
        for (Entity entity : event.getChunk().getEntities()) {
            if (entity instanceof Fireball
                    || entity.getType() == EntityType.FIREBALL) {
                entity.remove();
            }

            if (entity instanceof TNTPrimed
                    || entity.getType() == EntityType.PRIMED_TNT) {
                entity.remove();
            }
        }
    }

    @EventHandler
    public void onEntitySpawn(EntitySpawnEvent event) {
        if (event.getEntity() instanceof Fireball
                || event.getEntityType() == EntityType.FIREBALL) {
            event.setCancelled(true);
        }

        if (event.getEntity() instanceof TNTPrimed
                || event.getEntity().getType() == EntityType.PRIMED_TNT) {
            event.getEntity().remove();
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        event.setCancelled(true);
    }

}
