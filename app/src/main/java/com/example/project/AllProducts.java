package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class AllProducts extends AppCompatActivity implements productsRecyclerAdapter.onProductClicked {
    RecyclerView recyclerView;
    ArrayList<products> productsInfo,productsInfo1;
    productsRecyclerAdapter adapter;

    String email,user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_products);
        productsInfo = new ArrayList<>();
        productsInfo1 = new ArrayList<>();

        recyclerView = findViewById(R.id.allproduct_Rv);

        Intent intent = getIntent();
        email = intent.getStringExtra("email");
        user = intent.getStringExtra("user");
        System.out.println(email + user);

        getProducts();
        adapter = new productsRecyclerAdapter(productsInfo, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void getProducts(){
        try {
            products_Helper pHelper = new products_Helper(this);
            productsInfo = pHelper.getProductSInfo();
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onProductClicked(int position) {
        if (!productsInfo.isEmpty()){
            Intent intent = new Intent(this, ProductPageActicity.class);
            intent.putExtra("pName", productsInfo.get(position).getpName());
            intent.putExtra("pPrice", productsInfo.get(position).getpPrice());
            intent.putExtra("pQuantity", productsInfo.get(position).getpQuantity());
            intent.putExtra("pOwner", productsInfo.get(position).getpOwner());
            intent.putExtra("user", email);
            intent.putExtra("pType", productsInfo.get(position).getpType());
            intent.putExtra("pImage", productsInfo.get(position).getImage());
            startActivity(intent);
        }
    }

    @Override
    public void deleteProduct(int position) {
        return;
    }
}
