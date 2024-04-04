/**
 *
 Copyright 2012 Vineet Semwal
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
 http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

/**
 *  repeater.js tests
 *  
 *  @author Vineet Semwal
 * 
 */
test("create item",function(){
    $("#field").val("some thing..");
    var item=QuickView.createItem('li',"34");
   equal(item[0].id,"34" );
var found=$(item).prop("tagName");
  ok( found.toLowerCase()==="li");
});

test("append if no element",function(){
    $("#field").val("some thing..");
    var len=$("#parent").children($("#34")).length;
    equal(len,0);
    QuickView.append('li', "34","parent","","");
    len=$("#parent").children($("#34")).length;
    equal(len,1);
  $("ul").empty(); //clearing
    
});

test("append when children exist",function(){
    $("#field").val("some thing..");
    QuickView.append('li', "34","parent","" ,"");
    QuickView.append('li', "35","parent","" ,"");
    ok($("#parent").children("li")[0].id,34);// checking if appended as  element
    ok($("#parent").children("li")[1].id,35); 
     $("ul").empty(); //clearing

});

/*
boundary is specified
*/

test("append when boundary is specified",function(){
    $("#field").val("some thing..");
    var len=$("#parent").children($("#34")).length;
    equal(len,0);
    $("#parent").append("<li id='start'></li>");
    $("#parent").append("<li id='end'></li>");
    QuickView.append('li', "34","parent" ,"start","end");
    len=$("#parent").children("#34").length;
    equal(len,1);
  $("ul").empty(); //clearing

});


test("prepend when no children ",function(){
    $("#field").val("some thing..");
    var len=$("#parent").children($("#34")).length;
    equal(len,0); 
    QuickView.prepend('li', "34","parent" ,"","");
    len=$("#parent").children("#34").length;
    equal(len,1); 
 $("ul").empty(); //clearing
    
});

test("prepend when children exist",function(){
    $("#field").val("some thing..");
    QuickView.append('li', "34","parent" ,"","");
    QuickView.prepend('li', "33","parent" ,"","");
    ok($("#parent").children("li")[0].id,33);// checking if prepended as first element
    ok($("#parent").children("li")[1].id,34); 
 $("ul").empty(); //clearing
    
});



test("prepend whe boundary is specified ",function(){
    $("#field").val("some thing..");
    var len=$("#parent").children($("#34")).length;
    equal(len,0);
    $("#parent").append("<li id='start'></li>");
    $("#parent").append("<li id='end'></li>");
     QuickView.prepend('li', "34","parent" ,"start","end");
    len=$("#parent").children("#34").length;
    equal(len,1);
 $("ul").empty(); //clearing

});

test("remove item",function(){
    $("#field").val("some thing..");
    QuickView.append('li', "34","parent" ,"" ,"");
    QuickView.append('li', "35","parent" ,"","");
    QuickView.removeItem("34","parent");
    var item=$("#parent").children("li")[0];
    ok(item.id,"35");
    ok($("#parent").children("li").length,1);
     $("ul").empty(); //clearing
});


test("isPageScrollBarAtBottom",function(){
    $("ul").height(3000);
    $(window).scrollTop(3000);
    ok(QuickView.isPageScrollBarAtBottom());
    $("ul").height("10");    
      
});


test("showPageScrollBar",function(){
    $("ul").height(100);
     QuickView.showPageScrollBar();
     var result=$(document).height() > $(window).height() ;
     ok(result===true);
});



//style="overflow-y:auto;height:150px;width:50%"
test("isComponentScrollBarAtBottom",function(){
    $("#parent").css("overflow-y","auto").height(150).width(50);
    QuickView.append('li', "34","parent","","");
      $("#34").css("display","block").css("padding-bottom",200);  
     $("#parent").scrollTop(50);
    ok(QuickView.isComponentScrollBarAtBottom("parent","scroll at bottom"));
    $("ul").empty(); //clearing
    $("ul").removeAttr("style");
    
});


//style="overflow-y:auto;height:150px;width:50%"
test("isComponentScrollBarAtBottom",function(){
    $("#parent").css("overflow-y","auto").height(150).width(50);
    QuickView.append('li', "34","parent","","");
      $("#34").css("display","block").css("padding-bottom",200);  
     $("#parent").scrollTop(40);
    ok(!QuickView.isComponentScrollBarAtBottom("parent") ,"scroll not at bottom");
     $("ul").empty(); //clearing
       $("ul").removeAttr("style");
  
});