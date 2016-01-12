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
package org.apache.wicket.portlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayReader;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.portlet.MimeResponse;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.ResourceResponse;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;

import org.apache.wicket.settings.RequestCycleSettings;

/**
 * Temporarily holds the current state of a Wicket response when invoked from
 * WicketPortlet: buffer, headers, state and the redirect location to be
 * processed afterwards within WicketPortlet
 * 
 * @author Ate Douma
 * @author Peter Pastrnak
 */
public class ResponseState {
	private static final String AJAX_LOCATION_HEADER_NAME = "Ajax-Location";
	private static final int INITIAL_BUFFER_SIZE = 8 * 1024;
	private static final int MEMORY_BUFFER_SIZE = 64 * 1024;
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final AtomicLong index = new AtomicLong();

	private final boolean isActionResponse;
	private final boolean isEventResponse;
	private final boolean isRenderResponse;
	private final boolean isResourceResponse;
	private final boolean isMimeResponse;
	private final boolean isStateAwareResponse;
	private final Locale defaultLocale;
	private final PortletResponse response;
	private boolean flushed;
	private File responseBufferFolder;

	private ByteOutputBuffer byteOutputBuffer;
	private CharOutputBuffer charOutputBuffer;
	private ServletOutputStream outputStream;
	private PrintWriter printWriter;
	private HashMap<String, ArrayList<String>> headers;
	private ArrayList<Cookie> cookies;
	private boolean committed;
	private boolean closed;
	private boolean hasStatus;
	private boolean hasError;
	private Locale locale;
	private boolean setContentTypeAfterEncoding;
	private String characterEncoding;
	private int contentLength = -1;
	private String contentType;
	private int statusCode;

	/**
	 * FIXME javadoc
	 * 
	 * Stores the effective wicket url which is used by {@link WicketPortlet} in
	 * the view phase to request a render from wicket core.
	 * 
	 * @see IRequestCycleSettings#REDIRECT_TO_RENDER
	 * @see WicketFilterPortletHelper#initFilter
	 */
	private String redirectLocation;

	public ResponseState(PortletRequest request, PortletResponse response, File responseBufferFolder) {
		this.responseBufferFolder = responseBufferFolder;
		String lifecyclePhase = (String) request.getAttribute(PortletRequest.LIFECYCLE_PHASE);
		isActionResponse = PortletRequest.ACTION_PHASE.equals(lifecyclePhase);
		isEventResponse = PortletRequest.EVENT_PHASE.equals(lifecyclePhase);
		isRenderResponse = PortletRequest.RENDER_PHASE.equals(lifecyclePhase);
		isResourceResponse = PortletRequest.RESOURCE_PHASE.equals(lifecyclePhase);
		isStateAwareResponse = isActionResponse || isEventResponse;
		isMimeResponse = isRenderResponse || isResourceResponse;
		this.response = response;
		defaultLocale = isMimeResponse ? ((MimeResponse) response).getLocale() : null;
	}

	private ArrayList<String> getHeaderList(String name, boolean create) {
		if (headers == null) {
			headers = new HashMap<String, ArrayList<String>>();
		}
		ArrayList<String> headerList = headers.get(name);
		if (headerList == null && create) {
			headerList = new ArrayList<String>();
			headers.put(name, headerList);
		}
		return headerList;
	}

	private void failIfCommitted() {
		if (committed) {
			throw new IllegalStateException("Response has been already committed.");
		}
	}

	public boolean isActionResponse() {
		return isActionResponse;
	}

	public boolean isEventResponse() {
		return isEventResponse;
	}

	public boolean isRenderResponse() {
		return isRenderResponse;
	}

	public boolean isResourceResponse() {
		return isResourceResponse;
	}

	public boolean isMimeResponse() {
		return isMimeResponse;
	}

