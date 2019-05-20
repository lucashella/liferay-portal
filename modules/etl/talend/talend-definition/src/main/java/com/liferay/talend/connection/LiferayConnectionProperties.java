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

package com.liferay.talend.connection;

import com.liferay.talend.LiferayBaseComponentDefinition;
import com.liferay.talend.exception.ExceptionUtils;
import com.liferay.talend.runtime.LiferaySourceOrSinkRuntime;
import com.liferay.talend.tliferayconnection.TLiferayConnectionDefinition;
import com.liferay.talend.utils.PropertiesUtils;

import java.util.EnumSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.talend.components.api.properties.ComponentPropertiesImpl;
import org.talend.components.api.properties.ComponentReferenceProperties;
import org.talend.daikon.NamedThing;
import org.talend.daikon.exception.TalendRuntimeException;
import org.talend.daikon.i18n.GlobalI18N;
import org.talend.daikon.i18n.I18nMessageProvider;
import org.talend.daikon.i18n.I18nMessages;
import org.talend.daikon.properties.PresentationItem;
import org.talend.daikon.properties.ValidationResult;
import org.talend.daikon.properties.ValidationResultMutable;
import org.talend.daikon.properties.presentation.Form;
import org.talend.daikon.properties.presentation.Widget;
import org.talend.daikon.properties.property.Property;
import org.talend.daikon.properties.property.PropertyFactory;
import org.talend.daikon.properties.property.StringProperty;
import org.talend.daikon.sandbox.SandboxedInstance;

/**
 * @author Zoltán Takács
 */
