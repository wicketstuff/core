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
DATE_FORMAT : 'MM-dd-yyyy',

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
HEADER_MSG : 'Dados inválidos:',
FOOTER_MSG : 'Por favor, tente novamente.',
DEFAULT_MSG : 'Dados inválidos',
REQUIRED_MSG : 'Insira {1}.',
ALPHABETIC_MSG : '{1} é inválido. Characters allowed: A-Za-z',
ALPHANUMERIC_MSG : '{1} não é válido. Caracteres permitidos: A-Za-z0-9',
ALNUMHYPHEN_MSG : '{1} não é válido. Caracteres permitidos: A-Za-z0-9\-_',
ALNUMHYPHENAT_MSG : '{1} não é válido. Caracteres permitidos: A-Za-z0-9\-_@',
ALPHASPACE_MSG : '{1} não é válido. Caracteres permitidos: A-Za-z0-9\-_space',
MINLENGTH_MSG : '{1} deve ter pelo menos {2} caracteres.',
MAXLENGTH_MSG : '{1} não pode ter mais que {2} caracteres.',
NUMRANGE_MSG : '{1} deve ser um número no intervalo {2}.',
DATE_MSG : '{1} não é uma data válida, no formato MM-dd-yyyy.',
NUMERIC_MSG : '{1} deve ser um número.',
INTEGER_MSG : '{1} deve ser um número inteiro.',
DOUBLE_MSG : '{1} deve ser um número decimal.',
REGEXP_MSG : '{1} não é válido. Formato permitido: {2}.',
EQUAL_MSG : '{1} deve ser igual a {2}.',
NOTEQUAL_MSG : '{1} não pode ser igual a {2}.',
DATE_LT_MSG : '{1} deve ser menor que {2}.',
DATE_LE_MSG : '{1} deve ser menor ou igual a {2}.',
EMAIL_MSG : '{1} deve ser um e-mail válido.',
EMPTY_MSG : '{1} deve ser vacio.'

}//end