	public boolean isStateAwareResponse() {
		return isStateAwareResponse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponseWrapper#addCookie(javax.servlet
	 * .http.Cookie)
	 */
	public void addCookie(Cookie cookie) {
		failIfCommitted();

		if (cookies == null) {
			cookies = new ArrayList<Cookie>();
		}
		cookies.add(cookie);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponseWrapper#addDateHeader(java.lang
	 * .String, long)
	 */
	public void addDateHeader(String name, long date) {
		addHeader(name, Long.toString(date));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponseWrapper#addHeader(java.lang.String,
	 * java.lang.String)
	 */
	public void addHeader(String name, String value) {
		if (isMimeResponse) {
			failIfCommitted();
			getHeaderList(name, true).add(value);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponseWrapper#addIntHeader(java.lang.
	 * String, int)
	 */
	public void addIntHeader(String name, int value) {
		addHeader(name, Integer.toString(value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponseWrapper#containsHeader(java.lang
	 * .String)
	 */
	public boolean containsHeader(String name) {
		// Note: Portlet Spec 2.0 demands this to always return false...
		return isMimeResponse && getHeaderList(name, false) != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponseWrapper#sendError(int,
	 * java.lang.String)
	 */
	public void sendError(int errorCode, String errorMessage) throws IOException {
		failIfCommitted();
		committed = true;
		closed = true;
		hasError = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponseWrapper#sendError(int)
	 */
	public void sendError(int errorCode) throws IOException {
		sendError(errorCode, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponseWrapper#sendRedirect(java.lang.
	 * String)
	 */
	public void sendRedirect(String redirectLocation) throws IOException {
		if (isActionResponse || isMimeResponse) {
			failIfCommitted();
			this.closed = true;
			this.committed = true;
			this.redirectLocation = redirectLocation;
		}
	}

	public String getRedirectLocation() {
		return redirectLocation;
	}

	public String getAjaxRedirectLocation() {
		ArrayList<String> values = getHeaderList(AJAX_LOCATION_HEADER_NAME, false);
		return ((values != null) && (!values.isEmpty())) ? values.get(values.size() - 1) : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponseWrapper#setDateHeader(java.lang
	 * .String, long)
	 */
	public void setDateHeader(String name, long date) {
		setHeader(name, Long.toString(date));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponseWrapper#setHeader(java.lang.String,
	 * java.lang.String)
	 */
	public void setHeader(String name, String value) {
		if (isMimeResponse && !committed) {
			ArrayList<String> headerList = getHeaderList(name, true);
			headerList.clear();
			headerList.add(value);

			if (AJAX_LOCATION_HEADER_NAME.equalsIgnoreCase(name)) {
				redirectLocation = value;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.http.HttpServletResponseWrapper#setIntHeader(java.lang.
	 * String, int)
	 */
	public void setIntHeader(String name, int value) {
		setHeader(name, Integer.toString(value));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponseWrapper#setStatus(int,
	 * java.lang.String)
	 */
	public void setStatus(int statusCode, String message) {
		throw new UnsupportedOperationException("This method is deprecated and no longer supported");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpServletResponseWrapper#setStatus(int)
	 */
	public void setStatus(int statusCode) {
		if (!committed) {
			this.statusCode = statusCode;
			hasStatus = true;
			resetBuffer();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#flushBuffer()
	 */
	public void flushBuffer() throws IOException {
		if (isMimeResponse && !closed) {
			committed = true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#getBufferSize()
	 */
	public int getBufferSize() {
		return isMimeResponse ? Integer.MAX_VALUE : 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#getCharacterEncoding()
	 */
	public String getCharacterEncoding() {
		return isMimeResponse ? characterEncoding != null ? characterEncoding : "ISO-8859-1" : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#getContentType()
	 */
	public String getContentType() {
		return isMimeResponse ? contentType : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#getLocale()
	 */
	public Locale getLocale() {
		return isMimeResponse ? locale != null ? locale : defaultLocale : null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#getOutputStream()
	 */
	public ServletOutputStream getOutputStream() throws IOException {
		if (isStateAwareResponse) {
			// Portlet Spec 2.0 requires Portlet Container to supply a "no-op"
			// OutputStream object
			// so delegate back to current PortletServletResponseWrapper to
			// return that one
			return null;
		}
		if (outputStream == null) {
			if (printWriter != null) {
				throw new IllegalStateException("getWriter() has already been called on this response");
			}
			byteOutputBuffer = new ByteOutputBuffer();
			outputStream = new ServletOutputStream() {
				@Override
				public void write(int b) throws IOException {
					if (!closed) {
						byteOutputBuffer.write(b);
						if ((contentLength) > -1 && (byteOutputBuffer.size() >= contentLength)) {
							committed = true;
							closed = true;
						}
					}
				}
			};
		}
		return outputStream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#getWriter()
	 */
	public PrintWriter getWriter() throws IOException {
		if (isStateAwareResponse) {
			// Portlet Spec 2.0 requires Portlet Container to supply a "no-op"
			// PrintWriter object
			// so delegate back to current PortletServletResponseWrapper to
			// return that one
			return null;
		}
		if (printWriter == null) {
			if (outputStream != null) {
				throw new IllegalStateException("getOutputStream() has already been called on this response");
			}
			charOutputBuffer = new CharOutputBuffer();
			printWriter = new PrintWriter(charOutputBuffer);
		}
		return printWriter;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#isCommitted()
	 */
	public boolean isCommitted() {
		return isMimeResponse && committed;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#reset()
	 */
	public void reset() {
		resetBuffer(); // fails if committed
		headers = null;
		cookies = null;
		hasStatus = false;
		contentLength = -1;
		if (printWriter == null) {
			contentType = null;
			characterEncoding = null;
			locale = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#resetBuffer()
	 */
	public void resetBuffer() {
		failIfCommitted();
		if (outputStream != null) {
			try {
				outputStream.flush();
			}
			catch (Exception e) {
			}
			byteOutputBuffer.reset();
		}
		else if (printWriter != null) {
			printWriter.flush();
			charOutputBuffer.reset();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#setBufferSize(int)
	 */
	public void setBufferSize(int size) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletResponseWrapper#setCharacterEncoding(java.lang.String
	 * )
	 */
	public void setCharacterEncoding(String charset) {
		if (isResourceResponse && charset != null && printWriter == null) {
			failIfCommitted();
			characterEncoding = charset;
			setContentTypeAfterEncoding = false;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#setContentLength(int)
	 */
	public void setContentLength(int len) {
		if (isResourceResponse && printWriter == null && len > 0) {
			failIfCommitted();

			contentLength = len;
			if (outputStream != null) {
				try {
					outputStream.flush();
				}
				catch (Exception e) {
				}
			}

			if ((!closed) && (byteOutputBuffer != null) && (byteOutputBuffer.size() >= len)) {
				committed = true;
				closed = true;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * javax.servlet.ServletResponseWrapper#setContentType(java.lang.String)
	 */
	public void setContentType(String type) {
		if (isMimeResponse) {
			failIfCommitted();

			contentType = type;
			setContentTypeAfterEncoding = false;
			if (printWriter == null) {
				// TODO: parse possible encoding for better return value from
				// getCharacterEncoding()
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletResponseWrapper#setLocale(java.util.Locale)
	 */
	public void setLocale(Locale locale) {
		if (isResourceResponse) {
			failIfCommitted();
			this.locale = locale;
		}
	}

	public void clear() {
		if (printWriter != null) {
			printWriter.close();
			charOutputBuffer.reset();
			printWriter = null;
		}

		if (outputStream != null) {
			try {
				outputStream.close();
			}
			catch (IOException e) {
			}
			outputStream = null;
			byteOutputBuffer.reset();
		}

		headers = null;
		cookies = null;
		committed = false;
		hasStatus = false;
		hasError = false;
		locale = null;
		setContentTypeAfterEncoding = false;
		closed = false;
		characterEncoding = null;
		contentLength = -1;
		contentType = null;
		statusCode = 0;
		redirectLocation = null;
	}

	public void flushAndClose() throws IOException {
		if (flushed) {
			throw new IllegalStateException("Response has been already flushed and closed.");
		}
		flushed = true;

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				response.addProperty(cookie);
			}
			cookies = null;
		}

		if (isMimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) response;
			ResourceResponse resourceResponse = isResourceResponse ? (ResourceResponse) response : null;

			if (locale != null) {
				try {
					resourceResponse.setLocale(locale);
				}
				catch (UnsupportedOperationException usoe) {
					// TODO: temporary "fix" for JBoss Portal which doesn't
					// yet
					// support this
					// (although required by the Portlet API 2.0!)
				}
			}

			if (contentType != null) {
				if (characterEncoding != null) {
					if (setContentTypeAfterEncoding) {
						resourceResponse.setCharacterEncoding(characterEncoding);
						resourceResponse.setContentType(contentType);
					}
					else {
						resourceResponse.setContentType(contentType);
						resourceResponse.setCharacterEncoding(characterEncoding);
					}
				}
				else {
					mimeResponse.setContentType(contentType);
				}
			}
			else if (characterEncoding != null) {
				resourceResponse.setCharacterEncoding(characterEncoding);
			}

			if (headers != null) {
				for (Map.Entry<String, ArrayList<String>> entry : headers.entrySet()) {
					for (String value : entry.getValue()) {
						mimeResponse.addProperty(entry.getKey(), value);
					}
				}
				headers = null;
			}

			if (isResourceResponse && hasStatus) {
				resourceResponse.setProperty(ResourceResponse.HTTP_STATUS_CODE, Integer.toString(statusCode));
			}

			if (isResourceResponse && contentLength > -1) {
				try {
					resourceResponse.setContentLength(contentLength);
				}
				catch (UnsupportedOperationException usoe) {
					// TODO: temporary "fix" for JBoss Portal which doesn't
					// yet
					// support this
					// (although required by the Portlet API 2.0!)
				}
			}
		}

		if (!hasError && !closed && redirectLocation == null && isMimeResponse) {
			MimeResponse mimeResponse = (MimeResponse) response;

			if (outputStream != null) {
				outputStream.flush();
				byteOutputBuffer.writeToAndClose(mimeResponse.getPortletOutputStream());
			}
			else if (printWriter != null) {
				printWriter.flush();
				charOutputBuffer.writeToAndClose(mimeResponse.getWriter());
			}
		}

		if (outputStream != null) {
			outputStream.close();
			outputStream = null;
		}
		if (printWriter != null) {
			printWriter.close();
			printWriter = null;
		}
	}

	private File getResponseBufferFolder() {
		if (responseBufferFolder == null) {
			ServletContext context = ThreadPortletContext.getServletContext();
			if (context != null) {
				responseBufferFolder = (File) context.getAttribute("javax.servlet.context.tempdir");
				if (responseBufferFolder != null) {
					responseBufferFolder = new File(responseBufferFolder, "wicket-response-buffers");
				}
			}

			if (responseBufferFolder == null) {
				try {
					responseBufferFolder = File.createTempFile("wicket-response-buffers", null).getParentFile();
				}
				catch (IOException e) {
					throw new IllegalStateException("Cannot create response buffer folder.", e);
				}
			}
			responseBufferFolder.mkdirs();
		}
		return responseBufferFolder;
	}

	private File getResponseBufferFile() {
		return new File(getResponseBufferFolder(), "response-buffer" + index.getAndIncrement());
	}

	private class CharOutputBuffer extends CharArrayWriter {
		private File file;

		private Writer fileWriter;

		public CharOutputBuffer() {
			super(INITIAL_BUFFER_SIZE);
		}

		private boolean useFile(int pos) {
			return (fileWriter != null) || (pos > MEMORY_BUFFER_SIZE);
		}

		private Writer getFileWriter() throws IOException {
			if (fileWriter == null) {
				file = getResponseBufferFile();
				fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), DEFAULT_CHARSET));
				fileWriter.write(buf, 0, count);
			}
			return fileWriter;
		}

		@Override
		public void write(int c) {
			if (useFile(count + 1)) {
				try {
					getFileWriter().write(c);
					count++;
				}
				catch (IOException e) {
					throw new IllegalStateException("Cannot write cache file.", e);
				}
			}
			else {
				super.write(c);
			}
		}

		@Override
		public void write(char[] c, int off, int len) {
			if (useFile(count + len)) {
				try {
					getFileWriter().write(c, off, len);
					count += len;
				}
				catch (IOException e) {
					throw new IllegalStateException("Cannot write cache file.", e);
				}
			}
			else {
				super.write(c, off, len);
			}
		}

		@Override
		public void write(String str, int off, int len) {
			if (useFile(count + 1)) {
				try {
					getFileWriter().write(str, off, len);
					count += len;
				}
				catch (IOException e) {
					throw new IllegalStateException("Cannot write cache file.", e);
				}
			}
			else {
				super.write(str, off, len);
			}
		}

		public void writeToAndClose(Writer writer) throws IOException {
			try {
				Reader reader;

				if (fileWriter != null) {
					try {
						fileWriter.close();
					}
					catch (Exception e) {
					}

					reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), DEFAULT_CHARSET));
				}
				else {
					reader = new CharArrayReader(buf, 0, count);
				}

				try {
					int count;
					while ((count = reader.read(buf)) > 0) {
						writer.write(buf, 0, count);
					}
				}
				finally {
					if (reader != null) {
						try {
							reader.close();
						}
						catch (Exception e) {
						}
					}
				}
			}
			finally {
				buf = null;
				reset();
			}
		}

		@Override
		public void reset() {
			super.reset();

			if (fileWriter != null) {
				try {
					fileWriter.close();
					fileWriter = null;
				}
				catch (Exception e) {
				}
			}

			if (file != null) {
				try {
					file.delete();
					file = null;
				}
				catch (Exception e) {
				}
			}
		}
		
		@Override
		protected void finalize() throws Throwable {
			reset();
		}
	}

	private class ByteOutputBuffer extends ByteArrayOutputStream {
		private File file;

		private OutputStream fileOutputStream;

		public ByteOutputBuffer() {
			super(INITIAL_BUFFER_SIZE);
		}

		private boolean useFile(int pos) {
			return (fileOutputStream != null) || (pos > MEMORY_BUFFER_SIZE);
		}

		private OutputStream getFileWriter() throws IOException {
			if (fileOutputStream == null) {
				file = getResponseBufferFile();
				fileOutputStream = new BufferedOutputStream(new FileOutputStream(file));
				fileOutputStream.write(buf, 0, count);
			}
			return fileOutputStream;
		}

		@Override
		public void write(int b) {
			if (useFile(count + 1)) {
				try {
					getFileWriter().write(b);
					count++;
				}
				catch (IOException e) {
					throw new IllegalStateException("Cannot write cache file.", e);
				}
			}
			else {
				super.write(b);
			}
		}

		@Override
		public void write(byte[] b, int off, int len) {
			if (useFile(count + len)) {
				try {
					getFileWriter().write(b, off, len);
					count += len;
				}
				catch (IOException e) {
					throw new IllegalStateException("Cannot write cache file.", e);
				}
			}
			else {
				super.write(b, off, len);
			}
		}

		public void writeToAndClose(OutputStream outputStream) throws IOException {
			try {
				InputStream inputStream;

				if (fileOutputStream != null) {
					try {
						fileOutputStream.close();
					}
					catch (Exception e) {
					}

					inputStream = new BufferedInputStream(new FileInputStream(file));
				}
				else {
					inputStream = new ByteArrayInputStream(buf, 0, count);
				}

				try {
					int count;
					while ((count = inputStream.read(buf)) > 0) {
						outputStream.write(buf, 0, count);
					}
				}
				finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						}
						catch (Exception e) {
						}
					}
				}
			}
			finally {
				buf = null;
				reset();
			}
		}

		@Override
		public void reset() {
			super.reset();

			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
					fileOutputStream = null;
				}
				catch (Exception e) {
				}
			}

			if (file != null) {
				try {
					file.delete();
					file = null;
				}
				catch (Exception e) {
				}
			}
		}
		
		@Override
		protected void finalize() throws Throwable {
			reset();
		}
	}
}
