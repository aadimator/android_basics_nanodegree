package com.example.android.inventory;

import android.provider.BaseColumns;

/**
 * Inventory Contract Class
 */
public class InventoryContract {

    /*
        Inner class that defines the contents of the Inventory
     */
    public static final class InventoryEntry implements BaseColumns {

        public static final String COLUMN_ID = "id";
        public static final String TABLE_NAME = "inventory";

        public static final String COLUMN_PRODUCT_NAME = "name";

        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_IMAGE = "image";
    }
}
