package com.example.setimageactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageView mimageView;
    Button gallery;
    private static final int PICK_IMAGE=1;
    final int  REQUEST_IMAGE_CAPTURE=101;
    Uri imageuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mimageView=findViewById(R.id.imageView);
        gallery=findViewById(R.id.btngallery);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent g=new Intent();
                g.setType("image/*");
                g.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(g,"select picture"),PICK_IMAGE);
            }
        });




    }
    public void takePicture(View view){
        Intent imageTakeIntent= new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        if(imageTakeIntent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(imageTakeIntent,REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                mimageView.setImageBitmap(imageBitmap);
            }
            if(requestCode==PICK_IMAGE&& resultCode==RESULT_OK){
                imageuri= data.getData();
                try{
                    Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imageuri);
                    mimageView.setImageBitmap(bitmap);
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}