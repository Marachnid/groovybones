package user

import groovybones.user.UserService


/**
 * Responsible for profile page and management
 * Shows accumulated stats
 * Allows bulk deletes of saved games
 * Can reset stats back to 0
 * Can reset account
 */
class ProfileController {

    /**
     * handles rendering profile and loading/updating basic user stats into/in session
     * @return render profile
     */
    def profile() {
        log.info('ProfileController profile()')

        Map userStats = new UserService().getUserStats(session['userId'] as Long)

        userStats.totalGames = userStats?.wins + userStats?.losses
        if (userStats.totalGames == 0) userStats.winPercentage = 0
        else userStats.winPercentage = (int) ((userStats.wins / (userStats.wins + userStats.losses)) * 100)

        session['userStats'] = userStats

        render(view: 'profile')
    }


    /**
     * resets user's stats back to 0
     * @return redirect back to profile to update session
     */
    def resetStats() {
        log.info('ProfileController resetStats()')

        Map userStats = [wins: 0, losses: 0, totalScore: 0]
        new UserService().updateUser(session['userId'] as Long, userStats)
        redirect(controller: 'profile', action: 'profile')
    }


    /**
     * resets a user's profile by deleting and invalidating in session
     * fresh profile is created upon signing in
     * @return redirect back to home
     */
    def resetProfile() {
        log.info('ProfileController resetProfile()')

        new UserService().deleteUser(session['userId'] as Long)
        session.invalidate()
        redirect(controller: 'home', action: 'index')
    }
}