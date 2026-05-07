package com.jos.util;

import com.jos.storage.Storage;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.Component;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class LocationManager {
    private static final LocationManager locationManager = new LocationManager();
    public static LocationManager instance() {
        return locationManager;
    }

    private ArrayList<Locations> locations;
    private Locations selected;

    private Locations active;

    public void init() {
        this.locations = Storage.LOCATION_HANDLER.load();
    }

    public void createLocation(Vec3 pos) {
        var location = new Locations(pos);
        this.locations.add(location);
        this.selected = location;

    }
    public void removeLocation(Locations location) {
        this.locations.remove(location);
        organize();
    }

    public ArrayList<Locations> locations() {
        return locations;
    }


    public void selected(Locations selected) {this.selected = selected;}
    public Locations selected() {
        if (this.locations.contains(this.selected)) {return this.selected;}
        return null;
    }

    public void active(Locations active) {this.active = active;}
    public Locations active() {return this.active;}
    public void resetActive() {
        KeyUtils.releaseKeys();
        this.active = null;
    }

    public void onEnter(Locations location) {
        if (location.equals(this.active)) {return;}
        if (this.active != null) KeyUtils.releaseKeys();
        this.active = location;

        location.commands().forEach(PlayerUtils::sendCommand);


    }

    private void organize() {
        if (this.locations.isEmpty()) return;
        for (int i = 1; i < this.locations.size(); i++) {
            this.locations.get(i).num(i);
        }
    }
}
