package com.example.focus;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class TheTasks extends AppCompatActivity {

    MyAdapter adapter;
    ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_tasks);

        arrayList = (ArrayList<String>) getIntent().getSerializableExtra("list");
        RecyclerView recyclerView = findViewById(R.id.recyclerview);

        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);
        adapter = new MyAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

}