#!/bin/sh

echo "This script is under development and not meant to be used for productive usage"


echo "Create build directory"
mkdir build


echo "Build Apache httpd"
cd build
BUILD_DIR=`pwd`
#tar -xzf ../tools/loadbalancing-cluster/lbs/httpd-2.1.9-beta.tar.gz
#mv httpd-2.1.9-beta httpd-2.1.9-beta-src
#cd httpd-2.1.9-beta-src
#sh configure --prefix=$BUILD_DIR/httpd-2.1.9-beta
#make
#make install
#cd ..
#rm -r httpd-2.1.9-beta-src
cd ..
##diff tools/loadbalancing-cluster/lbs/httpd.conf build/httpd-2.1.9-beta/conf/httpd.conf
sed -e 's\@PREFIX@\/home/michi/src/erp/trunk/build/httpd-2.1.9-beta\g' tools/loadbalancing-cluster/lbs/httpd.conf > build/httpd-2.1.9-beta/conf/httpd.conf
##sed -e 's\@PREFIX@\$BUILD_DIR/httpd-2.1.9-beta\g' tools/loadbalancing-cluster/lbs/httpd.conf > build/httpd-2.1.9-beta/conf/httpd.conf
cp tools/loadbalancing-cluster/lbs/httpd-vhosts.conf build/httpd-2.1.9-beta/conf/extra/httpd-vhosts.conf
cp tools/loadbalancing-cluster/lbs/workers.properties build/httpd-2.1.9-beta/conf/.
