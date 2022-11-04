package com.example.a06_reciclerviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.a06_reciclerviews.config.Constantes;
import com.example.a06_reciclerviews.databinding.ActivityEditToDoBinding;
import com.example.a06_reciclerviews.modelo.ToDo;

public class EditToDo extends AppCompatActivity {

    private ActivityEditToDoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditToDoBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_edit_to_do);

        organizarDatos();

        binding.btnConfirmarEditToDo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToDo todo;
                if ((todo = crearTarea()) != null){
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(Constantes.TODO, todo);
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });
    }

    private void organizarDatos(){
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null){
            ToDo todo = (ToDo) bundle.getSerializable(Constantes.TODO);

            binding.txtTituloEdittoDo.setText(todo.getTitulo());
            binding.txtContenidoEditToDo.setText(todo.getContenido());
            binding.txtFechaEditTodo.setText(todo.getFecha().toString());
            binding.cbCompletadoEditToDo.setChecked(todo.isCompletado());
        }
        else {
            Toast.makeText(this, "EL BUNDLE ESTA VACIO", Toast.LENGTH_SHORT).show();
        }
    }

    private ToDo crearTarea(){
        if (binding.txtTituloEdittoDo.getText().toString().isEmpty() ||
                binding.txtContenidoEditToDo.getText().toString().isEmpty() ||
                binding.txtFechaEditTodo.getText().toString().isEmpty() )
            return null;
        return new ToDo(
                binding.txtTituloEdittoDo.getText().toString(),
                binding.txtContenidoEditToDo.getText().toString(),
                binding.cbCompletadoEditToDo.isChecked()
        );
    }
}