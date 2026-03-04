# Table Design / Ideas

## USER
- username (depends on aws auth)
- password (depends on aws auth)

* forgot if dependent table records can use ID auto_inc types or not

### One USER to Many Mods: 
- custom game modifiers to inject into the runtime
- larger board sizes would make the game last longer, scores higher, low-impact coding-wise and could be fun
- higher dice ranges could make things more interesting and could pair well with larger board sizes
  - higher ranges would make games end earlier, smaller chance of matching an opponent's dice and knocking them off
- These would be best separated from DIFFICULTY logic instead of having a mod that predefines harder opponents
- Would probably have to put some kind of limit on board sizes and dice ranges (10 max each?)
- *Need to think of some additional modifiers once things are up and running (maybe score modifiers or some kind of 'event')
- Stored something like [boardSize:X, diceRange:Y, other:Z] - mostly just integers or booleans that can be injected into a constructor
- TABLE: mods > mod_id(auto_inc), board_size(int), diceRange(int), other(), etc...

### One USER to Many Saved Games:
- This is kind of just a nice-to-have feature, maybe you could save-scum a game if you really wanted
- Would have small utility for a user, but would be interesting to see how it works and how other games might do similar things
- Would have to recall the predefined difficulty level/opponent, player board and current dice, opponent board and current dice, any active mods
- TABLE: games > game_id(auto_inc), playerBoard(varchar()), opponentBoard(varchar()), opponent(?)
- * playerBoard/opponentBoard will be parsed/stored as strings and de-parsed on retrieval (groovy JsonSlurper)

### One USER to One Stats:
- Stats would be a persistent db element throughout a User's lifecycle 
- After a game finishes, win/loss and total points are recorded
- These points are passed on to a user's profile where points are added to total score
- Win/loss is calculated into a ratio 
- Canceled games aren't recorded, it's not really a concern if someone exploits this for their win/loss ratio
- ***IDEA: If I add multiplayer, it would be kind of fun to bet your total score against another players'**
- TABLE: stats > player_id(int), total_score(int), wins(int), losses(int)


## GAME
- This DB logic would depend on how opponent choices and difficulty pans out
- Essentially, opponent difficulty will(should) rely mostly on interchangeable values that influence randomNumber generators
- Would mostly just be consumed by the game and probably untouched by users directly

### Opponent
- An opponent would include a name and various ints associated with it to change their in-game behavior
- ints would boil down to something like 
  - opponent_name: 'easy', dice_match_attack: 3, dice_match_selfish: 3, dice_match_neutral: 6
  - opponent_name: 'hard', dice_match_attack: 9, dice_match_selfish: 7, dice_match_neutral: 2
  - ***actual names/values would vary depending on how it's implemented**
- ***Might be a stretch goal to add custom opponents/difficulties for users to create, maybe a name form with sliders of predefined values**