public class LiferayConnectionProperties
	extends ComponentPropertiesImpl
	implements LiferayConnectionPropertiesProvider {

	public static final String FORM_WIZARD = "Wizard";

	public LiferayConnectionProperties(String name) {
		super(name);
	}

	public void afterAnonymousLogin() {
		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(FORM_WIZARD));
	}

	public void afterReferencedComponent() {
		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public void afterSiteId() {
		if (_log.isDebugEnabled()) {
			_log.debug("Site ID: " + siteId.getValue());
		}

		try {
			siteName.setValue(
				siteId.getPossibleValuesDisplayName(siteId.getValue()));
		}
		catch (TalendRuntimeException tre) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"Unable to determine the Site name for the ID: " +
						siteId.getValue());
			}
		}

		refreshLayout(getForm(Form.MAIN));
		refreshLayout(getForm(Form.REFERENCE));
	}

	public ValidationResult beforeSiteId() {
		ValidatedSandboxRuntime validatedSandboxRuntime =
			initializeSandboxedRuntime();

		ValidationResultMutable validationResultMutable =
			validatedSandboxRuntime.getValidationResultMutable();

		if (validationResultMutable.getStatus() ==
				ValidationResult.Result.ERROR) {

			return validationResultMutable;
		}

		LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
			validatedSandboxRuntime.getLiferaySourceOrSinkRuntime();

		try {
			List<NamedThing> webSites =
				liferaySourceOrSinkRuntime.getAvailableWebSites();

			if (webSites.isEmpty()) {
				validationResultMutable.setMessage(
					i18nMessages.getMessage("error.validation.sites"));
				validationResultMutable.setStatus(
					ValidationResult.Result.ERROR);
			}

			siteId.setPossibleNamedThingValues(webSites);
		}
		catch (Exception e) {
			_log.error("Unable to fetch Sites", e);

			return ExceptionUtils.exceptionToValidationResult(e);
		}

		return null;
	}

	@Override
	public LiferayConnectionProperties getLiferayConnectionProperties() {
		return this;
	}

	public String getReferencedComponentId() {
		return referencedComponent.componentInstanceId.getStringValue();
	}

	public LiferayConnectionProperties getReferencedConnectionProperties() {
		LiferayConnectionProperties liferayConnectionProperties =
			referencedComponent.getReference();

		if (liferayConnectionProperties != null) {
			return liferayConnectionProperties;
		}

		if (getReferencedComponentId() != null) {
			_log.error(
				"Connection has a reference to '{}' but the referenced " +
					"Object is null",
				getReferencedComponentId());
		}

		if (_log.isDebugEnabled()) {
			_log.debug(
				"Fall back to the actual instance " +
					"LiferayConnectionProperties for the runtime environment");
		}

		return getLiferayConnectionProperties();
	}

	@Override
	public void refreshLayout(Form form) {
		super.refreshLayout(form);

		String referencedComponentId = getReferencedComponentId();

		boolean useOtherConnection = false;

		if ((referencedComponentId != null) &&
			referencedComponentId.startsWith(
				TLiferayConnectionDefinition.COMPONENT_NAME)) {

			useOtherConnection = true;
		}

		PropertiesUtils.setHidden(form, anonymousLogin, useOtherConnection);
		PropertiesUtils.setHidden(form, apiSpecURL, useOtherConnection);
		PropertiesUtils.setHidden(form, loginType, useOtherConnection);
		PropertiesUtils.setHidden(form, password, useOtherConnection);
		PropertiesUtils.setHidden(form, userId, useOtherConnection);
		PropertiesUtils.setHidden(form, siteName, useOtherConnection);
		PropertiesUtils.setHidden(form, siteId, useOtherConnection);

		if (!useOtherConnection && anonymousLogin.getValue()) {
			PropertiesUtils.setHidden(form, userId, true);
			PropertiesUtils.setHidden(form, password, true);
		}
	}

	@Override
	public void setupLayout() {
		super.setupLayout();

		// Wizard form

		Form wizardForm = Form.create(this, FORM_WIZARD);

		Widget loginWizardWidget = Widget.widget(loginType);

		loginWizardWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);
		loginWizardWidget.setDeemphasize(true);

		wizardForm.addRow(loginWizardWidget);

		wizardForm.addRow(name);

		wizardForm.addRow(apiSpecURL);

		wizardForm.addRow(anonymousLogin);

		wizardForm.addRow(userId);

		wizardForm.addRow(password);

		Widget testConnectionWidget = Widget.widget(testConnection);

		testConnectionWidget.setLongRunning(true);
		testConnectionWidget.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		Widget advancedFormWizardWidget = Widget.widget(advanced);

		advancedFormWizardWidget.setWidgetType(Widget.BUTTON_WIDGET_TYPE);

		wizardForm.addRow(advancedFormWizardWidget);

		wizardForm.addColumn(testConnectionWidget);

		// Main form

		Form mainForm = Form.create(this, Form.MAIN);

		Widget loginMainWidget = Widget.widget(loginType);

		loginMainWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);

		mainForm.addRow(loginMainWidget);

		mainForm.addRow(apiSpecURL);

		mainForm.addRow(anonymousLogin);

		mainForm.addRow(userId);

		mainForm.addColumn(password);

		Widget siteIdWidget = Widget.widget(siteId);

		siteIdWidget.setCallAfter(true);
		siteIdWidget.setLongRunning(true);
		siteIdWidget.setWidgetType(Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		mainForm.addRow(siteIdWidget);

		Widget siteNameWidget = Widget.widget(siteName);

		siteNameWidget.setReadonly(true);

		mainForm.addColumn(siteNameWidget);

		// A form for a reference to a connection, used in a tLiferayInput
		// for example

		Form referenceForm = Form.create(this, Form.REFERENCE);

		Widget referencedComponentWidget = Widget.widget(referencedComponent);

		referencedComponentWidget.setWidgetType(
			Widget.COMPONENT_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(referencedComponentWidget);

		Widget loginReferenceWidget = Widget.widget(loginType);

		loginReferenceWidget.setWidgetType(Widget.ENUMERATION_WIDGET_TYPE);

		referenceForm.addRow(loginReferenceWidget);

		referenceForm.addRow(apiSpecURL);

		referenceForm.addRow(anonymousLogin);

		referenceForm.addRow(userId);

		referenceForm.addColumn(password);

		Widget siteIdReferenceWidget = Widget.widget(siteId);

		siteIdReferenceWidget.setCallAfter(true);
		siteIdReferenceWidget.setLongRunning(true);
		siteIdReferenceWidget.setWidgetType(
			Widget.NAME_SELECTION_REFERENCE_WIDGET_TYPE);

		referenceForm.addRow(siteIdReferenceWidget);

		Widget siteNameReferenceWidget = Widget.widget(siteName);

		siteNameReferenceWidget.setReadonly(true);

		referenceForm.addColumn(siteNameReferenceWidget);

		refreshLayout(referenceForm);

		// Advanced form

		Form advancedForm = new Form(this, Form.ADVANCED);

		advancedForm.addRow(connectTimeout);

		advancedForm.addRow(readTimeout);

		advancedForm.addRow(itemsPerPage);

		advancedForm.addRow(followRedirects);

		advancedForm.addRow(forceHttps);

		advanced.setFormtoShow(advancedForm);
	}

	@Override
	public void setupProperties() {
		super.setupProperties();

		apiSpecURL.setValue(_COMMERCE_CATALOG_OAS_URL);
		followRedirects.setValue(true);
		forceHttps.setValue(false);
		loginType.setValue(LoginType.Basic);
		password.setValue(_PASSWORD);
		userId.setValue(_USER_ID);
		siteName.setValue("");
		siteId.setValue("");
	}

	public ValidationResult validateTestConnection() {
		try {
			SandboxedInstance sandboxedInstance = getRuntimeSandboxedInstance();

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			liferaySourceOrSinkRuntime.initialize(null, this);

			ValidationResult validationResult =
				liferaySourceOrSinkRuntime.validate(null);

			Form form = getForm(FORM_WIZARD);

			if (validationResult.getStatus() == ValidationResult.Result.OK) {
				form.setAllowFinish(true);
				form.setAllowForward(true);
			}
			else {
				form.setAllowForward(false);
			}

			return validationResult;
		}
		catch (Exception e) {
			return new ValidationResult(
				ValidationResult.Result.ERROR, e.getMessage());
		}
	}

	public PresentationItem advanced = new PresentationItem("advanced");
	public Property<Boolean> anonymousLogin = PropertyFactory.newBoolean(
		"anonymousLogin");
	public Property<String> apiSpecURL = PropertyFactory.newString(
		"apiSpecURL");
	public Property<Integer> connectTimeout = PropertyFactory.newInteger(
		"connectTimeout", _CONNECT_TIMEOUT);
	public Property<Boolean> followRedirects = PropertyFactory.newBoolean(
		"followRedirects");
	public Property<Boolean> forceHttps = PropertyFactory.newBoolean(
		"forceHttps");
	public Property<Integer> itemsPerPage = PropertyFactory.newInteger(
		"itemsPerPage", _ITEMS_PER_PAGE);
	public Property<LoginType> loginType = PropertyFactory.newEnum(
		"loginType", LoginType.class
	).setRequired();
	public Property<String> name = PropertyFactory.newString(
		"name"
	).setRequired();
	public Property<String> password = PropertyFactory.newString(
		"password"
	).setFlags(
		EnumSet.of(Property.Flags.ENCRYPT, Property.Flags.SUPPRESS_LOGGING)
	);
	public Property<Integer> readTimeout = PropertyFactory.newInteger(
		"readTimeout", _READ_TIMEOUT);
	public ComponentReferenceProperties<LiferayConnectionProperties>
		referencedComponent = new ComponentReferenceProperties<>(
			"referencedComponent", TLiferayConnectionDefinition.COMPONENT_NAME);
	public StringProperty siteId = new StringProperty("siteId");
	public Property<String> siteName = PropertyFactory.newString("siteName");
	public PresentationItem testConnection = new PresentationItem(
		"testConnection");
	public Property<String> userId = PropertyFactory.newString("userId");

	public enum LoginType {

		Basic("Basic Authentication");

		public String getDescription() {
			return _description;
		}

		private LoginType(String description) {
			_description = description;
		}

		private final String _description;

	}

	public class ValidatedSandboxRuntime {

		public ValidatedSandboxRuntime(
			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime,
			ValidationResultMutable validationResultMutable) {

			if (validationResultMutable == null) {
				throw new NullPointerException(
					"validationResultMutable must not be null");
			}

			_liferaySourceOrSinkRuntime = liferaySourceOrSinkRuntime;
			_validationResultMutable = validationResultMutable;
		}

		public LiferaySourceOrSinkRuntime getLiferaySourceOrSinkRuntime() {
			return _liferaySourceOrSinkRuntime;
		}

		public ValidationResultMutable getValidationResultMutable() {
			return _validationResultMutable;
		}

		private final LiferaySourceOrSinkRuntime _liferaySourceOrSinkRuntime;
		private final ValidationResultMutable _validationResultMutable;

	}

	protected SandboxedInstance getRuntimeSandboxedInstance() {
		return LiferayBaseComponentDefinition.getSandboxedInstance(
			LiferayBaseComponentDefinition.RUNTIME_SOURCE_OR_SINK_CLASS_NAME);
	}

	protected ValidatedSandboxRuntime initializeSandboxedRuntime() {
		try (SandboxedInstance sandboxedInstance =
				LiferayBaseComponentDefinition.getSandboxedInstance(
					LiferayBaseComponentDefinition.
						RUNTIME_SOURCE_OR_SINK_CLASS_NAME)) {

			LiferaySourceOrSinkRuntime liferaySourceOrSinkRuntime =
				(LiferaySourceOrSinkRuntime)sandboxedInstance.getInstance();

			ValidationResultMutable validationResultMutable =
				new ValidationResultMutable(
					liferaySourceOrSinkRuntime.initialize(
						null, getReferencedConnectionProperties()));

			if (validationResultMutable.getStatus() ==
					ValidationResult.Result.ERROR) {

				return new ValidatedSandboxRuntime(
					liferaySourceOrSinkRuntime, validationResultMutable);
			}

			validationResultMutable = new ValidationResultMutable(
				liferaySourceOrSinkRuntime.validate(null));

			return new ValidatedSandboxRuntime(
				liferaySourceOrSinkRuntime, validationResultMutable);
		}
		catch (Exception e) {
			return new ValidatedSandboxRuntime(
				null, ExceptionUtils.exceptionToValidationResult(e));
		}
	}

	protected static final I18nMessages i18nMessages;

	static {
		I18nMessageProvider i18nMessageProvider =
			GlobalI18N.getI18nMessageProvider();

		i18nMessages = i18nMessageProvider.getI18nMessages(
			LiferayConnectionProperties.class);
	}

	private static final String _COMMERCE_CATALOG_OAS_URL =
		"\"http://localhost:8080/o/headless-commerce-admin-catalog/v1.0" +
			"/openapi.json\"";

	private static final int _CONNECT_TIMEOUT = 30;

	private static final int _ITEMS_PER_PAGE = 100;

	private static final String _PASSWORD = "test";

	private static final int _READ_TIMEOUT = 60;

	private static final String _USER_ID = "test@liferay.com";

	private static final Logger _log = LoggerFactory.getLogger(
		LiferayConnectionProperties.class);

	private static final long serialVersionUID = -746398918369840241L;

}