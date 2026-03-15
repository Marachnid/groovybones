package home

import groovy.json.JsonSlurper
import groovybones.CognitoOAuthService

/**
 * Responsible for managing login routing, callback processes, and logout
 */
class LoginController {

    CognitoOAuthService cognitoOAuthService

    /**
     * grabs login URL from AWS properties and redirects to AWS login
     * @return redirect users to login page
     */
    def login() { redirect(url: "${grailsApplication.config.aws.cognito.loginUI}") }

    /**
     * signs a user out of both cognito and the session, redirects users to home
     * grabs AWS logout URL from properties
     * @return logout redirect
     */
    def logout() {
        session.invalidate()
        redirect(url: "${grailsApplication.config.aws.cognito.loginUI}")
    }


    /**
     * call back method to validate sign-in code with authentication
     * @return redirect user to home for successful login, otherwise authorization error
     */
    def callback() {
        String code = params.code

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


//        println 'PAYLOAD ---------------------------------------'
//        println parsedPayload
//        println parsedPayload['sub']
//        println parsedPayload['cognito:username']



        //redirect to home after successful login
        redirect(controller: "home", action: "index")
    }
}