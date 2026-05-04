package com.jos.gui.fragments;

import com.jos.gui.CommandScreen;
import com.jos.gui.InputScreen;
import com.jos.gui.LocationScreen;
import com.jos.gui.buttons.ClickButton;
import com.jos.util.LocationManager;
import io.wispforest.owo.ui.component.UIComponents;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.ScrollContainer;
import io.wispforest.owo.ui.container.UIContainers;
import io.wispforest.owo.ui.core.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;


public class InputsFragment {

    private static FlowLayout container;

    public static ScrollContainer<FlowLayout> create() {

        container = UIContainers.verticalFlow(Sizing.fill(100), Sizing.content());
        container.padding(Insets.of(5));
        container.gap(5);

        var scrollContainer = UIContainers.verticalScroll(Sizing.fill(100), Sizing.fill(40), container)
                .scrollbarThiccness(4);
        scrollContainer.surface(Surface.PANEL_INSET);

        setMappings();

        return scrollContainer;
    }

    public static void setMappings() {

        var location = LocationManager.instance().selected();

        if (location == null) return;

        container.clearChildren();

        var buttonLine = UIContainers.horizontalFlow(Sizing.fill(100), Sizing.content());
        buttonLine.gap(5);

        buttonLine.child(new ClickButton(Component.literal("Add command"), button -> {
            Minecraft.getInstance().setScreen(new CommandScreen());
        }));
        buttonLine.child(new ClickButton(Component.literal("Add keybind"), button -> {
            Minecraft.getInstance().setScreen(new InputScreen());
        }));
        buttonLine.child(new ClickButton(Component.literal("Delete Location"), button -> {
            LocationManager.instance().removeLocation(LocationManager.instance().selected());
            Minecraft.getInstance().setScreen(new LocationScreen());
        }));

        container.child(buttonLine);


        location.keys().forEach(key -> {

            var line = UIContainers.horizontalFlow(Sizing.fill(100), Sizing.content());
            line.gap(5);

            var label = UIComponents.label(Component.literal("Press: " + key.getDisplayName().getString()).withStyle(ChatFormatting.BOLD));
            label.verticalTextAlignment(VerticalAlignment.CENTER);

            var remove = new ClickButton(Component.literal("X"), button -> {
                location.removeKey(key.getValue());
                setMappings();
            });


            line.child(remove).child(label);

            container.child(line);
        });

        location.commands().forEach(command -> {
            var line = UIContainers.horizontalFlow(Sizing.fill(100), Sizing.content());
            line.gap(5);
            var label = UIComponents.label(Component.literal("Command: " + command).withStyle(ChatFormatting.BOLD));
            label.verticalTextAlignment(VerticalAlignment.CENTER);


            var remove = new ClickButton(Component.literal("X"), button -> {
                location.removeCommand(command);
                setMappings();
            });

            line.child(remove).child(label);
            container.child(line);

        });
    }
}
