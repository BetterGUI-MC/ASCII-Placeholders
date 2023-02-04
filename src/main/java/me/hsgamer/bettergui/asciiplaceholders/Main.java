package me.hsgamer.bettergui.asciiplaceholders;

import me.hsgamer.hscore.bukkit.addon.PluginAddon;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.variable.VariableManager;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class Main extends PluginAddon {

    private static final Map<String, String> placeholders = new HashMap<>();
    private final Config config = new BukkitConfig(new File(getDataFolder(), "config.yml"));

    @Override
    public boolean onLoad() {
        try {
            saveResource("config.yml", false);
            return true;
        } catch (Exception e) {
            getPlugin().getLogger().log(Level.WARNING, "Error when loading the addon", e);
            return false;
        }
    }

    @Override
    public void onEnable() {
        config.setup();
        registerASCII();
        VariableManager.register("ascii_", (original, uuid) -> placeholders.get(original));
    }

    @Override
    public void onReload() {
        placeholders.clear();
        config.reload();
        registerASCII();
    }

    private void registerASCII() {
        config.getValues(false).forEach((string, object) -> placeholders.put(string, StringEscapeUtils.unescapeJava(String.valueOf(object))));
    }
}
