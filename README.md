# IppcAssistant app

This project is app I created as possible solution to problems of lacking integrated system in my workplace. 

## Workflow

Whole auditory work in wood industry consists of once every 2 years audits for enterprises that want to have their certificate reneved.
App has registry of enterprises who are recognized with code and have date of expirationf for their audits (Ippc Codes). 
We can filter and export data about choosen enterprises as text. We can set new audit for enterprise wit future date.
After trip, we can settle it, adding conducted audits and adding expenses to summary. In this case we can also export interesting us data.
If subject didn't fulfill requirements for audit, eg. some documents missing or inadequate equipement, there's option for setting reminder that 
it should send us this document in given time, or proof of fiing the issue. This, and upcoming audits are minitored by calendar.

## Technologies

* Java
* Vaadin
* Spring Boot
* Build tool: gradle
* Database: MySQL

## About code

It was my biggest solo project when i was deploying it to railway.com. Main purpose was to present my apabilities when it comes to programming. 
I completed online programming course before, but I dont consider this complete education, rather some place to start. Real education started with self set goals, 
finding way to get ideas to work, trying and testing, comunicate with others (stackoverflow) and feeling sense of achivement after seeing outcome. 
  Code isn't flawless and has many artifacts which are testimony to my learning process. Complete lack of tests (for:13.12.2022), even through I know mockito and Junit. Queries, and filtering mechanisms for records could be probably written better, altrough ther's use of TypedQuery, predicates and criteria also. 
Things getting messy with provided by railway.com MySQL database, mainly issues with LocalDae type, as I believe that server has different timezone set, and also
handling boolean values. Both this things were workin pefectly locally.
  There were few obstacles I needed to figure out, deployment on PaaS service with docker container mainly, but eventually everything came to beeing functional.


