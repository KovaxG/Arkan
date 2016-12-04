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

cd Ev

:label
alive.mp3
TIMEOUT /T 206 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
)

control.mp3
TIMEOUT /T 228 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
)

killin.mp3
TIMEOUT /T 207 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
)
	
night.mp3
TIMEOUT /T 230 /NOBREAK
TASKLIST /FI "pid eq %arg1%" | find "=" > nul
IF ERRORLEVEL 1 (
	taskkill /FI "imagename eq wmplayer.exe"
) else (
	GOTO label
)
ENDLOCAL
ECHO ON