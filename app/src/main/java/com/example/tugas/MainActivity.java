package com.example.tugas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private int taskNumber = 1;
    private EditText editTextTask;
    private Button buttonAdd;
    private ListView listViewTasks;
    private TaskAdapter tasksAdapter;
    private ArrayList<String> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewTasks = findViewById(R.id.listViewTasks);
        Button buttonDeleteAll = findViewById(R.id.buttonDeleteAll);


        tasks = new ArrayList<>();
        tasksAdapter = new TaskAdapter(this, tasks);
        listViewTasks.setAdapter(tasksAdapter);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String task = editTextTask.getText().toString().trim();
                if (!task.isEmpty()) {
                    String numberedTask;
                    if (tasks.isEmpty()) {
                        numberedTask = "1. " + task; // Jika tidak ada tugas sebelumnya, nomor tugas dimulai dari 1
                        taskNumber = 2; // Mengatur nomor untuk tugas berikutnya
                    } else {
                        int lastIndex = tasks.size() - 1; // Indeks terakhir dari daftar tugas
                        String lastTask = tasks.get(lastIndex); // Tugas terakhir
                        int lastNumber = Integer.parseInt(lastTask.split("\\.")[0]); // Nomor tugas terakhir
                        numberedTask = (lastNumber + 1) + ". " + task;
                        taskNumber = lastNumber + 2; // Mengatur nomor untuk tugas berikutnya
                    }

                    tasks.add(numberedTask);
                    tasksAdapter.notifyDataSetChanged();
                    editTextTask.setText("");
                }
            }
        });


        // Tambahkan OnClickListener pada tombol "Hapus Semua"
        buttonDeleteAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.clear(); // Menghapus semua tugas dari daftar
                tasksAdapter.notifyDataSetChanged(); // Memperbarui tampilan ListView

                // Mengatur ulang nomor tugas ke 1
                taskNumber = 1;
            }
        });


    }
}

