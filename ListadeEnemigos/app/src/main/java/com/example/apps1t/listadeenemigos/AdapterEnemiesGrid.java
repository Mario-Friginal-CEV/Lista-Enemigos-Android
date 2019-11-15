package com.example.apps1t.listadeenemigos;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdapterEnemiesGrid extends ArrayAdapter{

    Context context;
    int _item_layout;
    ArrayList<Enemy> enemies;

    public AdapterEnemiesGrid(@NonNull Context context, int resource, @NonNull ArrayList objects) {
        super(context, resource, objects);

        this.context = context;
        this._item_layout = resource;
        this.enemies = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Crear vista de la fila
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(_item_layout, parent, false);
        }

        ImageView elementImage = convertView.findViewById(R.id.pictureEnemyGrid);
        Uri enemyImage = Uri.parse(enemies.get(position).uri);
        elementImage.setImageURI(enemyImage);

        TextView nombre = convertView.findViewById(R.id.nameEnemyGrid);
        nombre.setText(enemies.get(position).nombre);

        return convertView;
    }
}
