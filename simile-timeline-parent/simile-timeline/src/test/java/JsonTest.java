import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.simile.timeline.json.JsonUtils;
import org.wicketstuff.simile.timeline.model.BandInfoParameters;


public class JsonTest extends TestCase
{
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonTest.class);

	public void testGenerateJson()
	{
		BandInfoParameters object = new BandInfoParameters();
		List<BandInfoParameters> objects = new ArrayList<BandInfoParameters>();
		objects.add(object);
		LOGGER.info(new JsonUtils().convertBandInfos(objects));
	}
}
