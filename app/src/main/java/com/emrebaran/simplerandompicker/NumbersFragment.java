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
import android.widget.EditText;
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

public class NumbersFragment extends Fragment {
    View viewNumbers;
    EditText edtMin,edtMax,edtHowMany;
    ArrayList<Integer> array_numbers;
    ArrayList<String> array_sequence;

    ListView listNumbers;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewNumbers = inflater.inflate(R.layout.fragment_numbers, container, false);


        edtMin = (EditText)viewNumbers.findViewById(R.id.edtMin);
        edtMax = (EditText)viewNumbers.findViewById(R.id.edtMax);
        edtHowMany = (EditText)viewNumbers.findViewById(R.id.edtHowMany);

        listNumbers = (ListView)viewNumbers.findViewById(R.id.listNumbers);
        listNumbers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),String.valueOf(array_numbers.get(position)),Toast.LENGTH_SHORT).show();

            }
        });


        FloatingActionButton btnRandomNumber = (FloatingActionButton)viewNumbers.findViewById(R.id.btnRandomNumber);
        btnRandomNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtMin.getText().length()<1 || edtMax.getText().length()<1 || edtHowMany.getText().length()<1)
                    Toast.makeText(getContext(),getString(R.string.warning_blank),Toast.LENGTH_SHORT).show();
                else {
                    hideKeyboard();
                    generateRandomNumber();
                }

            }



        });



        RelativeLayout lyNumbers = (RelativeLayout)viewNumbers.findViewById(R.id.lyNumbers);
        lyNumbers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

            return viewNumbers;

    }



    public boolean contains(final List<Integer> array, final int key) {

        return array.contains(key);

    }


    public void generateRandomNumber()
    {
        int min =Integer.parseInt(edtMin.getText().toString());
        int max = Integer.parseInt(edtMax.getText().toString());

        int howMany = Integer.parseInt(edtHowMany.getText().toString());


        if (min > max) {
            edtMin.setText(String.valueOf(max));
            edtMax.setText(String.valueOf(min));

            min = Integer.parseInt(edtMin.getText().toString());
            max = Integer.parseInt(edtMax.getText().toString());

        }

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
            for(int s=1; s<=array_numbers.size();s++)
                array_sequence.add(String.valueOf(s)+".");



            ListAdapterNumbers adapterx = new ListAdapterNumbers(getActivity(), array_sequence, array_numbers);
            listNumbers.setAdapter(adapterx);

        }
    }


    private void hideKeyboard()
    {
        try {
            InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            Log.d("numbers",String.valueOf(e));
        }

    }

}