package com.example.personas_material;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetallePesona extends AppCompatActivity {
    private Persona p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_pesona);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        final ImageView foto;
        TextView cedula, nombre, apellido;
        Bundle bundle;
        Intent intent;
        String ced, nom, apell, id;
        int fot;
        StorageReference storageReference;

        foto=findViewById(R.id.imgFotoDetalle);
        cedula=findViewById(R.id.lblCedulaDetalle);
        nombre=findViewById(R.id.lblNombreDetalle);
        apellido=findViewById(R.id.lblApellidoDetalle);

        intent =getIntent();
        bundle =intent.getBundleExtra("datos");

        //int fot=bundle.getInt("foto");
        id=bundle.getString ("id");
        ced=bundle.getString("cedula");
        nom=bundle.getString("nombre");
        apell=bundle.getString("apellido");

        storageReference= FirebaseStorage.getInstance().getReference();
        storageReference.child(id).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(foto);
            }
        });
       // foto.setImageResource(fot);
        cedula.setText(ced);
        nombre.setText(nom);
        apellido.setText(apell);

        p = new Persona(ced, nom, apell, 0, id);
    }

    public void onBackPressed(){
        finish();
        Intent intent =new Intent(DetallePesona.this,MainActivity.class);
        startActivity(intent);
    }

    public void eliminar(View v){
        String positivo, negativo;
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(R.string.titulo_eliminar_persona);
        builder.setMessage(R.string.pregunta_mensaje_eliminar);
        positivo=getString(R.string.opcion_si);
        negativo=getString(R.string.opcion_no);

        builder.setPositiveButton(positivo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                p.eliminar();
                onBackPressed();
            }
        });
        builder.setNegativeButton(negativo, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
