package org.wicketstuff.browserid;

import java.io.Serializable;
import java.util.Date;

import org.apache.wicket.util.string.Strings;

/**
 * An object representing the response from https://browserid.org
 */
public class BrowserId implements Serializable
{

	private static final long serialVersionUID = 1L;

	public enum Status
	{
		OK, FAIL;

		public static Status parse(final String t)
		{
			if ("okay".equals(t))
			{
				return OK;
			}
			else if ("failure".equals(t))
			{
				return FAIL;
			}
			else
			{
				throw new IllegalArgumentException("Unrecognized status: " + t);
			}
		}
	};

	private Status status;

	private String email;

	private String audience;

	private Date validUntil;

	private String issuer;

	private String reason;

	public String getReason()
	{
		return reason;
	}

	public void setReason(String reason)
	{
		this.reason = reason;
	}

	public Status getStatus()
	{
		return status;
	}

	public void setStatus(Status status)
	{
		this.status = status;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public String getAudience()
	{
		return audience;
	}

	public void setAudience(String audience)
	{
		this.audience = audience;
	}

	public Date getValidUntil()
	{
		return validUntil;
	}

	public void setValidUntil(Date validUntil)
	{
		this.validUntil = validUntil;
	}

	public String getIssuer()
	{
		return issuer;
	}

	public void setIssuer(String issuer)
	{
		this.issuer = issuer;
	}

	/**
	 * Create {@link BrowserId} object from success or failure JSON response
	 * 
	 * @param json
	 *            the response returned by https://browserid.org
	 * @return a {@link BrowserId} instance with status OK if the authentication passed, or with
	 *         status FAIL if the authentication failed, or {@code null} if the passed JSON response
	 *         is invalid
	 */
	public static BrowserId of(String json)
	{
		BrowserId browserId = null;

		if (json != null && json.startsWith("{") && json.endsWith("}"))
		{
			json = json.substring(1);
			json = json.substring(0, json.length() - 1);

			String[] settings = Strings.split(json, ',');
			if (settings.length > 0)
			{
				browserId = new BrowserId();
				for (String setting : settings)
				{
					String[] pair = Strings.split(setting, ':');
					pair[0] = Strings.replaceAll(pair[0], "\"", "").toString();
					pair[1] = Strings.replaceAll(pair[1], "\"", "").toString();

					if ("status".equals(pair[0]))
					{
						browserId.status = Status.parse(pair[1]);
					}
					else if ("email".equals(pair[0]))
					{
						browserId.email = pair[1];
					}
					else if ("audience".equals(pair[0]))
					{
						browserId.audience = pair[1];
					}
					else if ("issuer".equals(pair[0]))
					{
						browserId.issuer = pair[1];
					}
					else if ("valid-until".equals(pair[0]))
					{
						Long millis = Long.valueOf(pair[1]);
						browserId.validUntil = new Date(millis);
					}
					else if ("reason".equals(pair[0]))
					{
						browserId.reason = pair[1];
					}
					else
					{
						throw new IllegalArgumentException("Unknown setting: " + pair[0]);
					}
				}
			}
		}

		return browserId;
	}
}
