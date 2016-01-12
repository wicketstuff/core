package org.wicketstuff.objectautocomplete;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.IAutoCompleteRenderer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

/**
 * Builder for initializing a {@link org.wicketstuff.objectautocomplete.ObjectAutoCompleteField} and
 * a {@link org.wicketstuff.objectautocomplete.ObjectAutoCompleteBehavior}
 * 
 * The type parameter O specifies the object type of the objects to be selected, I the type of the
 * object's id
 * 
 * @author roland
 * @since May 24, 2008
 */
public class ObjectAutoCompleteBuilder<O, I extends Serializable>
{

	public static final String SEARCH_LINK_PANEL_ID = "searchLinkPanel";

	// Provider which gives the list of objects matching a
	// particular input string
	AutoCompletionChoicesProvider<O> choicesProvider;

	// A listener notified when a search has been cancelled
	ObjectAutoCompleteCancelListener cancelListener;

	// Renderer for the auto completion list
	IAutoCompleteRenderer<O> autoCompleteRenderer;

	// Renderer to use for the read only view when an object
	// has been already selected
	ObjectReadOnlyRenderer<I> readOnlyObjectRenderer;

	// whether the first match should be preselected
	boolean preselect;

	// delay in milliseconds after which an update should occur
	long delay;

	// maximum height of autocompletion list
	int maxHeightInPx;

	// show the list also on an empty input when users pushes down button
	boolean showListOnEmptyInput;

	// list of listeners to be notified on selection changes
	List<ObjectAutoCompleteSelectionChangeListener<I>> selectionChangeListener;

	// whether to react on 'onClick' on the read only view itself
	boolean searchOnClick;

	// whether search should be triggered on paste events
	boolean searchOnPaste;

	// An own component to use as 'search link' content
	Component searchLinkContent;

	// Text to show for the 'search link' when no image is set
	String searchLinkText;

	// image resource or reference to use for button which switchs back
	// to editing the field
	IResource imageResource;
	ResourceReference imageResourceReference;

	// whether the field cannot be changed after the first selection
	boolean unchangeable;

	// an optional renderer (which takes precedense of the 'default' renderer) for rendering
	// a more structured result
	ObjectAutoCompleteResponseRenderer<O> autoCompleteResponseRenderer;

	// property to use as an id for looking up via reflection
	String idProperty;

	// type of the id property. This is needed thanks to generic's type erasure. This type
	// is needed during runtime to help wicket to chose the proper converter.
	Class<I> idType;

	// clear input after selection is performed. Makes only sense in conjunction with
	// at least one selectionChangeListener
	boolean clearInputOnSelection;

	// Tag name which indicates the possible choices, typically "LI"
	String choiceTagName;

	// Width (in px) for the drop down element
	int width;

	// Alignment for the drop down panel
	enum Alignment
	{
		LEFT, RIGHT
	};

	Alignment alignement;

	public ObjectAutoCompleteBuilder(AutoCompletionChoicesProvider<O> pChoicesProvider)
	{
		this.choicesProvider = pChoicesProvider;
		cancelListener = null;
		autoCompleteRenderer = null;
		autoCompleteResponseRenderer = null;
		preselect = false;
		searchOnClick = false;
		searchOnPaste = false;
		showListOnEmptyInput = false;
		maxHeightInPx = -1;
		selectionChangeListener = new ArrayList<ObjectAutoCompleteSelectionChangeListener<I>>();
		searchLinkContent = null;
		searchLinkText = "[S]";
		readOnlyObjectRenderer = null;
		unchangeable = false;
		clearInputOnSelection = false;
		idProperty = "id";
		idType = null;
		choiceTagName = "LI";
		alignement = Alignment.LEFT;
		width = 0;
	}

	// =======================================================================================================
	// Configuration methods
	// =====================

