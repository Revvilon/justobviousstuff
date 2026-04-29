package com.jos.gui.buttons;

import com.jos.JustObviousStuffClient;
import com.jos.gui.LocationScreen;
import com.jos.gui.fragments.InputsFragment;
import com.jos.util.LocationManager;
import com.jos.util.Locations;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.DropdownComponent;
import io.wispforest.owo.ui.component.LabelComponent;
import io.wispforest.owo.ui.container.CollapsibleContainer;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.UIContainers;
import io.wispforest.owo.ui.core.*;
import net.minecraft.network.chat.Component;

import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.jos.gui.LocationScreen.*;

public class ClickDropdown extends CollapsibleContainer {
    public ClickDropdown(Component name, BiConsumer<ClickDropdown, FlowLayout> contentBuilder) {
        super(Sizing.content(), Sizing.content(), name, false);


        this.verticalAlignment(VerticalAlignment.CENTER);
        this.horizontalAlignment(HorizontalAlignment.LEFT);
        this.contentLayout.surface(Surface.BLANK);

        this.titleLayout.surface(Surface.flat(accentColor));

        var stack = UIContainers.verticalFlow(Sizing.content(), Sizing.content());
        stack.padding(Insets.of(2));

        contentBuilder.accept(this, stack);

        var scroll = UIContainers.verticalScroll(Sizing.content(), Sizing.fixed(100), stack);
        this.child(scroll);
    }


}
