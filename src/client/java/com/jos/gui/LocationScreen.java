package com.jos.gui;

import com.jos.gui.buttons.ClickButton;
import com.jos.gui.buttons.ClickDropdown;
import com.jos.gui.fragments.FooterFragment;
import com.jos.gui.fragments.HeaderFragment;
import com.jos.gui.fragments.InputsFragment;
import com.jos.gui.fragments.SelectorFragment;
import com.jos.util.LocationManager;
import com.jos.util.Locations;
import io.wispforest.owo.braid.widgets.owoui.OwoUIWidget;
import io.wispforest.owo.ui.base.BaseOwoContainerScreen;
import io.wispforest.owo.ui.base.BaseOwoScreen;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.component.DropdownComponent;
import io.wispforest.owo.ui.component.UIComponents;
import io.wispforest.owo.ui.container.FlowLayout;
import io.wispforest.owo.ui.container.UIContainers;
import io.wispforest.owo.ui.core.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.Flow;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.jos.gui.fragments.SelectorFragment.*;

public class LocationScreen extends BaseOwoScreen<FlowLayout> {

    public static final int accentColor = new java.awt.Color(47, 133, 248, 255).getRGB();
    public static final int hoverColor = new java.awt.Color(accentColor).brighter().getRGB();
    public static final int clickColor = new java.awt.Color(accentColor).darker().getRGB();

    public static final int backColor = new  java.awt.Color(2, 4, 7).getRGB();
    public static final int fontSize = Minecraft.getInstance().font.lineHeight;

    public static final ButtonComponent.Renderer buttonRenderer = ((context, button, delta) -> {
       context.fill(button.getX(), button.getY(), button.getX() + button.getWidth(), button.getY() + button.getHeight(), accentColor);
    });
    public static final ButtonComponent.Renderer buttonHoverRenderer = ((context, button, delta) -> {
        context.fill(button.getX(), button.getY(), button.getX() + button.getWidth(), button.getY() + button.getHeight(), hoverColor);
    });
    public static final ButtonComponent.Renderer buttonClickedRenderer = ((context, button, delta) -> {
        context.fill(button.getX(), button.getY(), button.getX() + button.getWidth(), button.getY() + button.getHeight(), clickColor);
    });


    public static Locations selectedLocation;

    @Override
    protected @NotNull OwoUIAdapter<FlowLayout> createAdapter() {
        return OwoUIAdapter.create(this, UIContainers::verticalFlow);
    }

    @Override
    protected void build(FlowLayout root) {
        root.surface(Surface.VANILLA_TRANSLUCENT)
                .horizontalAlignment(HorizontalAlignment.CENTER)
                .verticalAlignment(VerticalAlignment.CENTER);

        FlowLayout mainPanel = UIContainers.verticalFlow(Sizing.fill(60),  Sizing.content());

        mainPanel.padding(Insets.of(10)).surface(Surface.flat(backColor));
        mainPanel.verticalAlignment(VerticalAlignment.CENTER);
        mainPanel.horizontalAlignment(HorizontalAlignment.CENTER);

        mainPanel.child(HeaderFragment.create(this::onClose));
        mainPanel.child(SelectorFragment.create(root));

        mainPanel.child(InputsFragment.create());

        mainPanel.child(FooterFragment.create(root));

        root.child(mainPanel);
    }

}
