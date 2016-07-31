# Villie
##Interpreter of a very small subset of SML written in Java

##Language basics

For logical expressions 0 means `false` and anything else means `true`. <br/>
The division is integer. All intermediate results should be between -2<sup>31</sup> and 2<sup>31</sup> - 1. <br/>
`E := (E)` <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`| E < E | E > E | E = E | E <= E | E >= E` (these result in true / false expression) <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`| E + E | E - E | E * E | E / E` <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`| if E then E else E` <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`| funcName(args, ...)` <br/>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`| NUM | ID` <br/>

Function declarations have the form `fun funcName(args, ...) = E;` all in one line. <br/>

The parameters names are consisted of one letter. <br/>
The function names have two or more letters. <br/>

A function can be called from anywhere in the program as long as it is defined somewhere else in the same file. <br/>
If one line of the source file contains `E;` the expression is evaluated numerically and printed on the standard output. <br/>

LL(0) parser is used. LR was expected to produce too many states. <br/>
Removing left-recursion grammar refactoring leads to: <br/>

| Nonterminal | | Rule | Rule | Rule | Rule | Rule | Rule |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| E              | := | E<sub>1</sub>E'<sub>1</sub> | | | | | |
| E'<sub>1</sub> | := | < E<sub>1</sub> | > E<sub>1</sub> | <= E<sub>1</sub> | >= E<sub>1</sub> | = E<sub>1</sub> | ε |
| E<sub>1</sub>  | := | E<sub>2</sub>E'<sub>2</sub> | | | | | |
| E'<sub>2</sub> | := | + E<sub>2</sub>E'<sub>2</sub> | - E<sub>2</sub>E'<sub>2</sub> | ε | | | |
| E<sub>2</sub>  | := | E<sub>3</sub>E'<sub>3</sub> | | | | | |
| E'<sub>3</sub> | := | * E<sub>3</sub>E'<sub>3</sub> | / E<sub>3</sub>E'<sub>3</sub> | ε | | | |
| E<sub>3</sub>  | := | ID | NUM | (E) | if E then E else E | | |

##Usage

`java -jar Villie.jar [--argument] <source.code>` <br/>
where `--argument` can be one of `--recursive` or `--stackBased`. <br/>
The default abstract machine is stack based since the recursive one is prone to StackOverflow errors.

##Running Tests

The direcrory `tests` contains a bunch of tests. More tests can be added and they can be ran by executing `run_tests.py`.

##Acknowledgements

Courtesy goes to Georgi Gyurchev for assembling the task and the test cases back in 2012. <br/>
I would like to thank Dr Timothy Griffin for teaching the Compiler Construction course in my Part IB year in Cambridge. <br/>
Finally, I have to express my sincere gratitude towards Alex Chadwick for supervising me for this course (and a couple more) and for inspiring me to complete this long-pending toy project.<br/>

I don't pretend the implementation to be perfect or the most efficient possible at all and by choosing Java as a main development language it must be clear that the level of abstraction is (much) higher than usual for a standard compiler/interpreter but the goal to implement a stack based interpreter that deals with simple expressions and recursive functions was successfully accomplished.
