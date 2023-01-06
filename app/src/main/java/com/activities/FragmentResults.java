package com.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.R;
import com.dbManager.DbManager;
import com.entities.Result;

public class FragmentResults extends Fragment {
    TextView textViewPhaseFrag;
    TextView txtViewDate;
    TextView textViewTmHm;
    TextView textViewTmAw;
    TextView textViewGoalTmHm;
    TextView textViewGoalTmAw;


    public static FragmentResults newInstance(String country, int matchNb) {
        FragmentResults fr = new FragmentResults();
        Bundle args = new Bundle();
        args.putString("country", country);
        args.putInt("matchNb", matchNb);
        fr.setArguments(args);
        return fr;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String country = getArguments().getString("country");
        int matchNb = getArguments().getInt("matchNb");

        DbManager dbManager = new DbManager(getContext());
        Result res = dbManager.getResult(country, matchNb);

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
        textViewGoalTmHm.setText(String.valueOf(res.getGoalTmHm()));
        textViewTmAw.setText(res.getTeamAw());
        textViewGoalTmAw.setText(String.valueOf(res.getGoalTmAw()));
        return v;
    }
}
