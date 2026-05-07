package com.jos.mixin;

import com.jos.JustObviousStuffClient;
import com.jos.util.KeyUtils;
import com.jos.util.LocationManager;
import com.jos.util.Locations;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import org.jspecify.annotations.Nullable;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class ClientMixin {
    @Shadow
    public int missTime;
    @Shadow
    @Final
    public MouseHandler mouseHandler;
    @Shadow
    @Final
    public Options options;
    @Shadow
    @Nullable
    public Screen screen;

    @Inject(method = "handleKeybinds", at = @At("TAIL"))
    private void onKeyBind(CallbackInfo ci) {

        if (!JustObviousStuffClient.isEnabled || !JustObviousStuffClient.shouldHold) return;

        if (this.screen != null) return;

        LocationManager locationManager = LocationManager.instance();
        Locations active = locationManager.active();
        if (active == null) return;
        active.keys().forEach(key -> {
            KeyMapping.set(key, true);
        });

        if (!this.mouseHandler.isMouseGrabbed() && !this.options.keyAttack.isDown()) return;
        this.continueAttack(true);
        if (this.gameMode != null && this.hitResult instanceof BlockHitResult) {
            if (this.gameMode.isDestroying()) return;
            this.startAttack();
        }

    }

    @Shadow
    private void continueAttack(boolean leftClick) {

    }

    @Shadow
    protected abstract boolean startAttack();

    @Shadow
    @Nullable
    public HitResult hitResult;
    @Shadow
    @Nullable
    public MultiPlayerGameMode gameMode;
}
