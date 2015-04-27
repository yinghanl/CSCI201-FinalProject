# CSCI201-FinalProject

To Run this Game:
1.
You will first need to change the database information in DataBaseUtils to the information of the MySQL instance that you want to work in
  Edit the function 
  DataBaseUtils.getConnectionString();
  Currently it looks like:
  return("jdbc:mysql://localhost/towerdefense?user=root&password=yourpass");
  Enter your user and password if applicable.  
    If no password, delete everything after "user=root"
    
  In this instance of MySQL, run the script script/CreateDatabaseAndTable.sql
  Optional: If you want sample users run the script script/TestPopulation.sql
  
2.
Run the file InitializeGameServer.java

3. 
For each client, run the file createClient.java
  
