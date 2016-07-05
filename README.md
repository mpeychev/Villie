## Villie
#Interpreter of a very small subset of SML written in Java

#Language basics

false is 0, true is anything else
E := (E)
   | E < E | E > E | E = E | E <= E | E >= E (these result in true / false expressiion)
   | E + E | E - E | E * E | E / E
   | if E then E else E
   | funcName(args, ...)
   | NUM | ID
   | E;

Function declarations have the form
fun funcName(args, ...) = E;
all in one line.

The parameters names are consisted of one letter.
The function names have two or more letters.

A function can be called from anywhere in the program as long as it is defined somewhere else in the same file.

If one line of the source file contains "E;" the expression is evaluated numerically and printed on the standard output.
