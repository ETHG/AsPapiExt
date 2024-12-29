package org.rez.asPapiExt;

import me.wiefferink.areashop.AreaShop;
import org.bukkit.plugin.java.JavaPlugin;

public final class AsPapiExt extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        AreaShop areaShop = (AreaShop) getServer().getPluginManager().getPlugin("AreaShop");
        if (areaShop != null) {
            new Expansion(areaShop).register();
            getLogger().info("AreaShopPlaceholderExtension has been enabled!");
        } else {
            getLogger().severe("ASPAPIExp: AreaShop not found! Disabling plugin.");
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
