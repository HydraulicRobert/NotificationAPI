## Table of Contents
- [About](#About)
- [How to Use](#how-to-use)
- [Endpoints](#Endpoints)
- [Start Args](#startparameters)
- [Documentation](#documentation)
- [License](#license)
- [Configuration](#ini)
  
## About
**dbProxy** is a java based database proxy that allows your database to be accessed from any authenticated device.
Benefits include, but are not restricted to:
- **Availability**: Thanks to caching the database won't be overwhelmed by the amount of people accessing it
- **Security**: Improved security thanks to BCrypt hashed passwords and JWTs for authorization
- **Maintainability**: Thanks to the usage of Java and my programming any features can be added with ease

The server also comes with a React based frontend in a different repository. Soon i'll change it so that the jwt gets written into the cookies.

## how-to-use

make sure that your JAVA_HOME environment variable is set and java 17+ is installed.

open a terminal

navigate to the root directory of the project

build the JAR using gradle
```shell
./gradlew build
```
after the build finishes, the JAR will be located at
```shell
build/libs/dbProxy.jar
```
start the server to generate a blank ini
```shell
java -jar dbProxy.jar
```
add a user with username and password
```shell
java -jar dbProxy.jar -a username password
```
## Endpoints

### Authentication

| Method | Endpoint | Description |
|-------|---------|-------------|
| POST | `/login` | receive a jwt on successful authentication |
| GET | `/notAll` | get all notifications |
| GET | `/notTop1` | get most current startdate of notifications |
| GET | `/set` | get settings last save action datetime|

---
### login

#### POST login
- **Method:** `POST`
- **Endpoint:** `login`
- **Description:** returns a jwt upon successful authentication
- **Auth:** not required
- **Body:**
```json
{"username": "Your username", "password": "Yoru password"}
```
- **Response 200:**
```json
ey. . . 
```
### notAll

#### GET notAll
- **Method:** `GET`
- **Endpoint:** `notAll`
- **Description:** returns all cached notifications upon successful authorization
- **Auth:** required
- **Header:**
```json
{
  "Authorization": "Bearer <YOUR_ACCESS_TOKEN>",
  "Content-Type": "application/json"
}
```
- **Response 200:**
```json
[
    {
        "id": int,
        "affected": String,
        "problem": String,
        "severity": int,
        "startDate": String,
        "endDate": String
    }
]
```
### notTop1

#### GET notTop1
- **Method:** `GET`
- **Endpoint:** `notTop1`
- **Description:** returns the highest startdate among the notifications
- **Auth:** required
- **Header:**
```json
{
  "Authorization": "Bearer <YOUR_ACCESS_TOKEN>",
  "Content-Type": "application/json"
}
```
- **Response 200:**
```json
date time
```
### set

#### GET set
- **Method:** `GET`
- **Endpoint:** `set`
- **Description:** returns the settings datetime
- **Auth:** required
- **Header:**
```json
{
  "Authorization": "Bearer <YOUR_ACCESS_TOKEN>",
  "Content-Type": "application/json"
}
```
- **Response 200:**
```json
date time
```
## startparameters
start args get called when starting the app, like
```shell
java -jar appname.jar -command
```

output all commands
```shell
-h or --help
```

start configuration of ini
```shell
-c or --configuration
```

remove existign user
```shell
-r username or --remove username
```

show list of existing users
```shell
-s or --show
```

add a new user. password gets hashed once entered
```shell
-a username password or --add username password
```

## documentation
coming soon

## license
all rights reserved

## configuration
restart after changing the config for it to take effect.
when creating a ini by starting it for the first time it will look like this:

```ini
[Database]
url = 
username = 
password = 
driverClassName = 
dialect = 
show-sql = 
ddl-auto =
[Server]
port = 
logLevelRoot = 
logLevelSpring = 
logLevelHibernate = 
showSQLQueries = 
AllowedOrigins = 
```
when having added values, be it manually or via configuration, it may look like this:
```ini
[Database]
url = jdbc\:sqlserver\://localhost\:1433;databaseName\=meldungstabelle;trustServerCertificate\=true
username = sa
password = sa2007
driverClassName = com.microsoft.sqlserver.jdbc.SQLServerDriver
dialect = org.hibernate.dialect.SQLServer2012Dialect
show-sql = false
ddl-auto = none
[Server]
port = 8080
logLevelRoot = INFO
logLevelSpring = INFO
logLevelHibernate = INFO
showSQLQueries = false
AllowedOrigins = http://0.0.0.0:5173,http://192.168.0.223:5173, http://localhost:5173
```
### database url explaination
tells spring what database driver to load and where to find the sql database. jdbc:sqlserver for mssql, jdbc:oracle for oracle etc.
```ini
url = jdbc:sqlserver
```
### database driver classname
tells spring boot what username and password to use to log into the database
```ini
username = sa
password = sa
```
### database credentials
tells spring which driver to use
```ini
driverClassName = com.microsoft.sqlserver.jdbc.SQLServerDriver
```
### database dialect
tells spring which sql dialect to translate the hibernate to
```ini
dialect = org.hibernate.dialect.SQLServer2012Dialect
```
### database show sql
show or hide jpa operations in console. true or false
```ini
show-sql = false
```
### database ddl
decide wether or not spring changes your database depending on your jpa. none, create, create-drop, update, validate
```ini
ddl-auto = none
```

### server port
what port the server will be available from
```ini
port = 8080
```
### server log level
specifies how many details about everything get printed. INFO, DEBUG, TRACE, NONE
```ini
logLevelRoot = INFO
logLevelSpring = INFO
logLevelHibernate = INFO
```
### server show sql queries
specifies wether or not sql queries get printed or not. true or false
```ini
showSQLQueries = false
```
### server allowed origins
type your frontend's ip and port in. since it is a list, comma seperated. like this: "http://0.0.0.0:5173,http://192.168.0.223:5173, http://localhost:5173"
```ini
AllowedOrigins = http://0.0.0.0:5173,http://192.168.0.223:5173, http://localhost:5173
```
