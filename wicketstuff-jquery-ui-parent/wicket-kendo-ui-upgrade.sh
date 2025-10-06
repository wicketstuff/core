#!/bin/bash
set -e

# working_dir="target/kendo-ui"
# mkdir -p "$working_dir"
#
# Download the Kendo UI source zip into the working directory
# kendo_ui_version="2025.3.1002"
# kendo_ui_src_url=https://github.com/telerik/kendo-ui-core/archive/refs/tags/${kendo_ui_version}.zip
# kendo_ui_src_file="$working_dir/kendo-ui.zip"
# curl -L -o "$kendo_ui_src_file" "$kendo_ui_src_url"
# 
# unzip "$kendo_ui_src_file" -d "$working_dir"
# 
# cd "kendo-ui-core-${kendo_ui_version}"
# 
# npm run build
#
# exit 0

core=./kendoui.2025.3.1002.core
repo=.

core_js=$core/js
core_themes=$core/styles
core_themes_fonts=$core_themes/fonts
core_themes_textures=$core_themes/textures

repo_js=$repo/wicket-kendo-ui/src/main/resources/com/googlecode/wicket/kendo/ui/resource
repo_cultures=$repo/wicketstuff-kendo-ui-culture/src/main/resources/com/googlecode/wicket/kendo/ui/resource/cultures
repo_messages=$repo/wicketstuff-kendo-ui-culture/src/main/resources/com/googlecode/wicket/kendo/ui/resource/messages
repo_themes_root=$repo/wicketstuff-kendo-ui-themes
repo_themes_suffix=src/main/resources/com/googlecode/wicket/kendo/ui/theme
repo_themes_fonts_suffix=$repo_themes_suffix/fonts
repo_themes_textures_suffix=$repo_themes_suffix/textures

#js + map
cp $core_js/kendo.ui.core.min.js* $repo_js/

