package org.extendedalpha.pluginbase.inventoryapi;

import java.lang.reflect.Field;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class InventoryListener implements Listener {


    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerLogin(PlayerLoginEvent event) {
        Player player = event.getPlayer();
        if(player instanceof CraftHumanEntity) {
            CraftHumanEntity cplayer = (CraftHumanEntity)player;
            try {
                Field f = CraftHumanEntity.class.getDeclaredField("inventory");
                f.setAccessible(true);
                f.set(cplayer, new InventoryPlayer((CraftInventoryPlayer)f.get(cplayer)));
            } catch (NoSuchFieldException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (SecurityException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

}