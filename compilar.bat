@echo off
setlocal enabledelayedexpansion

:: 🧹 Limpieza inicial
if exist "bin\" (
    echo Eliminando carpeta bin\...
    rmdir /s /q "bin"
)

:: 🧠 Pedir versión
set /p version=Introduce la version del plugin:

:: ✨ Tus comandos de compilación
echo Compilando DreamServer v%version%...
javac -cp "libs/*" -d bin src\main\java\com\scratchernoobyt\ds\*.java
copy config.yml bin\
copy plugin.yml bin\
jar cf DreamServer.jar -C bin .

:: 📦 Renombrar el .jar
set archivoOriginal=DreamServer.jar
set archivoNuevo=DreamServer-%version%.jar

if exist "!archivoOriginal!" (
    ren "!archivoOriginal!" "!archivoNuevo!"
    echo ✅ Listo: !archivoNuevo!
) else (
    echo ❌ El archivo DreamServer.jar no fue encontrado.
)

pause
cls
