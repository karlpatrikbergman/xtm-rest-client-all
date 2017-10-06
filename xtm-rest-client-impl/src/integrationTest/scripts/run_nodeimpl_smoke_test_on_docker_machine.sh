#!/usr/bin/env bash

# Run from project root as
# ./xtm-rest-client-impl/src/integrationTest/scripts/run_nodeimpl_smoke_test_on_docker_machine.sh
./gradlew clean xtm-rest-client-impl:integrationTest \
    -DintegrationTest.single=NodeImplSmokeTest \
    -PdockerMachineHost=tcp://172.16.15.232:2376 \
    -PdockerMachineSshDirectory=${HOME}/.docker/machine/machines/centos-docker-machine-1 \
    -PshutdownStrategy=SKIP