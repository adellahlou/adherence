@echo off
rem **********************************************************************
rem This utility clones the file system to module by downloading ffs.hex
rem **********************************************************************

cls
echo .
echo Programming, please wait it will take up to 20 seconds ....
echo .

set hex2=ffs

rem --------------------------------
rem  Downloading the ffs
rem --------------------------------
echo ........          Programming application  (please wait)
nrfjprog  --program %hex2%.hex  --reset --quiet
echo .........         Programming application  (done)

rem --------------------------------
rem Reset the module
rem --------------------------------
echo ................  Resetting module
nrfjprog  --reset --quiet

rem **********************************************************************
echo .................
echo ###########################
echo The ffs cloning is complete
echo ###########################
echo .
rem **********************************************************************
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
