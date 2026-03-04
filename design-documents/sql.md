
CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(20) NOT NULL
);


INSERT INTO user (first_name) VALUES
    ('Talos'),
    ('Xarl'),
    ('Cyrion'),
    ('Uzas'),
    ('Mercutian'),
    ('Vandred'),
    ('Lucoryphus'),
    ('Sahaal'),
    ('Septimus'),
    ('Octavia');




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