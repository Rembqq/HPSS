Compile: `javac -cp libs\mpj-v0_44\lib\mpj.jar -d target\classes src\main\java\org\hpss\lab5\*.java`

Run:
CMD
```
set MPJ_HOME=libs\mpj-v0_44 
%MPJ_HOME%\bin\mpjrun.bat -np 6 -cp target\classes;libs\mpj-v0_44\lib\mpj.jar org.hpss.lab5.Lab5
```

PowerShell
```
$env:MPJ_HOME = "D:\Coding Projects\IJ-workspace\HPSS\libs\mpj-v0_44"
& "$env:MPJ_HOME\bin\mpjrun.bat" -np 6 -cp "target\classes;libs\mpj-v0_44\lib\mpj.jar" org.hpss.lab5.Lab5
```

Run with one core(PS):
```
cmd /c 'start /affinity 1 "" libs\mpj-v0_44\bin\mpjrun.bat -np 6 -dev multicore -cp "target\classes;libs\mpj-v0_44\lib\mpj.jar" org.hpss.lab5.Lab5'
```