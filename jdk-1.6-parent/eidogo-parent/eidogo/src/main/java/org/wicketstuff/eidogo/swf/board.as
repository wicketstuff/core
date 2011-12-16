import flash.external.ExternalInterface;

class Board {
    static var app : Board;
    var playerId,
        size,
        width,
        height,
        margin,
        boardWidth,
        boardHeight,
        boardMargin,
        stones,
        depth;
    
    function Board() {
        this.width = 421;
        this.height = 421;
        this.margin = 25;
        this.boardWidth = this.width - this.margin * 2;
        this.boardHeight = this.height - this.margin * 2;
        this.boardMargin = 5;
        this.depth = 1;
        
        ExternalInterface.addCallback("init", this, this.init);
        ExternalInterface.addCallback("clear", this, this.clear);
        ExternalInterface.addCallback("renderStone", this, this.renderStone);
        ExternalInterface.addCallback("renderMarker", this, this.renderMarker);
    }
    
    public function init(playerId, size) {
        this.playerId = playerId;
        this.size = size;
        
        // Board background
        _root.createEmptyMovieClip("bg_mc", _root.getNextHighestDepth());
        var mcBg = _root.bg_mc;
        mcBg.beginFill(0xE8C473);
        mcBg.moveTo(this.margin, this.margin);
        mcBg.lineTo(this.width-this.margin, this.margin);
        mcBg.lineTo(this.width-this.margin, this.height-this.margin);
        mcBg.lineTo(this.margin, this.height-this.margin);
        mcBg.lineTo(this.margin, this.margin);
        mcBg.endFill();
        
        // Grid lines, star points, labels
        _root.createEmptyMovieClip("grid_mc", _root.getNextHighestDepth());
        var mcGrid = _root.grid_mc;
        _root.createEmptyMovieClip("stars_mc", _root.getNextHighestDepth());
        var mcStars = _root.stars_mc;
        _root.createEmptyMovieClip("labels_mc", _root.getNextHighestDepth());
        var mcLabels = _root.labels_mc;
        var bW = this.boardWidth - (this.boardMargin * 2);
        var bH = this.boardHeight - (this.boardMargin * 2);
        var ptW = bW / this.size;
        var ptH = bH / this.size;
        var m = this.margin + this.boardMargin + (ptW / 2);
        var pts = ['A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T'];
        var fmt : TextFormat = new TextFormat();
        fmt.color = 0xAA9A76;
        fmt.font = "Arial";
        for (var x = 0; x < this.size; x++) {
            mcGrid.lineStyle(0, 0xAE9454);
            mcGrid.moveTo((x * ptW) + m, m);
            mcGrid.lineTo((x * ptW) + m, this.boardHeight + this.margin - this.boardMargin - (ptH / 2));
            mcLabels.createTextField("xlabel1_" + x, this.depth++, (x * ptW) + m - (ptW / 2) + 4, this.margin - 20, 20, 20);
            mcLabels['xlabel1_' + x].text = pts[x];
            mcLabels['xlabel1_' + x].setTextFormat(fmt);
            mcLabels.createTextField("xlabel2_" + x, this.depth++, (x * ptW) + m - (ptW / 2) + 4, this.height - this.margin + (this.margin - 22), 20, 20);
            mcLabels['xlabel2_' + x].text = pts[x];
            mcLabels['xlabel2_' + x].setTextFormat(fmt);
            for (var y = 0; y < this.size; y++) {
                if (x == 0) {
                    mcGrid.lineStyle(0, 0xAE9454);
                    mcGrid.moveTo(m, (y * ptH) + m);
                    mcGrid.lineTo(this.boardWidth + this.margin - this.boardMargin - (ptW / 2), (y * ptH) + m);
                    mcLabels.createTextField("ylabel1_" + y, this.depth++, this.margin - 22, (y * ptH) + m - (ptH / 2) + 2, 20, 20);
                    mcLabels['ylabel1_' + y].text = (19 - y < 10 ? " " : "") + (19 - y);
                    mcLabels['ylabel1_' + y].setTextFormat(fmt);
                    mcLabels.createTextField("ylabel2_" + y, this.depth++, this.width - this.margin + (this.margin - 22), (y * ptH) + m - (ptH / 2) + 2, 20, 20);
                    mcLabels['ylabel2_' + y].text = (19 - y < 10 ? " " : "") + (19 - y);
                    mcLabels['ylabel2_' + y].setTextFormat(fmt);
                }
                if (size != 19) continue;
                if ((x == 3 && y == 3) || (x == 15 && y == 3) || (x == 3 && y == 15) || (x == 15 && y == 15) ||
                    (x == 3 && y == 9) || (x == 9 && y == 3) || (x == 15 && y == 9) || (x == 9 && y == 15) ||
                    (x == 9 && y == 9)) {
                    mcStars.beginFill(0x705F36);
                    mcStars.moveTo((x * ptW) + m - 1.5, (y * ptH) + m - 1.5);
                    mcStars.lineTo((x * ptW) + m + 1.5, (y * ptH) + m - 1.5);
                    mcStars.lineTo((x * ptW) + m + 1.5, (y * ptH) + m + 1.5);
                    mcStars.lineTo((x * ptW) + m - 1.5, (y * ptH) + m + 1.5);
                    mcStars.moveTo((x * ptW) + m - 1.5, (y * ptH) + m - 1.5);
                    mcStars.endFill();
                }
            }
        }
        
        // Stones
        this.stones = {};
        
        // From: http://www.actionscript.org/forums/showthread.php3?s=&threadid=30328
        MovieClip.prototype.drawCircle = function(x, y, r) {
            var c1=r*(Math.SQRT2-1);
            var c2=r*Math.SQRT2/2;
            this.moveTo(x+r,y);
            this.curveTo(x+r,y+c1,x+c2,y+c2);
            this.curveTo(x+c1,y+r,x,y+r);
            this.curveTo(x-c1,y+r,x-c2,y+c2);
            this.curveTo(x-r,y+c1,x-r,y);
            this.curveTo(x-r,y-c1,x-c2,y-c2);
            this.curveTo(x-c1,y-r,x,y-r);
            this.curveTo(x+c1,y-r,x+c2,y-c2);
            this.curveTo(x+r,y-c1,x+r,y);
        }
        
        var board = this;
        var margin = this.margin;
        var boardMargin = this.boardMargin;
        _root.createEmptyMovieClip("mouse_mc", _root.getNextHighestDepth());
        var mcMouse : MovieClip = _root.mouse_mc;
        mcMouse.onMouseUp = function() {
            var mx = this._xmouse - margin;
            var my = this._ymouse - margin;
            var bx = Math.round((mx - boardMargin - (ptW / 2)) / ptW);
            var by = Math.round((my - boardMargin - (ptH / 2)) / ptH);
            if (bx < 0 || by < 0 || bx > size-1 || by > size-1) return;
            ExternalInterface.call("eidogo.delegate", board.playerId, "handleBoardMouseUp", bx, by);
        };
    }
    
