<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>
<asset:stylesheet src="gameSetup.css"/>
<title>Game Setup</title>

<main class="util-main">
    <div class="menu-box">
        <h1 class="page-title">Game Setup</h1>

        <g:form controller="GameSetup" action="gameInitialization" method="POST">
            <input type="hidden" name="id" />
            <input type="hidden" name="username" />
            <input type="hidden" name="difficulty" />
            <input type="hidden" name="selectedOpponent" id="selectedOpponent" />


            <!-- Character selection -->
            <div class="character-grid">

                <g:each in="${session['opponentList']}" var="opponent">
                    <div class="character-card"
                         data-id="${opponent.id}"
                         data-username="${opponent.username}"
                         data-difficulty="${opponent.difficulty}">

                        <div class="character-portrait"></div>
                        <div class="character-label">${opponent.username}</div>
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

                <g:each in="${session['savedGames']}" var="sg">

                    <%
                        def opponent = session['opponentList'].find {it.id == sg.opponentId}
                    %>

                    <option value="${sg.id}">${opponent.username} : turn ${sg.turn}</option>

                </g:each>

            </select>

            <g:form controller="savedGame" action="deleteSave" method="POST">
                <input type="hidden" name="id" id="deleteIdField" />
                <button type="submit" class="btn btn-small">Delete</button>
            </g:form>

            <g:form controller="savedGame" action="loadSave" method="POST">
                <input type="hidden" name="id" id="loadIdField" />
                <button type="submit" class="btn btn-small">Load</button>
            </g:form>

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
                    document.querySelector("input[name='id']").value = card.dataset.id;
                    document.querySelector("input[name='username']").value = card.dataset.username;
                    document.querySelector("input[name='difficulty']").value = card.dataset.difficulty;

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

    <script>
        document.addEventListener("DOMContentLoaded", () => {

            const savedGamesBox = document.querySelector('.saved-games-box');
            const forms = savedGamesBox.querySelectorAll('form');

            const deleteForm = forms[0];
            const loadForm   = forms[1];

            const select = document.querySelector('#savedGamesSelect');
            const deleteIdField = document.querySelector('#deleteIdField');
            const loadIdField   = document.querySelector('#loadIdField');

            deleteForm.addEventListener('submit', (e) => {
                const id = select.value;
                if (!id) {
                    e.preventDefault();
                    alert("Select a saved game first.");
                    return;
                }
                deleteIdField.value = id;
            });

            loadForm.addEventListener('submit', (e) => {
                const id = select.value;
                if (!id) {
                    e.preventDefault();
                    alert("Select a saved game first.");
                    return;
                }
                loadIdField.value = id;
            });
        });
    </script>


</main>
