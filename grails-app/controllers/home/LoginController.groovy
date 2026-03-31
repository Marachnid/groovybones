package home

import groovybones.user.UserService
import groovybones.User
import groovy.json.JsonSlurper
import groovybones.user.CognitoOAuthService

/**
 * Responsible for managing login routing, callback processes, and logout
 */
class LoginController {
    CognitoOAuthService cognitoOAuthService

    /**
     * grabs login URL from AWS properties and redirects to AWS login
     * @return redirect users to login page
     */
    def login() {
        log.info('LoginController login()')
        redirect(url: "${grailsApplication.config.getProperty('aws.cognito.loginUI')}")
    }


    /**
     * signs a user out of both cognito and the session, redirects users to home
     * grabs AWS logout URL from properties
     * @return logout redirect, redirects to home after Cognito signout
     */
    def logout() {
        log.info('LoginController logout()')
        session.invalidate()
        redirect(url: "${grailsApplication.config.getProperty('aws.cognito.logoutUI', String)}")
    }


    /**
     * call back method to validate sign-in code with authentication
     * @return redirect user to home for successful login, otherwise render authorization error
     */
    def callback() {
        log.info('LoginController callback()')
        String code = params['code']

        //in case of any issue with retrieving a code
        if (!code) {render "Missing authorization code"}

        //send code to auth request to authenticate user
        def tokens = cognitoOAuthService.callAuth(code)

        //grab JWT id_token
        def idToken = tokens['id_token']

        //grab id_token's payload, decode, parse
        def payload = idToken.split('\\.')
        def decodedPayload = new String(payload[1].decodeBase64())
        def parsedPayload = new JsonSlurper().parseText(decodedPayload)


        //TODO: probably should add a method in UserService for this
        UserService service = new UserService()
        User player = new User(
                service
                    .getUserById(User.findByCognitoSub(parsedPayload['sub'] as String).id)
                    .returnAsMap()
        )


        //if player is found via sub token, auto-update username and set session
        if (player != null) {
            log.info('existing User found and authenticated')
            player.username = parsedPayload['cognito:username']
            service.updateUser(player)
            player.cognitoSub = null
            session['player'] = player

        //else create a new User entity linked to sub token
        } else {
            log.info('existing User not found, creating a new User')
            player = service.createUser(
                    parsedPayload['sub'] as String,
                    parsedPayload['cognito:username'] as String
            )

            session['player'] = player
        }

        //redirect to home after successful login
        redirect(controller: "home", action: "index")
    }
}