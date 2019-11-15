package com.example.apps1t.listadeenemigos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

public class EnemiesGrid extends Fragment {

    AdapterEnemiesGrid adapterEnemiesGrid;
    Enemies enemies;
    GridView enemyGridView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_enemies_list, container, false);

        /* Acceso al MainActivity */
        final MainActivity mainActivity = (MainActivity) getActivity();

        /* Asignacion de la clase Enemies */
        enemies = new Enemies();

        /* Obtencion de los enemigos almacenados en el ArrayList enemies del MainActivity */
        enemies = mainActivity.getEnemies();

        /* Asignacion de las referencias XML a los elementos */
        enemyGridView = view.findViewById(R.id.enemyGridView);

        /* Declaracion del adapter personalizado y su asignacion al elemento */
        adapterEnemiesGrid = new AdapterEnemiesGrid(this.getActivity(), R.layout.enemygrid, enemies);

        enemyGridView.setAdapter(adapterEnemiesGrid);

        //Eliminar el elemento seleccionado del array manteniendo el click
        enemyGridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                //Código para que se guarden el texto en la otra pantallla
                enemies.remove(position);
                adapterEnemiesGrid.notifyDataSetChanged();
                MainActivity mainActivity = (MainActivity) getActivity();
                mainActivity.saveEnemies(enemies);
                return true;
            }
        });

        return view;
    }

}

