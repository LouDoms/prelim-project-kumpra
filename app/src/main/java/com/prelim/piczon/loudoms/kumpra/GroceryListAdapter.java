package com.prelim.piczon.loudoms.kumpra;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by LouDoms on 7/1/2015.
 */
public class GroceryListAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<GroceryList> groceryList;
    private Context context;

    public GroceryListAdapter(ArrayList<GroceryList> groceryList, Context context) {
        this.groceryList = groceryList;
        this.context = context;

        inflater = LayoutInflater.from(context);
    }


    @Override
    public int getCount() {
        return groceryList.size();
    }

    @Override
    public Object getItem(int position) {
        return groceryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        TextView item;
        TextView quantity;

        view = inflater.inflate(R.layout.item_view, null);

        item = (TextView) view.findViewById(R.id.txtItem);
        quantity = (TextView) view.findViewById(R.id.txtQuantity);

        item.setText(groceryList.get(position).getItem());
        quantity.setText(groceryList.get(position).getQuantity());


        return view;
    }
}
