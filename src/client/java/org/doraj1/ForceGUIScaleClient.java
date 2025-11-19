package org.doraj1;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import org.doraj1.config.ForceGUIScaleConfig;
import org.lwjgl.glfw.GLFW;

public class ForceGUIScaleClient implements ClientModInitializer {

    private static KeyMapping configKeyBinding;

    @Override
    public void onInitializeClient() {
        configKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "세부 설정 창 열기",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "ForceGUIScale"
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