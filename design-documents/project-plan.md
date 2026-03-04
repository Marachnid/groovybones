# Project Plan

## Week X
- [] 
- [] 


## Week 4
- [] Develop basic screen designs
- [x] Finish DB design notes
- [x] Develop game-unique logic for exponential score and removing matching dice
- [x] Develop logic to let a game-ai opponent run autonomously until board is full
- [] Develop logic to let two game-ai opponents run together autonomously until one board is full
- [x] Implement temporary Grails services for user table to explore GORM
- [] Implement HTTP webservice for temporary User table scaffolding
- [] Introduce Spock unit testing for game logic and services


## Week 5
- [] Further develop logic for game-ai behavior (hostile/selfish/neutral actions and chances)
- [] Introduce difficulty scaling to game-ai behavior 
- [] Move difficulty modifiers to DB and introduce a service layer for recalling them
- [] Implement basic front-end pages(GSPs) for a landing page, user profile page, game page



## Week 6
- [] Implement a finished-ish end-to-end game process for two game-ai opponents with difficulty scaling/behavior
- [] Tune difficulty profiles (Hard should win more often than Medium and much more often than Easy)
- [] Model a CLI User vs. Game-ai match
- [] Explore/Introduce AWS Cognito for signup/authentication
- [] Assess CLI Model and front-end page needs
- [] Implement DB designs and GORM classes/configurations
  - users, stats, games, mods -- users_games, users_mods


## Week 7
- [] Finalize CLI game model
- [] Finalize DB designs/classes/configurations
- [] Finalize Unit Testing
- [] Adapt CLI model into Web model
  - [] Implement screens for dedicated steps and processes
  - [] Implement user navigation from home/profile/game/stats/etc...
  - [] User gameboard interactions (click/drag dice onto a board column)
- [] Finalize User Webservice
- [] Finalize Game Grails services
- [] Port local DB to cloud hosting



## Week 8
- [] Finalize MVC interactions
- [] Implement bootstrap/UI designs
- [] Refine web UI/UX
- [] Work on adding game UI/UX details (dice img rolling, sound effects?, game-ai action timing)
- [] Port local app to cloud hosting
- [] Look at stretch goals/multiplayer



## Week 9-15
- [] TBD
- [] Refine project and recheck requirements
- [] Look at improving game UI/UX
- [] Add missing requirements if any
- [] Review checkpoints and content availability
- [] Create a presentation
- [] Refactor where needed