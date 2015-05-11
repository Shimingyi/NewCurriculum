package cn.edu.sdu.online.database;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import cn.edu.sdu.online.modal.MessionMessageItem;

public class MessionDBHelper extends SQLiteOpenHelper {
    private final static String DATABASE_NAME = "daka.db";
    private final static String TABLE_NAME = "task_table";
    private final static int DATABASE_VERSION = 1;

    private final static String TASK_ID = "task_id";
    private final static String TASK_CONTENT = "task_content";
    private final static String TASK_TIME = "task_time";
    private final static String Task_STATUS = "task_status";

    SQLiteDatabase db = getWritableDatabase();

    public MessionDBHelper(Context context, String name, CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
    }
    public MessionDBHelper(Context context) {
        this(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        String sql = "Create table " + TABLE_NAME + "(" + TASK_ID
                + " TEXT primary key, " + TASK_CONTENT + " TEXT," + TASK_TIME
                + " INTEGER, " + Task_STATUS + " integer DEFAULT '0');";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.db = db;
        String sql = "Create table " + TABLE_NAME + "(" + TASK_ID
                + " TEXT primary key, " + TASK_CONTENT + " TEXT," + TASK_TIME
                + " INTEGER, " + Task_STATUS + " integer DEFAULT '0');";
        db.execSQL(sql);
    }
    public int getSize() {
        db = getReadableDatabase();
        Cursor c = db.rawQuery("select count(*) from " + TABLE_NAME, null);
        c.moveToFirst();
        int size = c.getInt(0);
        c.close();
        db.close();
        return size;
    }
    public void insert(String uuid, String content, int time) {
        db = getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(TASK_ID, uuid);
        cv.put(TASK_CONTENT, content);
        cv.put(TASK_TIME, time);

        db.insert(TABLE_NAME, null, cv);
        db.close();
    }
    public void delete(String uuid) {
        db = getWritableDatabase();
        String sql = "DELETE FROM " + TABLE_NAME + " where task_id = " + "'"
                + uuid + "'";
        db.execSQL(sql);
        db.close();
    }
    public void updateStatus(String uuid,int status) {
        db = getWritableDatabase();
        String where = TASK_ID + " = ?";
        String[] whereValue = {uuid};

        ContentValues cv = new ContentValues();
        cv.put(Task_STATUS, status);
        db.update(TABLE_NAME, cv, where, whereValue);
        db.close();
    }
    public void changeItem(String uuid, String content) {
        db = getWritableDatabase();
        String where = TASK_ID + " = ?";
        String[] whereValue = {uuid};

        ContentValues cv = new ContentValues();
        cv.put(TASK_CONTENT, content);
        db.update(TABLE_NAME, cv, where, whereValue);
        db.close();

    }

    public ArrayList<MessionMessageItem> getList() {
        ArrayList<MessionMessageItem> lists = new ArrayList<MessionMessageItem>();
        db = getWritableDatabase();

        String sql = "SELECT * from " + TABLE_NAME;
        Cursor cursor = db.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            MessionMessageItem task = new MessionMessageItem();
            task.setUuid(cursor.getString(0));
            task.setMsg(cursor.getString(1));
            task.setCreateTime(cursor.getInt(2));
            task.setStatus(cursor.getInt(3));
            lists.add(task);
        }
        db.close();
        return lists;
    }

}
