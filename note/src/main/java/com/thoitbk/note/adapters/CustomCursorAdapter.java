package com.thoitbk.note.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.thoitbk.note.R;
import com.thoitbk.note.db.Note;

public class CustomCursorAdapter extends SimpleCursorAdapter {

    private Context context;
    private int layout;
    private Cursor c;
    private String[] from;
    private int[] to;
    private final LayoutInflater inflater;
    private boolean isInActionMode;

    public CustomCursorAdapter(Context context, int layout, Cursor c, String[] from, int[] to,
                               int flags, boolean isInActionMode) {
        super(context, layout, c, from, to, flags);
        this.context = context;
        this.layout = layout;
        this.c = c;
        this.from = from;
        this.to = to;
        this.inflater = LayoutInflater.from(context);
    }

    public void setInActionMode(boolean isInActionMode) {
        this.isInActionMode = isInActionMode;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        Log.e("thoitbk", "newView...");
        View view = inflater.inflate(layout, null);
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.title = (TextView) view.findViewById(R.id.row_title);
        viewHolder.content = (TextView) view.findViewById(R.id.row_content);
        viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
        view.setTag(viewHolder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        super.bindView(view, context, cursor);
        int titleIndex = cursor.getColumnIndexOrThrow(Note.COLUMN_TITLE);
        int contentIndex = cursor.getColumnIndexOrThrow(Note.COLUMN_CONTENT);
        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewH
        Log.e("thoitbk", "bindView");
    }

    public static class ViewHolder {
        public TextView title;
        public TextView content;
        public CheckBox checkBox;
    }
}
