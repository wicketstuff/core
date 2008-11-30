import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wicketstuff.simile.timeline.Timeline;
import org.wicketstuff.simile.timeline.json.JsonUtils;
import org.wicketstuff.simile.timeline.model.BandInfoParameters;


public class JsonTest extends TestCase {
	private static final Log LOG = LogFactory.getLog(JsonTest.class);
	
	public void testGenerateJson()
	{
		
		BandInfoParameters object = new BandInfoParameters();
		List<BandInfoParameters> objects = new ArrayList<BandInfoParameters>();
		objects.add(object);
		LOG.info(new JsonUtils().convertBandInfos(objects));
	}
}
