#!/bin/sh

if [ $(pidof -x runMatchmaker.sh | wc -w) -gt 2 ]; then
	echo Matchmaker already running
	exit
fi

java -jar /var/lib/jenkins/workspace/engine+matchmaker/dist/matchmaker.jar swoc-dev
