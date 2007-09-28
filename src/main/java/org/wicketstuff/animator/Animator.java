/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.animator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AbstractDefaultAjaxBehavior;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.IHeaderResponse;
import org.apache.wicket.markup.html.resources.JavascriptResourceReference;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.template.TextTemplateHeaderContributor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Animator implements Serializable
{

	private static final long serialVersionUID = 1L;
	private static Logger logger = LoggerFactory.getLogger(Animator.class);

	private List<IAnimatorSubject> subjects;

	private String animatorId;

	private Map<String, String> options;

	public Animator()
	{
		subjects = new ArrayList<IAnimatorSubject>();
		options = new HashMap<String, String>();
	}

	/**
	 * set the transition to easeInOut
	 * 
	 * @return this {@link Animator}
	 */
	public Animator withEaseInOutTransition()
	{
		return withTransition("Animator.tx.easeInOut");
	}

	/**
	 * set the transition to linear
	 * 
	 * @return this {@link Animator}
	 */
	public Animator withLinearTransition()
	{
		return withTransition("Animator.tx.linear");
	}

	/**
	 * set the transition to easeIn
	 * 
	 * @return this {@link Animator}
	 */
	public Animator withEaseInTransition()
	{
		return withTransition("Animator.tx.easeIn");
	}

	/**
	 * set the transition to easeOut
	 * 
	 * @return this {@link Animator}
	 */
	public Animator withEaseOutTransition()
	{
		return withTransition("Animator.tx.easeOut");
	}

	/**
	 * set the transition to strongEaseIn
	 * 
	 * @return this {@link Animator}
	 */
	public Animator withStrongEaseInTransition()
	{
		return withTransition("Animator.tx.strongEaseIn");
	}

	/**
	 * set the transition to strongEaseOut
	 * 
	 * @return this {@link Animator}
	 */
	public Animator withStrongEaseOutTransition()
	{
		return withTransition("Animator.tx.strongEaseOut");
	}

	/**
	 * set the transition to elastic
	 * 
	 * @return this {@link Animator}
	 */
	public Animator withElasticTransition()
	{
		return withTransition("Animator.tx.elastic");
	}

	/**
	 * set the transition to very elastic
	 * 
	 * @return this {@link Animator}
	 */
	public Animator withVeryElasticTransition()
	{
		return withTransition("Animator.tx.veryElastic");
	}

	/**
	 * set the transition to bouncy
	 * 
	 * @return this {@link Animator}
	 */
	public Animator withBouncyTransition()
	{
		return withTransition("Animator.tx.bouncy");
	}

	/**
	 * set the transition to very bouncy
	 * 
	 * @return this {@link Animator}
	 */
	public Animator withVeryBouncyTransition()
	{
		return withTransition("Animator.tx.veryBouncy");
	}

	/**
	 * set the transition to custom easeIn
	 * 
	 * @param acceleration
	 *            the acceleration of the transition
	 * @return this {@link Animator}
	 */
	public Animator withEaseInTransition(int acceleration)
	{
		return withTransition("Animator.makeEaseIn", acceleration);
	}

	/**
	 * set the transition to custom easeOut
	 * 
	 * @param acceleration
	 *            the acceleration of the transition
	 * @return this {@link Animator}
	 */
	public Animator withEaseOutTransition(int acceleration)
	{
		return withTransition("Animator.makeEaseOut", acceleration);
	}

	/**
	 * set the transition to custom elastic
	 * 
	 * @param acceleration
	 *            the acceleration of the transition
	 * @return this {@link Animator}
	 */
	public Animator withElasticTransition(int acceleration)
	{
		return withTransition("Animator.makeElastic", acceleration);
	}

	/**
	 * set the transition to custom bouncy
	 * 
	 * @param acceleration
	 *            the acceleration of the transition
	 * @return this {@link Animator}
	 */
	public Animator withBouncyTransition(int acceleration)
	{
		return withTransition("Animator.makeBouncy", acceleration);
	}

	/**
	 * set the transition to an Attack Decay Sustain Release envelope that
	 * starts and finishes on the same level
	 * 
	 * @param attackEnd
	 *            the attackEnd
	 * @param decayEnd
	 *            the decayEnd
	 * @param suspendEnd
	 *            the suspendEnd
	 * @param sustainLevel
	 *            the sustainLevel
	 * @return this {@link Animator}
	 */
	public Animator withADSRTransition(int attackEnd, int decayEnd, int suspendEnd, int sustainLevel)
	{
		return withTransition(String.format("Animator.makeADSR(%d, %d, %d, %d)", attackEnd,
				decayEnd, suspendEnd, sustainLevel));
	}

	private Animator withTransition(String transition, int acceleration)
	{
		return withTransition(String.format("%s(%d)", transition, acceleration));
	}

	private Animator withTransition(String transition)
	{
		options.put("transition", transition);
		return this;
	}

	/**
	 * sets the interval of the animation.
	 * 
	 * @param interval
	 *            the interval of the animation in milliseconds.
	 * @return this {@link Animator}
	 */
	public Animator interval(int interval)
	{
		options.put("interval", String.valueOf(interval));
		return this;
	}

	/**
	 * sets the duration of the animation.
	 * 
	 * @param duration
	 *            the duration of the animation in milliseconds.
	 * @return this {@link Animator}
	 */
	public Animator duration(int duration)
	{
		options.put("duration", String.valueOf(duration));
		return this;
	}

	/**
	 * adds a subject to the animation
	 * 
	 * @param subject
	 *            the subject to add
	 * @return this {@link Animator}
	 */
	public Animator addSubject(IAnimatorSubject subject)
	{
		subjects.add(subject);
		return this;
	}

	/**
	 * adds a {@code CssStyleSubject} object to the animation
	 * 
	 * @see CssStyleSubject#CssStyleSubject(IModel, String, String)
	 * @return this {@link Animator}
	 */
	public Animator addCssStyleSubject(IModel targets, String fromStyleOrClass,
			String toStyleOrClass)
	{
		return addSubject(new CssStyleSubject(targets, fromStyleOrClass, toStyleOrClass));
	}

	/**
	 * adds a {@code CssStyleSubject} object to the animation
	 * 
	 * @see CssStyleSubject#CssStyleSubject(IModel, String)
	 * @return this {@link Animator}
	 */
	public Animator addCssStyleSubject(IModel targets, String toStyleOrClass)
	{
		return addSubject(new CssStyleSubject(targets, toStyleOrClass));
	}

	/**
	 * adds a {@code NumericalStyleSubject} object to the animation
	 * 
	 * @see NumericalStyleSubject#NumericalStyleSubject(IModel, String, int,
	 *      int)
	 * @return this {@link Animator}
	 */
	public Animator addNumericalStyleSubject(IModel targets, String property, int fromValue,
			int toValue)
	{
		return addSubject(new NumericalStyleSubject(targets, property, fromValue, toValue));
	}

	/**
	 * adds a {@code NumericalStyleSubject} object to the animation
	 * 
	 * @see NumericalStyleSubject#NumericalStyleSubject(IModel, String, int,
	 *      int, String)
	 * @return this {@link Animator}
	 */
	public Animator addNumericalStyleSubject(IModel targets, String property, int from, int to,
			String unit)
	{
		return addSubject(new NumericalStyleSubject(targets, property, from, to, unit));
	}

	/**
	 * adds a {@code DiscreteStyleSubject} object to the animation
	 * 
	 * @see DiscreteStyleSubject#DiscreteStyleSubject(IModel, String, int, int)
	 * @return this {@link Animator}
	 */
	public Animator addDiscreteStyleSubject(IModel targets, String property, int from, int to)
	{
		return addSubject(new DiscreteStyleSubject(targets, property, from, to));
	}

	/**
	 * adds a {@code DiscreteStyleSubject} object to the animation
	 * 
	 * @see DiscreteStyleSubject#DiscreteStyleSubject(IModel, String, int, int,
	 *      double)
	 * @return this {@link Animator}
	 */
	public Animator addDiscreteStyleSubject(IModel targets, String property, int from, int to,
			double threshold)
	{
		return addSubject(new DiscreteStyleSubject(targets, property, from, to, threshold));
	}

	/**
	 * adds a {@code ColorStyleSubject} object to the animation
	 * 
	 * @see ColorStyleSubject#ColorStyleSubject(IModel, String, String, String)
	 * @return this {@link Animator}
	 */
	public Animator addColorStyleSubject(IModel targets, String property, String fromColor,
			String toColor)
	{
		return addSubject(new ColorStyleSubject(targets, property, fromColor, toColor));
	}

	/**
	 * This class encapsulates the actions an animator can take
	 * 
	 * @author Gerolf Seitz
	 * 
	 */
	public static abstract class Action
	{
		private Action()
		{
		}

		public abstract IModel getScript();

		/**
		 * @return a new {@link Action} representing the toggle command
		 */
		public static Action toggle()
		{
			return new Action()
			{
				@Override
				public IModel getScript()
				{
					return new Model("${animatorId}.toggle();");
				}
			};
		}

		/**
		 * @return a new {@link Action} representing the play command
		 */
		public static Action play()
		{
			return new Action()
			{
				@Override
				public IModel getScript()
				{
					return new Model("${animatorId}.play();");
				}
			};
		}

		/**
		 * @return a new {@link Action} representing the reverse command
		 */
		public static Action reverse()
		{
			return new Action()
			{
				@Override
				public IModel getScript()
				{
					return new Model("${animatorId}.reverse();");
				}
			};
		}

		/**
		 * @param from
		 *            the starting position of the animation (0 <= from <= 1)
		 * @param to
		 *            the final position of the animation (0 <= to <= 1)
		 * @return a new {@link Action} representing the seekFromTo command
		 */
		public static Action seekFromTo(final double from, final double to)
		{
			return new Action()
			{
				@Override
				public IModel getScript()
				{
					return new Model(String
							.format("${animatorId}.seekFromTo(%.2f,%.2f);", from, to));
				}
			};
		}

		/**
		 * @param to
		 *            the final position of the animation (0 <= to <= 1)
		 * @return a new {@link Action} representing the seekTo command
		 */
		public static Action seekTo(final double to)
		{
			return new Action()
			{
				@Override
				public IModel getScript()
				{
					return new Model(String.format("${animatorId}.seekTo(%.2f);", to));
				}
			};
		}

		/**
		 * @param to
		 *            the position to jump to
		 * @return a new {@link Action} representing the jumpTo command
		 */
		public static Action jumpTo(final double to)
		{
			return new Action()
			{
				@Override
				public IModel getScript()
				{
					return new Model(String.format("${animatorId}.jumpTo(%.2f);", to));
				}
			};
		}
	}

	/**
	 * Attaches the animator to the {@code component} (adds an AnimatorBehavior
	 * to the component). The {@code action} is executed on the specified
	 * {@code event}.
	 * 
	 * @param component
	 *            the component to which the AnimatorBehavior should be attached
	 *            to.
	 * @param event
	 *            the event on which the action should be executed.
	 * @param action
	 *            the action to be executed
	 * @return the attached {@code AnimatorBehavior}
	 */
	public AnimatorBehavior attachTo(Component component, String event, Action action)
	{
		AnimatorBehavior behavior = new AnimatorBehavior(event, action.getScript());
		component.add(behavior);
		return behavior;
	}

	/**
	 * Gets the id of this animator for use in javascript.
	 * 
	 * @return the id of this animator
	 */
	public String getAnimatorId()
	{
		if (animatorId == null)
		{
			throw new IllegalStateException(
					"You can't call Animator#getAnimatorId yet, as it has not yet been bound to a component!");
		}
		return animatorId;
	}

	private class AnimatorBehavior extends AttributeAppender
	{

		private static final long serialVersionUID = 1L;

		public AnimatorBehavior(String attribute, IModel appendModel)
		{
			super(attribute, true, appendModel, ";");
		}

		@Override
		public void renderHead(IHeaderResponse response)
		{
			super.renderHead(response);

			response.renderJavascriptReference(new JavascriptResourceReference(Animator.class,
					"animator.js"));

			Map<String, String> variables = new HashMap<String, String>();
			variables.put("animatorId", animatorId);

			StringBuffer optBuffer = new StringBuffer();
			optBuffer.append("{ ");
			for (String key : options.keySet())
			{
				optBuffer.append(key);
				optBuffer.append(": ");
				optBuffer.append(options.get(key));
				optBuffer.append(", ");
			}
			optBuffer.append(" }");
			variables.put("options", optBuffer.toString());

			StringBuffer init = new StringBuffer();
			for (int i = 0; i < subjects.size(); i++)
			{
				init.append(".addSubject(");
				init.append(((IAnimatorSubject)subjects.get(i)).getJavaScript());
				init.append(")");
			}
			variables.put("addSubjects", init.toString());

			TextTemplateHeaderContributor.forJavaScript(Animator.class, "wicket-animator.js",
					Model.valueOf(variables)).renderHead(response);
			response.renderOnLoadJavascript("init" + animatorId + "();");
			response.renderJavascriptReference(new JavascriptResourceReference(
					AbstractDefaultAjaxBehavior.class, "wicket-ajax.js"));
		}

		@Override
		public void bind(Component component)
		{
			super.bind(component);
			if (animatorId == null)
			{
				animatorId = component.getId() + "Animator";
			}
			String script = getReplaceModel().getObject().toString();

			getReplaceModel().setObject(script.replaceAll("\\$\\{animatorId\\}", animatorId));

		}
	}
}
