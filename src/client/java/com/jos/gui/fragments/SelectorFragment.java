package com.jos.gui.fragments;

import com.jos.gui.InputScreen;
import com.jos.gui.LocationScreen;
import com.jos.gui.buttons.ClickButton;
import com.jos.gui.buttons.ClickDropdown;
import com.jos.storage.Storage;
import com.jos.util.LocationManager;
import com.jos.util.Locations;
import com.jos.util.Util;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.UIComponents;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.UIContainers;
import io.wispforest.owo.ui.core.*;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;

import static com.jos.gui.LocationScreen.*;

public class SelectorFragment {

    public static FlowLayout create(FlowLayout root) {
        var body = UIContainers.horizontalFlow(Sizing.fill(100), Sizing.content());
        body.horizontalAlignment(HorizontalAlignment.LEFT);
        body.verticalAlignment(VerticalAlignment.TOP);
        body.gap(5);
        body.margins(Insets.of(5));

        var dropDown = new ClickDropdown(Component.literal("Locations") , (self, stack) -> {
            LocationManager.instance().locations().forEach(location -> {
                var button = new ClickButton(
                        Component.literal(String.valueOf(location.num())),
                        but -> {
                            LocationManager.instance().selected(location);
                            Minecraft.getInstance().setScreen(new LocationScreen());

                            self.toggleExpansion();
                        }
                );

                button.renderer(ButtonComponent.Renderer.flat(backColor, accentColor, backColor));
                button.sizing(Sizing.content(), Sizing.content());
                stack.child(button);
            });
        });

        var create = new ClickButton(Component.literal("Create Location"), but -> {
            LocationManager.instance().createLocation(Minecraft.getInstance().player.position());
            Minecraft.getInstance().setScreen(new LocationScreen());
        });
        var selectText = LocationManager.instance().selected() == null ? "" : LocationManager.instance().selected().num();
        var selected = UIComponents.label(Component.literal("Select Location: " + selectText));

        var save =  new ClickButton(Component.literal("Save"), but -> {
            Storage.save(LocationManager.instance().locations());
            Util.sendMsg(Component.literal("Saved successfully!"));
        });

        body.child(dropDown);
        body.child(create);
        body.child(selected);
        body.child(save);


        return body;
    }
}
