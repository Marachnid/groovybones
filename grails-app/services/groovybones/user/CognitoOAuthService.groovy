package groovybones.user

import groovy.json.JsonSlurper

/**Class is responsible for managing 3rd party Cognito authentication requests*/
class CognitoOAuthService {

    def grailsApplication

    /**
     * executes an HTTP POST request to AWS Cognito App Client
     * @param code received from callback redirect
     * @return Json response payload
     */
    def callAuth(String code) {

        //pull secrets from application.yml (secrets are imported)
        def cfg = grailsApplication.config.aws.cognito
        def url = "${cfg.domain}/oauth2/token"

        //set App Client ID and secret
        String clientId = cfg.clientId
        String clientSecret = cfg.clientSecret

        //encode credentials separately from POST body
        String credentials = "${clientId}:${clientSecret}".bytes.encodeBase64().toString()

        //build and encode auth request body
        def body = [
                grant_type: 'authorization_code',
                client_id: cfg.clientId,
                code: code,
                redirect_uri: 'http://localhost:8080/login/callback'
        ]
            .collect { k, v -> "${k}=${URLEncoder.encode(v, 'UTF-8')}" }
            .join('&')


        //build auth POST request
        HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection()
        conn.setRequestProperty('Authorization', "Basic ${credentials}")
        conn.setRequestMethod('POST')
        conn.doOutput = true
        conn.setRequestProperty('Content-Type', 'application/x-www-form-urlencoded')
        conn.outputStream.withWriter { it << body }

        //execute request
        def response = conn.inputStream.text

        //return response
        return new JsonSlurper().parseText(response)
    }
}
