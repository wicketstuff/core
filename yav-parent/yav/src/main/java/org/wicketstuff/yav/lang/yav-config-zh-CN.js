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
DECIMAL_SEP : '.',
THOUSAND_SEP : ',',
DATE_FORMAT : 'yyyy-MM-dd',

// change to set your own rules based on regular expressions
alphabetic_regex : "^[A-Za-z]*$",
alphanumeric_regex : "^[A-Za-z0-9]*$",
alnumhyphen_regex : "^[A-Za-z0-9\-_]*$",
alnumhyphenat_regex : "^[A-Za-z0-9\-_@]*$",
alphaspace_regex : "^[A-Za-z0-9\-_ \n\r\t]*$",
email_regex : "^(([0-9a-zA-Z]+[-._+&])*[0-9a-zA-Z]+@([-0-9a-zA-Z]+[.])+[a-zA-Z]{2,6}){0,1}$",

// change to set your own rule separator
RULE_SEP : '|',

// Translate By Cloudream (cloudream@gmail.com)

// change these strings for your own translation (do not change {n} values!)
HEADER_MSG : '数据验证未通过:',
FOOTER_MSG : '请重试.',
DEFAULT_MSG : '数据不正确.',
REQUIRED_MSG : '请输入 {1}.',
ALPHABETIC_MSG : '{1} 格式错误. 允许的字符: A-Za-z',
ALPHANUMERIC_MSG : '{1} 格式错误. 允许的字符: A-Za-z0-9',
ALNUMHYPHEN_MSG : '{1} 格式错误. 允许的字符: A-Za-z0-9\-_',
ALNUMHYPHENAT_MSG : '{1} 格式错误. 允许的字符: A-Za-z0-9\-_@',
ALPHASPACE_MSG : '{1} 格式错误. 允许的字符: A-Za-z0-9\-_"空格"',
MINLENGTH_MSG : '{1} 需至少 {2} 个字符.',
MAXLENGTH_MSG : '{1} 需少于 {2} 个字符.',
NUMRANGE_MSG : '{1} 需为 {2} 间的数字.',
DATE_MSG : '{1} 日期错误, 格式: yyyy-MM-dd.',
NUMERIC_MSG : '{1} 需为数字.',
INTEGER_MSG : '{1} 需为证书',
DOUBLE_MSG : '{1} 需为小数.',
REGEXP_MSG : '{1} 格式错误. 允许的格式: {2}.',
EQUAL_MSG : '{1} 需等于 {2}.',
NOTEQUAL_MSG : '{1} 需不同于 {2}.',
DATE_LT_MSG : '{1} 需早于 {2}.',
DATE_LE_MSG : '{1} 需早于或等于 {2}.',
EMAIL_MSG : '{1} 需为有效的E-mail地址.',
EMPTY_MSG : '{1} 需为空.'

}//end

