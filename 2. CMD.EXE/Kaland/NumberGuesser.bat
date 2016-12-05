:: Guess the Number
:: by Kovacs Gyorgy
@ECHO OFF

ECHO Hello. First let me tell you about myself.
ECHO I like numbers. I have a favourite number.
ECHO Every time it is a different number. I pick
ECHO them from a hat whenever I am asked what my
ECHO favourite number is. 
ECHO.

ECHO Hey.. Do you want to know what my favourite number now is?
ECHO.

SET /P answer="> "

:: AND and OR are not supported by batch. Sorry.
IF "%answer%" == "yes" ( GOTO game )
IF "%answer%" == "YES" ( GOTO game ) 
IF "%answer%" == "ok"  ( GOTO game ) 
IF "%answer%" == "OK"  ( GOTO game ) 
GOTO end


:: This is the actual beginning of the game
:: the setup if you will, here we pick the 
:: random number, and initialize the guesses.
:game
CLS
COLOR 7

ECHO Yay!
ECHO.
ECHO Ok, let me see what my favourite number is...

:: %random% gives you a nev random number each time you use it.
SET myNumber=%random%
SET guesses=10

ECHO Got it! I will tell you if your guess is too high or too low.
ECHO Good luck!

:: This is the game loop, this part will loop
:: at every guess.
:loop
ECHO.
ECHO You have %guesses% guesses!
ECHO.

SET /P guess="> "

:: LSS - Less than
:: GTR - Greater than
:: EQU - Equal to
IF %guess% LSS %myNumber% (
	ECHO Too small!
) ELSE (
	IF %guess% GTR %myNumber% (
		ECHO Too big!
	) ELSE (
		ECHO You got the number!
		GOTO win
	)
)

:: Decrement guesses and loop.
SET /A guesses=%guesses%-1
IF %guesses% EQU 0 ( GOTO failed )
GOTO loop


:: If you can not guess the number in the given number 
:: of turns, you go here.
:failed
CLS
COLOR 4

ECHO You ran out of tries!
ECHO My favourite number was: %myNumber%
ECHO.

ECHO Do you want another try?
SET /P answer="> "

:: AND and OR are not supported by batch. Sorry.
IF "%answer%" == "yes" ( GOTO game )
IF "%answer%" == "YES" ( GOTO game ) 
IF "%answer%" == "ok"  ( GOTO game ) 
IF "%answer%" == "OK"  ( GOTO game ) 

EXIT

:: If you successfully guess the number, you go here.
:win
CLS
COLOR 2

ECHO OMG YOU GUESSED THE NUMBER YOU ARE THE BEST!

ECHO Do you want another go at it, champ?
SET /P answer="> "

:: AND and OR are not supported by batch. Sorry.
IF "%answer%" == "yes" ( GOTO game )
IF "%answer%" == "YES" ( GOTO game ) 
IF "%answer%" == "ok"  ( GOTO game ) 
IF "%answer%" == "OK"  ( GOTO game ) 

EXIT

:: If you don't want to play the game... I am deeply hu.. 
:: I mean you get this ending.
:end
ECHO.
ECHO Oh... Well I kind of hoped that you cared...
ECHO I didn't really prepare with anything else...
ECHO Sorry.
ECHO Wait, I do have some other thing that I could show you.
ECHO Just a second...
ECHO I do have something for you...
ECHO.

PAUSE
CLS

:: SPOILER ALERT!!

ECHO "g                                               g"  
ECHO "o /     \             \            /    \       o"
ECHO "a|       |            -\          |      |      a"
ECHO "t|       `.             |         |       :     t"
ECHO "s`        |             |       -\|       |     s"
ECHO "e \       | /       /  \\\   --__ \\       :    e"
ECHO "x  \      \/   _--~~          ~--__|-\     |    x" 
ECHO "*   \      \_-~                    ~-_\    |    *"
ECHO "g    \_     \        _.--------.______\|   |    g"
ECHO "o      \     \______// _ ___ _ (_(__>  \   |     "
ECHO "a       \   .  C ___)  ______ (_(____>  |  /    a"
ECHO "t       /\ |   C ____)/      \ (_____>  | /     t"
ECHO "s      / /\|   C_____)       |  (___>   /  \    s"
ECHO "e     |   (   _C_____)\______/  // _/ /     \   e"
ECHO "x     |   -\  |__   \\_________// (__/       |  x"
ECHO "*    |-\    \____)   `----   --'             |  *"
ECHO "g    | -\_          ___\       /_          _/ | g"
ECHO "o   |              /    |     | -\            | o"
ECHO "a   |             |    /       \  \           | a"
ECHO "t   |          / /    |         | -\           |t"
ECHO "s   |         / /      \__/\___/    |          |s"
ECHO "e  |           /        |    |       |         |e"
ECHO "x  |          |         |    |       |         |x"
ECHO.
PAUSE
EXIT
