# CRON EXPRESSION PARSER

## Description
This is a cron parser that accepts a cron string and expands each field to show the specific times when the cron job will execute


### Features
1. Parses standard cron string with five time fields plus a command. 
2. Time fields are - minute, hour, day_of_month, month, day_of_week
3. Supports asterick(*), comma(,), step(/), range(-)

Example - 
*/15 0 1,15 * 1-5 /usr/bin/find

Output  -
- minute         0 15 30 45
- hour           0
- day_of_month   1 15
- month          1 2 3 4 5 6 7 8 9 10 11 12
- day_of_week    1 2 3 4 5
- command        /usr/bin/find


### Steps
1. Clone this repository. git clone <repository>
2. Build the project :
   mvn clean install
3. Run the tests :
   mvn test
4. Execute the script : 
   ./run.sh "*/15 0 1,15 * 1-5 /usr/bin/find"

   
