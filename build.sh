#!/bin/sh -f

usage () {
  echo "Usage $0 [-d] <ant target>"
  echo "Calls given Ant target, managing the directory structure."
  echo "Options: -d Execute Maven to manage the dependencies."
  exit 1;
}

[ -z "$1" ] && usage

basedir=`pwd`
if [[ ! -z "$_DIR" ]]; then
  basedir=$_DIR
fi
if  [[ "x$1" == "x-d" ]]; then
shift
[ -z "$1" ] && usage
for last_command in "cd ${basedir}/src/toolconf/maven && mkdir -p ../emacs 2> /dev/null" "cd ${basedir}/src/toolconf/maven && maven $ACM_OPTS antdep"; do
  echo "$last_command" | sh
  if [[ ! $? == 0 ]]; then
    exit 1
  fi
done;
fi
for last_command in "cd ${basedir}/src/toolconf/ant && ant -f dependencies.xml" "cd ${basedir}/src/toolconf/ant && ant $*"; do
  echo "Executing $last_command"
  echo "$last_command" | sh
  if [[ ! $? == 0 ]]; then
    exit 1
  fi
done;
