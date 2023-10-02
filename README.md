# AttendEasy

This is a REST API built using Spring Boot. It provides various endpoints for managers to allow them to track employee's attendance.

> Uses MySQL as a database and authentication with (jwt).

## Endpoints
The API provides the following endpoints:

### Authentication

* `POST /auth` - Returns a jwt if information is correct.

### User

* `GET /user` - Returns the information of the user.
* `POST /user` - Add a new user, only can be done if the role is MANAGER.

### Sessions

* `GET /sessions/report/{employeeId}` - Returns a report of that employee's attendance, only can be done if the role is MANAGER. 
* `GET /sessions/report` - Returns a list of reports of all employees's attendance, only can be done if the role is MANAGER.
* `POST /sessions/checkin` - Checks the employee in.
* `POST /sessions/checkout` - Checks the employee out.
