package com.example.gg.dbtry.models;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by GG on 12.06.2016.
 */

public class Vehicle extends Model {

    private static final String TABLE = "Vehicles";
    private int id;
    private String name;
    private int km;
    private int fuelType;
    private int tankSize;
    private String color;

    private static List<String> COLUMNS = Arrays.asList(
            "id",
            "name",
            "km",
            "fuel_type",
            "tank_size",
            "color");

    private static List<String> TYPES = Arrays.asList(
            "INTEGER",
            "TEXT",
            "INTEGER",
            "INTEGER",
            "INTEGER",
            "TEXT");

    public Vehicle()
    {
        super(COLUMNS,TYPES);
        super.setTABLE(TABLE);
        Log.i("VEHICLE CONST", Model.getTABLE()+"");
    }
    public void createTable()
    {
        super.createTable();
    }
    public void deleteTable()
    {
        super.deleteTable();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }

    public int getFuelType() {
        return fuelType;
    }

    public void setFuelType(int fuelType) {
        this.fuelType = fuelType;
    }

    public int getTankSize() {
        return tankSize;
    }

    public void setTankSize(int tankSize) {
        this.tankSize = tankSize;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString()
    {
        return "VEHICLE OBJECT --- id: "+this.getId()+", name: "+this.getName()+", color: "+this.getColor()+", fuelType: "+this.getFuelType()+", km: "+this.getKm()+", tankSize: "+this.getTankSize();
    }



}
