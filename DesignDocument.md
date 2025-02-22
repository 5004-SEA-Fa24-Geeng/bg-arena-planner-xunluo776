# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram 

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)
```mermaid
classDiagram
direction BT
class BGArenaPlanner {
  - BGArenaPlanner() 
  - String DEFAULT_COLLECTION
  + main(String[]) void
}
class BoardGame {
  + BoardGame(String, int, int, int, int, int, double, int, double, int) 
  - int minPlayTime
  - double averageRating
  - String name
  - int rank
  - int yearPublished
  - int minPlayers
  - int maxPlayers
  - int id
  - int maxPlayTime
  - double difficulty
  + getMinPlayers() int
  + getMaxPlayTime() int
  + getMaxPlayers() int
  + hashCode() int
  + getDifficulty() double
  + getMinPlayTime() int
  + toString() String
  + getName() String
  + getYearPublished() int
  + equals(Object) boolean
  + main(String[]) void
  + getRating() double
  + getId() int
  + getRank() int
  + toStringWithInfo(GameData) String
}
class ConsoleApp {
  + ConsoleApp(IGameList, IPlanner) 
  - Scanner IN
  - IPlanner planner
  - Scanner current
  - IGameList gameList
  - String DEFAULT_FILENAME
  - Random RND
  - processHelp() void
  - randomNumber() void
  - printCurrentList() void
  - printOutput(String, Object[]) void
  - printFilterStream(Stream~BoardGame~, GameData) void
  + start() void
  - nextCommand() ConsoleText
  - remainder() String
  - processFilter() void
  - processListCommands() void
  - getInput(String, Object[]) String
}
class GameData {
<<enumeration>>
  - GameData(String) 
  +  ID
  +  MIN_PLAYERS
  - String columnName
  +  NAME
  +  DIFFICULTY
  +  RATING
  +  MAX_TIME
  +  MIN_TIME
  +  YEAR
  +  MAX_PLAYERS
  +  RANK
  + getColumnName() String
  + fromString(String) GameData
  + fromColumnName(String) GameData
  + values() GameData[]
  + valueOf(String) GameData
}
class GameList {
  + GameList() 
  + clear() void
  + removeFromList(String) void
  + count() int
  + saveGame(String) void
  + getGameNames() List~String~
  + addToList(String, Stream~BoardGame~) void
}
class GamesLoader {
  - GamesLoader() 
  - String DELIMITER
  - processHeader(String) Map~GameData, Integer~
  + loadGamesFile(String) Set~BoardGame~
  - toBoardGame(String, Map~GameData, Integer~) BoardGame?
}
class IGameList {
<<Interface>>
  + String ADD_ALL
  + getGameNames() List~String~
  + count() int
  + saveGame(String) void
  + clear() void
  + addToList(String, Stream~BoardGame~) void
  + removeFromList(String) void
}
class IPlanner {
<<Interface>>
  + filter(String, GameData, boolean) Stream~BoardGame~
  + filter(String) Stream~BoardGame~
  + reset() void
  + filter(String, GameData) Stream~BoardGame~
}
class Operations {
<<enumeration>>
  - Operations(String) 
  +  NOT_EQUALS
  +  GREATER_THAN_EQUALS
  +  EQUALS
  +  LESS_THAN
  - String operator
  +  GREATER_THAN
  +  LESS_THAN_EQUALS
  +  CONTAINS
  + fromOperator(String) Operations
  + getOperator() String
  + getOperatorFromStr(String) Operations?
  + values() Operations[]
  + valueOf(String) Operations
}
class Planner {
  + Planner(Set~BoardGame~) 
  + reset() void
  + filter(String, GameData, boolean) Stream~BoardGame~
  + filter(String, GameData) Stream~BoardGame~
  + filter(String) Stream~BoardGame~
}

GameList  ..>  IGameList 
Planner  ..>  IPlanner 

```
### Provided Code

