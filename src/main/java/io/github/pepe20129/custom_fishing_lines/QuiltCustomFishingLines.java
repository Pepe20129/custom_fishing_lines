package io.github.pepe20129.custom_fishing_lines;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

public class QuiltCustomFishingLines implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer mod) {
		CustomFishingLines.onInitializeClient();
	}
}