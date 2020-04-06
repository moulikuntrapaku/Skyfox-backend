#!/bin/bash

set -v
export TAG=$3

# used  in compose file
export COMMIT_SHA=$3
export ENVIRONMENT=$4

export BOOKING_IMAGE=$1:$COMMIT_SHA-$ENVIRONMENT
export POSTGRES_IMAGE=$2:$COMMIT_SHA-$ENVIRONMENT
export BACKEND_PORT=$ENV_BACKEND_PORT
export DB_PORT=$ENV_DB_PORT_CONNECT
export HOST_DB_PORT=$ENV_DB_PORT
export DB_HOST=$ENV_DB_HOST

apt-get install jq -y
curl -o /usr/local/bin/ecs-cli https://amazon-ecs-cli.s3.amazonaws.com/ecs-cli-linux-amd64-latest
echo "$(curl -s https://amazon-ecs-cli.s3.amazonaws.com/ecs-cli-linux-amd64-latest.md5) /usr/local/bin/ecs-cli" | md5sum -c -
chmod +x /usr/local/bin/ecs-cli

ecs-cli configure --cluster "$CLUSTER_NAME" --default-launch-type EC2 --config-name "$CLUSTER_CONFIG_NAME" --region ap-south-1
ecs-cli configure profile --access-key "$AWS_ACCESS_KEY" --secret-key "$AWS_SECRET_KEY" --profile-name "$CLUSTER_PROFILE_NAME"

./aws_check_cluster_status.sh

ecs-cli registry-creds up ./creds_input_file.yml --role-name catalystSecretsExecutionRoleCI

echo  "using image.. $BOOKING_IMAGE"

ecs-cli compose --project-name "$ENVIRONMENT" down --cluster-config "$CLUSTER_CONFIG_NAME" --ecs-profile "$CLUSTER_PROFILE_NAME"
ecs-cli ps --cluster-config "$CLUSTER_CONFIG_NAME" --ecs-profile "$CLUSTER_PROFILE_NAME"

ecs-cli compose --project-name "$ENVIRONMENT" --cluster-config "$CLUSTER_CONFIG_NAME" --ecs-profile "$CLUSTER_PROFILE_NAME" up --create-log-groups
ecs-cli ps --cluster-config "$CLUSTER_CONFIG_NAME" --ecs-profile "$CLUSTER_PROFILE_NAME"

rm -rf ecs-registry-creds_*.yml

curl http://$EC2_HOST:$ENV_BACKEND_PORT/version

set +v