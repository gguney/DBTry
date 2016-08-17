package com.example.gg.dbtry.models;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import com.example.gg.dbtry.Applic;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by GG on 12.06.2016.
 */
public class Model{
    protected static String TABLE;
    protected static String PRIMARY_KEY = "id";
    protected static List<String> COLUMNS;
    protected static List<String> TYPES;
    protected static List<String> COMMAN_COLUMNS = Arrays.asList("created_date DATETIME default current_timestamp", "updated_date DATETIME", "delete_date DATETIME","is_deleted BOOL default 0");
    private static String CREATE_TABLE = "";

    private static final String DELETE_TABLE =
            "DROP TABLE IF EXISTS " + TABLE;


    public Model ()
    {

    }
    public Model(List<String> COLUMNS, List<String> TYPES)
    {
        this.COLUMNS = COLUMNS;
        this.TYPES = TYPES;

    }
    public static String getCreateStatement()
    {
        CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE + " (" +COLUMNS.get(0) + " INTEGER PRIMARY KEY AUTOINCREMENT";
        for (int i=1;i<COLUMNS.size();i++)
        {
            CREATE_TABLE += ", "+COLUMNS.get(i)+" "+TYPES.get(i);
        }
        for (String column: COMMAN_COLUMNS)
        {
            CREATE_TABLE += ", "+column;
        }
        CREATE_TABLE += ")";
        Log.i("CREATE STATEMENT FOR "+TABLE, CREATE_TABLE);
        return CREATE_TABLE;
    }
    public void createTable()
    {
        Applic.getDBHelper().execute(getCreateStatement());
    }
    public void deleteTable()
    {
        Applic.getDBHelper().execute(getDeleteStatement());
    }

    public static String getDeleteStatement()
    {
        return DELETE_TABLE;
    }

    public static String getTABLE() {
        return TABLE;
    }

    public static void setTABLE(String tableName) {
        TABLE = tableName;
    }

  //  public <T> T findById( int id)

    public void insert()
    {
        String[] columnsArray = new String[COLUMNS.size()];
        columnsArray = COLUMNS.toArray(columnsArray);
        Applic.getDBHelper().executeInsert(this.TABLE, reflectToContentValues(this));

    }
    public void update()
    {

    }
    public void delete()
    {

    }

    public <T> T findById(int id)
    {
        String[] columnsArray = new String[COLUMNS.size()];
        columnsArray = COLUMNS.toArray(columnsArray);
        return (T) reflectToObject(Applic.getDBHelper().executeFindById(this.TABLE,columnsArray,id));
    }
    public <T> T where(String[] by,String[] eq, String[] values)
    {
        String[] columnsArray = new String[COLUMNS.size()];
        columnsArray = COLUMNS.toArray(columnsArray);
        return (T) reflectToObject(Applic.getDBHelper().executeWhere(this.TABLE, columnsArray, by, eq, values));

    }
    public Object reflectToObject(ContentValues contentValues){
        Object object = null;
        Class reflectClass = this.getClass();
        try {
            object= Class.forName(this.getClass().getName()).getConstructor().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        //Log.i("DBTRYOBJECT",object.toString());

        Method method = null;
        String methodName =  "";
        Log.i("CONTENTSSS",contentValues.toString());
        for(int i=0;i<contentValues.size();i++)
        {
            //Log.i("DBTRYCONTENTVALUE",COLUMNS.get(i));
            //Log.i("DBTRYCONTENTVALUE--",contentValues.get(COLUMNS.get(i)).getClass().toString());
            methodName = upperCaseFirst(COLUMNS.get(i));
            //  Log.i("DBTRYMETHODNAME",methodName);

            try {
                switch (contentValues.get(COLUMNS.get(i)).getClass().toString())
                {
                    case "class java.lang.String":
                        method = reflectClass.getDeclaredMethod("set"+methodName,String.class);
                        break;
                    case "class java.lang.Integer":
                        method = reflectClass.getDeclaredMethod("set"+methodName,int.class);
                        break;
                    default:
                        method = reflectClass.getDeclaredMethod("set"+methodName,String.class);
                        break;


                }
                method.invoke(object,contentValues.get(COLUMNS.get(i)));
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }catch(NullPointerException e)
            {
                e.printStackTrace();
                Log.i("DBTRYEXCEPTION",methodName);
            }



        }

        return reflectClass.cast(object);
    }

    public ContentValues reflectToContentValues(Object object){


        Class reflectClass = this.getClass();
        ContentValues contentValues = new ContentValues();

        Method method = null;
        String methodName =  "";
        String str = "";
        int inte = 0;
        for(int i=0;i<COLUMNS.size();i++)
        {
            methodName = upperCaseFirst(COLUMNS.get(i));
            try {
                switch (TYPES.get(i))
                {
                    case "TEXT":
                        method = reflectClass.getDeclaredMethod("get"+methodName,null);
                        str = (String) method.invoke(object,null);
                        contentValues.put(COLUMNS.get(i),str);

                        break;
                    case "INTEGER":
                        method = reflectClass.getDeclaredMethod("get"+methodName,null);
                        inte = (int) method.invoke(object,null);
                        contentValues.put(COLUMNS.get(i),inte);
                        break;
                    default:
                        method = reflectClass.getDeclaredMethod("get"+methodName,null);
                        str = (String) method.invoke(object,null);
                        contentValues.put(COLUMNS.get(i),str);
                        break;


                }

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }


        }
        Log.i("DBCONTENVALUES", contentValues.toString());
        return contentValues;
    }

    public static String upperCaseFirst(String value) {


        value = value.replace("_"," ");
        String[] array = value.split(" ");
        String newValue = "";
        for (String str: array
             ) {

            char[] chars = str.toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            newValue += new String(chars);
        }
        return newValue;
    }

    /*
    public void insert(Class <T> theClass)
    {

    }
    */


}
