#!/bin/sh

if [ $(pidof -x runMatchmaker.sh | wc -w) -gt 2 ]; then
	echo Matchmaker already running
	exit
fi

cd /var/lib/jenkins/workspace/engine+matchmaker/dist
java -jar gos-engine.jar swoc-dev
