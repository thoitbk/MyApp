package com.thoitbk.note.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.thoitbk.note.R;
import com.thoitbk.note.db.Note;

import java.util.ArrayList;
import java.util.List;

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

    private List<Integer> checkPositions = new ArrayList<Integer>();

//    @Override
//    public View newView(Context context, Cursor cursor, ViewGroup parent) {
//        View view = inflater.inflate(layout, null);
//        final ViewHolder viewHolder = new ViewHolder();
//        viewHolder.title = (TextView) view.findViewById(R.id.row_title);
//        viewHolder.content = (TextView) view.findViewById(R.id.row_content);
//        if (layout == R.layout.selected_note_row) {
//            viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
//            viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                    Integer pos = (Integer) viewHolder.checkBox.getTag();
//                    Log.e("tag", "tag " + String.valueOf(pos));
//                    if (pos != null) {
//                        if (compoundButton.isChecked()) {
//                            if (!checkPositions.contains(pos)) {
//                                checkPositions.add(pos);
//                            }
//                        } else {
//                            if (checkPositions.contains(pos)) {
//                                checkPositions.remove(pos);
//                            }
//                        }
//                    }
//                }
//            });
//            viewHolder.checkBox.setTag(cursor.getPosition());
//            Log.e("pos", "cursor.getPosition() = " + cursor.getPosition());
//        }
//        view.setTag(viewHolder);
//        return view;
//    }

//    @Override
//    public void bindView(View view, Context context, Cursor cursor) {
//        super.bindView(view, context, cursor);
//        int titleIndex = cursor.getColumnIndexOrThrow(Note.COLUMN_TITLE);
//        int contentIndex = cursor.getColumnIndexOrThrow(Note.COLUMN_CONTENT);
//        ViewHolder viewHolder = (ViewHolder) view.getTag();
//        viewHolder.title.setText(cursor.getString(titleIndex));
//        viewHolder.content.setText(cursor.getString(contentIndex));
//        if (layout == R.layout.selected_note_row) {
//            viewHolder.checkBox.setChecked(checkPositions.contains(cursor.getPosition()));
//        }
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) view.findViewById(R.id.row_title);
            viewHolder.content = (TextView) view.findViewById(R.id.row_content);
            if (layout == R.layout.selected_note_row) {
                viewHolder.checkBox = (CheckBox) view.findViewById(R.id.checkbox);
                viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        Integer pos = (Integer) viewHolder.checkBox.getTag();
                        if (pos != null) {
                            if (compoundButton.isChecked()) {
                                if (!checkPositions.contains(pos)) {
                                    checkPositions.add(pos);
                                    ((View) compoundButton.getParent()).setBackgroundResource(R.color.selectedColor);
                                }
                            } else {
                                if (checkPositions.contains(pos)) {
                                    checkPositions.remove(pos);
                                    ((View) compoundButton.getParent()).setBackgroundResource(android.R.color.transparent);
                                }
                            }
                        }
                    }
                });
                viewHolder.checkBox.setTag(position);
            }
            view.setTag(viewHolder);
        } else {
            if (layout == R.layout.selected_note_row) {
                ((ViewHolder) view.getTag()).checkBox.setTag(position);
            }
        }

        Cursor cursor = this.getCursor();
        cursor.moveToPosition(position);

        int titleIndex = cursor.getColumnIndexOrThrow(Note.COLUMN_TITLE);
        int contentIndex = cursor.getColumnIndexOrThrow(Note.COLUMN_CONTENT);

        ViewHolder viewHolder = (ViewHolder) view.getTag();
        viewHolder.title.setText(cursor.getString(titleIndex));
        viewHolder.content.setText(cursor.getString(contentIndex));
        if (layout == R.layout.selected_note_row) {
            boolean checked = checkPositions.contains(position);
            viewHolder.checkBox.setChecked(checked);
            int colorResource = checked ? R.color.selectedColor : android.R.color.transparent;
            view.setBackgroundResource(colorResource);
        }

        return view;
    }

    public static class ViewHolder {
        public TextView title;
        public TextView content;
        public CheckBox checkBox;
    }
}
