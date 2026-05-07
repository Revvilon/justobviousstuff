package com.jos.mixin;

import com.jos.util.LocationManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.multiplayer.ClientPacketListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.jos.JustObviousStuffClient.mouseLock;

@Mixin(ClientPacketListener.class)
public class ConnectionMixin {
    @Inject(method = "handleLogin", at = @At("TAIL"))
    private void handleLogin(CallbackInfo ci) {
        mouseLock = false;
        LocationManager.instance().resetActive();
        KeyMapping.releaseAll();
    }
}
