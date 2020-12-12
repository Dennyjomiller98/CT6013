# CT6013
CT6013 S1707031

<b>NOTE:</b><br>
Custom Logging is available, and is currently located under your glassfish configuration (domain1 in our example): <br><br>
<i><b>(glassfish location) \glassfish\domains\domain1\config </b></i><br><br>
and is located as "logOutput.log" <br><br>

The main page is SNAPSHOTDIR/jsp/databaseselection.jsp (e.g. http://localhost:8080/CT6013_S1707031_Source-1.0-SNAPSHOT/jsp/databaseselection.jsp when running on local glassfish server) <br><br>

MongoDB Database Creation Scripts can be found: <b>*SourceCodeDirectory*/src/main/resources/startupscripts/mongodb/</b> <br/>
Oracle Database Creation Scripts can be found: <b>*SourceCodeDirectory*/src/main/resources/startupscripts/oracle/</b>

For Teacher Access, one is created upon startup. Both databases have the same credentials:<br/>
Email: Teacher@teacher.com <br/>
Password: Teacher123 <br/><br/>

Two Students have also been created: <br/>
Email: Student@student.com <br/>
Password: Student123 <br/><br/>

and: <br/>
Email: Enrolled@Enrolled.com <br/>
Password: Student123 <br/><br/>

Students can register from the start, but only teachers have access to register new teachers (to prevent students gaining teacher prvivileges). <br/><br/>

Test Courses and Modules are also included, but Teachers have the option to add more when logged in.
