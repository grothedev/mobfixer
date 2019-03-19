package org.grothedev.mobfixer;

import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class MobFixerPlugin extends JavaPlugin implements Listener{
	
	@Override
	public void onDisable() {
		super.onDisable();
		HandlerList.unregisterAll((Plugin)this);
		saveDefaultConfig();
		
	}

	@Override
	public void onEnable() {
		super.onEnable();
		
		getServer().getPluginManager().registerEvents(this, this);
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onMonsterSpawn(CreatureSpawnEvent event) {
		if (!getConfig().getBoolean("hp_enable")) return;
		
		int p = getConfig().getInt("hp");
		LivingEntity e = event.getEntity();
		if (e instanceof Monster) {
			e.setMaxHealth(e.getMaxHealth() * (p / 100.0));
		}
	}
	
	@EventHandler
	public void onPlayerDamage(EntityDamageEvent event) {
		if (!getConfig().getBoolean("damage_enable")) return;
		
		//TODO make sure the damage is from a mob
		Entity e = event.getEntity();
		int p = getConfig().getInt("damage");
		if (e instanceof Player) {
			log("player health: " + ((Player)e).getHealth());
			log(Double.toString(event.getDamage()));
			event.setDamage(event.getDamage() * (p/100.0));
			log(Double.toString(event.getDamage()));
			log("player health: " + ((Player)e).getHealth());
		}
	}
	
	private void log(String msg) {
		getLogger().info(msg);
	}
}
