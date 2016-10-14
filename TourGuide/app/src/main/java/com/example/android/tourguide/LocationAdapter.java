package com.example.android.tourguide;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Aadam on 9/29/2016.
 */

public class LocationAdapter extends ArrayAdapter<Location> {

    public LocationAdapter(Context context, ArrayList<Location> locations) {
        super(context, 0, locations);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        Location currentLocation = getItem(position);

        TextView locationName = (TextView) listItemView.findViewById(R.id.locationName);
        locationName.setText(currentLocation.getNameId());

        TextView locationAddress = (TextView) listItemView.findViewById(R.id.locationAddress);
        locationAddress.setText(currentLocation.getAddressId());

        ImageView locationImage = (ImageView) listItemView.findViewById(R.id.locationImage);
        if (currentLocation.hasImage()) {
            locationImage.setImageResource(currentLocation.getImageResourceId());
        } else {
            locationImage.setImageResource(R.drawable.default_image);
        }

        return listItemView;
    }
}
