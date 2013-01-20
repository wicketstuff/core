import org.apache.wicket.ajax.AjaxRequestTarget;

import com.googlecode.wicket.jquery.ui.widget.dialog.AbstractDialog;
import com.googlecode.wicket.jquery.ui.widget.dialog.DialogButton;


public class MyDialog extends AbstractDialog<String>
{
	private static final long serialVersionUID = 1L;

	public MyDialog(String id, String title)
	{
		super(id, title);
	}

	@Override
	protected void onClose(AjaxRequestTarget target, DialogButton button)
	{


	}
}
