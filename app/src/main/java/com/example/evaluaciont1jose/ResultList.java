package com.example.evaluaciont1jose;

import java.util.ArrayList;

public class ResultList {
    private static ArrayList<Result> listRes = new ArrayList<Result>() {{
        add(new Result("Group Stage", "20/11/2022 17:00", "Qatar", 1, "Ecuador", 1));
        add(new Result("Group Stage", "21/11/2022 14:00", "England", 1, "Iran", 0));
        add(new Result("Group Stage", "21/11/2022 17:00", "Senegal", 3, "Netherlands", 0));
        add(new Result("Group Stage", "21/11/2022 20:00", "EEUU", 1, "Wales", 2));
        add(new Result("Group Stage", "22/11/2022 11:00", "Argentina", 3, "Saudi Arabia", 1));
        add(new Result("Group Stage", "22/11/2022 14:00", "Denmark", 1, "Tunisia", 0));
        add(new Result("Group Stage", "22/11/2022 17:00", "Mexico", 3, "Polond", 2));
        add(new Result("Group Stage", "22/11/2022 20:00", "France", 1, "Australia", 0));
        add(new Result("Group Stage", "23/11/2022 11:00", "Morocco", 4, "Croatia", 1));
        add(new Result("Group Stage", "23/11/2022 14:00", "Germany", 0, "Japan", 1));
        add(new Result("Group Stage", "23/11/2022 17:00", "Spain", 0, "Costa Rica", 1));
        add(new Result("Group Stage", "23/11/2022 20:00", "Belgium", 1, "Canada", 3));
        add(new Result("Group Stage", "24/11/2022 11:00", "Switzerland", 3, "Camerun", 0));
        add(new Result("Group Stage", "24/11/2022 14:00", "Uruguay", 1, "Corea del sur", 3));
        add(new Result("Group Stage", "24/11/2022 17:00", "Portugal", 1, "Ghana", 1));
        add(new Result("Group Stage", "24/11/2022 20:00", "Brazil", 0, "Serbia", 1));
        add(new Result("Group Stage", "25/11/2022 11:00", "Wales", 3, "Iran", 1));
        add(new Result("Group Stage", "25/11/2022 14:00", "Qatar", 0, "Senegal", 1));
        add(new Result("Group Stage", "25/11/2022 17:00", "Netherlands", 3, "Ecuador", 2));
        add(new Result("Group Stage", "25/11/2022 20:00", "England", 1, "EEUU", 1));
        add(new Result("Group Stage", "26/11/2022 11:00", "Tunisia", 3, "Australia", 1));
        add(new Result("Group Stage", "26/11/2022 14:00", "Poland", 1, "Saudi Arabia", 1));
        add(new Result("Group Stage", "26/11/2022 17:00", "France", 1, "Denmark", 1));
        add(new Result("Group Stage", "26/11/2022 20:00", "Argentina", 3, "Mexico", 1));
        add(new Result("Group Stage", "27/11/2022 11:00", "Japan", 2, "Costa Rica", 1));
        add(new Result("Group Stage", "27/11/2022 14:00", "Belgium", 1, "Morocco", 0));
        add(new Result("Group Stage", "27/11/2022 20:00", "Croatia", 3, "Canada", 2));
        add(new Result("Group Stage", "27/11/2022 11:00", "Spain", 1, "Germany", 0));

    }};




    public static ArrayList<Result> getResult(String ctry) {
        ArrayList<Result> listResCtry = new ArrayList<>();
        for (Result res : listRes) {
            if (res.getTeamHm().trim().toLowerCase().equals(ctry) || res.getTeamAw().trim().toLowerCase().equals(ctry)) {
                listResCtry.add(res);
            }
        }
        return listResCtry;
    }
}
