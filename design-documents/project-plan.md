# Project Plan

## Week X
- [ ] 
- [ ] 


## Week 4
- [x] Develop basic screen designs
- [x] Finish DB design notes
- [x] Develop game-unique logic for exponential score and removing matching dice
- [x] Develop logic to let a game-ai opponent run autonomously until board is full
- [x] Implement temporary Grails services for user table to explore GORM
- [x] Implement HTTP webservice for temporary User table scaffolding


## Week 5
- [x] Further develop logic for game-ai behavior (hostile/selfish/neutral actions and chances)
- [x] Implement basic front-end pages(GSPs) for a landing page, user profile page, game page



## Week 6
- [x] Implement a finished-ish end-to-end game process for two opponents
- [x] Model a CLI User vs. Game-ai match
- [x] Explore/Introduce AWS Cognito for signup/authentication
- [x] Assess CLI Model and front-end page needs
- [x] Implement DB designs and GORM classes/configurations
  - users, stats, games, mods -- users_games, users_mods


## Week 7
- [x] Implement screens for dedicated steps and processes
- [x] Implement user navigation from home/profile/game/stats/etc...
- [x] User gameboard interactions (click/drag dice onto a board column)


## Week 8
- [x] Smooth out front end processes/game-flow
- [x] Add basic site styling
- [x] Figure out how to rotate/flip gameboards with CSS to mirror each other
- [x] Implement AWS Cognito user processes

## Week 9
- [ ] Rework game.gsp - create a dedicated game page with limited/hideable Navbar for save & exits
- [ ] Add 'intelligent' opponent actions - game opponent detects player columns and targets stacked values (i.e. col1[3, 3])
- [ ] Add 'intelligent' difficulty scaling (i.e. if (random <= 3 ? hostileAction() : randomAction())

### Features:
- [ ] As a registered user, I want to be able to read cumulative game records/stats from past games
- [ ] As a registered user, I want to be able to save(add) unfinished games or delete them
- [ ] As a user, I want to play against an 'intelligent' computer-controlled opponent



## BACKLOG
- [ ] Setup existing remote droplet/domain to host GroovyBones
- [ ] Configure remote droplet MySQL instance to host DB data (instead of AWS RDS)
- [ ] Tune difficulty profiles (Hard should win more often than Medium and much more often than Easy)
- [ ] Move difficulty modifiers to DB and introduce a service layer for recalling them
- [ ] Introduce Spock unit testing for game logic and services
- [ ] Figure out the least disruptive place to put a webservice in



## Week 9-15
