package com.century.db;

import lombok.NonNull;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PgDbService {
    private String url;
    private String user;
    private String pass;

    public PgDbService(String url, final String user, final String pass) {
        this.url = url;
        this.user = user;
        this.pass = pass;
    }

    private String createTableQuery = "CREATE TABLE logging.konnektor_recover_job_log (id serial, " +
            "date_run TIMESTAMP(0) WITHOUT TIME ZONE default now(), result integer);";

    private String checkLogTableExistQuery = "select distinct 1 as log_tbl_exst from pg_tables " +
            "where tablename = 'konnektor_recover_job_log' and schemaname = 'logging';";

    public Statement getPgStatement(final String url, final String user, final String pass) {
        Statement statement = null;
        try {
            statement = DriverManager.getConnection(url, user, pass).createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public boolean checkKonnetkorRecoverLogTable(@NonNull Statement statement) throws SQLException {
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(checkLogTableExistQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int result = 0;
        while (resultSet.next()) {
            result = resultSet.getInt("log_tbl_exst");
        }
        return result == 1;
    }

    public void insertKonnektorRecoverLog(@NonNull Statement statement, int result) {
        String insertNewLogResult = "INSERT INTO logging.konnektor_recover_job_log (result) VALUES (" + result + ");";
        try {
            statement.execute(insertNewLogResult);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
