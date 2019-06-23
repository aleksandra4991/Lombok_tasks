call gradlew build
if "%ERRORLEVEL%" == "0" goto runruncrud
echo.
echo GRADLEW BUILD has errors - breaking work
goto fail

:runruncrud
call runcrud.bat
echo running runcrud.bat
echo.
goto runchrome

:runchrome
start chrome.exe
echo running chrome
echo.
goto rungettask

:rungettask
start "" http://localhost:8080/crud/v1/task/getTasks
echo runninggettasks
echo.
goto end

:fail
echo.
echo There were errors

:end
echo the end
echo.
