package com.example.project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;

public class HomeFragment extends Fragment{
    ViewFlipper v_flipper,v_flipper1;
   /* RecyclerView recyclerView;
    Button AddBtn;
    ArrayList<products> productsInfo;
    productsRecyclerAdapter adapter;
    String userName;  */


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_fragment,container,false);
        /*recyclerView = view.findViewById(R.id.homeFragment_Rv);
        AddBtn = view.findViewById(R.id.home_addBtn);
        productsInfo = new ArrayList<>(); */


        int images[] = {R.drawable.f1,R.drawable.f2,R.drawable.f3,R.drawable.f4,R.drawable.f5,R.drawable.f6,R.drawable.f7};
      /*  int images2[] = {R.drawable.f7,R.drawable.f6,R.drawable.f5,R.drawable.f4,R.drawable.f3,R.drawable.f2,R.drawable.f1}; */
        int images1[] = {R.drawable.f10,R.drawable.f12,R.drawable.f15,R.drawable.f11,R.drawable.f14,R.drawable.f13,R.drawable.f16};
        //int images2[] = {R.drawable.f10,R.drawable.f12,R.drawable.f15,R.drawable.f11,R.drawable.f14,R.drawable.f13,R.drawable.f16};

        v_flipper = view.findViewById(R.id.v_flipper);
        v_flipper1 = view.findViewById(R.id.v_flipper1);
        //v_flipper2 = view.findViewById(R.id.v_flipper2);
        for(int image: images){
            flipperImages(image);
        }
        for(int image1: images1){
            flipperImages1(image1);
        }
        /*for(int image2: images2){
            flipperImages2(image2);
        }*/

      /*  try {
            Intent intent = getActivity().getIntent();
            String userEmail = intent.getStringExtra("email");
            DbHelper dbHelper = new DbHelper(getContext());
            userName = dbHelper.getUserName(userEmail);
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        AddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddProduct_Activity.class);
                Intent intent1 = getActivity().getIntent();
                intent.putExtra("user", intent1.getStringExtra("user"));
                startActivity(intent);
            }
        });

        //getting products
        getProducts();
        adapter = new productsRecyclerAdapter(productsInfo, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter); */

        return view;
    }

   /* public void getProducts(){
        products_Helper pHelper = new products_Helper(getContext());
        productsInfo = pHelper.getProductSInfo();
    } */


    public void flipperImages(int image){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image);
        v_flipper.addView(imageView);
        v_flipper.setFlipInterval(3500);
        v_flipper.setAutoStart(true);
        v_flipper.setInAnimation(getContext(),android.R.anim.slide_in_left);
        v_flipper.setOutAnimation(getContext(),android.R.anim.slide_out_right);
    }
    public void flipperImages1(int image1){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image1);
        v_flipper1.addView(imageView);
        v_flipper1.setFlipInterval(3000);
        v_flipper1.setAutoStart(true);
        v_flipper1.setInAnimation(getContext(),android.R.anim.slide_in_left);
        v_flipper1.setOutAnimation(getContext(),android.R.anim.slide_out_right);
    }
     /*public void flipperImages2(int image2){
        ImageView imageView = new ImageView(getContext());
        imageView.setBackgroundResource(image2);
        v_flipper2.addView(imageView);
        v_flipper2.setFlipInterval(3000);
        v_flipper2.setAutoStart(true);
        v_flipper2.setInAnimation(getContext(),android.R.anim.slide_in_left);
        v_flipper2.setOutAnimation(getContext(),android.R.anim.slide_out_right);
    }*/

  /*  public void onProductClicked(int position) {
        Intent intent = new Intent(getContext(), ProductPageActicity.class);
        intent.putExtra("pName", productsInfo.get(position).getpName());
        intent.putExtra("pPrice", productsInfo.get(position).getpPrice());
        intent.putExtra("pQuantity", productsInfo.get(position).getpQuantity());
        intent.putExtra("pOwner", productsInfo.get(position).getpOwner());
        intent.putExtra("user", userName);
        startActivity(intent);
    }

    @Override
    public void deleteProduct(int position) {
        return;
    } */
}
