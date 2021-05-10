package com.crs.crudfirebasemovies.Adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.crs.crudfirebasemovies.Models.Peliculas;
import com.crs.crudfirebasemovies.R;

import java.util.ArrayList;

public class ListViewPersonasAdapter extends BaseAdapter {

    Context context;
    ArrayList<Peliculas> peliculaData;
    LayoutInflater layoutInflater;
    Peliculas personaModel;

    public ListViewPersonasAdapter(Context context, ArrayList<Peliculas> personaData) {
        this.context = context;
        this.peliculaData = personaData;
        layoutInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
        );
    }

    @Override
    public int getCount() {
        return peliculaData.size();
    }

    @Override
    public Object getItem(int i) {
        return peliculaData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View rowView = view;
        if(rowView == null){
            rowView = layoutInflater.inflate(R.layout.lista_personas,null,true);
        }

        //Enlazar las vistas
        TextView titulo = rowView.findViewById(R.id.Titulos);
        TextView descripcion = rowView.findViewById(R.id.Descripcion);
        TextView tituloOriginal = rowView.findViewById(R.id.TituloOriginal);
        TextView duracion = rowView.findViewById(R.id.Duracion);
        TextView genero = rowView.findViewById(R.id.Genero);
        //TextView fechaRegistro = rowView.findViewById(R.id.FechaDuracion);

        personaModel = peliculaData.get(i);
        titulo.setText(personaModel.getTituloPelicula());
        descripcion.setText(personaModel.getDescripcion());
        tituloOriginal.setText(personaModel.getTituloOriginal());
        duracion.setText(personaModel.getDuracion());
        genero.setText(personaModel.getGenero());
        //fechaRegistro.setText(personaModel.getFechaRegistro());

        return rowView;
    }
}
