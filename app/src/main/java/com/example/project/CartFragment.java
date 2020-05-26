package com.example.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CartFragment extends Fragment implements CardRvAdapter.removeProduct {
    RecyclerView card_Rv;
    CardRvAdapter adapter;
    String user;
    ArrayList<CardInfo> productsInCard;
    String userEmail;
    Button buyBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart,container, false);
        card_Rv = view.findViewById(R.id.cardRecyclerView);
        buyBtn = view.findViewById(R.id.card_buyBtn);
        productsInCard = new ArrayList<>();

        try {
            Intent intent = getActivity().getIntent();
            userEmail = intent.getStringExtra("email");
            DbHelper dbHelper = new DbHelper(getContext());
            user = dbHelper.getUserName(userEmail);
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        getProductsInCard();
        adapter = new CardRvAdapter(productsInCard, this);
        card_Rv.setLayoutManager(new LinearLayoutManager(getContext()));
        card_Rv.setAdapter(adapter);


        buyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SalesActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("userEmail", userEmail);
                startActivity(intent);
            }
        });


        return view;
    }


    public void getProductsInCard(){
        try {
            card_Helper cHelper = new card_Helper(getContext());
            productsInCard = cHelper.getProducts(userEmail);
        }catch (Exception e){
            System.out.println(e.toString());
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void removeProduct(final int position) {
       if (!productsInCard.isEmpty()){
           AlertDialog.Builder alert = new AlertDialog.Builder(getContext(),AlertDialog.THEME_DEVICE_DEFAULT_DARK);
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
                       card_Helper cHelper = new card_Helper(getContext());
                       String result = cHelper.removeProductFromCard(productsInCard.get(position).getpName(),
                               productsInCard.get(position).getSeller(), productsInCard.get(position).getUser());
                       Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
                   }catch (Exception e){
                       Toast.makeText(getContext(),e.getMessage() ,Toast.LENGTH_LONG).show();
                   }
               }
           });
           alert.show();
       }
    }
}
