package com.example.android1lesson2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class second_activity extends AppCompatActivity {

    TextView textView;

    ImageButton button_photo , button_contacts ,  button_google;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_activity);
        textView = findViewById(R.id.tv_number);
        button_photo = findViewById(R.id.btn_photo);
        button_contacts  = findViewById(R.id.btn_contacts);
        button_google = findViewById(R.id.btn_google);

        button_photo.setOnClickListener(v -> {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            startActivity(intent);
        });

        button_contacts.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
            startActivityForResult(intent, 2);
        });

        button_google.setOnClickListener(v -> {
            String escapedQuery = null;
            try {
                escapedQuery = URLEncoder.encode("Android", "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Uri uri = Uri.parse("https://www.youtube.com/watch?v=dQw4w9WgXcQ" + escapedQuery);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
        });
    }

    public void go_btn2(View view) {
        Intent intent = new Intent(this, third_activity.class);
        startActivityForResult(intent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            textView.setText(data.getStringExtra("number"));
        }
        if(requestCode == 2  && resultCode == RESULT_OK && data != null){
            Uri contactData = data.getData();
            Cursor c =  managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Log.e("TAG", "onActivityResult: "+name);
            }
        }
    }
}
