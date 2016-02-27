package com.googlecode.wicket.jquery.ui.samples.pages.kendo.treeview;

import java.util.List;

import org.apache.wicket.ajax.json.JSONObject;

import com.googlecode.wicket.jquery.core.Options;
import com.googlecode.wicket.jquery.core.template.IJQueryTemplate;
import com.googlecode.wicket.jquery.core.template.JQueryTemplate;
import com.googlecode.wicket.jquery.ui.samples.data.bean.Band;
import com.googlecode.wicket.jquery.ui.samples.data.dao.BandsDAO;
import com.googlecode.wicket.kendo.ui.widget.treeview.AjaxTreeView;
import com.googlecode.wicket.kendo.ui.widget.treeview.TreeNode;
import com.googlecode.wicket.kendo.ui.widget.treeview.TreeNodeFactory;
import com.googlecode.wicket.kendo.ui.widget.treeview.TreeViewModel;

public class AjaxTreeViewPage extends AbstractTreeViewPage
{
	private static final long serialVersionUID = 1L;

	public AjaxTreeViewPage()
	{
		Options options = new Options();
		options.set("animation", false);
		// options.set("select", "function(e) { e.preventDefault(); }");

		this.add(this.newTreeView("treeview", newTreeViewModel(), options));
	}

	private AjaxTreeView newTreeView(String id, TreeViewModel model, Options options)
	{
		return new AjaxTreeView(id, model, options) {

			private static final long serialVersionUID = 1L;

			@Override
			protected IJQueryTemplate newTemplate()
			{
				return new JQueryTemplate() {

					private static final long serialVersionUID = 1L;

					@Override
					public String getText()
					{
						return "#= item.text # # if(typeof item.desc != 'undefined') { # : <i>#= item.desc #</i> # } #";
					}
				};
			}

			@Override
			protected TreeNodeFactory newTreeNodeFactory()
			{
				return new TreeNodeFactory() {

					private static final long serialVersionUID = 1L;

					@Override
					public JSONObject toJson(int index, TreeNode<?> node)
					{
						JSONObject object = super.toJson(index, node);

						if (node.getObject() instanceof Band)
						{
							object.put("desc", ((Band) node.getObject()).getDesc());
						}

						return object;
					}
				};
			}
		};
	}

	private static TreeViewModel newTreeViewModel()
	{
		return new TreeViewModel() {

			private static final long serialVersionUID = 1L;

			@Override
			protected List<? extends TreeNode<?>> load(int parentId)
			{
				return BandsDAO.get(parentId);
			}
		};
	}
}
