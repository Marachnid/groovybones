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


    /** drop, recreate, repopulate opponent table */
    void recreateOpponentTable() {
        sql.execute('DROP TABLE IF EXISTS opponent')

        sql.execute("""
            CREATE TABLE opponent (
                id INT AUTO_INCREMENT PRIMARY KEY,
                username VARCHAR(25) NOT NULL,
                difficulty TINYINT UNSIGNED NOT NULL,
                wins INT NOT NULL,
                losses INT NOT NULL,
                total_score INT NOT NULL);
            """)

        sql.execute("""
            INSERT INTO opponent (username, difficulty, wins, losses, total_score) VALUES
                ('Chug Chug', 1, 0, 0, 0),
                ('Big Slight', 2, 0, 0, 0),
                ('Vindictive One', 3, 0, 0, 0);
            """)
    }


    /** drop, recreate, repopulate user table */
    void recreateUserTable() {
        sql.execute("""
            CREATE TABLE user (
                id INT AUTO_INCREMENT PRIMARY KEY,
                cognito_sub VARCHAR(36) NOT NULL,
                username VARCHAR(25) NOT NULL,
                wins INT NOT NULL,
                losses INT NOT NULL,
                total_score INT NOT NULL);
            """)

        sql.execute("""
            INSERT INTO user (cognito_sub, username, wins, losses, total_score) VALUES
            ('123', 'testUser', 1, 1, 100);
        """)

    }

}
