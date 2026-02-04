KnuckleBones
Browser game recreation of 'Knucklebones' dice minigame in the game 'Cult of the Lamb'

Game works by having a tic-tac-toe gameboard and a dice giving a value between/including 1-6. After a dice is rolled, it is placed onto a players board at the bottom of one of the three columns.

Individual dice have their value counted towards a player's score, but if three dice match of the same column match (5x5x5), their values are multiplied exponentially (5x1 = 5, 5x2 = 10, 5x3 = 125).

A player can knock another player's dice off the board by placing a matching dice value in the matching column of the opposing board. If player1 has 5x5x5 in column1, and if player2 rolls a 5 and has an open slot in column1, they can place their 5 in column1 and player1 will lose all matching dice in their column1 (going from 5x5x5 to null/null/null).

	- Two users having their boards (2d arrays) compared against each other
	- columns with 3 matching dice multiply values by ^3
	- new values eliminate matching existing values in the same, opposing column (op board[newVal][1] == player board[any][1])
	- the game ends once any player is no longer able to place any more dice on their board
	- Game intelligence/operations would work by assessing the player's board and then using tuned random number generator values to control the likelihood the game:
		- takes an offensive action to eliminate player dice
		- takes a neutral action ignoring the player board values
		- takes a smart action to stack their own columns with matching dice to ^e values
		- might be able to create a shared multiplayer instance with Grails/WebSockets

The user profiles would stay minimal with usernames and logins (AWS handles user signups)
Users will be able to CRUD difficulty settings and game modifiers
- predefined difficulty fields that translate to backend random-number modifiers
- it shouldn't be that hard to increase dice range from 1-6 to 1-6+, but games might end quicker as boards fill quicker
- could allow scaling board size up with more columns or rows
- some of these could be predefined options but are CRUD here for the sake of class
- shouldn't have to be overly complicated HTTP data, just arrays or mapped items




Environment:
-Groovy				: 3.0.25
-Grails (GSP/Tomcat/MVC)	: 6.2.3
-GORM	(persistence/ORM)	: 8.12
-MySQL				: 8.4.6
-Gradle (build, dependencies)	: 7.6.4
-HTML/CSS
-JUnit (or Groovy's Spock)
-Log4J (or Log4J2, undecided)
-AWS for user signup/auth


Setup:
Service:
- GORM queries will be routed through an internal REST service to standardize DB requests and results
- GORM wraps Hibernate and functions the same way but uses implicit naming conventions to replace annotations seen in JPA/Hibernate ORM


Frontend:
- GSP/MVC style frontend
- Groovy will be used for HTTP handling to keep environment inclusive
- CSS seems like it should be able to handle most gameboard related pieces
- Bootstrap for generic HTML elements
- WebSockets might be used for a multiplayer session between two users

DB:
- Simple MySQL setup
- One user to many game_modifiers
- maybe a win/loss game record

Game Logic:
- Contain classes for player and opponent objects, difficulty logic
- Logic based on reading player board and assessing conditions, random number generators to decide positive, defensive, neutral actions
- Game Intelligence would be more sophisticated the more detailed conditions are and how deep they go


