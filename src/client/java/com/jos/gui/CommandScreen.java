package com.jos.gui;

import com.jos.gui.buttons.ClickButton;
import com.jos.util.LocationManager;
import com.mojang.blaze3d.platform.InputConstants;
import io.wispforest.owo.braid.widgets.textinput.TextBox;
import io.wispforest.owo.braid.widgets.textinput.TextEditingController;
import io.wispforest.owo.braid.widgets.textinput.TextInput;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.TextAreaComponent;
import io.wispforest.owo.ui.component.UIComponents;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.UIContainers;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class CommandScreen extends BaseOwoScreen<FlowLayout> {


    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, UIContainers::horizontalFlow);
    }

    @Override
    public void onClose() {
        Minecraft.getInstance().setScreen(new LocationScreen());
    }

    @Override
    protected void build(FlowLayout root) {
        root.surface(Surface.blur(10, 20))
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);


        var textBox = UIComponents.textBox(Sizing.fill(30)).text("/");

        var saveCmd = new ClickButton(Component.literal("Save"), butt -> {
            String value = textBox.getValue();
            if (value.isBlank() || !value.startsWith("/")) {
                return;
            };
            LocationManager.instance().selected().addCommand(value);
            this.onClose();
        });

        root.child(textBox);
        root.child(saveCmd);

        root.surface(Surface.blur(10, 20));
    }
}
