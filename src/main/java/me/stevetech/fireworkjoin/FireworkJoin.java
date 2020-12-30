package me.stevetech.fireworkjoin;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.java.JavaPlugin;

import javax.security.auth.login.Configuration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireworkJoin extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        getServer().getPluginManager().registerEvents(this, this);


        getLogger().info(getDescription().getName() + ' ' + getDescription().getVersion() + " has been Enabled");
    }


    @Override
    public void onDisable() {
        saveConfig();
        getLogger().info(getDescription().getName() + ' ' + getDescription().getVersion() + " has been Disabled");
    }

    @SuppressWarnings("unchecked")
    private void firework(Player player) {
        int delay = 0;
        for (Map<String, Object> element : (List<Map<String, Object>>) getConfig().getList("fireworks")) {

            getServer().getScheduler().runTaskLater(this, () -> {


            // Load colours from config
            List<Color> colors = new ArrayList<>();
            for (Map<String, Integer> color : (List<Map<String, Integer>>) element.get("colors")) {
                colors.add(Color.fromRGB(color.get("red"), color.get("green"), color.get("blue")));
            }

            // Create and launch a firework at the player's location
            Location loc = player.getLocation();
            Firework fw = loc.getWorld().spawn(loc, Firework.class);
            FireworkMeta fwMeta = fw.getFireworkMeta();
            fwMeta.addEffects(FireworkEffect.builder().withColor(colors)
                    .with(FireworkEffect.Type.valueOf((String) element.get("type"))).build());
            int power = (int) element.get("power");
            if (power < 0 || power > 127) {
                fw.setFireworkMeta(fwMeta);

                fw.detonate();
            } else {
                fwMeta.setPower(power);
                fw.setFireworkMeta(fwMeta);
            }
            }, delay);
            delay += getConfig().getInt("delay") * 20;
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        if (player.hasPermission("fireworkjoin")) {
            firework(player);
        }

    }
}