	// Intentionally package scope, to allow the autocompletion panel to register (but not outside
// the package)
	ObjectAutoCompleteBuilder<O, I> cancelListener(ObjectAutoCompleteCancelListener pCancelListener)
	{
		this.cancelListener = pCancelListener;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> autoCompleteRenderer(IAutoCompleteRenderer<O> renderer)
	{
		this.autoCompleteRenderer = renderer;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> autoCompleteResponseRenderer(
		ObjectAutoCompleteResponseRenderer<O> renderer)
	{
		this.autoCompleteResponseRenderer = renderer;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> idProperty(String pIdProperty)
	{
		idProperty = pIdProperty;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> idType(Class<I> type)
	{
		this.idType = type;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> readOnlyRenderer(
		ObjectReadOnlyRenderer<I> pReadOnlyObjectRenderer)
	{
		this.readOnlyObjectRenderer = pReadOnlyObjectRenderer;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> preselect()
	{
		this.preselect = true;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> delay(long pDelay)
	{
		this.delay = pDelay;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> searchOnClick()
	{
		this.searchOnClick = true;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> searchOnPaste()
	{
		this.searchOnPaste = true;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> searchLinkContent(Component pContent)
	{
		if (!pContent.getId().equals(SEARCH_LINK_PANEL_ID))
		{
			throw new IllegalArgumentException(
				"Panel used for the search link content must have id '" + SEARCH_LINK_PANEL_ID +
					"' (and not " + pContent.getId() + ")");
		}
		this.searchLinkContent = pContent;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> searchLinkImage(IResource pImageResource)
	{
		this.imageResource = pImageResource;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> searchLinkImage(ResourceReference pResourceReference)
	{
		this.imageResourceReference = pResourceReference;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> searchLinkText(String pText)
	{
		this.searchLinkText = pText;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> maxHeightInPx(int pMaxHeightInPx)
	{
		this.maxHeightInPx = pMaxHeightInPx;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> showListOnEmptyInput(boolean pShowListOnEmptyInput)
	{
		this.showListOnEmptyInput = pShowListOnEmptyInput;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> updateOnSelectionChange(
		ObjectAutoCompleteSelectionChangeListener<I>... pListeners)
	{
		for (ObjectAutoCompleteSelectionChangeListener<I> listener : pListeners)
		{
			if (listener == null)
			{
				throw new IllegalArgumentException("A listener to be notified for an ajax update "
					+ "on selection change cannot be null");
			}
		}
		selectionChangeListener.addAll(Arrays.asList(pListeners));
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> unchangeable()
	{
		this.unchangeable = true;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> clearInputOnSelection()
	{
		this.clearInputOnSelection = true;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> choiceTagName(String pTagName)
	{
		this.choiceTagName = pTagName;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> alignLeft()
	{
		this.alignement = Alignment.LEFT;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> alignRight()
	{
		this.alignement = Alignment.RIGHT;
		return this;
	}

	public ObjectAutoCompleteBuilder<O, I> width(int pWidth)
	{
		this.width = pWidth;
		return this;
	}


	// ==========================================================================================================
	// Builder methods
	// ===============

	public ObjectAutoCompleteBehavior<O> buildBehavior(Component objectIdHolder)
	{
		setupRenderer();
		return new ObjectAutoCompleteBehavior<O>(objectIdHolder, this);
	}

	private void setupRenderer()
	{
		if (autoCompleteRenderer == null && autoCompleteResponseRenderer == null)
		{
			ObjectAutoCompleteRenderer<O> r = new ObjectAutoCompleteRenderer<O>();
			r.setIdProperty(idProperty);
			autoCompleteRenderer = r;
		}
		else if (autoCompleteRenderer != null && autoCompleteResponseRenderer != null)
		{
			throw new IllegalStateException(
				"Only one type of renderer can be set, either an IAutoCompleteRenderer() "
					+ "or an ObjectAutoCompleteResponseRenderer, but not both");
		}
		else if (autoCompleteResponseRenderer != null)
		{
			autoCompleteResponseRenderer.setIdProperty(idProperty);
		}
		else
		{
			if (!(autoCompleteRenderer instanceof ObjectAutoCompleteRenderer))
			{
				throw new IllegalArgumentException("Can not set idProperty " + idProperty +
					" on a renderer of type " + autoCompleteRenderer.getClass() +
					". Need to operate on a ObjectAutoCompleteRenderer");
			}
			((ObjectAutoCompleteRenderer<?>)autoCompleteRenderer).setIdProperty(idProperty);
		}
	}

	public ObjectAutoCompleteField<O, I> build(String id)
	{
		setupRenderer();
		return build(id, new Model<I>());
	}

	public ObjectAutoCompleteField<O, I> build(String id, IModel<I> model)
	{
		setupRenderer();
		return new ObjectAutoCompleteField<O, I>(id, model, this);
	}
}
