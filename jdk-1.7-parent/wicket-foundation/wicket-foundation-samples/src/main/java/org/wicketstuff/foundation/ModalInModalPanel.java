package org.wicketstuff.foundation;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;
import org.wicketstuff.foundation.revealmodal.FoundationRevealModal;

public class ModalInModalPanel extends Panel {

	private static final long serialVersionUID = 1L;

	public ModalInModalPanel(String id) {
		super(id);
		final String content = "<h2>This is a second modal.</h2><p>See? It just slides into place after the other first modal. Very handy when you need subsequent dialogs, or when a modal option impacts or requires another decision.</p><a class=\"close-reveal-modal\">&#215;</a>";
		add(new FoundationRevealModal("secondModal", Model.of("Second Modal..."), Model.of(content)));
	}
}
