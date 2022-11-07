package com.example.a06_reciclerviews;

import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;

import com.example.a06_reciclerviews.adapters.todosAdapter;
import com.example.a06_reciclerviews.config.Constantes;
import com.example.a06_reciclerviews.modelo.ToDo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

        // crearTodos();

        // Orientaciones del movil
        // getResources().getConfiguration().orientation
        // PORTRAIT -> Vertical
        // LANDSCAPE -> Horizontal

        int columnas;

        columnas = getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT ? 1 : 2;

        adapter = new todosAdapter(MainActivity.this, todosList, R.layout.todo_model_view);
        layoutManager = new GridLayoutManager(MainActivity.this, columnas);
        binding.contentMain.contenedor.setAdapter(adapter);
        binding.contentMain.contenedor.setLayoutManager(layoutManager);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createToDo().show();
            }
        });


    }

    //Ventana emergente para crear un nuevo ToDo
    private AlertDialog createToDo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle(getString(R.string.create_title));
        builder.setCancelable(false);
        // TENEMOS QUE CREAR UN LAYOUT
        View alertView = LayoutInflater.from(MainActivity.this). inflate(R.layout.todo_model_alert, null);
        TextView txtTitulo = alertView.findViewById(R.id.txtTituloToDoModelAlert);
        TextView txtContenido = alertView.findViewById(R.id.txtContenidoToDoModelAlert);
        builder.setView(alertView);
        // CREAR BOTONES
        builder.setNegativeButton(getResources().getString(R.string.btn_negative), null);
        builder.setPositiveButton(getResources().getString(R.string.btn_positive_create), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtTitulo.getText().toString().isEmpty() && !txtContenido.getText().toString().isEmpty()){
                    ToDo todo = new ToDo(txtTitulo.getText().toString(),
                            txtContenido.getText().toString(), false);
                    todosList.add(0, todo);
                    adapter.notifyItemInserted(0);
                }
                else {
                    Toast.makeText(MainActivity.this, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return builder.create();
    }

    // Sin recycler View
    private void crearTodos() {
        for (int i = 0; i < 1000; i++) {
            todosList.add(new ToDo("Tarea "+i, "Contenido "+i, false));
        }
    }

    //Guarda la lista aunque se destruya la información
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(Constantes.TODO, todosList);
    }

    //Restablece la lista
    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<ToDo> temporal = (ArrayList<ToDo>) savedInstanceState.getSerializable(Constantes.TODO);
        todosList.addAll(temporal);
        adapter.notifyItemRangeInserted(0, todosList.size());
    }
}