# GroovyBones

## Project environment:
- **Language**
  - Groovy 3.0.25
- **Framework**
  - Grails 6.2.3 w/embedded Tomcat
- **ORM Framework**
  - GORM 8.1.2
- **Database**
  - MySQL 8.4.6
- **Dependency/Build Management**
  - Gradle 7.6.4
- **Testing**
  - Spock
- **Logging**
  - SLF4J built-in/managed by Grails
- **CSS**
  - bootstrap
- **Validation**: 
  - Bootstrap front end validation
  - backend TBD
- **Security/Authentication**
  - AWS Cognito
- **Hosting**
  - TBD, DigitalOcean Droplet or AWS
- **WEB SERVICE CONSUMED**
  - I'm creating an internal web service to route all user DB interactions through
    - _Not to be confused with Grails services/GORM_
    - Might include a split with game logic using Grails services while User logic using web services
    - Grails services can initiate game logic without latency while User services are HTTP


## Tech I'd like to Explore:
- Grails/Gradle/GORM is completely new as of this semester
- Docker/Docker Compose to address the different technology stack from class
- WebSockets for managing multiplayer instances