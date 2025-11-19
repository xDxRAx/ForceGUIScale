package org.doraj1.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ForceGUIScaleConfig {

    private static final ForceGUIScaleConfig INSTANCE = new ForceGUIScaleConfig();

    public boolean enabled = false;
    public int forceScale = 4;

    public static ForceGUIScaleConfig getInstance() {
        return INSTANCE;
    }

    public Screen createScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Component.literal("GUI 크기 설정"));

        ConfigCategory general = builder.getOrCreateCategory(Component.literal("일반"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startBooleanToggle(Component.literal("모드 활성화"), this.enabled)
                .setDefaultValue(false)
                .setTooltip(Component.literal("활성화 시 아래 설정된 크기로 GUI를 고정합니다."))
                .setSaveConsumer(newValue -> this.enabled = newValue)
                .build());

        general.addEntry(entryBuilder.startIntSlider(Component.literal("GUI 크기 강제 설정"), this.forceScale, 1, 8)
                .setDefaultValue(4)
                .setTooltip(Component.literal("원하는 GUI 크기를 설정하세요. 유니코드 설정에 영향을 받지 않습니다."))
                .setSaveConsumer(newValue -> this.forceScale = newValue)
                .build());

        return builder.build();
    }
}