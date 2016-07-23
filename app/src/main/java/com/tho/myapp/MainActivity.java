package com.tho.myapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

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
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(onItemClickListener);
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String item = (String) adapterView.getItemAtPosition(i);
            Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
            osNames.set(i, item + " clicked");
            adapter.notifyDataSetChanged();
        }
    };
}
