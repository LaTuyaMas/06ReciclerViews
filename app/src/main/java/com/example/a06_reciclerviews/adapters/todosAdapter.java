package com.example.a06_reciclerviews.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a06_reciclerviews.MainActivity;
import com.example.a06_reciclerviews.R;
import com.example.a06_reciclerviews.config.Constantes;
import com.example.a06_reciclerviews.modelo.ToDo;

import java.util.ArrayList;

public class todosAdapter extends RecyclerView.Adapter<todosAdapter.TodoVH> {

    // Elementos para que funcione el Recycler
    // Activity que contiene el RecyclerView
    private Context context;
    // Los datos a mostrar
    private ArrayList<ToDo> objects;
    // Las plantilla para los datos
    private int cardLayout;

    private ActivityResultLauncher<Intent> editarTareaLauncher;


    public todosAdapter(Context context, ArrayList<ToDo> objects, int cardLayout) {
        this.context = context;
        this.objects = objects;
        this.cardLayout = cardLayout;
    }

    // Se llama de forma automática para que se cree nuevos elementos de la plantilla
    // Retorna un objecto CARD ya LISTO para asignar datos
    @NonNull
    @Override
    public TodoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View todoView = LayoutInflater.from(context).inflate(cardLayout, null);
        RecyclerView.LayoutParams layoutParams = new RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        todoView.setLayoutParams(layoutParams);
        return new TodoVH(todoView);
    }
    //Asignará los valores a los elementos de la vista del CARD
    @Override
    public void onBindViewHolder(@NonNull TodoVH holder, int position) {
        ToDo todo = objects.get(position);
        holder.lblTitulo.setText(todo.getTitulo());
        holder.lblContenido.setText(todo.getContenido());
        holder.lblFecha.setText(todo.getFecha().toString());
        if (todo.isCompletado()){
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_on_background);
        }
        else {
            holder.btnCompletado.setImageResource(android.R.drawable.checkbox_off_background);
        }

        holder.btnCompletado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmaUser("¿Estas seguro que quieres cambiar el estado?", todo).show();
            }
        });

        holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eliminarTarea("¿Estas seguro que quieres eliminar la tarea?", todo).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
    // Retornar la cantidad de elementos que hay que instanciar
    @Override
    public int getItemCount() {
        return objects.size();
    }

    private AlertDialog confirmaUser(String mensage, ToDo todo){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle(mensage);
        builder.setCancelable(false);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                todo.setCompletado(!todo.isCompletado());
                notifyDataSetChanged();
            }
        });
        return builder.create();
    }
    private android.app.AlertDialog eliminarTarea(String mensage, ToDo todo){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        builder.setTitle(mensage);
        builder.setCancelable(false);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objects.remove(todo);
                notifyDataSetChanged();
            }
        });
        return builder.create();
    }

    public class TodoVH extends RecyclerView.ViewHolder{

        TextView lblTitulo, lblContenido, lblFecha;
        ImageButton btnCompletado;
        Button btnEliminar;
        public TodoVH(@NonNull View itemView) {
            super(itemView);
            lblTitulo = itemView.findViewById(R.id.lblTituloTodoViewModel);
            lblContenido = itemView.findViewById(R.id.lblContenidoTodoViewModel);
            lblFecha = itemView.findViewById(R.id.lblFechaTodoViewModel);
            btnCompletado = itemView.findViewById(R.id.btnCompletadoTodoViewModel);
            btnEliminar = itemView.findViewById(R.id.btnEliminarToDoViewModel);
        }
    }


    private void inicializaLaunchers(){

        /*
        editarTareaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK) {
                            if (result.getData() != null) {
                                if (result.getData().getExtras() != null) {
                                    if (result.getData().getExtras().getSerializable(Constantes.TODO) != null) {
                                        Inmueble inmueble = (Inmueble) result.getData().getExtras().getSerializable(Constantes.INMUEBLE);
                                        inmueblesList.set(currentlyEditing, inmueble);
                                        Toast.makeText(MainActivity.this, "Editado con exito", Toast.LENGTH_SHORT).show();
                                        mostrarInmueblesContenedor();
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "El bundle no lleva el tag INMUEBLE", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    Toast.makeText(MainActivity.this, "NO HAY BUNDLE EN EL INTENT", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(MainActivity.this, "NO HAY INTENT EN EL RESULT", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Ventana Cancelada", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        */
    }
}
