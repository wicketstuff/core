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
package com.googlecode.wicket.kendo.ui;

/**
 * Provides all Kendo UI message's culture identifiers
 * 
 * @author Sebastien Briquet - sebfz1
 */
public enum KendoMessage
{
	BG_BG("bg-BG"), // lf
	CS_CZ("cs-CZ"), // lf
	DA_DK("da-DK"), // lf
	DE_CH("de-CH"), // lf
	DE_DE("de-DE"), // lf
	EN_US("en-US"), // lf
	ES_ES("es-ES"), // lf
	FR_CH("fr-CH"), // lf
	FR_FR("fr-FR"), // lf
	HE_IL("he-IL"), // lf
	IT_CH("it-CH"), // lf
	IT_IT("it-IT"), // lf
	JA_JP("ja-JP"), // lf
	NB_NO("nb-NO"), // lf
	NL_NL("nl-NL"), // lf
	PL_PL("pl-PL"), // lf
	PT_BR("pt-BR"), // lf
	PT_PT("pt-PT"), // lf
	RO_RO("ro-RO"), // lf
	RU_RU("ru-RU"), // lf
	SK_SK("sk-SK"), // lf
	SV_SE("sv-SE"), // lf
	TR_TR("tr-TR"), // lf
	UK_UA("uk-UA"), // lf
	ZH_CN("zh-CN"), // lf
	ZH_HK("zh-HK"), // lf
	ZH_TW("zh-TW");

	private final String culture;

	/**
	 * Constructor
	 */
	private KendoMessage(String culture)
	{
		this.culture = culture;
	}

	@Override
	public String toString()
	{
		return this.culture;
	}
}
