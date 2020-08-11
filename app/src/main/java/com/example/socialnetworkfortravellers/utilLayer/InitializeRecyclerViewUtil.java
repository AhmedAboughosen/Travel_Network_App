package com.example.socialnetworkfortravellers.utilLayer;

import android.content.Context;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class InitializeRecyclerViewUtil {

    public static void initVerticalRecyclerView(RecyclerView recyclerView, Context context, RecyclerView.Adapter adapter) {
        try {
            // set main_logo LinearLayoutManager with default vertical orientation
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            // call the constructor of CustomAdapter to send the reference and data to Adapter
            recyclerView.setAdapter(adapter); // set the Adapter to RecyclerView
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void initVerticalRecyclerViewWithLine(RecyclerView recyclerView, Context context, RecyclerView.Adapter adapter) {
        try {

            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);

            // set main_logo LinearLayoutManager with default vertical orientation
            recyclerView.setLayoutManager(linearLayoutManager);

            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);

            // call the constructor of CustomAdapter to send the reference and data to Adapter
            recyclerView.setAdapter(adapter); // set the Adapter to RecyclerView


        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void initVerticalGridLayoutRecyclerView(RecyclerView recyclerView, Context context, RecyclerView.Adapter adapter, int number) {
        try {
            // set a GridLayoutManager with default vertical orientation and 2 number of columns
            GridLayoutManager gridLayoutManager = new GridLayoutManager(context, number);

            // set main_logo LinearLayoutManager with default vertical orientation
            recyclerView.setLayoutManager(gridLayoutManager);

            // call the constructor of CustomAdapter to send the reference and data to Adapter
            recyclerView.setAdapter(adapter); // set the Adapter to RecyclerView
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    public static void initHORIZONTALRecyclerView(RecyclerView recyclerView, Context context, RecyclerView.Adapter adapter) {
        try {
            LinearLayoutManager layoutManager
                    = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

            // set main_logo LinearLayoutManager with HORIZONTAL orientation
            recyclerView.setLayoutManager(layoutManager);

            // call the constructor of CustomAdapter to send the reference and data to Adapter
            recyclerView.setAdapter(adapter); // set the Adapter to RecyclerView
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
