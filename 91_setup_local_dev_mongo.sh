#!/bin/bash
source 90_set_local_env.sh
#docker run -d -p 27016:27017 \
#  --name "$MONGO_LOCAL_CONTAINER_NAME" \
#  -v "${CURRENT_DIR}"/movies-db/mongod_local.conf:/etc/mongo/mongod.conf:ro \
#  mongo:5.0.6 \
#  mongod --config /etc/mongo/mongod.conf


docker run -d \
  -p 27016:27017 \
  --name "$MONGO_LOCAL_CONTAINER_NAME" \
	-e MONGO_INITDB_ROOT_USERNAME=mongoadmin \
	-e MONGO_INITDB_ROOT_PASSWORD=secret \
	-v "${CURRENT_DIR}"/movies-db/mongod_local_no_auth.conf:/etc/mongo/mongod.conf:ro \
	mongo:5.0.6 \
	mongod --config /etc/mongo/mongod.conf

#docker run -it --rm \
#  mongo \
#	mongosh --host localhost \
#		-u mongoadmin \
#		-p secret \
#		--authenticationDatabase admin \
#		some-db