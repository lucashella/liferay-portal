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

package com.liferay.opensocial.service.base;

import com.liferay.opensocial.model.OAuthConsumer;
import com.liferay.opensocial.service.OAuthConsumerLocalService;
import com.liferay.opensocial.service.persistence.GadgetPersistence;
import com.liferay.opensocial.service.persistence.OAuthConsumerPersistence;
import com.liferay.opensocial.service.persistence.OAuthTokenPersistence;
import com.liferay.portal.kernel.bean.BeanReference;
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
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistryUtil;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the o auth consumer local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.opensocial.service.impl.OAuthConsumerLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.opensocial.service.impl.OAuthConsumerLocalServiceImpl
 * @generated
 */
public abstract class OAuthConsumerLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements IdentifiableOSGiService, OAuthConsumerLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>OAuthConsumerLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.opensocial.service.OAuthConsumerLocalServiceUtil</code>.
	 */

	/**
	 * Adds the o auth consumer to the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthConsumer the o auth consumer
	 * @return the o auth consumer that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public OAuthConsumer addOAuthConsumer(OAuthConsumer oAuthConsumer) {
		oAuthConsumer.setNew(true);

		return oAuthConsumerPersistence.update(oAuthConsumer);
	}

	/**
	 * Creates a new o auth consumer with the primary key. Does not add the o auth consumer to the database.
	 *
	 * @param oAuthConsumerId the primary key for the new o auth consumer
	 * @return the new o auth consumer
	 */
	@Override
	@Transactional(enabled = false)
	public OAuthConsumer createOAuthConsumer(long oAuthConsumerId) {
		return oAuthConsumerPersistence.create(oAuthConsumerId);
	}

	/**
	 * Deletes the o auth consumer with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthConsumerId the primary key of the o auth consumer
	 * @return the o auth consumer that was removed
	 * @throws PortalException if a o auth consumer with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public OAuthConsumer deleteOAuthConsumer(long oAuthConsumerId)
		throws PortalException {

		return oAuthConsumerPersistence.remove(oAuthConsumerId);
	}

	/**
	 * Deletes the o auth consumer from the database. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthConsumer the o auth consumer
	 * @return the o auth consumer that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public OAuthConsumer deleteOAuthConsumer(OAuthConsumer oAuthConsumer) {
		return oAuthConsumerPersistence.remove(oAuthConsumer);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			OAuthConsumer.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return oAuthConsumerPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthConsumerModelImpl</code>.
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

		return oAuthConsumerPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthConsumerModelImpl</code>.
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

		return oAuthConsumerPersistence.findWithDynamicQuery(
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
		return oAuthConsumerPersistence.countWithDynamicQuery(dynamicQuery);
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

		return oAuthConsumerPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public OAuthConsumer fetchOAuthConsumer(long oAuthConsumerId) {
		return oAuthConsumerPersistence.fetchByPrimaryKey(oAuthConsumerId);
	}

	/**
	 * Returns the o auth consumer with the primary key.
	 *
	 * @param oAuthConsumerId the primary key of the o auth consumer
	 * @return the o auth consumer
	 * @throws PortalException if a o auth consumer with the primary key could not be found
	 */
	@Override
	public OAuthConsumer getOAuthConsumer(long oAuthConsumerId)
		throws PortalException {

		return oAuthConsumerPersistence.findByPrimaryKey(oAuthConsumerId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(oAuthConsumerLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(OAuthConsumer.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("oAuthConsumerId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			oAuthConsumerLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(OAuthConsumer.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"oAuthConsumerId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(oAuthConsumerLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(OAuthConsumer.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("oAuthConsumerId");
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return oAuthConsumerPersistence.create(
			((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return oAuthConsumerLocalService.deleteOAuthConsumer(
			(OAuthConsumer)persistedModel);
	}

	public BasePersistence<OAuthConsumer> getBasePersistence() {
		return oAuthConsumerPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return oAuthConsumerPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the o auth consumers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.opensocial.model.impl.OAuthConsumerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of o auth consumers
	 * @param end the upper bound of the range of o auth consumers (not inclusive)
	 * @return the range of o auth consumers
	 */
	@Override
	public List<OAuthConsumer> getOAuthConsumers(int start, int end) {
		return oAuthConsumerPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of o auth consumers.
	 *
	 * @return the number of o auth consumers
	 */
	@Override
	public int getOAuthConsumersCount() {
		return oAuthConsumerPersistence.countAll();
	}

	/**
	 * Updates the o auth consumer in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param oAuthConsumer the o auth consumer
	 * @return the o auth consumer that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public OAuthConsumer updateOAuthConsumer(OAuthConsumer oAuthConsumer) {
		return oAuthConsumerPersistence.update(oAuthConsumer);
	}

	/**
	 * Returns the gadget local service.
	 *
	 * @return the gadget local service
	 */
	public com.liferay.opensocial.service.GadgetLocalService
		getGadgetLocalService() {

		return gadgetLocalService;
	}

	/**
	 * Sets the gadget local service.
	 *
	 * @param gadgetLocalService the gadget local service
	 */
	public void setGadgetLocalService(
		com.liferay.opensocial.service.GadgetLocalService gadgetLocalService) {

		this.gadgetLocalService = gadgetLocalService;
	}

	/**
	 * Returns the gadget persistence.
	 *
	 * @return the gadget persistence
	 */
	public GadgetPersistence getGadgetPersistence() {
		return gadgetPersistence;
	}

	/**
	 * Sets the gadget persistence.
	 *
	 * @param gadgetPersistence the gadget persistence
	 */
	public void setGadgetPersistence(GadgetPersistence gadgetPersistence) {
		this.gadgetPersistence = gadgetPersistence;
	}

	/**
	 * Returns the o auth consumer local service.
	 *
	 * @return the o auth consumer local service
	 */
	public OAuthConsumerLocalService getOAuthConsumerLocalService() {
		return oAuthConsumerLocalService;
	}

	/**
	 * Sets the o auth consumer local service.
	 *
	 * @param oAuthConsumerLocalService the o auth consumer local service
	 */
	public void setOAuthConsumerLocalService(
		OAuthConsumerLocalService oAuthConsumerLocalService) {

		this.oAuthConsumerLocalService = oAuthConsumerLocalService;
	}

	/**
	 * Returns the o auth consumer persistence.
	 *
	 * @return the o auth consumer persistence
	 */
	public OAuthConsumerPersistence getOAuthConsumerPersistence() {
		return oAuthConsumerPersistence;
	}

	/**
	 * Sets the o auth consumer persistence.
	 *
	 * @param oAuthConsumerPersistence the o auth consumer persistence
	 */
	public void setOAuthConsumerPersistence(
		OAuthConsumerPersistence oAuthConsumerPersistence) {

		this.oAuthConsumerPersistence = oAuthConsumerPersistence;
	}

	/**
	 * Returns the o auth token local service.
	 *
	 * @return the o auth token local service
	 */
	public com.liferay.opensocial.service.OAuthTokenLocalService
		getOAuthTokenLocalService() {

		return oAuthTokenLocalService;
	}

	/**
	 * Sets the o auth token local service.
	 *
	 * @param oAuthTokenLocalService the o auth token local service
	 */
	public void setOAuthTokenLocalService(
		com.liferay.opensocial.service.OAuthTokenLocalService
			oAuthTokenLocalService) {

		this.oAuthTokenLocalService = oAuthTokenLocalService;
	}

	/**
	 * Returns the o auth token persistence.
	 *
	 * @return the o auth token persistence
	 */
	public OAuthTokenPersistence getOAuthTokenPersistence() {
		return oAuthTokenPersistence;
	}

	/**
	 * Sets the o auth token persistence.
	 *
	 * @param oAuthTokenPersistence the o auth token persistence
	 */
	public void setOAuthTokenPersistence(
		OAuthTokenPersistence oAuthTokenPersistence) {

		this.oAuthTokenPersistence = oAuthTokenPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService
		getCounterLocalService() {

		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService
			counterLocalService) {

		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService
		getClassNameLocalService() {

		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService
			classNameLocalService) {

		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {

		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService
		getResourceLocalService() {

		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService
			resourceLocalService) {

		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService
		getUserLocalService() {

		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {

		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		PersistedModelLocalServiceRegistryUtil.register(
			"com.liferay.opensocial.model.OAuthConsumer",
			oAuthConsumerLocalService);
	}

	public void destroy() {
		PersistedModelLocalServiceRegistryUtil.unregister(
			"com.liferay.opensocial.model.OAuthConsumer");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return OAuthConsumerLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return OAuthConsumer.class;
	}

	protected String getModelClassName() {
		return OAuthConsumer.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = oAuthConsumerPersistence.getDataSource();

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

	@BeanReference(
		type = com.liferay.opensocial.service.GadgetLocalService.class
	)
	protected com.liferay.opensocial.service.GadgetLocalService
		gadgetLocalService;

	@BeanReference(type = GadgetPersistence.class)
	protected GadgetPersistence gadgetPersistence;

	@BeanReference(type = OAuthConsumerLocalService.class)
	protected OAuthConsumerLocalService oAuthConsumerLocalService;

	@BeanReference(type = OAuthConsumerPersistence.class)
	protected OAuthConsumerPersistence oAuthConsumerPersistence;

	@BeanReference(
		type = com.liferay.opensocial.service.OAuthTokenLocalService.class
	)
	protected com.liferay.opensocial.service.OAuthTokenLocalService
		oAuthTokenLocalService;

	@BeanReference(type = OAuthTokenPersistence.class)
	protected OAuthTokenPersistence oAuthTokenPersistence;

	@BeanReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@BeanReference(
		type = com.liferay.portal.kernel.service.ClassNameLocalService.class
	)
	protected com.liferay.portal.kernel.service.ClassNameLocalService
		classNameLocalService;

	@BeanReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;

	@BeanReference(
		type = com.liferay.portal.kernel.service.ResourceLocalService.class
	)
	protected com.liferay.portal.kernel.service.ResourceLocalService
		resourceLocalService;

	@BeanReference(
		type = com.liferay.portal.kernel.service.UserLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@BeanReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;

}