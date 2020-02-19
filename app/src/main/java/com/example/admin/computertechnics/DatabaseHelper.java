package com.example.admin.computertechnics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.sql.Types.TINYINT;

/**
 * Created by admin on 5/1/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Clients.db";
    public static final String TABLE_NAME = "client_table";
    public static final int DATABASE_VERSION = 1;

    public static final String ID_COLUMN = "id";
    public static final String LAST_NAME_COLUMN = "last_name";
    public static final String FIRST_NAME_COLUMN = "first_name";
    public static final String ADDRESS_COLUMN = "address";
    public static final String CITY_COLUMN = "city";
    public static final String PHONE_COLUMN = "phone";
    public static final String PHONE2_COLUMN = "phone2";
    public static final String DEVICE_COLUMN = "device";
    public static final String SERIAL_NUMBER_COLUMN = "serial_number";
    public static final String BAG_COLUMN = "bag";
    public static final String POWER_SUPPLY_COLUMN = "power_supply";
    public static final String BATTERY_COLUMN = "battery";
    public static final String COMMENTS_COLUMN = "comments";
    public static final String ENTRY_DATE_COLUMN = "entry";
    public static final String MESSAGE_DATE_COLUMN = "message_date";
    public static final String FINISHED_COLUMN = "finished";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "+TABLE_NAME+
            "("+ ID_COLUMN+ " TEXT UNIQUE,"+ LAST_NAME_COLUMN+ " TEXT,"+ FIRST_NAME_COLUMN+ " TEXT,"+
            ADDRESS_COLUMN+ " TEXT,"+ CITY_COLUMN+ " TEXT,"+ PHONE_COLUMN+ " TEXT,"+ PHONE2_COLUMN+ " TEXT,"+
            DEVICE_COLUMN+ " TEXT,"+ SERIAL_NUMBER_COLUMN+ " TEXT,"+ BAG_COLUMN+ " CHAR(1) DEFAULT 'N',"+
            POWER_SUPPLY_COLUMN+ " CHAR(1) DEFAULT 'N',"+ BATTERY_COLUMN+ " CHAR(1) DEFAULT 'N',"+ COMMENTS_COLUMN+ " TEXT,"+
            ENTRY_DATE_COLUMN+ " TEXT,"+ MESSAGE_DATE_COLUMN+ " TEXT,"+ FINISHED_COLUMN+ " TINYINT);";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "+TABLE_NAME;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }

    //Method to add user input to database
    public void addInfo(String id, String lastName, String firstName, String address, String city,
                        String phone, String phone2, String device, String serialNumber,
                        String bag, String powerSupply, String battery, String comments,
                        String entryDate, String messageDate, int finished) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID_COLUMN, id);
        values.put(LAST_NAME_COLUMN, lastName);
        values.put(FIRST_NAME_COLUMN, firstName);
        values.put(ADDRESS_COLUMN, address);
        values.put(CITY_COLUMN, city);
        values.put(PHONE_COLUMN, phone);
        values.put(PHONE2_COLUMN, phone2);
        values.put(DEVICE_COLUMN, device);
        values.put(SERIAL_NUMBER_COLUMN, serialNumber);
        values.put(BAG_COLUMN, bag);
        values.put(POWER_SUPPLY_COLUMN, powerSupply);
        values.put(BATTERY_COLUMN, battery);
        values.put(COMMENTS_COLUMN, comments);
        values.put(ENTRY_DATE_COLUMN, entryDate);
        values.put(MESSAGE_DATE_COLUMN, messageDate);
        values.put(FINISHED_COLUMN, finished);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //Method to retrieve all rows from database
    public Cursor getInfo(SQLiteDatabase db) {
        String[] projections = {ID_COLUMN, LAST_NAME_COLUMN, FIRST_NAME_COLUMN, ADDRESS_COLUMN,
                CITY_COLUMN, PHONE_COLUMN, PHONE2_COLUMN, DEVICE_COLUMN, SERIAL_NUMBER_COLUMN,
                BAG_COLUMN, POWER_SUPPLY_COLUMN, BATTERY_COLUMN, COMMENTS_COLUMN,
                ENTRY_DATE_COLUMN, MESSAGE_DATE_COLUMN, FINISHED_COLUMN};
        Cursor cursor = db.query(TABLE_NAME, projections, null, null, null, null, null);
        return cursor;
    }

    //Method to retrieve single row from database by clientId or clientLastName
    public Cursor getClientInfo(SQLiteDatabase db, String query) {
        String[] projections = {ID_COLUMN, LAST_NAME_COLUMN, FIRST_NAME_COLUMN, ADDRESS_COLUMN,
                CITY_COLUMN, PHONE_COLUMN, PHONE2_COLUMN, DEVICE_COLUMN, SERIAL_NUMBER_COLUMN,
                BAG_COLUMN, POWER_SUPPLY_COLUMN, BATTERY_COLUMN, COMMENTS_COLUMN,
                ENTRY_DATE_COLUMN, MESSAGE_DATE_COLUMN, FINISHED_COLUMN};
        String whereClause = "id =? OR last_name =?";
        String[] whereArgs = new String[]{query, query};
        Cursor cursor = db.query(TABLE_NAME, projections, whereClause, whereArgs, null, null, null);
        return cursor;
    }

    //Method to delete single row from database by clientId
    public void deleteClient(String clientId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME + " WHERE " + ID_COLUMN + "= '" + clientId + "'");
        db.close();
    }

    //Method to update FINISHED_COLUMN value by clientId
    public void updateStatus(String clientId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + FINISHED_COLUMN + "= 1 WHERE " + ID_COLUMN + "= '" + clientId + "'");
        db.close();
    }

    //Method to update MESSAGE_DATE_COLUMN value
    public void addMessageDate(String clientId) {
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String messageDate = sdf.format(today);

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET " + MESSAGE_DATE_COLUMN + "= '" + messageDate + "' WHERE " + ID_COLUMN + "= '" + clientId + "'");
        db.close();
    }
}
