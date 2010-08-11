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
DATE_FORMAT : 'dd-MM-yyyy',

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
HEADER_MSG : 'Nid yw\'r data\'n ddilys:',
FOOTER_MSG : 'Ceisiwch eto.',
DEFAULT_MSG : 'Nid yw\'r data\'n ddilys.',
REQUIRED_MSG : 'Rhowch {1}.',
ALPHABETIC_MSG : 'Nid yw {1} yn ddilys. Caniateir y cymeriadau: A-Za-z',
ALPHANUMERIC_MSG : 'Nid yw {1} yn ddilys. Caniateir y cymeriadau: A-Za-z0-9',
ALNUMHYPHEN_MSG : 'Nid yw {1} yn ddilys. Caniateir y cymeriadau: A-Za-z0-9\-_',
ALNUMHYPHENAT_MSG : 'Nid yw {1} yn ddilys. Caniateir y cymeriadau: A-Za-z0-9\-_@',
ALPHASPACE_MSG : 'Nid yw {1} yn ddilys. Caniateir y cymeriadau: A-Za-z0-9\-_space',
MINLENGTH_MSG : 'Mae {1} yn gorfod cynnwys o leiaf {2} cymeriad.',
MAXLENGTH_MSG : 'Ni all {1} gynnwys mwy na {2} cymeriad.',
NUMRANGE_MSG : 'Mae\'n rhaid bod {1} yn rhif o fewn yr amrediad {2}.',
DATE_MSG : 'Nid yw {1} yn ddyddiad dilys, wrth ddefnyddio\'r fformat  dd-MM-yyyy.',
NUMERIC_MSG : 'Mae\'n rhaid bod {1} yn rhif.',
INTEGER_MSG : 'Mae\'n rhaid bod {1} fod yn rhif cyfan',
DOUBLE_MSG : 'Mae\'n rhaid bod {1} yn ddegolyn.',
REGEXP_MSG : 'Nid yw {1} yn ddilys. Caniateir y fformat: {2}.',
EQUAL_MSG : 'Mae\'n rhaid bod {1} yn hafal i {2}.',
NOTEQUAL_MSG : 'Ni all {1} fod yn hafal i {2}.',
DATE_LT_MSG : 'Rhaid {1} ddod cyn {2}.',
DATE_LE_MSG : 'Rhaid {1} ddod cyn {2} neu gall fod yn hafal.',
EMAIL_MSG : 'Mae\'n rhaid bod {1} yn e-bost dilys.',
EMPTY_MSG : 'Mae\'n rhaid bod {1} yn wag.'

}//end