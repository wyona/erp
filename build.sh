#!/bin/sh

echo "This script is under development and not meant to be used for productive usage"


echo "Create build directory"
mkdir build


echo "Build Apache httpd"
cd build
BUILD_DIR=`pwd`
tar -xzf ../tools/loadbalancing-cluster/lbs/httpd-2.1.9-beta.tar.gz
mv httpd-2.1.9-beta httpd-2.1.9-beta-src
cd httpd-2.1.9-beta-src
sh configure --prefix=$BUILD_DIR/httpd-2.1.9-beta --enable-proxy --enable-proxy-http --enable-proxy-balancer --enable-status --enable-proxy-ajp
make
make install
cd ..
rm -r httpd-2.1.9-beta-src
cd ..
##diff tools/loadbalancing-cluster/lbs/httpd.conf build/httpd-2.1.9-beta/conf/httpd.conf
sed -e s+@PREFIX@+`pwd`/build/httpd-2.1.9-beta+g tools/loadbalancing-cluster/lbs/httpd.conf > build/httpd-2.1.9-beta/conf/httpd.conf
##sed -e 's\@PREFIX@\$BUILD_DIR/httpd-2.1.9-beta\g' tools/loadbalancing-cluster/lbs/httpd.conf > build/httpd-2.1.9-beta/conf/httpd.conf
cp tools/loadbalancing-cluster/lbs/httpd-vhosts.conf build/httpd-2.1.9-beta/conf/extra/httpd-vhosts.conf
cp tools/loadbalancing-cluster/lbs/workers.properties build/httpd-2.1.9-beta/conf/.


echo "Build and install mod_jk"
cd build
tar -xzf ../tools/loadbalancing-cluster/lbs/jakarta-tomcat-connectors-1.2.15-src.tar.gz
cd jakarta-tomcat-connectors-1.2.15-src/jk/native
./configure --with-apxs=$BUILD_DIR/httpd-2.1.9-beta/bin/apxs
make
make install
cd ../../../..
rm -r build/jakarta-tomcat-connectors-1.2.15-src


echo "Install Tomcat"
cd build
tar -xzf ../tools/loadbalancing-cluster/cnodes/jakarta-tomcat-5.0.30.tar.gz
mv jakarta-tomcat-5.0.30 jakarta-tomcat-5.0.30-cnode1
cp -r jakarta-tomcat-5.0.30-cnode1 jakarta-tomcat-5.0.30-cnode2
cd ..
cp tools/loadbalancing-cluster/cnodes/jakarta-tomcat-5.0.30-cnode1/conf/server.xml build/jakarta-tomcat-5.0.30-cnode1/conf/.
cp tools/loadbalancing-cluster/cnodes/jakarta-tomcat-5.0.30-cnode2/conf/server.xml build/jakarta-tomcat-5.0.30-cnode2/conf/.
