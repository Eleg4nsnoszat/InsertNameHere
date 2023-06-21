# InsertNameHere
## About
This team work project is for JIRA website, we had to write manual test cases and then make them automatic
and give feedback to the developer/tester team.
## Dependencies
- JDK 19
- MAVEN 4.0.0

## Config file

- You will need to create a config file with the given parameters below

| url               | The Automation Exercise website |
|-------------------|---------------------------------|
| correctUsername   | A valid username                |
| correctPassword   | A valid password                |
| emptyPassword      | Leave it blank                  |
| incorrectUsername | An invalid username             |
| incorrectPassword    | An invalid password             |
| emptyUsername         | Leave it blank                  |


  

## How to run a test from command line
- Open cmd or any terminal on your computer, navigate to the project and type this command:
```
mvn test -Dtest="required test class name"
```
and if you want the login test you will need a few argument ->
 ```
 -DcorrectUsername=the email that you gave in the config file
 ```
```
-DcorrectPassword=the password that you gave in the config file.
```
- So the full command will look like this (example):
```
 mvn test -Dtest="LogIntest#testSuccessfulLogIn" -DcorrectUsername=exampleUsername -DcorrectPassword=examplePassword
 ```

