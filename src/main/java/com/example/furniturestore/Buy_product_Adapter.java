package com.example.furniturestore;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

class Buy_product_Adapter extends RecyclerView.Adapter<Buy_product_Adapter.mybuy> {
    List<String> imgname,product_name;

    public Buy_product_Adapter(List<String> imgname, List<String> product_name) {
        this.imgname=imgname;
        this.product_name=product_name;
    }

    @NonNull
    @Override
    public mybuy onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_selling_product,parent,false);
        mybuy mybuy=new mybuy(v);
        return mybuy;
    }

    @Override
    public void onBindViewHolder(@NonNull mybuy holder, int position) {
        Picasso.get().load(imgname.get(position)).into(holder.images);
//        holder.images.setImageURI(Uri.parse(imgname.get(position)));
        holder.product_name.setText(product_name.get(position));
    }

    @Override
    public int getItemCount() {
        return imgname.size();
    }

    public class mybuy extends RecyclerView.ViewHolder {

        ImageView images;
        Button buy_button;
        TextView product_name;

        public mybuy(@NonNull View itemView) {
            super(itemView);
            images=itemView.findViewById(R.id.product_Image);
            buy_button=itemView.findViewById(R.id.buy);
            product_name=itemView.findViewById(R.id.product_buy_name);
        }
    }
}
