package org.doraj1.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class ForceGUIScaleConfig {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("forceguiscale.json");
    private static final ForceGUIScaleConfig INSTANCE = load();

    public boolean enabled = false;
    public int forceScale = 4;

    private ForceGUIScaleConfig() {
    }

    public static ForceGUIScaleConfig getInstance() {
        return INSTANCE;
    }

    private static ForceGUIScaleConfig load() {
        if (CONFIG_PATH == null || !Files.exists(CONFIG_PATH)) {
            ForceGUIScaleConfig config = new ForceGUIScaleConfig();
            config.save();
            return config;
        }

        try {
            String json = Files.readString(CONFIG_PATH);
            ForceGUIScaleConfig loaded = GSON.fromJson(json, ForceGUIScaleConfig.class);
            return Objects.requireNonNullElseGet(loaded, ForceGUIScaleConfig::new);
        } catch (IOException e) {
            e.printStackTrace();
            return new ForceGUIScaleConfig();
        }
    }

    public void save() {
        try {
            String json = GSON.toJson(this);
            Files.writeString(CONFIG_PATH, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Screen createScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("Force GUI Scale 설정"));

        builder.setSavingRunnable(() -> {
            save();
            if (Minecraft.getInstance() != null) {
                Minecraft.getInstance().resizeDisplay();
            }
        });

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("일반"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("모드 활성화"), this.enabled)
                .setDefaultValue(false)
                .setTooltip(Component.literal("켜면 아래 설정된 크기로 GUI를 고정합니다."))
                .setSaveConsumer(newValue -> this.enabled = newValue)
                .build());

        general.addEntry(entryBuilder.startIntSlider(Component.literal("GUI 크기 강제 설정"), this.forceScale, 1, 10)
                .setDefaultValue(4)
                .setTooltip(Component.literal("원하는 GUI 크기를 설정하세요. 유니코드 설정에 영향을 받지 않습니다."))
                .setSaveConsumer(newValue -> this.forceScale = newValue)
                .build());

        return builder.build();
    }
}
