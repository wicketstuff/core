package org.wicketstuff.console.engine

import java.util.Map

import java.io.PrintWriter
import java.io.StringWriter
import java.io.PrintStream
import java.io.ByteArrayOutputStream
import scala.tools.nsc._
import scala.tools.nsc.interpreter._
import scala.tools.nsc.interpreter.{ Results => IR }

class ScalaEngine extends IScriptEngine {

  val settings = new Settings
  settings.usejavacp.value = true
  val interpreter = new IMain(settings)

  def execute(script: String): IScriptExecutionResult = {
    interpret(script, new java.util.HashMap())
  }

  def execute(script: String, bindings: Map[String, Object]): IScriptExecutionResult = {
    interpret(script, bindings)
  }

  def interpret(script: String, bindings: Map[String, Object]): IScriptExecutionResult = {

    val oldOut: PrintStream = System.out
    val oldErr: PrintStream = System.err
    val bout: ByteArrayOutputStream = new ByteArrayOutputStream()
    val newOut: PrintStream = new PrintStream(bout, false);

    var exception: Exception = null
    var output: String = null
    var returnValue: Any = null
    var result: IR.Result = null

    try {
      val it = bindings.entrySet.iterator
      while (it.hasNext) {
        val entry = it.next
        interpreter.quietBind(NamedParam(entry.getKey, entry.getValue()))
      }

      System.setOut(newOut)
      System.setErr(newOut)
      Console.setOut(newOut)
      Console.setErr(newOut)

      result = interpreter.quietRun(script)

      if (result != IR.Success) {
        throw new RuntimeException("Script execution failed")
      }

    } catch {
      case ex: Exception => exception = ex
    } finally {
      System.setOut(oldOut);
      System.setErr(oldErr);
      Console.setOut(oldOut)
      Console.setErr(oldErr)

      newOut.close
      bout.close
      output = bout.toString();

      interpreter.reset
    }

    return new DefaultScriptExecutionResult(script, exception, output, returnValue);
  }

}


