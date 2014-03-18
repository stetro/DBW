#!/bin/sh
#
# run this script only in this scripts folder!

xjc $PWD/../definitions/products.xsd -d $PWD/../../java/ -p de.stetro.matin.dbw.prak1.entities.products
xjc $PWD/../definitions/employees.xsd -d $PWD/../../java/ -p de.stetro.matin.dbw.prak1.entities.employees
