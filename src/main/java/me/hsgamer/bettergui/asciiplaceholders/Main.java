package me.hsgamer.bettergui.asciiplaceholders;

import me.hsgamer.bettergui.api.addon.BetterGUIAddon;
import me.hsgamer.bettergui.lib.core.variable.VariableManager;
import org.apache.commons.lang.StringEscapeUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public final class Main extends BetterGUIAddon {

    private static final Map<String, String> placeholders = new HashMap<>();

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
        registerASCII();
        VariableManager.register("ascii_", (original, uuid) -> placeholders.get(original));
    }

    @Override
    public void onReload() {
        placeholders.clear();
        reloadConfig();
        registerASCII();
    }

    private void registerASCII() {
        getConfig().getValues(false).forEach((string, object) -> placeholders.put(string, StringEscapeUtils.unescapeJava(String.valueOf(object))));
    }
}
