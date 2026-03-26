package user

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import javax.sql.DataSource

/** Carries out integration tests for Opponent domain persistence operations */
@Integration
@Rollback
@Stepwise
class UserServiceIntegrationSpec extends Specification {
    @Shared
    DataSource dataSource


}