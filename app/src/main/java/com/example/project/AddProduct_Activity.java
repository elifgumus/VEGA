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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class AddProduct_Activity extends AppCompatActivity{
    EditText ProductName_Et,Price_ET,Quantity_ET,Type_ET;
    ImageView imageView;
    Uri uri;
    Bitmap selectedImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product_);
        ProductName_Et = findViewById(R.id.addproduct_productNameET);
        Price_ET = findViewById(R.id.addproduct_priceET);
        Quantity_ET = findViewById(R.id.addproduct_quantityET);
        Type_ET = findViewById(R.id.ddproduct_typeEt);
        imageView = findViewById(R.id.addproduct_ImageView);


    }

    public void openGallery(View view){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
        }else {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 2 && grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 3);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 3 && resultCode == RESULT_OK && data != null){
            uri = data.getData();
            try {
                if (Build.VERSION.SDK_INT>=28){
                    ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), uri);
                    selectedImage = ImageDecoder.decodeBitmap(source);
                    imageView.setImageBitmap(selectedImage);
                }else {
                    selectedImage = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                    imageView.setImageBitmap(selectedImage);
                }
            }catch (Exception e){
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void AddProduct(View view){
        String pName = ProductName_Et.getText().toString().trim();
        String pPrice = Price_ET.getText().toString().trim();
        String pQuantity = Quantity_ET.getText().toString().trim();
        String pType = Type_ET.getText().toString().trim();
        boolean result = checkInfo(pName,pPrice,pQuantity,pType);
        if (result == false){

            products_Helper pHelper = new products_Helper(this);
            Bitmap smallerImage = smallerImage(selectedImage, 500);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            smallerImage.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            byte[] byteArray = outputStream.toByteArray();

            String owner = getIntent().getStringExtra("user");
            String msg = pHelper.addProduct(pName,pPrice,pQuantity, owner, pType, byteArray);
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }
    }

    public Bitmap smallerImage(Bitmap image, int maxSize){
        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float) width / (float) height;
        if (bitmapRatio>1){
            width = maxSize;
            height = (int) (width/bitmapRatio);
        }else {
            height = maxSize;
            width = (int) (height/bitmapRatio);
        }
        return Bitmap.createScaledBitmap(image,width,height,true);
    }

    public boolean checkInfo(String name, String price, String quantity, String type){
        boolean con = false;
        if (TextUtils.isEmpty(name)){
            ProductName_Et.setError("Empty!");
            ProductName_Et.requestFocus();
            con = true;
        }
        if (TextUtils.isEmpty(price)){
            Price_ET.setError("Empty!");
            Price_ET.requestFocus();
            con = true;
        }
        if (TextUtils.isEmpty(quantity)){
            Quantity_ET.setError("Empty!");
            Quantity_ET.requestFocus();
            con = true;
        }
        if (TextUtils.isEmpty(type)){
            Type_ET.setError("Empty!");
            Type_ET.requestFocus();
            con = true;
        }
        if (!type.equals("Clothes") && !type.equals("clothes")
        && !type.equals("Cosmetics") && !type.equals("cosmetics")
        && !type.equals("Electronics") && !type.equals("electronics")
        && !type.equals("Market") && !type.equals("market")
        && !type.equals("Home and life") && !type.equals("home and life")){
            con = true;
            Toast.makeText(this, "Please enter a valid type", Toast.LENGTH_SHORT).show();
            Type_ET.setError("Wrong entry!");
            Type_ET.requestFocus();
        }
        if (selectedImage == null){
            con = true;
        }

        return con;
    }
}
