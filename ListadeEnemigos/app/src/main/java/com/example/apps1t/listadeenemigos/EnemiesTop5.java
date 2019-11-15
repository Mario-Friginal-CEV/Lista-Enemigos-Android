package com.example.apps1t.listadeenemigos;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class EnemiesTop5 extends Fragment {

    AdapterEnemiesList adapterEnemiesList;
    Enemies enemies;
    ListView enemyListView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_enemies_top5, container, false);

        MainActivity mainActivity = (MainActivity) getActivity();

        enemies = new Enemies();

        enemies = mainActivity.getEnemies();

        enemyListView = view.findViewById(R.id.enemyListView);

        adapterEnemiesList = new AdapterEnemiesList(this.getActivity(), R.layout.enemylist, enemies);

        enemyListView.setAdapter(adapterEnemiesList);

        return view;
    }
}
