package ${package}

import org.apache.wicket._
import org.apache.wicket.protocol.http._
import org.apache.wicket.markup.html._
import org.apache.wicket.markup.html.form._
import org.apache.wicket.model.PropertyModel
import org.apache.wicket.markup.html.basic.Label

import org.wicketstuff.scala._

class HelloWicketWorld extends WebApplication {
   def getHomePage = classOf[HomePage]
}

class HomePage extends WebPage with ScalaWicket {
  
  var name = "default"
  add(new Form("form"){
    add(new SLabel("helloworld2", name))
    add(new STextField[String]("name", {println ("stf-getter"); name}, {println ("stf-setter"); name = _}) )
  })
  
}