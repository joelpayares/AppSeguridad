package com.appseguridad;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.Objects;

public class Registro extends AppCompatActivity implements View.OnClickListener, Serializable {
    private Validacion validacion = new Validacion();
    private FirebaseAuth mAuth;

    private EditText nombres;
    private EditText celular;
    private EditText correo;
    private EditText clave;
    private EditText confClave;

    private Boolean flag = false;

    private ProgressDialog progressDialog;

    UsuariosSQLiteHelper usdbh;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        usdbh = new UsuariosSQLiteHelper(this, "DBUsuarios", null, 1);
        db = usdbh.getWritableDatabase();

        progressDialog = new ProgressDialog(this);

        nombres = findViewById(R.id.txtNombres);
        celular = findViewById(R.id.txtCelular);
        correo = findViewById(R.id.txtCorreo);
        clave = findViewById(R.id.txtContraseña);
        confClave = findViewById(R.id.txtConfContraseña);
        Button registro = findViewById(R.id.btnRegistro);

        registro.setOnClickListener(this);
    }

    private void crearUsuario(String correo, String clave) {
        mAuth.createUserWithEmailAndPassword(correo, clave)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    progressDialog.setMessage("Registro exitoso!!");
                                    progressDialog.show();

                                    almacenarDatos(Objects.requireNonNull(mAuth.getCurrentUser()).getUid());

                                    limpiar();

                                    flag = true;
                                }
                            }, 2000);
                        }
                    }
                });

        if (!flag){
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    progressDialog.setMessage("Registro Fallo!!");
                    progressDialog.dismiss();
                }
            }, 2000);
        }
    }

    @Override
    public void onClick(View view) {
        if (Validar()) {
            progressDialog.setMessage("Procesando Registro!!");
            progressDialog.show();

            crearUsuario(correo.getText().toString(), clave.getText().toString());
        }
    }

    private Boolean Validar() {
        if (!validacion.regla_letcesp(nombres.getText().toString()) ||
                !validacion.regla_lleno(nombres.getText().toString())) {
            nombres.setError("Campo Vacio o invalido");
            return false;
        }

        if (!validacion.regla_numtel(celular.getText().toString()) ||
                !validacion.regla_lleno(celular.getText().toString())) {
            celular.setError("Campo Vacio o invalido");
            return false;
        }

        if (!validacion.regla_corele(correo.getText().toString()) ||
                !validacion.regla_lleno(correo.getText().toString())) {
            correo.setError("Campo Vacio o invalido");
            return false;
        }

        if (!clave.getText().toString().equals(confClave.getText().toString())) {
            confClave.setError("Contraseña no coincide");
            return false;
        } /*else if (!validacion.regla_letsesp(clave.getText().toString()) ||
                !validacion.regla_lleno(clave.getText().toString())) {
            clave.setError("Campo Vacio o invalido");
            return false;
        } else if (!validacion.regla_letsesp(confClave.getText().toString()) ||
                !validacion.regla_lleno(confClave.getText().toString())) {
            confClave.setError("Campo Vacio o invalido");
            return false;
        }*/

        return true;
    }

    private void almacenarDatos(String uid) {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();

        String nombresbd = nombres.getText().toString();
        String celularbd = celular.getText().toString();
        String correobd = correo.getText().toString();
        String clavebd = clave.getText().toString();

        if (db != null) {
            ContentValues nuevoRegistro = new ContentValues();
            nuevoRegistro.put("codigo", uid);
            nuevoRegistro.put("nombre", nombresbd);
            nuevoRegistro.put("celular", celularbd);
            nuevoRegistro.put("correo", correobd);
            nuevoRegistro.put("clave", clavebd);

            myRef.child("Users").child(uid).setValue(new Usuario(uid, nombresbd,
                        celularbd, correobd, clavebd));

            db.insert("Usuarios", null, nuevoRegistro);

            db.close();
        }

        Intent intent = new Intent(Registro.this, MainActivity.class);
        startActivity(intent);
    }

    private void limpiar() {
        nombres.setText("");
        celular.setText("");
        correo.setText("");
        clave.setText("");
        confClave.setText("");
    }
}