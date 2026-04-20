OrderingApp



Overzicht:
Een eenvoudige JavaFX fruitshop-demo met een basis GUI-indeling. Ontworpen als een schoon, uitbreidbaar startpunt voor de app.



Structuur:

App (app/) – MainApp, AppManager, AppFactory

Controller (controller/) – MainController, UIHandler

Model (model/) – Fruit, Cart, FruitFactory (laadt fruitdata uit JSON)

View (view/) – GridView (fruitgrid), CartView (winkelwagenpaneel), MenuView (topbalk placeholder), FXStyles (stijlconstanten)

Resources – JSON-data, afbeeldingen en berichten



Huidige indeling:

Boven: topbalk placeholder

Links: grid van fruitobjecten

Rechts: paneel voor de winkelwagen



Eerste keer draaien:
Wanneer je de app start, zie je:

Een lichtgrijze topbalk (menu-placeholder)

Een blauwe grid links met fruitnamen

Een groen paneel rechts voor de winkelwagen



Doel:

Biedt een visuele template en een duidelijke OOP-structuur

Laat een werkend fruitgrid vroeg zien

Ontworpen voor eenvoudige uitbreiding van functies en UI-elementen

