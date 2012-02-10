/*
 * Copyright 2011 inaiat.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.wicketstuff.mootools.meiomask.examples;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @author inaiat
 */
public class TestModel implements Serializable
{

	private static final long serialVersionUID = 1L;
	private String custom;
	private Integer custom2;
	private String fixedPhone;
	private String fixedPhoneUs;
	private String fixedCpf;
	private String fixedCnpj;
	private Long fixedCep;
	private String fixedTime;
	private String fixedCc;
	private Date fixedDate;
	private Date fixedDateUs;

	private Integer reverseInteger;

	public Integer getReverseInteger()
	{
		return reverseInteger;
	}

	public void setReverseInteger(Integer reverseInteger)
	{
		this.reverseInteger = reverseInteger;
	}


	/**
	 * @return the custom
	 */
	public String getCustom()
	{
		return custom;
	}

	/**
	 * @param custom
	 *            the custom to set
	 */
	public void setCustom(String custom)
	{
		this.custom = custom;
	}

	/**
	 * @return the fixedPhone
	 */
	public String getFixedPhone()
	{
		return fixedPhone;
	}

	/**
	 * @param fixedPhone
	 *            the fixedPhone to set
	 */
	public void setFixedPhone(String fixedPhone)
	{
		this.fixedPhone = fixedPhone;
	}

	/**
	 * @return the fixedCpf
	 */
	public String getFixedCpf()
	{
		return fixedCpf;
	}

	/**
	 * @param fixedCpf
	 *            the fixedCpf to set
	 */
	public void setFixedCpf(String fixedCpf)
	{
		this.fixedCpf = fixedCpf;
	}

	/**
	 * @return the fixedPhoneUs
	 */
	public String getFixedPhoneUs()
	{
		return fixedPhoneUs;
	}

	/**
	 * @param fixedPhoneUs
	 *            the fixedPhoneUs to set
	 */
	public void setFixedPhoneUs(String fixedPhoneUs)
	{
		this.fixedPhoneUs = fixedPhoneUs;
	}

	/**
	 * @return the fixedCnpj
	 */
	public String getFixedCnpj()
	{
		return fixedCnpj;
	}

	/**
	 * @param fixedCnpj
	 *            the fixedCnpj to set
	 */
	public void setFixedCnpj(String fixedCnpj)
	{
		this.fixedCnpj = fixedCnpj;
	}

	/**
	 * @return the fixedCep
	 */
	public Long getFixedCep()
	{
		return fixedCep;
	}

	/**
	 * @param fixedCep
	 *            the fixedCep to set
	 */
	public void setFixedCep(Long fixedCep)
	{
		this.fixedCep = fixedCep;
	}

	/**
	 * @return the fixedTime
	 */
	public String getFixedTime()
	{
		return fixedTime;
	}

	/**
	 * @param fixedTime
	 *            the fixedTime to set
	 */
	public void setFixedTime(String fixedTime)
	{
		this.fixedTime = fixedTime;
	}

	/**
	 * @return the fixedCC
	 */
	public String getFixedCc()
	{
		return fixedCc;
	}

	/**
	 * @param fixedCC
	 *            the fixedCC to set
	 */
	public void setFixedCC(String fixedCc)
	{
		this.fixedCc = fixedCc;
	}

	/**
	 * @return the fixedDate
	 */
	public Date getFixedDate()
	{
		return fixedDate;
	}

	/**
	 * @param fixedDate
	 *            the fixedDate to set
	 */
	public void setFixedDate(Date fixedDate)
	{
		this.fixedDate = fixedDate;
	}

	/**
	 * @return the fixedDateUs
	 */
	public Date getFixedDateUs()
	{
		return fixedDateUs;
	}

	/**
	 * @param fixedDateUs
	 *            the fixedDateUs to set
	 */
	public void setFixedDateUs(Date fixedDateUs)
	{
		this.fixedDateUs = fixedDateUs;
	}

	public void setCustom2(Integer custom2) 
	{
		this.custom2 = custom2;
	}

	public Integer getCustom2() 
	{
		return custom2;
	}
}
