package ${package}

import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.form.Form
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.wicketstuff.scala.{STextField, SLabel, ScalaWicket}

class HomePage(parameters: PageParameters)
  extends WebPage(parameters)
  with ScalaWicket {

  var name = "default"
  add(new Form("form") {
    add(new ScalaLabel("helloworld2", name))
    add(new ScalaTextField[String]("name", {println ("stf-getter"); name}, {println ("stf-setter"); name = _}) )
  })

}
