package com.jos.mixin;

import com.jos.JustObviousStuffClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MouseHandler.class)
public class InputMixin {
    @Inject(method = "turnPlayer", at = @At("HEAD"), cancellable = true)
    private void onMove(double d, CallbackInfo ci) {
        if (JustObviousStuffClient.mouseLock && JustObviousStuffClient.isEnabled) ci.cancel();
    }
}