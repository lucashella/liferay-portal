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

package com.liferay.document.library.sync.service.base;

import com.liferay.document.library.sync.model.DLSyncEvent;
import com.liferay.document.library.sync.service.DLSyncEventLocalService;
import com.liferay.document.library.sync.service.persistence.DLSyncEventPersistence;
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

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Reference;

/**
 * Provides the base implementation for the dl sync event local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.document.library.sync.service.impl.DLSyncEventLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.document.library.sync.service.impl.DLSyncEventLocalServiceImpl
 * @generated
 */
public abstract class DLSyncEventLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, DLSyncEventLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>DLSyncEventLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.document.library.sync.service.DLSyncEventLocalServiceUtil</code>.
	 */

	/**
	 * Adds the dl sync event to the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlSyncEvent the dl sync event
	 * @return the dl sync event that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLSyncEvent addDLSyncEvent(DLSyncEvent dlSyncEvent) {
		dlSyncEvent.setNew(true);

		return dlSyncEventPersistence.update(dlSyncEvent);
	}

	/**
	 * Creates a new dl sync event with the primary key. Does not add the dl sync event to the database.
	 *
	 * @param syncEventId the primary key for the new dl sync event
	 * @return the new dl sync event
	 */
	@Override
	@Transactional(enabled = false)
	public DLSyncEvent createDLSyncEvent(long syncEventId) {
		return dlSyncEventPersistence.create(syncEventId);
	}

	/**
	 * Deletes the dl sync event with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event that was removed
	 * @throws PortalException if a dl sync event with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLSyncEvent deleteDLSyncEvent(long syncEventId)
		throws PortalException {

		return dlSyncEventPersistence.remove(syncEventId);
	}

	/**
	 * Deletes the dl sync event from the database. Also notifies the appropriate model listeners.
	 *
	 * @param dlSyncEvent the dl sync event
	 * @return the dl sync event that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DLSyncEvent deleteDLSyncEvent(DLSyncEvent dlSyncEvent) {
		return dlSyncEventPersistence.remove(dlSyncEvent);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			DLSyncEvent.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return dlSyncEventPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.sync.model.impl.DLSyncEventModelImpl</code>.
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

		return dlSyncEventPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.sync.model.impl.DLSyncEventModelImpl</code>.
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

		return dlSyncEventPersistence.findWithDynamicQuery(
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
		return dlSyncEventPersistence.countWithDynamicQuery(dynamicQuery);
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

		return dlSyncEventPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public DLSyncEvent fetchDLSyncEvent(long syncEventId) {
		return dlSyncEventPersistence.fetchByPrimaryKey(syncEventId);
	}

	/**
	 * Returns the dl sync event with the primary key.
	 *
	 * @param syncEventId the primary key of the dl sync event
	 * @return the dl sync event
	 * @throws PortalException if a dl sync event with the primary key could not be found
	 */
	@Override
	public DLSyncEvent getDLSyncEvent(long syncEventId) throws PortalException {
		return dlSyncEventPersistence.findByPrimaryKey(syncEventId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(dlSyncEventLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DLSyncEvent.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("syncEventId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			dlSyncEventLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(DLSyncEvent.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"syncEventId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(dlSyncEventLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DLSyncEvent.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("syncEventId");
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return dlSyncEventPersistence.create(((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return dlSyncEventLocalService.deleteDLSyncEvent(
			(DLSyncEvent)persistedModel);
	}

	public BasePersistence<DLSyncEvent> getBasePersistence() {
		return dlSyncEventPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return dlSyncEventPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns a range of all the dl sync events.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.document.library.sync.model.impl.DLSyncEventModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of dl sync events
	 * @param end the upper bound of the range of dl sync events (not inclusive)
	 * @return the range of dl sync events
	 */
	@Override
	public List<DLSyncEvent> getDLSyncEvents(int start, int end) {
		return dlSyncEventPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of dl sync events.
	 *
	 * @return the number of dl sync events
	 */
	@Override
	public int getDLSyncEventsCount() {
		return dlSyncEventPersistence.countAll();
	}

	/**
	 * Updates the dl sync event in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param dlSyncEvent the dl sync event
	 * @return the dl sync event that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DLSyncEvent updateDLSyncEvent(DLSyncEvent dlSyncEvent) {
		return dlSyncEventPersistence.update(dlSyncEvent);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			DLSyncEventLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		dlSyncEventLocalService = (DLSyncEventLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DLSyncEventLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DLSyncEvent.class;
	}

	protected String getModelClassName() {
		return DLSyncEvent.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = dlSyncEventPersistence.getDataSource();

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

	protected DLSyncEventLocalService dlSyncEventLocalService;

	@Reference
	protected DLSyncEventPersistence dlSyncEventPersistence;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

}