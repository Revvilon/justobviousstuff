package com.jos.render;

import com.jos.JustObviousStuffClient;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.phys.Vec3;
import com.mojang.blaze3d.platform.InputConstants;


import java.awt.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Set;

public class TextRender implements  Renderable {
    private Vec3 pos;
    private int num;
    private ArrayList<String> text;

    public TextRender(Vec3 pos, int num, ArrayList<String> text) {
        this.pos = pos;
        this.num = num;
        this.text = text;
    }

    @Override
    public void render(WorldRenderContext context) {
        if (!JustObviousStuffClient.shouldText) return;
        ArrayList<Component> lines = new ArrayList<>();

        lines.add(Component.literal("ID: " + this.num));

        this.text.forEach(line -> {
            lines.add(Component.literal(line));
        });


        PoseStack poseStack = context.matrices();
        CameraRenderState cam = context.worldState().cameraRenderState;
        Font  font = Minecraft.getInstance().font;

        double x = pos.x - cam.pos.x;
        double y = pos.y + 1.5 - cam.pos.y;
        double z = pos.z - cam.pos.z;

        poseStack.pushPose();
        poseStack.translate(x, y, z);
        poseStack.mulPose(cam.orientation);
        poseStack.scale(0.025f, -0.025f, 0.025f);

        for (int i = 0; i < lines.size(); i++) {

            Component line = lines.get(i);
            float width = (float)(-font.width(line)) / 2;

            float yOffset = i * font.lineHeight + 2;

            font.drawInBatch(line, width, yOffset, Color.white.getRGB(),
                    false,
                    poseStack.last().pose(),
                    context.consumers(),
                    Font.DisplayMode.SEE_THROUGH,
                    new Color(0, 0, 0, 50).getRGB(),
                    15728880
            );

        }
        poseStack.popPose();

    }

    @Override
    public RenderPipeline getPipeline() {
        return null;
    }
}
