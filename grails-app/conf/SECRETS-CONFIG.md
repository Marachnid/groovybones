## IMPORTANT

With how we have the DB secrets set up, Gradle/Grails is expecting application.yml properties that are missing
The build won't fail, but you'll get errors for DB connection failures
Use a secrets file imported by application.yml

CREATE a new file: 'application-secrets.yml'

ADD this property declaration for MySQL:

dataSource:
    url: 'jdbc:mysql://localhost:PORT#/db schema'
    username: 'db username'
    password: 'username password'



ADD this property declaration for AWS:

aws:
    region: us-east-2
    cognito:
        userPoolId: TEMP
        clientId: TEMP
        clientSecret: TEMP
        domain: https://us-east-TEMP.auth.us-east-2.amazoncognito.com
        loginUI: https://us-east-TEMP.auth.us-east-2.amazoncognito.com/login?client_id=TEMP&redirect_uri=http://localhost:8080/login/callback&response_type=code&scope=email+openid+phone
        logoutUI: https://us-east-TEMP.auth.us-east-2.amazoncognito.com/logout?client_id=TEMP&logout_uri=http://localhost:8080/
    credentials:
        accessKey: TEMP
        secretKey: TEMP