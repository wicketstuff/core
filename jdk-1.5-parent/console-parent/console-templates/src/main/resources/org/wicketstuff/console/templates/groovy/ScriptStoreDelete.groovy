import org.wicketstuff.console.templates.ScriptTemplate
import org.wicketstuff.console.engine.Lang
if (!component.store) return "There is no store attached"
component.store.delete(1L)
