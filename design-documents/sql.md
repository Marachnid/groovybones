
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(20) NOT NULL,
    first_name VARCHAR(20) NOT NULL,
    wins SMALLINT UNSIGNED NOT NULL,
    losses SMALLINT UNSIGNED NOT NULL,
    total_score INT NOT NULL
);


INSERT INTO user (first_name, user_name, wins, losses, total_score) VALUES
    ('Talos','chosenOne', 1, 1, 20),
    ('Xarl','vindictivevindicator', 1, 1, 20),
    ('Cyrion','xxSHADOWSTALKERxx', 1, 1, 20),
    ('Uzas','redhands', 1, 1, 20),
    ('Mercutian','bolts4you', 1, 1, 20),
    ('Vandred','exalted1', 1, 1, 20),
    ('Lucoryphus','crawler-chief', 1, 1, 20),
    ('Sahaal','bigSlight', 1, 1, 20),
    ('Septimus','artificer', 1, 1, 20),
    ('Octavia','withered-rose', 1, 1, 20);




CREATE TABLE user (
id INT AUTO_INCREMENT PRIMARY KEY,
first_name VARCHAR(20) NOT NULL,
user_name VARCHAR(20) NOT NULL
);


INSERT INTO user (first_name, user_name) VALUES
('Talos', 'chosenOne'),
('Xarl', 'vindictivevindicator'),
('Cyrion', 'xxSHADOWSTALKERxx'),
('Uzas', 'redhands'),
('Mercutian', 'bolts4you'),
('Vandred', 'exalted1'),
('Lucoryphus', 'crawler-chief'),
('Zso', 'bigSlight'),
('Septimus', 'deckrunner'),
('Octavia', 'withered-rose');