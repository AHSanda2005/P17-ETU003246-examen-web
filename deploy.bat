@echo off
setlocal EnableDelayedExpansion

:: Définition de CATALINA_HOME et CATALINA_BASE
set "CATALINA_HOME=C:\tomcat-10.1.28-windows-x64\apache-tomcat-10.1.28"
set "CATALINA_BASE=%CATALINA_HOME%"

:: Vérification de l'existence du répertoire Tomcat
if not exist "%CATALINA_HOME%" (
    echo Erreur : Le répertoire Tomcat n'existe pas : %CATALINA_HOME%
    echo Veuillez modifier la variable CATALINA_HOME dans le script avec le bon chemin
    exit /b 1
)

:: Variables
set "BUILD_DIR=.\build"
set "SRC_DIR=.\src"
set "WEBAPP_DIR=.\webapp"
set "WAR_NAME=ETU003246.war"
set "JAVA_FILES_LIST=java_files.txt"
set "POSTGRES_JAR=.\lib\mysql-connector-java-8.0.30.jar"  :: 

:: 1. Créer le dossier /build
echo Suppression du répertoire %BUILD_DIR%...
if exist "%BUILD_DIR%" rmdir /s /q "%BUILD_DIR%"
echo Création du répertoire %BUILD_DIR%...
mkdir "%BUILD_DIR%\WEB-INF\classes"

:: 2. Ajouter le fichier web.xml dans build
if exist "src\main\%WEBAPP_DIR%\WEB-INF\web.xml" (
    echo Ajout du fichier web.xml...
    copy "src\main\%WEBAPP_DIR%\WEB-INF\web.xml" "%BUILD_DIR%\WEB-INF\" >nul
    if errorlevel 1 (
        echo Erreur lors de la copie de web.xml.
        exit /b 1
    )
) else (
    echo Erreur : fichier web.xml introuvable dans src\main\%WEBAPP_DIR%\WEB-INF\web.xml
    exit /b 1
)

:: 3. Trouver les fichiers .java et les compiler
echo Recherche des fichiers .java dans %SRC_DIR%...
dir /s /b "%SRC_DIR%\*.java" > "%JAVA_FILES_LIST%"
for %%F in ("%JAVA_FILES_LIST%") do if %%~zF==0 (
    echo Aucun fichier .java trouvé dans %SRC_DIR%..
    del "%JAVA_FILES_LIST%"
    exit /b 1
)

echo Compilation des fichiers Java listés dans %JAVA_FILES_LIST%...
javac -d "%BUILD_DIR%\WEB-INF\classes" -cp ".\lib\servlet-api.jar;%POSTGRES_JAR%" @%JAVA_FILES_LIST%
if errorlevel 1 (
    echo Erreur lors de la compilation des fichiers Java.
    del "%JAVA_FILES_LIST%"
    exit /b 1
)
del "%JAVA_FILES_LIST%"

:: 4. Copier les fichiers de l'application web dans le build
echo Copie des fichiers de l'application web...
xcopy "src\main\%WEBAPP_DIR%\*" "%BUILD_DIR%\" /E /I /Y >nul

:: 4.1 Copier les fichiers HTML et JSP situés à la racine du projet
echo Copie des fichiers HTML et JSP situés à la racine du projet...
for %%F in (*.html *.jsp) do (
    copy "%%F" "%BUILD_DIR%\" >nul
)

:: 5. Copier le fichier .jar PostgreSQL dans WEB-INF/lib
echo Copier le fichier PostgreSQL .jar dans WEB-INF/lib...
if exist "%POSTGRES_JAR%" (
    echo Found PostgreSQL JAR. Copying...
    if not exist "%BUILD_DIR%\WEB-INF\lib" (
        mkdir "%BUILD_DIR%\WEB-INF\lib"
    )
    copy "%POSTGRES_JAR%" "%BUILD_DIR%\WEB-INF\lib\" >nul
    if errorlevel 1 (
        echo Erreur lors de la copie du fichier PostgreSQL .jar.
        exit /b 1
    )
) else (
    echo Erreur : Fichier PostgreSQL .jar introuvable.
    exit /b 1
)

:: lololo
:: copy ".\lib\taglibs-standard-impl-1.2.5.jar" "%BUILD_DIR%\WEB-INF\lib\" >nul
:: copy ".\lib\jstl-1.2.jar" "%BUILD_DIR%\WEB-INF\lib\" >nul 

:: 6. Créer le fichier WAR
echo Création du fichier WAR...
cd "%BUILD_DIR%"
jar -cvf "..\%WAR_NAME%" . 
cd ..

:: 7. Déployer le WAR dans Tomcat
echo Déploiement du fichier WAR dans Tomcat...
copy "%WAR_NAME%" "%CATALINA_HOME%\webapps\" >nul

:: 7.1 Donner les permissions complètes au fichier WAR
echo Configuration des permissions pour le fichier WAR...
icacls "%CATALINA_HOME%\webapps\%WAR_NAME%" /grant Everyone:F
if errorlevel 1 (
    echo Erreur lors de la configuration des permissions pour %WAR_NAME%.
    exit /b 1
)

:: Donner les permissions au répertoire décompressé
set "WAR_DIR=%CATALINA_HOME%\webapps\%WAR_NAME:.war=%"
if exist "%WAR_DIR%" (
    echo Configuration des permissions pour le répertoire décompressé...
    icacls "%WAR_DIR%" /grant Everyone:F /T
    if errorlevel 1 (
        echo Erreur lors de la configuration des permissions pour le répertoire décompressé.
        exit /b 1
    )
)

:: 8. Démarrer Tomcat
echo Démarrage de Tomcat...
call "%CATALINA_HOME%\bin\startup.bat"

echo Déploiement terminé. Accédez à l'application à : http://localhost:8080/%WAR_NAME:.war=%
endlocal
