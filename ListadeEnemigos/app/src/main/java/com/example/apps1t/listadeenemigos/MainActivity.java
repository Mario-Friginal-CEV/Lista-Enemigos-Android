package com.example.apps1t.listadeenemigos;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView menu;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;
    Enemies enemies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        enemies = loadEnemies();

        if(enemies == null){
            enemies = new Enemies();
        }

        //Referencias de los elementos
        menu = findViewById(R.id.menu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        //Selecciona los elementos del menu
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                changeScreen(position);
            }
        });


        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.abrir_menu, R.string.cerrar_menu);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        changeScreen(0);
    }

    public Enemies getEnemies() {

        return enemies;
    }


    void changeScreen(int screen) {
        Fragment fragment = null;
        FragmentManager manager = getSupportFragmentManager();

        switch (screen) {
            case 0:
                fragment = new EnemiesGrid();
                break;
            case 1:
                fragment = new EnemyAdd();
                break;
            case 2:
                fragment = new EnemiesTop5();
                break;
        }

        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
        drawerLayout.closeDrawer(Gravity.START);
    }

    public void sendData(Enemy enemy){
        if(enemy != null) enemies.add(enemy);
    }

    public void saveEnemies(Enemies enemies){
        String json = enemies.toJson();
        SharedPreferences preferences = getSharedPreferences("Enemies", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("enemy", json);
        editor.commit();

    }

    Enemies loadEnemies() {
        SharedPreferences preferences = getSharedPreferences("Enemies", MODE_PRIVATE);
        String json = preferences.getString("enemy", "");
        return Enemies.fromJson(json);
    }

    // Permite desplegar el menu lateral
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    // Permite cerrar el menu lateral
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Guarda los datos contenidos en datosEnemigos cuando se pausa o cierra la aplicacion
    @Override
    protected void onPause() {
        super.onPause();
        saveEnemies(enemies);
    }
}