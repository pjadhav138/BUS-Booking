package com.example.practiceapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class myprofile extends AppCompatActivity {

    private String TAG = getClass().getSimpleName();
    CircleImageView profilephoto;
    Button changephoto, logout;
    TextView name, email;
    File image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile);
        profilephoto = findViewById(R.id.imageview_profile_photo);
        changephoto = findViewById(R.id.btn_change_photo);
        logout = findViewById(R.id.logout);
        name = findViewById(R.id.username);
        email = findViewById(R.id.mail_address);
        SharedPreferences preferences = getSharedPreferences("MyApp",MODE_PRIVATE);
       String imagePath =  preferences.getString("imagePath","");
        if (!imagePath.isEmpty()){
           Bitmap bitmap =  BitmapFactory.decodeFile(imagePath);
           profilephoto.setImageBitmap(bitmap);
        }

            if (ContextCompat.checkSelfPermission(myprofile.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(myprofile.this, new String[]{
                        Manifest.permission.CAMERA
                }, 100);
            }

        changephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog popup = new Dialog(myprofile.this);
                popup.setContentView(R.layout.pop_up_layout);
                ImageView camera = popup.findViewById(R.id.camera);
                ImageView galary = popup.findViewById(R.id.galary);


                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
              /*          Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(camera,104);
                        Log.e(TAG, "onClick: Camera");  */

                        if (ContextCompat.checkSelfPermission(myprofile.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(myprofile.this, new String[]{
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
                            }, 105);
                        }
                        String path = Environment.getExternalStorageDirectory().getPath() + "/Download/File/";
                        File file = new File(path);
                        if (!file.exists()) {
                            file.mkdirs();
                        }

                        Log.e("TAG", "onClick: " + file.exists());

                        image = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/File", "/" + System.currentTimeMillis() + ".jpg");
                        Intent changephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                        Log.e(TAG, "onClick: " + Uri.parse(image.getPath()));
//                        changephoto.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(image));

                        startActivityForResult(changephoto, 104);

                        popup.dismiss();
                    }
                });
                galary.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.e(TAG, "onClick: Galary");

                        Intent galary = new Intent(Intent.ACTION_GET_CONTENT);
                        galary.setType("image/*");
                        startActivityForResult(galary, 106);

                        popup.dismiss();
                    }
                });
                popup.show();


            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 105:
                Log.e(TAG, "test request code");
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 104) {
                Bitmap capturedimage = (Bitmap) data.getExtras().get("data");
                profilephoto.setImageBitmap(capturedimage);
                image = new File(Environment.getExternalStorageDirectory().getPath() + "/Download/File", "/" + System.currentTimeMillis() + ".jpg");

                FileOutputStream stream = null;
                try {
                    stream = new FileOutputStream(image);
                    capturedimage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    stream.close();
                    SharedPreferences preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("imagePath", image.getPath());
                    editor.commit();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            if (requestCode == 106) {
                Log.e(TAG, "onActivityResult: " + data.getExtras());
                profilephoto.setImageURI(data.getData());
                SharedPreferences preferences = getSharedPreferences("MyApp", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("imagePath", data.getData().toString());
                Uri.parse(data.getData().toString());
                editor.commit();
            }
        }
    }
}