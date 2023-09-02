### Simple Java Command Line Unitility for testing DB Connectivity 

Sample Usage: `java -jar MSSQLConnectionTester-1.0.jar -u  <dbURL> -s <Select SQL that will be executed again the DB>`

Help:

    iVi Quick DB Connection Tester 2023
    Missing required options: u, s
    usage: Usage:
    -s,--sql <sql>     SQL to Execute
    -u,--url <dbURL>   DB URL to Execute

MS SQL JDBC Drive is included in the provided .jar file. Any other JDBC Driver needs to be included in the `CLASS_PATH`
