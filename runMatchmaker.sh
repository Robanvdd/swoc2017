#!/bin/sh

if [ $(pidof -x runMatchmaker.sh | wc -w) -gt 2 ]; then
	echo Matchmaker already running
	exit
fi

cd /home/bitnami/swoc2017/test/matchmaker/build/jar
java -jar matchmaker.jar swoc-dev
