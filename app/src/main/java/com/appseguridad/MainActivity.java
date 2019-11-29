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
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;

    private Button iniciar;
    private Button registro;
    private EditText correo;
    private EditText clave;

    private String nomusu;
    private String clausu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();

        iniciar = findViewById(R.id.btnIniciar);
        registro = findViewById(R.id.btnRegistrar);
        correo = findViewById(R.id.txtEmail);
        clave = findViewById(R.id.txtPass);

        progressDialog = new ProgressDialog(this);

        iniciar.setOnClickListener(this);
        registro.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Object tag = view.getTag();

        if ("btnInicio".equals(tag)) {
            nomusu = correo.getText().toString();
            clausu = clave.getText().toString();

            progressDialog.setMessage("Ingresando!!");
            progressDialog.show();

            inicioSecion(nomusu, clausu);
        } else if ("btnRegistro".equals(tag)) {
            Intent saltoHome = new Intent(MainActivity.this, Registro.class);
            startActivity(saltoHome);
        }
    }

    private void inicioSecion(String nomusu, String clausu) {
        mAuth.signInWithEmailAndPassword(nomusu, clausu)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent saltoHome = new Intent(MainActivity.this, Home.class);
                            startActivity(saltoHome);
                        } else {
                            progressDialog.setMessage("Inicio Falló!!");
                        }

                        progressDialog.dismiss();
                    }
                });
    }
}