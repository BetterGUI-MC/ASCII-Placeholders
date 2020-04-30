package me.hsgamer.bettergui.asciiplaceholders;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import me.hsgamer.bettergui.manager.VariableManager;
import me.hsgamer.bettergui.object.addon.Addon;
import me.hsgamer.bettergui.util.CommonUtils;
import org.apache.commons.lang.StringEscapeUtils;

public final class Main extends Addon {

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
    VariableManager.register("ascii_", ((player, s) -> placeholders.getOrDefault(s, null)));
  }

  @Override
  public void onReload() {
    placeholders.clear();
    reloadConfig();
    registerASCII();
  }

  private void registerASCII() {
    getConfig().getValues(false).forEach((string, object) ->
        placeholders.put(string,
            CommonUtils.colorize(StringEscapeUtils.unescapeJava(String.valueOf(object)))));
  }
}
