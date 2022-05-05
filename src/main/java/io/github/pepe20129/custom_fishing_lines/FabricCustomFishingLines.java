package io.github.pepe20129.custom_fishing_lines;

import net.fabricmc.api.ClientModInitializer;

public class FabricCustomFishingLines implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		CustomFishingLines.onInitializeClient();
	}
}