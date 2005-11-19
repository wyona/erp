$!/*
$! * Copyright 2004 Hewlett Packard Development Company.
$! * 
$! * Licensed under the Apache License, Version 2.0 (the "License");
$! * you may not use this file except in compliance with the License.
$! * You may obtain a copy of the License at
$! * 
$! *      http://www.apache.org/licenses/LICENSE-2.0
$! * 
$! * Unless required by applicable law or agreed to in writing, software
$! * distributed under the License is distributed on an "AS IS" BASIS,
$! * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
$! * See the License for the specific language governing permissions and
$! * limitations under the License.
$! */ 
$!
$! File name:      TOMCAT_Setup_Java.com
$! Version:        V1.0
$!
$!
$!
$! First, set up the TZ logical so that the modification dates for files will
$! be correctly returned.
$!
$! This code will be obsolete after version 1.4.2-1 of Java.
$!
$ if f$trnlnm("TZ") .nes. "" then goto tz_defined
$ loctime = f$trnlnm("SYS$LOCALTIME") - "SYS$SYSROOT:[SYS$ZONEINFO.SYSTEM."
$ bpos = f$locate("]", loctime)
$ if (bpos .eq. f$length(loctime)) then goto loctime_err
$ loctime[bpos,1] := "/" 
$ loctime = loctime - "."
$ pluspos = f$locate("PLUS", loctime)
$ if (pluspos .eq. f$length(loctime)) then goto noplus
$ loctime = loctime - "LUS"
$ loctime[pluspos,1] := "+"
$ noplus:
$ write sys$output "Defining TZ as ''loctime'"
$ define TZ "''loctime'"
$ goto tz_defined
$!
$ err:
$ write sys$output "Error, no closing bracket in ''loctime'.  Cannot define TZ logical"
$ tz_defined:
$!
$! TOMCAT$JDK_HOME, if defined, should point to the base directory of a Java
$! kit. This is used to locate things like the Java setup command procedure,
$! tools.jar, etc.
$
$ default_java_version = 142
$ gosub set_dclcmds
$ jdk_home = f$trnlnm("TOMCAT$JDK_HOME")
$ if default_java_version .lt. 142 
$ then
$   if jdk_home .eqs. ""
$   then
$     jdk_home = "sys$common:[java$''default_java_version']"
$   endif
$ else
$   if jdk_home .eqs. ""
$   then
$     if f$search("sys$manager:java$''default_java_version'_setup.com") .eqs. "" Then goto No_Java
$     open/read js sys$manager:java$'default_java_version'_setup.com
$     read js line
$     close js
$     ap = f$locate("@", line)
$     bp = f$locate("]", line)
$     ds = f$extract(ap+1,bp-ap,line)
$     ds = ds - ".COM]" + "]"
$     jdk_home = ds
$   endif
$ endif
$
$ jdk_root == jdk_home - "]"
$
$! Check if the Java setup command procedure from a different version of the
$! JDK that we'll be using was previously executed. We do this by seeing if
$! the symbol JAVA is defined and, if it is, extracting the Java version from
$! the symbol's value. If possible, we'll run the CANCEL_SETUP procedure.
$! If we can;t figure out how to do that, we'll exit.
$!
$ if f$type(java) .nes. ""
$ then
$   java_version = f$edit(f$parse(java,,, "DIRECTORY"), "UPCASE")
$   js = f$locate("JAVA$", java_version)
$   java_version = f$extract(js, 8, java_version)
$   jdk_version = f$edit(f$parse(jdk_home,,, "DIRECTORY"), "UPCASE")
$   js = f$locate("JAVA$", jdk_version)
$   jdk_version = f$extract(js, 8, jdk_version)
$   if java_version .nes. jdk_version
$   then
$     cancel_procedure = (jdk_home - "]") + ".COM]" +java_version + "_CANCEL_SETUP.COM"
$     if f$search("''cancel_procedure'") .nes. ""
$     then
$       cancel_procedure = f$edit("''cancel_procedure'","UPCASE")
$       write sys$output "Calling Java cancel setup procedure ''cancel_procedure'"
$       @'cancel_procedure'
$     else
$       write sys$output ""
$       write sys$output "Your process has symbol and logical name definitions that were created by a"
$       write sys$output "different version of Java than what will be used by TOMCAT. To run TOMCAT,"
$       write sys$output "you must execute a command procedure which will reset your process so that the"
$       write sys$output "required version of Java will run properly. The command procedure name is:"
$       write sys$output java_version, "_CANCEL_SETUP.COM"
$       write sys$output ""
$       write sys$output "If you do not have this file on your system, please refer to the "
$       write sys$output "release notes for information on obtaining this file."
$       write sys$output ""
$       exit
$     endif
$   endif
$ endif
$


