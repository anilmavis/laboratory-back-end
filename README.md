# laboratory-back-end
It is a Spring Boot REST API for a laboratory reporting project. Go [here](https://github.com/anilmavis/laboratory-front-end/) for the React front-end.
## User Requirements
* Report Description (ID, Patient First and Last Name, Patient ID (TC), Diagnosis Title, Diagnosis Details, Report Issued
Date, A Photo of the Physical Report in .png / .jpg Format)
* A report must be defined by only one laboratorian, one laborant can define n reports. (First Name, Last Name, Hospital ID (7 Digits))
* Patient full name and TC, and laborant full name search should be possible. Sorting by report date should be able to be done.
* Changes should be made on an existing report.
* Details of all existing reports should be available for review.
* Delete an existing report.
* Users must login to the system with username and password.
* It should include an authorisation mechanism. For example standard users create and associate records, but not delete and edit them. Administrator can create all to carry out actions. In addition, the standard users cannot create or edit existing users, they can only view them.
## System Requirements
* **JDK 17**: It is required to build and run the project. It can be installed on Debian 11 with the following command:
```sh
sudo apt install openjdk-17-jdk
```
* **Maven**: It is required to be in the PATH only if `mvn` is used instead of the wrapper `./mvnw` while building and running. It can be installed on Debian based GNU/Linux distributions by running the below command:
```sh
sudo apt install maven
```
## How to Build
```sh
./mvnw package
```
Then, a .jar file will be created in the target directory.
## How to Run
```sh
./mvnw spring-boot:run
```
Then, the server listens on port 8080 (http://localhost:8080). Or, find the .jar file in the releases and run the following command:
```sh
java -jar laboratory-0.0.1-SNAPSHOT.jar
```
Finally, run the front-end.
## System Architecture
### Repositories
Since they extend JpaRepository of Spring to gain all functionality, they are straight-forward. However, ReportRepository is important because it contains the necessary method annotated by SQL Query for searching purpose.
### Services
Service layer normally contains the business logic, but in this basic software most of the logic is in PatientService and LaborantService, since the format and validity of TC and hospital ID are checked here. The other service methods directly use repository methods because the other validations (whether null, unique or empty) are handled automatically by the column constraint annotations of the models. Also, some fields in models have JsonIgnore annotation to avoid infinite recursion caused by bidirectional relationships.
### Controllers
All controllers use api/v1/ prefix in their request path. GET requests are used to fetch all from the database or for searching reports. The other sensitive operations use POST. The PreAuthorize annotation is used to check if the user has necesaary authority to do specific operations such as delete and edit. This way, only admins can run such methods. Sorting in ascending order may not be required, since the IDs of the reports indirectly represents the created date.
### Tests
There are unit tests but no integration tests currently, since the functionality is tested via Postman. Run ```./mvnw test``` to execute the unit tests.
### Authorisation
Since Basic Auth is used, the login credentials is sent for every request. Thus, HTTPS is recommended. Also, to improve it, JWT based auth can be added. The default credentials in the database are user:user and admin:admin. There are two roles, USER and ADMIN.
### Database
H2 embedded in-memory SQL database is used for convenience and configuration classes of models populate some default data when the app is started. However, it can be configured to use a file for nonvolatile storage. The other databases such as MySQL can also be configured easily without affecting the code. The only class that I used SQL query for searching is ReportRepository. Its syntax should be compatible with most databases. There are bidirectional many-to-one relations between the patient / laborant and report tables to make the database third normal form, eliminating the data duplication and redundancy. There is a many to many bidirectional relationship between user and role tables. Since this project is small-scale, the report photos are stored as a base64 string in the database instead of file system. So, uploading very big photos is not advised. In case of an app which has thousands of reports, using file system would be much better.
#### SQL ER Diagram
![er](https://github.com/anilmavis/laboratory-back-end/assets/77068958/aab25d7e-2a2f-4fd0-a03e-647c67f5693c)
