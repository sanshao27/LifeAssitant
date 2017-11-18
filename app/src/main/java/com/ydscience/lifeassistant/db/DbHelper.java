package com.ydscience.lifeassistant.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


/**
 * Created by ydscience on 2017/9/2.
 */

public class DbHelper  extends SQLiteOpenHelper {

    public static abstract class Entry implements BaseColumns{
        public static final String TABLE_CUR_TODAY = "curtoday";
        public static final String TABLE_MORE_INFO = "moreinfo";
        public static final String DB_NAME = "weatherinfo.db";
        public static final String CITY = "city";
        public static final String AQI = "aqi";
        public static final String WENDU = "wendu";
        public static final String DATE = "date";
        public static final String HIGH = "high";
        public static final String LOW = "low";
        public static final String FENGLI = "fengli";
        public static final String FENGXIANG = "fengxiang";
        public static final String TYPE = "type";
    }

    public DbHelper(Context context) {
        super(context,Entry.DB_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCurToadyTable = "CREATE TABLE IF NOT EXISTS "+Entry.TABLE_CUR_TODAY+"("+Entry.CITY
                +" TEXT PRIMARY KEY,"+Entry.AQI+" TEXT ,"+Entry.WENDU+" TEXT ,"+Entry.DATE +" TEXT ,"
                +Entry.HIGH+" TEXT ,"+Entry.LOW+" TEXT ,"+Entry.FENGLI+" TEXT ,"+Entry.FENGXIANG+" TEXT ,"
                +Entry.TYPE+ " TEXT)";
        String sqlMoreInfoTable = "CREATE TABLE IF NOT EXISTS "+Entry.TABLE_MORE_INFO+"("+Entry.DATE +" TEXT ,"
                +Entry.HIGH+" TEXT ,"+Entry.LOW+" TEXT ,"+Entry.FENGLI+" TEXT ,"+Entry.FENGXIANG+" TEXT ,"
                +Entry.TYPE+ " TEXT)";
        db.execSQL(sqlCurToadyTable);
        db.execSQL(sqlMoreInfoTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
