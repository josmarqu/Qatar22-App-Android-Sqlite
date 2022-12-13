package com.example.evaluaciont1jose.dbManager;

import android.content.Context;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Calendar;

public class DbManager{
    private Context context;

    public DbManager(Context context) {
        this.context = context;
    }
    public Connection connection() {
        Connection con = null;

        File filesDir = context.getFilesDir();
        File h2Database = new File(filesDir, "bd/mi_base_de_datos.h2.db");
        String h2DatabasePath = h2Database.getPath();
        try {
            Class.forName("org.h2.Driver");
            con = DriverManager.getConnection("jdbc:h2:"+h2DatabasePath);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }
}
