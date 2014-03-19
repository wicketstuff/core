/**
 * EidoGo -- Web-based SGF Editor
 * Copyright (c) 2007, Justin Kramer <jkkramer@gmail.com>
 * Code licensed under AGPLv3:
 * http://www.fsf.org/licensing/licenses/agpl-3.0.html
 *
 * Initialize things for EidoGo to function: stylesheets, etc
 */

/**
 * Search for DIV elements with the class 'eidogo-player-auto' and insert a
 * Player into each. Also handle problem-mode Players with
 * 'eidogo-player-problem'
**/
(function() {
    
    var autoCfg = window.eidogoConfig || {};
    var problemCfg = {
        theme:          "problem",
        problemMode:    true,
        markVariations: false,
        markNext:       false,
        shrinkToFit:    true
    };
    var scriptPath = eidogo.util.getPlayerPath();    
    var path = eidogo.playerPath = (autoCfg.playerPath || scriptPath || 'player').replace(/\/$/, "");
    
    if (!autoCfg.skipCss) {
        eidogo.util.addStyleSheet(path + '/css/player.css');
        if (eidogo.browser.ie && parseInt(eidogo.browser.ver, 10) <= 6) {
            eidogo.util.addStyleSheet(path + '/css/player-ie6.css');
        }
    }
    
    eidogo.util.addEvent(window, "load", function() {        
        
        eidogo.autoPlayers = [];
        var els = [];
        var divs = document.getElementsByTagName('div');
        var len = divs.length;
        for (var i = 0; i < len; i++) {
            if (eidogo.util.hasClass(divs[i], "eidogo-player-auto") ||
                eidogo.util.hasClass(divs[i], "eidogo-player-problem")) {
                els.push(divs[i]);
            }
        }
        var el;
        for (var i = 0; el = els[i]; i++) {
            var cfg = {container: el, disableShortcuts: true, theme: "compact"};
            for (var key in autoCfg)
                cfg[key] = autoCfg[key];
            if (eidogo.util.hasClass(el, "eidogo-player-problem"))
                for (var key in problemCfg)
                    cfg[key] = problemCfg[key];
            
            var sgfUrl = el.getAttribute('sgf');
            if (sgfUrl) cfg.sgfUrl = sgfUrl;
            else if (el.innerHTML) cfg.sgf = el.innerHTML;
            
            el.innerHTML = "";
            eidogo.util.show(el);
            
            var player = new eidogo.Player(cfg);
            eidogo.autoPlayers.push(player);
        }
        
    });
    
})();