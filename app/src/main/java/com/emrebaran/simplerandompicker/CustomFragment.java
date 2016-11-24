package com.emrebaran.simplerandompicker;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * Created by mree on 24.11.2016.
 */

public class CustomFragment extends Fragment {
    View viewCustom;

    EditText edtAdd,edtHowMany;

    ListView listAdded,listSelected;

    ArrayList<String> array_added;
    ArrayList<String> array_selected;
    ArrayList<String> array_added_sequence;

    ArrayList<Integer> array_numbers;
    ArrayList<String> array_sequence;

    CheckBox chkClear;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewCustom = inflater.inflate(R.layout.fragment_custom, container, false);

        edtAdd = (EditText)viewCustom.findViewById(R.id.edtAdd);
        edtHowMany = (EditText)viewCustom.findViewById(R.id.edtHowMany);

        chkClear = (CheckBox)viewCustom.findViewById(R.id.chkClear);

        listAdded = (ListView)viewCustom.findViewById(R.id.listCustomAdded);
        listSelected = (ListView)viewCustom.findViewById(R.id.listCustomSelected);

        listAdded.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),array_added.get(position),Toast.LENGTH_SHORT).show();
            }
        });

        listSelected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),array_selected.get(position),Toast.LENGTH_SHORT).show();
            }
        });


        listAdded.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                array_added.remove(position);

                array_added_sequence = new ArrayList<String>();

                for(int s=0; s<array_added.size();s++) {

                    array_added_sequence.add(String.valueOf(s+1)+".");


                }

                ListAdapterLetters adapterx = new ListAdapterLetters(getActivity(), array_added_sequence, array_added);
                listAdded.setAdapter(adapterx);

                return false;
            }
        });


        array_added = new ArrayList<String>();
        array_added_sequence = new ArrayList<String>();

        ImageButton btnAdd = (ImageButton)viewCustom.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtAdd.getText().length()<1)
                    Toast.makeText(getContext(),getString(R.string.warning_blank),Toast.LENGTH_SHORT).show();
                else {
                    array_added.add(edtAdd.getText().toString());
                    array_added_sequence.add(String.valueOf(array_added.size())+".");

                    ListAdapterLetters adapterx = new ListAdapterLetters(getActivity(), array_added_sequence, array_added);
                    listAdded.setAdapter(adapterx);

                    if(chkClear.isChecked())
                        edtAdd.setText("");
                }



            }
        });


        FloatingActionButton btnRandomCustom = (FloatingActionButton)viewCustom.findViewById(R.id.btnRandomCustom);
        btnRandomCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtHowMany.getText().length()<1)
                    Toast.makeText(getContext(),getString(R.string.warning_blank),Toast.LENGTH_SHORT).show();
                else if(array_added.size()<1)
                    Toast.makeText(getContext(),getString(R.string.warning_blank),Toast.LENGTH_SHORT).show();
                else if(Integer.parseInt(edtHowMany.getText().toString())>array_added.size())
                    Toast.makeText(getContext(),getString(R.string.warning_range),Toast.LENGTH_SHORT).show();
                else
                    generateRandomNumber();
            }



        });


        RelativeLayout lyCustom = (RelativeLayout)viewCustom.findViewById(R.id.lyCustom);
        lyCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });


        return viewCustom;

    }


    public void generateRandomNumber()
    {
        int min = 0;
        int max = array_added.size()-1;

        int howMany = Integer.parseInt(edtHowMany.getText().toString());

        int range = max - min + 1;

        if (howMany > range) {
            Toast.makeText(getContext(), getString(R.string.warning_range), Toast.LENGTH_SHORT).show();

        } else {

            array_numbers = new ArrayList<Integer>();

            int i = 0;
            while (i < howMany) {
                Random r = new Random();
                int i1 = r.nextInt(range) + min;

                if (!contains(array_numbers, i1)) {
                    array_numbers.add(i1);
                    i++;
                } else
                    continue;

            }

            Collections.sort(array_numbers);


            array_sequence = new ArrayList<String>();
            array_selected = new ArrayList<String>();

            for(int s=0; s<array_numbers.size();s++) {

                array_sequence.add(String.valueOf(s+1) + ".");
                array_selected.add(array_added.get(array_numbers.get(s)));

            }


            hideKeyboard();

            ListAdapterLetters adapterx = new ListAdapterLetters(getActivity(), array_sequence, array_selected);
            listSelected.setAdapter(adapterx);

        }
    }

    public boolean contains(final List<Integer> array, final int key) {

        return array.contains(key);

    }

    private void hideKeyboard()
    {
        try {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.d("custom",String.valueOf(e));
        }

    }

}