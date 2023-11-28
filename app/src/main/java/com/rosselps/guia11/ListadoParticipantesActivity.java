package com.rosselps.guia11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import java.sql.Statement;
import java.util.ArrayList;

public class ListadoParticipantesActivity extends AppCompatActivity {
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_participantes);

        lv= findViewById(R.id.lv);

        listarParticipantes();
    }

    public Connection realizarConexion(){
        Connection cn = null;
        try {
            StrictMode.ThreadPolicy policy= new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

            Class.forName("net.sourceforge.jtds.jdbc.Driver").newInstance();
            cn = DriverManager.getConnection("jdbc:jtds:sqlserver://172.20.0.1:63173;" +
                    "databaseName=Evento;user=user;password=12345");
        }catch (Exception ex){
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return cn;
    }

    public void listarParticipantes(){
        ArrayList<String> lista=new ArrayList<>();
        try {
            Statement stm = realizarConexion().createStatement();
            ResultSet rs = stm.executeQuery(
                    "SELECT nombre, apellido, correo FROM participantes");
            while (rs.next()){
                String nombre = rs.getString(1);
                String apellido = rs.getString(2);
                String correo = rs.getString(3);

                lista.add("Nombre: " + nombre
                        + "\nApellido: " + apellido
                        + "\nCorreo: " + correo);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,lista);
            lv.setAdapter(adapter);
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void regresar(View view){
        finish();
    }
}
