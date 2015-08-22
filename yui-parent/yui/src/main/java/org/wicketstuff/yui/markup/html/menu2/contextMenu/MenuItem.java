package org.wicketstuff.yui.markup.html.menu2.contextMenu;

import java.util.HashMap;
import java.util.Iterator;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAction;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAjaxAction;

public class MenuItem extends AbstractMenuItem {

	private String helpText;
	private String url;
	private String target;
	private boolean emphasis;
	private boolean strongEmphasis;
	private boolean selected;
	private boolean checked;
	private String classname;
	private String onClick;
	
	private IYuiMenuAction action;
	
	public MenuItem( String id, IYuiMenuAction action ) {
		this( id, id );
		this.action = action;
		
		if ( action.getName() != null && action.getName().getObject() != null ) {
			setText( action.getName().getObject().toString() );
		}
	}
	
	public MenuItem( String id ) {
		this( id, id );
	}

	public MenuItem(String id, String text) {
		super(id, text);
	}
	
	public void onClick() {
		if ( action != null ) {
			action.onClick();
		}
	}

	public void onClick(AjaxRequestTarget target, String targetId) {
		
		if ( action instanceof IYuiMenuAjaxAction ) {
			((IYuiMenuAjaxAction)action).onClick(target, targetId);
		}
	}

	public String getHelpText() {
		return helpText;
	}

	public void setHelpText(String helpText) {
		this.helpText = helpText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public boolean isEmphasis() {
		return emphasis;
	}

	public void setEmphasis(boolean emphasis) {
		this.emphasis = emphasis;
	}

	public boolean isStrongEmphasis() {
		return strongEmphasis;
	}

	public void setStrongEmphasis(boolean strongEmphasis) {
		this.strongEmphasis = strongEmphasis;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getOnClick() {
		return onClick;
	}

	public void setOnClick(String onClick) {
		this.onClick = onClick;
	}

	public HashMap<String, String> getProperties() {
		HashMap<String, String> props = new HashMap();

		props.put("text", getText());

		if (url != null) {
			props.put("url", url);
		}
		if (target != null) {
			props.put("target", target);
		}

		if (emphasis) {
			props.put("emphasis", String.valueOf(emphasis));
		}
		if (strongEmphasis) {
			props.put("strongemphasis", String.valueOf(strongEmphasis));
		}
		if (isDisabled()) {
			props.put("disabled", String.valueOf(isDisabled()));
		}
		if (selected) {
			props.put("selected", String.valueOf(selected));
		}

		if (checked) {
			props.put("checked", String.valueOf(checked));
		}




		if (classname != null) {
			props.put("classname", classname);
		}

		return props;
	}

	public String getItemData(YuiContextMenuBehavior behavior) {
		StringBuffer buf = new StringBuffer();

		buf.append("{");
		HashMap<String, String> props = getProperties();
		Iterator<String> keys = props.keySet().iterator();
		while (keys.hasNext()) {
			String key = keys.next();
			buf.append(key).append(": ");
			buf.append("\"").append(props.get(key)).append("\"");
			if (keys.hasNext()) {
				buf.append(",\n");
			}

		}
		buf.append( "\n,onclick: { fn: " ).append( getPathToRoot() + " }");
		buf.append(" }");

		return buf.toString();
	}

}
