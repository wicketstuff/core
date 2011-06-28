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
package org.wicketstuff.mootools.meiomaks.test;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.junit.Ignore;
import org.wicketstuff.mootools.meiomask.CustomMaskField;
import org.wicketstuff.mootools.meiomask.MaskType;
import org.wicketstuff.mootools.meiomask.MeioMaskField;

/**
 * 
 * @author inaiat
 */
@Ignore
public class TestPage extends WebPage
{

	private static final long serialVersionUID = 1L;
	private final TestModel testModel = new TestModel();

	public TestPage()
	{
		FeedbackPanel feedbackPanel = new FeedbackPanel("feedBack");
		add(feedbackPanel);

		Form<TestModel> form = new Form<TestModel>("form", new CompoundPropertyModel<TestModel>(
			testModel));

		add(form);
		form.add(new CustomMaskField<String>("fixed", "####-##-##"));
		form.add(new MeioMaskField<String>("fixedPhone", MaskType.FixedPhone));
		form.add(new MeioMaskField<String>("fixedPhoneUs", MaskType.FixedPhoneUs));
		form.add(new MeioMaskField<String>("fixedCpf", MaskType.FixedCpf));
		form.add(new MeioMaskField<String>("fixedCnpj", MaskType.FixedCnpj));
		form.add(new MeioMaskField<String>("fixedCep", MaskType.FixedCep));
		form.add(new MeioMaskField<String>("fixedTime", MaskType.FixedTime));
		form.add(new MeioMaskField<String>("fixedCc", MaskType.FixedCc));
		form.add(new MeioMaskField<Date>("fixedDate", MaskType.FixedDate));
		form.add(new MeioMaskField<Date>("fixedDateUs", MaskType.FixedDateUs));

		form.add(new MeioMaskField<Integer>("reverseInteger", MaskType.ReverseInteger));
		form.add(new MeioMaskField<BigDecimal>("reverseDecimal", MaskType.ReverseDecimal));
		form.add(new MeioMaskField<BigDecimal>("reverseDecimalUs", MaskType.ReverseDecimalUs));
		form.add(new MeioMaskField<BigDecimal>("reverseReais", MaskType.ReverseReais));
		form.add(new MeioMaskField<BigDecimal>("reverseDollar", MaskType.ReverseDollar));

		form.add(new MeioMaskField<String>("regexpIp", MaskType.RegexpIp));
		form.add(new MeioMaskField<String>("regexpEmail", MaskType.RegexpEmail));


	}

	static class TestModel implements Serializable
	{
		private static final long serialVersionUID = 1L;

		private String fixed;
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
		private BigDecimal reverseDecimal;
		private BigDecimal reverseDecimalUs;
		private BigDecimal reverseReais;
		private BigDecimal reverseDollar;
		private String regexpIp;
		private String regexpEmail;


		/**
		 * @return the fixed
		 */
		public String getFixed()
		{
			return fixed;
		}

		/**
		 * @param fixed
		 *            the fixed to set
		 */
		public void setFixed(String fixed)
		{
			this.fixed = fixed;
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
			setFixedCc(fixedCc);
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

		/**
		 * @return the reverseInteger
		 */
		public Integer getReverseInteger()
		{
			return reverseInteger;
		}

		/**
		 * @param reverseInteger
		 *            the reverseInteger to set
		 */
		public void setReverseInteger(Integer reverseInteger)
		{
			this.reverseInteger = reverseInteger;
		}

		/**
		 * @param fixedCc
		 *            the fixedCc to set
		 */
		public void setFixedCc(String fixedCc)
		{
			this.fixedCc = fixedCc;
		}

		/**
		 * @return the reverseDecimal
		 */
		public BigDecimal getReverseDecimal()
		{
			return reverseDecimal;
		}

		/**
		 * @param reverseDecimal
		 *            the reverseDecimal to set
		 */
		public void setReverseDecimal(BigDecimal reverseDecimal)
		{
			this.reverseDecimal = reverseDecimal;
		}

		/**
		 * @return the reverseDecimalUs
		 */
		public BigDecimal getReverseDecimalUs()
		{
			return reverseDecimalUs;
		}

		/**
		 * @param reverseDecimalUs
		 *            the reverseDecimalUs to set
		 */
		public void setReverseDecimalUs(BigDecimal reverseDecimalUs)
		{
			this.reverseDecimalUs = reverseDecimalUs;
		}

		/**
		 * @return the reverseReais
		 */
		public BigDecimal getReverseReais()
		{
			return reverseReais;
		}

		/**
		 * @param reverseReais
		 *            the reverseReais to set
		 */
		public void setReverseReais(BigDecimal reverseReais)
		{
			this.reverseReais = reverseReais;
		}

		/**
		 * @return the reverseDollar
		 */
		public BigDecimal getReverseDollar()
		{
			return reverseDollar;
		}

		/**
		 * @param reverseDollar
		 *            the reverseDollar to set
		 */
		public void setReverseDollar(BigDecimal reverseDollar)
		{
			this.reverseDollar = reverseDollar;
		}

		/**
		 * @return the regexpIp
		 */
		public String getRegexpIp()
		{
			return regexpIp;
		}

		/**
		 * @param regexpIp
		 *            the regexpIp to set
		 */
		public void setRegexpIp(String regexpIp)
		{
			this.regexpIp = regexpIp;
		}

		/**
		 * @return the regexpEmail
		 */
		public String getRegexpEmail()
		{
			return regexpEmail;
		}

		/**
		 * @param regexpEmail
		 *            the regexpEmail to set
		 */
		public void setRegexpEmail(String regexpEmail)
		{
			this.regexpEmail = regexpEmail;
		}
	}
}
