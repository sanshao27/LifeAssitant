package com.ydscience.lifeassistant.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ydscience on 2017/9/2.
 */

public class WeatherInfoProvider extends ContentProvider {

    // Uri的authority
    public static final String AUTHORITY = "com.ydscience.lifeassistant.db.WeatherInfoProvider";
    // Uri的path
    public static final String PATH_ONE = "curtoday";
    public static final String PATH_TWO = "moreinfo";
    // UriMatcher中URI对应的序号
    public static final int ITEM_ONE = 1;
    public static final int ITEM_TWO = 2;
    private static final UriMatcher URI_MATCHER;
    static {
        URI_MATCHER  = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(AUTHORITY, PATH_ONE, ITEM_ONE);
        URI_MATCHER.addURI(AUTHORITY, PATH_TWO, ITEM_TWO);
    }
    private SQLiteDatabase mSQLiteDatabase;

    @Override
    public boolean onCreate() {
        mSQLiteDatabase = new DbHelper(getContext()).getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        String tableName = getTbaleName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("illegal uri"+uri);
        }
        return mSQLiteDatabase.query(tableName,projection,selection,selectionArgs,null,null,sortOrder,null);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
//        switch (URI_MATCHER.match(uri)){
//            case ITEM_ONE:
//                return AUTHORITY+"/"+PATH_ONE;
//            case ITEM_TWO:
//                return AUTHORITY+"/"+PATH_TWO;
//            default: throw new IllegalArgumentException("UnKnown uri"+uri);
//        }
        return  null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        String tableName = getTbaleName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("illegal uri"+uri);
        }
        mSQLiteDatabase.insert(tableName,null,values);
        getContext().getContentResolver().notifyChange(uri,null);
        return uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTbaleName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("illegal uri"+uri);
        }
        int result = mSQLiteDatabase.delete(tableName,selection,selectionArgs);
        if(result >0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return result;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        String tableName = getTbaleName(uri);
        if (tableName == null){
            throw new IllegalArgumentException("illegal uri"+uri);
        }
        int result = mSQLiteDatabase.update(tableName,values,selection,selectionArgs);
        if(result > 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return result;
    }

    private String getTbaleName(Uri uri){
        String tableName = "";
        switch (URI_MATCHER.match(uri)){
            case ITEM_ONE:
                tableName = DbHelper.Entry.TABLE_CUR_TODAY;
                break;
            case ITEM_TWO:
                tableName = DbHelper.Entry.TABLE_MORE_INFO;
                break;
            default: throw new IllegalArgumentException("illegal uri"+uri);
        }
        return tableName;
    }
}
