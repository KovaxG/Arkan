echo off
netsh wlan show profiles

set /p SSID="Select the network to show password for: "
netsh wlan show profile name="%SSID%" key=clear | findstr  "Key Content"
pause