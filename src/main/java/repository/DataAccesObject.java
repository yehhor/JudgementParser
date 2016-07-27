package repository;

import model.Judge;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


/**
 * Created by yehor on 29.06.2016.
 */
public class DataAccesObject {

    private static DataAccesObject dao;

    private DataAccesObject() {
        flushDB();
    }

    public static DataAccesObject getInstance() {
        if (dao == null)
            dao = new DataAccesObject();
        return dao;
    }

    private ResourceBundle bundle = ResourceBundle.getBundle("db/dbProp");

    private final String URL = bundle.getString("db.url");
    private final String username = bundle.getString("db.username");
    private final String password = bundle.getString("db.password");


    private String createStringSql()
    {
        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(bundle.getString("db.init")))));
            String curLine;
            while ((curLine = reader.readLine()) != null){
                builder.append(curLine).append("\r\n");
            }
        }catch (IOException e)
        {
            System.out.println("exception creatingSQLString");
            e.printStackTrace();
        }
        return builder.toString();
    }


    private void flushDB()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(URL, username, password);
            if (con == null) throw new Exception("No DB CONNECTION");
            Statement st = con.createStatement();
            st.execute("DROP TABLE IF EXISTS `judges`");
            st.execute(createStringSql());
            if (st != null) st.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("FlushDB EXCEPTION");
            e.printStackTrace();
            System.out.println();
        }
    }
    private Connection con = null;

    public void save(Judge judge) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(URL, username, password);
            if (con == null) throw new Exception("No DB CONNECTION");
            Statement st = con.createStatement();
            String sql = String.format("INSERT INTO judges (name, email, adress, phone, url, schedule_MON_TH," +
                    " schedule_FR, schedule_BREAK) VALUES " +
                            "  ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s')", judge.getName(), judge.getEmail(), judge.getAdress(),
                    judge.getPhone(), judge.getUrl(), judge.getSchedule_MON_TH(), judge.getSchedule_FR(), judge.getSchedule_BREAK());
            st.executeUpdate(sql);
            if (st != null) st.close();
            if (con != null) con.close();
        } catch (Exception e) {
            System.out.println("JDBC EXCEPTION");
            e.printStackTrace();
            System.out.println(judge.getUrl());
            System.out.println();
        }
    }

}


