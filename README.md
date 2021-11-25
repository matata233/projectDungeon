# My Personal Project

## A text-based fantasy dungeon adventure game

**What will the application do?**

- A simplified, text-based, 5x5 dungeon encounter game that explore 3 different levels of a dungeon
- You may encounter random events in the Dungeon
- You may encounter monsters in the Dungeon
- You may find some treasure in the Dungeon
  
**Who will use it?**

- Anyone can play this game. Have some fun !

**Why is this project of interest to you?**

- I am exited that I am available to make my first own game
- I can apply what I have learned in class in real practice
- My project can be further expanded in the future, adding more game elements and mechanic

### ***Enjoy your journey !*** 

## User Stories 

- As a player, I want to create my own character
- As a player, I want to check my current stats
- As a player, I want to check my inventory
- As a player, I want to equip a weapon (add equipment to my character)
- As a player, I want to loot an item ((add a thing to my inventory)
- As a player, I want to save my progress of the game to file
- As a player, I want to load my past progress and continue the game

## Phase 4: Task 2
- Sample Event Log

Mon Nov 22 16:21:20 PST 2021 
Rusted Sword has been added to player's inventory.


Mon Nov 22 16:21:30 PST 2021
Iron Sword has been added to player's inventory.


Mon Nov 22 16:21:34 PST 2021
Iron Sword has been removed from player's inventory.


Mon Nov 22 16:21:34 PST 2021
Iron Sword has been added to player's main hand.


Mon Nov 22 16:21:38 PST 2021
Iron Sword has been removed from player's main hand.


Mon Nov 22 16:21:38 PST 2021
Iron Sword has been added to player's inventory.


Mon Nov 22 16:21:38 PST 2021
Rusted Sword has been removed from player's inventory.


Mon Nov 22 16:21:38 PST 2021
Rusted Sword has been added to player's main hand.


Mon Nov 22 16:21:42 PST 2021
Iron Sword has been added to player's inventory.


Mon Nov 22 16:21:44 PST 2021
Iron Sword has been added to player's inventory.

## Phase 4: Task 3

- See UML_Design_Diagram.pdf for UML class diagram (Class dependency excluded)
- Further refactoring is not required in current design. 
- However, if more random events are added to the game,
A new class RandomEvent should be created and all the related methods should be refactored out from GameConsole and GameGUI. For example, a new method call addRandomEvent() will be implemented in RandomEvent. GameConsole and Game GUI will call this method to generate random event during the game.