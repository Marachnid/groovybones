## IMPORTANT

With how we have the DB secrets set up, Gradle/Grails is expecting application.yml properties that are missing
The build won't fail, but you'll get errors for DB connection failures
Use a secrets file imported by application.yml

CREATE a new file: 'application-secrets.yml'

ADD this property declaration:

dataSource:
    url: 'jdbc:mysql://localhost:PORT#/db schema'
    username: 'db username'
    password: 'username password'