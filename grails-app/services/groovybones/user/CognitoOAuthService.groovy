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
        log.info('Login attempt')

        //pull secrets from application.yml (secrets are imported)
        final config = grailsApplication.config
        final String domain = config.getProperty('aws.cognito.domain', String) + '/oauth2/token'
        final String clientId = config.getProperty('aws.cognito.clientId', String)
        final String clientSecret = config.getProperty('aws.cognito.clientSecret', String)
        final String redirectURI = config.getProperty('aws.cognito.redirectURI', String)

        //encode credentials separately from POST body
        String credentials = "${clientId}:${clientSecret}".bytes.encodeBase64().toString()

        //build and encode auth request body
        final def body = [
                grant_type: 'authorization_code',
                client_id: clientId,
                code: code,
                redirect_uri: redirectURI
        ]
            .collect { k, v -> "${k}=${URLEncoder.encode(v, 'UTF-8')}" }
            .join('&')


        //build auth POST request
        HttpURLConnection conn = (HttpURLConnection) new URL(domain).openConnection()
        conn.setRequestProperty('Authorization', "Basic ${credentials}")
        conn.setRequestMethod('POST')
        conn.doOutput = true
        conn.setRequestProperty('Content-Type', 'application/x-www-form-urlencoded')
        conn.outputStream.withWriter { it << body }


        //execute request
        final def response = conn.inputStream.text
        log.info("Auth request status: ${conn.responseCode}")

        //return response
        return new JsonSlurper().parseText(response)
    }
}
