package org.wicketstuff.scala

import org.junit.Test
import org.apache.wicket.util.tester.WicketTester
import org.apache.wicket.markup.html.WebPage
import org.apache.wicket.markup.html.basic.Label
import org.apache.wicket.markup.Markup

class ComponentXTest {

  @Test
  def roModel() {
    class ReadOnlyModelTestPage extends WebPage {
      val label = new Label("l", "a")
      add(label)

      setMarkup(Markup.of("<html><body><span wicket:id='l'></span></body></html>"))
    }

    val tester = new WicketTester
    val page = new ReadOnlyModelTestPage
    page.label.roModel( () => "b")
    tester.startPage(page)

    tester.assertModelValue("l", "b")
  }


  @Test
  def on() {
    class OnTestPage extends WebPage {
      val label = new Label("l", "a")
      add(label)

      setMarkup(Markup.of("<html><body><span wicket:id='l'></span></body></html>"))
    }

    val tester = new WicketTester
    val page = new OnTestPage
    page.label.on("click", (target) => target.appendJavaScript("Executed!"))
    tester.startPage(page)

    tester.executeAjaxEvent(page.label, "click")
    tester.assertContains("Executed!")
  }

}
