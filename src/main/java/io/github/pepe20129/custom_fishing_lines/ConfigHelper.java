package io.github.pepe20129.custom_fishing_lines;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

public class ConfigHelper {
	public static void loadConfig() {
		try {
			File configFile = getConfigFile();
			if (configFile.exists()) {
				Gson gson = new GsonBuilder().create();
				setConfig(gson.fromJson(Files.readString(configFile.toPath()), Config.class));
			} else {
				setConfig(new Config());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void saveConfig() {
		try {
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			FileWriter fileWriter = new FileWriter(ConfigHelper.getConfigFile());
			fileWriter.write(gson.toJson(getConfig()));
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Config config = new Config();

	public static Config getConfig() {
		return config;
	}

	public static void setConfig(Config config) {
		ConfigHelper.config = config;
	}

	static File getConfigFile() {
		return new File(FabricLoader.getInstance().getConfigDir().toFile(), CustomFishingLines.MODID + ".json");
	}
}