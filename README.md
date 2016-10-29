# BDM2016
ITU BDM

# Deploy

To setup deploy script to the linux server run the script `setup.ps1` from the repository root and fill in the login information. 
This will create a file `connectionstring`. We ignore this file on commit, so don't worry about accidently sharing it.

You can now push .jar's (and anything really) running the deploy script from a powershell with the command:

```
.\deploy.ps1 <myfile>
```

Files sharing the same name in the root directory on the server will be replaced by the new files.