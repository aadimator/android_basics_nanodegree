package com.example.android.inventory;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Add Product Activity
 */
public class AddProductActivity extends AppCompatActivity {

    private int REQUEST_IMAGE_UPLOAD = 1;
    byte[] image;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_UPLOAD && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Log.v("Path:", selectedImageUri.toString());
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                Bitmap scaledDown = scaleDown(bitmap, 250, true);

                ImageView testImage = (ImageView) findViewById(R.id.testImage);
                testImage.setImageBitmap(scaledDown);

                // Convert Bitmap to byte array
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                scaledDown.compress(Bitmap.CompressFormat.PNG, 0, stream);
                image = stream.toByteArray();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to get image", Toast.LENGTH_SHORT).show();
            }
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        final InventoryDbHelper db = new InventoryDbHelper(this);
        Button add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextProductName = (EditText) findViewById(R.id.product_name);
                EditText editTextQuantity = (EditText) findViewById(R.id.quantity);
                EditText editTextPrice = (EditText) findViewById(R.id.price);
                String productName = editTextProductName.getText().toString();
                if (productName.matches("")) {
                    Toast.makeText(AddProductActivity.this, "Please enter a Product Name.", Toast.LENGTH_SHORT).show();
                    return;
                }
                String sQuantity = editTextQuantity.getText().toString();
                if (sQuantity.matches("")) {
                    Toast.makeText(AddProductActivity.this, "Please enter quantity.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int quantity = Integer.parseInt(sQuantity);
                String sPrice = editTextPrice.getText().toString();
                if (sPrice.matches("")) {
                    Toast.makeText(AddProductActivity.this, "Please enter price.", Toast.LENGTH_SHORT).show();
                    return;
                }
                int price = Integer.parseInt(sPrice);
                // Check if image has been set or not
                if (image == null) {
                    Toast.makeText(AddProductActivity.this, "Please set an image.", Toast.LENGTH_SHORT).show();
                    return;
                }
                db.insertData(productName, quantity, price, image);
                Intent mainIntent = new Intent(AddProductActivity.this, MainActivity.class);
                startActivity(mainIntent);
                String message = "Product Added!";
                Toast.makeText(AddProductActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

        // Capture Image
        Button addImage = (Button) findViewById(R.id.addImage);
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                // Show only images, no videos or anything else
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                // Always show the chooser (if there are multiple options available)
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });

    }

    public static Bitmap scaleDown(Bitmap realImage, float maxImageSize, boolean filter) {
        float ratio = Math.min(
                (float) maxImageSize / realImage.getWidth(),
                (float) maxImageSize / realImage.getHeight());
        int width = Math.round((float) ratio * realImage.getWidth());
        int height = Math.round((float) ratio * realImage.getHeight());

        Bitmap newBitmap = Bitmap.createScaledBitmap(realImage, width,
                height, filter);
        return newBitmap;
    }
}
