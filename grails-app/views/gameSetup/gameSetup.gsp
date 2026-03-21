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

        %{--opponent container w/opponent cards--}%
        <div id="opponents" class="flex-row">
            <div class="opponent-container flex-col border-dark">
                <div class="opponent-card flex-center"></div>
                <h3>EASY</h3>
            </div>

            <div class="opponent-container flex-col border-dark">
                <div class="opponent-card flex-center"></div>
                <h3>MEDIUM</h3>
            </div>

            <div class="opponent-container flex-col border-dark">
                <div class="opponent-card flex-center"></div>
                <h3>HARD</h3>
            </div>
        </div>

        %{--play button--}%
        <div id="playContainer" class="flex-center">
            <button id="playButton">
                <g:link controller="GameSetup" action="gameInitialization">Start</g:link>
            </button>
        </div>

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