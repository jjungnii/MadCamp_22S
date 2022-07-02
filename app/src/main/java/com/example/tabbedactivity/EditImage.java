package com.example.tabbedactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class EditImage extends AppCompatActivity {
    private static final int CAMERA_REQUEST_CODE = 100;
    private static final int GALLERY_REQUEST_CODE = 200;

    private ImageView selectedImageView;
    private String img_fname;
    private Bitmap bitmap_tmp;
    public ImageView selectedImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_image);
        this.selectedImageView = (ImageView) findViewById(R.id.new_memory_selected_image);
        img_fname = getIntent().getStringExtra("img_filename");
    }

    public void openGallery(View view) {
        //TODO: Step 2. launch gallery request
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Image"), GALLERY_REQUEST_CODE);
    }

    public void openCamera(View view) {
        //TODO: Step 5. launch camera activity
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }
    }

    public void cancel(View view) {
        finish();
    }

    public void save(View view) {
        //TODO: Step 9. Update model object
//        Memory memory = new Memory(titleEditText.getText().toString());
        FileOutputStream fos;

        File storage = getCacheDir();
        File f = new File(storage, img_fname);
        if(f.exists()) Log.i("file", "saved");

        try
        {
            fos = new FileOutputStream(f);
            bitmap_tmp.compress(Bitmap.CompressFormat.PNG, 100, fos);

            if(f.exists()) Log.i("file", "saved");
            fos.close();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        finish();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                bitmap_tmp = BitmapFactory.decodeStream(imageStream);
                selectedImageView.setImageBitmap(bitmap_tmp);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            bitmap_tmp = imageBitmap;
            selectedImageView.setImageBitmap(bitmap_tmp);
        }
    }
}