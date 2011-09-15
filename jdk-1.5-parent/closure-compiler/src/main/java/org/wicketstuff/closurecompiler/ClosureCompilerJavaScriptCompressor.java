package org.wicketstuff.closurecompiler;

import java.util.ArrayList;
import java.util.List;

import com.google.javascript.jscomp.CommandLineRunner;
import com.google.javascript.jscomp.CompilationLevel;
import com.google.javascript.jscomp.Compiler;
import com.google.javascript.jscomp.CompilerOptions;
import com.google.javascript.jscomp.JSSourceFile;
import com.google.javascript.jscomp.Result;
import org.apache.wicket.javascript.IJavaScriptCompressor;
import org.apache.wicket.util.lang.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * create a javascript compressor based on google's closure compiler.
 * </p>
 * due to the amount of cpu used by closure compiler the generated javascript
 * should definitely be cached, e.g. using resource caching in wicket 1.5.
 *
 * @author Peter Ertl
 */
public class ClosureCompilerJavaScriptCompressor implements IJavaScriptCompressor
{
	private static final Logger log = LoggerFactory.getLogger(ClosureCompilerJavaScriptCompressor.class);

	private CompilationLevel level;

	public ClosureCompilerJavaScriptCompressor()
	{
		level = CompilationLevel.ADVANCED_OPTIMIZATIONS;
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
		final Compiler compiler = new Compiler();
		final CompilerOptions options = new CompilerOptions();
		level.setOptionsForCompilationLevel(options);

		// custom configuration options
		configure(compiler);

		// TODO figure out if this is the proper way of invoking the compiler
		// TODO check if compiler instance creation is expensive and instances can / should be pooled
		// TODO integrate logging into slf4j

		// environment for compilation
		final List<JSSourceFile> externs = new ArrayList<JSSourceFile>();
//		final List<JSSourceFile> externs = CommandLineRunner.getDefaultExterns();

		// input sources
		final List<JSSourceFile> inputs = new ArrayList<JSSourceFile>();
		inputs.add(JSSourceFile.fromCode("custom", uncompressed));

		// compile input
		final Result result = compiler.compile(externs, inputs, options);

		if (result.success == false)
		{
			// TODO better error reporting on compiler errors
			return uncompressed;
		}
		// TODO report warnings etc. ?

		return compiler.toSource();
	}

	protected void configure(Compiler compiler)
	{
		// for overriding + configuring
	}
}
