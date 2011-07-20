import org.wicketstuff.console.templates.ScriptTemplate
import org.wicketstuff.console.engine.Lang
if (!component.store) return "There is no store attached"
def script = "println \"foo\""
def template = new ScriptTemplate("Foo", script, Lang.GROOVY)
component.store.save(template)
"script saved"
