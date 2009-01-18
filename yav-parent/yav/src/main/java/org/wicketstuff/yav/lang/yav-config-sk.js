/***********************************************************************
 * YAV - Yet Another Validator  v2.0                                   *
 * Copyright (C) 2005-2008                                             *
 * Author: Federico Crivellaro <f.crivellaro@gmail.com>                *
 * WWW: http://yav.sourceforge.net                                     *
 ***********************************************************************/

var yav_config = {

// CHANGE THESE VARIABLES FOR YOUR OWN SETUP

// if you want yav to highligh fields with errors
inputhighlight : true,
// if you want to use multiple class names
multipleclassname : true,
// classname you want for the error highlighting
inputclasserror : 'inputError',
// classname you want for your fields without highlighting
inputclassnormal : 'inputNormal',
// classname you want for the inner error highlighting
innererror : 'innerError',
// classname you want for the inner help highlighting
innerhelp : 'innerHelp',
// div name where errors (and help) will appear (or where jsVar variable is dinamically defined)
errorsdiv : 'errorsDiv',
// if you want yav to alert you for javascript errors (only for developers)
debugmode : false,
// if you want yav to trim the strings
trimenabled : true,

// change to set your own decimal separator and your date format
DECIMAL_SEP : ' ',
THOUSAND_SEP : ',',
DATE_FORMAT : 'dd.MM.yyyy',

// change to set your own rules based on regular expressions
alphabetic_regex : "^[A-Za-z]*$",
alphanumeric_regex : "^[A-Za-z0-9]*$",
alnumhyphen_regex : "^[A-Za-z0-9\-_]*$",
alnumhyphenat_regex : "^[A-Za-z0-9\-_@]*$",
alphaspace_regex : "^[A-Za-z0-9\-_ \n\r\t]*$",
email_regex : "^(([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}){0,1}$",

// change to set your own rule separator
RULE_SEP : '|',

// change these strings for your own translation (do not change {n} values!)
HEADER_MSG : 'NeplatnÈ d·ta:',
FOOTER_MSG : 'Sk˙ste znova.',
DEFAULT_MSG : 'D·ta s˙ neplatnÈ.',
REQUIRED_MSG : 'Zadaj {1}.',
ALPHABETIC_MSG : '{1} je neplatnÈ. PovolenÈ znaky: A-Za-z',
ALPHANUMERIC_MSG : '{1} je neplatnÈ. PovolenÈ znaky: A-Za-z0-9',
ALNUMHYPHEN_MSG : '{1} je neplatnÈ. PovolenÈ znaky: A-Za-z0-9\-_',
ALNUMHYPHENAT_MSG : '{1} je neplatnÈ. PovolenÈ znaky: A-Za-z0-9\-_@',
ALPHASPACE_MSG : '{1} je neplatnÈ. PovolenÈ znaky: A-Za-z0-9\-_space',
MINLENGTH_MSG : '{1} musÌ obsahovaù aspoÚ {2} znaky/ov.',
MAXLENGTH_MSG : '{1} musÌ obsahovaù aspoÚ {2} znaky/ov.',
NUMRANGE_MSG : '{1} musÌ byù ËÌslo v rozsahu {2}.',
DATE_MSG : '{1} nie je platn˝ d·tum, pouûi form·t dd.MM.yyyy.',
NUMERIC_MSG : '{1} musÌ byù ËÌslo.',
INTEGER_MSG : '{1} musÌ byù re·lne.',
DOUBLE_MSG : '{1} musÌ byù desatinnÈ .',
REGEXP_MSG : '{1} nie je platnÈ. Povolen˝ form·t: {2}.',
EQUAL_MSG : '{1} musÌ byù ekvivalentnÈ s {2}.',
NOTEQUAL_MSG : '{1}musÌ byù ekvivalentnÈ s {2}.',
DATE_LT_MSG : '{1} musÌ byù predch·dzaj˙ce {2}.',
DATE_LE_MSG : '{1} musÌ byù predch·dzaj˙ce alebo ekvivalentnÈ s {2}.',
EMAIL_MSG : '{1} musÌ byù platn˝m e-mailom.',
EMPTY_MSG : '{1} musÌ byù pr·zdne.'

}//end

