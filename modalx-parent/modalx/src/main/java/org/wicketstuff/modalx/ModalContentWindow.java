// -[KeepHeading]-

// -[Copyright]-

/**
 * � 2006, 2009. Step Ahead Software Pty Ltd. All rights reserved.
 * 
 * Source file created and managed by Javelin (TM) Step Ahead Software. To
 * maintain code and model synchronization you may directly edit code in method
 * bodies and any sections starting with the 'Keep_*' marker. Make all other
 * changes via Javelin. See http://stepaheadsoftware.com for more details.
 */
package org.wicketstuff.modalx;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.request.resource.ResourceReference;

// -[Class]-

/**
 * Class Name : ModalContentWindow Diagram : Wicket Generic Modal Windows Project : Echobase
 * framework Type : concrete Specialized form of ModalWindow that knows how to interact with
 * ModelContentPanels in a way which simplifies the code that opens a modal window.
 * 
 * @author Chris Colman
 */
public class ModalContentWindow extends ModalWindow
{
	private static final long serialVersionUID = 1L;

	// -[KeepWithinClass]-
	public static ResourceReference CSS = new PackageResourceReference(ModalContentWindow.class,
		"css/modalx/ModalX.css");

	// -[Fields]-

	protected ModalMgr modalMgr;

	// -[Methods]-

	/**
	 * Describe here
	 */
	public ModalMgr getModalMgr()
	{
		return modalMgr;
	}

	/**
	 * Override in derived class to set up appropriate styles.
	 */
	public void initStyles()
	{
		setCssClassName("w_vegas");
	}

	@Override
	public void renderHead(IHeaderResponse response)
	{
		super.renderHead(response);
		response.render(CssHeaderItem.forReference(CSS));
	}

	/**
	 * Constructs the object
	 */
	public ModalContentWindow(ModalMgr iModalMgr, String id, boolean maskBehind)
	{
		super(id);

		modalMgr = iModalMgr;

		// Set sizes of this ModalWindow. You can also do this from the invoking
		// component
		// but its not a bad idea to set some good default values.
		setInitialWidth(450);
		setInitialHeight(300);

		setCssClassName(ModalWindow.CSS_CLASS_GRAY);

		if (maskBehind)
			setMaskType(ModalWindow.MaskType.SEMI_TRANSPARENT);
		else
			setMaskType(ModalWindow.MaskType.TRANSPARENT);

		setUseInitialHeight(false);
		setResizable(false);

		// Derived classes should call seStyles in their initStyles() override
		initStyles();
	}

}
