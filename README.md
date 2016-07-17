# Villie
##Interpreter of a very small subset of SML written in Java

##Language basics

For logical expressions 0 means `false` and anything else means `true`. <br/>
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

