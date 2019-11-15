package com.example.apps1t.listadeenemigos;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Debug;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class EnemyAdd extends Fragment {
    ImageView imageIV;
    Button addenemy;
    EditText nombreET;
    EditText motivoET;
    SeekBar seekBar;
    Bitmap image;
    Uri uri;



    final int GALLERY_REQUEST = 4;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_enemy_add, container, false);

        imageIV = view.findViewById(R.id.imageIV);
        addenemy = view.findViewById(R.id.addEnemy);
        nombreET = view.findViewById(R.id.nombreET);
        motivoET =view.findViewById(R.id.motivoET);
        seekBar = view.findViewById(R.id.seekBar);

        imageIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gallery();
            }
        });

        addenemy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendName();

            }
        });
        return view;
    }

    //Acceso a la galeria
    public void gallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST);
    }

    //Coge los parametros de enemy y los envia al main activity en un objeto enemy
    void sendName() {
        if(uri == null){
            Toast.makeText(getActivity(), "Añade una foto", Toast.LENGTH_LONG).show();
            return;
        }else {
            String name = nombreET.getEditableText().toString();
            String motive = motivoET.getEditableText().toString();
            int hate = seekBar.getProgress();
            Enemy enemy = new Enemy(name, motive, uri.toString(), hate);
            MainActivity mainActivity = (MainActivity) getActivity();
            mainActivity.sendData(enemy);
            mainActivity.changeScreen(0);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != RESULT_OK) {
            Toast.makeText(getActivity(), "No hay foto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (requestCode == GALLERY_REQUEST) {
            try {
                uri = data.getData();
                // Leer fichero de la imagen
                InputStream inputStream = getActivity().getContentResolver().openInputStream(uri);
                // Transformar los bits del fichero a un mapa de bits (imagen)
                image = BitmapFactory.decodeStream(inputStream);
                imageIV.setImageBitmap(image);
                uri = saveImage(image);

            } catch (FileNotFoundException e) {
                Toast.makeText(getActivity(),"Imagen no encontrada", Toast.LENGTH_SHORT);
            }
        }
    }

    protected Uri saveImage(Bitmap image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(getContext().getContentResolver(), image, "Title", null);
        return Uri.parse(path);
    }
}

