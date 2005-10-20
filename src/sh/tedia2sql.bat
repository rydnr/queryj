@echo off
perl tedia2sql -i ..\..\docs\queryjsql-per.dia -o ..\sql\%1\queryjsql -t %1 -s -d -k
pause
