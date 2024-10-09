# FleetDryRun_postGreSQL_springBoot_JSONB_v2 
Inserting the data in the JsonB type column in postgreSQL using Spring Boot and retrieving the data from the Table By using Api. We can also get the data from database in the file (.pdf, .txt, .xlsx) In this format we can get the data. </br>

*CURLS*

*****CURL FOR INSERTING THE DATA**** </br>
curl --location 'http://localhost:8080/api/data' \
--header 'Content-Type: application/json' \
--data '{
  "id": "Student6",
  "admitYear": "2024",
  "address": {
    "postCode": "456789",
    "city": "Mysuru"
  }
}'   </br>

*****CURL FOR RETRIEVING THE DATA**** </br>

curl --location 'http://localhost:8080/api/data' </br>

*****CURL FOR RETRIEVING THE DATA AND SAVING THE DATA IN REQUIRED FORMAT (.pdf , .xlsx, .txt)**** </br>

**For text File**  </br>
curl --location 'http://localhost:8080/api/data/export?format=txt'     </br>

**For PDF File**  </br>
curl --location 'http://localhost:8080/api/data/export?format=pdf'    </br>

**For Excel File**  </br>
curl --location 'http://localhost:8080/api/data/export?format=xlsx'    </br>

#The File will get stored in the project directory by default if you want to change it you can change in the service layer file
