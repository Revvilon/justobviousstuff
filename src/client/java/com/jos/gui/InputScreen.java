package com.jos.gui;

import com.jos.util.LocationManager;
import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.UIComponents;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.UIContainers;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class InputScreen extends BaseOwoScreen<FlowLayout> {


    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, UIContainers::verticalFlow);
    }

    @Override
    public boolean keyPressed(KeyEvent input) {

        InputConstants.Key key = InputConstants.getKey(input);

        if (key.getValue() == InputConstants.KEY_ESCAPE) {
            this.onClose();
            return true;
        }

        LocationManager.instance().selected().addKey(key.getValue());


        this.onClose();
        Minecraft mc = Minecraft.getInstance();
        mc.setScreen(new LocationScreen());

        return super.keyPressed(input);
    }

    @Override
    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {

        LocationManager.instance().selected().addKey(InputConstants.Type.MOUSE.getOrCreate(click.button()).getValue());


        this.onClose();
        Minecraft.getInstance().setScreen(new LocationScreen());


        return super.mouseClicked(click, doubled);
    }

    @Override
    protected void build(FlowLayout root) {
        root.surface(Surface.blur(10, 20))
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        root.child(UIComponents.label(Component.literal("Press any button...")).positioning(Positioning.relative(50, 50)));
    }
}
