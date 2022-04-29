# Property Plus

Property Plus is a CRUD application to help keep track of properties, owners, agents and reservations in a convenient to use package. 

Made for ICS 499 at Metropolitan State University to better understand design patterns and how to use GitHub as a team. 

<img src="Group7Proj/src/main/resources/images/Property.jpg" width="600" height="600">

## Release (Jar)
############## \
The standalone jar will still require the database to be up and running.

## Installation Development Environment (Local)
Developed using Windows and ran in local environments.  Launch from your preferred IDE.
### Windows
 #### Software needed
  * [Java 11](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)
  * Local server ([MySQL Server](https://dev.mysql.com/downloads/mysql/)).
  * Install the database ([MySQL Workbench](https://www.mysql.com/products/workbench/) or CMD) 
  * IDE (Ex. [IntelliJ IDEA](https://www.jetbrains.com/idea/download/#section=windows))

#### Steps
1. Add sqlFile.sql to MySQL workbench from the 'resources/sql' folder to install database. (Or your preferred way to run a database).
2. By default, the local MySQL server should be running. (If not, run this command in the CMD - "net start mysql") 
3. Either clone or download and load into your preferred IDE to run. 
4. Look at the sqlFile.sql to see the passwords of the accounts. The admin account has full control over the program while the other accounts are limited. 
  
