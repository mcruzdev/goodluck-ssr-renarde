package dev.matheuscruz.model;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Database {

    public static final Map<String, DrawingModel> DRAWINGS = new ConcurrentHashMap<>();

    static {
        DRAWINGS.put("496a2801-322a-411d-a562-8e044df56b60", new DrawingModel(
                new HashSet<>(),
                "http://localhost:8080/drawings/496a2801-322a-411d-a562-8e044df56b60/participants",
                null
        ));
    }

    public static void add(String key, DrawingModel drawingModel) {
        DRAWINGS.put(key, drawingModel);
    }

    public static DrawingModel get(String key) {
        return DRAWINGS.get(key);
    }
}
