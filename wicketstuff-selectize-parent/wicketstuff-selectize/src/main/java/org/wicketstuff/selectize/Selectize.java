package org.wicketstuff.selectize;

import java.io.InputStream;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.json.JSONObject;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.io.IOUtils;
import org.wicketstuff.selectize.SelectizeCssResourceReference.Theme;

/**
 * Used to create a selectize choice component
 * 
 * @author Tobias Soloschenko
 *
 */
public class Selectize extends TextField<String> {

    private static final long serialVersionUID = 1L;

    private String delimiter = ",";

    private Theme theme = Theme.NONE;

    public Selectize(String id) {
	this(id, null);
    }

    public Selectize(String id, IModel<String> model) {
	super(id, model);
	setOutputMarkupId(true);
	setOutputMarkupPlaceholderTag(true);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
	super.renderHead(response);
	response.render(JavaScriptHeaderItem.forReference(SelectizeJavaScriptResourceReference.instance()));
	response.render(CssHeaderItem.forReference(SelectizeCssResourceReference.instance(theme)));
	try (InputStream inputStream = Selectize.class.getResourceAsStream("res/js/selectize_init.js")) {
	    String string = new String(IOUtils.toByteArray(inputStream));
	    JSONObject selectizeConfig = new JSONObject();
	    selectizeConfig.put("selectizeMarkupId", getMarkupId());
	    selectizeConfig.put("delimiter", delimiter);
	    String replace = string.replaceAll("%\\(selectizeConfig\\)", selectizeConfig.toString());
	    response.render(OnDomReadyHeaderItem.forScript(replace));
	} catch (Exception e) {
	    throw new WicketRuntimeException("Error while initializing the selectize script", e);
	}
    }

    /**
     * Sets the delimiter (default is ",")
     * 
     * @param delimiter the delimiter to be used
     */
    public void setDelimiter(String delimiter) {
	this.delimiter = delimiter;
    }

    /**
     * Sets the theme of the selectize component
     * 
     * @param theme
     *            the theme
     */
    public void setTheme(Theme theme) {
	this.theme = theme;
    }
}