    public function trace(arg) {
        ExternalInterface.call("console.log", arg);
    }
    
    public function clear() {
        for (var id in stones) {
            if (stones[id]) {
                stones[id].removeMovieClip();
                stones[id] = null;
            }
        }
    }
    
    public function renderStone(pt, color) {
        var bW = this.boardWidth - (this.boardMargin * 2);
        var bH = this.boardHeight - (this.boardMargin * 2);
        var ptW = bW / this.size;
        var ptH = bH / this.size;
        var m = this.margin + this.boardMargin;
        var x = (pt.x + 1) * ptW + m - (ptW / 2);
        var y = (pt.y + 1) * ptH + m - (ptH / 2);
        var id = "stone-" + pt.x + "-" + pt.y;
        if (stones[id]) {
            stones[id].removeMovieClip();
            stones[id] = null;
        }
        if (color == "white") {
            _root.createEmptyMovieClip(id, _root.getNextHighestDepth());
            var mcW = _root[id];
            mcW.beginFill(0xFFFFFF);
            mcW.lineStyle(1.75, 0x888888);
            mcW.drawCircle(x, y, ptW / 2 - 0.5);
            mcW.endFill();
            this.stones[id] = mcW;
        } else if (color == "black") {
            _root.createEmptyMovieClip(id, _root.getNextHighestDepth());
            var mcB = _root[id];
            mcB.beginFill(0x000000);
            mcB.lineStyle(1.5, 0x000000);
            mcB.drawCircle(x, y, ptW / 2 - 0.5);
            mcB.endFill();
            this.stones[id] = mcB;
        }
    }
    
    public function renderMarker(pt, type) {
        var bW = this.boardWidth - (this.boardMargin * 2);
        var bH = this.boardHeight - (this.boardMargin * 2);
        var ptW = bW / this.size;
        var ptH = bH / this.size;
        var m = this.margin + this.boardMargin;
        var x = (pt.x + 1) * ptW + m - (ptW / 2);
        var y = (pt.y + 1) * ptH + m - (ptH / 2);
        var id = "marker-" + pt.x + "-" + pt.y;
    }
    
    static function main(mc) {
        app = new Board();
    }
}