#!/bin/sh -f

usage() {
    echo "Usage $0 [trunk|<branch>]"
    echo "Checks out the trunk or a given branch of Queryj,"
    echo "and builds it afterwards."
    echo "Note: this script tries to delete any already-existing checkout."
    exit 1;
}

[ -z "$1" ] && usage

folder=$1

if  [[ ! "x$folder" == "xtrunk" ]]; then
  folder=branches/$folder
fi
if [[ -d $folder ]] ; then
  if [[ -d previous_build ]] ; then
    rm -rf previous_build
  fi
  if [[ ! -d previous_build ]] ; then
    mv $folder previous_build
  fi
fi

svn co svn://svn.acm-sl.org/var/svn/queryj/$folder >> /dev/null
