package com.emrebaran.simplerandompicker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapterLetters extends ArrayAdapter<String>{


	private final Activity _context;
	private final ArrayList<String> _sequence;
	private final ArrayList<String> _letters;



	public ListAdapterLetters(Activity context, ArrayList<String> sequence, ArrayList<String> letters) {
	super(context, R.layout.custom_list_layout,sequence);
	this._context = context;
	this._sequence = sequence;
	this._letters = letters;
	}
	 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	 
	LayoutInflater inflater = _context.getLayoutInflater();
	View rowView = inflater.inflate(R.layout.custom_list_layout, null, true);
	
	TextView txtTitle = (TextView) rowView.findViewById(R.id.item_sequence);
	TextView txtTitle2 = (TextView) rowView.findViewById(R.id.item_number);

		switch(position%2){
			case 0:
				rowView.setBackgroundDrawable(_context.getResources().getDrawable(R.drawable.list_item_selector_light));
				break;
			case 1:
				rowView.setBackgroundDrawable(_context.getResources().getDrawable(R.drawable.list_item_selector_dark));
				break;
			default :
				rowView.setBackgroundDrawable(_context.getResources().getDrawable(R.drawable.list_item_selector_dark));
				break;
		}

	txtTitle.setText(_sequence.get(position));
	txtTitle2.setText(_letters.get(position));

	return rowView;
	}

}
