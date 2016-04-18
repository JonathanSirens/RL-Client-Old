@echo off
title Ikov Client
COLOR 2F
"C:\Program Files (x86)\Java\jdk1.8.0_25\bin\java.exe" -server -Xmx512m -cp bin;libs/* org.ikov.client.Client
pause