#!/bin/bash
while true; do
  docker-compose restart app1
  sleep 1800
  docker-compose restart app2
  sleep 1800
done