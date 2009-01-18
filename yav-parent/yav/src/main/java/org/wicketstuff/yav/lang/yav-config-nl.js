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
HEADER_MSG : 'Fout:',
FOOTER_MSG : 'Probeer opnieuw.',
DEFAULT_MSG : 'Waarde is niet geldig.',
REQUIRED_MSG : '{1} is niet opgegeven.',
ALPHABETIC_MSG : '{1} is niet geldig. Tekens toegestaan: A-Za-z',
ALPHANUMERIC_MSG : '{1} is niet geldig. Tekens toegestaan: A-Za-z0-9',
ALNUMHYPHEN_MSG : '{1} is niet geldig. Tekens toegestaan: A-Za-z0-9\-_',
ALNUMHYPHENAT_MSG : '{1} is niet geldig. Tekens toegestaan: A-Za-z0-9\-_@',
ALPHASPACE_MSG : '{1} is niet geldig. Tekens toegestaan: A-Za-z0-9\-_space',
MINLENGTH_MSG : '{1} moet minimaal {2} tekens lang zijn.',
MAXLENGTH_MSG : '{1} mag niet langer zijn dan {2} tekens.',
NUMRANGE_MSG : '{1} moet een nummer zijn in de range {2}.',
DATE_MSG : '{1} is geen geldig datum formaat, gebruik dd-MM-yyyy.',
NUMERIC_MSG : '{1} moet numeriek zijn.',
INTEGER_MSG : '{1} moet een integer zijn',
DOUBLE_MSG : '{1} moet een decimaal zijn.',
REGEXP_MSG : '{1} is niet geldig. Formaat toegestaan: {2}.',
EQUAL_MSG : '{1} moet gelijk zijn aan {2}.',
NOTEQUAL_MSG : '{1} moet niet gelijk zijn aan {2}.',
DATE_LT_MSG : '{1} moet eerder zijn dan {2}.',
DATE_LE_MSG : '{1} moet eerder zijn of gelijk aan {2}.',
EMAIL_MSG : '{1} is geen geldig e-mail adres.',
EMPTY_MSG : '{1} moet leeg zijn.'

}//end
