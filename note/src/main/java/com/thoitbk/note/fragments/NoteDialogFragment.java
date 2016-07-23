package com.thoitbk.note.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.thoitbk.note.R;

public class NoteDialogFragment extends DialogFragment implements View.OnClickListener {

    private Button addButton;
    private Button cancelButton;

    public NoteDialogFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_dialog, container);
        getDialog().setTitle(R.string.note_dialog_title);

        addButton = (Button) view.findViewById(R.id.add);
        cancelButton = (Button) view.findViewById(R.id.cancel);

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.add:
                Toast.makeText(this.getActivity(), "add", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
