package org.apache.wicket.extensions.sitemap;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.http.WebRequest;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.AbstractResource;
import org.apache.wicket.request.resource.IResource;
import org.apache.wicket.request.resource.ResourceReference;

public abstract class SiteMapIndex extends ResourceReference implements Observer {

	private static final long serialVersionUID = 1L;
	private static final String PARAM_SITEMAP_OFFSET = "offset";
	private static final String PARAM_SITEMAP_SOURCEINDEX = "sourceindex";

	private static final String HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
			+ "<sitemapindex xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n";
	private static final String FOOTER = "</sitemapindex>";
	private static final int MAX_BYTES_SITEMAP = 10485760; // 10 megabyte
	private static final int MAX_ENTRIES_PER_SITEMAP = 50000;
	private static final SimpleDateFormat STRIPPED_DAY_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private String domain;

	public SiteMapIndex() {
		super("sitemap.xml");
	}

	@Override
	public IResource getResource() {
		return new SiteMapResourceResponse();
	}

	public String getDomain() {
		if (domain == null) {
			final Request rawRequest = RequestCycle.get().getRequest();
			if (!(rawRequest instanceof WebRequest)) {
				throw new WicketRuntimeException("sitemap.xml generation is only possible for http requests");
			}
			WebRequest wr = (WebRequest) rawRequest;
			domain = "http://" + ((HttpServletRequest) wr.getContainerRequest()).getHeader("host");
		}
		return domain;
	}

	@Override
	public void update(Observable o, Object arg) {
		// todo feedback loop to adjust block sizes
		if (o instanceof SiteMapFeed) {
			final SiteMapFeed siteMapFeed = (SiteMapFeed) o;
			if ((siteMapFeed.getEntriesWritten() > MAX_ENTRIES_PER_SITEMAP)
					|| (siteMapFeed.getBytesWritten() > MAX_BYTES_SITEMAP)) {
				throw new IllegalStateException("please adjust block sizes for this sitemap.");
			}
		}
	}

	private class SiteMapResourceResponse extends AbstractResource {
		private static final long serialVersionUID = 1L;

		@Override
		protected ResourceResponse newResourceResponse(Attributes attributes) {
			ResourceResponse response = new ResourceResponse();
			response.setWriteCallback(new WriteCallback() {
				@Override
				public void writeData(final Attributes attributes) {
					PrintWriter w = new PrintWriter(attributes.getResponse().getOutputStream());

					final Integer index = attributes.getParameters().get(PARAM_SITEMAP_OFFSET).toOptionalInteger();
					final Integer sourceIndex = attributes.getParameters().get(PARAM_SITEMAP_SOURCEINDEX)
							.toOptionalInteger();

					try {
						if (index != null && sourceIndex != null) {

							SiteMapFeed feed = new SiteMapFeed(new IOffsetSiteMapEntryIterable.SiteMapIterable() {
								@Override
								public IOffsetSiteMapEntryIterable.SiteMapIterator iterator() {
									return getDataSources()[sourceIndex].getIterator(index);
								}
							});
							feed.addObserver(SiteMapIndex.this);
							feed.writeFeed(w);
						} else {
							w.write(HEADER);
							int sourceNumber = 0;
							for (IOffsetSiteMapEntryIterable dataBlock : getDataSources()) {
								int upperLimitNumblocks = dataBlock.getUpperLimitNumblocks();
								for (int i = 0; i < upperLimitNumblocks; i++) {
									w.append("<sitemap>\n<loc>");
									final PageParameters params = new PageParameters();
									params.add(PARAM_SITEMAP_SOURCEINDEX, String.valueOf(sourceNumber));
									params.add(PARAM_SITEMAP_OFFSET,
											String.valueOf(i * dataBlock.getElementsPerSiteMap()));
									final String url = getDomain() + "/"
											+ RequestCycle.get().mapUrlFor(SiteMapIndex.this, params);
									w.append(StringEscapeUtils.escapeXml(url));
									Date changed = dataBlock.changedDate();
									w.append("</loc>\n");
									if (changed != null) {
										w.append("<lastmod>");
										w.append(STRIPPED_DAY_FORMAT.format(dataBlock.changedDate()));
										w.append("</lastmod>\n");
									}
									w.append("</sitemap>\n");
								}
								sourceNumber++;
							}
							w.write(FOOTER);
						}
						w.flush();
					} catch (IOException e) {
						throw new WicketRuntimeException("unable to construct sitemap.xml for request: "
								+ ((HttpServletRequest) ((WebRequest) attributes.getRequest()).getContainerRequest())
										.getRemoteAddr(), e);
					} finally {
						w.close();
					}

				}
			});
			response.setFileName("sitemap.xml");
			return response;
		};
	}

	public abstract IOffsetSiteMapEntryIterable[] getDataSources();
}