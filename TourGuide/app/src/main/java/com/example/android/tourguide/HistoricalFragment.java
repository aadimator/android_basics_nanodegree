package com.example.android.tourguide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by Aadam on 9/29/2016.
 */

public class HistoricalFragment extends Fragment {
    public HistoricalFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.location_list, container, false);

        ArrayList<Location> locations = new ArrayList<Location>();
        locations.add(new Location(R.string.pakistan_monument, R.string.pakistan_monument_address,
                R.drawable.pakistan_monument));
        locations.add(new Location(R.string.rawal_lake, R.string.rawal_lake_address,
                R.drawable.rawal_lake));
        locations.add(new Location(R.string.shah_faisal, R.string.shah_faisal_address,
                R.drawable.shah_faisal));
        locations.add(new Location(R.string.lake_view_park, R.string.lake_view_park_address,
                R.drawable.lake_view_park));

        LocationAdapter locationAdapter = new LocationAdapter(getActivity(), locations);
        ListView listView = (ListView) rootView.findViewById(R.id.list);
        listView.setAdapter(locationAdapter);

        return rootView;
    }
}
