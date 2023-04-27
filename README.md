# ryback-api
API for ryback-web and future mobile app

## Running

`docker compose up`


* runs database and api

`docker compose down`
* stops app and database

Don't forget to forward port 8080 if running separately in docker

`docker run -p 8080:8080 <image id>`

## To Do
### features
* get all recipe ingredients for recipe
### bugs
### refactors
* split logic out of controller into service class
* tests