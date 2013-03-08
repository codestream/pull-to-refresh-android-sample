package com.example.pulltorefreshsample;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CarsAdapter extends ArrayAdapter<Cars>{
	private Context mContext;
	private List<Cars> mCars;
	private LayoutInflater mLayoutInflater;

	public CarsAdapter(Context context, int resource,
			ArrayList<Cars> objects) {
		super(context, resource, objects);
		this.mContext = context;
		this.mCars = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final CarsHolder carsHolder = new CarsHolder();
		if(view == null){
			mLayoutInflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = mLayoutInflater.inflate(R.layout.cars_row_item, null);
		}
		
		final Cars cars = mCars.get(position);
		
		if(cars != null){
			carsHolder.mCarId = (TextView)view.findViewById(R.id.text_car_id);
			carsHolder.mCarName = (TextView)view.findViewById(R.id.text_car_name);
			carsHolder.mCarURL = (TextView)view.findViewById(R.id.text_car_url);
			carsHolder.mCarIcon = (ImageView)view.findViewById(R.id.view_car_image);
			carsHolder.mCarId.setText(cars.getCarId());
			carsHolder.mCarName.setText(cars.getCarName());
			carsHolder.mCarURL.setText(cars.getCarUrl());
			carsHolder.mCarIcon.setImageBitmap(cars.getCarImage());
		}
		return view;
	}
	
	private class CarsHolder {
		TextView mCarId;
		TextView mCarName;
		TextView mCarURL;
		ImageView mCarIcon;
	}
}
