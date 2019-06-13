package com.example.camerateste;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 254;
    private Uri uri;
    private ImageView image;
    private File photoFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();
    }

    private void findViews() {
        image = findViewById(R.id.iv);
        Button btOpen = findViewById(R.id.bt_open_camera);

        btOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getCameraIntent(), CAMERA_REQUEST_CODE);
            }
        });
    }

    Intent getCameraIntent() {
        uri = createUriForFile();
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        it.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        return it;
    }

    private Uri createUriForFile() {
        photoFile = createPhotoFile();
        return FileProvider.getUriForFile(getBaseContext(), getBaseContext().getPackageName() + ".provider", photoFile);
    }

    private File createPhotoFile() {
        File externalFilesDir = getBaseContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(externalFilesDir, "photo");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE){
            Bitmap bitmap = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
            image.setImageBitmap(bitmap);
        }
    }
}
