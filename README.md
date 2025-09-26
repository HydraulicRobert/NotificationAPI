server to allow authenticated third party devices to access files without disturbing the main program's access to it, be it for remote access or to receive push notifications in case something important happens. allows login via jwt and request body

-a name password to add a user (password hashed with bcrypt)

-r name to remove a user

-s to show the added users

-c to configure 

-h to show all commands

currently only microsoft sql server supported. 

REST:

/login to get a jwt

/notAll to get all notifications

/set to get the settings timestamp

/notTop1 to get the most current notification timestamp
