#!/bin/sh

echo "WARNING: This script is still under development and not ready for productive usage"

echo "Startup Apache httpd as Load Balancer"
./build/httpd-2.1.9-beta/bin/apachectl start
