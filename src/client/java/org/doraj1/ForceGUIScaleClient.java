package org.doraj1;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.resources.ResourceLocation;
import org.doraj1.config.ForceGUIScaleConfig;
import org.lwjgl.glfw.GLFW;

public class ForceGUIScaleClient implements ClientModInitializer {

    private static KeyMapping configKeyBinding;
    private static final KeyMapping.Category CATEGORY_FORCE_GUISCALE = new
            KeyMapping.Category(ResourceLocation.fromNamespaceAndPath("mod", "name"));

    @Override
    public void onInitializeClient() {
        configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.forceguiscale.open_settings",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                CATEGORY_FORCE_GUISCALE
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (configKeyBinding.consumeClick()) {
                openConfigScreen(client);
            }
        });

        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("guiscale")
                    .executes(context -> {
                        Minecraft.getInstance().execute(() -> openConfigScreen(Minecraft.getInstance()));
                        return 1;
                    }));
        });
    }

    private void openConfigScreen(Minecraft client) {
        client.setScreen(ForceGUIScaleConfig.getInstance().createScreen(client.screen));
    }
}