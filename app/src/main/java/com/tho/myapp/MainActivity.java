package com.tho.myapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AbsListView.MultiChoiceModeListener {

    private ListView listView;

    private ArrayAdapter<String> adapter;

    private List<String> osNames = new ArrayList<String>(Arrays.asList("Android", "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux"
            , "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux"
            , "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux", "Windows 7", "Linux"));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, osNames);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        listView.setAdapter(adapter);
        listView.setMultiChoiceModeListener(this);

//        listView.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
        //listView.setSelected(checked);
        Log.e("tho", String.valueOf(position) + " " + String.valueOf(checked));
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        MenuInflater inflater = mode.getMenuInflater();
        inflater.inflate(R.menu.main_cab_menu, menu);
        Log.e("tho", "sdjfhskjhf");
        return true;
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        return false;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        return false;
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {

    }

//    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
//
//        @Override
//        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//            String item = (String) adapterView.getItemAtPosition(i);
//            Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
//            osNames.set(i, item + " clicked");
//            adapter.notifyDataSetChanged();
//        }
//    };
}
