package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SalesActivity extends AppCompatActivity implements CardRvAdapter.removeProduct{
    EditText addressEt;
    TextView totalPriceTv;
    RecyclerView goods_Rv;
    Button buyBtn;

    CardRvAdapter adapter;
    String user;
    ArrayList<CardInfo> productsInCard;
    String userEmail;

    float totalPrice=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        addressEt = findViewById(R.id.sales_addressEt);
        goods_Rv = findViewById(R.id.sales_goodsRv);
        buyBtn = findViewById(R.id.salses_buyBtn);
        totalPriceTv = findViewById(R.id.sales_totalPriceTv);

        productsInCard = new ArrayList<>();

        user = getIntent().getStringExtra("user");
        userEmail = getIntent().getStringExtra("userEmail");


        getProductsInCard();
        adapter = new CardRvAdapter(productsInCard, this);
        goods_Rv.setLayoutManager(new LinearLayoutManager(this));
        goods_Rv.setAdapter(adapter);

        for (int x=0; x<productsInCard.size(); x++){
             totalPrice += Float.parseFloat(productsInCard.get(x).getpPrice());
        }

        totalPriceTv.setText("Total price : " + String.valueOf(totalPrice) + " TL");

    }

    public void getProductsInCard(){
        try {
            card_Helper cHelper = new card_Helper(this);
            productsInCard = cHelper.getProducts(userEmail);
        }catch (Exception e){
            System.out.println(e.toString());
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void buy(View view){
        if (productsInCard.isEmpty()){
            Toast.makeText(this, "There is no products in card", Toast.LENGTH_LONG).show();

        }else {
            card_Helper cHelper = new card_Helper(SalesActivity.this);
            for (int index=0; index < productsInCard.size(); index++){
                cHelper.removeProductFromCard(productsInCard.get(index).getpName(), productsInCard.get(index).getSeller(),
                        productsInCard.get(index).getUser());
            }
            Toast.makeText(this, "Your order has been reached to us successfully", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, BottomNavigation.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    public void removeProduct(final int position) {
        if (!productsInCard.isEmpty()){
            AlertDialog.Builder alert = new AlertDialog.Builder(this,AlertDialog.THEME_DEVICE_DEFAULT_DARK);
            alert.setTitle("DELETE Product");
            alert.setMessage("If you delete this product, all of products in your card that have a same name and seller will be removed, do you want to continue ?");
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    try {
                        card_Helper cHelper = new card_Helper(SalesActivity.this);
                        String result = cHelper.removeProductFromCard(productsInCard.get(position).getpName(),
                                productsInCard.get(position).getSeller(), productsInCard.get(position).getUser());
                        Toast.makeText(SalesActivity.this, result, Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        Toast.makeText(SalesActivity.this,e.getMessage() ,Toast.LENGTH_LONG).show();
                    }
                }
            });
            alert.show();
        }
    }

}

