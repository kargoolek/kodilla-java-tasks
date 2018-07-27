call runcrud.bat

if "%ERRORLEVEL%" == "0" goto runbrowser
goto failed

:failed
echo Something went wrong... :(
goto finish

:runbrowser
start chrome "http://localhost:8080/crud/v1/task/getTasks"
goto finish

:finish
echo exiting...
pause