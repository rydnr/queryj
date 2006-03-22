#!/bin/sh -f

usage() {
    echo "Usage $0 [trunk|<branch>]"
    echo "Checks out the trunk or a given branch of Queryj,"
    echo "and builds it afterwards."
    exit 1;
}

[ -z "$1" ] && usage

folder=$1

if  [[ ! "x$folder" == "xtrunk" ]]; then
  folder=branches/$folder
fi
echo "svn co svn://svn.acm-sl.org/var/svn/queryj/$folder" | sh
