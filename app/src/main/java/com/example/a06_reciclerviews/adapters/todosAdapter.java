package com.example.a06_reciclerviews.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a06_reciclerviews.R;
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
                eliminarTarea(todo, holder.getAdapterPosition()).show();
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // AlertDialog con todos los campos a editar
                // Necesita el todo
                // Necesito la posición
                editToDo(todo, holder.getAdapterPosition()).show();
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
    private android.app.AlertDialog eliminarTarea(ToDo todo, int position){
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(context);

        builder.setCancelable(false);
        TextView mensaje = new TextView(context);
        mensaje.setText("¿Estas seguro que eso no se puede editar?");
        mensaje.setTextSize(24);
        mensaje.setTextColor(Color.RED);
        mensaje.setPadding(100,100,100,100);
        builder.setView(mensaje);

        builder.setNegativeButton("NO", null);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                objects.remove(todo);
                notifyItemRemoved(position);
            }
        });
        return builder.create();
    }

    private androidx.appcompat.app.AlertDialog editToDo(ToDo todo, int position) {
        androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder.setTitle("Editar ToDo");
        builder.setCancelable(false);

        // TENEMOS QUE CREAR UN LAYOUT
        View alertView = LayoutInflater.from(context). inflate(R.layout.todo_model_alert, null);
        TextView txtTitulo = alertView.findViewById(R.id.txtTituloToDoModelAlert);
        TextView txtContenido = alertView.findViewById(R.id.txtContenidoToDoModelAlert);
        builder.setView(alertView);

        txtTitulo.setText(todo.getTitulo());
        txtContenido.setText(todo.getContenido());

        // CREAR BOTONES
        builder.setNegativeButton(context.getString(R.string.btn_negative), null);
        builder.setPositiveButton("EDITAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (!txtTitulo.getText().toString().isEmpty() && !txtContenido.getText().toString().isEmpty()){
                    todo.setContenido(txtContenido.getText().toString());
                    todo.setTitulo(txtTitulo.getText().toString());
                    notifyItemChanged(position);
                }
                else {
                    Toast.makeText(context, "FALTAN DATOS", Toast.LENGTH_SHORT).show();
                }
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
}
