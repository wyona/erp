#!/bin/sh

echo "This script is under development and not meant to be used for productive usage"

diff tools/loadbalancing-cluster/lbs/httpd.conf build/httpd-2.1.9-beta/conf/httpd.conf
cp tools/loadbalancing-cluster/lbs/httpd-vhosts.conf build/httpd-2.1.9-beta/conf/extra/httpd-vhosts.conf
cp tools/loadbalancing-cluster/lbs/workers.properties build/httpd-2.1.9-beta/conf/.
