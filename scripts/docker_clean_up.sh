#!/usr/bin/env bash

 docker rm -v $(docker ps -a -q -f status=exited)
 docker rmi $(docker images -f "dangling=true" -q)
 docker volume rm $(docker volume ls -qf dangling=true)
 docker network rm nodeimpl_smoke_test_network node_connect_test_network