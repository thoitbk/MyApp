package com.thoitbk.note.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class NoteDAO {

    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public NoteDAO(Context context) {
        dbHelper = new DbHelper(context);
    }

    public void open() {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void createNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_TITLE, note.getTitle());
        values.put(Note.COLUMN_CONTENT, note.getContent());
        database.insert(Note.TABLE_NOTE, null, values);
    }

    public void deleteNote(long id) {
        database.delete(Note.TABLE_NOTE, Note.COLUMN_ID + " = ?", new String[] {String.valueOf(id)});
    }

    public void updateNote(Note note) {
        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_TITLE, note.getTitle());
        values.put(Note.COLUMN_CONTENT, note.getContent());
        database.update(Note.TABLE_NOTE, values, Note.COLUMN_ID + " = ?", new String[] {String.valueOf(note.getId())});
    }
}
