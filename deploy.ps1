param(
    [ValidateNotNullOrEmpty()]
    [string]$file=$(throw "file parameter is mandatory, please provide one.")
)

$errorMsg = "[ERROR] Add file `'connectionstring`' with two lines, address and password in the form:`r`n`r`n<user>@<ip>`r`n<password>"
if(-not (Test-Path "connectionstring")) {Write-Error $errorMsg } 
else{
    $connectionstring = Get-Content ("connectionstring" | select -First 2)
    if(-not $connectionstring.Length -eq 2){ Write-Error $errorMsg } 
    else {
        $pw = $connectionstring[1]
        $address = $connectionstring[0]
        Write-Host -NoNewline "[DEBUG] IP: " $address "`r`n[DEBUG] PW: "****"`r`n"
        .\Tools\pscp.exe -pw `"$pw`" $file $address`:.
    }
}

