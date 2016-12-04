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

cd Av

:label
burn.mp3
TIMEOUT /T 238 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
)

eyes.mp3
TIMEOUT /T 180 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
)

love.mp3
TIMEOUT /T 249 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
)
	
need.mp3
TIMEOUT /T 227 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
) else (
	GOTO label
)
ENDLOCAL
ECHO ON