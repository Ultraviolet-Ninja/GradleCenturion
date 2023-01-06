#!/bin/bash
jlink \
--module-path src/main/java \
--add-modules java.base,javafx.controls,javafx.fxml,\
opencsv,commons-lang3 \
--output baselayer \
#--compress=2 \
#--no-header-files \
#--no-man-pages \
#--strip-debug