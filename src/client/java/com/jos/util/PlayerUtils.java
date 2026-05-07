package com.jos.util;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.jos.JustObviousStuffClient.isEnabled;

public class PlayerUtils {
    private static final PlayerUtils INSTANCE = new PlayerUtils();

    public static PlayerUtils instance() {
        return INSTANCE;
    }

    public void init() {
        ClientTickEvents.END_CLIENT_TICK.register(INSTANCE::onTick);
    }

    public void onTick(Minecraft mc) {
        whenInside(mc);
    }

    public static boolean isInside(Locations location, Player player) {
        double dx = player.getX() - location.pos().x;
        double dz = player.getZ() - location.pos().z;
        double distSq = dx * dx + dz * dz;

        return distSq <= 1.0f && player.getY() >= (location.pos.y - .5) && player.getY() <= (location.pos.y + 1);
    }

    private void whenInside(Minecraft mc) {
        if (!isEnabled) return;

        Player player = mc.player;

        if (player == null || mc.level == null) return;

        LocationManager locationManager = LocationManager.instance();

        List<Locations> locationsList = locationManager.locations();
        locationsList.forEach((loc) -> {
            boolean currentlyInside = isInside(loc, player);

            if (currentlyInside) {
                locationManager.onEnter(loc);
            }
        });
    }

    public static void sendCommand(String command) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        String formatted = command.replaceFirst("/", "");

        mc.player.connection.sendCommand(formatted);
    }
}
