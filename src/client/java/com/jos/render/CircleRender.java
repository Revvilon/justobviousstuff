package com.jos.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.injection.At;

import java.awt.*;

import static com.jos.JustObviousStuffClient.MOD_ID;
import static com.jos.render.JosRender.*;

public class CircleRender implements Renderable {

    public static final RenderPipeline CIRCLE_PIPELINE = RenderPipelines.register(
            RenderPipeline.builder(RenderPipelines.DEBUG_FILLED_SNIPPET)
                    .withLocation(Identifier.fromNamespaceAndPath(MOD_ID, "pipeline/circle_pipeline"))
                    .withVertexFormat(DefaultVertexFormat.POSITION_COLOR, VertexFormat.Mode.TRIANGLE_STRIP)
                    .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
                    .withCull(false)
                    .withDepthWrite(true)
                    .withDepthBias(-1f, -1f)
                    .build()
    );

    private final Vec3 pos;
    private final int color;
    private final float radius;

    public CircleRender(Vec3 pos, Color color, float radius) {
        this.pos = pos;
        this.color = color.getRGB();
        this.radius = radius;
    }

    @Override
    public RenderPipeline getPipeline() {
        return CIRCLE_PIPELINE;
    }

    @Override
    public void render(WorldRenderContext context) {
        PoseStack matrices = context.matrices();
        Vec3 camera = context.worldState().cameraRenderState.pos;

        matrices.pushPose();

        float x = (float) (pos.x - camera.x);
        float y = (float) (pos.y - camera.y);
        float z = (float) (pos.z - camera.z);

        matrices.translate(x, y, z);

        var buffer = getBuffer(CIRCLE_PIPELINE);

        int numSegments = 32;

        float lineWidth = 0.01f;
        float outerRadius = radius + lineWidth;
        float innerRadius = radius - lineWidth;

        for (int i = 0; i <= numSegments; i++) {
            double angle = i * (2.0 * Math.PI / numSegments);
            float vx = (float) Math.cos(angle);
            float vz = (float) Math.sin(angle);

            buffer.addVertex(matrices.last().pose(), vx * outerRadius, 0, vz * outerRadius)
                    .setColor(color);

            buffer.addVertex(matrices.last().pose(), vx * innerRadius, 0, vz * innerRadius)
                    .setColor(color);
        }

        matrices.popPose();
    }
}
