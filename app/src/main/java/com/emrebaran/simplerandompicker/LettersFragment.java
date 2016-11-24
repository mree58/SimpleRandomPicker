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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * Created by mree on 24.11.2016.
 */

public class LettersFragment extends Fragment {
    View viewLetters;

    EditText edtLetters,edtHowMany;

    ArrayList<Integer> array_numbers;
    ArrayList<String> array_sequence;
    ArrayList<String> array_letters;

    ListView listLetters;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewLetters = inflater.inflate(R.layout.fragment_letters, container, false);

        edtLetters = (EditText)viewLetters.findViewById(R.id.edtLetters);
        edtHowMany = (EditText)viewLetters.findViewById(R.id.edtHowMany);

        listLetters = (ListView)viewLetters.findViewById(R.id.listLetters);
        listLetters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(),array_letters.get(position),Toast.LENGTH_SHORT).show();

            }
        });
        FloatingActionButton btnRandomLetter = (FloatingActionButton)viewLetters.findViewById(R.id.btnRandomLetter);
        btnRandomLetter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(edtHowMany.getText().length()<1)
                    Toast.makeText(getContext(),getString(R.string.warning_blank),Toast.LENGTH_SHORT).show();
                else if(Integer.parseInt(edtHowMany.getText().toString())>edtLetters.getText().length())
                    Toast.makeText(getContext(),getString(R.string.warning_range),Toast.LENGTH_SHORT).show();
                else
                    edtLetters.setText(eleminateDuplicateLetters(edtLetters.getText().toString()));

            }



        });



        RelativeLayout lyLetters = (RelativeLayout)viewLetters.findViewById(R.id.lyLetters);
        lyLetters.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard();
            }
        });

        return viewLetters;
    }

    private String eleminateDuplicateLetters(String s)
        {
            s = s.replace(" ","");

            char[] chars = s.toCharArray();
            Set<Character> charSet = new LinkedHashSet<Character>();
            for (char c : chars) {
                charSet.add(c);
            }

            StringBuilder sb = new StringBuilder();
            for (Character character : charSet) {
                sb.append(character);
            }


            generateRandomNumber(sb.toString());

            return sb.toString();

        }


    public void generateRandomNumber(String sLetters)
    {
        int min = 0;
        int max = sLetters.length()-1;

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
            array_letters = new ArrayList<String>();

            char[] chars = sLetters.toCharArray();


            for(int s=0; s<array_numbers.size();s++) {

                array_sequence.add(String.valueOf(s+1) + ".");
                array_letters.add(String.valueOf(chars[array_numbers.get(s)]));

            }

            hideKeyboard();

            ListAdapterLetters adapterx = new ListAdapterLetters(getActivity(), array_sequence, array_letters);
            listLetters.setAdapter(adapterx);


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
            Log.d("letters",String.valueOf(e));
        }

    }

}