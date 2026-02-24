# ================================================================
# NEXIS CONNECT - Zapusk proekta
# ================================================================
# Odin port :8090 - i frontend i backend na odnom adrese!
# Kak rabotaet:
#   1. Maven sobiraet React (npm install + npm run build)
#   2. dist/ kopiruetsya v static/ Spring Boot
#   3. Spring Boot zapuskaetsya na :8090
#   4. Sayt dostopen na http://localhost:8090
# ================================================================
$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
$env:JAVA_HOME = "C:\Users\Lenovo\.jdks\corretto-17.0.18"
Write-Host ""
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "   NEXIS CONNECT - Zapusk" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Java: $env:JAVA_HOME" -ForegroundColor Gray
Write-Host ""
Write-Host "Shag 1: Maven sobiraet React + zapuskaet Spring Boot..." -ForegroundColor Yellow
Write-Host "  (pervyy raz mozhet zanять 2-3 minuty - skachivanie Node)" -ForegroundColor Gray
Write-Host ""
Write-Host "================================================" -ForegroundColor Cyan
Write-Host "  SAYT: http://localhost:8090" -ForegroundColor Green
Write-Host "  API:  http://localhost:8090/api/" -ForegroundColor Green
Write-Host "================================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "Dlya ostanovki: Ctrl+C" -ForegroundColor Gray
Write-Host ""
Set-Location $projectRoot
& ".\mvnw.cmd" spring-boot:run
