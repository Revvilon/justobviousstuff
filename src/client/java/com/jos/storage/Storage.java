package com.jos.storage;


import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jos.util.Locations;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.world.phys.Vec3;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.ArrayList;

import static com.jos.JustObviousStuffClient.MOD_ID;

public class Storage {
    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Path FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve("locations.json");

    public static void save(ArrayList<Locations> locations) {
        try (Writer writer = new FileWriter(FILE_PATH.toFile())) {
            GSON.toJson(locations, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Locations> load() {
        File file = FILE_PATH.toFile();
        if (!file.exists()) return new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Locations>>() {}.getType();
            return GSON.fromJson(reader, listType);
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
