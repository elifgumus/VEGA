package com.example.project;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class AccountFragment extends Fragment implements productsRecyclerAdapter.onProductClicked {
    TextView et_userName;
    CircleImageView userImageView;
    RecyclerView siparislerimRv,myProductsRv;
    Button addBtn;
    String userEmail;
    String userName;

    productsRecyclerAdapter adapter;
    ArrayList<products> productsInfo;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.account_fragment, container, false);
        et_userName = view.findViewById(R.id.account_etUserName);
        userImageView = view.findViewById(R.id.account_userImageView);
        siparislerimRv = view.findViewById(R.id.account_siparislerimRv);
        myProductsRv = view.findViewById(R.id.accounts_myProductsRv);
        addBtn = view.findViewById(R.id.profile_addBtn);
        productsInfo = new ArrayList<>();

        try {
            Intent intent = getActivity().getIntent();
            userEmail = intent.getStringExtra("email");
            DbHelper dbHelper = new DbHelper(getContext());
            userName = dbHelper.getUserName(userEmail);
            et_userName.setText(userName);
        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        et_userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openalert();
            }
        });
        userImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openalert();
            }
        });

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddProduct_Activity.class);
                Intent intent1 = getActivity().getIntent();
                intent.putExtra("user", intent1.getStringExtra("user"));
                startActivity(intent);
            }
        });

        getUserProducts();
        adapter = new productsRecyclerAdapter(productsInfo, this);
        myProductsRv.setLayoutManager(new LinearLayoutManager(getContext()));
        myProductsRv.setAdapter(adapter);

        return view;
    }

    public void openalert(){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        alert.setTitle("Open update page");
        alert.setMessage("Do you want to open update page ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(getContext(), UpdateAccount.class);
                intent.putExtra("email",userEmail);
                startActivity(intent);
            }
        });
        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }

    public void getUserProducts(){
        try {
            products_Helper pHelper = new products_Helper(getContext());
            productsInfo = pHelper.getUserProductsInfo(userName);

        }catch (Exception e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onProductClicked(int position) {
       if (!productsInfo.isEmpty()){
           Intent intent = new Intent(getContext(), ProductPageActicity.class);
           intent.putExtra("pName", productsInfo.get(position).getpName());
           intent.putExtra("pPrice", productsInfo.get(position).getpPrice());
           intent.putExtra("pQuantity", productsInfo.get(position).getpQuantity());
           intent.putExtra("pOwner", productsInfo.get(position).getpOwner());
           intent.putExtra("user", userEmail);
           intent.putExtra("pType", productsInfo.get(position).getpType());
           startActivity(intent);
       }
    }

    @Override
    public void deleteProduct(final int position) {
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext(), AlertDialog.THEME_DEVICE_DEFAULT_DARK);
        alert.setTitle("DELETE Product");
        alert.setMessage("Do you want to delete this product ?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    products_Helper pHelper = new products_Helper(getContext());
                    String msg = pHelper.deleteProduct(productsInfo.get(position).getpName(), userName);
                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                }catch (Exception e){
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alert.show();
    }
}
