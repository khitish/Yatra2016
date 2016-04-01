package com.mobisys.android.FirstJsonExUrl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class DeptArrayAdapter extends ArrayAdapter<DEPT_HOLD>{

	private Context context;
	ArrayList<DEPT_HOLD> dataObject;
	public DeptArrayAdapter(Context context, int textViewResourceId,
			ArrayList<DEPT_HOLD> dataObject) {
		super(context, textViewResourceId, dataObject);
		this.context=context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView=convertView;

		if(rowView==null){
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		


        }



        return rowView;
	}



	

}
