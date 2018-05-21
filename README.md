# parking-app

## About
This REST API allows a user to enter a date/time range and get back the rate at which they would be charged to park for that time span. It is built using the Spring Framework. For ease of use, I have included the standalone JAR that you can run in the target folder: https://github.com/danarchgithub/parking-app/blob/master/target/parking-app-0.1.0.jar. Running this file will start the local server on port 8080 (http://localhost:8080). In this folder you can also see the results of tests run during compilation. If you would like, you can compile on your own using Maven or Gradle.

## API Contract
### Get status of the server
URI - /status\
HTTP Method = GET\
No parameters accepted.\
It will return:\
id - The number of times this request has been run during the current session.\
content - "The server is running."

Curl example:\
curl http://localhost:8080/status \
{"id":1,"content":"The server is running."}

Request:\
HTTP Method = GET\
Request URI = /status\
Parameters = {}\
Headers = {}\
Body = null\
Session Attrs = {}

Response:\
Status = 200\
Error message = null\
Headers = {Content-Type=[application/json;charset=UTF-8]}\
Content type = application/json;charset=UTF-8\
Body = {"id":1,"content":"The server is running."}\
Forwarded URL = null\
Redirected URL = null\
Cookies = []

### Set the rates list
URI - /setrates\
HTTP Method = POST\
Content-Type = application/json\
Request body must be a JSON array of rates with days, times, and price for each rate. Days must be in all lowercase in EEE format seperated by a comma. Times must be in HHmm format and seperated by a "-". Price must be a single integer.\
It will return the array of rates that were sent in the request body.

Curl example (for Windows with quotes escaped):\
curl http://localhost:8080/setrates --header "Content-Type: application/json" --request POST --data {\\"rates\":[{\\"days\\":\\"mon,tues,thurs\\",\\"times\\":\\"0900-2100\\",\\"price\\":1500},{\\"days\\":\\"fri,sat,sun\\",\\"times\\":\\"0900-2100\\",\\"price\\":2000},{\\"days\\":\\"wed\\",\\"times\\":\\"0600-1800\\",\\"price\\":1750},{\\"days\\":\\"mon,wed,sat\\",\\"times\\":\\"0100-0500\\",\\"price\\":1000},{\\"days\\":\\"sun,tues\\",\\"times\\":\\"0100-0700\\",\\"price\\":925}]}

[{"days":"mon,tues,thurs","times":"0900-2100","price":1500},{"days":"fri,sat,sun","times":"0900-2100","price":2000},{"days":"wed","times":"0600-1800","price":1750},{"days":"mon,wed,sat","times":"0100-0500","price":1000},{"days":"sun,tues","times":"0100-0700","price":925}]

Request:\
HTTP Method = POST\
Request URI = /setrates\
Parameters = {}\
Headers = {Content-Type=[application/json;charset=UTF-8]}\
Body = {"rates":[{"days":"mon,tues,thurs","times":"0900-2100","price":1500},{"days":"fri,sat,sun","times":"0900-2100","price":2000},{"days":"wed","times":"0600-1800","price":1750},{"days":"mon,wed,sat","times":"0100-0500","price":1000},{"days":"sun,tues","times":"0100-0700","price":925}]}\
Session Attrs = {}

Response:\
Status = 200\
Error message = null\
Headers = {Content-Type=[application/json;charset=UTF-8]}\
Content type = application/json;charset=UTF-8\
Body = [{"days":"mon,tues,thurs","times":"0900-2100","price":1500},{"days":"fri,sat,sun","times":"0900-2100","price":2000},{"days":"wed","times":"0600-1800","price":1750},{"days":"mon,wed,sat","times":"0100-0500","price":1000},{"days":"sun,tues","times":"0100-0700","price":925}]\
Forwarded URL = null\
Redirected URL = null\
Cookies = []

### Get the current list of rates
URI - /getrates\
HTTP Method = GET\
No parameters accepted.\
It will return the current list of rates.

Curl example:\
curl http://localhost:8080/getrates \
[{"days":"mon,tues,thurs","times":"0900-2100","price":1500},{"days":"fri,sat,sun","times":"0900-2100","price":2000},{"days":"wed","times":"0600-1800","price":1750},{"days":"mon,wed,sat","times":"0100-0500","price":1000},{"days":"sun,tues","times":"0100-0700","price":925}]

Request:\
HTTP Method = GET\
Request URI = /getrates\
Parameters = {}\
Headers = {}\
Body = null\
Session Attrs = {}

Response:\
Status = 200\
Error message = null\
Headers = {Content-Type=[application/json;charset=UTF-8]}\
Content type = application/json;charset=UTF-8\
Body = [{"days":"mon,tues,thurs","times":"0900-2100","price":1500},{"days":"fri,sat,sun","times":"0900-2100","price":2000},{"days":"wed","times":"0600-1800","price":1750},{"days":"mon,wed,sat","times":"0100-0500","price":1000},{"days":"sun,tues","times":"0100-0700","price":925}]\
Forwarded URL = null\
Redirected URL = null\
Cookies = []

### Return the price for the requested start and end time
URI - /price\
HTTP Method = GET or POST\
Parameters:\
startDate - ISO 8601 formatted start date (i.e. 2018-05-16T07:00:00Z)\
endDate - ISO 8601 formatted end date\
It will return a string of the price for the date/time range.\
Limitations:\
The start and end date must be on the same day. If not it will return "Overnight parking is not allowed." Also the rate must completely encapsulate a datetime range for it to be available. Otherwise it will return the string "unavailable".

Curl POST example (rate array has already been set using /setrates):\
curl http://localhost:8080/price --data "startDate=2018-05-16T07:00:00Z" --data "endDate=2018-05-16T17:00:00Z" \
1750

Curl GET example (rate array has already been set using /setrates):\
curl "http://localhost:8080/price?startDate=2018-05-16T07:00:00Z&endDate=2018-05-16T17:00:00Z" \
1750

Request:\
HTTP Method = GET\
Request URI = /price\
Parameters = {startDate=[2018-05-16T07:00:00Z], endDate=[2018-05-16T17:00:00Z]}\
Headers = {}\
Body = null\
Session Attrs = {}

Response:\
Status = 200\
Error message = null\
Headers = {Content-Type=[text/plain;charset=UTF-8], Content-Length=[4]}\
Content type = text/plain;charset=UTF-8\
Body = 1750\
Forwarded URL = null\
Redirected URL = null\
Cookies = []
