# Lisp Simple Parser

## Made for Monna Histea Scripting language

## How to?
* Connect with your favorite package manager.
  * For example `Gradle` with `Jitpack`
* Use `LispLexer.lex` to lex the text. It will return list with tokens
* List with tokens then gets passed into `LispParser.parse` it will return `LispData` list
* Then list of `LispData` stuff is used to eval, convert to `JSON` or to do some other nasty stuff :)
* Use `LispToJson` to convert lispish code to `JSON`ish :D 😌

## Why?
* It's better way to organize Novel plan instead of typescript boiler code with its ugly `=>` and `{}`
* Syntax probably could be like this:
```lisp
(scene
    :Start1
    (set :userLocation (ask-user "Where did you go?"))
    (when userLocation
        (= :Kiev (print "Good day to move into Kiev, isn't it?")
        (= :Warsaw (print "Wow, Nice city"))
        :else (print "Ok! Happy way to ya :)"))
```

## How to run?

* No need to run it. It's not language interpreter, it's just a **Parser**
* It is used to `LispLexer` -> `LispParser` -> `LispToJson` then put into `JSON`
* Then run it in your favorite Engine 😁