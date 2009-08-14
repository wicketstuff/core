package org.wicketstuff.scala.sample

import org.apache.wicket._
import org.apache.wicket.protocol.http._
import org.apache.wicket.markup.html._
import org.apache.wicket.markup.html.form._
import org.apache.wicket.model.{PropertyModel, CompoundPropertyModel}
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.feedback.{ContainerFeedbackMessageFilter, ComponentFeedbackMessageFilter, FeedbackMessage}
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.validation.validator.EmailAddressValidator

import org.wicketstuff.scala._

class HelloWicketWorld extends WebApplication {
   def getHomePage = classOf[HomePage]
}

/**
 * Some examples of how to construct the components with Scala.
 */
class HomePage extends WebPage with ScalaWicket {
  var name = "default"
   
  val form = new Form("form1")
  add(form)

  // create an anonymous function and have it implicitly converted into a fodel
  val nf = () ⇒ { println ("nff1"); name }
  form.add(new Label("helloworld1", nf))
  form.add(new Label("helloworld3", () ⇒ { println ("label gtr"); name }))

  // explicit fodel with debug lines
  form.add(new TextField("name1", new Fodel[String]({println ("stf-getter"); name}, {println ("stf-setter"); name = _}) ) )
  // and the shorter form
  form.add(new TextField("name2", new Fodel[String](name, name = _ ) ) )
  // and the Scala TextField is even shorter
  // this example requires the [String] parameter as the compiler is unable to infer the type of the parameters in the {name = _} function.
  form.add(new STextField[String]("name3", name, name = _  ) )
   
  // link with a closure
  var clickCount = 0
  add(new SLink("clicker", {clickCount += 1; println(clickCount)}))
   
  // using a fodel
  form.add(new Label("helloworld4", new Fodel({println ("f()label gtr"); name;})))
   
  // usig an SLabel with a closure
  form.add(new SLabel("helloworld2", {println ("slabel gtr"); name;}))

  // the form for new presentations and votes
  add(new Form[Presentation]("form2", new CompoundPropertyModel(new Presentation)){
    add(new TextField("name"))
    add(new TextField("author"))
    override def onSubmit {
      val newP = getModelObject
      Presentation add newP
      println ("presentations: "+Presentation.stub)
      setModelObject(new Presentation)
    }
  })

  add(new SPropertyListView[Presentation]("presentations", Presentation.stub, (li:ListItem[Presentation]) ⇒ { // list gets passed in by name
    val p = li.getModelObject()
    li add(new SLabel("name", p name))
    li add(new SLabel("author", p author))
    li add(new SLabel("votes", p.votes.toString))

    li add(new Form[Vote]("form", new CompoundPropertyModel[Vote](new Vote)) {
      add(new TextField("email").add(EmailAddressValidator.getInstance))
      add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(li)))
      override def onSubmit {
        val v = getModelObject
        p.addVotes(v)
        // we dont need to do anything with the returned presentation,
        // as the list view will reload it's model from the reset
        // service upon render
      }
    })
  }).setReuseItems(true))
    
}