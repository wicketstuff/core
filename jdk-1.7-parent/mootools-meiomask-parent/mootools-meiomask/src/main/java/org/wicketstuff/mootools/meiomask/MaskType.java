/*
 *  Copyright 2010 inaiat.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.wicketstuff.mootools.meiomask;

import org.wicketstuff.mootools.meiomask.behavior.MeioMaskBehavior;

/**
 * 
 * @author inaiat
 */
public enum MaskType
{

	Custom("fixed"), FixedPhone("fixed.phone", "(##) ####-####"), FixedPhoneUs("fixed.phone-us",
		"(###) ###-####"), FixedCpf("fixed.cpf", "###.###.###-##"), FixedCnpj("fixed.cnpj",
		"##.###.###/####-##"), FixedDate("fixed.date", "##/##/####"), FixedDateUs("fixed.date-us",
		"##/##/####"), FixedCep("fixed.cep", "#####-###"), FixedTime("fixed.time", "##:##"), FixedCc(
		"fixed.cc", "#### #### #### ####"), ReverseInteger("reverse.integer"), ReverseDecimal(
		"reverse.decimal"), ReverseDecimalUs("reverse.decimal-us"), ReverseReais("reverse.reais"), ReverseDollar(
		"reverse.dollar"), RegexpIp("regexp.ip"), RegexpEmail("regexp.email");

	private String maskName;
	private String mask;

	private MaskType(String maskName)
	{
		this(maskName, null);
	}

	private MaskType(String maskName, String mask)
	{
		this.maskName = maskName;
		this.mask = mask;
	}

	public String getMaskName()
	{
		return maskName;
	}

	public String getMask()
	{
		return mask;
	}

	public MeioMaskBehavior ofBehavior()
	{
		return new MeioMaskBehavior(this);
	}
}
