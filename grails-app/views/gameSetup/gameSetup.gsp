<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>
<asset:stylesheet src="gameSetup.css"/>
<title>Game Setup</title>

<main class="util-main">
    <div class="menu-box">
        <h1 class="page-title">Game Setup</h1>

        <g:form controller="GameSetup" action="gameInitialization" method="POST">

            <input type="hidden" name="username" />
            <input type="hidden" name="difficulty" />
            <input type="hidden" name="wins" />
            <input type="hidden" name="losses" />
            <input type="hidden" name="totalscore" />
            <input type="hidden" name="selectedOpponent" id="selectedOpponent" required />


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
            <div class="menu-buttons">
                <button class="btn btn-medium" type="submit">Start</button>
            </div>
        </g:form>

    <!-- Saved games -->
        <div class="saved-games-box">
            <label for="savedGamesSelect">Saved Games</label>
            <select id="savedGamesSelect">
                <option value="">-- Select Saved Game --</option>
            </select>
            <button class="btn btn-small" id="deleteSave">Delete</button>
            <button class="btn btn-small" id="loadSave">Load</button>

        </div>
    </div>

    <script>
        document.addEventListener("DOMContentLoaded", () => {
            const cards = document.querySelectorAll(".character-card");
            const form = document.querySelector("form");
            const selectedOpponent = document.getElementById("selectedOpponent");

            cards.forEach(card => {
                card.addEventListener("click", () => {

                    cards.forEach(c => c.classList.remove("selected"));
                    card.classList.add("selected");

                    //set hidden field values
                    document.querySelector("input[name='username']").value = card.dataset.username;
                    document.querySelector("input[name='difficulty']").value = card.dataset.difficulty;
                    document.querySelector("input[name='wins']").value = card.dataset.wins;
                    document.querySelector("input[name='losses']").value = card.dataset.losses;
                    document.querySelector("input[name='totalscore']").value = card.dataset.totalscore;

                    //used to send alert if no opponent selected
                    selectedOpponent.value = card.dataset.username;
                });
            });

            //send alert if opponent not selected when clicking play
            form.addEventListener("submit", (e) => {
                if (!selectedOpponent.value) {
                    e.preventDefault();
                    alert("Please select an opponent before starting the game.");
                }
            });
        });
    </script>


</main>
