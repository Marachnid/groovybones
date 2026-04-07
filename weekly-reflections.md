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



## Week 4
2/9/26 - 18h

Tasks Completed:
- Converted game operation scripts into GameBoard class to control add/delete dice actions, map board values, and calculate score
- Added logic for auto-running a board until all of its columns are full
- Built out a dev design for DB tables - User + associated tables, Opponent (difficulty) table
- Created an initial project design
- Created screen designs

Notes:
- GRAILS/GORM does not use DAOs
  - GORM/Grails merges domain objects and persistence into a single class where Hibernate separates them
  - Hibernate uses Data Mapper patterns (static typing)
  - GORM uses Active Record patterns (dynamic typing)
  - in GORM, the entity IS the persistence class
    - where a Java ORM entity is just a mapping to a DB table, GORM is a mapping with persistence behavior by default
  - Grails controllers automatically initiate and open/close Hibernate sessions
  - It looks like:
    - Hibernate: Controller > Service > DAO > Hibernate > DB
    - GORM: Controller > Service (optional) > Domain Class (GORM class/DB entity)


## Week 5
2/16/26 - 3h

Tasks Completed:
- minimal work completed, unexpected funeral and other obligations

Notes:




## Week 6
2/23/26 - 2h

Tasks Completed:
- minimal work completed, unexpected funeral and other obligations


Notes:



## Week 7
3/2/26 - 35h

Tasks Completed:
- Built out base functionality of the game/website/UI design
- Integrated a signed-in and signed-out experience
- Integrated game processes into website session to build an end-to-end gameplay process
- Added CSS to give basic styling and UX features
- Built out AWS Cognito signin/registration/authentication and synced to local DB

Notes:
- Busy week, many parts of the code/logic/current setup were overhauled to work smoothly together
- Feature-based development is much easier with the platform figured/built out



## Week 8
3/9/ - 20h + ?

Tasks Completed:
- Smoothed out UX experience and game-logic opponent, game-logic opponent can run autonomously against a player
- Fixed UI components to render more smoothly
- Merged Dev into Main to allow feature-based development

Notes:
- Fixed/finalized a lot of the temp/messy aspects of the code and logic
- 

## Week 9
3/16/ - 20h

Tasks Completed:
- Updated project documentation/design documents
- Reworked game and gameover screens
- Completed feature/intelligent-opponents
- Completed feature/difficulty-scaling 

Notes:
- Fixed/finalized a lot of the temp/messy aspects of the code and logic


## Week 10
3/23/ - 30h

Tasks Completed:
- Redesigned pages and controllers to utilize additional persistent data
- Further developed persistence classes for SavedGames and user operations
- Added new styling to pages
- Learned/implemented Spock integration testing
- Added logging to the environment with rolling policies for test/dev

Notes:
- A lot of work developing integration tests and learning how to manage different environments
- Page designs are coming together, but not sure how to arrange everything in an interesting way


## Week 11
3/30/ - 40h
Tasks Completed:
- Huge overhaul for CSS design, implemented a class-based styling system for a unified website theme
- Additional integration testing/implementations for User/SavedGame persistence
- Built dynamic stats and tutorial into game page
- Smoothed out and unified naming conventions and package structures

Notes:
- A lot of things are in place, but User/SavedGame/Opponent interactions are frustrating with Opponent behind API
- I spent more time on the CSS than I probably should have, but I love the neon red
- Integration tests are getting hard to keep current
- Noticing a lot of the fine differences
- Assessing and refactoring how persistence is handled


## Week 12
4/6/ - 40h
* half of this is carried over from the previous week

### Completed a large refactor of Persistence full end-to-end working:
> I noticed that I was putting an excessive amount of work into separating User and Opponent domain references 
> into detached references before hitting the session. This also needed a lot of testing to verify, and a lot 
> of extra Domain (Hibernate/GORM) constraints to define/control values.
>
> In practice/outside of testing, I noticed the actual behavior of the application behaving a bit differently as well; 
> most likely from subtle differences in how the integration testing sessions are managed vs. a live dev environment.
>
> The main problem became apparent once everything for User and Opponent on the front end was connected - I couldn't use 
> full GORM/Hibernate persistence mapping for SavedGame one-to-many relationships with Opponent behind an API.
>
> With Full GORM/Hibernate persistence mapping, I would need a persistence reference of both Opponent and User together 
> to create a SavedGame, but I'm only ever able to return detached Opponent references via the API. Circumventing the API 
> just to save games felt like it would ruin the spirit of creating a webservice in the first place. This created a lot 
> of bloat for everything persistence related and a lot of constraints/mappings that were getting too tedious.
>
> To solve my problem I had to limit GORM/Hibernate to only database/table access and remove the constraints/mappings 
> for one-to-many relationships. Funnily enough, the manual process for managing SavedGames feels considerably easier; 
> at least in this case.
>
> SavedGame only requires a user and opponent ID and two gameboard lists which are all already available in the session. 
> Instead of having long GORM processes to retrieve references for each domain, validate them against each other, 
> try to add (so on and so forth), SavedGame is its own entity that we can save/reconstruct only through session values. 
>
> With a simplified GORM/Hibernate-level relationships, the Service classes of User and SavedGame now intersect slightly
> to handle deleting saved games if a user is deleted, but the overall complexity and setup is significantly reduced
>
>With the new simplification and separation of SavedGame, Services and persistence are now id-driven for session management:
> 1. Full persistence references aren't needed in the session, they are instead split into maps of as-needed data
> 2. Minimal User and Opponent data is used in-session, with full data retrieved as needed or reconstructed from already-present session data
> 3. Service methods for Opponent and User receive/return modular Maps of specific data instead of full domain objects (i.e. `opponentStats[wins: .., losses: .., totalScore: ..]`)
> 4. By having Services act more modularly, we can call/refresh less data and have pages be more responsive to DB changes

### Data/Persistence validations updated
> I removed some unnecessary testing and methods that started feeling too heavy to keep current. Because the front end
> doesn't really leave any room for unexpected user values. Only fractions of data make it to the session, testing for
> things like 'update to cognitoSub ignored' started feeling unnecessary when it never reaches the session.
> 
> Update methods were also moved into try/catch blocks to reduce some of the many if/else validation blocks and have room
> to add more control over specific value updates if needed.

Notes:
- I struggle a lot with locking in a design until I can see how it's being used
- A lot of stuff was slimmed down to keep only what is needed/useful
- Testing was reduced for unnecessary interactions. There's very little areas for unexpected input