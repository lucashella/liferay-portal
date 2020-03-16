/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.saml.persistence.service.base;

import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.saml.persistence.model.SamlSpAuthRequest;
import com.liferay.saml.persistence.service.SamlSpAuthRequestLocalService;
import com.liferay.saml.persistence.service.persistence.SamlIdpSpConnectionPersistence;
import com.liferay.saml.persistence.service.persistence.SamlIdpSpSessionPersistence;
import com.liferay.saml.persistence.service.persistence.SamlIdpSsoSessionPersistence;
import com.liferay.saml.persistence.service.persistence.SamlSpAuthRequestPersistence;
import com.liferay.saml.persistence.service.persistence.SamlSpIdpConnectionPersistence;
import com.liferay.saml.persistence.service.persistence.SamlSpMessagePersistence;
import com.liferay.saml.persistence.service.persistence.SamlSpSessionPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the saml sp auth request local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.saml.persistence.service.impl.SamlSpAuthRequestLocalServiceImpl}.
 * </p>
 *
 * @author Mika Koivisto
 * @see com.liferay.saml.persistence.service.impl.SamlSpAuthRequestLocalServiceImpl
 * @generated
 */
public abstract class SamlSpAuthRequestLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, IdentifiableOSGiService,
			   SamlSpAuthRequestLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>SamlSpAuthRequestLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.saml.persistence.service.SamlSpAuthRequestLocalServiceUtil</code>.
	 */

	/**
	 * Adds the saml sp auth request to the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpAuthRequest the saml sp auth request
	 * @return the saml sp auth request that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SamlSpAuthRequest addSamlSpAuthRequest(
		SamlSpAuthRequest samlSpAuthRequest) {

		samlSpAuthRequest.setNew(true);

		return samlSpAuthRequestPersistence.update(samlSpAuthRequest);
	}

	/**
	 * Creates a new saml sp auth request with the primary key. Does not add the saml sp auth request to the database.
	 *
	 * @param samlSpAuthnRequestId the primary key for the new saml sp auth request
	 * @return the new saml sp auth request
	 */
	@Override
	@Transactional(enabled = false)
	public SamlSpAuthRequest createSamlSpAuthRequest(
		long samlSpAuthnRequestId) {

		return samlSpAuthRequestPersistence.create(samlSpAuthnRequestId);
	}

	/**
	 * Deletes the saml sp auth request with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request that was removed
	 * @throws PortalException if a saml sp auth request with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SamlSpAuthRequest deleteSamlSpAuthRequest(long samlSpAuthnRequestId)
		throws PortalException {

		return samlSpAuthRequestPersistence.remove(samlSpAuthnRequestId);
	}

	/**
	 * Deletes the saml sp auth request from the database. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpAuthRequest the saml sp auth request
	 * @return the saml sp auth request that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public SamlSpAuthRequest deleteSamlSpAuthRequest(
		SamlSpAuthRequest samlSpAuthRequest) {

		return samlSpAuthRequestPersistence.remove(samlSpAuthRequest);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			SamlSpAuthRequest.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return samlSpAuthRequestPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlSpAuthRequestModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return samlSpAuthRequestPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlSpAuthRequestModelImpl</code>.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<T> orderByComparator) {

		return samlSpAuthRequestPersistence.findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return samlSpAuthRequestPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(
		DynamicQuery dynamicQuery, Projection projection) {

		return samlSpAuthRequestPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public SamlSpAuthRequest fetchSamlSpAuthRequest(long samlSpAuthnRequestId) {
		return samlSpAuthRequestPersistence.fetchByPrimaryKey(
			samlSpAuthnRequestId);
	}

	/**
	 * Returns the saml sp auth request with the primary key.
	 *
	 * @param samlSpAuthnRequestId the primary key of the saml sp auth request
	 * @return the saml sp auth request
	 * @throws PortalException if a saml sp auth request with the primary key could not be found
	 */
	@Override
	public SamlSpAuthRequest getSamlSpAuthRequest(long samlSpAuthnRequestId)
		throws PortalException {

		return samlSpAuthRequestPersistence.findByPrimaryKey(
			samlSpAuthnRequestId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(
			samlSpAuthRequestLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SamlSpAuthRequest.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"samlSpAuthnRequestId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			samlSpAuthRequestLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(SamlSpAuthRequest.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"samlSpAuthnRequestId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(
			samlSpAuthRequestLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(SamlSpAuthRequest.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"samlSpAuthnRequestId");
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return samlSpAuthRequestPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return samlSpAuthRequestLocalService.deleteSamlSpAuthRequest(
			(SamlSpAuthRequest)persistedModel);
	}

	public BasePersistence<SamlSpAuthRequest> getBasePersistence() {
		return samlSpAuthRequestPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return samlSpAuthRequestPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the saml sp auth requests.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.saml.persistence.model.impl.SamlSpAuthRequestModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of saml sp auth requests
	 * @param end the upper bound of the range of saml sp auth requests (not inclusive)
	 * @return the range of saml sp auth requests
	 */
	@Override
	public List<SamlSpAuthRequest> getSamlSpAuthRequests(int start, int end) {
		return samlSpAuthRequestPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of saml sp auth requests.
	 *
	 * @return the number of saml sp auth requests
	 */
	@Override
	public int getSamlSpAuthRequestsCount() {
		return samlSpAuthRequestPersistence.countAll();
	}

	/**
	 * Updates the saml sp auth request in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param samlSpAuthRequest the saml sp auth request
	 * @return the saml sp auth request that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public SamlSpAuthRequest updateSamlSpAuthRequest(
		SamlSpAuthRequest samlSpAuthRequest) {

		return samlSpAuthRequestPersistence.update(samlSpAuthRequest);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			SamlSpAuthRequestLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		samlSpAuthRequestLocalService = (SamlSpAuthRequestLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return SamlSpAuthRequestLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return SamlSpAuthRequest.class;
	}

	protected String getModelClassName() {
		return SamlSpAuthRequest.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource =
				samlSpAuthRequestPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(
				dataSource, sql);

			sqlUpdate.update();
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
	}

	@Reference
	protected SamlIdpSpConnectionPersistence samlIdpSpConnectionPersistence;

	@Reference
	protected SamlIdpSpSessionPersistence samlIdpSpSessionPersistence;

	@Reference
	protected SamlIdpSsoSessionPersistence samlIdpSsoSessionPersistence;

	protected SamlSpAuthRequestLocalService samlSpAuthRequestLocalService;

	@Reference
	protected SamlSpAuthRequestPersistence samlSpAuthRequestPersistence;

	@Reference
	protected SamlSpIdpConnectionPersistence samlSpIdpConnectionPersistence;

	@Reference
	protected SamlSpMessagePersistence samlSpMessagePersistence;

	@Reference
	protected SamlSpSessionPersistence samlSpSessionPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

}