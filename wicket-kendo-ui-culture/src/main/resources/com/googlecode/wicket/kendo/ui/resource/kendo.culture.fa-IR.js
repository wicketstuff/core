/**
 * Copyright 2015 Telerik AD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
(function(f, define){
    define([], f);
})(function(){

(function( window, undefined ) {
    var kendo = window.kendo || (window.kendo = { cultures: {} });
    kendo.cultures["fa-IR"] = {
        name: "fa-IR",
        numberFormat: {
            pattern: ["n-"],
            decimals: 2,
            ",": ",",
            ".": "/",
            groupSize: [3],
            percent: {
                pattern: ["-n %","n %"],
                decimals: 2,
                ",": ",",
                ".": "/",
                groupSize: [3],
                symbol: "%"
            },
            currency: {
                pattern: ["$n-","$n"],
                decimals: 2,
                ",": ",",
                ".": "/",
                groupSize: [3],
                symbol: "ريال"
            }
        },
        calendars: {
            standard: {
                days: {
                    names: ["يكشنبه","دوشنبه","سه شنبه","چهارشنبه","پنجشنبه","جمعه","شنبه"],
                    namesAbbr: ["يكشنبه","دوشنبه","سه شنبه","چهارشنبه","پنجشنبه","جمعه","شنبه"],
                    namesShort: ["ی","د","س","چ","پ","ج","ش"]
                },
                months: {
                    names: ["ژانويه","فوريه","مارس","آوريل","مه","ژوئن","ژوئيه","اوت","سپتامبر","اُكتبر","نوامبر","دسامبر"],
                    namesAbbr: ["ژانويه","فوريه","مارس","آوريل","مه","ژوئن","ژوئيه","اوت","سپتامبر","اُكتبر","نوامبر","دسامبر"]
                },
                AM: ["ق.ظ","ق.ظ","ق.ظ"],
                PM: ["ب.ظ","ب.ظ","ب.ظ"],
                patterns: {
                    d: "dd/MM/yyyy",
                    D: "dddd, dd MMMM yyyy",
                    F: "dddd, dd MMMM yyyy hh:mm:ss tt",
                    g: "dd/MM/yyyy hh:mm tt",
                    G: "dd/MM/yyyy hh:mm:ss tt",
                    m: "d MMMM",
                    M: "d MMMM",
                    s: "yyyy'-'MM'-'dd'T'HH':'mm':'ss",
                    t: "hh:mm tt",
                    T: "hh:mm:ss tt",
                    u: "yyyy'-'MM'-'dd HH':'mm':'ss'Z'",
                    y: "MMMM, yyyy",
                    Y: "MMMM, yyyy"
                },
                "/": "/",
                ":": ":",
                firstDay: 6
            }
        }
    }
})(this);


return window.kendo;

}, typeof define == 'function' && define.amd ? define : function(_, f){ f(); });