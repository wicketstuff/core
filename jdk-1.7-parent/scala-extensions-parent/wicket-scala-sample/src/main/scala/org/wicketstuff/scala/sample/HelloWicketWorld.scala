package org.wicketstuff.scala.sample

import org.apache.wicket.feedback.ContainerFeedbackMessageFilter
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.list.ListItem
import org.apache.wicket.model.{CompoundPropertyModel, IModel}
import org.apache.wicket.protocol.http.WebApplication
import org.apache.wicket.validation.validator.EmailAddressValidator
import org.wicketstuff.scala._
import org.wicketstuff.scala.markup.html.form.ScalaForm
import org.wicketstuff.scala.markup.html.list.ScalaPropertyListView
import org.wicketstuff.scala.model.Fodel
import org.wicketstuff.scala.traits.ScalaMarkupContainerT

import scala.language.postfixOps

class HelloWicketWorld extends WebApplication {
   def getHomePage = classOf[HomePage]
}

/**
 * Some examples of how to construct the components with Scala.
 */
class HomePage
  extends WebPage
  with ScalaMarkupContainerT {

  var name = "default"
   
  val form: ScalaForm[Unit] = form[Unit]("form1")

  // create an anonymous function and have it implicitly converted into a fodel
  val nf: IModel[String] = () ⇒ { println ("nff1"); name }
  form.label("helloworld1", nf)
  val getter: IModel[String] = () ⇒ { println ("label gtr"); name }
  form.label("helloworld3", getter)

  // explicit fodel with debug lines
  form.text("name1", new Fodel[String]({println ("stf-getter"); name}, {println ("stf-setter"); name = _}) )
  // and the shorter form
  form.text("name2", new Fodel[String](name, name = _ ))
  // and the Scala TextField is even shorter
  // this example requires the [String] parameter as the compiler is unable to infer the type of the parameters in the {name = _} function.
  form.text[String]("name3", new Fodel[String](name, name = _))
   
  // link with a closure
  var clickCount = 0
  link("clicker") {clickCount += 1; println(clickCount)}
   
  // using a fodel
  form.label("helloworld4", new Fodel({println ("f()label gtr"); name;}))
   
  // using an SLabel with a closure
  form.label("helloworld2", {println ("slabel gtr"); name;})

  // the form for new presentations and votes
  val presentationForm = form[Presentation]("form2", new CompoundPropertyModel(new Presentation),
    Map(
      "submit" -> { theForm: ScalaForm[Presentation] =>
        val newP = theForm.getModelObject
        Presentation add newP
        println ("presentations: "+Presentation.stub)
        theForm.setModelObject(new Presentation)
      }
    )
  )
  presentationForm.text("name")
  presentationForm.text("author")


  add(new ScalaPropertyListView[Presentation]("presentations", Presentation.stub, (li:ListItem[Presentation]) ⇒ { // list gets passed in by name
    val p = li.getModelObject
    li.label("name", p name)
    li.label("author", p author)
    li.label("votes", p.votes.toString)

    val form = li.form[Vote]("form", new CompoundPropertyModel[Vote](new Vote),
      Map("submit" -> { (theForm: ScalaForm[Vote]) =>
        val v = theForm.getModelObject
        p.addVotes(v)
        // we dont need to do anything with the returned presentation,
        // as the list view will reload it's model from the reset
        // service upon render
      })
    )
    form.text("email").add(EmailAddressValidator.getInstance)
    form.feedback("feedback", new ContainerFeedbackMessageFilter(li))

  }).setReuseItems(true))
    
}
