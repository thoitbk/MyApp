package com.thoitbk.note.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.thoitbk.note.R;
import com.thoitbk.note.contentproviders.NoteContentProvider;
import com.thoitbk.note.db.Note;
import com.thoitbk.note.fragments.NoteDialogFragment;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private Toolbar toolbar;
    private ListView noteListView;

    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        noteListView = (ListView) findViewById(R.id.noteList);
        fillData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int selectedItem = item.getItemId();
        switch (selectedItem) {
            case R.id.add_note:
                Toast.makeText(this, "add note", Toast.LENGTH_LONG).show();
                showNoteDialog();
                break;
            case R.id.setting:
                Toast.makeText(this, "setting", Toast.LENGTH_LONG).show();
                break;
            default:
                break;
        }

        return true;
    }

    private void showNoteDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag("noteDialog");
        if (fragment != null) {
            fragmentManager.beginTransaction().remove(fragment).commit();
        }
        NoteDialogFragment noteDialogFragment = new NoteDialogFragment();
        noteDialogFragment.show(fragmentManager, "noteDialog");
    }

    private void fillData() {
        String from[] = {Note.COLUMN_TITLE, Note.COLUMN_CONTENT};
        int to[] = {R.id.row_title, R.id.row_content};
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
        adapter = new SimpleCursorAdapter(this, R.layout.note_row, null, from, to, 0);
        noteListView.setAdapter(adapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {Note.COLUMN_ID, Note.COLUMN_TITLE, Note.COLUMN_CONTENT};
        CursorLoader cursorLoader = new CursorLoader(this, NoteContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
