package com.example.evaluaciont1jose;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentResults extends Fragment {
    TextView textViewPhaseFrag;
    TextView txtViewDate;
    TextView textViewTmHm;
    TextView textViewTmAw;
    TextView textViewGoalTmHm;
    TextView textViewGoalTmAw;
    String resCtry;
    int resNb;


    public static FragmentResults newInstance(String ctry, int res) {
        FragmentResults fr = new FragmentResults();

        Bundle args = new Bundle();
        args.putString("ctry", ctry);
        args.putInt("res", res);
        fr.setArguments(args);

        return fr;
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        resCtry = getArguments().getString("ctry");
        resNb = getArguments().getInt("res");
        View v = inflater.inflate(R.layout.fragment, container, false);
        textViewPhaseFrag= v.findViewById(R.id.textViewPhaseFrag);
        txtViewDate = v.findViewById(R.id.txtViewDate);
        textViewTmHm = v.findViewById(R.id.textViewTmHm);
        textViewTmAw = v.findViewById(R.id.textViewTmAw);
        textViewGoalTmHm = v.findViewById(R.id.textViewGoalTmHm);
        textViewGoalTmAw= v.findViewById(R.id.textViewGoalTmAw);

        ArrayList<Result> res = ResultList.getResult(resCtry);

        textViewPhaseFrag.setText(res.get(resNb).getPhase());
        txtViewDate.setText(res.get(resNb).getDate());
        textViewTmHm.setText(res.get(resNb).getTeamHm());
        textViewGoalTmHm.setText(String.valueOf(res.get(resNb).getGoaltmHm()));
        textViewTmAw.setText(res.get(resNb).getTeamAw());
        textViewGoalTmAw.setText(String.valueOf(res.get(resNb).getGoaltmAw()));

        return v;
    }
}
