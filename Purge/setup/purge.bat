@echo off
IF [%1] == [] GOTO HELP
IF [%1] == [-log] GOTO LOGS
IF [%1] == [-wks] GOTO WS
IF [%1] == [-h] GOTO HELP

if exist %1 (
    GOTO LIST
) else (
    GOTO HELP
)

java -jar %~dp0\purge.jar %1 %2 %3 %4 %5 %6 %7
goto:eof

:LOGS
java -jar %~dp0\purge.jar -af %~dp0\listDelLogs.txt
goto:eof

:WS
java -jar %~dp0\purge.jar -path C:\Dev -file %2 -d
goto:eof

:LIST
java -jar %~dp0\purge.jar -af %1
pause
goto:eof

:HELP
java -jar %~dp0\purge.jar -h
set log= -log               	     Logs
echo %log%
set wks= -wks ^<^>Workspace
echo  -wks ^<num. WKS^>             Workspace
goto:eof

