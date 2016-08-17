package com.example.gg.dbtry;

import android.app.Application;
import android.util.Log;

import com.example.gg.dbtry.models.Vehicle;

/**
 * Created by GG on 12.06.2016.
 */

public class Applic extends Application {
    private static Applic instance;
    private static DBHelper dbHelper;

    @Override
    public void onCreate() {
        instance = this;
        dbHelper = new DBHelper(this.getApplicationContext());
        Vehicle vehicle = new Vehicle();
        vehicle.createTable();
        vehicle = null;


        super.onCreate();
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
       // SugarContext.terminate();
    }
    public static Applic getInstance() {
        return instance;
    }
    public static DBHelper getDBHelper()
    {
        return dbHelper;
    }

}
