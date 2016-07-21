package org.wicketstuff.scala

import org.wicketstuff.scala.markup.html.basic.ScalaLabel
import org.wicketstuff.scala.model.FodelString

import scala.collection.JavaConversions.seqAsJavaList
import org.apache.wicket.behavior.AttributeAppender
import org.apache.wicket.datetime.markup.html.form.DateTextField
import org.apache.wicket.extensions.validation.validator.RfcCompliantEmailAddressValidator
import org.apache.wicket.markup.html.basic.{ MultiLineLabel, Label }
import org.apache.wicket.markup.html.form.{ TextField, TextArea, SubmitLink, RadioGroup, Radio, PasswordTextField, FormComponent, Form, DropDownChoice, CheckGroup, Button }
import org.apache.wicket.markup.html.link.{ Link, BookmarkablePageLink }
import org.apache.wicket.markup.html.link.ExternalLink
import org.apache.wicket.markup.html.list.{ PageableListView, ListView, ListItem }
import org.apache.wicket.markup.html.navigation.paging.{ PagingNavigator, IPageable }
import org.apache.wicket.markup.html.panel.FeedbackPanel
import org.apache.wicket.markup.html.WebMarkupContainer
import org.apache.wicket.model.{ PropertyModel, IModel }
import org.apache.wicket.model.{ Model, LoadableDetachableModel, CompoundPropertyModel }
import org.apache.wicket.request.mapper.parameter.PageParameters
import org.apache.wicket.MarkupContainer
import org.apache.wicket.{ Component, Application }
import org.apache.wicket.Page
import org.apache.wicket.validation.IValidatable
import org.apache.wicket.validation.IValidator
import org.apache.wicket.markup.html.form.CheckBox
import org.apache.wicket.Session
import scala.reflect.ClassTag
import scala.language.implicitConversions

@deprecated(message = "Use the fine grained traits instead, or the implicit conversion", since = "7.0.0")
trait DSLWicket {
  self: MarkupContainer ⇒

  def homeLink(id: String): BookmarkablePageLink[_] = blink(id, Application.get().getHomePage)
  def logoutLink(id: String): Link[_] = link(id, () ⇒ { Session.get().invalidate() })
  def container(id: String): DSLMarkupContainer = { val c = new WebMarkupContainer(id) with DSLMarkupContainer; add(c); c }
  def hide() = setVisibilityAllowed(false)
  def show() = setVisibilityAllowed(true)
  def hide(c: Component*) = c.foreach(x ⇒ x.setVisibilityAllowed(false))
  def show(c: Component*) = c.foreach(x ⇒ x.setVisibilityAllowed(true))
  def feedback(id: String = "feedback") = add(new FeedbackPanel(id))
  def ldm[T](loadF: () ⇒ T): IModel[T] = {
    val ldm = new LoadableDetachableModel[T] { override def load(): T = loadF() }; ldm
  }
  // MultiLineLabel
  def multiLineLabel[T](id: String, model: IModel[T] = null): MultiLineLabel = { val label = new MultiLineLabel(id, model); add(label); label }
  def multiLineLabel[T](id: String, value: String): MultiLineLabel = { val label = new MultiLineLabel(id, value); add(label); label }
  // Label
  def label[T](id: String, model: IModel[T] = null): Label = { val label = new Label(id, model); add(label); label }
  def labelf[T](id: String, gtr: ⇒ String): Label = { val label = new ScalaLabel(id, new FodelString(gtr)); add(label); label }
  def label[T](id: String, value: String): Label = { val label = new Label(id, value); add(label); label }

