package org.wicketstuff.springreference.examples;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.wicketstuff.springreference.SpringReference;

/**
 * This page shows an example about calling a {@link FinalService#doPublicProtectedFinal() public
 * final method} on a {@link FinalService service} which in turn calls a protected method on itself.
 * This is a case that does not work with dynamic proxies and <code>&#64;{@link SpringBean}</code>
 * but works with {@link SpringReference}.
 * 
 * Subclasses must implement {@link #getFinalService()} used to get the service.
 * 
 * See the subclasses for the different variants.
 * 
 * @author akiraly
 */
public abstract class AbstractFinalPage extends WebPage
{
	private static final long serialVersionUID = -3161899967614848900L;

	/**
	 * Constructor.
	 */
	public AbstractFinalPage()
	{
		add(new Link<Void>("linkFinal")
		{
			private static final long serialVersionUID = 1036441365201560152L;

			@Override
			public void onClick()
			{
				doStuff();
			}
		});
		add(new BookmarkablePageLink<Void>("linkPrivate", AbstractApp.get().getPrivatePage()));
	}

	protected void doStuff()
	{
		FinalService finalService = getFinalService();
		finalService.doPublic();
		finalService.doPublicPrivateFinal();
		finalService.doPublicFinal();
		finalService.doPublicProtected();
		finalService.doPublicProtectedFinal2();

		// this fails with @SpringBean/dynamic proxies but not with
		// SpringReference
		finalService.doPublicProtectedFinal();
	}

	/**
	 * @return service instance
	 */
	public abstract FinalService getFinalService();
}