#cultures
cp $core_js/cultures/*.min.js* $repo_cultures/
echo 'check if there is new cultures, and create enums if needed'

#messages
cp $core_js/messages/*.min.js* $repo_messages/
echo 'check if there is new messages, and create enums if needed'

#textures
for theme in theme-black theme-blue-opal theme-bootstrap theme-default theme-fiori theme-flat theme-high-contrast theme-material theme-material-black theme-metro theme-metro-black theme-moonlight theme-nova theme-office365 theme-silver theme-uniform
do
rm -r $repo_themes_root/$theme/$repo_themes_fonts_suffix/*
cp -r $core_themes_fonts/* $repo_themes_root/$theme/$repo_themes_fonts_suffix/

rm $repo_themes_root/$theme/$repo_themes_textures_suffix/*
cp $core_themes_textures/* $repo_themes_root/$theme/$repo_themes_textures_suffix/
done

#theme-black
rm $repo_themes_root/theme-black/$repo_themes_suffix/Black/*
cp $core_themes/Black/* $repo_themes_root/theme-black/$repo_themes_suffix/Black/
cp $core_themes/kendo.common.min.css* $repo_themes_root/theme-black/$repo_themes_suffix/
cp $core_themes/kendo.black.min.css* $repo_themes_root/theme-black/$repo_themes_suffix/
cp $core_themes/kendo.black.mobile.min.css* $repo_themes_root/theme-black/$repo_themes_suffix/

#theme-blue-opal
rm $repo_themes_root/theme-blue-opal/$repo_themes_suffix/BlueOpal/*
cp $core_themes/BlueOpal/* $repo_themes_root/theme-blue-opal/$repo_themes_suffix/BlueOpal/
cp $core_themes/kendo.common.min.css* $repo_themes_root/theme-blue-opal/$repo_themes_suffix/
cp $core_themes/kendo.blueopal.min.css* $repo_themes_root/theme-blue-opal/$repo_themes_suffix/
cp $core_themes/kendo.blueopal.mobile.min.css* $repo_themes_root/theme-blue-opal/$repo_themes_suffix/

#theme-bootstrap
rm $repo_themes_root/theme-bootstrap/$repo_themes_suffix/Bootstrap/*
cp $core_themes/Bootstrap/* $repo_themes_root/theme-bootstrap/$repo_themes_suffix/Bootstrap/
cp $core_themes/kendo.common-bootstrap.min.css* $repo_themes_root/theme-bootstrap/$repo_themes_suffix/
cp $core_themes/kendo.bootstrap.min.css* $repo_themes_root/theme-bootstrap/$repo_themes_suffix/
cp $core_themes/kendo.bootstrap.mobile.min.css* $repo_themes_root/theme-bootstrap/$repo_themes_suffix/

#theme-default
rm $repo_themes_root/theme-default/$repo_themes_suffix/Default/*
cp $core_themes/Default/* $repo_themes_root/theme-default/$repo_themes_suffix/Default/
cp $core_themes/kendo.common.min.css* $repo_themes_root/theme-default/$repo_themes_suffix/
cp $core_themes/kendo.default.min.css* $repo_themes_root/theme-default/$repo_themes_suffix/
cp $core_themes/kendo.default.mobile.min.css* $repo_themes_root/theme-default/$repo_themes_suffix/

#theme-fiori
rm $repo_themes_root/theme-fiori/$repo_themes_suffix/Fiori/*
cp $core_themes/Fiori/* $repo_themes_root/theme-fiori/$repo_themes_suffix/Fiori/
cp $core_themes/kendo.common-fiori.min.css* $repo_themes_root/theme-fiori/$repo_themes_suffix/
cp $core_themes/kendo.fiori.min.css* $repo_themes_root/theme-fiori/$repo_themes_suffix/
cp $core_themes/kendo.fiori.mobile.min.css* $repo_themes_root/theme-fiori/$repo_themes_suffix/

#theme-flat
rm $repo_themes_root/theme-flat/$repo_themes_suffix/Flat/*
cp $core_themes/Flat/* $repo_themes_root/theme-flat/$repo_themes_suffix/Flat/
cp $core_themes/kendo.common.min.css* $repo_themes_root/theme-flat/$repo_themes_suffix/
cp $core_themes/kendo.flat.min.css* $repo_themes_root/theme-flat/$repo_themes_suffix/
cp $core_themes/kendo.flat.mobile.min.css* $repo_themes_root/theme-flat/$repo_themes_suffix/

#theme-high-contrast
rm $repo_themes_root/theme-high-contrast/$repo_themes_suffix/HighContrast/*
cp $core_themes/HighContrast/* $repo_themes_root/theme-high-contrast/$repo_themes_suffix/HighContrast/
cp $core_themes/kendo.common.min.css* $repo_themes_root/theme-high-contrast/$repo_themes_suffix/
cp $core_themes/kendo.highcontrast.min.css* $repo_themes_root/theme-high-contrast/$repo_themes_suffix/
cp $core_themes/kendo.highcontrast.mobile.min.css* $repo_themes_root/theme-high-contrast/$repo_themes_suffix/

#theme-material
rm $repo_themes_root/theme-material/$repo_themes_suffix/Material/*
cp $core_themes/Material/* $repo_themes_root/theme-material/$repo_themes_suffix/Material/
cp $core_themes/kendo.common-material.min.css* $repo_themes_root/theme-material/$repo_themes_suffix/
cp $core_themes/kendo.material.min.css* $repo_themes_root/theme-material/$repo_themes_suffix/
cp $core_themes/kendo.material.mobile.min.css* $repo_themes_root/theme-material/$repo_themes_suffix/

#theme-material-black
rm $repo_themes_root/theme-material-black/$repo_themes_suffix/MaterialBlack/*
cp $core_themes/MaterialBlack/* $repo_themes_root/theme-material-black/$repo_themes_suffix/MaterialBlack/
cp $core_themes/kendo.common-material.min.css* $repo_themes_root/theme-material-black/$repo_themes_suffix/
cp $core_themes/kendo.materialblack.min.css* $repo_themes_root/theme-material-black/$repo_themes_suffix/
cp $core_themes/kendo.materialblack.mobile.min.css* $repo_themes_root/theme-material-black/$repo_themes_suffix/

#theme-metro
rm $repo_themes_root/theme-metro/$repo_themes_suffix/Metro/*
cp $core_themes/Metro/* $repo_themes_root/theme-metro/$repo_themes_suffix/Metro/
cp $core_themes/kendo.common.min.css* $repo_themes_root/theme-metro/$repo_themes_suffix/
cp $core_themes/kendo.metro.min.css* $repo_themes_root/theme-metro/$repo_themes_suffix/
cp $core_themes/kendo.metro.mobile.min.css* $repo_themes_root/theme-metro/$repo_themes_suffix/

#theme-metro-black
rm $repo_themes_root/theme-metro-black/$repo_themes_suffix/MetroBlack/*
cp $core_themes/MetroBlack/* $repo_themes_root/theme-metro-black/$repo_themes_suffix/MetroBlack/
cp $core_themes/kendo.common.min.css* $repo_themes_root/theme-metro-black/$repo_themes_suffix/
cp $core_themes/kendo.metroblack.min.css* $repo_themes_root/theme-metro-black/$repo_themes_suffix/
cp $core_themes/kendo.metroblack.mobile.min.css* $repo_themes_root/theme-metro-black/$repo_themes_suffix/

#theme-moonlight
rm $repo_themes_root/theme-moonlight/$repo_themes_suffix/Moonlight/*
cp $core_themes/Moonlight/* $repo_themes_root/theme-moonlight/$repo_themes_suffix/Moonlight/
cp $core_themes/kendo.common.min.css* $repo_themes_root/theme-moonlight/$repo_themes_suffix
cp $core_themes/kendo.moonlight.min.css* $repo_themes_root/theme-moonlight/$repo_themes_suffix
cp $core_themes/kendo.moonlight.mobile.min.css* $repo_themes_root/theme-moonlight/$repo_themes_suffix/

#theme-nova
rm $repo_themes_root/theme-nova/$repo_themes_suffix/Nova/*
cp $core_themes/Nova/* $repo_themes_root/theme-nova/$repo_themes_suffix/Nova/
cp $core_themes/kendo.common-nova.min.css* $repo_themes_root/theme-nova/$repo_themes_suffix
cp $core_themes/kendo.nova.min.css* $repo_themes_root/theme-nova/$repo_themes_suffix
cp $core_themes/kendo.nova.mobile.min.css* $repo_themes_root/theme-nova/$repo_themes_suffix/

#theme-office365
rm $repo_themes_root/theme-office365/$repo_themes_suffix/Office365/*
cp $core_themes/Office365/* $repo_themes_root/theme-office365/$repo_themes_suffix/Office365/
cp $core_themes/kendo.common-office365.min.css* $repo_themes_root/theme-office365/$repo_themes_suffix/
cp $core_themes/kendo.office365.min.css* $repo_themes_root/theme-office365/$repo_themes_suffix/
cp $core_themes/kendo.office365.mobile.min.css* $repo_themes_root/theme-office365/$repo_themes_suffix/

#theme-silver
rm $repo_themes_root/theme-silver/$repo_themes_suffix/Silver/*
cp $core_themes/Silver/* $repo_themes_root/theme-silver/$repo_themes_suffix/Silver/
cp $core_themes/kendo.common.min.css* $repo_themes_root/theme-silver/$repo_themes_suffix/
cp $core_themes/kendo.silver.min.css* $repo_themes_root/theme-silver/$repo_themes_suffix/
cp $core_themes/kendo.silver.mobile.min.css* $repo_themes_root/theme-silver/$repo_themes_suffix/

#theme-uniform
rm $repo_themes_root/theme-uniform/$repo_themes_suffix/Uniform/*
cp $core_themes/Uniform/* $repo_themes_root/theme-uniform/$repo_themes_suffix/Uniform/
cp $core_themes/kendo.common.min.css* $repo_themes_root/theme-uniform/$repo_themes_suffix/
cp $core_themes/kendo.uniform.min.css* $repo_themes_root/theme-uniform/$repo_themes_suffix/
cp $core_themes/kendo.uniform.mobile.min.css* $repo_themes_root/theme-uniform/$repo_themes_suffix/
