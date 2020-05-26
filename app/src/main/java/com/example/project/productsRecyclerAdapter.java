package com.example.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class productsRecyclerAdapter extends RecyclerView.Adapter<productsRecyclerAdapter.productHolder> {

    ArrayList<products> productsInfo;
    onProductClicked onProductClicked;

    public productsRecyclerAdapter(ArrayList<products> productsInfo, onProductClicked onProductClicked) {
        this.productsInfo = productsInfo;
        this.onProductClicked = onProductClicked;
    }

    @NonNull
    @Override
    public productHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.product_row_adapter, parent, false);

        return new productHolder(view, onProductClicked);
    }

    @Override
    public void onBindViewHolder(@NonNull productHolder holder, int position) {
        if (productsInfo.isEmpty()){
            holder.pNameTV.setText("No products available :(");
            holder.pNameTV.setTextSize(25);
            holder.pTypeTV.setVisibility(View.GONE);
            holder.pSallerTV.setVisibility(View.GONE);
            holder.pPriceTV.setVisibility(View.GONE);
            holder.pQuantityTV.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.GONE);
        }else {
            holder.pNameTV.setText("Product : " + productsInfo.get(position).getpName());
            holder.pQuantityTV.setText("Quantity : " + productsInfo.get(position).getpQuantity());
            holder.pPriceTV.setText("Price : " + productsInfo.get(position).getpPrice());
            holder.pSallerTV.setVisibility(View.GONE);
            holder.pTypeTV.setText("Type : " + productsInfo.get(position).getpType());
            Bitmap image = BitmapFactory.decodeByteArray(productsInfo.get(position).getImage(),
                    0, productsInfo.get(position).getImage().length);
            holder.imageView.setImageBitmap(image);
        }

    }

    @Override
    public int getItemCount() {
        if (productsInfo.isEmpty()){
            return 1;
        }else {
            return productsInfo.size();
        }
    }

    public class productHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView pNameTV,pPriceTV,pQuantityTV,pSallerTV,pTypeTV;
        ImageView imageView;
        onProductClicked onProductClicked;
        public productHolder(@NonNull View itemView, final onProductClicked onProductClicked) {
            super(itemView);
            this.onProductClicked = onProductClicked;
            pNameTV = itemView.findViewById(R.id.padapter_pNameTV);
            pPriceTV = itemView.findViewById(R.id.padapter_priceTV);
            pQuantityTV = itemView.findViewById(R.id.padapter_quantityTV);
            pSallerTV = itemView.findViewById(R.id.padapter_sallerTV);
            pTypeTV = itemView.findViewById(R.id.padapter_typeTV);
            imageView = itemView.findViewById(R.id.productAdapter_image);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onProductClicked.deleteProduct(getLayoutPosition());
                    return true;
                }
            });
        }

        @Override
        public void onClick(View v) {
            onProductClicked.onProductClicked(getLayoutPosition());
        }
    }

    public interface onProductClicked{
        void onProductClicked(int position);
        void deleteProduct(int position);
    }
}
