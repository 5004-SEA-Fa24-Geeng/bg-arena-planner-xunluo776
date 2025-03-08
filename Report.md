# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.
* == checks memory location, .equals checks the value of two objects
   ```java
   // your code here
   String a = new String("");
   String b = new String("");
   boolean loc = (a == b);
   boolean eql = a.equals(b);

   assertTrue(eql);
   assertFalse(loc);
   ```




2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner? 
* if compare, we can use equalsIgnoreCase
* for sort, we can do string.sort(String.CASE_INSENSITIVE_ORDER)





3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point. 
   * order matters for <=, >=, <, >. for example, <= must come before <, otherwise if the input is <=, and the code check < first, it will go to < block(because <= contains <)(< does not contain <=) and return Operations.LESS_THAN, but it should be Operations.LESS_THAN_EQUALS. if check <= first, it will give the right answer.


4. What is the difference between a List and a Set in Java? When would you use one over the other? 
* a list can have duplicates and it is indexed, set does not have duplicates and order(tree set can have ascending order)
* if I want a indexed and duplicates-allowed sequence, use lists, if I want remove duplicates, use sets.



5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here? 
map is java version dictionary, help pair key and value. map allows the program to store values with keys rather than index, and quick lookup.



6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?
* enum is a group of constants. in GameData, the enum maps column names to some values, making the code more readable.







7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
                case CMD_QUESTION: // same as help
                case CMD_HELP:
                    processHelp();
                    break;
                case INVALID:
                default:
                    CONSOLE.printf("%s%n", ConsoleText.INVALID);
            }
    ``` 

    ```java
    // your code here, don't forget the class name that is dropped in the switch block..
    if (ct == CMD_QUESTION || ct == CMD_HELP) {
    processHelp();
   } else if (ct == INVALID) {
   CONSOLE.printf("%s%n", ConsoleText.INVALID);
   } else {
   CONSOLE.printf("%s%n", ConsoleText.INVALID);
   }
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
// your consoles output here
*******Bienvenue sur le site BoardGame Arena Planner.*******

Un outil pour aider les gens ࠰lanifier les jeux 
veulent jouer sur Board Game Arena. 

Pour commencer, entrez votre premi貥 commande ci-dessous, ou tapez ? ou help pour les options de commande.
> 
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/).  As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?


As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 
> Localization is important because it makes software or programs available to users from all over the world,
>  improving user experience like user does not need to learn a language or rely on translator to use the software. 
> It ensures that text, formats, and UI are correct. Addtionally, people will not buy what they cannot read. According to CSA Research,
> "it showed that if people can’t understand a website, they won’t buy products from it. 
> They specifically studied non-English-speaking individuals residing in different countries of the EU. 
> When presented with websites in English only, they clicked out of them faster than you could say “localize.”"
> However, poor localization can lead to serious 
> issues, from being offensive to legal problems. for example, translation errors in tech have 
> led to public relations disasters. and not just the translation, culture on the other head is also important.
> Same object could refer to different meaning in different culture, according to Rishi Anand, "While white symbolizes 
> purity in Western cultures, it represents mourning in some Asian cultures. Overlooking these nuances can lead to misunderstandings 
> and can even alienate your target audience, rather than making them your ardent fan."
> Using properties allows us to keep text separate from code, so it will be easier to maintain. And use utf8 to prevent display errors.
> 
* reference: 
* https://linguidoor.com/7-biggest-challenges-of-software-localization/
* https://phrase.com/blog/posts/why-localization-is-about-survival-in-the-global-marketplace/