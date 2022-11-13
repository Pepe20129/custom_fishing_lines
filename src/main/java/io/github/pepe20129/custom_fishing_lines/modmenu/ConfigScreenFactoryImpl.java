package io.github.pepe20129.custom_fishing_lines.modmenu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import io.github.pepe20129.custom_fishing_lines.Config;
import io.github.pepe20129.custom_fishing_lines.ConfigHelper;
import io.github.pepe20129.custom_fishing_lines.CustomFishingLines;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ConfigScreenFactoryImpl implements ConfigScreenFactory<Screen> {
	@Override
	public Screen create(Screen parent) {
		ConfigBuilder configBuilder = ConfigBuilder.create()
												   .setParentScreen(parent)
												   .setTitle(Text.translatable("title.custom_fishing_lines.config"))
												   .setSavingRunnable(ConfigHelper::saveConfig);

		ConfigCategory configCategory = configBuilder.getOrCreateCategory(Text.translatable("title.custom_fishing_lines.config"));

		ConfigEntryBuilder entryBuilder = configBuilder.entryBuilder();

		Config config = ConfigHelper.getConfig();

		configCategory.addEntry(entryBuilder.startBooleanToggle(Text.translatable("option.custom_fishing_lines.enabled"), config.enabled)
											.setDefaultValue(true)
											.setTooltip(Text.translatable("option.custom_fishing_lines.enabled.tooltip"))
											.setSaveConsumer(newValue -> config.enabled = newValue)
											.build());

		configCategory.addEntry(entryBuilder.startStringDropdownMenu(Text.translatable("option.custom_fishing_lines.pattern"), config.pattern)
											.setDefaultValue("")
											.setTooltip(Text.translatable("option.custom_fishing_lines.pattern.tooltip"))
											.setSelections(CustomFishingLines.PATTERNS.keySet())
											.setSaveConsumer(newValue -> config.pattern = newValue)
											.build());

		configCategory.addEntry(entryBuilder.startFloatField(Text.translatable("option.custom_fishing_lines.percentage_bias"), config.percentageBias)
											.setDefaultValue(2)
											.setTooltip(Text.translatable("option.custom_fishing_lines.percentage_bias.tooltip"))
											.setSaveConsumer(newValue -> config.percentageBias = newValue)
											.build());

		configCategory.addEntry(entryBuilder.startIntField(Text.translatable("option.custom_fishing_lines.segment_count"), config.segmentCount)
											.setDefaultValue(-1)
											.setTooltip(Text.translatable("option.custom_fishing_lines.segment_count.tooltip"))
											.setSaveConsumer(newValue -> config.segmentCount = newValue)
											.build());

		return configBuilder.build();
	}
}