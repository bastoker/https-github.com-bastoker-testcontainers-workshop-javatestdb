# testcontainers-workshop-javatestdb
Testcontainers Java Database Testing opdracht

# Opdracht

Doel van deze opdracht is om te werken met een veelvoorkomende testopzet: Het testen met databases.

Het testobject bestaat uit een Java Backend App die voor de opslag van gegeven gebruik maakt van een database.
Om deze app goed te kunnen testen hebben we een database nodig die door de app gebruikt kan worden. Om hierin te voorzien
maken we gebruik van Testcontainers.

De App zelf bestaat uit een REST-API die met HTTP-calls benaderd kan worden. Hiervoor bevat het project een fixture om HTTP-berichten te versturen naar de applicatie.

Met HTTP GET-requests kunnen we gegevens opvragen. Met HTTP POST-requests kunnen we wijzigingen doorvoeren.
HTTP DELETE requests maken het mogelijk om gegevens te verwijderen.

## Bouwen van het project
Voordat we testen kunnen draaien en toevoegen aan het project moeten we de code eerst compileren (bouwen).
Bij voorkeur doen we dit met [IntelliJ](https://www.jetbrains.com/idea/download/) of [VS Code met de Java Plugin](https://code.visualstudio.com/docs/languages/java#_install-visual-studio-code-for-java)

Als je je IDE hebt opgestart kan je de bestaande test openen in 
``src/test/nl/salves/workshop/testcontainers/db/HappyFlowTest.java``

## Toevoegen nieuw testgeval voor het verwijderen van gegevens
De opdracht is om een testgeval te maken voor de verwijder-flow van de App.
Er is een HTTP endpoint waarmee vakanties verwijderd kunnen worden uit de Vakantieplanner. Hiervoor moet nog een test gemaakt worden.

In de testopzet van beide unittests zijn Testcontainers en het starten van de applicatie al geconfigureerd.

## Afronden opdracht
Rond deze opdracht af door een of meer slagende testcases toe te voegen aan de ``VerwijderenTest`` class.

Voeg je wijzigingen toe met een ``git commit`` en ``git push`` dit naar deze Git-repo. Je kan de test ook in de UI
van GitHub.com zelf aanpassen.

## Na afloop
Na afloop hoef je niets op te ruimen. Dit doet Testcontainers automatisch voor je. Super makkelijk toch?