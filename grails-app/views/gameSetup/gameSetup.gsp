<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>
<asset:stylesheet src="gameSetup.css"/>

<main class="util-main">
    <div class="menu-box">
        <h2 class="page-title">Game Setup</h2>

        <g:form controller="GameSetup" action="gameInitialization" method="POST">

            <input type="hidden" name="username" />
            <input type="hidden" name="difficulty" />
            <input type="hidden" name="wins" />
            <input type="hidden" name="losses" />
            <input type="hidden" name="totalscore" />

            <!-- Character selection -->
            <div class="character-grid">

                <g:each in="${session['opponentsList']}" var="opponent">
                    <div class="character-card"
                         data-username="${opponent.username}"
                         data-difficulty="${opponent.difficulty}"
                         data-wins="${opponent.wins}"
                         data-losses="${opponent.losses}"
                         data-totalscore="${opponent.totalScore}">

                        <div class="character-portrait"></div>

                        <div class="character-label">
                            ${opponent.username}
                        </div>
                    </div>
                </g:each>

            </div>

            <!-- Start button -->
            <div class="play-button-container">
                <button class="menu-action" type="submit">Start</button>
            </div>
        </g:form>

    <!-- Saved games -->
        <div class="saved-games-box">

            <label for="savedGamesSelect">Saved Games</label>

            <select id="savedGamesSelect">
                <option value="">-- Select Saved Game --</option>
            </select>

            <button class="menu-action" id="deleteSave">Delete</button>
            <button class="menu-action" id="loadSave">Load</button>

        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const cards = document.querySelectorAll(".character-card");

            cards.forEach(card => {
                card.addEventListener("click", () => {

                    cards.forEach(c => c.classList.remove("selected"));
                    card.classList.add("selected");

                    document.querySelector("input[name='username']").value = card.dataset.username;
                    document.querySelector("input[name='difficulty']").value = card.dataset.difficulty;
                    document.querySelector("input[name='wins']").value = card.dataset.wins;
                    document.querySelector("input[name='losses']").value = card.dataset.losses;
                    document.querySelector("input[name='totalscore']").value = card.dataset.totalscore;
                });
            });
        });
    </script>

</main>
