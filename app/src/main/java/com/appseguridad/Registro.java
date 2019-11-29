package com.appseguridad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registro extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;

    private Button registro;
    private EditText correo;
    private EditText clave;
    private EditText confClave;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        registro = findViewById(R.id.btnRegistro);
        correo = findViewById(R.id.txtCorreo);
        clave = findViewById(R.id.txtContraseña);

        registro.setOnClickListener(this);
    }

    private void crearUsuario(String correo, String clave) {
        mAuth.createUserWithEmailAndPassword(correo, clave)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.setMessage("Registro Exitoso!!");

                            Intent intent = new Intent(Registro.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            progressDialog.setMessage("Registro Falló!!");
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void onClick(View view) {
        progressDialog.setMessage("Procesando Registro!!");
        progressDialog.show();

        crearUsuario(correo.getText().toString(), clave.getText().toString());
    }
}