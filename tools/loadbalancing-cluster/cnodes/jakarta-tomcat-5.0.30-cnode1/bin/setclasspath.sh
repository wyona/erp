# -----------------------------------------------------------------------------
#  Set CLASSPATH and Java options
#
#  $Id: setclasspath.sh,v 1.7.2.3 2004/11/17 20:06:34 yoavs Exp $
# -----------------------------------------------------------------------------

# Make sure prerequisite environment variables are set
if [ -z "$JAVA_HOME" ]; then
  echo "The JAVA_HOME environment variable is not defined"
  echo "This environment variable is needed to run this program"
  exit 1
fi
if [ "$os400" = "true" ]; then
  if [ ! -x "$JAVA_HOME"/bin/java -o ! -x "$JAVA_HOME"/bin/javac ]; then
    echo "The JAVA_HOME environment variable is not defined correctly"
    echo "This environment variable is needed to run this program"
    echo "NB: JAVA_HOME should point to a JDK not a JRE"
    exit 1
  fi
else
  if [ ! -x "$JAVA_HOME"/bin/java -o ! -x "$JAVA_HOME"/bin/jdb -o ! -x "$JAVA_HOME"/bin/javac ]; then
    echo "The JAVA_HOME environment variable is not defined correctly"
    echo "This environment variable is needed to run this program"
    echo "NB: JAVA_HOME should point to a JDK not a JRE"
    exit 1
  fi
fi
if [ -z "$BASEDIR" ]; then
  echo "The BASEDIR environment variable is not defined"
  echo "This environment variable is needed to run this program"
  exit 1
fi
if [ ! -x "$BASEDIR"/bin/setclasspath.sh ]; then
  if $os400; then
    # -x will Only work on the os400 if the files are:
    # 1. owned by the user
    # 2. owned by the PRIMARY group of the user
    # this will not work if the user belongs in secondary groups 
    eval
  else
    echo "The BASEDIR environment variable is not defined correctly"
    echo "This environment variable is needed to run this program"
    exit 1
  fi
fi

# Set the default -Djava.endorsed.dirs argument
JAVA_ENDORSED_DIRS="$BASEDIR"/common/endorsed

# Set standard CLASSPATH
CLASSPATH="$JAVA_HOME"/lib/tools.jar

# OSX hack to CLASSPATH
JIKESPATH=
if [ `uname -s` = "Darwin" ]; then
  OSXHACK="/System/Library/Frameworks/JavaVM.framework/Versions/CurrentJDK/Classes"
  if [ -d "$OSXHACK" ]; then
    for i in "$OSXHACK"/*.jar; do
      JIKESPATH="$JIKESPATH":"$i"
    done
  fi
fi

# Set standard commands for invoking Java.
_RUNJAVA="$JAVA_HOME"/bin/java
if [ "$os400" != "true" ]; then
  _RUNJDB="$JAVA_HOME"/bin/jdb
fi
_RUNJAVAC="$JAVA_HOME"/bin/javac
