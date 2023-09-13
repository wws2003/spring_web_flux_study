#!/bin/bash
source 90_set_local_env.sh
docker restart "$MONGO_LOCAL_CONTAINER_NAME"
