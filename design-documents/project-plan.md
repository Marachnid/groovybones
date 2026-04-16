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
- [x] Rework game.gsp - create a dedicated game page with limited/hideable Navbar for save & exits
- [x] Add 'intelligent' opponent actions - game opponent detects player columns and targets stacked values (i.e. col1[3, 3])
- [x] Add 'intelligent' difficulty scaling (i.e. if (random <= 3 ? hostileAction() : randomAction())

### Features:
- [x] As a user, I want to play against an 'intelligent' computer-controlled opponent
- [x] Tune difficulty profiles (Hard should win more often than Medium and much more often than Easy)


## Week 10
- [x] Created new CSS theme/designs
- [x] refactor and assessment of UI interactions

### Features:



## Week 11
- [x] Finalized overall design theme
- [x] Add unique features to game page - added pseudo navigation, tutorial, and stats into the gameboard

### Features:
- [x] As a user, I want to play against an 'intelligent' computer-controlled opponent



## Week 12
- [x] Completed large refactor of persistence relationships/interactions
- [x] Cleaned up docs, tests, services, domains

### Features:
- [x] As a registered user, I want to be able to read cumulative game records/stats from past games
- [x] As a registered user, I want to be able to save(add) unfinished games or delete them
- [x] As a user, I want to play against an 'intelligent' computer-controlled opponent


## Week 13
- [x] Setup PRD hosting on DigitalOcean droplet with purchased domain name - groovybones.net
- [x] Set up and connected RDS to droplet
- [x] Create full MVP of app running in PRD


## Week 14
- [ ] Add/build/style about.gsp
- [ ] Add/build/style main tutorial.gsp
- [ ] Complete finishing touches for documentation
- [ ] Take a look at UI updates for V2

## Week 15 & 16
- [] I'm sure there will be something