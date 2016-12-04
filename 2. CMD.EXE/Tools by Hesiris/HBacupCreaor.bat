echo off
rem suppresses the output of the command itself, i.e. echo asd will yield 
rem  'asd'
rem  and not
rem  'echo asd
rem  asd'

rem also: using rem insted of :: prevents some errors occuring inside commented lines
SETLOCAL EnableDelayedExpansion

rem create folders.txt template if it doesn't exist
if NOT exist folders.txt (
	echo.   > folders.txt
	echo ::Lines starting with double colon will be ignored >> folders.txt
	echo ::Insert folders without leading drive letter, e.g. to copy C:\Downloads\Films use Downloads\Films or Dowloads/Films >> folders.txt
	echo.   >> folders.txt
	echo Downloads/Videos >> folders.txt 
	echo.   >> folders.txt
	echo ::Do not modify the following line, it is a marker. Put folders from your users folder after it >> folders.txt
	echo ::e.g. to copy ^<partition^>:\Users\^<username^>\Music write Music >> folders.txt
	echo ::Users/^<Username^>/ >> folders.txt
	echo.   >> folders.txt
	echo My Pictures >> folders.txt
	
	echo --------------------------------
	echo The file containing the locations "folders.txt" was not found. A template has been created
	echo Please fill in with the names of the folders to be copied
)

if not exist folders.txt (
	echo Could not read/write "folders.txt" file which contains the locations for the files to be moved
	echo Please check if you have read/write permissions in the locations this program is stored.
) 
rem sets active code page to 65001, thus enabling the parsing of UTF-8 characters 
rem some say it prevents batches from working, but i did not experience such things
rem may not work on win 7 and earlier
chcp 65001
echo --------------------------------

rem this should stop beeping, but doesn't seem to work reliably and requires admin privileges 
rem net stop beep

rem the real thing starts here. Enjoy!
:START
call:getVerifiedPath "Copy FROM following location (e.g. C or C:/Backup): " src
call:getVerifiedPath "To location: " dest

set /p uns="Username on source drive: "
call:strRemSpaceBeginEnd %uns% uns
rem not prompting the user to create the directory if it doesn't exist, assures that they did not just mistype it
set /p und="Username on destination drive: "
call:strRemSpaceBeginEnd %und% und

set /p mv="(m)ove or (c)opy? (Default: copy)"


rem removes all spaces from strings (substitutes space with nothing "<space>=<nothing>")
rem other string operations: http://www.dostips.com/DtTipsStringManipulation.php
set mv=%mv: =%
rem if it were used on the others strings as well, they would lose even spaces in filenames, so a function is used
rem insted to only remove start and end spaces and adds colon to beginning and end of the strings

if "%mv%"=="m" (
	set mv=/MOVE
)else if "%mv%"=="M" (
	set mv=/MOVE 
)else set mv=

rem echo %src% %dest% %uns% %und% %mv%



rem  paranthesi are crucial
rem  also note that using %i will work directly from command prompt, however %%i is necessary inside a batch file
set /a isUsers=0
rem this is to enable the reading of whitespaces, using "eol=" will not work (skip=1 is for this application only). More info:
rem http://ss64.org/viewtopic.php?id=1544
FOR /F skip^=1^ tokens^=*^ delims^=^ eol^=  %%i IN (folders.txt) DO (

	set j=%%i
	rem echo !j:~0,2!
	if NOT "!j:~0,2!"=="::" (
		if !isUsers! EQU 1 (
			echo "%src%\Users\%uns%\%%i"
			@robocopy  "%src%\Users\%uns%\%%i" "%dest%\Users\%und%\%%i" %mv% /e 
		) else (
			echo "%src%\%%i"
			@robocopy  "%src%\%%i" "%dest%\%%i" %mv% /e 
		)
	)
	
	if "%%i"=="::Users/<Username>/" (
		set /a isUsers=1
	)
)

rem robocopy parameters: /e -copy empty subdirectories; /njh - no job header---to reduce clutter; 
rem /unicode - logs in unicode if /tee and /log+:file are added, but has no effect on console

rem enabled if the pair at the beginning was desabled
rem net start beep

pause
goto:eof


rem function declarations

rem returns a string that is an existing folder
:getVerifiedPath <message> <return: path>
setlocal 
:_verifyPathStart
set /p loc=%~1
call:verifyInput "%loc%" loc
rem echo inside:scr: "%loc%"
if %loc%==false goto _verifyPathStart
endlocal & set %~2=%loc%
goto:eof


rem removes spaces from beginning and end, ads a colon if only one letter is entered
:verifyInput <input path> <corrected path(false on failure)>
setlocal
set input=%~1
set label=%~3
call:strRemSpaceBeginEnd "%input%" input
call:strlen length "%input%"

if %length% EQU 1 set input=%input%:

if %length% equ 0  (
	echo No input detected, please input an address
	endlocal & set %~2=false & goto:eof
)
rem if the next is in the else case, on %inpu%== null, it will be an invalid command and break the whole if
if NOT EXIST %input% (
	echo Location does not exist, please type again
	endlocal & set %~2=false
) else endlocal & set %~2=%input%

goto:eof

rem <input> <output> is just for visualization, it works without it
:strRemSpaceBeginEnd <input> <output> -ü all variables are passed as reference, unless SETLOCAL and ENDLOCAL are called
setlocal EnableDelayedExpansion
set "str=%~1"
rem removes ending spaces
for /l %%a in (1,1,31) do if "!str:~-1!"==" " set str=!str:~0,-1!
rem removes beginning spaces
for /f "tokens=* delims= " %%a in ("%str%") do set str=%%a

endlocal & set "%~2=%str%"

goto:eof


rem it's magic, don't ask
rem http://stackoverflow.com/questions/5837418/how-do-you-get-the-string-length-in-a-batch-file
:strlen <resultVar> <stringVar>
(   
    setlocal EnableDelayedExpansion

rem echo arg: no %~2  with!%~2!
rem theoretically this shoudl be "s=!%~2!", but for some reason it goes the other way around

    set "s=%~2#"
	set "len=0"
    for %%P in (4096 2048 1024 512 256 128 64 32 16 8 4 2 1) do (
        if "!s:~%%P,1!" NEQ "" ( 
            set /a "len+=%%P"
            set "s=!s:~%%P!"
        )
    )
)
( 
    endlocal & set "%~1=%len%"
    exit /b
)


ENDLOCAL