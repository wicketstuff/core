package org.wicketstuff.logback;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

/**
 * A page that shows all registered loggers in a drop-down list and allows changing the effective
 * level per logger. Useful for online debugging.
 */
public class ConfiguratorPage extends WebPage
{
	private static final long serialVersionUID = 1L;

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ConfiguratorPage.class);

	private static final List<Level> LEVELS = Arrays.asList(Level.WARN, Level.DEBUG, Level.TRACE,
		Level.INFO, Level.ERROR, Level.ALL);

	public ConfiguratorPage(final PageParameters parameters)
	{
		super(parameters);

		LoggerContext context = (LoggerContext)LoggerFactory.getILoggerFactory();
		List<Logger> loggers = context.getLoggerList();

		final FeedbackPanel feedback = new FeedbackPanel("feedback");
		feedback.setOutputMarkupId(true);

		final DropDownChoice<Logger> loggersDDC = new DropDownChoice<Logger>("loggers",
			Model.of((Logger)null), loggers);

		Collections.sort(LEVELS, new Comparator<Level>()
		{

			public int compare(Level o1, Level o2)
			{
				return o1.levelInt > o2.levelInt ? 1 : -1;
			}
		});

		final DropDownChoice<Level> levelsDDC = new DropDownChoice<Level>("levels",
			Model.of((Level)null), LEVELS);
		levelsDDC.setOutputMarkupId(true);

		add(feedback, loggersDDC, levelsDDC);

		loggersDDC.add(new AjaxFormComponentUpdatingBehavior("onchange")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				Logger log = loggersDDC.getModelObject();
				Level level = log.getEffectiveLevel();
				levelsDDC.setModelObject(level);
				logger.debug("Logger '{}' has level '{}'.", log.getName(), level);
				target.add(levelsDDC);
			}
		});

		levelsDDC.add(new AjaxFormComponentUpdatingBehavior("onchange")
		{
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target)
			{
				Level level = levelsDDC.getModelObject();
				Logger log = loggersDDC.getModelObject();
				log.setLevel(level);

				logger.debug("Logger '{}' now has level '{}'.", log.getName(), level);
				info("Logger on '" + log.getName() + "' has been set to '" + level.toString() + "'");
				target.add(levelsDDC, feedback);
			}
		});
	}
}
