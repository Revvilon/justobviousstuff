package com.jos.render;

import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderContext;

public interface Renderable {
    void render(WorldRenderContext context);

    RenderPipeline getPipeline();
}
