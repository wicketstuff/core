package org.wicketstuff.yui.markup.html.contributor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class YuiDependencyResolver implements Serializable
{
	private final Map<String, Set<String>> deps = Collections
			.synchronizedMap(new HashMap<String, Set<String>>());

	private static final Logger log = LoggerFactory.getLogger(YuiDependencyResolver.class);

	public Set<String> resolveDependencies(String module, String path)
	{
		if (deps.containsKey(module))
		{
			return deps.get(module);
		}
		else
		{
			Set<String> depmods = new LinkedHashSet<String>();
			fetchModuleDependencies(depmods, module, path);
			deps.put(module, depmods);
			return depmods;
		}
	}

	public boolean hasCssAsset(String module, String path)
	{
		String asset = path + "/" + module + "/assets/" + module + ".css";
		URL url = getClass().getResource(asset);
		return null != url;
	}

	protected void fetchModuleDependencies(Set<String> deps, String module, String path)
	{
		String baseName = path + "/" + module + "/" + module;

		InputStream is = getClass().getResourceAsStream(baseName + ".js");

		if (null == is)
		{
			if (log.isDebugEnabled())
			{
				log.debug("Unable to find " + baseName + ".js" + ". Trying beta...");
			}

			is = getClass().getResourceAsStream(baseName + "-beta.js");
			if (null == is)
			{
				if (log.isDebugEnabled())
				{
					log.debug("Unable to find " + baseName + "-beta.js"
							+ ". Trying experimental...");
				}


				is = getClass().getResourceAsStream(baseName + "-experimental.js");
				if (null == is)
				{
					if (log.isInfoEnabled())
					{
						log.info("No Module found for " + module);
					}
					return;
				}
			}
		}

		BufferedReader buffy = new BufferedReader(new InputStreamReader(is));

		String line;
		Pattern pattern = Pattern.compile("\\* @requires (.*)");
		String dependencies = "";
		try
		{
			while ((line = buffy.readLine()) != null)
			{
				Matcher matcher = pattern.matcher(line);
				if (matcher.find())
				{
					if (log.isInfoEnabled())
					{
						log.info("Found a match with " + line);
					}
					dependencies = matcher.group(1);
					break;
				}
			}

			if (dependencies.contains(","))
			{
				String[] splitDeps = dependencies.split(",");
				for (String sdep : splitDeps)
				{
					if (!Strings.isEmpty(sdep))
					{
						String tdep = sdep.trim();
						tdep = tdep.toLowerCase();
						fetchModuleDependencies(deps, tdep, path);
						deps.add(tdep);
					}
				}
			}
			else if (!Strings.isEmpty(dependencies))
			{
				String tdep = dependencies.trim();
				tdep = tdep.toLowerCase();
				fetchModuleDependencies(deps, tdep, path);
				deps.add(tdep);
			}
			return;
		}
		catch (IOException e)
		{
			log.error("error reading " + baseName, e);
			return;
		}
		finally
		{
			try
			{
				buffy.close();
			}
			catch (IOException e)
			{
				log.error("unable to close BufferedReader", e);
			}

		}

	}

	public static void main(String[] args)
	{
		Pattern pat = Pattern.compile("\\* @requires (.*)");
		final String baseDir = "/home/jim/src/wicket/wicket-stuff/wicket-contrib-yui/src/main/java/org/wicketstuff/yui/inc/2.2.2";
		File dir = new File(baseDir);

		FileFilter ff = new FileFilter()
		{

			public boolean accept(File pathname)
			{
				return pathname.isDirectory() && !pathname.getName().startsWith(".");
			}

		};

		File[] files = dir.listFiles(ff);

		for (File file : files)
		{
			String dirName = file.getName();
			log.info("checking directory: " + file.getName());

			String script = baseDir + "/" + dirName + "/" + dirName;
			File scriptFile = new File(script + ".js");

			if (scriptFile.exists())
			{
				log.info("Found script file " + scriptFile.getName());
			}
			else
			{
				log.info("couldn't find " + scriptFile.getName() + " will try with beta next");

				scriptFile = new File(script + "-beta.js");

				if (scriptFile.exists())
				{
					log.info("Found script file " + scriptFile.getName());
				}
				else
				{
					log.info("couldn't find " + scriptFile.getName()
							+ " will try experimental next");
					scriptFile = new File(script + "-experimental.js");
					if (scriptFile.exists())
					{
						log.info("Found script file " + scriptFile.getName());
					}
					else
					{
						log.info("couldn't find " + scriptFile.getName() + ". Giving up");
						continue;
					}
				}
			}

			try (RandomAccessFile raf = new RandomAccessFile(scriptFile, "r"))
			{
				byte[] bytes = new byte[(int)raf.length()];
				raf.read(bytes);
				String contents = new String(bytes);
				Matcher match = pat.matcher(contents);

				if (match.find())
				{

					log.info("We have a match in " + scriptFile + ": " + match.group(1));
					log.info("Starting at " + match.start() + " ending at " + match.end());

				}
				else
				{
					log.info("no match for pattern in " + scriptFile);
				}

			}
			catch (FileNotFoundException e)
			{
				log.error("Not found", e);
				continue;
			}
			catch (IOException e)
			{
				log.error("Can't open", e);
			}

		}
	}
}
