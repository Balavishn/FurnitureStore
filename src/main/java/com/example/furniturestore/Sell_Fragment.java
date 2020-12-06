package com.example.furniturestore;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.Console;
import java.util.HashMap;


public class Sell_Fragment extends Fragment {
    Uri uri;
    ImageView imageView;
    DatabaseReference ref;
    StorageReference profileref;
    EditText product_name;
    ProgressDialog progressDialog;

    StorageTask mUploadTask;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_sell_, container, false);
        imageView=v.findViewById(R.id.sell_product);
        Button upload=v.findViewById(R.id.upload_click);
        product_name=v.findViewById(R.id.product_name);


        ref= FirebaseDatabase.getInstance().getReference("Sell");

        profileref= FirebaseStorage.getInstance().getReference("Sell_product");
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(i,100);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (product_name.getText().toString().isEmpty()){
                    product_name.setError("Enter Product Name");
                }
                else{
                    progressDialog = new ProgressDialog(getContext());

                    progressDialog.setTitle("Uploading...");

                    progressDialog.show();

                    profileref=profileref.child(product_name.getText().toString()).child("product.jpg");

                    mUploadTask=profileref.putFile(uri);
                    Task<Uri> urltask=mUploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()){
                                throw task.getException();
                            }
                            return profileref.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()){
                                Uri downuri= (Uri) task.getResult();
                                Log.d("Download Url",downuri.toString());
                                HashMap<String, String> imgmap = new HashMap<>();
                                imgmap.put("product", product_name.getText().toString());
                                imgmap.put("url", downuri.toString());
                                ref.child(product_name.getText().toString()).setValue(imgmap);
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Upload Successfully", Toast.LENGTH_SHORT).show();
                                imageView.setImageResource(R.drawable.ic_baseline_image_24);
                                product_name.setText("");
                            }
                        }
                    });

                   /* .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> imguri=taskSnapshot.getStorage().getDownloadUrl();
                            if (imguri.isSuccessful()) {
                                Log.d("Url", imguri.getResult().toString());
                                HashMap<String, String> imgmap = new HashMap<>();
                                imgmap.put("product", product_name.getText().toString());
                                imgmap.put("url", imguri.getResult().toString());
                                ref.child(product_name.getText().toString()).setValue(imgmap);
                                progressDialog.dismiss();
                                Toast.makeText(getContext(), "Upload Successfully", Toast.LENGTH_SHORT).show();
                                imageView.setImageResource(R.drawable.ic_baseline_image_24);
                                product_name.setText("");
                            }
                        }
                    });*/
                  /*  profileref.child("product.jpg").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri u) {
                            HashMap<String,String> imgmap=new HashMap<>();
                            imgmap.put("product",product_name.getText().toString());
                            imgmap.put("url",u.toString());
                            ref.child(product_name.getText().toString()).setValue(imgmap);
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), "Upload Successfully", Toast.LENGTH_SHORT).show();
                            imageView.setImageResource(R.drawable.ic_baseline_image_24);
                            product_name.setText("");
                        }
                    });*/
                }
            }
        });
        return v;
    }

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //bar.setVisibility(View.VISIBLE);
        uri=data.getData();
        imageView.setImageURI(uri);
    }
}