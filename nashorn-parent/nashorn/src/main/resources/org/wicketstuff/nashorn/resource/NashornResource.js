// loading esprima.js from classpath
load("classpath:org/wicketstuff/nashorn/resource/esprima.js");

// loading escodegen.js from classpath
load("classpath:org/wicketstuff/nashorn/resource/escodegen.browser"
		+ (debug ? "" : ".min") + ".js");

// nashorn parser is buggy. It resolves Identifier to MemberExpressions. See https://github.com/estools/escodegen/issues/279
// load parser.js from nashorn resources
// load("nashorn:parser.js");
// var astObject = parse(script);

var astObject = esprima.parse(script);

if (debug) {
	print(debug_log_prefix + "Original Script: " + script);
	print(debug_log_prefix + "Original Script AST: "
			+ JSON.stringify(astObject));
}

function getAbortStatement(type) {
	var abortStatement = {
		"type" : "IfStatement",
		"test" : {
			"type" : "CallExpression",
			"callee" : {
				"type" : "Identifier",
				"name" : "nashornResourceReferenceScriptExecutionThread.isInterrupted"
			},
			"arguments" : [

			]
		},
		"consequent" : {
			"type" : "BlockStatement",
			"body" : []
		},
		"alternate" : null
	};

	if (type === "function") {
		abortStatement.consequent.body.unshift({
			"type" : "ReturnStatement",
			"argument" : null
		});
	} else {
		abortStatement.consequent.body.unshift({
			"type" : "BreakStatement",
			"label" : null
		});
	}
	if (debug) {
		abortStatement.consequent.body
				.unshift({
					"type" : "ExpressionStatement",
					"expression" : {
						"type" : "CallExpression",
						"callee" : {
							"type" : "Identifier",
							"name" : "print"
						},
						"arguments" : [ {
							"type" : "Literal",
							"value" : debug_log_prefix
									+ "Abort thread execution and skip all loop / function calls"
						} ]
					}
				});
	}
	return abortStatement;
}

// manipulate ast
function manipulateTree(astObject) {
	var targetAstObject;
	for ( var property in astObject) {
		var node = astObject[property];
		if (typeof node == 'object') {
			if (node == null) {
				// skip nulls
				continue;
			}
			if (Array.isArray(node) && node.length == 0) {
				// skip empty arrays
				continue;
			}
			// if any kind of loop inject abort statement
			if (node.type == 'WhileStatement'
					|| node.type == 'DoWhileStatement'
					|| node.type == 'ForStatement') {
				
				// if the loop has an empty statement change to block 
				// statement and inject the abort statement
				if(node.body.type == 'EmptyStatement'){
					node.body.type = 'BlockStatement';
					node.body.body = new Array();
				}
				node.body.body.unshift(getAbortStatement("loop"));
			// also if the node is a function, because of recursive invocation
			} else if (node.type == 'FunctionDeclaration') {
				node.body.body.unshift(getAbortStatement("function"));
			} else {
				// walk through the tree
				manipulateTree(node);
			}
		}
	}
}

manipulateTree(astObject);

// generate the script again
var safeScript = escodegen.generate(astObject);

if (debug) {
	print(debug_log_prefix + "Safe Script AST: " + JSON.stringify(astObject));
	print(debug_log_prefix + "Safe Script: " + safeScript);
}

// return value
safeScript;