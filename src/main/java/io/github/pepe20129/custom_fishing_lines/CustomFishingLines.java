package io.github.pepe20129.custom_fishing_lines;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CustomFishingLines {
	public static final String MODID = "custom_fishing_lines";

	public static final Logger LOGGER = LoggerFactory.getLogger(MODID);

	public static final Map<String, Pattern> PATTERNS = new HashMap<>();

	public static void onInitializeClient() {
		ConfigHelper.loadConfig();

		ResourceManagerHelper clientResources = ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES);

		clientResources.registerReloadListener(new SimpleSynchronousResourceReloadListener() {
			@Override
			public void reload(ResourceManager manager) {
				manager.findResources("patterns", path -> path.getPath().endsWith(".json")).forEach(
					(key, value) -> {
						try (InputStream inputStream = value.getInputStream()) {
							String rawData = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
							inputStream.close();
							Gson gson = new GsonBuilder().create();
							Pattern pattern = gson.fromJson(rawData, Pattern.class);
							String path = key.getPath();
							//remove ".json"
							path = path.substring(0, path.length() - 5);
							//remove "patterns/"
							path = path.substring(9);
							PATTERNS.put(path, pattern);
						} catch (Exception exception) {
							LOGGER.error("Error occurred while parsing: \"" + key.toString() + "\"", exception);
						}
					}
				);
			}

			@Override
			public Identifier getFabricId() {
				return new Identifier(MODID, "patterns");
			}
		});
	}
}