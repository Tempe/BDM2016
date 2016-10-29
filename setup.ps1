$server = Read-Host "input host (e.g. group17@8.8.8.8): "
$password = Read-Host "password: "
New-Item connectionstring -ItemType File -Force -Value "$server`r`n$password"