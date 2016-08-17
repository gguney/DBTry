package com.example.gg.dbtry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.SyncStateContract;
import android.util.Log;

import com.example.gg.dbtry.models.Vehicle;

import java.util.Arrays;


/**
 * Created by GG on 8.06.2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "orm.db";
    public static SQLiteDatabase writableDB;
    public static SQLiteDatabase readableDB;
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
       // Log.i("DBTRY","DBHelper Class constructed");

        //Vehicle veh = new Vehicle();
      //  veh.createTable();
        //db.execSQL(SQL_CREATE_ENTRIES);

    }
    public void execute(String query)
    {
        SQLiteDatabase writableDB = this.getWritableDatabase();
        try
        {
            writableDB.execSQL(query);
        }
        catch(SQLiteException e)
        {
            Log.i("SQL EXECUTE ERROR", e+"");

        }
        finally
        {
            if(writableDB!=null)
                writableDB.close();
        }
    }
    public ContentValues executeFindById(String table, String[] columns, int id)
    {
        SQLiteDatabase readableDB = this.getWritableDatabase();

        try
        {
            // 2. build query
            Cursor cursor =
                    readableDB.query(table, // a. table
                            columns, // b. column names
                            " id = ?", // c. selections
                            new String[] { id+"" }, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit

            if (cursor != null)
            {
                cursor.moveToFirst();
                return cursorRowToContentValues(cursor);

            }
        }
        catch(SQLiteException e)
        {
            Log.e("SQL EXECUTE ERROR", e+"");

        }
        finally
        {
            if(readableDB!=null)
                readableDB.close();

        }
        return null;

    }
    public ContentValues executeWhere(String table, String[] columns, String[] by, String[] eq,String[] values)
    {
        SQLiteDatabase readableDB = this.getWritableDatabase();
        String selections = "";
        for (int i=0; i<by.length;i++)
        {
            if(i!=0)
                selections += "AND ";
            selections += by[i]+" "+eq[i]+" ?";
        }
        try
        {
            Cursor cursor =
                    readableDB.query(table, // a. table
                            columns, // b. column names
                            selections, // c. selections
                            values, // d. selections args
                            null, // e. group by
                            null, // f. having
                            null, // g. order by
                            null); // h. limit

            if (cursor != null)
            {

                cursor.moveToFirst();
                return cursorRowToContentValues(cursor);

            }
        }
        catch(SQLiteException e)
        {
            Log.e("SQLEXECUTE ERROR", e+"");

        }
        finally
        {
            if(readableDB!=null)
                readableDB.close();
        }
        return null;

    }

    public void executeInsert(String table, ContentValues contentValues){
        // 1. get reference to writable DB

        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        contentValues.remove("id");
        // 3. insert
       // tableName = "'"+tableName+"'";
        db.insert(table,null,contentValues);

        // 4. close
        db.close();
    }
/*

       public List getAllRecords(String tableName) {
    	 List<Record> records = new ArrayList();

        // 1. build the query
        String query = "SELECT * FROM " + "'"+tableName+"'";

    	// 2. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        // 3. go over each row, build book and add it to list
        Record record = null ;
        int i = 0;
        if (cursor.moveToFirst()) {
            do {

                	record = new Record();

                	record.setRowNo(cursor.getInt(0));
                	record.setTypeSpinnerID(cursor.getInt(1));
                    record.setSelectedItemPosition(cursor.getInt(2));
            		record.setTypeNameID(cursor.getInt(3));
            		record.setTypeName(cursor.getString(4));
            		record.setStageNameID(cursor.getInt(5));
            		record.setStageName(cursor.getString(6));
            		record.setDistanceID(cursor.getInt(7));
            		record.setDistance(cursor.getString(8));
            		record.setTargetTimeID(cursor.getInt(9));
            		record.setTargetTime(cursor.getString(10));
            		record.setCarDueID(cursor.getInt(11));
            		record.setCarDue(cursor.getString(12));
            		record.setRowID(cursor.getInt(13));
            		record.setDayCount(cursor.getInt(14));
            		record.setStageTimeID(cursor.getInt(15));
            		record.setStageTime(cursor.getString(16));
            		record.setPenaltyTimeID(cursor.getInt(17));
            		record.setPenaltyTime(cursor.getString(18));

                	records.add(record);

            } while (cursor.moveToNext());
        }

        return records;
}

    public void updateRecord(String tableName,Record record)
    {

    	// 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

		// 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(RECORD_COLUMNS[0], record.getRowNo());
        values.put(RECORD_COLUMNS[1], record.getTypeSpinnerID());
        values.put(RECORD_COLUMNS[2], record.getSelectedItemPosition());
        values.put(RECORD_COLUMNS[3], record.getTypeNameID());
        values.put(RECORD_COLUMNS[4], record.getTypeName());
        values.put(RECORD_COLUMNS[5], record.getStageNameID());
        values.put(RECORD_COLUMNS[6], record.getStageName());
        values.put(RECORD_COLUMNS[7], record.getDistanceID());
        values.put(RECORD_COLUMNS[8], record.getDistance());
        values.put(RECORD_COLUMNS[9], record.getTargetTimeID());
        values.put(RECORD_COLUMNS[10], record.getTargetTime());
        values.put(RECORD_COLUMNS[11], record.getCarDueID());
        values.put(RECORD_COLUMNS[12], record.getCarDue());
        values.put(RECORD_COLUMNS[13], record.getRowID());
        values.put(RECORD_COLUMNS[14], record.getDayCount());
        values.put(RECORD_COLUMNS[15], record.getStageTimeID());
        values.put(RECORD_COLUMNS[16], record.getStageTime());
        values.put(RECORD_COLUMNS[17], record.getPenaltyTimeID());
        values.put(RECORD_COLUMNS[18], record.getPenaltyTime());

        tableName = "'"+tableName+"'";
        // 3. updating row
        int i = db.update(tableName, //table
        		values, // column/value
        		"Row_No"+" = ?", // selections
                new String[] { String.valueOf(record.getRowNo()) }); //selection args

        // 4. close
        db.close();

    }
*/


    public void onCreate(SQLiteDatabase db) {
        //Log.i("DBTRY","DBHelper onCreate");


    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
       // db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public static ContentValues cursorRowToContentValues(Cursor cursor) {
        ContentValues values = new ContentValues();
        String[] columns = cursor.getColumnNames();
        int length = columns.length;
        for (int i = 0; i < length; i++) {
            switch (cursor.getType(i)) {
                case Cursor.FIELD_TYPE_NULL:
                    values.putNull(columns[i]);
                    break;
                case Cursor.FIELD_TYPE_INTEGER:
                    values.put(columns[i], cursor.getInt(i));
                    break;
                case Cursor.FIELD_TYPE_FLOAT:
                    values.put(columns[i], cursor.getDouble(i));
                    break;
                case Cursor.FIELD_TYPE_STRING:
                    values.put(columns[i], cursor.getString(i));
                    break;
                case Cursor.FIELD_TYPE_BLOB:
                    values.put(columns[i], cursor.getBlob(i));
                    break;
            }
        }
        return values;
    }


}

/*

                case 0://Cursor.FIELD_TYPE_NULL
                    values.putNull(columns[i]);
                    break;
                case 1://Cursor.FIELD_TYPE_INTEGER
                    values.put(columns[i], cursor.getInt(i));
                    break;
                case 2://Cursor.FIELD_TYPE_FLOAT
                    values.put(columns[i], cursor.getDouble(i));
                    break;
                case 3://Cursor.FIELD_TYPE_STRING
                    values.put(columns[i], cursor.getString(i));
                    break;
                case 4://Cursor.FIELD_TYPE_BLOB
                    values.put(columns[i], cursor.getBlob(i));
                    break;
            }


 */