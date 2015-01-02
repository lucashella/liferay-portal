/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.dynamicdatamapping.storage;

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.dynamicdatamapping.BaseDDMTestCase;
import com.liferay.portlet.dynamicdatamapping.model.UnlocalizedValue;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marcellus Tavares
 */
public class DDMFormValuesTest extends BaseDDMTestCase {

	@Before
	public void setUp() {
		_ddmFormValues = createDDMFormValues(null);

		_ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(_FIELD_NAME, null));
		_ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(_FIELD_NAME, null));
		_ddmFormValues.addDDMFormFieldValue(
			createDDMFormFieldValue(_FIELD_NAME, null));
	}

	@Test
	public void testDDMFormFieldValuesMap() {
		Map<String, List<DDMFormFieldValue>> ddmFormFieldValuesMap =
			_ddmFormValues.getDDMFormFieldValuesMap();

		List<DDMFormFieldValue> ddmFormFieldValues = ddmFormFieldValuesMap.get(
			_FIELD_NAME);

		Assert.assertEquals(3, ddmFormFieldValues.size());
	}

	@Test
	public void testEqualsWithDifferentAvailableLocales() {
		DDMFormValues ddmFormValues1 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormValues ddmFormValues2 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.BRAZIL, LocaleUtil.US),
			LocaleUtil.US);

		Assert.assertFalse(ddmFormValues1.equals(ddmFormValues2));
	}

	@Test
	public void testEqualsWithDifferentDDMFormFieldValues() {
		DDMFormValues ddmFormValues1 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmFormValues1.addDDMFormFieldValue(
			createDDMFormFieldValue(
				StringUtil.randomString(), StringUtil.randomString(),
				new UnlocalizedValue(StringUtil.randomString())));

		DDMFormValues ddmFormValues2 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmFormValues2.addDDMFormFieldValue(
			createDDMFormFieldValue(
				StringUtil.randomString(), StringUtil.randomString(),
				new UnlocalizedValue(StringUtil.randomString())));

		Assert.assertFalse(ddmFormValues1.equals(ddmFormValues2));
	}

	@Test
	public void testEqualsWithDifferentDefaultLocale() {
		DDMFormValues ddmFormValues1 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormValues ddmFormValues2 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.BRAZIL);

		Assert.assertFalse(ddmFormValues1.equals(ddmFormValues2));
	}

	@Test
	public void testEqualsWithDifferentOrderOfDDMFormFieldValues() {
		DDMFormValues ddmFormValues1 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		DDMFormFieldValue nestedDDMFormFieldValue1 = createDDMFormFieldValue(
			StringUtil.randomString(), StringUtil.randomString(),
			new UnlocalizedValue(StringUtil.randomString()));

		DDMFormFieldValue nestedDDMFormFieldValue2 = createDDMFormFieldValue(
			StringUtil.randomString(), StringUtil.randomString(),
			new UnlocalizedValue(StringUtil.randomString()));

		ddmFormValues1.addDDMFormFieldValue(nestedDDMFormFieldValue1);
		ddmFormValues1.addDDMFormFieldValue(nestedDDMFormFieldValue2);

		DDMFormValues ddmFormValues2 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmFormValues2.addDDMFormFieldValue(nestedDDMFormFieldValue2);
		ddmFormValues2.addDDMFormFieldValue(nestedDDMFormFieldValue1);

		Assert.assertFalse(ddmFormValues1.equals(ddmFormValues2));
	}

	@Test
	public void testEqualsWithSameAttributes() {
		DDMFormFieldValue ddmFormFieldValue = createDDMFormFieldValue(
			StringUtil.randomString(), StringUtil.randomString(),
			new UnlocalizedValue(StringUtil.randomString()));

		DDMFormValues ddmFormValues1 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmFormValues1.addDDMFormFieldValue(ddmFormFieldValue);

		DDMFormValues ddmFormValues2 = createDDMFormValues(
			null, createAvailableLocales(LocaleUtil.US), LocaleUtil.US);

		ddmFormValues2.addDDMFormFieldValue(ddmFormFieldValue);

		Assert.assertTrue(ddmFormValues1.equals(ddmFormValues2));
	}

	private static final String _FIELD_NAME = StringUtil.randomString();

	private DDMFormValues _ddmFormValues;

}