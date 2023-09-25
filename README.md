Hello there!

I'm currently looking for a coding job, so I decided to create a Risk board game app to showcase my Java language competency.

Here's some pictures: (from left to right, icon on the homescreen, splash screen, game in progress)

And here is the video link if you'd like to see the game in action:


Note:
The board art was created by "Riskgamenut" and posted on the Risk wikipedia page, which is where I found it. I simply added the buttons and other interactive elements on top of the board art. 

How does the game work? 
Region objects are used to represent each each country on the board. Each Region has a unit count and a color assigned to it, among other attributes. As the game progresses, Regions are controlled by different players, changing their color and the number of occupying units. In addition, each Region defines and populates its own Region array. The Region array includes all contiguous Regions, allowing the game to highlight and move units between connected countries. The BoardModel class instantiates all of the Regions and has its own master RegionArray, which contains all countries on the board. MainActivity uses the master RegionArray to scan through the board and update unit counts and colors in the UI based on who controls what. 

The game is broken into multiple phases. First, is the setup phase. Here, players take turn placing one unit at a time onto an empty board. Once every country is occupied by a single unit, the game enters the "Place" phase. During this phase, players take turns placing one unit at a time onto the countries they already occupy. Between these two phases, each player places a totoal of 30 pieces for a total of 120, since my app supports four players. 

Once 120 pieces have been placed, the game moves into the play phase, which is itself broken into three phases: Reinforce, Attack, and Fortify. During the Reinforce phase, players recieve reinforcements that may be placed into their territories depending on how many territories they control and any continent bonuses they're recieving. During the attack phase, the player may attempt to take over a contiguous enemy territory. The attacker rolls 3 red die. The defender rolls 2 white. The highest two dice from the atacker and the defender side are compared and units are subtracted from both sides depending on the results. During the Fortify phase, a player may move units from one territory to another adjacent territory once per turn. 

And that's pretty much it. Once a player no longer has any pieces, they're removed from the turn order. 




