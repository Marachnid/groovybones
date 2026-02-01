package home

/**
 * message controller that renders message view
 * views/message/message.gsp is implicitly rendered
 */
class MessageController {

    /**
     * single-line method to print a test message and render message
     * @return renders message.gsp
     */
    def message() {println 'MessageController message()'}
}
