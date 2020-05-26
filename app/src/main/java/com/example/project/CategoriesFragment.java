package com.example.project;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class CategoriesFragment extends Fragment {
    @Nullable
    CardView clothes,market,home,cosmetic,electronic,allproduct;

    String userEmail,userName;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentcategories, container, false);
        clothes=view.findViewById(R.id.clothes);
        market=view.findViewById(R.id.market);
        home=view.findViewById(R.id.home);
        cosmetic=view.findViewById(R.id.cosmetic);
        electronic=view.findViewById(R.id.electronic);
        allproduct=view.findViewById(R.id.allproduct);

        clothes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openalert();
            }
        });
        market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openalert1();
            }
        });
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openalert2();
            }
        });
        cosmetic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openalert3();
            }
        });
        electronic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openalert4();
            }
        });
        allproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openalert5();
            }
        });

        try {
            Intent intent = getActivity().getIntent();
            userEmail = intent.getStringExtra("email");
            DbHelper dbHelper = new DbHelper(getContext());
            userName = dbHelper.getUserName(userEmail);
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


        return view;
    }
    public void openalert(){
        Intent intent = new Intent(getContext(), Clothes.class);
        intent.putExtra("user", userName);
        intent.putExtra("email", userEmail);
        startActivity(intent);
    }
    public void openalert1(){
        Intent intent = new Intent(getContext(), Market.class);
        intent.putExtra("user", userName);
        intent.putExtra("email", userEmail);
        startActivity(intent);
    }
    public void openalert2(){
        Intent intent = new Intent(getContext(), Home.class);
        intent.putExtra("user", userName);
        intent.putExtra("email", userEmail);
        startActivity(intent);
    }
    public void openalert3(){
        Intent intent = new Intent(getContext(), Cosmetic.class);
        intent.putExtra("user", userName);
        intent.putExtra("email", userEmail);
        startActivity(intent);
    }
    public void openalert4(){
        Intent intent = new Intent(getContext(), Electronic.class);
        intent.putExtra("user", userName);
        intent.putExtra("email", userEmail);
        startActivity(intent);
    }
    public void openalert5(){
        Intent intent = new Intent(getContext(),AllProducts.class);
        intent.putExtra("user", userName);
        intent.putExtra("email", userEmail);
        startActivity(intent);
    }

    }

