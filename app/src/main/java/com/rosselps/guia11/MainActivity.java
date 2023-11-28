package com.rosselps.guia11;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre, txtApellido, txtCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        txtApellido = findViewById(R.id.txtApellido);
        txtCorreo = findViewById(R.id.txtCorreo);

        conexionBD();
    }

    public Connection conexionBD(){
        Connection cn= null;
        try {
            StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            Toast.makeText(this, "Se cargó el driver", Toast.LENGTH_SHORT).show();
            cn = DriverManager.getConnection("jdbc:jtds:sqlserver://172.20.0.1:63173;" +
                    "databaseName=Evento;user=user;password=12345");
            Toast.makeText(this, "Conexión exitosa", Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return cn;
    }
    public void AgregarParticipante(View view){
        try {
            PreparedStatement pps = conexionBD().prepareStatement
                    ("INSERT INTO participantes VALUES(?,?,?)");

            pps.setString(1, txtNombre.getText().toString());
            pps.setString(2,txtApellido.getText().toString());
            pps.setString(3,txtCorreo.getText().toString());

            pps.execute();
            Toast.makeText(this, "Registro Agregado", Toast.LENGTH_SHORT).show();
        }catch (SQLException ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void verPartipantes(View view){
        Intent i = new Intent(this, ListadoParticipantesActivity.class);
        startActivity(i);
    }
}
