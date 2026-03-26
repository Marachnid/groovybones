package util

import groovy.sql.Sql
import javax.sql.DataSource



class SQLRunner {
    DataSource dataSource
    Sql sql

    /** default no-arg constructor */
    SQLRunner() {}

    /**
     * instantiates new sql object on construction
     * @param dataSource db credential properties
     */
    SQLRunner(DataSource dataSource) { sql = new Sql(dataSource) }

    /** control db operations */
    void refreshDB() {
        dropTables()
        insertTables()
        populateTables()
    }

    /** controls dropping tables */
    void dropTables() {
        sql.execute('DROP TABLE IF EXISTS saved_game')
        sql.execute('DROP TABLE IF EXISTS opponent')
        sql.execute('DROP TABLE IF EXISTS user')
    }

    /** controls inserting tables */
    void insertTables() {
        insertOpponentTable()
        insertUserTable()
        insertSavedGameTable()
    }

    /** populates existing tables with sample data */
    void populateTables() {
        //opponent
        sql.execute("""
            INSERT INTO opponent (username, difficulty, wins, losses, total_score) VALUES
                ('Chug Chug', 1, 0, 0, 0),
                ('Big Slight', 2, 0, 0, 0),
                ('Vindictive One', 3, 0, 0, 0);
        """)

        //user
        sql.execute("""
            INSERT INTO user (cognito_sub, username, wins, losses, total_score) VALUES
            ('123', 'testUser', 1, 1, 1),
            ('234', 'otherTestUser', 0, 0, 0);
        """)

        //saved_game
        sql.execute("""
            INSERT INTO saved_game (user_board, opponent_board, turn, user_id, opponent_id) VALUES
            ('user-board', 'opponent-board', 3, 1, 1);
        """)

        //saved_game
        sql.execute("""
            INSERT INTO saved_game (user_board, opponent_board, turn, user_id, opponent_id) VALUES
            ('user-board', 'opponent-board', 3, 1, 2);
        """)

        //saved_game
        sql.execute("""
            INSERT INTO saved_game (user_board, opponent_board, turn, user_id, opponent_id) VALUES
            ('user-board', 'opponent-board', 3, 2, 1);
        """)
    }


    /** insert opponent table */
    void insertOpponentTable() {
        sql.execute("""
            CREATE TABLE opponent (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(25) NOT NULL,
                difficulty TINYINT UNSIGNED NOT NULL,
                wins INT NOT NULL,
                losses INT NOT NULL,
                total_score INT NOT NULL);
        """)
    }

    /** insert user table */
    void insertUserTable() {
        sql.execute("""
            CREATE TABLE user (
                id INT AUTO_INCREMENT PRIMARY KEY,
                cognito_sub VARCHAR(36) NOT NULL,
                username VARCHAR(25) NOT NULL,
                wins INT NOT NULL,
                losses INT NOT NULL,
                total_score INT NOT NULL);
        """)
    }

    /** insert saved_game table*/
    void insertSavedGameTable() {
        sql.execute("""
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
        """)
    }
}