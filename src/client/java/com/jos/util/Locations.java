package com.jos.util;

import com.google.gson.annotations.Expose;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.server.dialog.Input;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.jos.JustObviousStuffClient.isEnabled;

public class Locations {
    Vec3 pos;
    int num;
    Set<Integer> keys = new HashSet<>();
    Set<String> commands = new HashSet<>();

    Locations(Vec3 pos) {
        this.pos = pos;
        this.num(LocationManager.instance().locations().size() + 1);
    }

    public Vec3 pos() {return this.pos;}
    public int num() {return this.num;}

    public void addKey(int key) {
        this.keys.add(key);
    }
    public void removeKey(int key) {
        this.keys.remove(key);
    }

    public void addCommand(String command) {this.commands.add(command);}
    public void removeCommand(String command) {this.commands.remove(command);}

    public Set<String> commands() {
        if (this.commands == null) this.commands = new HashSet<>();
        return this.commands;
    }

    public Set<InputConstants.Key> keys() {
        Set<InputConstants.Key> keys = new HashSet<>();
        this.keys.forEach((key) -> {
            if (key == InputConstants.MOUSE_BUTTON_LEFT || key == InputConstants.MOUSE_BUTTON_RIGHT) {
                keys.add(InputConstants.Type.MOUSE.getOrCreate(key));
            } else {
                keys.add(InputConstants.Type.KEYSYM.getOrCreate(key));
            }
        });
        return keys;
    }


    public void num(int num) {this.num = num;}
}
