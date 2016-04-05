# angular-boot

* Copy us-census.db to the working directory
* Run gradlew run
* Connect to http://127.0.0.1:8080

Note: you may modify the DB file or table name the server reads in src/main/resources/application.yml

## TODO

* SQLLite connection pool.
* Prepared statements.
* Jump to top of the page on selection? Find a better approach than side menu?
* Configure DB & table name from an external file.
* Count, limit, and return rows in one shot. Or at least make counting way quicker.
* Now that the count query is slow, add "Loading..." during query.
* Download/unzip db with gradle.
* Get rid of Spring!
