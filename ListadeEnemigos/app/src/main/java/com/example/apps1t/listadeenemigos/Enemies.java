package com.example.apps1t.listadeenemigos;

import java.util.ArrayList;
import com.google.gson.Gson;

public class Enemies extends ArrayList<Enemy> {

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    static public Enemies fromJson(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Enemies.class);
    }
}
