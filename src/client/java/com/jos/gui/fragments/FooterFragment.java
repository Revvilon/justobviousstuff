package com.jos.gui.fragments;

import com.jos.JustObviousStuffClient;
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

public class FooterFragment {

    public static FlowLayout create(FlowLayout root) {
        var body = UIContainers.horizontalFlow(Sizing.fill(100), Sizing.content());
        body.horizontalAlignment(HorizontalAlignment.LEFT);
        body.verticalAlignment(VerticalAlignment.CENTER);
        body.gap(5);
        body.margins(Insets.of(5));

        var toggleTextButton = new ClickButton(Component.literal("Text Render"), but -> {
            JustObviousStuffClient.shouldText = !JustObviousStuffClient.shouldText;
        });

        body.child(toggleTextButton);


        return body;
    }
}
