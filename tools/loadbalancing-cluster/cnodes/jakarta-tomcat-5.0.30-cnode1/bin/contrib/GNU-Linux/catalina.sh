#!/bin/bash
#
# Startup script for the Tomcat Servlet Container
#
# chkconfig: - 86 14
# description: Tomcat is the servlet container that is used in the official \
#              Reference Implementation for the Java Servlet and JavaServer \
#              Pages technologies
 
# Source function library.
if [ -x /etc/rc.d/init.d/functions ]; then
    . /etc/rc.d/init.d/functions
else
    echo_success() {
        echo -n "[  OK  ]"
            echo -ne "\r"
                return 0
                }
                echo_failure() {
                    echo -n "[FAILED]"
                        echo -ne "\r"
                            return 1
                            }
fi
prog=tomcat
TOMCAT_HOME=/opt/tomcat

start() {
    echo -n $"Starting $prog: "
    su - root -c $TOMCAT_HOME/bin/startup.sh > /dev/null
    RETVAL=$?
    if [ $RETVAL -eq 0 ]; then
        echo_success
        else
            echo_failure
            fi
            echo
            [ $RETVAL = 0 ] && touch /var/lock/subsys/$prog
            return $RETVAL
}
stop() {
    echo -n $"Stopping $prog: "
    su - root -c $TOMCAT_HOME/bin/shutdown.sh > /dev/null
    RETVAL=$?
    if [ $RETVAL -eq 0 ]; then
        echo_success
        else
            echo_failure
            fi
            echo
            [ $RETVAL = 0 ] && rm -f /var/lock/subsys/$prog
            return $RETVAL
}

# See how we were called.
case "$1" in
  start)
    start
    ;;
  stop)
    stop
    ;;
  restart)
    stop
    start
    ;;
  status)
    INSTANCES=`ps --columns 512 -aef|grep java|grep tomcat|grep 
org.apache.catalina.startup.Bootstrap|wc -l`
        if [ $INSTANCES -eq 0 ]; then
            echo $prog is stopped
                REVAL=3
                else
                        if [ $INSTANCES -eq 1 ]; then
                                echo $prog is running 1 instance...
                                    else
                                            echo $prog is running $INSTANCES instances...
                                                fi
                                                    REVAL=0
                                                    fi
                                                    ;;
  *)
    echo $"Usage: $prog {start|stop|restart|status|help}"
    exit 1
esac

exit $RETVAL
