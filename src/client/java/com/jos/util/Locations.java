package com.jos.util;

import com.google.gson.annotations.Expose;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.server.dialog.Input;
import net.minecraft.world.phys.Vec3;

import java.util.HashSet;
import java.util.Set;

import static com.jos.JustObviousStuffClient.isEnabled;

public class Locations {
    Vec3 pos;
    int num;
    Set<Integer> keys = new HashSet<>();

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


    public void onEnter() {
        if (LocationManager.instance().lastEntered() != this.num) {

            KeyMapping.releaseAll();

            if (!this.keys.isEmpty()) {
                this.keys().forEach(key -> {
                    KeyMapping.set(key, true);
                });
            }

        }
        LocationManager.instance().lastEntered(this.num);
    }
    public void onLeave() {
        LocationManager.instance().lastEntered(0);
    }
}
