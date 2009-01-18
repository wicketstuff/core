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

// cambia estas frases para tener tu propia traduccion (no cambiar los valores de {n})
HEADER_MSG : 'Datos no v&aacute;lidos:',
FOOTER_MSG : 'por favor, int&eacute;ntelo otra vez.',
DEFAULT_MSG : 'el dato no es v&aacute;lido.',
REQUIRED_MSG : 'insertar {1}.',
ALPHABETIC_MSG : '{1} no es v&aacute;lido. Caracteres permitidas: A-Za-z',
ALPHANUMERIC_MSG : '{1} no es v&aacute;lido. Caracteres permitidas: A-Za-z0-9',
ALNUMHYPHEN_MSG : '{1} no es v&aacute;lido. Caracteres permitidas: A-Za-z0-9\-_',
ALNUMHYPHENAT_MSG : '{1} no es v&aacute;lido. Caracteres permitidas: A-Za-z0-9\-_@',
ALPHASPACE_MSG : '{1} no es v&aacute;lido. Caracteres permitidas: A-Za-z0-9\-_espacio',
MINLENGTH_MSG : '{1} debe tener al menos {2} caracteres.',
MAXLENGTH_MSG : '{1} debe tener como mucho {2} caracteres.',
NUMRANGE_MSG : '{1} debe ser un n&uacute;mero en el intervalo {2}.',
DATE_MSG : '{1} no es una fecha usando el formato dd-MM-yyyy.',
NUMERIC_MSG : '{1} debe ser un número.',
INTEGER_MSG : '{1} debe ser un n&uacute;mero entero.',
DOUBLE_MSG : '{1} debe ser un n&uacute;mero decimal.',
REGEXP_MSG : '{1} no es v&aacute;lido. El formato permitido es {2}.',
EQUAL_MSG : '{1} tiene que ser igual que {2}.',
NOTEQUAL_MSG : '{1} no tiene que ser igual que {2}.',
DATE_LT_MSG : '{1} tiene que ser anterior a {2}.',
DATE_LE_MSG : '{1} tiene que ser anterior o igual a {2}.',
EMAIL_MSG : '{1} no es una direcci&oacute;n de correo electr&oacute;nico v&aacute;lida.',
EMPTY_MSG : '{1} debe ser vacio.'

}//end
