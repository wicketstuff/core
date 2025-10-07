#!/bin/bash

core_js=./jquery-ui-1.13.2
core_themes=./jquery-ui-themes-1.13.2/themes

repo=/home/sebastien/Github/wicket-jquery-ui-8.x
repo_js=$repo/wicket-jquery-ui/src/main/resources/com/googlecode/wicket/jquery/ui/resource
repo_themes_prefix=$repo/wicket-jquery-ui-themes
repo_themes_suffix=src/main/resources/com/googlecode/wicket/jquery/ui/theme
repo_themes_suffix_images=$repo_themes_suffix/images

#js
cp $core_js/jquery-ui.js $repo_js
cp $core_js/jquery-ui.min.js $repo_js

#themes
for theme in base black-tie blitzer cupertino dark-hive dot-luv eggplant excite-bike flick hot-sneaks humanity le-frog mint-choc overcast pepper-grinder redmond smoothness south-street start sunny swanky-purse trontastic ui-darkness ui-lightness vader
do
  rm $repo_themes_prefix/theme-$theme/$repo_themes_suffix/*.css
  rm $repo_themes_prefix/theme-$theme/$repo_themes_suffix_images/*
  cp -r $core_themes/$theme/jquery* $repo_themes_prefix/theme-$theme/$repo_themes_suffix/
  cp -r $core_themes/$theme/images/ $repo_themes_prefix/theme-$theme/$repo_themes_suffix/
done
