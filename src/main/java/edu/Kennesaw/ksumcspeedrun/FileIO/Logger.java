package edu.Kennesaw.ksumcspeedrun.FileIO;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@SuppressWarnings("SpellCheckingInspection")
public class Logger {

    // This Async method can be used to log severe error messages if necessary in an errorlog folder
    public static void logError(String message, Throwable e, Plugin plugin) {

        Bukkit.getAsyncScheduler().runNow(plugin, scheduledTask -> {
            plugin.getLogger().severe(message);
            plugin.getLogger().severe(e.getMessage());
            Date date = new Date();
            String timeStamp = String.valueOf(date.getTime());
            File errorDir = new File(plugin.getDataFolder(), "errorlog");
            if (errorDir.mkdirs()) {
                plugin.getLogger().info("Creating subdirectory \"errorlog\"");
            }
            try {
                File errorFile = new File(errorDir, "error-log-" + timeStamp + ".txt");
                if (errorFile.createNewFile()) {
                    try (PrintWriter pw = new PrintWriter(errorFile)) {
                        pw.println("Severe error occurred on: " + date + " at " + date.getTime());
                        pw.println("The config.yml is improperly formatted. Please review the file or delete it for a default file.");
                        e.printStackTrace(pw);
                    }
                }
            } catch (IOException k) {
                plugin.getLogger().warning("Unable to save error report to file.");
            }
        });

    }
}
