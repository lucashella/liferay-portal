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

package com.liferay.account.internal.search.spi.model.query.contributor;

import com.liferay.account.constants.AccountConstants;
import com.liferay.portal.kernel.search.BooleanClauseOccur;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.search.filter.BooleanFilter;
import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.search.spi.model.query.contributor.ModelPreFilterContributor;
import com.liferay.portal.search.spi.model.registrar.ModelSearchSettings;

import org.osgi.service.component.annotations.Component;

/**
 * @author Drew Brokke
 */
@Component(
	immediate = true,
	property = "indexer.class.name=com.liferay.account.model.AccountEntry",
	service = ModelPreFilterContributor.class
)
public class AccountEntryModelPreFilterContributor
	implements ModelPreFilterContributor {

	@Override
	public void contribute(
		BooleanFilter booleanFilter, ModelSearchSettings modelSearchSettings,
		SearchContext searchContext) {

		_filterByAccountUserIds(booleanFilter, searchContext);
		_filterByDomains(booleanFilter, searchContext);
		_filterByOrganizationIds(booleanFilter, searchContext);
		_filterByParentAccountEntryId(booleanFilter, searchContext);
		_filterByStatus(booleanFilter, searchContext);
		_filterByType(booleanFilter, searchContext);
	}

	private void _filterByAccountUserIds(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] accountUserIds = (long[])searchContext.getAttribute(
			"accountUserIds");

		if (ArrayUtil.isNotEmpty(accountUserIds)) {
			TermsFilter accountEntryTermsFilter = new TermsFilter(
				"accountUserIds");

			accountEntryTermsFilter.addValues(
				ArrayUtil.toStringArray(accountUserIds));

			booleanFilter.add(accountEntryTermsFilter, BooleanClauseOccur.MUST);
		}
	}

	private void _filterByDomains(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		String[] domains = (String[])searchContext.getAttribute("domains");

		if (ArrayUtil.isNotEmpty(domains)) {
			TermsFilter domainTermsFilter = new TermsFilter("domains");

			domainTermsFilter.addValues(domains);

			booleanFilter.add(domainTermsFilter, BooleanClauseOccur.MUST);
		}
	}

	private void _filterByOrganizationIds(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long[] organizationIds = (long[])searchContext.getAttribute(
			"organizationIds");

		if (ArrayUtil.isNotEmpty(organizationIds)) {
			TermsFilter accountEntryTermsFilter = new TermsFilter(
				"organizationIds");

			accountEntryTermsFilter.addValues(
				ArrayUtil.toStringArray(organizationIds));

			booleanFilter.add(accountEntryTermsFilter, BooleanClauseOccur.MUST);
		}
	}

	private void _filterByParentAccountEntryId(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		long parentAccountEntryId = GetterUtil.getLong(
			searchContext.getAttribute("parentAccountEntryId"),
			AccountConstants.ACCOUNT_ENTRY_ID_ANY);

		if (parentAccountEntryId != AccountConstants.ACCOUNT_ENTRY_ID_ANY) {
			booleanFilter.addRequiredTerm(
				"parentAccountEntryId", String.valueOf(parentAccountEntryId));
		}
	}

	private void _filterByStatus(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		int status = GetterUtil.getInteger(
			searchContext.getAttribute("status"),
			WorkflowConstants.STATUS_APPROVED);

		if (status != WorkflowConstants.STATUS_ANY) {
			booleanFilter.addRequiredTerm("status", String.valueOf(status));
		}
	}

	private void _filterByType(
		BooleanFilter booleanFilter, SearchContext searchContext) {

		String type = GetterUtil.getString(
			searchContext.getAttribute(Field.TYPE));

		if (Validator.isNotNull(type)) {
			booleanFilter.addRequiredTerm(Field.TYPE, type);
		}
	}

}