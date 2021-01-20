#!/bin/bash
set -e
set -v

echo "Running this as `whoami`"
echo "Currently running this in $PWD"

cd /home/ec2-user/deployment/
echo "Existing contents of the directory are"
ls

IFS='-' read -ra identifiers <<< $DEPLOYMENT_GROUP_NAME  # DEPLOYMENT_GROUP_NAME is of form neev-xx-team-xx-[backend|frontend]-deployment-[integration|staging|production]
export BATCH_ID=${identifiers[1]}
export TEAM_ID=${identifiers[3]}
export ENVIRONMENT=${identifiers[6]}
export PREFIX="/neev-$BATCH_ID/team-$TEAM_ID/$ENVIRONMENT"
export POSTGRES_IMAGE=`aws ssm get-parameters --name "$PREFIX/POSTGRES_IMAGE" | jq ".Parameters[0].Value" | tr -d \"`
export HOST_DB_PORT=`aws ssm get-parameters --name "$PREFIX/HOST_DB_PORT" | jq ".Parameters[0].Value"  | tr -d \"`
export DB_HOST=`aws ssm get-parameters --name "$PREFIX/DB_HOST" | jq ".Parameters[0].Value" | tr -d \"`
export DB_PORT=`aws ssm get-parameters --name "$PREFIX/DB_PORT" | jq ".Parameters[0].Value" | tr -d \"`
export POSTGRES_USERNAME=`aws ssm get-parameters --name "$PREFIX/POSTGRES_USERNAME" | jq ".Parameters[0].Value" | tr -d \"`
export DB_NAME=`aws ssm get-parameters --name "$PREFIX/DB_NAME" | jq ".Parameters[0].Value" | tr -d \"`
export UI_HOST=`aws ssm get-parameters --name "$PREFIX/UI_HOST" | jq ".Parameters[0].Value" | tr -d \"`
export MOVIE_SERVICE_HOST=`aws ssm get-parameters --name "$PREFIX/MOVIE_SERVICE_HOST" | jq ".Parameters[0].Value" | tr -d \"`
export BACKEND_PORT=`aws ssm get-parameters --name "$PREFIX/BACKEND_PORT" | jq ".Parameters[0].Value" | tr -d \"`
export POSTGRES_PASSWORD=`aws ssm get-parameters --name "$PREFIX/POSTGRES_PASSWORD" | jq ".Parameters[0].Value" | tr -d \"`
export VERSION=`aws ssm get-parameters --name "$PREFIX/VERSION" | jq ".Parameters[0].Value" | tr -d \"`
export BOOKING_IMAGE=`aws ssm get-parameters --name "$PREFIX/BOOKING_IMAGE" | jq ".Parameters[0].Value" | tr -d \"`
export REGISTRY_ID=`aws ssm get-parameters --name "$PREFIX/REGISTRY_ID" | jq ".Parameters[0].Value" | tr -d \"`

env > /home/ec2-user/envs_available_at_deploytime
echo "Logging into ECR"
$(aws ecr get-login --no-include-email --registry-ids $REGISTRY_ID)
/home/ec2-user/bin/docker-compose down || true
/home/ec2-user/bin/docker-compose up
