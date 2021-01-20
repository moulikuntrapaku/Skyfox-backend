#!/bin/bash
set -e
set -v

IFS='-' read -ra identifiers <<< $DEPLOYMENT_GROUP_NAME  # DEPLOYMENT_GROUP_NAME is of form neev-xx-team-xx-[backend|frontend]-deployment-[integration|staging|production]
export BATCH_ID=${identifiers[1]}
export TEAM_ID=${identifiers[3]}
export ENVIRONMENT=${identifiers[6]}
export PREFIX="/neev-$BATCH_ID/team-$TEAM_ID/$ENVIRONMENT"
export POSTGRES_IMAGE=`aws ssm get-parameter --name "$PREFIX/POSTGRES_IMAGE" | jq ".Parameters[0].Value"`
export HOST_DB_PORT=`aws ssm get-parameter --name "$PREFIX/HOST_DB_PORT" | jq ".Parameters[0].Value"`
export DB_HOST=`aws ssm get-parameter --name "$PREFIX/DB_HOST" | jq ".Parameters[0].Value"`
export DB_PORT=`aws ssm get-parameter --name "$PREFIX/DB_PORT" | jq ".Parameters[0].Value"`
export POSTGRES_USERNAME=`aws ssm get-parameter --name "$PREFIX/POSTGRES_USERNAME" | jq ".Parameters[0].Value"`
export DB_NAME=`aws ssm get-parameter --name "$PREFIX/DB_NAME" | jq ".Parameters[0].Value"`
export UI_HOST=`aws ssm get-parameter --name "$PREFIX/UI_HOST" | jq ".Parameters[0].Value"`
export MOVIE_SERVICE_HOST=`aws ssm get-parameter --name "$PREFIX/MOVIE_SERVICE_HOST" | jq ".Parameters[0].Value"`
export BACKEND_PORT=`aws ssm get-parameter --name "$PREFIX/BACKEND_PORT" | jq ".Parameters[0].Value"`
export POSTGRES_PASSWORD=`aws ssm get-parameter --name "$PREFIX/POSTGRES_PASSWORD" | jq ".Parameters[0].Value"`
export VERSION=`aws ssm put-parameter --name "$PREFIX/VERSION" | jq ".Parameters[0].Value"`
export BOOKING_IMAGE=`aws ssm put-parameter --name "$PREFIX/BOOKING_IMAGE" | jq ".Parameters[0].Value"`

env > /home/ec2-user/envs_available_at_deploytime
