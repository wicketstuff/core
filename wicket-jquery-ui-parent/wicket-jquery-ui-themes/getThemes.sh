#!/bin/bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

JQUERY_UI_VER=1.13.3

rm -rf /tmp/jquery-ui-themes
mkdir /tmp/jquery-ui-themes
cd /tmp/jquery-ui-themes

wget https://jqueryui.com/resources/download/jquery-ui-themes-${JQUERY_UI_VER}.zip

unzip jquery-ui-themes-${JQUERY_UI_VER}.zip

cd jquery-ui-themes-${JQUERY_UI_VER}/themes

for theme in $(ls -1);
do
	if [ ${theme} = 'base' ]; then
		cp ${theme}/theme.css ${SCRIPT_DIR}/wicketstuff-jquery-ui-theme-${theme}/src/main/resources/org/wicketstuff/jquery/ui/theme/
	else
		cp -r ${theme}/* ${SCRIPT_DIR}/wicketstuff-jquery-ui-theme-${theme}/src/main/resources/org/wicketstuff/jquery/ui/theme/
	fi
done;
