/*
 * $Id: SliderPage.java 4820 2006-03-08 08:21:01Z eelco12 $ $Revision: 4820 $
 * $Date: 2006-03-08 16:21:01 +0800 (Wed, 08 Mar 2006) $
 * 
 * ==============================================================================
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.wicketstuff.yui.examples.pages;

import org.wicketstuff.yui.markup.html.slider.Slider;
import org.wicketstuff.yui.markup.html.slider.SliderSettings;
import org.wicketstuff.yui.examples.WicketExamplePage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

/**
 * Page that displays the Slider component of the Yahoo UI library.
 * 
 * @author Eelco Hillenius
 * @author Josh
 */
public class SliderPage extends WicketExamplePage
{
	/**
	 * Construct.
	 */
	public SliderPage()
	{
        add(new FeedbackPanel("feedback"));
        add(new SliderForm("sliderForm"));
	}
    
    private class SliderForm extends Form {
        
        private Integer wicketScore 	= new Integer(0);
        private Integer strutsScore 	= new Integer(0);
        private Integer tapestryScore 	= new Integer(0);
        private Integer jsfScore 		= new Integer(0);
        
        private TextField tfWicket;
        private TextField tfStruts;
        private TextField tfTapestry;
        private TextField tfJSF;
        
        public SliderForm(String id) {
            super(id);
            
            int leftUp 	  = 150;
            int rightDown = 150;
            int tick 	  = 1;
            float divisor = 10f;
            
            /*
             * leftup and rightup are in pixel sizes so that we can make sure the HTML will fit the overall size
             * of the slider. Through "width" settings on html or css 
             * 
             */
            
            add(tfWicket = new TextField("wicketScore", new PropertyModel(this, "wicketScore")));
            add(new Slider("wicketSlider", new PropertyModel(this, "selection"),  tfWicket, SliderSettings.getDefault(leftUp, rightDown, tick), divisor));

            add(tfStruts = new TextField("strutsScore", new PropertyModel(this, "strutsScore")));
            add(new Slider("strutsSlider", new PropertyModel(this, "selection"), tfStruts, SliderSettings.getDefault(leftUp, rightDown, tick), 30));
            
            add(tfTapestry = new TextField("tapestryScore", new PropertyModel(this, "tapestryScore")));
            add(new Slider("tapestrySlider", new PropertyModel(this, "selection"),  tfTapestry, SliderSettings.getAqua(leftUp, rightDown, tick), 30));

            add(tfJSF= new TextField("jsfScore", new PropertyModel(this, "jsfScore")));
            add(new Slider("jsfSlider", new PropertyModel(this, "selection"), tfJSF, SliderSettings.getAqua(leftUp, rightDown, tick), 30));
        }
        
        protected void onSubmit() {
            info("Wicket: " 	+ this.wicketScore.toString());
            info("Struts: " 	+ this.strutsScore.toString());
            info("Tapestry: " 	+ this.tapestryScore.toString());
            info("JSF: " 		+ this.jsfScore.toString());
        }
        
        public Integer getStrutsScore() {
            return strutsScore;
        }

        public void setStrutsScore(Integer strutsScore) {
            this.strutsScore = strutsScore;
        }

        public Integer getWicketScore() {
            return wicketScore;
        }

        public void setWicketScore(Integer wicketScore) {
            this.wicketScore = wicketScore;
        }

		public Integer getJsfScore()
		{
			return jsfScore;
		}

		public void setJsfScore(Integer jsfScore)
		{
			this.jsfScore = jsfScore;
		}

		public Integer getTapestryScore()
		{
			return tapestryScore;
		}

		public void setTapestryScore(Integer tapestryScore)
		{
			this.tapestryScore = tapestryScore;
		}

		public TextField getTfJSF()
		{
			return tfJSF;
		}

		public void setTfJSF(TextField tfJSF)
		{
			this.tfJSF = tfJSF;
		}

		public TextField getTfStruts()
		{
			return tfStruts;
		}

		public void setTfStruts(TextField tfStruts)
		{
			this.tfStruts = tfStruts;
		}

		public TextField getTfTapestry()
		{
			return tfTapestry;
		}

		public void setTfTapestry(TextField tfTapestry)
		{
			this.tfTapestry = tfTapestry;
		}

		public TextField getTfWicket()
		{
			return tfWicket;
		}

		public void setTfWicket(TextField tfWicket)
		{
			this.tfWicket = tfWicket;
		}
    }

}
