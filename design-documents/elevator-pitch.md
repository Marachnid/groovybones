My individual project idea is a browser-game called GroovyBones to recreate a fun dice game called 'Knucklebones' in the videogame: 'Cult of the Lamb'. Knucklebones is a turn-based game that uses two 3x3 boards which you place rolled dice on, scoring points for yourself and knocking opponent dice off until one player cannot place any more dice; the player with the highest score wins. More info on the game rules can be found here: https://cult-of-the-lamb.fandom.com/wiki/Knucklebones.

The main problem I'm trying to solve is how to create and manage a sustained user interaction/state for turn-based game flow while integrating user persistence. This includes creating opponent logic that reads current game state, assesses data like score/dice positions/potential score, and reacts with either selfish, neutral, or hostile actions; like an angry form that's trying to submit more points than you. The likelihood of these interactions will be created by using random number generators to scale difficulty of the game opponent. Game stats will also be added to the user's profile, accumulating wins/losses, total score, etc.).

The game will rely on an internal webservice to handle all DB interactions for user game/profile management, as well as opponent difficulty data used in random number generators.

The game will allow a user to:
- Sign up and manage their profile
- Play GroovyBones against a game-logic opponent
- Save/Delete current games
- CRUD game state modifiers (ex. this board uses a 4x4 setup, allows dice values up to 10, score modifiers, etc.)
- Read/Delete player statistics/records (wins/losses, total score, highest in-game score, etc.)
- *maybe* Allow multiplayer instances with a shared game state using WebSockets or another tool