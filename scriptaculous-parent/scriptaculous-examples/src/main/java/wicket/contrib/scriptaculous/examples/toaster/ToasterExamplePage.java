package wicket.contrib.scriptaculous.examples.toaster;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.model.Model;
import org.wicketstuff.scriptaculous.effect.Effect;
import org.wicketstuff.scriptaculous.fx.Toaster;
import org.wicketstuff.scriptaculous.fx.ToasterSettings.ToasterLocation;

public class ToasterExamplePage extends WebPage
{

	public ToasterExamplePage()
	{
		super();
		final Model<String> messageModel=new Model<String>();

		final Toaster toasterStandard = new Toaster("standardToaster",messageModel,false);
		add(toasterStandard);
		add(new AjaxLink<String>("standard")
		{
		
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				messageModel.setObject("heres how I look as standard");
				toasterStandard.publishMessage(target);
			}
		}); 

		
		final Toaster toasterMiddle = new Toaster("middleToaster",messageModel,true);
		toasterMiddle.getToasterSettings().setLocation(ToasterLocation.MiddleMiddle);
		toasterMiddle.getToasterSettings().setToasterBackground("red");
		toasterMiddle.getToasterSettings().setToasterBorderColor("green");
		toasterMiddle.setupCSSLocation();
		add(toasterMiddle);
		add(new AjaxLink<String>("middle")
		{
		
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				messageModel.setObject("heres how I look when in middle");
				toasterMiddle.publishMessage(target);
		
			}
		
		}); 

		
		final Toaster toasterEffects = new Toaster("effectsToaster",messageModel,false);

		// Dont want a middle effect
		toasterEffects.getToasterSettings().setMiddle(null);
		
		Effect.DropOut fade=new Effect.DropOut(toasterEffects.getContainer());
		fade.setQueue("end");
		
		toasterEffects.getToasterSettings().setFade(fade);
		add(toasterEffects);
		add(new AjaxLink<String>("effects")
		{
		
			@Override
			public void onClick(AjaxRequestTarget target)
			{
				messageModel.setObject("heres how I look with custom effects");
				toasterEffects.publishMessage(target);
		
			}
		
		});		
		
	}

}
