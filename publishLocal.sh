#!/usr/bin/env bash

cd `dirname $0`
echo "`cat ./version.sbt` ? [Y/n]: "
read answer
case $answer in
  "" | "Y" | "y" | "yes" | "Yes" | "YES" )
    sbt clean +publishLocal;;
  * )
    exit 1;;
esac