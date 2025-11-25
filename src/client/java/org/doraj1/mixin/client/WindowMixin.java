package org.doraj1.mixin.client;

import com.mojang.blaze3d.platform.Window;
import org.doraj1.config.ForceGUIScaleConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Window.class)
public class WindowMixin {
    @Inject(method = "calculateScale", at = @At("HEAD"), cancellable = true)
    public void onCalculateScaleFactor(int guiScale, boolean forceUnicode, CallbackInfoReturnable<Integer> cir) {
        ForceGUIScaleConfig config = ForceGUIScaleConfig.getInstance();

        if (config.enabled) {
            int targetScale = config.forceScale;

            if (targetScale < 1) targetScale = 1;

            cir.setReturnValue(targetScale);
        }
    }
}