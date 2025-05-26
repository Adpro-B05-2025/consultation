Write-Host "Starting Prometheus and Grafana..."
docker-compose up -d

Write-Host "`nMonitoring services started!"
Write-Host "Access Prometheus at: http://localhost:9090"
Write-Host "Access Grafana at: http://localhost:3000"
Write-Host "`nGrafana default credentials:"
Write-Host "Username: admin"
Write-Host "Password: admin"
Write-Host "`nDon't forget to set up your Grafana dashboard!"