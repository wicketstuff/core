package org.wicketstuff.foundation;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.wicketstuff.foundation.revealmodal.FoundationRevealModal;

public class RevealModalPage extends BasePage {

	private static final long serialVersionUID = 1L;

	public RevealModalPage(PageParameters params) {
		super(params);
		
		final String content = "<h2>Awesome. I have it.</h2><p class=\"lead\">Your couch. It is mine.</p><p>I&#39;m a cool paragraph that lives inside of an even cooler modal. Wins!</p><a class=\"close-reveal-modal\">&#215;</a>";
		add(new FoundationRevealModal("basic", Model.of("Click Me For A Modal"), Model.of(content)));
		
		final String videoContent = "<h2>This modal has video</h2><div class=\"flex-video widescreen vimeo\"><iframe width=\"1280\" height=\"720\" src=\"//www.youtube-nocookie.com/embed/wnXCopXXblE?rel=0\" frameborder=\"0\" allowfullscreen></iframe></div><p><a class=\"close-reveal-modal\">&#215;</a>";
		add(new FoundationRevealModal("video", Model.of("Example Modal with Video..."), Model.of(videoContent)));
		
		add(new FoundationRevealModal("modalInModal", Model.of("Modal in a modal...")) {

			private static final long serialVersionUID = 1L;

			@Override
			public WebMarkupContainer createContentPanel(String id) {
				return new ModalInModalPanel(id);
			}
		});
	}
}
