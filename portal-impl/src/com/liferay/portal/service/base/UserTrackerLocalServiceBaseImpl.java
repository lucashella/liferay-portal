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

package com.liferay.portal.service.base;

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
import com.liferay.portal.kernel.model.UserTracker;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.UserTrackerLocalService;
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.UserTrackerPathPersistence;
import com.liferay.portal.kernel.service.persistence.UserTrackerPersistence;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the user tracker local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.portal.service.impl.UserTrackerLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.service.impl.UserTrackerLocalServiceImpl
 * @generated
 */
public abstract class UserTrackerLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements IdentifiableOSGiService, UserTrackerLocalService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>UserTrackerLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.portal.kernel.service.UserTrackerLocalServiceUtil</code>.
	 */

	/**
	 * Adds the user tracker to the database. Also notifies the appropriate model listeners.
	 *
	 * @param userTracker the user tracker
	 * @return the user tracker that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public UserTracker addUserTracker(UserTracker userTracker) {
		userTracker.setNew(true);

		return userTrackerPersistence.update(userTracker);
	}

	/**
	 * Creates a new user tracker with the primary key. Does not add the user tracker to the database.
	 *
	 * @param userTrackerId the primary key for the new user tracker
	 * @return the new user tracker
	 */
	@Override
	@Transactional(enabled = false)
	public UserTracker createUserTracker(long userTrackerId) {
		return userTrackerPersistence.create(userTrackerId);
	}

	/**
	 * Deletes the user tracker with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userTrackerId the primary key of the user tracker
	 * @return the user tracker that was removed
	 * @throws PortalException if a user tracker with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public UserTracker deleteUserTracker(long userTrackerId)
		throws PortalException {

		return userTrackerPersistence.remove(userTrackerId);
	}

	/**
	 * Deletes the user tracker from the database. Also notifies the appropriate model listeners.
	 *
	 * @param userTracker the user tracker
	 * @return the user tracker that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public UserTracker deleteUserTracker(UserTracker userTracker) {
		return userTrackerPersistence.remove(userTracker);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			UserTracker.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return userTrackerPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserTrackerModelImpl</code>.
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

		return userTrackerPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserTrackerModelImpl</code>.
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

		return userTrackerPersistence.findWithDynamicQuery(
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
		return userTrackerPersistence.countWithDynamicQuery(dynamicQuery);
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

		return userTrackerPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public UserTracker fetchUserTracker(long userTrackerId) {
		return userTrackerPersistence.fetchByPrimaryKey(userTrackerId);
	}

	/**
	 * Returns the user tracker with the primary key.
	 *
	 * @param userTrackerId the primary key of the user tracker
	 * @return the user tracker
	 * @throws PortalException if a user tracker with the primary key could not be found
	 */
	@Override
	public UserTracker getUserTracker(long userTrackerId)
		throws PortalException {

		return userTrackerPersistence.findByPrimaryKey(userTrackerId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(userTrackerLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(UserTracker.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("userTrackerId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			userTrackerLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(UserTracker.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"userTrackerId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(userTrackerLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(UserTracker.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("userTrackerId");
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return userTrackerPersistence.create(((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return userTrackerLocalService.deleteUserTracker(
			(UserTracker)persistedModel);
	}

	public BasePersistence<UserTracker> getBasePersistence() {
		return userTrackerPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return userTrackerPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the user trackers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.portal.model.impl.UserTrackerModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of user trackers
	 * @param end the upper bound of the range of user trackers (not inclusive)
	 * @return the range of user trackers
	 */
	@Override
	public List<UserTracker> getUserTrackers(int start, int end) {
		return userTrackerPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of user trackers.
	 *
	 * @return the number of user trackers
	 */
	@Override
	public int getUserTrackersCount() {
		return userTrackerPersistence.countAll();
	}

	/**
	 * Updates the user tracker in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param userTracker the user tracker
	 * @return the user tracker that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public UserTracker updateUserTracker(UserTracker userTracker) {
		return userTrackerPersistence.update(userTracker);
	}

	/**
	 * Returns the user tracker local service.
	 *
	 * @return the user tracker local service
	 */
	public UserTrackerLocalService getUserTrackerLocalService() {
		return userTrackerLocalService;
	}

	/**
	 * Sets the user tracker local service.
	 *
	 * @param userTrackerLocalService the user tracker local service
	 */
	public void setUserTrackerLocalService(
		UserTrackerLocalService userTrackerLocalService) {

		this.userTrackerLocalService = userTrackerLocalService;
	}

	/**
	 * Returns the user tracker persistence.
	 *
	 * @return the user tracker persistence
	 */
	public UserTrackerPersistence getUserTrackerPersistence() {
		return userTrackerPersistence;
	}

	/**
	 * Sets the user tracker persistence.
	 *
	 * @param userTrackerPersistence the user tracker persistence
	 */
	public void setUserTrackerPersistence(
		UserTrackerPersistence userTrackerPersistence) {

		this.userTrackerPersistence = userTrackerPersistence;
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
	 * Returns the user tracker path local service.
	 *
	 * @return the user tracker path local service
	 */
	public com.liferay.portal.kernel.service.UserTrackerPathLocalService
		getUserTrackerPathLocalService() {

		return userTrackerPathLocalService;
	}

	/**
	 * Sets the user tracker path local service.
	 *
	 * @param userTrackerPathLocalService the user tracker path local service
	 */
	public void setUserTrackerPathLocalService(
		com.liferay.portal.kernel.service.UserTrackerPathLocalService
			userTrackerPathLocalService) {

		this.userTrackerPathLocalService = userTrackerPathLocalService;
	}

	/**
	 * Returns the user tracker path persistence.
	 *
	 * @return the user tracker path persistence
	 */
	public UserTrackerPathPersistence getUserTrackerPathPersistence() {
		return userTrackerPathPersistence;
	}

	/**
	 * Sets the user tracker path persistence.
	 *
	 * @param userTrackerPathPersistence the user tracker path persistence
	 */
	public void setUserTrackerPathPersistence(
		UserTrackerPathPersistence userTrackerPathPersistence) {

		this.userTrackerPathPersistence = userTrackerPathPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register(
			"com.liferay.portal.kernel.model.UserTracker",
			userTrackerLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.portal.kernel.model.UserTracker");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return UserTrackerLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return UserTracker.class;
	}

	protected String getModelClassName() {
		return UserTracker.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = userTrackerPersistence.getDataSource();

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

	@BeanReference(type = UserTrackerLocalService.class)
	protected UserTrackerLocalService userTrackerLocalService;

	@BeanReference(type = UserTrackerPersistence.class)
	protected UserTrackerPersistence userTrackerPersistence;

	@BeanReference(
		type = com.liferay.counter.kernel.service.CounterLocalService.class
	)
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@BeanReference(
		type = com.liferay.portal.kernel.service.UserTrackerPathLocalService.class
	)
	protected com.liferay.portal.kernel.service.UserTrackerPathLocalService
		userTrackerPathLocalService;

	@BeanReference(type = UserTrackerPathPersistence.class)
	protected UserTrackerPathPersistence userTrackerPathPersistence;

	@BeanReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry
		persistedModelLocalServiceRegistry;

}