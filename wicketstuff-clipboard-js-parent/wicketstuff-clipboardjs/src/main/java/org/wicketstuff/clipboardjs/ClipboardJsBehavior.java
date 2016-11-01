package org.wicketstuff.clipboardjs;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.lang.Args;
import org.apache.wicket.util.string.Strings;

public class ClipboardJsBehavior extends Behavior {

    public enum Action {
        COPY,
        CUT
    }

    private String target;
    private IModel<String> textModel;
    private Action action = Action.COPY;

    public ClipboardJsBehavior setTarget(Component target) {
        Args.notNull(target, "target");
        target.setOutputMarkupId(true);
        this.target = "#"+target.getMarkupId();
        return this;
    }

    public ClipboardJsBehavior setTarget(String selector) {
        Args.notNull(selector, "selector");
        this.target = selector;
        return this;
    }

    public ClipboardJsBehavior setText(IModel<String> textModel) {
        Args.notNull(textModel, "textModel");
        this.textModel = textModel;
        return this;
    }

    public ClipboardJsBehavior setAction(Action action) {
        this.action = action != null ? action : Action.COPY;
        return this;
    }

    @Override
    public void bind(final Component component) {
        super.bind(component);
        component.setOutputMarkupId(true);
    }

    @Override
    public void onComponentTag(final Component component, final ComponentTag tag) {
        super.onComponentTag(component, tag);

        if (action == Action.CUT) {
            tag.put("data-clipboard-action", "cut");
        }

        if (!Strings.isEmpty(target)) {
            tag.put("data-clipboard-target", target);
        }

        if (textModel != null) {
            final String text = textModel.getObject();
            if (!Strings.isEmpty(text)) {
                tag.put("data-clipboard-text", text);
            }
        }
    }

    @Override
    public void renderHead(final Component component, final IHeaderResponse response) {
        super.renderHead(component, response);

        response.render(JavaScriptHeaderItem.forReference(ClipboardJsReference.INSTANCE));
        initializeClipboardJs(response, component);
    }

    protected void initializeClipboardJs(final IHeaderResponse response, final Component component) {
        response.render(OnDomReadyHeaderItem.forScript(String.format("new Clipboard('#%s')", component.getMarkupId())));
    }

    @Override
    public void detach(final Component component) {
        super.detach(component);
        if (textModel != null) {
            textModel.detach();
        }
    }
}
