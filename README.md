# Villie
##Interpreter of a very small subset of SML written in Java

##Language basics

`false` is 0, `true` is anything else <br />
`E := (E)` <br />
`   | E < E | E > E | E = E | E <= E | E >= E` (these result in true / false expressiion) <br />
`   | E + E | E - E | E * E | E / E` <br />
`   | if E then E else E` <br />
`   | funcName(args, ...)` <br />
`   | NUM | ID` <br />
`   | E;` <br />

Function declarations have the form <br />
`fun funcName(args, ...) = E;` <br />
all in one line. <br />

The parameters names are consisted of one letter. <br />
The function names have two or more letters. <br />

A function can be called from anywhere in the program as long as it is defined somewhere else in the same file. <br />
If one line of the source file contains `E;` the expression is evaluated numerically and printed on the standard output. <br />
