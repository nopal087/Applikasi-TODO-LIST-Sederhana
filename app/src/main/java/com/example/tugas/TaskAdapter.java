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
            // Mengecek apakah view sudah ada. Jika belum, inflate layout untuk setiap item tugas.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_task, parent, false);
        }

        // Mendapatkan referensi ke elemen-elemen UI pada layout item_task.
        final TextView textViewTask = convertView.findViewById(R.id.textViewTask);
        final EditText editTextEditTask = convertView.findViewById(R.id.editTextEditTask);
        Button buttonEdit = convertView.findViewById(R.id.buttonEdit);
        Button buttonDelete = convertView.findViewById(R.id.buttonDelete);

        // Mendapatkan tugas pada posisi tertentu dari ArrayList tasks.
        final String task = getItem(position);

        // Menampilkan teks tugas pada TextView.
        textViewTask.setText(task);

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Mengambil tugas yang telah diubah dari EditText.
                String updatedTask = editTextEditTask.getText().toString().trim();
                if (!updatedTask.isEmpty()) {
                    // Mengambil nomor urutan tugas yang ada.
                    String taskNumber = tasks.get(position).split("\\.")[0];
                    // Menggabungkan nomor urutan tugas dengan tugas yang telah diubah.
                    String numberedTask = taskNumber + ". " + updatedTask;
                    // Mengganti tugas pada posisi tertentu dalam ArrayList tasks.
                    tasks.set(position, numberedTask);
                    // Memberi tahu adapter bahwa data telah berubah.
                    notifyDataSetChanged();

                    // Menghapus isi teks di dalam EditText setelah tugas diperbarui.
                    editTextEditTask.setText("");
                }
            }
        });

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menghapus tugas dari ArrayList tasks.
                tasks.remove(position);
                // Memberi tahu adapter bahwa data telah berubah.
                notifyDataSetChanged();

                // Mengecek apakah semua tugas telah dihapus.
                if (tasks.isEmpty()) {
                    // Mengatur nomor tugas kembali ke 1 jika tidak ada tugas lagi.
                    taskNumber = 1;
                } else {
                    // Mengatur ulang nomor tugas berdasarkan posisi setelah penghapusan.
                    for (int i = position; i < tasks.size(); i++) {
                        String task = tasks.get(i);
                        int taskNumber = i + 1;
                        // Mengupdate nomor urutan tugas.
                        String updatedTask = taskNumber + ". " + task.substring(task.indexOf(".") + 2);
                        tasks.set(i, updatedTask);
                    }
                }
            }
        });

        return convertView;
    }
}

