#!/bin/sh -f

ENGINE="$1"
SUBFOLDER="$1"

if [ "x$SUBFOLDER" = "xinnodb" ]
then
   SUBFOLDER="mysql"
fi

mkdir -p ../sql/$SUBFOLDER
#tedia2sql -i ../../docs/queryj-sql-per.dia -o ../sql/$1/queryjsql -t $1 -s -k -d
perl ../tedia2sql/tedia2sql -i ../../docs/queryj-sql-per.dia -o ../sql/$SUBFOLDER/queryjsql -t $ENGINE -s -d
