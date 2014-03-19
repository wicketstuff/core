/**
 * EidoGo -- Web-based SGF Editor
 * Copyright (c) 2007, Justin Kramer <jkkramer@gmail.com>
 * Code licensed under AGPLv3:
 * http://www.fsf.org/licensing/licenses/agpl-3.0.html
 *
 * Quick and dirty SGF parser.
 */

/**
 * @class Returns an SGF-like JSON object of the form:
 *      { PROP1: value,  PROP2: value, ..., _children: [...]}
 */
eidogo.SgfParser = function() {
    this.init.apply(this, arguments);
}
eidogo.SgfParser.prototype = {
    /**
     * @constructor
     * @param {String} sgf Raw SGF data to parse
     */
    init: function(sgf, completeFn) {
        completeFn = (typeof completeFn == "function") ? completeFn : null;
        this.sgf = sgf;
        this.index = 0;
        this.root = {_children: []};
        this.parseTree(this.root);
        completeFn && completeFn.call(this);
    },
    parseTree: function(curnode) {
        while (this.index < this.sgf.length) {
            var c = this.curChar();
            this.index++;
            switch (c) {
                case ';':
                    curnode = this.parseNode(curnode);
                    break;
                case '(':
                    this.parseTree(curnode);
                    break;
                case ')':
                    return;
                    break;
            }
        }
    },
    parseNode: function(parent) {
        var node = {_children: []};
        if (parent)
            parent._children.push(node);
        else
            this.root = node;
        node = this.parseProperties(node);
        return node;
    },
    parseProperties: function(node) {
        var key = "";
        var values = [];
        var i = 0;
        while (this.index < this.sgf.length) {
            var c = this.curChar();
            if (c == ';' || c == '(' || c == ')') {
                break;
            }
            if (this.curChar() == '[') {
                while (this.curChar() == '[') {
                    this.index++;
                    values[i] = "";
                    while (this.curChar() != ']' && this.index < this.sgf.length) {
                        if (this.curChar() == '\\') {
                            this.index++;
                            // not technically correct, but works in practice
                            while (this.curChar() == "\r" || this.curChar() == "\n") {
                                this.index++;
                            }
                        }
                        values[i] += this.curChar();
                        this.index++;
                    }
                    i++;
                    while (this.curChar() == ']' || this.curChar() == "\n" || this.curChar() == "\r") {
                        this.index++;
                    }
                }
                if (node[key]) {
                    if (!(node[key] instanceof Array)) {
                        node[key] = [node[key]];
                    }
                    node[key] = node[key].concat(values);
                } else {
                    node[key] = values.length > 1 ? values : values[0];
                }
                key = "";
                values = [];
                i = 0;
                continue;
            }
            if (c != " " && c != "\n" && c != "\r" && c != "\t") {
                key += c;
            }
            this.index++;
        }
        return node;
    },
    curChar: function() {
        return this.sgf.charAt(this.index);
    }
};