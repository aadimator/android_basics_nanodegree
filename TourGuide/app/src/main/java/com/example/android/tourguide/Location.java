package com.example.android.tourguide;

/**
 * Created by Aadam on 9/29/2016.
 */

public class Location {

    private int mNameId;
    private int mAddressId;

    private int mImageResourceId = NO_IMAGE_PROVIDED;

    private static final int NO_IMAGE_PROVIDED = -1;

    public Location(int nameId, int addressId) {
        mNameId = nameId;
        mAddressId = addressId;
    }

    public Location(int nameId,int addressId, int imageResourceId) {
        mNameId = nameId;
        mAddressId = addressId;
        mImageResourceId = imageResourceId;
    }

    public int getNameId() {
        return mNameId;
    }

    public int getAddressId() {
        return mAddressId;
    }

    public int getImageResourceId() {
        return mImageResourceId;
    }

    public boolean hasImage() {
        return mImageResourceId != NO_IMAGE_PROVIDED;
    }
}
