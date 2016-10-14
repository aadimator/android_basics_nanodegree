package com.example.android.inventory;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    private Cursor cur;
    private String subProductName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        final InventoryDbHelper db = new InventoryDbHelper(this);
        Intent getListName = getIntent();
        String productName = getListName.getExtras().getString("listItem");
        int pos = productName.indexOf("\nQuantity");
        subProductName = productName.substring(0, pos);

        cur = db.getData(subProductName);

        if (cur.moveToFirst()) {

            // Set Product Name
            TextView tName = (TextView) findViewById(R.id.text_name);
            tName.setText(subProductName);

            // Set Price
            int price = cur.getInt(cur.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRICE));
            TextView tPrice = (TextView) findViewById(R.id.text_price);
            tPrice.setText("$" + price);

            // Set Quantity
            int quantity = getQuantity(cur);
            TextView tQuantity = (TextView) findViewById(R.id.text_quantity);
            tQuantity.setText("" + quantity);
        }

        // Decrease quantity by 1
        Button btnTrack = (Button) findViewById(R.id.track);
        btnTrack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cur.moveToFirst()) {
                    int quantity = getQuantity(cur);
                    if (quantity > 0) {
                        Log.i(LOG_TAG, "Quantity : " + quantity);
                        db.updateData(subProductName, quantity, -1);
                        cur = db.getData(subProductName);
                        quantity = getQuantity(cur);
                        Log.i(LOG_TAG, "Quantity : " + quantity);
                        TextView tQuantity = (TextView) findViewById(R.id.text_quantity);
                        tQuantity.setText("" + quantity);
                    } else {
                        Toast.makeText(DetailsActivity.this, "It's empty! Order Now!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // Increase quantity by 1
        Button btnReceive = (Button) findViewById(R.id.receive);
        btnReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cur.moveToFirst()) {
                    int quantity = getQuantity(cur);
                    Log.i(LOG_TAG, "Quantity : " + quantity);
                    db.updateData(subProductName, quantity, 1);
                    cur = db.getData(subProductName);
                    quantity = getQuantity(cur);
                    Log.i(LOG_TAG, "Quantity : " + quantity);
                    TextView tQuantity = (TextView) findViewById(R.id.text_quantity);
                    tQuantity.setText("" + quantity);
//                    Toast.makeText(DetailsActivity.this, "Refresh!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Order Now
        Button orderNow = (Button) findViewById(R.id.order);
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = "";
                if (cur.moveToFirst()) {
                    productName = cur.getString(cur.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_PRODUCT_NAME));
                }
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_TEXT, "In need of some " + productName);
                startActivity(Intent.createChooser(intent, "Send Email"));
            }
        });

        // delete row
        Button delete_ = (Button) findViewById(R.id.delete_data);
        delete_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case DialogInterface.BUTTON_POSITIVE:
                                if (db.deleteData(subProductName)) {
                                    Intent returnHome = new Intent(DetailsActivity.this, MainActivity.class);
                                    startActivity(returnHome);
                                    Toast.makeText(DetailsActivity.this, "Deleted!", Toast.LENGTH_SHORT).show();
                                }
                                break;

                            case DialogInterface.BUTTON_NEGATIVE:
                                break;
                        }
                    }
                };
                AlertDialog.Builder ab = new AlertDialog.Builder(DetailsActivity.this);
                ab.setMessage("Are you sure you want to delete?").setPositiveButton("Yes", dialogClickListener)
                        .setNegativeButton("No", dialogClickListener).show();
            }
        });


        // Convert byte array to bitmap and display the image
        ImageView img = (ImageView) findViewById(R.id.imageView);
        byte[] image = cur.getBlob(cur.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_IMAGE));
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        img.setImageBitmap(bitmap);
    }

    private int getQuantity(Cursor cur) {
        if (cur.moveToFirst()) {
            return cur.getInt(cur.getColumnIndex(InventoryContract.InventoryEntry.COLUMN_QUANTITY));
        }
        Log.e(LOG_TAG, "Cursor Problem");
        return -1;
    }
}
