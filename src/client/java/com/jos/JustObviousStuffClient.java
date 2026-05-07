package com.jos;

import com.jos.gui.LocationScreen;
import com.jos.render.CircleRender;
import com.jos.render.JosRender;
import com.jos.render.Renderable;
import com.jos.render.TextRender;
import com.jos.util.*;
import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientWorldEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.fabricmc.fabric.api.event.client.player.ClientPlayerBlockBreakEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.impl.renderer.RendererManager;
import net.minecraft.client.Camera;
import net.minecraft.client.InputType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.entity.DisplayRenderer;
import net.minecraft.client.renderer.entity.state.TextDisplayEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.server.dialog.Input;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.logging.log4j.core.pattern.TextRenderer;
import org.joml.Matrix3dStack;
import org.joml.Vector3f;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.input.InputQuirks;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.jos.gui.LocationScreen.accentColor;

public class JustObviousStuffClient implements ClientModInitializer {
    public static final String MOD_ID = "jos";
    public static boolean isEnabled = false;
    public static boolean mouseLock = false;
    public static boolean shouldHold = true;
    public static boolean shouldText = false;


    private static KeyMapping toggleBind;
    private static KeyMapping clearBind;
    private static KeyMapping guiBind;
    private static KeyMapping releaseBind;

    @Override
    public void onInitializeClient() {

        PlayerUtils.instance().init();
        LocationManager.instance().init();
        KeyUtils.init();


        ClientWorldEvents.AFTER_CLIENT_WORLD_CHANGE.register((mc, client) -> {
            LocationManager.instance().resetActive();
        });

        ClientTickEvents.END_CLIENT_TICK.register(mc -> {
        });

        WorldRenderEvents.AFTER_ENTITIES.register((context) -> {
            if (!isEnabled) return;
            LocationManager.instance().locations().forEach(location -> {
                if (Minecraft.getInstance().player == null || Minecraft.getInstance().level == null) return;

                Renderable circleRender = new CircleRender(location.pos(), new Color(accentColor), 1f);
                circleRender.render(context);

                JosRender.getInstance().draw(Minecraft.getInstance(), circleRender.getPipeline());


                ArrayList<String> lines = new ArrayList<>();
                lines.addAll(location.commands());
                location.keys().forEach(key -> lines.add(key.getDisplayName().getString()));

                Renderable textRender = new TextRender(location.pos(), location.num(), lines);
                textRender.render(context);
            });

        });
    }
}
