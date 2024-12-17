# Define the JAVA_HOME path
$env:JAVA_HOME = "C:\path\to\your\java\home"

# Add JAVA_HOME to the PATH
$env:PATH = "$env:JAVA_HOME\bin;$env:PATH"

# Navigate to the project directory
Set-Location -Path .\recipe_manager

# Clean and build the Maven project
mvn clean install

# Check if the build was successful
if ($LASTEXITCODE -eq 0) {
    Write-Host "Build successful!"
} else {
    Write-Host "Build failed!"
    exit 1
}
