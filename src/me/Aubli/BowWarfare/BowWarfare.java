package me.Aubli.BowWarfare;

import java.util.logging.Logger;

import me.Aubli.BowWarfare.Listeners.EntityDamageListener;
import me.Aubli.BowWarfare.Listeners.PlayerInteractListener;
import me.Aubli.BowWarfare.Listeners.PlayerRespawnListener;
import me.Aubli.BowWarfare.Listeners.SignChangeListener;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BowWarfare extends JavaPlugin{
	
	public static Logger log = Bukkit.getLogger();
	
	private static BowWarfare instance;
	
	private static int maxP;
	private static int minP;
	
	private static String pluginPrefix = ChatColor.DARK_GREEN + "[" + ChatColor.DARK_GRAY + "BowWarfare" + ChatColor.DARK_GREEN + "]" + ChatColor.RESET + " ";
	
	@Override
	public void onDisable(){
		GameManager.getManager().shutdown();
		log.info("[" + getDescription().getName() + "] disabled!");
	}
	
	@Override
	public void onEnable(){
		
		init();
		
		log.info("[" + getDescription().getName() + "] enabled!");
	}
	
	private void init(){
		instance = this;
		
		new GameManager();
		
		registerListeners();
	
		loadConfig();
		
		getCommand("bw").setExecutor(new BowExecuter());
	}
	
	private void registerListeners(){
		PluginManager pm = Bukkit.getPluginManager();
		
		pm.registerEvents(new SignChangeListener(), this);
		pm.registerEvents(new PlayerRespawnListener(), this);
		pm.registerEvents(new EntityDamageListener(), this);
		pm.registerEvents(new PlayerInteractListener(), this);
	}
	
	private void loadConfig(){
		
		getConfig().addDefault("config.maxPlayers", 24);
		getConfig().addDefault("config.minPlayers", 5);
		
		maxP = getConfig().getInt("config.maxPlayers");
		minP = getConfig().getInt("config.minPlayers");
		
		
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	
	
	public static BowWarfare getInstance(){
		return instance;
	}
	
	public static int getMaxPlayers(){
		return maxP;
	}
	
	public static int getMinPlayers(){
		return minP;
	}
	
	public static String getPrefix(){
		return pluginPrefix;
	}
}
