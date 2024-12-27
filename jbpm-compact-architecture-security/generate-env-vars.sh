#!/bin/bash
PROPERTIES_FILE=./src/main/resources/application.properties
if [[ -f ${PROPERTIES_FILE} ]]; then
  cat ${PROPERTIES_FILE} \
    | sed 's/=.*//g' \
    | sed 's/^%.*//g' \
    | sed 's/^#.*//g' \
    | sed '/^$/d' \
    | sed 's/-/_/g' \
    | sed 's/\//_/g' \
    | sed 's/\./_/g' \
    | sed 's/[a-z]/\U&/g' \
    | sed 's/^/            - name: /g' \
    | sort

echo "            - name: QUARKUS_DATASOURCE_JDBC_URL"
echo "            - name: QUARKUS_DATASOURCE_REACTIVE_URL"
echo "            - name: QUARKUS_DATASOURCE_USERNAME"
echo "            - name: QUARKUS_DATASOURCE_PASSWORD"

fi
