# REASUNTA TO DO DEMO PROJECT

## SIMPLY TO DO PLANNER
Created 2022-06-27

Author: Alibek Akbarov

### REST API: 
/swagger-ui/index.html

HttpMethod.GET
* get all tasks [/task/all]
* get by id [/task/{id}]
* get by title [/task/title?title={title}]
* get by date [/task/by-date?date={yyyy-MM-dd}]
* get between dates [/task/between-dates?beginDate={yyyy-MM-dd}&endDate={yyyy-MM-dd}]
* get overdue tasks [/task/overdue]
* get without actitivy date [/task/without-date]

HttpMethod.POST
* create new task [/task] (task entity in the body)

HttpMethod.PUT
* update task [/task] (task entity in the body)

HttpMethod.DELETE
* delete task [/task/{id}]


### Task details

Please complete the task below. You may take up to 1 week for fulfilling the task.

Please create a microservice which is responsible for holding a todo list of a user.
Design data as you see fit.

Requirements:

0) In this assignment the language must be Java 8+, the main framework must be SpringBoot and it should be managed by Maven.
1) It is sufficient to have an in memory database (H2)
2) Microservice has to have a RESTful interface.
3) The interface must support CRUD operations on tasks in a users todo list.
4) The interface must have additional capabilities: get all tasks for a given date, get a list of overdue tasks, get tasks without a due date. It is OK to add more if you see fit.
5) Functionality should be covered with Unit tests.

Thank you and enjoy the ride)