$ setup_file = F$SEARCH("''jdk_root'.com]java$%%%_setup.com")
$ write sys$output "Calling Java setup procedure ''setup_file'"
$ @'setup_file' 'P1'
$!
$! Define the various JAVA and DECC logicals...
$!
$ define JAVA$DELETE_ALL_VERSIONS             true
$ define JAVA$RENAME_ALL_VERSIONS             true
$ define JAVA$CREATE_ONE_VERSION              true
$ define JAVA$CREATE_DIR_WITH_OWNER_DELETE    true
$ define JAVA$READDIR_CASE_DISABLE            true
$ define JAVA$DISABLE_MULTIDOT_DIRECTORY_STAT true
$ define JAVA$DAEMONIZE_MAIN_THREAD           true
$ define JAVA$TIMED_READ_USE_QIO              true
$ define JAVA$CACHING_INTERVAL                10
$ define JAVA$FSYNC_INTERVAL                  5
$ define JAVA$CACHING_DIRECTORY               1
$
$! Enable unix-like file sharing
$!
$ define JAVA$FILE_OPEN_MODE 3
$!
$! For forking, use sockets
$!
$ Define JAVA$FORK_PIPE_STYLE 2
$!
$! Now set up the JAVA file control bits
$!
$! Set file controls for ODS-5
$ JAVA$M_UNIX_AND_VMS  = 8
$ option_string = JAVA$M_UNIX_AND_VMS
$ define JAVA$FILENAME_CONTROLS 'option_string'
$
$! Define the DECC logicals necessary to run TOMCAT 
$ define  DECC$ARGV_PARSE_STYLE            ENABLE
$ define  DECC$EFS_CASE_PRESERVE           ENABLE
$ define  DECC$POSIX_SEEK_STREAM_FILE      ENABLE
$ define  DECC$EFS_CHARSET                 ENABLE
$ define  DECC$ENABLE_GETENV_CACHE         ENABLE
$ define  DECC$FILE_PERMISSION_UNIX        ENABLE
$ define  DECC$FIXED_LENGTH_SEEK_TO_EOF    ENABLE
$ define  DECC$RENAME_NO_INHERIT           ENABLE
$ define  DECC$ENABLE_TO_VMS_LOGNAME_CACHE ENABLE
$ define  DECC$EFS_NO_DOTS_IN_DIRNAME      ENABLE
$!
$ exit
$ no_java:
$ write sys$output ""
$ write sys$output "  ** Error -- the default version of java, ''default_java_version'  is not installed on"
$ write sys$output "  **          this machine.  Please define the logical name TOMCAT$JDK_HOME as described"
$ write sys$output "  **          in the release notes and online help and then start TOMCAT."
$ write sys$output ""
$
$============================================================================
$ !
$ ! insure dcl commands aren't redefined by user
$ !
$ set_dclcmds:
$     call     := call
$     close    := close
$     copy     := copy
$     create   := create
$     deassign := deassign
$! Allow our redefinition of define to stand
$!     define   := define
$     delete   := delete
$     exit     := exit
$     gosub    := gosub
$     goto     := goto
$     inquire  := inquire
$     install  := install
$     open     := open
$     pipe     := pipe
$     purge    := purge
$     read     := read
$     rename   := rename
$     return   := return
$     run      := run
$     search   := search
$     set      := set
$     show     := show
$     start    := start
$     stop     := stop
$     type     := type
$     wait     := wait
$     write    := write
$     return
