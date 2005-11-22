#!/bin/sh

echo "WARNING: This script is still under development and not ready for productive usage"

echo "Startup Apache httpd as Load Balancer"
./build/httpd-2.1.9-beta/bin/apachectl start

echo "Startup Tomcat as Cluster"
chmod -R 755 build/jakarta-tomcat-5.0.30-cnode1/bin
./build/jakarta-tomcat-5.0.30-cnode1/bin/startup.sh
chmod -R 755 build/jakarta-tomcat-5.0.30-cnode2/bin
./build/jakarta-tomcat-5.0.30-cnode2/bin/startup.sh