Provide a class diagram for the provided code as you read through it.  For the classes you are adding, you will create them as a separate diagram, so for now, you can just point towards the interfaces for the provided code diagram.
```mermaid
classDiagram
direction BT

class BGArenaPlanner {
  - BGArenaPlanner() 
  - String DEFAULT_COLLECTION
  + main(String[]) void
}

class BoardGame {
  + BoardGame(String, int, int, int, int, int, double, int, double, int) 
  - String name
  - int id
  - int minPlayers
  - int maxPlayers
  - int minPlayTime
  - int maxPlayTime
  - double difficulty
  - int rank
  - double averageRating
  - int yearPublished
  + getName() String
  + getId() int
  + getMinPlayers() int
  + getMaxPlayers() int
  + getMinPlayTime() int
  + getMaxPlayTime() int
  + getDifficulty() double
  + getRank() int
  + getRating() double
  + getYearPublished() int
  + toString() String
  + toStringWithInfo(GameData) String
  + equals(Object) boolean
  + hashCode() int
}

class ConsoleApp {
  + ConsoleApp(IGameList, IPlanner) 
  - Scanner IN
  - IPlanner planner
  - Scanner current
  - IGameList gameList
  - String DEFAULT_FILENAME
  - Random RND
  - processHelp() void
  - randomNumber() void
  - printCurrentList() void
  - printOutput(String, Object[]) void
  - printFilterStream(Stream~BoardGame~, GameData) void
  + start() void
  - nextCommand() ConsoleText
  - remainder() String
  - processFilter() void
  - processListCommands() void
  - getInput(String, Object[]) String
}

class GameData {
<<enumeration>>
  - GameData(String) 
  +  ID
  +  MIN_PLAYERS
  - String columnName
  +  NAME
  +  DIFFICULTY
  +  RATING
  +  MAX_TIME
  +  MIN_TIME
  +  YEAR
  +  MAX_PLAYERS
  +  RANK
  + getColumnName() String
  + fromString(String) GameData
  + fromColumnName(String) GameData
}

class GamesLoader {
  - GamesLoader() 
  - String DELIMITER
  - processHeader(String) Map~GameData, Integer~
  + loadGamesFile(String) Set~BoardGame~
  - toBoardGame(String, Map~GameData, Integer~) BoardGame?
}

class IGameList {
<<Interface>>
  + String ADD_ALL
  + getGameNames() List~String~
  + count() int
  + saveGame(String) void
  + clear() void
  + addToList(String, Stream~BoardGame~) void
  + removeFromList(String) void
}

class IPlanner {
<<Interface>>
  + filter(String, GameData, boolean) Stream~BoardGame~
  + filter(String) Stream~BoardGame~
  + reset() void
  + filter(String, GameData) Stream~BoardGame~
}

class Operations {
<<enumeration>>
  - Operations(String) 
  +  NOT_EQUALS
  +  GREATER_THAN_EQUALS
  +  EQUALS
  +  LESS_THAN
  - String operator
  +  GREATER_THAN
  +  LESS_THAN_EQUALS
  +  CONTAINS
  + fromOperator(String) Operations
  + getOperator() String
  + getOperatorFromStr(String) Operations?
}

ConsoleApp *-- IPlanner
ConsoleApp *-- IGameList
GamesLoader --> BoardGame
BGArenaPlanner --> ConsoleApp

```


### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces. 
```mermaid
classDiagram
    direction BT

    class IGameList {
        <<Interface>>
        + String ADD_ALL
        + getGameNames() List~String~
        + count() int
        + saveGame(String) void
        + clear() void
        + addToList(String, Stream~BoardGame~) void
        + removeFromList(String) void
    }

    class GameList {
        + GameList()
        + clear() void
        + removeFromList(String) void
        + count() int
        + saveGame(String) void
        + getGameNames() List~String~
        + addToList(String, Stream~BoardGame~) void
        - Set~BoardGame~ games
    }

    class IPlanner {
        <<Interface>>
        + filter(String, GameData, boolean) Stream~BoardGame~
        + filter(String) Stream~BoardGame~
        + reset() void
        + filter(String, GameData) Stream~BoardGame~
    }

    class Planner {
        + Planner(Set~BoardGame~)
        + reset() void
        + filter(String, GameData, boolean) Stream~BoardGame~
        + filter(String, GameData) Stream~BoardGame~
        + filter(String) Stream~BoardGame~
        - Set~BoardGame~ allGames
        - Set~BoardGame~ filteredGames
    }

    GameList ..|> IGameList
    Planner ..|> IPlanner

```




## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

1. addToList() ensure game is added 
2. removeFromList() ensure game is removed
3. getGameNames() ensure get the content of the game, and case insensitive
4. saveGame() ensure game is saved 
5. clear() ensure it is removed
6. filter(String) ensure filters the board games by the passed in text filter. Assumes the results are sorted in ascending order
7. filter(String, GameData) ensure it returns the right output based on the sign
8. filter(String, GameData, boolean) ensure it returns the right output based on the sign and correct order
9. reset() make sure the list is reset.




## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.





## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two. 
