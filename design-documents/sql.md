
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cognito_sub VARCHAR(36) NOT NULL,
    username VARCHAR(25) NOT NULL,
    wins INT NOT NULL,
    losses INT NOT NULL,
    total_score INT NOT NULL
);


INSERT INTO user (cognito_sub, username, wins, losses, total_score) VALUES
('014bc500-0071-70f4-e700-763d5e76f3a1', 'chosenOne', 0, 0, 0);


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



