package org.wicketstuff.openlayers.proxy;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.InputStreamRequestEntity;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.wicket.IRequestTarget;
import org.apache.wicket.RequestCycle;
import org.apache.wicket.behavior.AbstractAjaxBehavior;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebRequestCycle;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.protocol.http.servlet.AbortWithHttpStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* @author Michael O'Cleirigh (michael.ocleirigh@rivulet.ca)
* 
* The OpenLayer.ProxyHost javascript variable when defined will allow requests
* to be sent through a server side proxy.  This gets around cross site scripting problems.
* 
* 
* In order to make the behaviour work it needs to be added onto the OpenLayersMap.
* 
* Then the OpenLayers.ProxyHost=behaviour.getProxyUrl() method can be used.
* 
* See openlayers-examples for an example (e.g. MapWithWMSGetFeatureInfo)
*
*/
public class WFSProxyBehaviour extends AbstractAjaxBehavior { 

	/**
	 * 
	 */
	private static final long serialVersionUID = -4710843334047637872L;

	private static final Logger log = LoggerFactory.getLogger(WFSProxyBehaviour.class);
	
	private String wmsURL;
	
	/**
	 * 
	 */
	public WFSProxyBehaviour() {
	}
	
	class ProxyResponseTarget implements IRequestTarget {

		/* (non-Javadoc)
		 * @see org.apache.wicket.IRequestTarget#detach(org.apache.wicket.RequestCycle)
		 */
		public void detach(RequestCycle requestCycle) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see org.apache.wicket.IRequestTarget#respond(org.apache.wicket.RequestCycle)
		 */
		public void respond(RequestCycle requestCycle) {
			
			WebRequest wr =  (WebRequest) requestCycle.getRequest();
			
			
			
			WebResponse response = (WebResponse) requestCycle.getResponse();

			HttpServletRequest request = wr.getHttpServletRequest();
			
			
			
			String requestURL = request.getParameter("url");
			
			
			
			if (requestURL == null) {
				log.warn ("requestURL could not be resolved.");
				throw new AbortWithHttpStatusException(HttpServletResponse.SC_PRECONDITION_FAILED, false);
//				response.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
			}
			try {
				
				HttpClient client = new HttpClient();

				if (request.getMethod().toLowerCase().equals("get")) {

					String decodedURL = URLDecoder.decode(requestURL, "UTF-8");
					
					GetMethod getMethod = new GetMethod(decodedURL);

					int proxyResponseCode = client.executeMethod(getMethod);

					log.debug("redirected get, code = " + proxyResponseCode);
					
					if (proxyResponseCode != 200) {
						throw new AbortWithHttpStatusException(proxyResponseCode, false);
					}
					
//					response.getHttpServletResponse().setStatus(proxyResponseCode);
					
					// Pass response headers back to the client
			        Header[] headerArrayResponse = getMethod.getResponseHeaders();
			        for(Header header : headerArrayResponse) {
			       		response.setHeader(header.getName(), header.getValue());
			        }
			        
			        // Send the content to the client
			        InputStream inputStreamProxyResponse = getMethod.getResponseBodyAsStream();
			        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStreamProxyResponse);

		        	response.write(bufferedInputStream);

					
					
				} else if (request.getMethod().toLowerCase().equals("post")) {
					
					// need to get the first line and get the url.
					
				
					
					String decodedURL = URLDecoder.decode(requestURL, "UTF-8");
					
					PostMethod pm = new PostMethod(decodedURL);
					
					Set<String> parameters = request.getParameterMap().keySet();
					
					for (String p : parameters) {
					
						String value = request.getParameter(p);
						
						pm.setParameter(p, value);				
						
					}
					
					pm.setContentChunked(true);

					pm.setRequestEntity(new InputStreamRequestEntity (request.getInputStream()));
					
					int proxyResponseCode = client.executeMethod(pm);
					
					log.debug("redirected post, code = " + proxyResponseCode);
					
					if (proxyResponseCode != 200) {
						throw new AbortWithHttpStatusException(proxyResponseCode, false);
					}
					
					// Pass response headers back to the client
			        Header[] headerArrayResponse = pm.getResponseHeaders();
			        for(Header header : headerArrayResponse) {
			       		response.setHeader(header.getName(), header.getValue());
			        }
			        
			        // Send the content to the client
			        InputStream inputStreamProxyResponse = pm.getResponseBodyAsStream();
			        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStreamProxyResponse);

			        response.write(bufferedInputStream);
			        

				} else {
					// unsupported
					// fall through
//					response.getHttpServletResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
					throw new AbortWithHttpStatusException(HttpServletResponse.SC_NOT_FOUND, false);
				}
			} catch (Exception e) {
				log.error("getInputStream() failed", e);
				// fall through
//				response.getHttpServletResponse().setStatus(HttpServletResponse.SC_NOT_FOUND);
				throw new AbortWithHttpStatusException(HttpServletResponse.SC_NOT_FOUND, false);
			}
			
		}
		
	}

	/* (non-Javadoc)
	 * @see org.apache.wicket.behavior.IBehaviorListener#onRequest()
	 */
	public void onRequest() {

		
		WebRequestCycle wrc = (WebRequestCycle) RequestCycle.get();
		
		wrc.setRequestTarget(new ProxyResponseTarget());
		
		wrc.setRedirect(false);
		
	
	}


	/**
	 * 
	 * @param onlyTargetActivePage
	 * @return the proxy url that should be appended to OpenLayers.ProxyHost
	 */
	public String getProxyUrl(boolean onlyTargetActivePage) {

		return getCallbackUrl(true) + "&url=";
	}

	
	
	
	

}

