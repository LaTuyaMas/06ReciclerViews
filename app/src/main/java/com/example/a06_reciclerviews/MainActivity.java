package com.example.a06_reciclerviews;

import android.os.Bundle;

import com.example.a06_reciclerviews.adapters.todosAdapter;
import com.example.a06_reciclerviews.modelo.ToDo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import com.example.a06_reciclerviews.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private ArrayList<ToDo> todosList;

    private todosAdapter adapter;
    // Encargado de indicar como se organizarán los elementos en el Recycler
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        todosList = new ArrayList<>();

        crearTodos();

        adapter = new todosAdapter(MainActivity.this, todosList, R.layout.todo_model_view);
        layoutManager = new LinearLayoutManager(MainActivity.this);

        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void crearTodos() {
        for (int i = 0; i < 1000; i++) {
            todosList.add(new ToDo("Tarea "+i, "Contenido "+i, false));
        }
    }
}