package com.crs.crudfirebasemovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.crs.crudfirebasemovies.Adaptadores.ListViewPersonasAdapter;
import com.crs.crudfirebasemovies.Models.Peliculas;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Peliculas> listPeliculas = new ArrayList<Peliculas>();

    ArrayAdapter<Peliculas> arrayAdapterPelicula;
    ListViewPersonasAdapter listViewPersonasAdapter;
    LinearLayout linearLayoutEditar;

    EditText inputTitulo, inputDescripcion, inputtituloOriginal, inputDuracion, inputGenero;
    Button btnCancelar;
    ListView listViewPeliculas;

    Peliculas peliculasSeleccionada;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputTitulo = findViewById(R.id.inputTitulo);
        inputDescripcion = findViewById(R.id.inputDescripcion);
        inputtituloOriginal = findViewById(R.id.inputtituloOriginal);
        inputDuracion = findViewById(R.id.inputDuracion);
        inputGenero = findViewById(R.id.inputGenero);
        btnCancelar = findViewById(R.id.btnCancelar);

        listViewPeliculas = findViewById(R.id.ListViewPersonas);
        linearLayoutEditar = findViewById(R.id.linearLayoutEditar);

        listViewPeliculas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                peliculasSeleccionada = (Peliculas) adapterView.getItemAtPosition(i);
                inputTitulo.setText(peliculasSeleccionada.getTituloPelicula());
                inputDescripcion.setText(peliculasSeleccionada.getDescripcion());
                inputtituloOriginal.setText(peliculasSeleccionada.getTituloOriginal());
                inputDuracion.setText(peliculasSeleccionada.getDuracion());
                inputGenero.setText(peliculasSeleccionada.getGenero());

                //Hacer visible el linear layout
                linearLayoutEditar.setVisibility(View.VISIBLE);
            }
        });
        btnCancelar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                linearLayoutEditar.setVisibility(View.GONE);
                peliculasSeleccionada = null;
            }
        });

        inicializarFireBase();
        listarPeliculas();
    }

    private void inicializarFireBase(){
        FirebaseApp.initializeApp(this);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    private void listarPeliculas(){
        databaseReference.child("Peliculas").orderByChild("timesstamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listPeliculas.clear();
                for (DataSnapshot objSnapshot : dataSnapshot.getChildren()){
                    Peliculas p = objSnapshot.getValue(Peliculas.class);
                    listPeliculas.add(p);
                }

                //Iniciar nuestro propio adaptador
                arrayAdapterPelicula = new ArrayAdapter<Peliculas>(
                    MainActivity.this,
                    android.R.layout.simple_list_item_1,
                    listPeliculas
                );
                listViewPeliculas.setAdapter(arrayAdapterPelicula);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.crud_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        String titulo = inputTitulo.getText().toString();
        String descripcion = inputDescripcion.getText().toString();
        String tituloOriginal = inputtituloOriginal.getText().toString();
        String duracion = inputDuracion.getText().toString();
        String genero = inputGenero.getText().toString();


        switch (item.getItemId()){
            case R.id.menu_agregar:
                insertar();
                break;

            case  R.id.menu_guardar:
                if (peliculasSeleccionada !=null){
                    if (validarInputs() == false){
                        Peliculas p = new Peliculas();
                        p.setIdPelicula(peliculasSeleccionada.getIdPelicula());
                        p.setTituloPelicula(titulo);
                        p.setDescripcion(descripcion);
                        p.setDuracion(duracion);
                        p.setGenero(genero);
                        p.setTituloOriginal((tituloOriginal));
                        p.setFechaRegistro(peliculasSeleccionada.getFechaRegistro());
                        p.setTimeStamp(peliculasSeleccionada.getTimeStamp());

                        databaseReference.child("Peliculas").child(p.getIdPelicula()).setValue(p);
                        Toast.makeText(MainActivity.this,"Actualizado Correctamente !!!",Toast.LENGTH_SHORT).show();
                        linearLayoutEditar.setVisibility(View.GONE);
                        peliculasSeleccionada = null;
                    }
                }else{
                    Toast.makeText(MainActivity.this,"Seleccione una persona !!!",Toast.LENGTH_SHORT).show();
                }
            break;
            case R.id.menu_eliminar:
                if (peliculasSeleccionada != null){
                    Peliculas p2 = new Peliculas();
                    p2.setIdPelicula(peliculasSeleccionada.getIdPelicula());
                    databaseReference.child("Peliculas").child(p2.getIdPelicula()).removeValue();
                    linearLayoutEditar.setVisibility(View.GONE);
                    peliculasSeleccionada = null;
                    Toast.makeText(MainActivity.this,"Eliminado correctamente !!!",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(MainActivity.this,"Seleccione una persona para eliminar !!!",Toast.LENGTH_SHORT).show();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public boolean validarInputs(){
        String titulo = inputTitulo.getText().toString();
        String Descripcion = inputDescripcion.getText().toString();

        if(titulo.isEmpty()){
            showError(inputTitulo, "Debe llenar este campo");
            return true;
        }
        else if (Descripcion.isEmpty()){
            showError(inputTitulo, "Debe llenar este campo");
            return true;
        }else{
            return false;
        }
    }

    public void insertar(){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(
                MainActivity.this
        );
        View mView = getLayoutInflater().inflate(R.layout.insertar, null);
        Button btnInsertar = (Button) mView.findViewById(R.id.btnInsertar);
        final EditText mInputTitulo = (EditText) mView.findViewById(R.id.inputTitulo);
        final EditText mInputDescripcion = (EditText) mView.findViewById(R.id.inputDescripcion);
        final EditText mInputTituloOriginal = (EditText) mView.findViewById(R.id.inputtituloOriginal);
        final EditText mInputDuracion = (EditText) mView.findViewById(R.id.inputDuracion);
        final EditText mInputGenero = (EditText) mView.findViewById(R.id.inputGenero);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        dialog.show();

        btnInsertar.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String titulo = mInputTitulo.getText().toString();
                String descripcion = mInputDescripcion.getText().toString();
                String tituloOriginal = mInputTituloOriginal.getText().toString();
                String duracion = mInputDuracion.getText().toString();
                String genero = mInputGenero.getText().toString();

                if (titulo.isEmpty() || descripcion.isEmpty() || tituloOriginal.isEmpty()){
                    showError(mInputTitulo, "Debe de completar el titulo, descripcion y titulo original como minimo");
                }else{
                    Peliculas p = new Peliculas();
                    p.setIdPelicula(UUID.randomUUID().toString());
                    p.setTituloPelicula(titulo);
                    p.setDescripcion(descripcion);
                    p.setTituloOriginal(tituloOriginal);
                    p.setDuracion(duracion);
                    p.setGenero(genero);
                    p.setFechaRegistro(getFechaNormal(getFechaMilisegundos()));
                    p.setTimeStamp(getFechaMilisegundos() * -1);

                    databaseReference.child("Peliculas").child(p.getIdPelicula()).setValue(p);
                    Toast.makeText(MainActivity.this,"Registrado Correctamente !!!",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            }
        });
    }

    public void showError(EditText input, String s){
        input.requestFocus();
        input.setError(s);
    }

    public long getFechaMilisegundos(){
        Calendar calendar = Calendar.getInstance();
        long tiempoUnix = calendar.getTimeInMillis();

        return tiempoUnix;
    }

    public String getFechaNormal(long fechaMili){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT-5"));
        String fecha = sdf.format(fechaMili);
        return  fecha;
    }
}