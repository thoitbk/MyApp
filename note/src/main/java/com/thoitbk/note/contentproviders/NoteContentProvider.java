package com.thoitbk.note.contentproviders;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.thoitbk.note.db.DbHelper;
import com.thoitbk.note.db.Note;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NoteContentProvider extends ContentProvider {

    private DbHelper dbHelper;

    private static final int NOTES = 1;
    private static final int NOTE_ID = 2;

    private static final String AUTHORITY = "com.thoitbk.note.contentproviders";
    private static final String BASE_PATH = "notes";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/notes";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "note";

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, BASE_PATH, NOTES);
        uriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", NOTE_ID);
    }

    @Override
    public boolean onCreate() {
        dbHelper = new DbHelper(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        checkColumns(projection);
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(Note.TABLE_NOTE);
        int uriType = uriMatcher.match(uri);
        switch (uriType) {
            case NOTES:
                break;
            case NOTE_ID:
                builder.appendWhere(Note.COLUMN_ID + "=" + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("");
        }

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        long id = 0;

        switch (uriType) {
            case NOTES:
                id = database.insert(Note.TABLE_NOTE, null, values);
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        int rowDeleted = 0;

        switch (uriType) {
            case NOTES:
                rowDeleted = database.delete(Note.TABLE_NOTE, selection, selectionArgs);
                break;
            case NOTE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowDeleted = database.delete(Note.TABLE_NOTE, Note.COLUMN_ID + "=?", new String[] {id});
                } else {
                    rowDeleted = database.delete(Note.TABLE_NOTE, Note.COLUMN_ID + "=" + id + " AND " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int uriType = uriMatcher.match(uri);
        int rowUpdated = 0;
        SQLiteDatabase database = dbHelper.getWritableDatabase();

        switch (uriType) {
            case NOTES:
                database.update(Note.TABLE_NOTE, values, selection, selectionArgs);
                break;
            case NOTE_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    database.update(Note.TABLE_NOTE, values, Note.COLUMN_ID + "=?", new String[] {id});
                } else {
                    database.update(Note.TABLE_NOTE, values, Note.COLUMN_ID + "=" + id + " AND " + selection, selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);

        return rowUpdated;
    }

    private void checkColumns(String[] projection) {
        String[] available = {Note.COLUMN_ID, Note.COLUMN_TITLE, Note.COLUMN_CONTENT};
        if (projection != null) {
            Set<String> projectionColumns = new HashSet<String>(Arrays.asList(projection));
            Set<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            if (!availableColumns.containsAll(projectionColumns)) {
                throw new IllegalArgumentException("Unknown column");
            }
        }
    }
}
