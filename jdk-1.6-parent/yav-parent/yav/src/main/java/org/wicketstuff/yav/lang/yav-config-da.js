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
HEADER_MSG : 'Ugyldige informationer:',
FOOTER_MSG : 'Prøv igen.',
DEFAULT_MSG : 'Informationerne er ikke gyldige.',
REQUIRED_MSG : 'Indtast {1}.',
ALPHABETIC_MSG : '{1} er ikke gyldig. Tilladte tegn: A-Za-z',
ALPHANUMERIC_MSG : '{1} er ikke gyldig. Tilladte tegn: A-Za-z0-9',
ALNUMHYPHEN_MSG : '{1} er ikke gyldig. Tilladte tegn: A-Za-z0-9\-_',
ALNUMHYPHENAT_MSG : '{1} er ikke gyldig. Tilladte tegn: A-Za-z0-9\-_@',
ALPHASPACE_MSG : '{1} er ikke gyldig. Tilladte tegn: A-Za-z0-9\-_mellemrum',
MINLENGTH_MSG : '{1} skal være mindst {2} tegn lang.',
MAXLENGTH_MSG : '{1} må ikke være mere end {2} tegn lang.',
NUMRANGE_MSG : '{1} skal ligge i intervallet {2}.',
DATE_MSG : '{1} er ikke en gyldig dato, brug formatet dd.MM.yyyy.',
NUMERIC_MSG : '{1} skal være et tal.',
INTEGER_MSG : '{1} skal være et helt tal',
DOUBLE_MSG : '{1} skal være et decimaltal.',
REGEXP_MSG : '{1} er ikke gyldig. Tilladt format: {2}.',
EQUAL_MSG : '{1} og {2} skal være ens.',
NOTEQUAL_MSG : '{1} og {2} må ikke være ens.',
DATE_LT_MSG : '{1} skal være før {2}.',
DATE_LE_MSG : '{1} skal være før, eller lig med {2}.',
EMAIL_MSG : '{1} skal være en gyldig e-mail-adresse.',
EMPTY_MSG : '{1} skal være tom.'

}//end
