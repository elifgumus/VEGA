package com.example.project;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CardRvAdapter extends RecyclerView.Adapter<CardRvAdapter.CardHolder> {

    ArrayList<CardInfo> productsInCard;
    removeProduct removeProduct;

    public CardRvAdapter(ArrayList<CardInfo> productsInCard, removeProduct removeProduct) {
        this.productsInCard = productsInCard;
        this.removeProduct = removeProduct;
    }

    @NonNull
    @Override
    public CardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_rvadapter, parent, false);
        return new CardHolder(view, removeProduct);
    }

    @Override
    public void onBindViewHolder(@NonNull CardHolder holder, int position) {
      if (productsInCard.isEmpty()){
          holder.imageView.setVisibility(View.GONE);
          holder.pPriceTv.setTextSize(25);
          holder.pPriceTv.setText("No products in card... :(");
          holder.deleteBtn.setVisibility(View.GONE);
          holder.sellerTv.setVisibility(View.GONE);
          holder.pNameTv.setVisibility(View.GONE);
      }else {
          holder.sellerTv.setText("Seller :" + productsInCard.get(position).getSeller());
          holder.pPriceTv.setText(productsInCard.get(position).getpPrice() + " TL");
          holder.pNameTv.setText(productsInCard.get(position).getpName());
          Bitmap image = BitmapFactory.decodeByteArray(productsInCard.get(position).getImage(),
                  0, productsInCard.get(position).getImage().length);
          holder.imageView.setImageBitmap(image);
      }
    }

    @Override
    public int getItemCount() {
        if (productsInCard.isEmpty()){
            return 1;
        }else {
            return productsInCard.size();
        }
    }

    public class CardHolder extends RecyclerView.ViewHolder{
        TextView pNameTv,pPriceTv,sellerTv;
        Button deleteBtn;
        ImageView imageView;
        removeProduct removeProduct;
        public CardHolder(@NonNull View itemView, final removeProduct removeProduct) {
            super(itemView);
            this.removeProduct = removeProduct;
            pNameTv = itemView.findViewById(R.id.card_adapter_pNameTv);
            pPriceTv = itemView.findViewById(R.id.card_adapter_pPriceTv);
            sellerTv = itemView.findViewById(R.id.card_adapter_sellerTv);
            deleteBtn = itemView.findViewById(R.id.card_adapter_deleteBtn);
            imageView = itemView.findViewById(R.id.card_adapter_ImageView);

            deleteBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   removeProduct.removeProduct(getLayoutPosition());
                }
            });
        }
    }
    public interface removeProduct{
        void removeProduct(int position);
    }
}
