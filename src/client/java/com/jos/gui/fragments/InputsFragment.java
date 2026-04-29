package com.jos.gui.fragments;

import com.jos.gui.InputScreen;
import com.jos.gui.LocationScreen;
import com.jos.gui.buttons.ClickButton;
import com.jos.util.KeyUtils;
import com.jos.util.LocationManager;
import com.jos.util.Locations;
import io.wispforest.owo.ui.component.UIComponents;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.ScrollContainer;
import io.wispforest.owo.ui.container.UIContainers;
import io.wispforest.owo.ui.core.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;



public class InputsFragment {

    private static FlowLayout container;

    public static ScrollContainer<FlowLayout> create() {

        container = UIContainers.verticalFlow(Sizing.fill(100), Sizing.content());
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


        add();

        location.keys().forEach(key -> {

            var line = UIContainers.horizontalFlow(Sizing.fill(100), Sizing.content());

            var label = UIComponents.label(Component.literal("Press: " + key.getDisplayName().getString()).withStyle(ChatFormatting.BOLD));
            label.verticalTextAlignment(VerticalAlignment.CENTER)
                    .horizontalTextAlignment(HorizontalAlignment.CENTER);

            var remove = new ClickButton(Component.literal("Remove keybind"), button -> {
                location.removeKey(key.getValue());
                setMappings();
            });

            line.child(label).child(remove);

            container.child(line);
        });


        remove();
    }

    private static void add() {
        container.child(new ClickButton(Component.literal("Add keybind"), button -> {
            Minecraft.getInstance().setScreen(new InputScreen());
        }));
    }

    private static void remove() {
        container.child(new ClickButton(Component.literal("Remove Location"), button -> {
            LocationManager.instance().removeLocation(LocationManager.instance().selected());
            Minecraft.getInstance().setScreen(new LocationScreen());
        }));
    }
}
