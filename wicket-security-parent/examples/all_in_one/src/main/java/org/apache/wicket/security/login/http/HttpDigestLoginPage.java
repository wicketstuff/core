/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.security.login.http;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.Application;
import org.apache.wicket.IPageMap;
import org.apache.wicket.PageParameters;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.model.IModel;
import org.apache.wicket.protocol.http.WebRequest;
import org.apache.wicket.protocol.http.WebResponse;
import org.apache.wicket.security.WaspSession;
import org.apache.wicket.security.authentication.LoginException;
import org.apache.wicket.util.crypt.Base64;
import org.apache.wicket.util.lang.Objects;
import org.apache.wicket.util.lang.PropertyResolver;
import org.apache.wicket.util.lang.PropertyResolverConverter;
import org.apache.wicket.util.string.AppendingStringBuffer;
import org.apache.wicket.util.string.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ==WARNING, This class is not production ready, and as of yet contains a serious bug
 * preventing this class from doing its intended task.== Login page that uses
 * httpauthentication to login. This class adds support for the digest scheme. RFC 2617.
 * Support for the basic scheme is configurable. Before you use this class you should at
 * least understand the principals behind digest authentication. A few notes:<br />
 * <ul>
 * <li>if you want to use qop=auth-int you need to do this yourself in
 * {@link #getQop(WebRequest, WebResponse)} and
 * {@link #getA2Value(org.apache.wicket.security.login.http.HttpDigestLoginPage.DigestAuthorizationRequestHeader, WebRequest)}
 * .</li>
 * <li>You need to take care of storing the checksum H(A1) if you use the MD5-sess
 * algorithm</li>
 * <li>You need either the value of H(A1) for the specified algorithm or the clear text
 * password of the user</li>
 * <li>No Authentication-Info header is send unless you do so in
 * {@link #addDigestHeaders(WebRequest, WebResponse)}, this means only a single nonce is
 * used, see also {@link #validateNonceCount(String, String)}</li>
 * </ul>
 * 
 * @author marrink
 * @see <a href="http://tools.ietf.org/html/rfc2617">rfc2617</a>
 */
public abstract class HttpDigestLoginPage extends HttpAuthenticationLoginPage
{
	private static final Logger log = LoggerFactory.getLogger(HttpDigestLoginPage.class);

	/**
	 * Matches recurring patterns like : key="some value" or key=value separated by a
	 * comma (,). groups are as following 1:key-value pair, 2:key, 3:value without quotes
	 * if value was quoted, 4: value if value was not quoted.
	 */
	private static final Pattern HEADER_FIELDS =
		Pattern.compile("(([a-zA-Z]+)=(?:\"([\\p{Graph}\\p{Blank}]+?)\"|([^\\s\",]+)))+,?");

	private boolean allowBasicAuthenication = true;

	/**
	 * If the MD5-sess algorithm is used the hash value is only calculated once. The
	 * results of the checksum of this hash are stored in this value. The spec calls this
	 * H(A1).
	 * 
	 * @see <a href="http://tools.ietf.org/html/rfc2617#section-3.2.2.2">section 3.2.2.2
	 *      (A1)</a>
	 */
	private String a1ChecksumForMD5Sess;

	/**
	 * The nonceCount of the server. Please build your own support for multiple nonces.
	 */
	private int nonceCount;

	/**
	 * Construct.
	 */
	public HttpDigestLoginPage()
	{
	}

	/**
	 * Construct.
	 * 
	 * @param model
	 */
	public HttpDigestLoginPage(IModel< ? > model)
	{
		super(model);
	}

	/**
	 * Construct.
	 * 
	 * @param pageMap
	 */
	public HttpDigestLoginPage(IPageMap pageMap)
	{
		super(pageMap);
	}

	/**
	 * Construct.
	 * 
	 * @param parameters
	 */
	public HttpDigestLoginPage(PageParameters parameters)
	{
		super(parameters);
	}

	/**
	 * Construct.
	 * 
	 * @param pageMap
	 * @param model
	 */
	public HttpDigestLoginPage(IPageMap pageMap, IModel< ? > model)
	{
		super(pageMap, model);
	}

	/**
	 * @see org.apache.wicket.security.login.http.HttpAuthenticationLoginPage#handleAuthentication(org.apache.wicket.protocol.http.WebRequest,
	 *      org.apache.wicket.protocol.http.WebResponse, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	protected void handleAuthentication(WebRequest request, WebResponse response, String scheme,
			String param) throws LoginException
	{
		if (!handleDigestAuthentication(request, response, scheme, param))
			return;
		if (isAllowBasicAuthenication())
			super.handleAuthentication(request, response, scheme, param);
		else
		{
			log.error("Unsupported Http authentication type: " + scheme);
			throw new RestartResponseAtInterceptPageException(Application.get()
				.getApplicationSettings().getAccessDeniedPage());
		}
	}

	/**
	 * Handles authentication for the "Digest" scheme. If the scheme is not the digest
	 * scheme true is returned so another implementation may try it
	 * 
	 * @param request
	 * @param response
	 * @param scheme
	 * @param param
	 * @return true if authentication by another scheme should be attempted, false if
	 *         authentication by another scheme should not be attempted.
	 * @throws LoginException
	 *             if the user can not be logged in
	 */
	protected boolean handleDigestAuthentication(WebRequest request, WebResponse response,
			String scheme, String param) throws LoginException
	{
		if (!"Digest".equalsIgnoreCase(scheme))
			return true;
		if (param == null)
		{
			log.error("Digest headers not supplied");
			return false;
		}
		DigestAuthorizationRequestHeader header = parseHeader(request);
		if (header == null)
		{
			log.error("Invalid Digest headers supplied:" + param);
			return false;
		}
		String supportedQop = getQop(request, response);
		boolean qopSupport = !Strings.isEmpty(supportedQop);
		if (qopSupport)
		{
			// if we sent qop header the client must return one of the options
			String[] qopOptions = supportedQop.split(" ");
			boolean supported = false;
			for (int i = 0; i < qopOptions.length && !supported; i++)
				supported = qopOptions[i].equals(header.getQop());
			if (!supported)
			{
				response.getHttpServletResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
			// we requested qop so these headers must be present
			if (Strings.isEmpty(header.getCnonce()) || Strings.isEmpty(header.getNc()))
			{
				response.getHttpServletResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
			this.nonceCount++;
			if (!validateNonceCount(header.getNonce(), header.getNc()))
			{
				log.warn("Nonce-count failed, expected: " + Integer.toHexString(this.nonceCount)
					+ " but got " + header.getNc() + ", possible replay");
				response.getHttpServletResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
		}
		else
		{
			// no qop support so these headers are not allowed
			if (!(Strings.isEmpty(header.getCnonce()) && Strings.isEmpty(header.getNc())))
			{
				response.getHttpServletResponse().setStatus(HttpServletResponse.SC_BAD_REQUEST);
				return false;
			}
		}
		// verify response (request-digest)
		String expectedDigest;
		// FIXME expected digest does not match digest generated by ie7, verify
		// our specs.
		String hA1 = getA1Checksum(header, request), a2 = getA2Value(header, request);
		if ("auth".equals(header.getQop()) || "auth-int".equals(header.getQop()))
		{
			expectedDigest =
				digest(header.getAlgorithm(), hA1, header.getNonce() + ":" + header.getNc() + ":"
					+ header.getCnonce() + ":" + header.getQop() + ":"
					+ checksum(header.getAlgorithm(), a2));
		}
		else
		{
			expectedDigest =
				digest(header.getAlgorithm(), hA1, header.getNonce() + ":"
					+ checksum(header.getAlgorithm(), a2));
		}
		if (!expectedDigest.equals(header.getResponse()))
		{
			log
				.warn("A request-digest from the client did not match the expected value, this might indicate malicious activity.");
			if (log.isDebugEnabled())
				log.debug("Expected the following digest: \"" + expectedDigest
					+ "\", the request contained the following headers: " + header);
		}
		else
		{
			// username and password are now available and validated
			Object loginContext = getDigestLoginContext(header.getUsername());
			Session session = Session.get();
			if (session instanceof WaspSession)
			{
				if (!isAuthenticated())
					((WaspSession) session).login(loginContext);
				if (!continueToOriginalDestination())
				{
					throw new RestartResponseAtInterceptPageException(Application.get()
						.getHomePage());
				}
			}
			else
				log.error("Unable to find WaspSession");
		}
		return false;
	}

	/**
	 * Delivers a context suitable for logging in with the specified username. The
	 * password can be considered verified, and is unknown to the server at this time
	 * (only a checksum is required to verify the password). Please refer to your specific
	 * wasp implementation for a suitable context.
	 * 
	 * @param username
	 * @return the login context or null if none could be created
	 */
	protected abstract Object getDigestLoginContext(String username);

	/**
	 * Calculates the checksum for the A1 value from the specification. This
	 * implementation knows how to calculate this value for MD5 and MD5-sess but requires
	 * the cleartext password. If you do not have the cleartext password you must know the
	 * checksum and return it here directly.
	 * 
	 * @param header
	 * @param request
	 * @return the calculated value
	 * @throws WicketRuntimeException
	 *             if the algorithm is not understood
	 * @see <a href="http://tools.ietf.org/html/rfc2617#section-3.2.2.2">section 3.2.2.2
	 *      (A1)</a>
	 * @see #onMD5SessHashCalculated()
	 */
	protected String getA1Checksum(DigestAuthorizationRequestHeader header, WebRequest request)
	{
		if (Strings.isEmpty(header.getAlgorithm()) || "MD5".equals(header.getAlgorithm()))
			return checksum(header.getAlgorithm(), header.getUsername() + ":" + header.getRealm()
				+ ":" + getClearTextPassword(header.getUsername()));
		else if ("MD5-sess".equals(header.getAlgorithm()))
		{
			if (Strings.isEmpty(getA1ChecksumForMD5Sess()))
			{
				setA1ChecksumForMD5Sess(checksum(header.getAlgorithm(), checksum(header
					.getAlgorithm(), header.getUsername() + ":" + header.getRealm() + ":"
					+ getClearTextPassword(header.getUsername()))
					+ ":" + header.getNonce() + ":" + header.getCnonce()));
				onMD5SessHashCalculated();
			}
			return getA1ChecksumForMD5Sess();
		}
		throw new WicketRuntimeException("Unable to handle algorithm:" + header.getAlgorithm());
	}

	/**
	 * Called when the MD5-sess algorithm is used and the hash is calculated for the first
	 * time. Implementations should use this notification to store this hash somewhere,
	 * like the session. Implementations are also responsible for retrieving this value
	 * uppon creation of this page.
	 * 
	 * @see #getA1ChecksumForMD5Sess()
	 * @see #setA1ChecksumForMD5Sess(String)
	 */
	protected abstract void onMD5SessHashCalculated();

	/**
	 * Calculates the A2 value from the specification. This implementation only knows how
	 * to calculate this value when there is no qop present or when the qop is 'auth'.
	 * 
	 * @param header
	 * @param request
	 * @return the calculated value
	 * @throws WicketRuntimeException
	 *             if the qop value is not understood
	 * @see <a href="http://tools.ietf.org/html/rfc2617#section-3.2.2.3">section 3.2.2.3
	 *      (A2)</a>
	 */
	protected String getA2Value(DigestAuthorizationRequestHeader header, WebRequest request)
	{
		if (Strings.isEmpty(header.getQop()) || "auth".equals(header.getQop()))
			return request.getHttpServletRequest().getMethod() + ":" + header.getUri();
		throw new WicketRuntimeException("Unable to handle qop:" + header.getQop());
	}

	/**
	 * In order to prove the validity of the request the server need to have access to the
	 * clear text value of the password. Note that you only need to provide the clear text
	 * password if you do not have access to the H(A1) value. If you do you should return
	 * that in
	 * {@link #getA1Checksum(org.apache.wicket.security.login.http.HttpDigestLoginPage.DigestAuthorizationRequestHeader, WebRequest)}
	 * instead.
	 * 
	 * @param username
	 *            the username from the request
	 * @return the password as typed by the user
	 * @see <a href="http://tools.ietf.org/html/rfc2617#section-3.2.2.2">section 3.2.2.2
	 *      (A1)</a>
	 */
	protected abstract String getClearTextPassword(String username);

	/**
	 * Performs a digest over a secret and some data as required by the algorithm. The
	 * default only supports MD5 and MD5-sess.
	 * 
	 * @param algorithm
	 * @param secret
	 * @param data
	 * @return the digest or null if the algorithm is not supported
	 * @see #checksum(String, String)
	 * @see <a href="http://tools.ietf.org/html/rfc2617#section-3.2.1">section 3.2.1
	 *      (algorithm)</a>
	 */
	protected String digest(String algorithm, String secret, String data)
	{

		if ("MD5".equals(algorithm) || "MD5-sess".equals(algorithm))
		{
			return checksum(algorithm, secret + ":" + data);
		}
		return null;
	}

	/**
	 * Performs a checksum operation over the data as required by the algorithm. The
	 * default only supports MD5 and MD5-sess.
	 * 
	 * @param algorithm
	 * @param data
	 * @return a checksum or null if the algorithm is not supported
	 * @throws WicketRuntimeException
	 *             if the algorithm could not be located
	 * @see <a href="http://tools.ietf.org/html/rfc2617#section-3.2.1">section 3.2.1
	 *      (algorithm)</a>
	 */
	protected String checksum(String algorithm, String data)
	{
		if ("MD5".equals(algorithm) || "MD5-sess".equals(algorithm))
		{
			MessageDigest digest = null;
			try
			{
				digest = MessageDigest.getInstance(algorithm);
			}
			catch (NoSuchAlgorithmException e)
			{
				throw new WicketRuntimeException("Client requested " + algorithm
					+ ", but the algorithm could not be located");
			}
			digest.update(data.getBytes());
			return new String(digest.digest());
		}
		return null;
	}

	/**
	 * @see org.apache.wicket.security.login.http.HttpAuthenticationLoginPage#requestAuthentication(org.apache.wicket.protocol.http.WebRequest,
	 *      org.apache.wicket.protocol.http.WebResponse)
	 */
	@Override
	protected void requestAuthentication(WebRequest request, WebResponse response)
	{
		if (allowBasicAuthenication)
			super.requestAuthentication(request, response);
		else
			response.getHttpServletResponse().setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		addDigestHeaders(request, response);

	}

	/**
	 * Adds a "WWW-Authenticate" header for digest authentication to the response.
	 * 
	 * @param request
	 * @param response
	 */
	protected void addDigestHeaders(WebRequest request, WebResponse response)
	{
		AppendingStringBuffer buffer = new AppendingStringBuffer(150);
		buffer.append("Digest realm=\"").append(getRealm(request, response)).append("\"");
		String domain = getDomain(request, response);
		if (domain != null)
			buffer.append(", domain=\"").append(domain).append("\"");
		buffer.append(", nonce=\"").append(getNonce(request, response)).append("\"");
		buffer.append(", opaque=\"").append(getNonce(request, response)).append("\"");
		DigestAuthorizationRequestHeader header = parseHeader(request);
		if (header != null)
			buffer.append(", stale=\"").append(isNonceStale(request, header.getNonce())).append(
				"\"");
		String algorithm = getAlgorithm(request, response);
		if (algorithm != null)
			buffer.append(", algorithm=\"").append(algorithm).append("\"");
		String qop = getQop(request, response);
		if (qop != null)
			buffer.append(", qop=\"").append(qop).append("\"");
		response.getHttpServletResponse().addHeader("WWW-Authenticate", buffer.toString());
	}

	/**
	 * The optional qop-options as specified in section 3.2.1 of RFC 2617. According to
	 * the spec 'auth' or 'auth-int' can be used or neither. Multiple options must be
	 * separated by a space. Default is to return only 'auth'.
	 * 
	 * @param request
	 * @param response
	 * @return a string identifying the supported qop-options
	 */
	protected String getQop(WebRequest request, WebResponse response)
	{
		return "auth";
	}

	/**
	 * The optional algorithm as specified in section 3.2.1 of RFC 2617. Default is to
	 * return MD5.
	 * 
	 * @param request
	 * @param response
	 * @return a string identifying the request algorithm
	 */
	protected String getAlgorithm(WebRequest request, WebResponse response)
	{
		return "MD5";
	}

	/**
	 * The optional (list of) domain(s) as specified in section 3.2.1 of RFC 2617. Default
	 * is to return null.
	 * 
	 * @param request
	 * @param response
	 * @return an unquoted list of space separated URI's, or null if all URI's on this
	 *         server apply.
	 */
	protected String getDomain(WebRequest request, WebResponse response)
	{
		return null;
	}

	/**
	 * Validates the contents of nonce send with the request. as specified in section
	 * 3.2.1 of RFC 2617. By default it does not enforce a time limit on nonces but does
	 * check for a valid timestamp, ETag and private key.
	 * 
	 * @param request
	 * 
	 * @param nonce
	 * @return true if the nonce is stale, false otherwise
	 * @see #getNonce(WebRequest, WebResponse)
	 */
	protected boolean isNonceStale(WebRequest request, String nonce)
	{
		String[] parts = new String(Base64.decodeBase64(nonce.getBytes())).split(":");
		if (parts.length != 3)
			return true;
		long nonceTime = 0;
		try
		{
			nonceTime = Long.parseLong(parts[0]);
		}
		catch (NumberFormatException e)
		{
			return true;
		}
		if (nonceTime < 0 || nonceTime > System.currentTimeMillis())
			return true;
		if (!Objects.equal(parts[1], request.getHttpServletRequest().getHeader("ETag")))
			return true;
		return !Objects.equal(getPrivateKey(), parts[2]);
	}

	/**
	 * Validates the value of the nonce count.
	 * 
	 * @param nonce
	 *            the nonce used by the client
	 * @param clientNonceCount
	 *            the number of times the client used this nonce, counted in hex
	 * @return true if the client nonce count matches the server nonce count, false
	 *         otherwise
	 */
	protected boolean validateNonceCount(String nonce, String clientNonceCount)
	{
		if (clientNonceCount == null || clientNonceCount.length() == 0)
			return false;
		// skip leading zero's
		int index = 0;
		while (clientNonceCount.charAt(index) == '0')
			index++;
		return Integer.toHexString(this.nonceCount).equals(clientNonceCount.substring(index));
	}

	/**
	 * The nonce as specified in section 3.2.1 of RFC 2617.
	 * 
	 * @param request
	 * @param response
	 * @return a base64 encoded string
	 * @see #isNonceStale(WebRequest, String)
	 */
	protected String getNonce(WebRequest request, WebResponse response)
	{
		long time = System.currentTimeMillis();
		return new String(Base64.encodeBase64(new AppendingStringBuffer(50).append(time)
			.append(":").append(request.getHttpServletRequest().getHeader("ETag")).append(":")
			.append(getPrivateKey()).toString().getBytes()));
	}

	/**
	 * A private server key used by the default implementation of
	 * {@link #getNonce(WebRequest, WebResponse)}
	 * 
	 * @return a private server key.
	 */
	protected String getPrivateKey()
	{
		return "Wasp, to protect and serve 'The Queen'.";
	}

	/**
	 * The opaque as specified in section 3.2.1 of RFC 2617.
	 * 
	 * @param request
	 * @param response
	 * @return a base64 encoded string
	 */
	protected String getOpaque(WebRequest request, WebResponse response)
	{
		return new String(Base64.encodeBase64("Wicket, tastes like honey.".getBytes()));
	}

	/**
	 * Tells if besides digest also basic authentication is supported. Default is true
	 * 
	 * @return true if basic authentication is also supported, false otherwise
	 */
	public final boolean isAllowBasicAuthenication()
	{
		return allowBasicAuthenication;
	}

	/**
	 * Sets the flag to allow or disallow basic authentication.
	 * 
	 * @param allowBasicAuthenication
	 *            allowBasicAuthenication
	 */
	public final void setAllowBasicAuthenication(boolean allowBasicAuthenication)
	{
		this.allowBasicAuthenication = allowBasicAuthenication;
	}

	/**
	 * Gets a1ValueForMD5Sess.
	 * 
	 * @return a1ValueForMD5Sess
	 */
	protected final String getA1ChecksumForMD5Sess()
	{
		return a1ChecksumForMD5Sess;
	}

	/**
	 * Sets a1ValueForMD5Sess.
	 * 
	 * @param valueForMD5Sess
	 *            a1ValueForMD5Sess
	 */
	protected final void setA1ChecksumForMD5Sess(String valueForMD5Sess)
	{
		a1ChecksumForMD5Sess = valueForMD5Sess;
	}

	/**
	 * Parses the authorization header for a digest scheme.
	 * 
	 * @param request
	 * @return the header or null if the header was not available or for a different
	 *         scheme
	 */
	protected final DigestAuthorizationRequestHeader parseHeader(WebRequest request)
	{
		String header = request.getHttpServletRequest().getHeader("Authorization");
		if (header == null)
			return null;
		if (!header.startsWith("Digest "))
			return null;
		header = header.substring(7);
		Matcher m = HEADER_FIELDS.matcher(header);
		if (!m.matches())
			return null;
		DigestAuthorizationRequestHeader digestHeader = new DigestAuthorizationRequestHeader();
		m.reset();
		while (m.find())
		{
			String key = m.group(2);
			String value = m.group(3);
			if (Strings.isEmpty(value))
				value = m.group(4);
			if (!digestHeader.addKeyValuePair(key, value))
				log.warn("Unknown header: " + key + ", skipping header.");
		}
		return digestHeader;
	}

	/**
	 * Simple pojo to hold all the parsed fields from the request header "Authorization".
	 * 
	 * @author marrink
	 */
	protected static final class DigestAuthorizationRequestHeader
	{
		private static final PropertyResolverConverter converter =
			new NoOpPropertyResolverConverter();

		private String username;

		private String realm;

		private String nonce;

		private String uri;

		private String qop;

		private String nc;

		private String cnonce;

		private String response;

		private String opaque;

		private String algorithm;

		/**
		 * Constructor to be used when key value pairs are going to be added later.
		 * 
		 * @see #addKeyValuePair(String, String)
		 */
		protected DigestAuthorizationRequestHeader()
		{

		}

		/**
		 * Dynamically resolves a header to the correct field and sets it value.
		 * 
		 * @param key
		 * @param value
		 * @return true, if the value was set, false if the value could not be set
		 */
		public boolean addKeyValuePair(String key, String value)
		{
			if (Strings.isEmpty(key))
				return false;
			try
			{
				PropertyResolver.setValue(key, this, value, converter);
			}
			catch (WicketRuntimeException e)
			{
				log.debug("Failed to set header: " + key, e);
				return false;
			}
			return true;
		}

		/**
		 * Gets realm.
		 * 
		 * @return realm
		 */
		public final String getRealm()
		{
			return realm;
		}

		/**
		 * Gets nonce.
		 * 
		 * @return nonce
		 */
		public final String getNonce()
		{
			return nonce;
		}

		/**
		 * Gets opaque.
		 * 
		 * @return opaque
		 */
		public final String getOpaque()
		{
			return opaque;
		}

		/**
		 * Gets username.
		 * 
		 * @return username
		 */
		public String getUsername()
		{
			return username;
		}

		/**
		 * Sets username.
		 * 
		 * @param username
		 *            username
		 */
		public void setUsername(String username)
		{
			this.username = username;
		}

		/**
		 * Gets uri.
		 * 
		 * @return uri
		 */
		public String getUri()
		{
			return uri;
		}

		/**
		 * Sets uri.
		 * 
		 * @param uri
		 *            uri
		 */
		public void setUri(String uri)
		{
			this.uri = uri;
		}

		/**
		 * Gets qop.
		 * 
		 * @return qop
		 */
		public String getQop()
		{
			return qop;
		}

		/**
		 * Sets qop.
		 * 
		 * @param qop
		 *            qop
		 */
		public void setQop(String qop)
		{
			this.qop = qop;
		}

		/**
		 * Gets nc.
		 * 
		 * @return nc
		 */
		public String getNc()
		{
			return nc;
		}

		/**
		 * Sets nc.
		 * 
		 * @param nc
		 *            nc
		 */
		public void setNc(String nc)
		{
			this.nc = nc;
		}

		/**
		 * Gets cnonce.
		 * 
		 * @return cnonce
		 */
		public String getCnonce()
		{
			return cnonce;
		}

		/**
		 * Sets cnonce.
		 * 
		 * @param cnonce
		 *            cnonce
		 */
		public void setCnonce(String cnonce)
		{
			this.cnonce = cnonce;
		}

		/**
		 * Gets response. aka request-digest.
		 * 
		 * @return response
		 */
		public String getResponse()
		{
			return response;
		}

		/**
		 * Sets response.
		 * 
		 * @param response
		 *            response
		 */
		public void setResponse(String response)
		{
			this.response = response;
		}

		/**
		 * Sets realm.
		 * 
		 * @param realm
		 *            realm
		 */
		public void setRealm(String realm)
		{
			this.realm = realm;
		}

		/**
		 * Sets nonce.
		 * 
		 * @param nonce
		 *            nonce
		 */
		public void setNonce(String nonce)
		{
			this.nonce = nonce;
		}

		/**
		 * Sets opaque.
		 * 
		 * @param opaque
		 *            opaque
		 */
		public void setOpaque(String opaque)
		{
			this.opaque = opaque;
		}

		/**
		 * Gets algorithm.
		 * 
		 * @return algorithm
		 */
		public String getAlgorithm()
		{
			return algorithm;
		}

		/**
		 * Sets algorithm.
		 * 
		 * @param algorithm
		 *            algorithm
		 */
		public void setAlgorithm(String algorithm)
		{
			this.algorithm = algorithm;
		}

		/**
		 * 
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString()
		{
			AppendingStringBuffer buffer = new AppendingStringBuffer(150);
			buffer.append("WWW-Authenticate: Digest username=\"").append(getUsername()).append(
				"\", realm=\"").append(getRealm()).append("\", nonce=\"").append(getNonce())
				.append("\", uri=\"").append(getUri()).append("\", qop=").append(getQop()).append(
					", cnonce=\"").append(getCnonce()).append("\", nc=").append(getNc()).append(
					", response=\"").append(getResponse()).append("\"");
			return buffer.toString();
		}
	}

	private static class NoOpPropertyResolverConverter extends PropertyResolverConverter
	{
		private static final long serialVersionUID = 1L;

		/**
		 * Construct.
		 */
		public NoOpPropertyResolverConverter()
		{
			super(null, null);
		}

		/**
		 * @see org.apache.wicket.util.lang.PropertyResolverConverter#convert(java.lang.Object,
		 *      java.lang.Class)
		 */
		@Override
		public Object convert(Object object, Class< ? > clz)
		{
			return object; // assume correct type.
		}

	}
}
