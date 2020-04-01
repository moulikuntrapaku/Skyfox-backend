#!/bin/bash

aws configure set aws_access_key_id $AWS_ACCESS_KEY
aws configure set aws_secret_access_key $AWS_SECRET_KEY

while true; do
    eval status=`aws ecs describe-clusters --cluster ec2-catalyst-cluster-ci --region ap-south-1 | jq .clusters[].status`
    if [ "$status" == "ACTIVE" ]; then
        break
    fi
    aws ecs describe-clusters --cluster ec2-catalyst-cluster-ci --region ap-south-1
    sleep 10
done