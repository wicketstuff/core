package org.wicketstuff.yui.examples.pages;

import java.io.Serializable;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.wicketstuff.yui.markup.html.menu2.IYuiMenuAjaxAction;
import org.wicketstuff.yui.markup.html.menu2.contextMenu.MenuItem;
import org.wicketstuff.yui.markup.html.menu2.contextMenu.YuiContextMenu;
import org.wicketstuff.yui.markup.html.menu2.contextMenu.YuiContextMenuBehavior;



public class ContextMenu2Page extends WicketExamplePage {
	
	WebMarkupContainer subPanel = null;
	
	public ContextMenu2Page() {
		
		add( new FeedbackPanel( "feedback"));
		
			
		YuiContextMenu testMenu1 = new YuiContextMenu( "testMenu1" );
		
		testMenu1.add( new MenuItem( "Cut", new TestAction( "Cut")));
		testMenu1.add( new MenuItem( "Copy", new TestAction( "Copy")));
		testMenu1.add( new MenuItem( "Paste", new TestAction( "Paste") ));
		
		YuiContextMenu testMenu2 = new YuiContextMenu( "testMenu2" );

		testMenu2.add( new MenuItem( "Yellow", new ChangeColorAction( "Yellow") ));
		testMenu2.add( new MenuItem( "Green", new ChangeColorAction( "Green") ));
		testMenu2.add( new MenuItem( "Blue", new ChangeColorAction( "Blue") ));
		
		
		YuiContextMenuBehavior cmBehavior = new YuiContextMenuBehavior(testMenu1, testMenu2);
		
		WebMarkupContainer markup =new WebMarkupContainer( "panel" );
		markup.setOutputMarkupId(true);
		
		cmBehavior.applyAttributes( markup, testMenu1, new Model( "123") );
		
		subPanel = new WebMarkupContainer( "subPanel" );
		subPanel.setOutputMarkupId(true);
		cmBehavior.applyAttributes( subPanel, testMenu2, new Model( "777") );
		markup.add( subPanel );

		
		add( markup );
		markup.add( cmBehavior );
	}
	
	public class TestAction implements IYuiMenuAjaxAction, Serializable {
		
		private String name;
		
		public TestAction( String name ) {
			this.name = name;			
		}

		public void onClick(AjaxRequestTarget target, String targetId) {
			System.out.println( "(A) TestAction[" + name + "] clicked" );
			
		}

		public IModel getName() {
			return new Model( name );
		}

		public void onClick() {
			System.out.println( "TestAction[" + name + "] clicked" );
			
		}
		
	}
	
	public class ChangeColorAction implements IYuiMenuAjaxAction, Serializable {
		
		private String name;
		
		public ChangeColorAction( String name ) {
			this.name = name;			
		}

		public void onClick(AjaxRequestTarget target, String targetId) {
			subPanel.add( new AttributeModifier( "bgcolor", true, new Model( name )));
			target.addComponent(subPanel);			
		}

		public IModel getName() {
			return new Model( name );
		}

		public void onClick() {
			System.out.println( "TestAction[" + name + "] clicked" );
			
		}
		
	}

}
