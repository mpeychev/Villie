# Villie
##Interpreter of a very small subset of SML written in Java (wip)

##Language basics

For logical expressions 0 means `false` and anything else means `true`. <br />
`E := (E)` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`| E < E | E > E | E = E | E <= E | E >= E` (these result in true / false expression) <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`| E + E | E - E | E * E | E / E` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`| if E then E else E` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`| funcName(args, ...)` <br />
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`| NUM | ID` <br />

Function declarations have the form <br />
`fun funcName(args, ...) = E;` <br />
all in one line. <br />

The parameters names are consisted of one letter. <br />
The function names have two or more letters. <br />

A function can be called from anywhere in the program as long as it is defined somewhere else in the same file. <br />
If one line of the source file contains `E;` the expression is evaluated numerically and printed on the standard output. <br />
