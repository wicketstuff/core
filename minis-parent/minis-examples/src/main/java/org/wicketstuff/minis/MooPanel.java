package org.wicketstuff.minis;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.wicketstuff.minis.mootipbehavior.MootipPanel;

public class MooPanel extends MootipPanel {
	public MooPanel() {
		super();
		add(new Label("counter",new AbstractReadOnlyModel<String>(){
			
			private int counter=0;
			@Override
		public String getObject() {
			counter++;
			return counter+"";
		}}));
	}
}
