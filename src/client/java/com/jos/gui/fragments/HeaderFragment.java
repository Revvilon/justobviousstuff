package com.jos.gui.fragments;

import com.jos.gui.buttons.ClickButton;
import io.wispforest.owo.ui.base.BaseParentUIComponent;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.component.UIComponents;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.GridLayout;
import io.wispforest.owo.ui.container.OverlayContainer;
import io.wispforest.owo.ui.container.UIContainers;
import io.wispforest.owo.ui.core.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.joml.Matrix3x2f;

import static com.jos.gui.LocationScreen.fontSize;

public class HeaderFragment {
    public static FlowLayout create(Runnable onClose) {

        var header = UIContainers.horizontalFlow(Sizing.fill(100), Sizing.content());

        var label = UIComponents.label(Component.literal("Just Obvious Stuff"))
                .verticalTextAlignment(VerticalAlignment.CENTER)
                .horizontalTextAlignment(HorizontalAlignment.CENTER);

        var labelContainer = UIContainers.horizontalFlow(Sizing.content(), Sizing.content()).child(label);
        labelContainer.positioning(Positioning.relative(50, 50));

        var closeButton = new ClickButton(Component.literal("X").withStyle(ChatFormatting.BOLD), onClose);

        var buttonContainer = UIContainers.horizontalFlow(Sizing.content(), Sizing.content()).child(closeButton);

        header.horizontalAlignment(HorizontalAlignment.RIGHT).verticalAlignment(VerticalAlignment.CENTER);

        header.child(labelContainer).child(buttonContainer);


        return header;
    }
}
