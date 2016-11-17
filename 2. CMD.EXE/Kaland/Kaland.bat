:: The @ makes this line a silent line.
@ECHO OFF

CALL :myFunc1 "Sample Text"
CALL :myFunc2 "Sample Text"
ECHO End of Program.
PAUSE
EXIT

:myFunc1
ECHO %*
EXIT /B

:myFunc2
ECHO %* Again.
EXIT /B