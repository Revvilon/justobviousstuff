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

    public interface StorageUtil<T> {
        void save(ArrayList<T> data);
        ArrayList<T> load();
    }

    private static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .create();

    private static final Path FILE_PATH = FabricLoader.getInstance().getConfigDir().resolve("locations.json");

    public static class JsonStorage<T>  implements StorageUtil<T> {
        private final Path path;
        private final Type type;

        public JsonStorage(Path path, TypeToken<ArrayList<T>> typeToken) {
            this.path = path;
            this.type = typeToken.getType();
        }

        @Override
        public void save(ArrayList<T> data) {
            try (Writer writer = new FileWriter(path.toFile())) {
                GSON.toJson(data, writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public ArrayList<T> load() {
            File file = path.toFile();
            if (!file.exists()) return new ArrayList<>();

            try (Reader reader = new FileReader(file)) {
                ArrayList<T> result = GSON.fromJson(reader, type);
                return result != null ? result : new ArrayList<>();
            } catch (IOException e) {
                e.printStackTrace();
                return new ArrayList<>();
            }
        }
    }

    public static final JsonStorage<Locations> LOCATION_HANDLER = new JsonStorage<>(
            FabricLoader.getInstance().getConfigDir().resolve("locations.json"),
            new TypeToken<ArrayList<Locations>>() {}
    );
}
