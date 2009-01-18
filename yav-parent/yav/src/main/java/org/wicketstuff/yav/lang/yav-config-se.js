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
THOUSAND_SEP : ' ',
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

// å = \u00e5
// ä = \u00e4
// ö = \u00f6
// Å = \u00c5
// Ä = \u00c4
// Ö = \u00d6

// SWEDISH
HEADER_MSG : 'Formul\u00e4ret ej korrekt ifyllt:',
FOOTER_MSG : 'V\u00e4nligen f\u00f6rs\u00f6k igen.',
DEFAULT_MSG : 'Inkorrekt data.',
REQUIRED_MSG : 'Fyll i f\u00e4ltet {1}.',
ALPHABETIC_MSG : 'F\u00e4ltet {1} inneh\u00e5ller otill\u00e5tna tecken. Till\u00e5tna tecken: A-Za-z',
ALPHANUMERIC_MSG : 'F\u00e4ltet {1} inneh\u00e5ller otill\u00e5tna tecken. Till\u00e5tna tecken: A-Za-z0-9',
ALNUMHYPHEN_MSG : 'F\u00e4ltet {1} inneh\u00e5ller otill\u00e5tna tecken. Till\u00e5tna tecken: A-Za-z0-9\-_',
ALNUMHYPHENAT_MSG : 'F\u00e4ltet {1} inneh\u00e5ller otill\u00e5tna tecken. Till\u00e5tna tecken: A-Za-z0-9\-_@',
ALPHASPACE_MSG : 'F\u00e4ltet {1} inneh\u00e5ller otill\u00e5tna tecken. Till\u00e5tna tecken: A-Za-z0-9\-_space',
MINLENGTH_MSG : 'F\u00e4ltet {1} m\u00e5ste inneh\u00e5lla minst {2} tecken.',
MAXLENGTH_MSG : 'F\u00e4ltet {1} f\u00e5r ej inneh\u00e5lla mer \u00e4n {2} tecken.',
NUMRANGE_MSG : 'F\u00e4ltet {1} m\u00e5ste vara inom {2}.',
DATE_MSG : 'F\u00e4ltet {1} \u00e4r ej ett korrekt ifyllt datum, datumformatet m\u00e5ste vara yyyy-MM-dd.',
NUMERIC_MSG : 'F\u00e4ltet {1} m\u00e5ste vara en siffra.',
INTEGER_MSG : 'F\u00e4ltet {1} m\u00e5ste vara ett heltal.',
DOUBLE_MSG : 'F\u00e4ltet {1} m\u00e5ste vara en decimalsiffra.',
REGEXP_MSG : 'F\u00e4ltet {1} \u00e4r ej till\u00e5tet. Till\u00e5tet format: {2}.',
EQUAL_MSG : 'F\u00e4ltet {1} m\u00e5ste vara lika med {2}.',
NOTEQUAL_MSG : 'F\u00e4ltet {1} f\u00e5r ej vara lika med {2}.',
DATE_LT_MSG : 'F\u00e4ltet {1} m\u00e5ste vara tidigare \u00e4n {2}.',
DATE_LE_MSG : 'F\u00e4ltet {1} m\u00e5ste vara tidigare \u00e4n eller lika med {2}.',
EMAIL_MSG : 'F\u00e4ltet {1} m\u00e5ste vara en korrekt e-postadress.',
EMPTY_MSG : 'F\u00e4ltet {1} m\u00e5ste var tomt.'

}//end
