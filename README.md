### About

This is backend part of Web-application. Backend responses for retrieving data using API and storing it in database.

### Task

Create a simple Web-application and CI/CD infrastructure and pipeline for it.

Using API https://covidtracker.bsg.ox.ac.uk/about-api get all data about “By country over time" for current year and store it into your DB:

```
date_value, country_code, confirmed, deaths, stringency_actual, stringency.
```
Output the data by country_code (the country_code is set) in the form of a table and sort them by deaths in ascending order.

### Building app

```bash
mvn clean compile package -DskipTests=true

docker build -t backend:latest .

docker run --name backend -e DB_HOST=<IP:PORT> \ 
                          -e DB_NAME=<DATABASE_NAME> \ 
                          -e DB_USERNAME=<DATABASE_USERNAME> \ 
                          -e DB_PASSWORD=<DATABASE_DB_PASSWORD> \
                           backend:latest
```
