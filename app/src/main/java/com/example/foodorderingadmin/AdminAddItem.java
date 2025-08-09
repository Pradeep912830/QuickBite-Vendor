package com.example.foodorderingadmin;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.UUID;

public class AdminAddItem extends AppCompatActivity {
    ImageView backPressed, chooseImage, showImage;
    EditText itemName, itemPrice, descriptionOfFood, ingredientsOfFood;
    AppCompatButton addItemButton;
    Uri uri;
    private static final int PICK_IMAGE_REQUEST = 1;

    DatabaseReference databaseReference;
    StorageReference storageReference;
    ProgressDialog progressDialog;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin_add_iteam);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        backPressed = findViewById(R.id.backPressed);
        chooseImage = findViewById(R.id.chooseImage);
        showImage = findViewById(R.id.showImage);
        itemName = findViewById(R.id.itemName);
        itemPrice = findViewById(R.id.itemPrice);
        descriptionOfFood = findViewById(R.id.descriptionOfFood);
        ingredientsOfFood = findViewById(R.id.ingredientsOfFood);
        addItemButton = findViewById(R.id.addItemButton);

        backPressed.setOnClickListener(v -> onBackPressed());

        chooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });


        addItemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadData();
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReference("FoodItems");
        storageReference = FirebaseStorage.getInstance().getReference("FoodImages");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

    }


    private void openImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture!"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null){
             uri = data.getData();
            showImage.setImageURI(uri);
        }
    }

    public void uploadData(){
        String name = itemName.getText().toString().trim();
        String price = itemPrice.getText().toString().trim();
        String description = descriptionOfFood.getText().toString().trim();
        String ingredient = ingredientsOfFood.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            itemName.setError("Please Enter Food Name!");
            return;
        }

        if(TextUtils.isEmpty(price)){
            itemPrice.setError("Please Enter Food Price!");
            return;
        }

        if(TextUtils.isEmpty(description)){
            descriptionOfFood.setError("Please Enter Food Description!");
            return;
        }

        if(TextUtils.isEmpty(ingredient)){
            ingredientsOfFood.setError("Please Enter Food Ingredients!");
            return;
        }

        progressDialog.setMessage("Upload Items");
        progressDialog.show();

        StorageReference fileref = storageReference.child(UUID.randomUUID().toString()+ ".jpg");

        fileref.putFile(uri).
                addOnSuccessListener(taskSnapshot ->
                        fileref.getDownloadUrl().addOnSuccessListener( uri -> {

                            String imageUrl = uri.toString();
                            saveDataToDataBase(name, price, description, ingredient, imageUrl);
                        })
                )
                .addOnFailureListener(e ->{
                    progressDialog.dismiss();
                    Toast.makeText(this, "Image upload failed!" + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void saveDataToDataBase(String name, String price,String description, String ingredients, String imageUrl){
        String key = databaseReference.push().getKey();

        HashMap<String, Object> itemMap = new HashMap<>();
        itemMap.put("id", key);
        itemMap.put("name", name);
        itemMap.put("price", price);
        itemMap.put("description", description);
        itemMap.put("ingredients", ingredients);
        itemMap.put("imageUrl", imageUrl);

        databaseReference.child(key).setValue(itemMap).addOnSuccessListener(aVoid -> {
            progressDialog.dismiss();
            Toast.makeText(this, "Item added Successfully!", Toast.LENGTH_SHORT).show();
            clearFields();
        }).addOnFailureListener(e -> {
            progressDialog.dismiss();
            Toast.makeText(this, "Failed to add item"+e.getMessage(), Toast.LENGTH_SHORT).show();

        });
    }

    private void clearFields(){
        itemName.setText("");
        itemPrice.setText("");
        descriptionOfFood.setText("");
        ingredientsOfFood.setText("");
        showImage.setImageResource(R.drawable.food);
        uri = null;
    }
}