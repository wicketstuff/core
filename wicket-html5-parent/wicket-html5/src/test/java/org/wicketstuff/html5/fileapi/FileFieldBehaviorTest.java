package org.wicketstuff.html5.fileapi;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.mock.MockRequestParameters;
import org.apache.wicket.util.lang.Bytes;
import org.apache.wicket.util.tester.WicketTester;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link FileFieldChangeBehavior} and {@link FileFieldSizeCheckBehavior}.
 *
 * @author akiraly
 */
public class FileFieldBehaviorTest
{
	private final WicketTester tester = new WicketTester();

	@AfterEach
	public void after()
	{
		tester.destroy();
	}

	@Test
	public void testServerSideParse()
	{
		TestPage page = new TestPage();

		FileFieldChangeBehavior behavior = new FileFieldChangeBehavior()
		{
			private static final long serialVersionUID = 6582070615361579911L;

			@Override
			protected void onEvent(AjaxRequestTarget target, FileList fileList)
			{
				assertNotNull(fileList);
				assertEquals(2, fileList.getNumOfFiles());
				assertEquals(4096, fileList.getSize());
				Html5File file = fileList.get(1);
				assertEquals("test2.txt", file.getName());
				assertEquals(2048, file.getSize());
				assertEquals("text/plain", file.getType());
				assertNotNull(file.getLastModifiedDate());
				assertEquals(1302254582, file.getLastModifiedDate().getTimeInMillis());
			}
		};
		page.getField().add(behavior);

		tester.startPage(page);

		addParams();

		tester.executeBehavior(behavior);
	}

	@Test
	public void testSizeCheck()
	{
		TestPage page = new TestPage();

		FileFieldChangeBehavior behavior = new FileFieldSizeCheckBehavior()
		{
			private static final long serialVersionUID = 4191810142625675846L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, FileList fileList)
			{
			}

			@Override
			protected void onError(AjaxRequestTarget target, FileList fileList)
			{
			}

		};
		page.getField().add(behavior);

		tester.startPage(page);

		addParams();
		page.getForm().setMaxSize(Bytes.gigabytes(1));
		tester.executeBehavior(behavior);
		tester.assertNoErrorMessage();

		addParams();
		page.getForm().setMaxSize(Bytes.kilobytes(1));
		tester.executeBehavior(behavior);
		FeedbackMessage errorMessage = page.getField().getFeedbackMessages().first(FeedbackMessage.ERROR);
		assertNotNull(errorMessage);
	}

	protected void addParams()
	{
		MockRequestParameters params = tester.getRequest().getPostParameters();
		params.addParameterValue("num", "2");
		params.addParameterValue("file[0].name", "test.txt");
		params.addParameterValue("file[0].size", "2048");
		params.addParameterValue("file[0].type", "");
		params.addParameterValue("file[1].name", "test2.txt");
		params.addParameterValue("file[1].size", "2048");
		params.addParameterValue("file[1].type", "text/plain");
		params.addParameterValue("file[1].lastModifiedTime", "1302254582");
	}
}
