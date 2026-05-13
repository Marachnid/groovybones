


<%@ page contentType="text/html;charset=UTF-8" %>
<meta name="layout" content="main"/>
<asset:stylesheet src="about.css"/>
<title>About</title>


<main class="util-main">
  <div class="menu-box">

    <h1 class="page-title">About</h1>
    <section>
      <p>
        Thanks for stopping by my class project for Enterprise Java. Groovybones is a re-creation of the dice game Knucklebones found in the game <a href="https://www.cultofthelamb.com/">Cult of the Lamb</a>.
        I chose to re-create Knucklebones because I wanted to explore how to smoothly manage prolonged session and user interactions relying on specific game state. I was also very interested in exploring how
        I might create dynamic opponent logic capable of responding to both player and opponent gameboard state and how I might scale difficulty behavior from easy, medium, and hard. I also can't help myself and seem to insist
        on basing all personal and class projects on 2d-arrays.
      </p>

      <p>
        Groovybones differs a bit from the class curriculum by using Groovy with Grails (<em>hence the name</em>) instead of a vanilla Java application. I chose Grails because I wanted a fresh perspective on web development with a new framework
        as well as utilize Groovy for OOP development beyond just scripting/automating tasks. Groovy ended up being an excellent choice for Groovybones as the many closures and iterators allowed very flexible means for opponent logic to
        interpret gameboard state and make intelligent decisions. Grails was also a breath of fresh air from usual Java frontend development (not without its own challenges) and was useful in highlighting some of the key differences between Java and Groovy that get taken for granted.
      </p>
    </section>

  </div>
</main>
