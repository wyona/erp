#!/bin/sh

echo "WARNING: This script is still under development and not ready for productive usage"

echo "Shutdown Apache httpd as Load Balancer"
./build/httpd-2.1.9-beta/bin/apachectl stop

echo "Shutdown Tomcat as Cluster"
./build/jakarta-tomcat-5.0.30-cnode1/bin/shutdown.sh
./build/jakarta-tomcat-5.0.30-cnode2/bin/shutdown.sh
