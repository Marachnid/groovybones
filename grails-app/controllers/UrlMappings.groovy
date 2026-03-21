

/**
 * Responsible for defining URL mapping and pathing
 */
class UrlMappings {

    /**
     * defines a default home action when landing on '/' and calls HomeController to render index.gsp
     * defines a catch-all route closure to handle implicit rendering/pathing of controllers
     */
    static mappings = {
        "/"(controller: "home", action: "index")

        //webservice routes
        "/opponent"(controller: "opponentService") {action = [GET: "get", POST: "post"]}

        "/opponent/$id"(controller: "opponentService") {action = [GET: 'getById']}


        /*
          catch-all route that allows implicit rendering from controllers
          controller called -> optional controller action -> optional ID value and/or extension
          () - closure with no extra configurations/restraints
         */
        "/$controller/$action?/$id?(.$format)?"()
    }
}
