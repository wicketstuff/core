package org.wicketstuff.jwicket.ui.effect;


import org.apache.wicket.Component;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.wicketstuff.jwicket.JQuery;
import org.wicketstuff.jwicket.JQueryHeaderContributor;
import org.wicketstuff.jwicket.JQueryResourceReference;
import org.wicketstuff.jwicket.JQuerySpeed;

import java.util.ArrayList;
import java.util.List;


/** This is the base class for all jQuery UI effects. The general usage is
 *
 * <pre>
 * Panel myPanel = new Panel("myPanel");
 * EffectExplode explode = new EffectExplode(); // create effect
 * explode.setSpeed(1000); // set options
 * explode.setPieces(3);
 * add(explode); // add effect to page just for header contribution
 * explode.fire(target, myPanel); // execute effect on component
 * </pre>
 *
 * It is not possible to fire multiple effects just by calling {@code fire}
 * multiple times like this:
 * <pre>
 * Panel myPanel = new Panel("myPanel");
 * EffectExplode explode = new EffectExplode(); // create effect
 * explode.setSpeed(1000); // set options
 * explode.setPieces(3);
 * add(explode); // add effect to page just for header contribution
 * explode.fire(target, myPanel); // execute effect on component
 *
 * EffectBlind blind = new EffectBlind(); // create next effect
 * add(blind); // add effect to page just for header contribution
 * blind.fire(target, myPanel); // execute next effect on comonent
 * </pre>
 * This will lead to surprising results because jQuery does not wait for completion
 * of the explode effect before starting the blind effect.
 *
 * Instead you must build a chain of post effects for a main effect like this:
 * <pre>
 * Panel myPanel = new Panel("myPanel");
 * EffectExplode explode = new EffectExplode(); // create effect
 * explode.setSpeed(1000); // set options
 * explode.setPieces(3);
 * add(explode); // add effect to page just for header contribution
 *
 * List<AbstractJqueryEffect> postEffects = new ArrayList<AbstractJqueryEffect>();
 *
 * EffectBlind blind = new EffectBlind(); // create first post effect
 * add(blind); // add effect to page just for header contribution
 * postEffects.add(blind); // add effect to chain of post effects
 *
 * EffectBounce bounce = new EffectBounce(); // create next post effect
 * add(bounce); // add effect to page just for header contribution
 * postEffects.add(bounce); // add effect to chain of post effects
 *
 * explode.fire(target, postEffects, myPanel); // execute main effect and post effects on component 
 * </pre>
 *
 * It is important to add the effects during Page/Panel creation to ensure
 * that the needed js libraries are added to header.
 * <br/><br/>
 * Correct:
<pre>
final EffectBlind blind = new EffectBlind(); // create first post effect
add(blind); // add effect to page just for header contribution
add(new AjaxLink("id") {
       &#064;Override
       public void onClick(AjaxRequestTarget target) {
          blind.fire(target, component); // fire effect
       }
    }
);
</pre>
 * <br/><br/>
 * Wrong:
<pre>
add(new AjaxLink("id") {
       &#064;Override
       public void onClick(AjaxRequestTarget target) {
          final EffectBlind blind = new EffectBlind(); // create first post effect
          add(blind); // add effect to page just for header contribution
          blind.fire(target, component); // fire effect
       }
    }
);
</pre>
 *
 */
public abstract class AbstractJqueryUiEffect extends JQueryHeaderContributor  {

	private static final long serialVersionUID = 1L;

	private static long nextId = 0;
	
	public static final JQueryResourceReference jQueryUiEffectsCoreJs
		= JQuery.isDebug()
		? new JQueryResourceReference(AbstractJqueryUiEffect.class, "jquery.effects.core.js")
		: new JQueryResourceReference(AbstractJqueryUiEffect.class, "jquery.effects.core.min.js");

	public AbstractJqueryUiEffect(final JQueryResourceReference... requiredLibraries) {
		super(jQueryUiEffectsCoreJs, requiredLibraries);
	}


	protected boolean restoreStyleAfterEffect = true;

