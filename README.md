## Table of Contents
- [About](#About)
- [Manual](#Manual)
- [Endpoints](#Endpoints)
- [Startparameters](#Startparameters)
- [Documentation](#Documentation)
- [Configuration](#Configuration)
- [Dependencies](#Dependencies)
- [Planned](#Planned)
- [License](#License)
  
## About
**NotificationAPI** is a java based database abstraction rest api that allows your database to be accessed from any authenticated device.
Benefits include, but are not restricted to:
- **Availability**: Improves database performance and stability through intelligent caching mechanisms.
- **Security**: Improved security thanks to BCrypt hashed passwords and JWTs for authorization
- **Maintainability**: Thanks to the usage of Java any feature can be added with ease

The server also comes with a React based frontend in a [different](https://github.com/hydraulicRobert/NotificationAPI_Frontend) repository. Soon the jwt gets written into the cookies.

## Manual

make sure that your JAVA_HOME environment variable is set and java 17+ is installed.

open a terminal

navigate to the root directory of the project

build the JAR using gradle
```shell
./gradlew build
```
after the build finishes, the JAR will be located at
```shell
build/libs/NotificationAPI.jar
```
start the server to generate a blank ini
```shell
java -jar NotificationAPI.jar
```
add a user with username and password
```shell
java -jar NotificationAPI.jar -a username password
```
## Endpoints

### Authentication

| Method | Endpoint | Description |
|-------|---------|-------------|
| POST | `/auth/login` | receive a refresh jwt on successful authentication |
| POST | `/auth/refresh` | receive an access jwt on successful authentication |
| GET | `/notifications` | get all notifications |
| GET | `/notifications/latest-startdate` | get most current startdate of notifications |
| GET | `/settings/last-modified` | get settings last save action datetime|

---
### /auth/login
refresh and access token get saved as cookies
#### POST /auth/login
- **Method:** `POST`
- **Endpoint:** `/auth/login`
- **Description:** returns a jwt upon successful authentication
- **Auth:** not required

- **Response 200:**
- **Body:**
```
ok
```
- **Cookie**
```
refreshJwt: ey...
```
### /auth/refresh
refresh and access token get saved as cookies
#### POST /auth/refresh
- **Method:** `POST`
- **Endpoint:** `/auth/refresh`
- **Description:** returns a jwt upon successful authentication
- **Auth:** required
- **Cookie**
```
refreshJwt: ey...
```
- **Response 200:**
- **Body:**
```
ok
```
- **Cookie**
```
accessJwt: ey...
```
### /notifications

#### GET /notifications
- **Method:** `GET`
- **Endpoint:** `/notifications`
- **Description:** returns all cached notifications upon successful authorization
- **Auth:** required
- **Cookie:**
```
accessJwt: ey...
```
- **Response 200:**
- **Body:**
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
### /notifications/latest-startdate

#### GET /notifications/latest-startdate
- **Method:** `GET`
- **Endpoint:** `/notifications/latest-startdate`
- **Description:** returns the highest startdate among the notifications
- **Auth:** required
- **Cookie:**
```
accessJwt: ey...
```
- **Response 200:**
- **Body:**
```json
date time
```
### /settings/last-modified

#### GET /settings/last-modified
- **Method:** `GET`
- **Endpoint:** `/settings/last-modified`
- **Description:** returns the settings datetime
- **Auth:** required
- **Cookie:**
```
accessJwt: ey...
```
- **Response 200:**
- **Body:**
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

remove existing user
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

## Documentation
explanation of the different components
### folder and object structure
#### configuration
##### cfginputoutput
contains all functions to read and write from files and the simplified logging

##### startargs
deals with the start parameters on starting the server

##### userdetailsconfig
contains the user source

##### websecurityconfig
everything security related. cors, authenticationmanager, hashing

#### errornotifications
contains the server content.

controller = links

entity = the objects like notifications

repository = crud repositories for the entities

#### events
contains the events for the project

##### authenticationeventlistener
authentication log event

#### filter
contains all the filters for the websecurityconfig

##### jwtauthenticationfilter
deals with the jwt authentication

##### postfilterlogfilter
log filter after every other filter was ran through

#### jwt
contains all stuff corresponding to extraction and creation of jwts

##### jwtutils
creates, reads and validates the jwts

##### rsakeyproperties
rsa keys

#### service
main file that starts the server service is in here

##### notificationsapplication
configures and starts the server. 

#### resources
contains all files associated with the properties like the token lifetime and the banner when starting

##### application.properties
token lifetime, secret key and web logging level
create a secret key on linux using 
```shell
openssl rand -hex 32
```
if you dont have it, use 
```shell
sudo apt install openssl
```
to install it

##### banner
the text logo that gets printed on starting the server

#### test
contains all junit test files

#### database
database related stuff

##### compatibility
compatible with any jdbc driver. only delivered with mssql. use gradle.build to add new dependencies which can be found on websites like mvnrepository.com.
then install it with gradle, using 

to install postgresql for example, add 
```shell
implementation 'org.postgresql:postgresql:42.7.8'
```
to the dependencies part of the build.gradle and use the corresponding syntax in the ini
then run either
```shell
./gradlew build
```
or 
```shell
./gradlew dependencies
```
to download the new dependencies

##### structure
the tables name is by default meldungstabelle, but can be changed in the ini url

###### notification
table name: notification
| variable | type | description |
|-------|---------|-------------|
| `id` | int | notification index number |
| `affected` | varchar(255) | affected entity |
| `problem` | varchar(255) | affected entitys problem |
| `severity` | int | how bad the affected entitys problem is|
| `start_Date` | datetime2(7) | datetime it started |
| `end_Date` | datetime2(7) | datetime it ended|

###### settings
table name: settings
| variable | type | description |
|-------|---------|-------------|
| `id` | int | settings index number |
| `last_Change_By` | varchar(255) | name of the entity who changed the settings last |
| `last_Change_On` | datetime2(7) | datetime the settings were changed last |

###### example 
script to create an example database
```sql
USE [master]
GO
/****** Object:  Database [meldungstabelle]    Script Date: 03.01.2026 21:25:00 ******/
CREATE DATABASE [meldungstabelle]
GO

CREATE TABLE [dbo].[notification](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[affected] [varchar](255) NULL,
	[problem] [varchar](255) NULL,
	[severity] [int] NULL,
	[start_Date] [datetime2](7) NULL,
	[end_Date] [datetime2](7) NULL,
 CONSTRAINT [PK_notification] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
CREATE TABLE [dbo].[settings](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[last_Change_By] [varchar](255) NULL,
	[last_Change_On] [datetime2](7) NULL,
 CONSTRAINT [PK_settings] PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
```

## Configuration
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
### database url explanation
tells spring what database driver to load and where to find the sql database. jdbc:sqlserver for mssql, jdbc:oracle for oracle etc.
```ini
url = jdbc:sqlserver
```
for oracle for example it would be
```ini
url = jdbc:oracle
```
### database credentials
tells spring boot what username and password to use to log into the database
```ini
username = sa
password = sa
```
### database driver classname
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
decide whether or not spring changes your database depending on your jpa. none, create, create-drop, update, validate
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
type your frontend's ip and port in. since it is a list, comma separated. like this: "http://0.0.0.0:5173,http://192.168.0.223:5173, http://localhost:5173"
```ini
AllowedOrigins = http://0.0.0.0:5173,http://192.168.0.223:5173, http://localhost:5173
```
## Dependencies
dependencies are listed under [Dependencies](https://github.com/HydraulicRobert/NotificationAPI/network/dependencies)

and can be found in the file [dependencies.txt](https://github.com/HydraulicRobert/NotificationAPI/blob/main/src/main/resources/dependencies.txt)


## Planned

## License
all rights reserved

NOTICE: The usage of this software or its source code, including but not limited to datasets, training,
or any other AI-related activities, is strictly prohibited.
