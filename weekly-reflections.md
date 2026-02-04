# Weekly Reflections


### template
## Week X
Date - hours per week

Tasks Completed:
- task
- task
- task
- task

Notes:
- note
- note




## Week 1
1/19/26 - ?

Tasks Completed: 
- researched Grails/Gradle/GORM framework for Groovy webapps
- tested out new project environment and MVC interactions
- tested out GORM class mappings with simple ORM table/class
- added some basic design notes and documentation on the environment

Notes:
- Grails/GORM is very heavy on convention over configuration, heavily relying on implicit rendering based on directory/project structure
- Grails console tool can create webapp scaffolding with a lot of items prepackaged
  - The only extra dependency I had to bring in was a MySQL connector


## Week 2
1/26/26 - ?

Tasks Completed:
- created an elevator pitch
- added to design notes - user features, game design
- researched a bit more about game specifics
- checked project requirements against current designs
- added more one-to-many db designs/thoughts

Notes:
- Annoyingly, there's already another fanmade/public recreation of Knucklebones, but it doesn't have any kind of user management, and I can't imagine they used Groovy/Java for it
- I've noticed the problem I'm solving is more of a conceptual problem rather than a concrete problem like a scheduler
  - feels like I'd have to be more deliberate about specific deliverables for a 'smooth game flow'



## Week 3
2/4/26 - 8h

Tasks Completed:
- tested out 2d array gameboard creation and operations
- added testing scripts to learn how to interact/read the board effectively
- added methods for adding/removing specific values
- added logic for game-specific operations like multiplying repeated values v^#reps and removing matching dice
- Opponent logic for detecting and taking hostile action against a player is partially built out
- task

Notes:
- Logging is built-in via SLF4J
- Groovy scripts and Groovy classes have slightly different variable scope
- Board operations aren't as bad as I thought they were going to be, Sets, .contains() and .removeAll() handle most operations
- Opponent logic is going to have to consider a lot of factors that need to be modularized to make it easier to implement
  - it needs to know:
    - Newly generated number
    - Player board values/positions
    - Self board values/positions
    - IF genNum matches ANY board value --- IF SELF-column.contains(genNum) IS NOT full
- 