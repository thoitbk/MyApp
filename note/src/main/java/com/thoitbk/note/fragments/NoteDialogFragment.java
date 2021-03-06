package com.thoitbk.note.fragments;

import android.content.ContentValues;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.thoitbk.note.R;
import com.thoitbk.note.contentproviders.NoteContentProvider;
import com.thoitbk.note.db.Note;

public class NoteDialogFragment extends DialogFragment implements View.OnClickListener {

    private Button addButton;
    private Button cancelButton;

    private EditText titleEditText;
    private EditText contentEditText;

    public NoteDialogFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_dialog, container);
        getDialog().setTitle(R.string.note_dialog_title);

        addButton = (Button) view.findViewById(R.id.add);
        cancelButton = (Button) view.findViewById(R.id.cancel);
        titleEditText = (EditText) view.findViewById(R.id.title);
        contentEditText = (EditText) view.findViewById(R.id.content);

        addButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add:
                String title = titleEditText.getText().toString();
                String content = contentEditText.getText().toString();
                SaveNoteTask saveNoteTask = new SaveNoteTask();
                saveNoteTask.execute(new Note(title, content));
                break;
            case R.id.cancel:
                this.dismiss();
                break;
            default:
                this.dismiss();
        }
    }

    public class SaveNoteTask extends AsyncTask<Note, Void, Void> {

        @Override
        protected Void doInBackground(Note... params) {
            ContentValues values = new ContentValues();
            values.put(Note.COLUMN_TITLE, params[0].getTitle());
            values.put(Note.COLUMN_CONTENT, params[0].getContent());
            NoteDialogFragment.this.getActivity().getContentResolver().insert(NoteContentProvider.CONTENT_URI, values);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(NoteDialogFragment.this.getActivity(), "add note successfully", Toast.LENGTH_SHORT).show();
            NoteDialogFragment.this.dismiss();
        }
    }
}
