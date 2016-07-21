/*
 * Copyright 2012 Igor Vaynberg
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this work except in compliance with
 * the License. You may obtain a copy of the License in the LICENSE file, or at:
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package org.wicketstuff.select2;

import java.io.Serializable;

import org.apache.wicket.Component;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.json.JSONException;
import org.apache.wicket.ajax.json.JSONStringer;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.wicketstuff.select2.json.Json;

/**
 * Select2 settings. Refer to the Select2 documentation for what these options mean.
 * 
 * @author igor
 */
public final class Settings implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * Some predefined width option values
	 */
	public static class Widths
	{
		public static String OFF = "off";
		public static String COPY = "copy";
		public static String RESOLVE = "resolve";
		public static String ELEMENT = "element";
	}

	private Integer minimumInputLength, minimumResultsForSearch;
	private Integer maximumSelectionLength;
	private Object placeholder;
	private boolean allowClear;
	private boolean multiple;
	private boolean closeOnSelect;
	private String id, matcher, tokenizer;
	private String sorter;
	private String templateSelection, templateResult, escapeMarkup;
	private String initSelection;  //TODO Deprecated in Select2 4.0
	private String query;
	private String width;
	private String containerCss, dropdownCss, containerCssClass, dropdownCssClass; //TODO deprecated

	private AjaxSettings ajax;
	private String data;
	private boolean tags;
	private String createTag;
	private String separator;
	private String[] tokenSeparators;
	private boolean selectOnClose;
	private boolean dropdownAutoWidth;
	/**
	 * Theme is an interface that might contribute to the headers.
	 */
	private ISelect2Theme theme;

	/**
	 * If stateless is set to true then component will not be used as context
	 * for serving JSON.
	 */
	private boolean stateless = false;

	/**
	 * Path to which JSON producing resource will be attached.
	 */
	private String mountPath;

	public CharSequence toJson()
	{
		try
		{
			JSONStringer writer = new JSONStringer();
			writer.object();
			Json.writeObject(writer, "minimumInputLength", minimumInputLength);
			Json.writeObject(writer, "minimumResultsForSearch", minimumResultsForSearch);
			Json.writeObject(writer, "maximumSelectionLength", maximumSelectionLength);
			Json.writeObject(writer, "placeholder", placeholder);
			Json.writeObject(writer, "allowClear", allowClear);
			Json.writeObject(writer, "multiple", multiple);
			Json.writeObject(writer, "closeOnSelect", closeOnSelect);
			Json.writeFunction(writer, "id", id);
			Json.writeFunction(writer, "matcher", matcher);
			Json.writeFunction(writer, "tokenizer", tokenizer);
			Json.writeFunction(writer, "sorter", sorter);
			Json.writeFunction(writer, "templateSelection", templateSelection);
			Json.writeFunction(writer, "templateResult", templateResult);
			Json.writeFunction(writer, "escapeMarkup", escapeMarkup);
			Json.writeFunction(writer, "initSelection", initSelection);
			Json.writeFunction(writer, "query", query);
			Json.writeObject(writer, "width", width);
			Json.writeObject(writer, "theme", theme != null ? theme.name() : null);
			Json.writeFunction(writer, "containerCss", containerCss);
			Json.writeObject(writer, "containerCssClass", containerCssClass);
			Json.writeFunction(writer, "dropdownCss", dropdownCss);
			Json.writeObject(writer, "dropdownCssClass", dropdownCssClass);
			Json.writeObject(writer, "separator", separator);
			Json.writeObject(writer, "tokenSeparators", tokenSeparators);
			Json.writeObject(writer, "selectOnClose", selectOnClose);
			Json.writeObject(writer, "dropdownAutoWidth", dropdownAutoWidth);
			if (ajax != null)
			{
				writer.key("ajax");
				ajax.toJson(writer);
			}
			Json.writeFunction(writer, "data", data);
			Json.writeObject(writer, "tags", tags);
			Json.writeFunction(writer, "createTag", createTag);
			writer.key("language").value(Session.get().getLocale().toLanguageTag());
			writer.endObject();

			return writer.toString();
		}
		catch (JSONException e)
		{
			throw new RuntimeException("Could not convert Select2 settings object to Json", e);
		}
	}

	public Integer getMinimumInputLength()
	{
		return minimumInputLength;
	}

	public Settings setMinimumInputLength(Integer minimumInputLength)
	{
		this.minimumInputLength = minimumInputLength;
		return this;
	}

	public Integer getMinimumResultsForSearch()
	{
		return minimumResultsForSearch;
	}

	public Settings setMinimumResultsForSearch(Integer minimumResultsForSearch)
	{
		this.minimumResultsForSearch = minimumResultsForSearch;
		return this;
	}

	public Object getPlaceholder()
	{
		return placeholder;
	}

	public Settings setPlaceholder(Object placeholder)
	{
		this.placeholder = placeholder;
		return this;
	}

	public boolean getAllowClear()
	{
		return allowClear;
	}

	public Settings setAllowClear(boolean allowClear)
	{
		this.allowClear = allowClear;
		if (allowClear && placeholder == null) {
			throw new WicketRuntimeException("Placeholder need to be specified");
		}
		return this;
	}

	public boolean getMultiple()
	{
		return multiple;
	}

	public Settings setMultiple(boolean multiple)
	{
		this.multiple = multiple;
		return this;
	}

	public boolean getCloseOnSelect()
	{
		return closeOnSelect;
	}

	public Settings setCloseOnSelect(boolean closeOnSelect)
	{
		this.closeOnSelect = closeOnSelect;
		return this;
	}

	public String getId()
	{
		return id;
	}

	public Settings setId(String id)
	{
		this.id = id;
		return this;
	}

	public String getTemplateSelection()
	{
		return templateSelection;
	}

	public Settings setTemplateSelection(String templateSelection)
	{
		this.templateSelection = templateSelection;
		return this;
	}

	public String getTemplateResult()
	{
		return templateResult;
	}

	public Settings setTemplateResult(String templateResult)
	{
		this.templateResult = templateResult;
		return this;
	}

	public String getInitSelection()
	{
		return initSelection;
	}

	public Settings setInitSelection(String initSelection)
	{
		this.initSelection = initSelection;
		return this;
	}

	public String getQuery()
	{
		return query;
	}

	public Settings setQuery(String query)
	{
		this.query = query;
		return this;
	}

	public AjaxSettings getAjax()
	{
		return getAjax(false);
	}

	public AjaxSettings getAjax(boolean createIfNotSet)
	{
		if (createIfNotSet && ajax == null)
		{
			ajax = new AjaxSettings();
		}
		return ajax;
	}

	public Settings setAjax(AjaxSettings ajax)
	{
		this.ajax = ajax;
		return this;
	}

	public String getData()
	{
		return data;
	}

	public Settings setData(String data)
	{
		this.data = data;
		return this;
	}

	public boolean getTags()
	{
		return tags;
	}

	public Settings setTags(boolean tags)
	{
		this.tags = tags;
		return this;
	}

	public String getCreateTag()
	{
		return createTag;
	}

	public Settings setCreateTag(String createTag)
	{
		this.createTag = createTag;
		return this;
	}

	public Integer getMaximumSelectionLength()
	{
		return maximumSelectionLength;
	}

	public Settings setMaximumSelectionLength(Integer maximumSelectionLength)
	{
		this.maximumSelectionLength = maximumSelectionLength;
		return this;
	}

	public String getMatcher()
	{
		return matcher;
	}

	public Settings setMatcher(String matcher)
	{
		this.matcher = matcher;
		return this;
	}

	public String getTokenizer()
	{
		return tokenizer;
	}

	public Settings setTokenizer(String tokenizer)
	{
		this.tokenizer = tokenizer;
		return this;
	}

	public String getSorter()
	{
		return sorter;
	}

	public Settings setSorter(String sorter)
	{
		this.sorter = sorter;
		return this;
	}

	public String getEscapeMarkup()
	{
		return escapeMarkup;
	}

	public Settings setEscapeMarkup(String escapeMarkup)
	{
		this.escapeMarkup = escapeMarkup;
		return this;
	}

	public String getWidth()
	{
		return width;
	}

	public Settings setWidth(String width)
	{
		this.width = width;
		return this;
	}

	public ISelect2Theme getTheme()
	{
		return theme;
	}

	public Settings setTheme(final String theme)
	{
		this.theme = new ISelect2Theme()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void renderHead(Component varComponent, IHeaderResponse varResponse)
			{
			}

			@Override
			public String name()
			{
				return theme;
			}
		};
		return this;
	}


	public Settings setTheme(ISelect2Theme theme)
	{
		this.theme = theme;
		return this;
	}

	public String getContainerCss()
	{
		return containerCss;
	}

	public Settings setContainerCss(String containerCss)
	{
		this.containerCss = containerCss;
		return this;
	}

	public String getDropdownCss()
	{
		return dropdownCss;
	}

	public Settings setDropdownCss(String dropdownCss)
	{
		this.dropdownCss = dropdownCss;
		return this;
	}

	public String getContainerCssClass()
	{
		return containerCssClass;
	}

	public Settings setContainerCssClass(String containerCssClass)
	{
		this.containerCssClass = containerCssClass;
		return this;
	}

	public String getDropdownCssClass()
	{
		return dropdownCssClass;
	}

	public Settings setDropdownCssClass(String dropdownCssClass)
	{
		this.dropdownCssClass = dropdownCssClass;
		return this;
	}

	public String getSeparator()
	{
		return separator;
	}

	public Settings setSeparator(String separator)
	{
		this.separator = separator;
		return this;
	}

	public String[] getTokenSeparators()
	{
		return tokenSeparators;
	}

	public Settings setTokenSeparators(String[] tokenSeparators)
	{
		this.tokenSeparators = tokenSeparators;
		return this;
	}

	public boolean getSelectOnClose()
	{
		return selectOnClose;
	}

	public Settings setSelectOnClose(boolean selectOnClose)
	{
		this.selectOnClose = selectOnClose;
		return this;
	}

	public boolean getDropdownAutoWidth()
	{
		return dropdownAutoWidth;
	}

	public Settings setDropdownAutoWidth(boolean dropdownAutoWidth)
	{
		this.dropdownAutoWidth = dropdownAutoWidth;
		return this;
	}

	public boolean isStateless()
	{
		return stateless;
	}

	public void setStateless(boolean stateless)
	{
		this.stateless = stateless;
	}

	public String getMountPath()
	{
		return mountPath;
	}

	public void setMountPath(String mountPath)
	{
		this.mountPath = mountPath;
	}
}
