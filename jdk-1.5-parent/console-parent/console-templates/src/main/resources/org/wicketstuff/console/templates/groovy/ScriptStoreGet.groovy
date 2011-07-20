import org.wicketstuff.console.templates.ScriptTemplate
import org.wicketstuff.console.engine.Lang
if (!component.store) return "There is no store attached"
script = component.store.getById(1L)
println "${script.title} [${script.id}, ${script.lang}]"
println script.script
