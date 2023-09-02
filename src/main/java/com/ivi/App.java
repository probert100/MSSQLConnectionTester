package com.ivi;
import java.sql.*;

import org.apache.commons.cli.*;


public class App
{
    public static void checkConnection(String connectionUrl, String sql) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection(connectionUrl);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
                System.out.println("Executing SQL Query now: " + sql);
                runSQLQuery(sql, conn);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

     public static void runSQLQuery(String sql, Connection conn) {
         ResultSet resultSet = null;
         try (Statement statement = conn.createStatement();)
         {
             resultSet = statement.executeQuery(sql);
             ResultSetMetaData rsmd = resultSet.getMetaData();
             int columnsNumber = rsmd.getColumnCount();
             while (resultSet.next()) {
                 for (int i = 1; i <= columnsNumber; i++) {
                     if (i > 1) System.out.print(",  ");
                     String columnValue = resultSet.getString(i);
                     System.out.print(columnValue + " " + rsmd.getColumnName(i));
                 }
                 System.out.println("");
             }
         }
         catch (SQLException e) {
             e.printStackTrace();
         }
     }


    public static void main( String[] args )
    {
        System.out.println( "iVi Quick DB Connection Tester 2023" );
        Options options = new Options();

        Option dbURl = Option.builder("u").longOpt("url")
                .argName("dbURL")
                .hasArg()
                .required(true)
                .desc("DB URL to Execute").build();
        options.addOption(dbURl);

        Option sql = Option.builder("s").longOpt("sql")
                .argName("sql")
                .hasArg()
                .required(true)
                .desc("SQL to Execute").build();
        options.addOption(sql);

        // define parser
        CommandLine cmd;
        CommandLineParser parser = new BasicParser();
        HelpFormatter helper = new HelpFormatter();

        try {
            cmd = parser.parse(options, args);
            if(cmd.hasOption("u") &&  cmd.hasOption("s") ) {
                String opt_sql = cmd.getOptionValue("sql");
                String opt_url = cmd.getOptionValue("url");
                System.out.println("dbURL set to: " + opt_url);

                checkConnection(opt_url, opt_sql);
            }


        } catch (ParseException e) {
            System.out.println(e.getMessage());
            helper.printHelp("Usage:", options);
            System.exit(0);
        }


    }

}
