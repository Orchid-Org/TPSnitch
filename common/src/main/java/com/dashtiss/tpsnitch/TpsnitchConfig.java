package com.dashtiss.tpsnitch;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * TPSnitch configuration loaded from config/tpsnitch.toml.
 */
public class TpsnitchConfig {
    /** If true, enables debug logging. */
    public boolean debug = false;
    /** The file name for TPSnitch logs. */
    public String logFileName = "tpsnitch_log.json";
    /** Log interval in seconds. */
    public int logIntervalSeconds = 10;

    private static final String CONFIG_FILE = "config/tpsnitch.toml";
    private static TpsnitchConfig INSTANCE;

    public static TpsnitchConfig get() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    private static TpsnitchConfig load() {
        Path path = Paths.get(CONFIG_FILE);
        CommentedFileConfig config = CommentedFileConfig.builder(path).autoreload().autosave().build();
        config.load();
        TpsnitchConfig cfg = new TpsnitchConfig();
        cfg.debug = config.getOrElse("debug", false);
        cfg.logFileName = config.getOrElse("logFileName", "tpsnitch_log.json");
        cfg.logIntervalSeconds = config.getOrElse("logIntervalSeconds", 10);
        config.set("debug", cfg.debug);
        config.set("logFileName", cfg.logFileName);
        config.set("logIntervalSeconds", cfg.logIntervalSeconds);
        config.save();
        config.close();
        return cfg;
    }
}
