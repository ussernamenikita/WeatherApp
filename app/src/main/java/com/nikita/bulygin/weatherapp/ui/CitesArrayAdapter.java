package com.nikita.bulygin.weatherapp.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.nikita.bulygin.weatherapp.domain.entities.City;

import java.util.ArrayList;
import java.util.List;


public class CitesArrayAdapter extends BaseAdapter implements Filterable {

    private final int itemRes;
    private LayoutInflater inflater;

    private List<City> data = new ArrayList<>();

    public CitesArrayAdapter(Context context, int drop_down_item_city) {
        this.inflater = LayoutInflater.from(context);
        this.itemRes = drop_down_item_city;
    }


    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public String getItem(int position) {
        if(position >= data.size()){
            return "";
        }
        City city = data.get(position);
        return city.getName()+", "+city.getCountryCode();
    }

    public City getCity(int position){
        return  position < data.size() ? data.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return data.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        @SuppressLint("ViewHolder") View view = convertView == null ? inflater.inflate(itemRes, parent, false) : convertView;
        City city = position < data.size() ? data.get(position) : null;
        if (city != null && view instanceof TextView) {
            String text = city.getName() + ", " + city.getCountryCode();
            ((TextView) view).setText(text);
        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                return null;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

            }
        };
    }

    public void setData(@NonNull List<City> citiesList) {
        this.data = citiesList;
        notifyDataSetChanged();
    }
}
