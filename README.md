# Setup service

## Install java
- Install jdk11
- Verify installed java version by running command
```shell script
java -version
javac -version
```

## Install colima to run containers
- Set up lima for docker
    - `brew install colima`
    - start colima by executing `colima start`
    - In the terminal type `docker ps` and you shouldn't see any error and possibly line indicating headers like "CONTAINER ID, IMAGE etc"


## Install postgres
- Setup Postgres using docker
    - Start db command
    `docker run --name postgresdb -e POSTGRES_DB=bookingengine -e POSTGRES_USER=bookingengine -e POSTGRES_PASSWORD=postgres -p 5432:5432 -d postgres`
    - Install below tools to connect to the database
    ```
       brew cask install pgadmin4   
       brew install psqlodbc
    ```
    - Connect to db command
    `psql -h localhost -U bookingengine -d bookingengine` 

## Seed data
- set DB_PASSWORD as environment variable before running the script
- params
    - starting date
    - number of weeks to seed data from given starting date

`sh seedShowData.sh 12-12-2021 3`

## Run the application
- Build the application and run the test along with coverage
```shell script
./gradlew clean
./gradlew build
```
- Run the server locally on localhost:8080
```shell script
./gradlew bootRun
```

## Run the application using docker compose
- Build the application and run the test along with coverage
```shell script
./gradlew clean
./gradlew build
```
- Run the server locally on localhost:8080
```shell script
docker-compose -f docker-compose-local.yml up 
```

## Deployment
###  Deployment prerequisite -
Deployment assumes that the cluster with  below configuration is created
- run below commands to setup cluster on aws

- `ecs-cli configure --cluster "$CLUSTER_NAME" --default-launch-type EC2 --config-name "$CLUSTER_CONFIG_NAME" --region ap-south-1`
- `ecs-cli configure profile --access-key "$AWS_ACCESS_KEY" --secret-key "$AWS_SECRET_KEY" --profile-name "$CLUSTER_PROFILE_NAME"`
- `ecs-cli up --keypair catalyst --capability-iam --size 1 --instance-type t2.medium --cluster-config` 

values for $CLUSTER_NAME, $CLUSTER_PROFILE_NAME, $CLUSTER_CONFIG_NAME and other  env  variables should be referenced from configuration from gitlab

Currently the pipeline supports deployment to integration, staging and prod environments.
The deployment to integration environment is automatic as opposed to staging and prod environments. 
Every deployment has two stages category of stages 
1. promote stage (ex. promote-staging)  and 
2. deploy stage (ex. deploy-staging)

### 1. Promote stage 
- Tags the image built on the last stage with the current commit id and environment name. 
- pushes newly tagged image to gitlab container registry
- Tag format : [registry url][project namespace][app name]:[COMMIT_SHA]-[ENVIRONMENT]
- Example: registry.gitlab.com/tw-catalyst/booking-web/reactapp:8721c173-integration

### 2. Deploy stage
- Sets up secret registry to authenticate gitlab container registry from aws.  
- deploys the apps to the aws ecs service using images tagged with current commit sha.
- curls /version endpoint at the end of every deployment. 

### Resources
- ecs-cli - https://docs.aws.amazon.com/AmazonECS/latest/developerguide/cmd-ecs-cli.html
- gitlab ci - https://docs.gitlab.com/ee/ci/
