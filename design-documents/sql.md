
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cognito_sub VARCHAR(36) NOT NULL,
    username VARCHAR(25) NOT NULL,
    wins INT NOT NULL,
    losses INT NOT NULL,
    total_score INT NOT NULL
);

INSERT INTO user (cognito_sub, username, wins, losses, total_score) VALUES
('123', 'testUser', 1, 1, 100);


CREATE TABLE opponent (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(25) NOT NULL,
    difficulty TINYINT UNSIGNED NOT NULL,
    wins INT NOT NULL,
    losses INT NOT NULL,
    total_score INT NOT NULL
);

INSERT INTO opponent (username, difficulty, wins, losses, total_score) VALUES
    ('Chug Chug', 1, 0, 0, 0),
    ('Big Slight', 2, 0, 0, 0),
    ('Vindictive One', 3, 0, 0, 0)
;

    ('Talos','chosenOne', 1, 1, 20),
    ('Xarl','vindictivevindicator', 1, 1, 20),
    ('Cyrion','xxSHADOWSTALKERxx', 1, 1, 20),
    ('Uzas','redhands', 1, 1, 20),
    ('Mercutian','bolts4you', 1, 1, 20),
    ('Vandred','exalted1', 1, 1, 20),
    ('Lucoryphus','crawler-chief', 1, 1, 20),
    ('Sahaal','bigSlight', 1, 1, 20),
    ('Septimus','artificer', 1, 1, 20),
    ('Octavia','withered-rose', 1, 1, 20)


CREATE TABLE saved_game (
    id INT NOT NULL AUTO_INCREMENT,
    user_board VARCHAR(60),
    opponent_board VARCHAR(60),
    turn INT NOT NULL,
    user_id INT NOT NULL,
    opponent_id INT NOT NULL,
    
    PRIMARY KEY (id),
    CONSTRAINT fk_saved_game_user
        FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_saved_game_opponent
        FOREIGN KEY (opponent_id) REFERENCES opponent(id)
);


CREATE TABLE user_saved_game (
id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    saved_game_id INT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_user_saved_game_user
        FOREIGN KEY (user_id) REFERENCES user(id),
    CONSTRAINT fk_user_saved_game_saved
        FOREIGN KEY (saved_game_id) REFERENCES saved_game(id)
);


CREATE TABLE opponent_saved_game (
id INT NOT NULL AUTO_INCREMENT,
    opponent_id INT NOT NULL,
    saved_game_id INT NOT NULL,

    PRIMARY KEY (id),
    CONSTRAINT fk_opponent_saved_game_opponent
        FOREIGN KEY (opponent_id) REFERENCES opponent(id),
    CONSTRAINT fk_opponent_saved_game_saved
        FOREIGN KEY (saved_game_id) REFERENCES saved_game(id)
);


INSERT INTO saved_game (user_board, opponent_board, turn, user_id, opponent_id) VALUES
('user-board', 'opponent-board', 3, 2, 1);

INSERT INTO user_saved_game(user_id, saved_game_id) VALUES
(2, 1);

INSERT INTO opponent_saved_game(opponent_id, saved_game_id) VALUES
(1, 1);