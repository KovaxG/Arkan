@echo OFF
SETLOCAL EnableDelayedExpansion

SET arg1=%1%
SET arg1=%arg1:"=%

SET /A counter=0

for /f "tokens=2 delims=," %%a in ('tasklist /nh /fi "imagename eq cmd.exe" /fo csv') do (
	set MYPID=%%a
	if !counter! equ 1 (
		GOTO start
	) else (
		SET /A counter+=1
	)
)
:start
FOR /F "delims=" %%I IN (!MYPID!) DO SET newpid=%%I
echo !newpid! > file

cd Cl

:label
church.mp3
TIMEOUT /T 254 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
)

fire.mp3
TIMEOUT /T 241 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
)

nobody.mp3
TIMEOUT /T 178 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
)
	
years.mp3
TIMEOUT /T 238 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
) else (
	GOTO label
)
ENDLOCAL
ECHO ON