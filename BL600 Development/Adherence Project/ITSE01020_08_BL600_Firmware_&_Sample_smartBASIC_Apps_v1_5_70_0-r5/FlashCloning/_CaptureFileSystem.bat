@echo off
rem **********************************************************************
rem This utility captures the file system to file ffs.hex
rem **********************************************************************

cls
echo .
echo Programming, please wait it will take up to 20 seconds ....
echo .

set hex2=ffs
set memstart=0x34000
set memlen=36864

rem ------------------------------------------
rem  Read the file system
rem ------------------------------------------
echo ..                Acquiring info
nrfjprog --memrd %memstart% --n %memlen% --w 8 >%hex2%.txt
if ERRORLEVEL 1 goto nrfjprog_err

rem --------------------------------
rem  Convert to hex file
rem --------------------------------
echo ...
Nrf2Hex %hex2%.txt /B %memlen% /I 1 >NUL
if ERRORLEVEL 1 goto nrf2Hex_err

rem --------------------------------
rem Reset the module
rem --------------------------------
echo ................  Resetting module
nrfjprog  --reset --quiet

rem **********************************************************************
echo .................
echo ###################################
echo The File system capture is complete
echo ###################################
echo .
rem **********************************************************************
del %hex2%.txt >NUL
goto :end

:nrf2Hex_err
echo ?????????????????????????????????????????????????????????????????????
echo FAIL
echo .
echo The utility Nrf2Hex has failed. Please contact a Laird representative
echo .
echo ?????????????????????????????????????????????????????????????????????
goto :end

:nrfjprog_err
echo ?????????????????????????????????????????????????????????????????????
echo FAIL
echo .
echo The utility nrfjprog has failed. Please contact a Laird representative
echo .
echo ?????????????????????????????????????????????????????????????????????
goto :end

:end
pause
:abort