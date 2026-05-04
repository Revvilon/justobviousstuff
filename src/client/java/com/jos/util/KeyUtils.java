package com.jos.util;

import com.jos.JustObviousStuffClient;
import com.jos.gui.LocationScreen;
import com.jos.gui.MouseScreen;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.Options;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import org.jspecify.annotations.Nullable;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.jos.JustObviousStuffClient.*;

public class KeyUtils {

    public static final KeyMapping.Category CATEGORY = KeyMapping.Category.register(Identifier.parse("jos"));
    public static final String TOGGLE = "key.jos.toggle_mod";
    public static final String GUI =  "key.jos.open_gui";
    public static final String RELEASE =  "key.jos.release_keys";
    public static final String MOUSE = "key.jos.mouse_lock";

    public static KeyMapping toggleKey;
    public static KeyMapping guiKey;
    public static KeyMapping releaseKey;
    public static KeyMapping mouseKey;

    public static void registerKeyInputs() {
        holdKeys();
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            while (guiKey.consumeClick()) {
                client.setScreen(new LocationScreen());
            }
            while (releaseKey.consumeClick()) {
                mouseLock = false;
                releaseKeys();
            }
            while (toggleKey.consumeClick()) {
                toggleKey.setDown(false);
                isEnabled = !isEnabled;
                Util.sendMsg(Component.literal("Jos is now " + (isEnabled ? "enabled" : "disabled")));
                if (!isEnabled) {KeyMapping.releaseAll();}
            }
            while (mouseKey.consumeClick()) {
                mouseKey.setDown(false);
                Util.toggleMouseLock();
            }
        });
    }

    public static void init() {
        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                TOGGLE,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                CATEGORY
        ));
        releaseKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                RELEASE,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                CATEGORY
        ));
        guiKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                GUI,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                CATEGORY
        ));
        mouseKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                MOUSE,
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_PERIOD,
                CATEGORY
        ));
        registerKeyInputs();
    }

    static final Set<InputConstants.Key> keys = new  HashSet<>();
    public static void holdKeys(@Nullable Set<InputConstants.Key> ke) {
        keys.clear();
        if (ke == null) return;
        keys.addAll(ke);
    }

    public static void releaseKeys() {
        keys.forEach(key -> {
            KeyMapping.set(key, false);
        });
        holdKeys(null);
    }

    private static void holdKeys() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (!isEnabled) releaseKeys();
            keys.forEach((key) -> KeyMapping.set(key, true));
        });
    }
}
