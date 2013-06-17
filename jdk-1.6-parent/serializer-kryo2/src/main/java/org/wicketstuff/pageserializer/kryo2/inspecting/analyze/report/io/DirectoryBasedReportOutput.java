package org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.io;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.wicket.util.file.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.ISerializedObjectTree;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.IReportOutput;
import org.wicketstuff.pageserializer.kryo2.inspecting.analyze.report.IReportRenderer;


public class DirectoryBasedReportOutput {
	
	private final static Logger LOG = LoggerFactory.getLogger(DirectoryBasedReportOutput.class);

	private final File _directory;

	public DirectoryBasedReportOutput(File directory) {
		if (!directory.isDirectory()) throw new RuntimeException("not a directory: "+directory);
		_directory = directory;
		
		LOG.info("write reports into "+directory);
	}
	
	protected void write(IReportKeyGenerator keyGenerator, ISerializedObjectTree tree, IReportRenderer renderer) {
		String report = renderer.render(tree);
		File output=new File(_directory,keyGenerator.keyOf(tree));
		try {
			Files.writeTo(output, new ByteArrayInputStream(report.getBytes(Charset.forName("UTF-8"))));
		} catch (IOException e) {
			throw new RuntimeException("write report to "+output,e);
		}
	}
	
	public IReportOutput with(IReportKeyGenerator keyGenerator) {
		return new KeyReportOutputAdapter(keyGenerator);
	}
	
	class KeyReportOutputAdapter implements IReportOutput {

		private final IReportKeyGenerator _keyGenerator;

		public KeyReportOutputAdapter(IReportKeyGenerator keyGenerator) {
			_keyGenerator = keyGenerator;
		}

		@Override
		public void write(ISerializedObjectTree tree, IReportRenderer renderer) {
			DirectoryBasedReportOutput.this.write(_keyGenerator,tree,renderer);
		}
		
	}
}
