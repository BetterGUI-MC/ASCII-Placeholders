package me.hsgamer.bettergui.asciiplaceholders;

import me.hsgamer.bettergui.api.addon.GetPlugin;
import me.hsgamer.bettergui.api.addon.Reloadable;
import me.hsgamer.hscore.bukkit.config.BukkitConfig;
import me.hsgamer.hscore.config.Config;
import me.hsgamer.hscore.config.PathString;
import me.hsgamer.hscore.expansion.common.Expansion;
import me.hsgamer.hscore.expansion.extra.expansion.DataFolder;
import me.hsgamer.hscore.variable.VariableBundle;
import org.apache.commons.lang.StringEscapeUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class Main implements Expansion, DataFolder, GetPlugin, Reloadable {

    private static final Map<String, String> placeholders = new HashMap<>();
    private final Config config = new BukkitConfig(new File(getDataFolder(), "config.yml"));
    private final VariableBundle variableBundle = new VariableBundle();

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
        variableBundle.register("ascii_", placeholders::get);
    }

    @Override
    public void onReload() {
        placeholders.clear();
        config.reload();
        registerASCII();
    }

    @Override
    public void onDisable() {
        variableBundle.unregisterAll();
    }

    private void registerASCII() {
        config.getNormalizedValues(false).forEach((string, object) -> placeholders.put(PathString.joinDefault(string), StringEscapeUtils.unescapeJava(String.valueOf(object))));
    }
}
