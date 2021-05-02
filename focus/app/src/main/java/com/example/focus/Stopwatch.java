package com.example.focus;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Stopwatch extends AppCompatActivity implements TaskDialog.TaskDialogListener{

    private Chronometer chronometer;
    private long pauseOffset;
    private boolean running;
    String timeToked;
    String taskDone;
    //long elapsed;
    //TextView timeTokedtxtVi;
    //TextView taskdonetxtvi;
    Button intentBtn;
    Date date;
    SimpleDateFormat df;
    String formattedDate;

    ArrayList<String> tasksList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);

        loadData();

        //timeTokedtxtVi= findViewById(R.id.textView2);
        //taskdonetxtvi = findViewById(R.id.textView3);
        intentBtn = findViewById(R.id.showbtn);

        chronometer = findViewById(R.id.chronometer);
        chronometer.setFormat("Time: %s");
        chronometer.setBase(SystemClock.elapsedRealtime());

        intentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Stopwatch.this, TheTasks.class);
                intent.putExtra("list", tasksList);
                startActivity(intent);
            }
        });
    }

    public void startChronometer(View v) {
        if (!running) {
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffset);
            chronometer.start();
            running = true;
        }
    }
    public void pauseChronometer(View v) {
        if (running) {
            chronometer.stop();
            pauseOffset = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
            //elapsed = (int) (SystemClock.elapsedRealtime()-chronometer.getBase());
            //timeToked=(String.valueOf(elapsed));
            //elapsed = SystemClock.elapsedRealtime()-chronometer.getBase();
            timeToked=chronometer.getText().toString();
            //Toast.makeText(Stopwatch.this, "time:"+timeToked,Toast.LENGTH_LONG).show();
            //
            //startActivity(new Intent(Stopwatch.this,Pop.class));
            openDialog();
            //timeTokedtxtVi.setText(timeToked);
            date = Calendar.getInstance().getTime();
            df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            formattedDate = df.format(date);

        }
    }
    public void resetChronometer(View v) {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffset = 0;
    }

    public void openDialog(){
        TaskDialog taskDialog = new TaskDialog();
        taskDialog.show(getSupportFragmentManager(), "task dialog");
    }

    @Override
    public void applyTexts(String task) {
        taskDone=(task);
        //taskdonetxtvi.setText(taskDone);
        tasksList.add(timeToked+ "     "+formattedDate+ "     " + taskDone);
        saveData();
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tasksList);
        editor.putString("task list", json);
        editor.apply();
    }
    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("task list", null);
        Type type = new TypeToken<ArrayList<String>>() {}.getType();
        tasksList = gson.fromJson(json, type);
        if (tasksList == null) {
            tasksList = new ArrayList<>();
        }
    }
}