package com.jos.util;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

import static com.jos.JustObviousStuffClient.mouseLock;
import static com.jos.JustObviousStuffClient.shouldHold;

public class Util {
    public static void sendMsg(Component component) {
        if (Minecraft.getInstance().player == null) return;
        Component message = Component.empty()
                .append(Component.literal(("[")).withStyle(ChatFormatting.WHITE, ChatFormatting.BOLD))
                .append(Component.literal(("JOS")).withStyle(ChatFormatting.DARK_AQUA, ChatFormatting.BOLD))
                .append(Component.literal(("] ")).withStyle(ChatFormatting.WHITE, ChatFormatting.BOLD))
                .append(component);

        Minecraft.getInstance().player.displayClientMessage(message, false);
    }

    private static void mouseLock() {
        Util.sendMsg(Component.literal("Mouse lock " + (mouseLock ? "enabled" : "disabled")));
    }

    public static void toggleMouseLock() {
        mouseLock = !mouseLock;
        mouseLock();
    }

    public static void toggleMouseLock(boolean val) {
        if (mouseLock == val) return;
        mouseLock = val;
        mouseLock();
    }

    public static void toggleHold(boolean val) {
        shouldHold = val;
    }
}
