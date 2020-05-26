package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateAccount extends AppCompatActivity {
    EditText et_name,et_email,et_address,et_phone;
    Button updateBtn;
    CircleImageView imageView;
    String name,Email,address,phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        et_name = findViewById(R.id.update_etName);
        et_email = findViewById(R.id.update_etEmail);
        et_address = findViewById(R.id.update_etAddress);
        et_phone = findViewById(R.id.update_etPhone);
        updateBtn = findViewById(R.id.update_updateBtn);
        imageView = findViewById(R.id.update_imageView);

        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        try {
            DbHelper dbHelper = new DbHelper(this);
            ArrayList<User> userInfo = new ArrayList<>();
            userInfo = dbHelper.getUserInfo(email);
            name = userInfo.get(0).getName();
            Email = userInfo.get(0).getEmail();
            address = userInfo.get(0).getAddress();
            phone = userInfo.get(0).getPhone();
            et_name.setText(name);
            et_email.setText(Email);
            et_phone.setText(phone);
            et_address.setText(address);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }


    public void update(View view){
        boolean result = checkInfo();
        if (result == false){
            try {
                DbHelper dbHelper = new DbHelper(this);
                String newName = et_name.getText().toString().trim();
                String newAddress = et_address.getText().toString().trim();
                String newPhone = et_phone.getText().toString();
                String res = dbHelper.updateUser(newName,Email,newAddress,newPhone);
                Toast.makeText(this, res, Toast.LENGTH_LONG).show();
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    public boolean checkInfo(){
        boolean con = false;
       if (et_name.getText().toString().trim().equals(name) && et_address.getText().toString().equals(address)
        && et_phone.getText().toString().equals(phone)){
           con = true;
           Toast.makeText(this, "You didn't change any thing", Toast.LENGTH_LONG).show();
       }
        return con;
    }
}
