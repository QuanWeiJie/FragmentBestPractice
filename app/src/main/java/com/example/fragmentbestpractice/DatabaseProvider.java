package com.example.fragmentbestpractice;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.widget.Switch;

public class DatabaseProvider extends ContentProvider {
    public static final int Book_DIR  =0;
    public static final String AUTHORITY ="com.example.fragmentbestpractice.provider";
    public static UriMatcher uriMatcher;
    private MyDatabaseHelper dbHelper;
    public DatabaseProvider() {
    }
 static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY,"Vocabulary",Book_DIR);
 }
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        switch (uriMatcher.match(uri)){
            case Book_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.fragmentbestpractice.provider.Vocabulary";
        }
       return null;
        // throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext(),"Vocabulary_Book.db",null,1);
        return true;
        // TODO: Implement this to initialize your content provider on startup.
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        //查询数据
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        switch(uriMatcher.match(uri)){
            case Book_DIR:
             cursor = db.query("Vocabulary",projection,selection,selectionArgs,null,null,sortOrder);
             break;
            default:
                break;
        }return cursor;
        // TODO: Implement this to handle query requests from clients.
        //throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
