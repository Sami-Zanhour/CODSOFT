package com.example.todolist.activities;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.todolist.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ToDoActitivity extends AppCompatActivity implements onDialogCloseListner  {

    private RecyclerView recyclerView;
    private Button addTask;
    private ImageButton notification, search;
    private TextView title, tasks_title;
    private DataBaseHelper myDB;
    private List<ToDoModel> list;
    private ToDoAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_to_do_actitivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_view);
        addTask = findViewById(R.id.addTask);
        notification = findViewById(R.id.notification);
        search = findViewById(R.id.search);
        title = findViewById(R.id.title);
        tasks_title = findViewById(R.id.tasks_title);
        myDB = new DataBaseHelper(ToDoActitivity.this);
        list = new ArrayList<>();
        adapter = new ToDoAdapter(myDB, ToDoActitivity.this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        list = myDB.getAllTasks();
        Collections.reverse(list);
        adapter.setTasks(list);

        addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddNewTask.newInstance().show(getSupportFragmentManager(), AddNewTask.TAG);
            }
        });
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new RecyclerViewTouchHelper(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    public void onDialogClose(DialogInterface dialogInterface) {
        list = myDB.getAllTasks();
        Collections.reverse(list);
        adapter.setTasks(list);
        adapter.notifyDataSetChanged();
    }
}