


<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>
<asset:stylesheet src="tutorial.css"/>
<title>Tutorial</title>


<main class="util-main">
  <div class="menu-box">

    <h1 class="page-title">Tutorial</h1>
    <section>
      <h2 class="page-header">Rules</h2>
      <p>
        Groovybones is a re-creation of the dice game Knucklebones found in the game <a href="https://www.cultofthelamb.com/">Cult of the Lamb</a>.
        The rules are simple, each turn a random number from 1 to 6 will be generated for the player or opponent to place on their board which will add to their score:
      </p>

      <ul>
        <li>Stacking multiple matching numbers in the same column will multiply their score exponentially for each match</li>
        <li>Opponent score can be attacked by matching numbers in your own columns against the opponent's, removing those numbers from their board</li>
        <li>The game ends once either the opponent or player board is completely full</li>
        <li>The highest score wins!</li>
      </ul>
    </section>

    <section>
      <h2 class="page-header">Multiplying Score</h2>
      <p>Stacking matching numbers in your columns will multiply those values exponentially:</p>

      <ul>
        <li>[3, 3] = 9 points</li>
        <li>[3, 3, 3] = 27 points</li>
      </ul>
    </section>

    <section>
      <h2 class="page-header">Attacking Numbers</h2>
      <p>Numbers can be removed from a board by matching them in the opposing column:</p>

      <ul>
        <li>If the opponent has [3, 3] in their leftmost column, the player can remove those numbers from the opponent's board by placing a 3 in their own leftmost column</li>
      </ul>
    </section>

    <section>
      <h2 class="page-header">Opponents</h2>
      <p>
        Each opponent has a slightly different behavior that encourages different strategies and playstyles.
        Strategies for dealing with one opponent may not work on another and each opponent has their own feel to them. Their names hint at how they will respond to the player:
      </p>

    <ul>
      <li>Chug Chug, the glutton</li>
      <li>Big Slight, the taunt</li>
      <li>Vindictive One, was slighted by Big Slight and just hates you</li>
    </ul>
    </section>

  </div>
</main>