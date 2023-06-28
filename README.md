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
* **JDK 17**: It is required to build and run the project.
* **Maven**: It is required to be in the PATH only if `mvn` is used instead of the wrapper `./mvnw` while building and running. It can be installed on Debian based GNU/Linux distributions by running the below command:
```sh
sudo apt install mvn
```
## How to Build
```sh
./mvnw spring-boot:build
```
## How to Run
```sh
./mvnw spring-boot:run
```
Then, the server listens on port 8080.

http://localhost:8080
## System Architecture
### Tests
Tthere are unit tests but no integration tests currently, since the functionality is tested via Postman.
### Authorisation
Since Basic Auth is used, the login credentials is sent for every request. Thus, HTTPS is recommended. The default credentials in the database is user:user and admin:admin. There are two roles, USER and ADMIN.
### Database
H2 embedded in-memory SQL database is used for convenience. However, it can be configured to use a file for volatile storage. The other databases such as MySQL can also be configured easily without affecting the code. There are bidirectional many-to-one relations between the patient / laborant and report tables to make the tables third normal form. There is a many to many bidirectional relationship between user and role tables. Since this project is small-scale, the report photos are stored in the database instead of file system.
#### SQL ER Diagram
![er](https://github.com/anilmavis/laboratory-back-end/assets/77068958/aab25d7e-2a2f-4fd0-a03e-647c67f5693c)
