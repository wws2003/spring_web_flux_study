#!/bin/bash
source 90_set_local_env.sh
docker run -d -p 27016:27017 \
  --name "$MONGO_LOCAL_CONTAINER_NAME" \
  -v "${CURRENT_DIR}"/movies-db/mongod_local.conf:/etc/mongo/mongod.conf:ro \
  mongo:5.0.6 \
  mongod --config /etc/mongo/mongod.conf
