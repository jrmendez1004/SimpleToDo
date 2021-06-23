package com.example.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
//import android.os.FileUtils;
import org.apache.commons.io.FileUtils;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button button;
    EditText item;
    RecyclerView list;
    listAdapter ListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        item = findViewById(R.id.item);
        list = findViewById(R.id.list);

        loadItems();

        listAdapter.onLongClickListener onLongClickListener = new listAdapter.onLongClickListener() {
            @Override
            public void onItemLongClick(int position) {
                items.remove(position); //removes item
                ListAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(), "Item removed", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        };

        ListAdapter = new listAdapter(items, onLongClickListener);

        list.setAdapter(ListAdapter);
        list.setLayoutManager(new LinearLayoutManager(this));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String todoItem = item.getText().toString(); //gets string
                items.add(todoItem); //put string in array

                ListAdapter.notifyItemInserted(items.size() - 1);
                item.setText("");
                Toast.makeText(getApplicationContext(), "Item added", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });

    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

    //load items
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity", "Error reading Items", e);
            items = new ArrayList<>();
        }
    }

    //write file on close
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity", "Error writing Items", e);

        }
    }
}