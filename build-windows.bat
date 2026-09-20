@echo off
setlocal
where java >nul 2>nul || (echo Java bulunamadi. JDK 17 kurulu olmali.&exit /b 1)
for /f "tokens=3" %%v in ('java -version 2^>^&1 ^| findstr /i "version"') do echo Java %%v
where gradle >nul 2>nul || (echo Gradle bulunamadi. Gradle 8.7+ kurun veya gradlew ekleyin.&exit /b 1)
gradle clean build
if errorlevel 1 exit /b 1
echo JAR hazir: build\libs\fabric-ai-animation-0.1.0.jar
