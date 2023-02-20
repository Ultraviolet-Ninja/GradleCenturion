#!/bin/bash
jlink \
--module-path src/main/java \
--output base-layer \
#--add-modules java.base,javafx.controls,javafx.fxml,\
#opencsv,commons-lang3 \

#--compress=2 \
#--no-header-files \
#--no-man-pages \
#--strip-debug