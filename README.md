# Reminders-app

Server-side application that allows to store reminders(notes) in relational database
and handles CRUD operation.
Some operations available through HATEOAS
Data sent through HTTP requests are in JSON format.

REST API endpoints:

- GET api/v1/reminders
  get list of all reminders

- GET api/v1/reminders/{id}
  get reminder by id

- POST api/v1/reminders
  Add new reminder (over request body in JSON)
  
  JSON schema:
  {
  "task": "make coffee",
  "details": "go to the kitchen and prepare some delicious coffee :)",
  "deadline": "2022-01-20"
  }
  
- DELETE api/v1/reminders/{id}
  delete reminder  
  
- PUT api/v1/reminders/update/{id}
  update reminder (over request body in JSON same schema as adding new reminder)

- PUT api/v1/reminders/done/{id}?isdone=true
  mark as done
  
- PUT api/v1/reminders/done/{id}?isdone=false&newdeadline={newDeadline}
  mark as undone

PostgreSQL database was used for this project.
Table schema:

CREATE TABLE reminders (
	id BIGSERIAL NOT NULL PRIMARY KEY,
	task VARCHAR(50) NOT NULL,
	details VARCHAR(150) NOT NULL,
	created TIMESTAMP(50),
	deadline DATE,
	done VARCHAR(5)
);
