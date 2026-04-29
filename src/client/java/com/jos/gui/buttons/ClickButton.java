package com.jos.gui.buttons;

import com.jos.gui.LocationScreen;
import io.wispforest.owo.braid.widgets.button.Button;
import io.wispforest.owo.ui.component.ButtonComponent;
import io.wispforest.owo.ui.core.Sizing;
import net.minecraft.network.chat.Component;
import org.joml.Matrix3x2f;

import java.util.function.Consumer;

import static com.jos.gui.LocationScreen.*;

public class ClickButton extends ButtonComponent {


    public ClickButton(Component message, Runnable consumer) {
        super(message, buttonComponent -> {});

        this.message = message;
        this.sizing(Sizing.content());
        this.renderer(LocationScreen.buttonRenderer);
        this.onPress(button -> {
            consumer.run();
        });
        this.renderer(Renderer.flat(accentColor, hoverColor, backColor));
    }
    public ClickButton(Component message, Consumer<ButtonComponent> button) {
        super(message, button);

        this.message = message;
        this.sizing(Sizing.content());
        this.renderer(Renderer.flat(accentColor, hoverColor, backColor));
    }
}
