# laboratory-back-end
It is a REST API for a laboratory reporting project. It is possible to add new report, patient and laborant. It is also possible to edit and delete the existing ones.

There are bidirectional many-to-one relations between the patient / laborant and report tables to make the tables third normal form. There is a many to many bidirectional relationship between user and role tables.
## SQL ER Diagram
![er](https://github.com/anilmavis/laboratory-back-end/assets/77068958/e5dbe425-5ad1-4235-b169-916c12651f66)

## Requirements
- **Java**: Java 17 is required to build and run the project.
- **Maven**: It is required to be in the PATH only if `mvn` is used instead of the wrapper `./mvnw` while building and running. It can be installed on Debian based GNU/Linux distributions by running the below command:
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
Then, the Spring Boot back-end listens on port 8080.

http://localhost:8080

Go [here](https://github.com/anilmavis/laboratory-front-end/) for the React front-end.
