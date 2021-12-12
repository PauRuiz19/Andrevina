package com.example.endevinaelnumero;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class MainActivity2 extends AppCompatActivity {

    class Record implements Comparable{
        public int intents;
        public String nom;
        public Uri foto;

        public Record(int _intents, String _nom, Uri uri ) {
            intents = _intents;
            nom = _nom;
            foto = uri;
        }
        public int getIntents() {
            return intents;
        }
        public void setIntents(int intents) {
            this.intents = intents;
        }

        public int compareTo(Object r) {
            int compareRecord = ((Record)r).getIntents();
            return this.intents-compareRecord;
        }
    }
    public static ArrayList<Record> listaRecords = new ArrayList<Record>();
    String currentPhotoPath;
    private File image = null;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            dispatchTakePictureIntent();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_main2);
        Intent intent2 = getIntent();
        listaRecords.add(new Record(intent2.getIntExtra("INTENTOS",0),intent2.getStringExtra("NOMBRE"),Uri.fromFile(image)));
        Collections.sort(listaRecords);
        ArrayAdapter<Record> adapter = new ArrayAdapter<Record>(this, R.layout.list_item,listaRecords)
        {
            @Override
            public View getView(int pos, View convertView, ViewGroup container)
            {
                // getView ens construeix el layout i hi "pinta" els valors de l'element en la posició pos
                if( convertView==null ) {
                    // inicialitzem l'element la View amb el seu layout
                    convertView = getLayoutInflater().inflate(R.layout.list_item, container, false);
                }
                // "Pintem" valors (també quan es refresca)
                ((TextView) convertView.findViewById(R.id.nom)).setText(getItem(pos).nom);
                ((TextView) convertView.findViewById(R.id.intents)).setText(Integer.toString(getItem(pos).intents));
                ((ImageView) convertView.findViewById(R.id.imageView)).setImageURI(getItem(pos).foto);
                return convertView;
            }

        };

        // busquem la ListView i li endollem el ArrayAdapter
        ListView lv = (ListView) findViewById(R.id.recordsView);
        lv.setAdapter(adapter);
    }
    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent

        // Create the File where the photo should go

        File photoFile = createImageFile();
        // Continue only if the File was successfully created

        Uri photoURI = FileProvider.getUriForFile(this,
                "com.pau.android.fileprovider",
                photoFile);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(takePictureIntent, 1);
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        }
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}
