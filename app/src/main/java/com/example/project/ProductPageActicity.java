package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.ECField;

public class ProductPageActicity extends AppCompatActivity {
    TextView pNameTv,pPriceTv,pQuantityTv,pOwnerTv,pTypeTv;
    String pName,pPrice,pQuantity,pOwner,user,type;
    ImageView imageView;
    byte[] image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_page_acticity);
        pNameTv = findViewById(R.id.productPage_pNameTv);
        pPriceTv = findViewById(R.id.productPage_pPriceTv);
        pQuantityTv = findViewById(R.id.productPage_pQuantity);
        pOwnerTv = findViewById(R.id.productPage_pOwnerTv);
        pTypeTv = findViewById(R.id.productPage_pTypeTv);
        imageView = findViewById(R.id.productPage_ImageView);

        Intent intent = getIntent();
        pName =intent.getStringExtra("pName");
        pPrice = intent.getStringExtra("pPrice");
        pQuantity = intent.getStringExtra("pQuantity");
        pOwner = intent.getStringExtra("pOwner");
        user = intent.getStringExtra("user");
        type = intent.getStringExtra("pType");
        image = intent.getByteArrayExtra("pImage");

        pNameTv.setText(pName);
        pPriceTv.setText(pPrice + " TL");
        pQuantityTv.setText("Quantity :" + pQuantity);
        pOwnerTv.setText("Seller : " + pOwner);
        pTypeTv.setText("Type : " + type);

        Bitmap image = BitmapFactory.decodeByteArray(intent.getByteArrayExtra("pImage"),
                0, intent.getByteArrayExtra("pImage").length);
        imageView.setImageBitmap(image);

    }

    public void addToCard(View view){
        try {
            card_Helper cHelper = new card_Helper(this);
            String result = cHelper.addToCard(pName, pPrice,pOwner, user,image);
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void buyNow(View view){
        try {
            card_Helper cHelper = new card_Helper(this);
            String result = cHelper.addToCard(pName, pPrice,pOwner, user,image);
            Intent intent = new Intent(this, SalesActivity.class);
            intent.putExtra("userEmail", user);
            startActivity(intent);

        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


}
