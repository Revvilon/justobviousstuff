package com.jos.util;

import com.jos.storage.Storage;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;

public class LocationManager {
    private static final LocationManager locationManager = new LocationManager();
    private int lastEntered = 0;
    public static LocationManager instance() {
        return locationManager;
    }

    private ArrayList<Locations> locations;
    private Locations selected;

    public void init() {
        this.locations = Storage.load();
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

    public void clearList() {
    }
    public ArrayList<Locations> locations() {
        return locations;
    }

    public void lastEntered(int num) {
        this.lastEntered = num;
    }
    public int lastEntered() {
        return this.lastEntered;
    }

    public void selected(Locations selected) {this.selected = selected;}
    public Locations selected() {
        if (this.locations.contains(this.selected)) {return this.selected;}
        return null;
    }

    private void organize() {
        if (this.locations.isEmpty()) return;
        for (int i = 1; i < this.locations.size(); i++) {
            this.locations.get(i).num(i);
        }
    }
}
