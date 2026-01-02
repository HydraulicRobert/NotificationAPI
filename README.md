## Table of Contents
- [About](#-about)
- [How to Use](#-how-to-use)
- [Endpoints] (#-API-Endpoints)
- [Start Args] (#-start-args)
- [Documentation](#-documentation)
- [License](#-license)
- [Contacts](#-contacts)
## About
**dbProxy** is a java based database proxy that allows your database to be accessed from any authenticated device.
Benefits include, but are not restricted to:
- **Availability**: Thanks to caching the database won't be overwhelmed by the amount of people accessing it
- **Security**: Improved security thanks to BCrypt hashed passwords and JWTs for authorization
- **Maintainability**: Thanks to the usage of Java and my programming any features can be added with ease

The server also comes with a React based frontend in a different repository. 

##how-to-use
First make sure that your Java system variable is set. 
```shell
#Open a terminal
#use gradle to build the jar using "gradlew build"
#navigate to the location of the jar.
#either start the server to create an empty ini file using "java -jar appname.jar" 
#or start it with the -c startcommand to use the server's inbuilt ini configuration, like "java -jar appname.jar -c"
#then add a user using "java -jar appname.jar -a username password

```

## API-Endpoints

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
- **Methode:** `POST`
- **Endpoint:** `login`
- **Beschreibung:** returns a jwt upon successful authentication
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
- **Methode:** `GET`
- **Endpoint:** `notAll`
- **Beschreibung:** returns all cached notifications upon successful authorization
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
- **Methode:** `GET`
- **Endpoint:** `notTop1`
- **Beschreibung:** returns the highest startdate among the notifications
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
- **Methode:** `GET`
- **Endpoint:** `set`
- **Beschreibung:** returns the settings datetime
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
## start-args
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

- [Documentation](#-documentation)
- [License](#-license)
- [Contacts](#-contacts)
## documentation
coming soon

## license
all rights reserved

##contacts
rotaszko@gmx.net