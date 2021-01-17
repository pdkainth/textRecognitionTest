package com.example.textrecognition;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.text.FirebaseVisionText;
import com.google.firebase.ml.vision.text.FirebaseVisionTextRecognizer;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ImageToText.ImageToTextCompleteListener {

  // textview object to display text
  private TextView textView;
  private EditText editText;

  private ImageToText imageToText;
  private static final String TAG = "MAIN_ACTIVITY";

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // init textview
    textView = findViewById(R.id.textId);
    editText = findViewById(R.id.save_name);
    editText.setText("");

    imageToText = new ImageToText(getApplicationContext(), this);
    imageToText.setOnImageToTextComplete(this);

  }

  // onClick activity for the take picture button
  public void execute(View view) {
    Log.d(TAG, "Starting image to text");
    imageToText.start();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    imageToText.extractText(requestCode, resultCode, data);

  }

  @Override
  public void onImageToTextComplete(String s) {
    textView.setText(s);
  }

  public void save(View view) {
    Log.d(TAG, "Starting image to text");
    String prefix = editText.getText().toString();
    if (!prefix.isEmpty()) {
      imageToText.save(prefix);
      imageToText.cleanup();
      textView.setText("");
      editText.setText("");
    } else {
      Toast.makeText(this, "File prefix input missing", Toast.LENGTH_LONG).show();
    }

  }

}