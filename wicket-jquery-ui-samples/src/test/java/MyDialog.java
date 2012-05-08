import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.ui.dialog.AbstractDialog;


public class MyDialog extends AbstractDialog<String>
{
	private static final long serialVersionUID = 1L;

	public MyDialog(String id, String title)
	{
		super(id, title);
	}

	@Override
	protected void onClose(AjaxRequestTarget target, String button)
	{

		
	}
}