	/**	Some effects leave the effected component in a mixed up styling. To avoid this,
	 *	the current style of the component is preserved and restored after the effect
	 *	was shown. By default the preservation is active. Call this method with {@code false}
	 *	as parameter value to turn it off.
	 *
	 *	@param value switch the preservation on or off.
	 *	@return This effect
	 */
	public AbstractJqueryUiEffect setRestoreStyleAfterEffect(final boolean value) {
		this.restoreStyleAfterEffect = value;
		return this;
	}



	protected String speed = null;

	/**	A common parameter for all effects is the speed. You may control the speed in
	 *	predefined {@link JQuerySpeed} steps with this method
	 *
	 *	@param value the desired speed or {@code null} to reset the speed to the default value.
	 */
	public AbstractJqueryUiEffect setSpeed(final JQuerySpeed value) {
		if (value == null)
			this.speed = null;
		else
			this.speed = "'" + value.getSpeed() + "'";
		return this;
	}

	/** The effect speed may also be specified in milliseconds
	 *
	 *	@param the speed in milliseconds or a value <= 0 to reset the speed to the default value.
	 */
	public AbstractJqueryUiEffect setSpeed(final int ms) {
		if (ms <= 0)
			this.speed = null;
		else
			this.speed = String.valueOf(ms);
		return this;
	}



	protected String effectClass = null;

	/**	jQuery applies a specific CSS class during effect processing; e.g.
	 *	{@code "ui-effects-explode"} for {@link Explode}. This is done
	 *	by surrounding the original component by a {@code <div>} tag. The
	 *	component keeps it's CSS class.
	 *
	 *	This method defines a CSS class that replaces the compontnt's
	 *	CSS class during effect. The surrounding {@code <div>} tag and it's
	 *	effect specific class is not touched by this setting. It applies only
	 *	to the component.
	 *
	 *	@param value the CSS class or {@code null} to reset to the default CSS class.
	 */
	public void setEffectClass(final String value) {
		this.effectClass = value;
	}
	public String getEffectClass() {
		return this.effectClass;
	}



	protected String fadeInAfter = null;

	/**	After the execution of an effect, the affected element sometimes is
	 *	no more visible. This may be in result of the effect mode (e.g.
	 *	{@link EffectMode#HIDE} or the effect itself {@link Explode) ore
	 *	some other circumstances.
	 *	By setting the fade in property, the original element is faded in
	 *	after the effect.
	 *
	 *	@param value the speed for the fadeIn action.
	 */
	public AbstractJqueryUiEffect setFadeInAfter(final int speed) {
		if (speed <= 0)
			this.fadeInAfter = null;
		else
			this.fadeInAfter = String.valueOf(speed);
		return this;
	}

	public AbstractJqueryUiEffect setFadeInAfter(final JQuerySpeed speed) {
		if (speed == null)
			this.fadeInAfter = null;
		else
			this.fadeInAfter = "'" + speed.getSpeed() + "'";
		return this;
	}




	abstract String getEffectName();

	/**	A concrete effect must implement this method to append
	 *	the code for it's options. You don't need to render the
	 *	speed option because this option is common for all effects
	 *	and therefore rendered automatically.
	 *
	 * @param jsString a StringBuilder to append the options for the effect
	 */
	abstract void appendOptions(final StringBuilder jsString);


	private String varNameElement;
	private String varNameStyle;
	private String varNameClass;

