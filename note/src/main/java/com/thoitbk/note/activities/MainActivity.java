package com.thoitbk.note.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.thoitbk.note.R;
import com.thoitbk.note.adapters.CustomCursorAdapter;
import com.thoitbk.note.contentproviders.NoteContentProvider;
import com.thoitbk.note.db.Note;
import com.thoitbk.note.fragments.NoteDialogFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>, ActionMode.Callback {

    private Toolbar toolbar;
    private ListView noteListView;

    private CustomCursorAdapter adapter;

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
        loadData();
        noteListView.setOnItemLongClickListener(new NoteListViewLongClickListener());
    }

    private ActionMode mActionMode;
    private List<Integer> selectedItems = new ArrayList<Integer>();

    private class NoteListViewLongClickListener implements AdapterView.OnItemLongClickListener {

        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
            if (mActionMode == null) {
                mActionMode = MainActivity.this.startSupportActionMode(MainActivity.this);
                loadData();
            }
            if (selectedItems.contains(position)) {
                toggleSelectedItem(position, false);
                selectedItems.remove(new Integer(position));

            } else {
                toggleSelectedItem(position, true);
                selectedItems.add(position);
            }
            if (mActionMode != null && selectedItems.isEmpty()) {
                mActionMode.finish();
                mActionMode = null;
                return true;
            }

            return true;
        }
    }

    private void toggleSelectedItem(int pos, boolean checked) {
        int colorResource = checked ? R.color.selectedColor : android.R.color.transparent;
        View view = getViewByPosition(pos, noteListView);
        if (view != null) {
            view.setBackgroundResource(colorResource);

        }
    }

    private View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            return listView.getAdapter().getView(pos, null, listView);
        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
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

    private void loadData() {
        String from[] = {Note.COLUMN_TITLE, Note.COLUMN_CONTENT};
        int to[] = {R.id.row_title, R.id.row_content};
        int layout = mActionMode == null ? R.layout.note_row : R.layout.selected_note_row;
        adapter = new CustomCursorAdapter(this, layout, null, from, to , 0, false);
        getSupportLoaderManager().initLoader(0, null, this);
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

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.main_cab_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_note:
                mode.finish();
                return true;
            default:
                mode.finish();
                return true;
        }
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        mode.finish();
        mActionMode = null;
        loadData();
        for (Integer selectedItem : selectedItems) {
            getViewByPosition(selectedItem, noteListView).setBackgroundResource(android.R.color.transparent);
        }
        selectedItems.clear();
    }
}