  implicit def ser2model[S <: Serializable](ser: S): IModel[S] = Model.of(ser)
  def textField[T](id: String)(implicit m: ClassTag[T]): STextField[T] = {
    val field = new TextField[T](id) with STextField[T]; field.setType(m.runtimeClass); add(field); field
  }
  def emailField(id: String): STextField[String] = {
    val ef = textField[String](id)
    ef.add(RfcCompliantEmailAddressValidator.getInstance()); ef
  }
  def passField(id: String): SPasswordField = {
    val pass = new PasswordTextField(id) with SPasswordField; add(pass); pass
  }
  def textArea(id: String): STextArea = {
    val field = new TextArea[String](id) with STextArea; field.setType(classOf[String]); add(field); field
  }
  def dateField(id: String, format: String = "dd/MM/yyyy"): DateTextField = {
    val field = DateTextField.forDatePattern(id, format); add(field); field
  }
  def checkGroup[T](id: String): SCheckGroup[T] = { val cg = new CheckGroup[T](id) with SCheckGroup[T]; add(cg); cg; }
  def checkBox(id: String): CheckBox = { val c = new CheckBox(id); add(c); c; }
  def submitLink(id: String, submit: () ⇒ _): SubmitLink = {
    val sl = new SubmitLink(id) { override def onSubmit() = submit() }
    add(sl); sl
  }
  def submitLink(id: String, submit: (SubmitLink) ⇒ _): SubmitLink = {
    val sl = new SubmitLink(id) { override def onSubmit() = submit(this) }
    add(sl); sl
  }
  def button(id: String, submit: () ⇒ _): Button = {
    val button = new Button(id) { override def onSubmit() = submit() }
    add(button); button
  }
  def cancelButton(id: String, submit: () ⇒ _): Button = {
    val button = new Button(id) { override def onSubmit() = submit() }
    button.setDefaultFormProcessing(false)
    add(button); button
  }
  def cancelButton(id: String): Button = {
    val button = new Button(id).setDefaultFormProcessing(false)
    add(button); button
  }
  def form[T](id: String): SForm[T] = {
    val form = new Form[T](id) with SForm[T]; add(form); form
  }
  def form[T](id: String, onsubmit: () ⇒ _): SForm[T] = {
    val form = new Form[T](id) with SForm[T] {
      override def onSubmit() = onsubmit()
    }; add(form); form
  }
  def button(id: String, submit: (Button) ⇒ _): Button = {
    val button = new Button(id) { override def onSubmit() = submit(this) }
    add(button); button
  }
  def pmodel[T](obj: AnyRef, expression: String): IModel[T] = { new PropertyModel[T](obj, expression) }
  def spmodel[T](obj: AnyRef, expression: String): IModel[T] = { val pm = pmodel[T](obj, expression); setDefaultModel(pm); pm }
  def cmodel[T](obj: T): IModel[T] = { new CompoundPropertyModel[T](obj); }
  def scmodel[T](obj: T): IModel[T] = { val cpm = cmodel[T](obj); setDefaultModel(cpm); cpm }
  def compound[T](obj: T): IModel[T] = { val m = new CompoundPropertyModel[T](obj); setDefaultModel(m); m }
  def blink[T](id: String, clazz: Class[_ <: Page], params: PageParameters = null): BookmarkablePageLink[T] = {
    val b = new BookmarkablePageLink[T](id, clazz, params); add(b); b
  }
  def link[T](id: String, click: () ⇒ _): SLink[T] = {
    val l = new Link[T](id) with SLink[T] { override def onClick() = click() }
    add(l); l
  }
  def link[T](id: String, click: (SLink[_]) ⇒ _): SLink[T] = {
    val l = new Link[T](id) with SLink[T] { override def onClick() = click(this) }
    add(l); l
  }
  def link[T](container: WebMarkupContainer, id: String, click: (SLink[_]) ⇒ _): SLink[T] = {
    val l = new Link[T](id) with SLink[T] { override def onClick() = click(this) }
    container.add(l); l
  }
  def listView[T](id: String, populate: (SListItem[T]) ⇒ _): ListView[T] = {
    val lv = new ListView[T](id) {
      override def populateItem(item: ListItem[T]) = populate(item.asInstanceOf[SListItem[T]])
      override def newItem(index: Int, itemModel: IModel[T]): ListItem[T] = new ListItem[T](index, itemModel) with SListItem[T]
    }
    add(lv); lv
  }
  def listView[T](id: String, populate: (SListItem[T]) ⇒ _, m: IModel[_ <: java.util.List[_ <: T]]): ListView[T] = {
    val lv = listView[T](id, populate)
    lv.setDefaultModel(m)
    lv
  }
  def pageableListView[T](id: String, populate: (SListItem[T]) ⇒ _, m: IModel[_ <: java.util.List[T]], pageSize: Int): DSLPageable[T] = {
    val lv = new PageableListView[T](id, m, pageSize) with DSLPageable[T] { 
      override def populateItem(item: ListItem[T]) = populate(item.asInstanceOf[SListItem[T]])
      override def newItem(index: Int, itemModel: IModel[T]): ListItem[T] = new ListItem[T](index, itemModel) with SListItem[T]
    }
    add(lv); lv
  }
  def pageableListView[T](id: String, populate: (SListItem[T]) ⇒ _, l: java.util.List[T], pageSize: Int): DSLPageable[T] = {
    val lv = new PageableListView[T](id, l, pageSize) with DSLPageable[T] {
      override def populateItem(item: ListItem[T]) = populate(item.asInstanceOf[SListItem[T]])
      override def newItem(index: Int, itemModel: IModel[T]): ListItem[T] = new ListItem[T](index, itemModel) with SListItem[T]
    }
    add(lv); lv
  }
  def pagingNavigator(id: String, pageable: IPageable): PagingNavigator = {
    val p = new PagingNavigator(id, pageable); add(p); p
  }
  def mobject[T](obj: T) = setDefaultModelObject(obj)
  def mobject[T]() = getDefaultModelObject.asInstanceOf[T]
  def dropDownChoice[T](id: String, choices: java.util.List[_ <: T] = null): DropDownChoice[T] = {
    val dropdown = new DropDownChoice[T](id, choices); add(dropdown); dropdown
  }
  def emailLink(id: String, email: String, label: String): ExternalLink = {
    val el = new ExternalLink(id, "mailto:" + email, label)
    add(el)
    el
  }
  def emailLink(id: String, email: String) { emailLink(id, email, email) }
  def select[T](id: String): DropDownChoice[T] = select[T](id, null)
  def select[T](id: String, elements: List[T]): DropDownChoice[T] = {
    val ddc = new DropDownChoice[T](id, elements)
    add(ddc); ddc
  }
  def radioGroup[T](id: String, model: IModel[T] = null): DSLRadioGroup[T] = {
    val group = new RadioGroup[T](id) with DSLRadioGroup[T]
    add(group); group
  }
  trait DSLRadioGroup[T] extends RadioGroup[T] {
    def radio[T](id: String, m: IModel[T] = null): Radio[T] = {
      val radio = new Radio[T](id, m, this.asInstanceOf[RadioGroup[T]]); add(radio); radio
    }
    def radio(id: String, value: String): DSLRadioGroup[T] = { radio[String](id, Model.of(value)); this }
    def required() = setRequired(true)
    def optional() = setRequired(false)
  }
  trait SPasswordField extends PasswordTextField with RequireableFormComponent[String] with FunctionalValidatable[String]
  trait SForm[T] extends Form[T] with DSLWicket
  trait SLink[T] extends Link[T] with DSLWicket
  trait RequireableFormComponent[T] extends FormComponent[T] with DSLWicket {
    def required() = setRequired(true)
    def optional() = setRequired(false)
  }
  trait FunctionalValidatable[T] extends FormComponent[T] {
    def validation(f: (IValidatable[T]) ⇒ Unit) = add(new IValidator[T] {
      def validate(validatable: IValidatable[T]) = f(validatable)
    })
  }
  trait SListItem[T] extends ListItem[T] with DSLWicket
  trait SCheckGroup[T] extends CheckGroup[T] with DSLWicket with RequireableFormComponent[java.util.Collection[T]]
  trait STextField[T] extends TextField[T] with RequireableTextField[T] with FunctionalValidatable[T]
  trait STextArea extends TextArea[String] with RequireableTextArea with FunctionalValidatable[String]
  trait RequireableTextField[T] extends TextField[T] with RequireableFormComponent[T]
  trait RequireableTextArea extends TextArea[String] with RequireableFormComponent[String]
  trait DSLMarkupContainer extends WebMarkupContainer with DSLWicket
  trait DSLPageable[T] extends PageableListView[T] {
    def navigator(id: String) = this.getParent.add(new PagingNavigator(id, this))
  }
  object OddBehavior extends AttributeAppender("class", Model.of("odd"))

}
