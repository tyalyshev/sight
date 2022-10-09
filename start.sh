#!/bin/bash

#
NETWORK_NAME=app-network
#
echo 'Create PGSQL data folder'
mkdir db/data

echo 'Create network' ${NETWORK_NAME}
if [ -z $(docker network ls --filter name=^${NETWORK_NAME}$ --format="{{ .Name }}") ] ; then
     docker network create ${NETWORK_NAME} ;
fi

export DOCKER_BUILDKIT=1
export BUILDKIT_PROGRESS=plain

echo 'Containers run'
docker-compose up -d --build --force-recreate
