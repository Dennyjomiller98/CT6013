/*MONGODB*/
/*Use This Script To Create Database/Collections used for the Application, and auto-populate certain fields */
use db_ct6013_s1707031;
db.students.insertOne({
    "Student_ID":"s0000001",
    "First_Name":"FirstNameTest",
    "Surname":"SurnameTest",
    "Email":"EmailTest@test.com",
    "DOB":"1990-01-11T00:00:00.000+00:00",
    "Address":"AddressLine1CSVSplit,AddressLine2CSVSplit,CountyCSVSplit,PostcodeCSVSplit",
    "Is_Enrolled":"true",
});
//Needs to insertOne for db.teachers / db.modules / db.courses / db.marks