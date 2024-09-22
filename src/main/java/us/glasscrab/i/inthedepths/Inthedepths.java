package us.glasscrab.i.inthedepths;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Inthedepths extends JavaPlugin {
    public static Inthedepths INSTANCE;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new EventListener(new Manager()), this);
        Bukkit.getPluginManager().registerEvents(new OpalCraftEvent(new Manager()), this);
        INSTANCE = this;
        this.getCommand("giveopal").setExecutor(new GiveOpalCommand());
        this.getLogger().info("InTheDepths has been enabled!");
    }

    @Override
    public void onDisable() {
        this.getLogger().info("InTheDepths has been disabled!");
    }
}
