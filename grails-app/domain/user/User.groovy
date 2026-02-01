package user

/**
 * Represents a User DB entity in MySQL table, 'user'
 * Convention over Configuration - GORM/Grails doesn't really use tags/annotations for mapping
 * Implicitly maps User class to 'user' table
 * User attributes are automatically paired with matching DB columns
 */
class User {
    /*
        camelCase is parsed into snake_case
        if DB id column name = 'id', id attribute is implicit
     */
    String firstName
    String userName

    /**
     * closure for matching input field constraints to DB column constraints (not null, varchar(20))
     */
    static constraints = {
        firstName blank: false, maxSize: 20
        userName blank: false, maxSize: 20
    }

    /**
     * closure for default mappings - Grails/GORM uses 'optimistic locking' and expects a version number column
     * set to false if not using versioning/a version column
     */
    static mapping = {
        version false

        //IF DB table/column names did not match class conventions, declare:
        //table 'my_table'
        //id column: 'column_name'
        //firstName column: 'column_name'
    }
}