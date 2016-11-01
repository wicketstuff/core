package org.wicketstuff.clipboardjs;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

/**
 *
 */
public class ClipboardJsBehavior extends Behavior {

    enum Action {
        COPY,
        CUT
    }

    private Component button;
    private String target;
    private Action action = Action.COPY;

    public ClipboardJsBehavior setTarget(Component target) {
        Args.notNull(target, "target");
        target.setOutputMarkupId(true);
        this.target = target.getMarkupId();
        return this;
    }

    public ClipboardJsBehavior setAction(Action action) {
        this.action = action != null ? action : Action.COPY;
        return this;
    }

    @Override
    public void bind(final Component component) {
        super.bind(component);

        if (button != null) {
            throw new IllegalStateException(ClipboardJsBehavior.class.getName() + " can be assigned to only one button");
        }
        button = component.setOutputMarkupId(true);
    }

    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        super.onComponentTag(component, tag);

        if (action == Action.CUT) {
            tag.put("data-clipboard-action", "cut");
        }

        if (!Strings.isEmpty(target)) {
            tag.put("data-clipboard-target", "#" + target);
        }
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        super.renderHead(component, response);

        response.render(JavaScriptHeaderItem.forReference(ClipboardJsReference.INSTANCE));
        response.render(OnDomReadyHeaderItem.forScript(String.format("new Clipboard('#%s')", button.getMarkupId())));
    }
}
