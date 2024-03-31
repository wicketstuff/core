package org.wicketstuff.scala.sample

case class Presentation(id:Long, name:String, author:String, var votes:Int) {
  def this()=this(0,"","",0)
  def this(name:String, author:String)=this(0,name,author,0)
  override def toString = s"$id $name $author"
  
  def addVotes(v:Vote) = votes += 1
}

object Presentation {

  var stub =
    p(2, "Grails", "Richard", 2) ::
    p(1, "Scala", "Antony Stubbs", 2) ::
    p(0, "Wicket", "Antony Stubbs", 1) :: Nil

  def p(id:Long, name:String, author:String, votes:Int) = new Presentation(id, name, author, votes)

  def add(p:Presentation) = {
    stub = stub :+ p
    println("New presentation: " + p)
  }
}

case class Vote(email:String){
  def this()=this("")
  override def toString = "email: " + email
}
