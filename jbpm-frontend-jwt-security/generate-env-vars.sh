#!/bin/bash
PROPERTIES_FILE=./src/main/resources/application.properties
if [[ -f ${PROPERTIES_FILE} ]]; then
  cat ${PROPERTIES_FILE} | sed 's/=.*/=/g' | sed 's/^%.*//g'| sed 's/^#.*//g' | sed '/^$/d' | sed 's/-/_/g' | sed 's/\./_/g' | sed 's/[a-z]/\U&/g'
fi

