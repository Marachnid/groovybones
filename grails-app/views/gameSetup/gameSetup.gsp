<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <asset:stylesheet src="gameSetup.css"/>
    <title>Game Setup</title>
</head>
<body>
    <div class="content-container flex-col">

        <h2 id="title">Game Setup</h2>


        <g:form controller="GameSetup" action="gameInitialization" method="POST">

            <input type="hidden" name="username" />
            <input type="hidden" name="difficulty" />
            <input type="hidden" name="wins" />
            <input type="hidden" name="losses" />
            <input type="hidden" name="totalscore" />

            <div id="opponents" class="flex-row">

            <!--iterate through opponents-->
            <g:each in="${session['opponentsList']}" var="opponent">
                    <div class="opponent-container flex-col border-dark"
                         data-username="${opponent.username}"
                        data-difficulty="${opponent.difficulty}"
                        data-wins="${opponent.wins}"
                        data-losses="${opponent.losses}"
                        data-totalscore="${opponent.totalScore}">
                <div class="opponent-card flex-center"></div>
                <h3>${opponent.username}</h3>
                </div>
            </g:each>
            </div>

            <div id="playContainer" class="flex-center">
                <button id="playButton" type="submit">Start</button>
            </div>
        </g:form>

        <script>
            document.addEventListener("DOMContentLoaded", () => {
                const cards = document.querySelectorAll(".opponent-container");

                cards.forEach(card => {
                    card.addEventListener("click", () => {

                        cards.forEach(c => c.classList.remove("selected"));
                        card.classList.add("selected");

                        // Fill hidden fields using dataset
                        document.querySelector("input[name='username']").value = card.dataset.username;
                        document.querySelector("input[name='difficulty']").value = card.dataset.difficulty;
                        document.querySelector("input[name='wins']").value = card.dataset.wins;
                        document.querySelector("input[name='losses']").value = card.dataset.losses;
                        document.querySelector("input[name='totalscore']").value = card.dataset.totalscore;
                    });
                });
            });
        </script>












%{--            opponent container w/opponent cards--}%
%{--        <div id="opponents" class="flex-row">--}%
%{--            <div class="opponent-container flex-col border-dark">--}%
%{--                <div class="opponent-card flex-center"></div>--}%
%{--                <h3>${session['op1'].opponent.username}</h3>--}%
%{--            </div>--}%

%{--            <div class="opponent-container flex-col border-dark">--}%
%{--                <div class="opponent-card flex-center"></div>--}%
%{--                <h3>${session['op2'].opponent.username}</h3>--}%
%{--            </div>--}%

%{--            <div class="opponent-container flex-col border-dark">--}%
%{--                <div class="opponent-card flex-center"></div>--}%
%{--                <h3>${session['op3'].opponent.username}</h3>--}%
%{--            </div>--}%
%{--        </div>--}%

%{--        play button--}%
%{--        <div id="playContainer" class="flex-center">--}%
%{--            <button id="playButton">--}%
%{--                <g:link controller="GameSetup" action="gameInitialization">Start</g:link>--}%
%{--            </button>--}%
%{--        </div>--}%

        %{--load game container--}%
        <div id="loadGame" class="flex-row border-dark">
            <label for="savedGamesSelect">Saved Games</label>

            <select id="savedGamesSelect">
                <option value="">-- Select Saved Game --</option>
            </select>

            <button id="deleteSave">DELETE</button>
            <button id="loadSave">LOAD</button>
        </div>

    </div>
</body>
</html>