	/*	Build the javascript code for all effects. Each effect of the chain is
	 *	activated in the post procession function of the preceding effect.
	 */
	private void prepare(final StringBuilder jsString, final AbstractJqueryUiEffect firstEffect, final List<AbstractJqueryUiEffect> chain) {
		if (firstEffect == null && (chain == null || chain.size() == 0)) {
			// We are through. No more effects to append. We can clear up.
			if (restoreStyleAfterEffect) {
				// effectElement<n>.attr('style',originalStyle<n>);
				jsString.append(varNameElement);
				jsString.append(".attr('style',");
				jsString.append(varNameStyle);
				jsString.append(");");

				jsString.append(varNameStyle);
				jsString.append("=null;");
			}
			jsString.append(varNameElement);
			jsString.append("=null;");
			return;
		}
		AbstractJqueryUiEffect currentEffect = firstEffect;
		if (currentEffect == null)
			currentEffect = chain.get(0);

		if (currentEffect.getEffectClass() != null) {
			// Save current class of affected element and set effect class
			// var originalClass<n>=effectElement<n>.attr('class');
			jsString.append("var ");
			jsString.append(varNameClass);
			jsString.append("=");
			jsString.append(varNameElement);
			jsString.append(".attr('class');");

			// Apply temporary effect class
			// effectElement<n>.attr('class','<temporaryClassName>');
			jsString.append(varNameElement);
			jsString.append(".attr('class','");
			jsString.append(currentEffect.getEffectClass());
			jsString.append("');");
		}


		// create js for current effect including effect options
		jsString.append(varNameElement);
		jsString.append(".effect('");
		jsString.append(currentEffect.getEffectName());
		jsString.append("'");
		currentEffect.appendOptions(jsString);

		if (speed != null) {
			jsString.append(",");
			jsString.append(speed);
		}

		// post effect function
		jsString.append(",function(){");

		if (currentEffect.getEffectClass() != null) {
			// Restore the original class of affected element after effect
			// effectElement<n>.attr('class',originalClass<n>);
			jsString.append(varNameElement);
			jsString.append(".attr('class',");
			jsString.append(varNameClass);
			jsString.append(");");

			// clean up variable
			jsString.append(varNameClass);
			jsString.append("=null;");
		}

		if (currentEffect.fadeInAfter != null) {
			// Fade in the Element after effect completion and before starting a new effect
			jsString.append(varNameElement);
			jsString.append(".fadeIn(");
			jsString.append(currentEffect.fadeInAfter);
			jsString.append(");");
		}

		// recurse through the chain of post effects
		if (chain != null || (firstEffect != null && chain == null))
			if (firstEffect != null)
				prepare(jsString, null, chain);
			else
				prepare(jsString, null, chain.subList(1, chain.size()));

		jsString.append("}");
		// End of post effect processing


		jsString.append(");");
	}


	/** Let's go! Execute the effect for a bunch of components
	 * 
	 * @param target
	 * @param components fire the effect on this component
	 */
	public void fire(final AjaxRequestTarget target, final Component... components) {
		fire(target, (List<AbstractJqueryUiEffect>)null, components);
	}


	/** Let's go! Execute the effect for a bunch of components and after the effect
	 *	was completed execute another effect for all components
	 * 
	 * @param target
	 * @param postEffect this effect is executed after completion of this effect
	 * @param components fire the effect on this component
	 */
	public void fire(final AjaxRequestTarget target, final AbstractJqueryUiEffect postEffect, final Component... components) {
		List<AbstractJqueryUiEffect> postEffects = new ArrayList<AbstractJqueryUiEffect>(1);
		postEffects.add(postEffect);
		fire(target, postEffects, components);
	}


	/** Let's go! Execute the effect for a bunch of components and after the effect
	 *	was completed execute some other effects for all components
	 * 
	 * @param target
	 * @param postEffects these effects are executed after completion of this effect 
	 * @param components fire the effect on this component
	 */
	public void fire(final AjaxRequestTarget target, final List<AbstractJqueryUiEffect> postEffects, final Component... components) {
		if (components == null)
			return;

		StringBuilder jsString = new StringBuilder();
		for (Component component : components)
			if (component != null)
				if (component.getOutputMarkupId()) {
					varNameElement = "effectElement" + nextId;
					varNameStyle   = "originalStyle" + nextId;
					varNameClass   = "originalClass" + nextId;
					nextId++;

					// var effectElement<n>=jQuery('#<componentID>');
					jsString.append("{var ");
					jsString.append(varNameElement);
					jsString.append("=jQuery('#");
					jsString.append(component.getMarkupId());
					jsString.append("');");

					if (restoreStyleAfterEffect) {
						// var originalStyle<n>=effectElement<n>.attr('style');
						jsString.append("var ");
						jsString.append(varNameStyle);
						jsString.append("=");
						jsString.append(varNameElement);
						jsString.append(".attr('style');");
					}

					// build js for main effect and postEffects
					prepare(jsString, this, postEffects);

					jsString.append("}");
				}
				else
					throw new WicketRuntimeException("You must not fire a jQuery effet on a component wich hast not set output markupId to true! Component: " + component);

		target.appendJavaScript(jsString.toString());
		//target.getHeaderResponse().renderOnLoadJavascript(jsString.toString());
	}

}
