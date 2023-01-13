#!/bin/sh
i=0
while true
do
	i=`expr $i + 1 `
	echo value of \$i is :${i}
	sleep 1
done