/**
 * EidoGo -- Web-based SGF Editor
 * Copyright (c) 2007, Justin Kramer <jkkramer@gmail.com>
 * Code licensed under AGPLv3:
 * http://www.fsf.org/licensing/licenses/agpl-3.0.html
 */

/**
 * @class Applies rules (capturing, ko, etc) to a board.
 */
eidogo.Rules = function(board) {
    this.init(board);
};
eidogo.Rules.prototype = {
    /**
     * @constructor
     * @param {eidogo.Board} board The board to apply rules to
     */
    init: function(board) {
        this.board = board;
        this.pendingCaptures = [];
    },
    /**
     * Called to see whether a stone may be placed at a given point
    **/
    check: function(pt, color) {
        // already occupied?
        if (this.board.getStone(pt) != this.board.EMPTY) {
            return false;
        }
        // TODO: check for suicide? (allowed in certain rulesets)    
        // TODO: ko
        return true;
    },
    /**
     * Apply rules to the current game (perform any captures, etc)
    **/
    apply: function(pt, color) {
        this.doCaptures(pt, color);
    },
    /**
     * Thanks to Arno Hollosi for the capturing algorithm
     */
    doCaptures: function(pt, color) {
        var captures = 0;
        captures += this.doCapture({x: pt.x-1, y: pt.y}, color);
        captures += this.doCapture({x: pt.x+1, y: pt.y}, color);
        captures += this.doCapture({x: pt.x, y: pt.y-1}, color);
        captures += this.doCapture({x: pt.x, y: pt.y+1}, color);
        // check for suicide
        captures -= this.doCapture(pt, -color);
        if (captures < 0) {
            // make sure suicides give proper points (some rulesets allow it)
            color = -color;
            captures = -captures;
        }
        color = color == this.board.WHITE ? "W" : "B";
        this.board.captures[color] += captures;
    },
    doCapture: function(pt, color) {
        this.pendingCaptures = [];
        if (this.findCaptures(pt, color))
            return 0;
        var caps = this.pendingCaptures.length;
        while (this.pendingCaptures.length) {
            this.board.addStone(this.pendingCaptures.pop(), this.board.EMPTY);
        }
        return caps;
    },
    findCaptures: function(pt, color) {
        // out of bounds?
        if (pt.x < 0 || pt.y < 0 ||
            pt.x >= this.board.boardSize || pt.y >= this.board.boardSize)
            return 0;
        // found opposite color
        if (this.board.getStone(pt) == color)
            return 0;
        // found a liberty
        if (this.board.getStone(pt) == this.board.EMPTY)
            return 1;
        // already visited?
        for (var i = 0; i < this.pendingCaptures.length; i++)
            if (this.pendingCaptures[i].x == pt.x && this.pendingCaptures[i].y == pt.y)
                return 0;
        
        this.pendingCaptures.push(pt);
        
        if (this.findCaptures({x: pt.x-1, y: pt.y}, color))
            return 1;
        if (this.findCaptures({x: pt.x+1, y: pt.y}, color))
            return 1;
        if (this.findCaptures({x: pt.x, y: pt.y-1}, color))
            return 1;
        if (this.findCaptures({x: pt.x, y: pt.y+1}, color))
            return 1;
        return 0;
    }
}