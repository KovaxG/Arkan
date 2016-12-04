ECHO OFF

set /p pid=
echo %pid%
FOR /F "delims=" %%I IN (%pid%) DO SET newpid=%%I
echo %newpid%

set /p resp=type to continue

ECHO ON