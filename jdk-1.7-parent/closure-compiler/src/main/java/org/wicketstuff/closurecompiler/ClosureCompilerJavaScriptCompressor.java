package org.wicketstuff.closurecompiler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.javascript.jscomp.SourceFile;
import org.apache.wicket.javascript.IJavaScriptCompressor;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.JSSourceFile;
import com.google.javascript.jscomp.Result;

/**
 * <p>
 * create a javascript compressor based on google's closure compiler.
 * </p>
 * due to the amount of cpu used by closure compiler the generated javascript should definitely be
 * cached, e.g. using resource caching in wicket 1.5.
 * 
 * @author Peter Ertl
 */
public class ClosureCompilerJavaScriptCompressor implements IJavaScriptCompressor
{
	private static final Logger log = LoggerFactory.getLogger(ClosureCompilerJavaScriptCompressor.class);

	private CompilationLevel level;

	public ClosureCompilerJavaScriptCompressor()
	{
		level = CompilationLevel.SIMPLE_OPTIMIZATIONS;
	}

	public CompilationLevel getLevel()
	{
		return level;
	}

	public void setLevel(CompilationLevel level)
	{
		this.level = Args.notNull(level, "level");
	}

	public String compress(String uncompressed)
	{
		try
		{
			return compressSource(uncompressed);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return uncompressed;
		}
	}

	public final String compressSource(String uncompressed) throws Exception
	{
		// environment for compilation
		final List<SourceFile> externs = CommandLineRunner.getDefaultExterns();

		// create compiler + options
		final Compiler compiler = new Compiler();
		final CompilerOptions options = new CompilerOptions();
		level.setOptionsForCompilationLevel(options);

		// never remove unused stuff:
		// this only would work when we had all javascript for the page bundled together
		// also this will not work due to the dynamic rendering of javascript after page creation
		options.removeUnusedVars = false;
		options.removeUnusedLocalVars = false;
		options.removeUnusedPrototypeProperties = false;
		options.removeUnusedPrototypePropertiesInExterns = false;

		// custom configuration options
		configure(compiler, options, externs);

		// TODO integrate logging into slf4j

		// input sources
		final List<JSSourceFile> inputs = new ArrayList<JSSourceFile>();
		inputs.add(JSSourceFile.fromCode("custom", uncompressed));

		// compile input
		final Result result = compiler.compile(externs, inputs, options);

		if (result.success == false)
		{
			throw new ClosureCompilationException(Arrays.asList(result.errors));
		}
		return compiler.toSource();
	}

	protected void configure(Compiler compiler, CompilerOptions options, List<SourceFile> externs)
	{
		// for overriding + configuring
	}
}
