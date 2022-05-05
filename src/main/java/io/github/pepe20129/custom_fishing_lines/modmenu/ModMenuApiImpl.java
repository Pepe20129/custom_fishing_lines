package io.github.pepe20129.custom_fishing_lines.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

public class ModMenuApiImpl implements ModMenuApi {
	public ConfigScreenFactory<?> getModConfigScreenFactory() {
		return new ConfigScreenFactoryImpl();
	}
}