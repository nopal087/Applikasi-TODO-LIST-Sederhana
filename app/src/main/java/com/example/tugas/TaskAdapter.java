package com.example.tugas;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<String> {

    private int taskNumber = 1;
    private ArrayList<String> tasks;

    public TaskAdapter(Context context, ArrayList<String> tasks) {
        super(context, 0, tasks);
        this.tasks = tasks;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }

        final TextView textViewTask = convertView.findViewById(R.id.textViewTask);
        final EditText editTextEditTask = convertView.findViewById(R.id.editTextEditTask);
        Button buttonEdit = convertView.findViewById(R.id.buttonEdit);
        Button buttonDelete = convertView.findViewById(R.id.buttonDelete);

        final String task = getItem(position);
        textViewTask.setText(task);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String updatedTask = editTextEditTask.getText().toString().trim();
                if (!updatedTask.isEmpty()) {
                    String taskNumber = tasks.get(position).split("\\.")[0]; // Mengambil nomor urutan tugas yang ada
                    String numberedTask = taskNumber + ". " + updatedTask;
                    tasks.set(position, numberedTask);
                    notifyDataSetChanged();

                    // Menghapus isi teks di dalam EditText
                    editTextEditTask.setText("");
                }
            }
        });



        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tasks.remove(position);
               notifyDataSetChanged();

                // Mengecek apakah semua tugas telah dihapus
                if (tasks.isEmpty()) {
                    taskNumber = 1; // Mengatur nomor kembali ke 1
                } else {
                    // Mengatur ulang nomor tugas berdasarkan posisi setelah penghapusan
                    for (int i = position; i < tasks.size(); i++) {
                        String task = tasks.get(i);
                        int taskNumber = i + 1;
                        String updatedTask = taskNumber + ". " + task.substring(task.indexOf(".") + 2); // Mengupdate nomor tugas
                        tasks.set(i, updatedTask);
                    }
                }
            }
        });



        return convertView;
    }
}
