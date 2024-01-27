# openfasttrace-lambda
OpenFastTrace as a service using AWS Lambda and API Gateway

# ⚠️ This project is deprecated and not maintained any more. ⚠️

## Development

### Deploy

1. Configure your AWS credentials in `~/.aws/credentials`
1. Copy `gradle.properties.template` to `gradle.properties` and adapt the settings for your environment:
    * `awsProfile`: the profile for your credentials used in `~/.aws/credentials`
    * `awsDeployBucket`: an s3 bucket used for uploading artifacts during deployment
    * `awsRegion`: the region where to deploy the application
1. Run `./gradlew deploy`

### Running system tests

Run `./gradlew deploy` to deploy the application and create file `test.properties` containing configuration for the system tests.
