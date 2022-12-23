package com.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.R;
import com.entities.Result;

public class FragmentResults extends Fragment {
    Result res;
    TextView textViewPhaseFrag;
    TextView txtViewDate;
    TextView textViewTmHm;
    TextView textViewTmAw;
    TextView textViewGoalTmHm;
    TextView textViewGoalTmAw;


    public static FragmentResults newInstance(String country) {
        FragmentResults fr = new FragmentResults();
        Bundle args = new Bundle();
        args.putString("country", country);
        fr.setArguments(args);
        return fr;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String country = getArguments().getString("country");

        //TODO: IMPLEMENT DATABASE CALL TO GET THE RESULTS OF THE COUNTRY
        //res = resultList.getResult();


        View v = inflater.inflate(R.layout.fragment, container, false);
        textViewPhaseFrag= v.findViewById(R.id.textViewPhaseFrag);
        txtViewDate = v.findViewById(R.id.txtViewDate);
        textViewTmHm = v.findViewById(R.id.textViewTmHm);
        textViewTmAw = v.findViewById(R.id.textViewTmAw);
        textViewGoalTmHm = v.findViewById(R.id.textViewGoalTmHm);
        textViewGoalTmAw= v.findViewById(R.id.textViewGoalTmAw);



        textViewPhaseFrag.setText(res.getPhase());
        txtViewDate.setText(res.getDate());
        textViewTmHm.setText(res.getTeamHm());
        textViewGoalTmHm.setText(String.valueOf(res.getGoaltmHm()));
        textViewTmAw.setText(res.getTeamAw());
        textViewGoalTmAw.setText(String.valueOf(res.getGoaltmAw()));
        return v;
    }
}
