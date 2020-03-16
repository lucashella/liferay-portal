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

package com.liferay.dynamic.data.lists.service.base;

import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordFinder;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordPersistence;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetFinder;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordSetPersistence;
import com.liferay.dynamic.data.lists.service.persistence.DDLRecordVersionPersistence;
import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
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
 * Provides the base implementation for the ddl record local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordLocalServiceImpl
 * @generated
 */
public abstract class DDLRecordLocalServiceBaseImpl
	extends BaseLocalServiceImpl
	implements AopService, DDLRecordLocalService, IdentifiableOSGiService {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Use <code>DDLRecordLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil</code>.
	 */

	/**
	 * Adds the ddl record to the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecord the ddl record
	 * @return the ddl record that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecord addDDLRecord(DDLRecord ddlRecord) {
		ddlRecord.setNew(true);

		return ddlRecordPersistence.update(ddlRecord);
	}

	/**
	 * Creates a new ddl record with the primary key. Does not add the ddl record to the database.
	 *
	 * @param recordId the primary key for the new ddl record
	 * @return the new ddl record
	 */
	@Override
	@Transactional(enabled = false)
	public DDLRecord createDDLRecord(long recordId) {
		return ddlRecordPersistence.create(recordId);
	}

	/**
	 * Deletes the ddl record with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record that was removed
	 * @throws PortalException if a ddl record with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDLRecord deleteDDLRecord(long recordId) throws PortalException {
		return ddlRecordPersistence.remove(recordId);
	}

	/**
	 * Deletes the ddl record from the database. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecord the ddl record
	 * @return the ddl record that was removed
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public DDLRecord deleteDDLRecord(DDLRecord ddlRecord) {
		return ddlRecordPersistence.remove(ddlRecord);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(
			DDLRecord.class, clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return ddlRecordPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl</code>.
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

		return ddlRecordPersistence.findWithDynamicQuery(
			dynamicQuery, start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl</code>.
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

		return ddlRecordPersistence.findWithDynamicQuery(
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
		return ddlRecordPersistence.countWithDynamicQuery(dynamicQuery);
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

		return ddlRecordPersistence.countWithDynamicQuery(
			dynamicQuery, projection);
	}

	@Override
	public DDLRecord fetchDDLRecord(long recordId) {
		return ddlRecordPersistence.fetchByPrimaryKey(recordId);
	}

	/**
	 * Returns the ddl record matching the UUID and group.
	 *
	 * @param uuid the ddl record's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddl record, or <code>null</code> if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord fetchDDLRecordByUuidAndGroupId(String uuid, long groupId) {
		return ddlRecordPersistence.fetchByUUID_G(uuid, groupId);
	}

	/**
	 * Returns the ddl record with the primary key.
	 *
	 * @param recordId the primary key of the ddl record
	 * @return the ddl record
	 * @throws PortalException if a ddl record with the primary key could not be found
	 */
	@Override
	public DDLRecord getDDLRecord(long recordId) throws PortalException {
		return ddlRecordPersistence.findByPrimaryKey(recordId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery =
			new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(ddlRecordLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DDLRecord.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("recordId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery
		getIndexableActionableDynamicQuery() {

		IndexableActionableDynamicQuery indexableActionableDynamicQuery =
			new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(
			ddlRecordLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(DDLRecord.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName("recordId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {

		actionableDynamicQuery.setBaseLocalService(ddlRecordLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(DDLRecord.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName("recordId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {

		final ExportActionableDynamicQuery exportActionableDynamicQuery =
			new ExportActionableDynamicQuery() {

				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary =
						portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(
						stagedModelType, modelAdditionCount);

					long modelDeletionCount =
						ExportImportHelperUtil.getModelDeletionCount(
							portletDataContext, stagedModelType);

					manifestSummary.addModelDeletionCount(
						stagedModelType, modelDeletionCount);

					return modelAdditionCount;
				}

			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(
			new ActionableDynamicQuery.AddCriteriaMethod() {

				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(
						dynamicQuery, "modifiedDate");
				}

			});

		exportActionableDynamicQuery.setCompanyId(
			portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setGroupId(
			portletDataContext.getScopeGroupId());

		exportActionableDynamicQuery.setPerformActionMethod(
			new ActionableDynamicQuery.PerformActionMethod<DDLRecord>() {

				@Override
				public void performAction(DDLRecord ddlRecord)
					throws PortalException {

					StagedModelDataHandlerUtil.exportStagedModel(
						portletDataContext, ddlRecord);
				}

			});
		exportActionableDynamicQuery.setStagedModelType(
			new StagedModelType(
				PortalUtil.getClassNameId(DDLRecord.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	public PersistedModel createPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ddlRecordPersistence.create(((Long)primaryKeyObj).longValue());
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {

		return ddlRecordLocalService.deleteDDLRecord((DDLRecord)persistedModel);
	}

	public BasePersistence<DDLRecord> getBasePersistence() {
		return ddlRecordPersistence;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {

		return ddlRecordPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns all the ddl records matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddl records
	 * @param companyId the primary key of the company
	 * @return the matching ddl records, or an empty list if no matches were found
	 */
	@Override
	public List<DDLRecord> getDDLRecordsByUuidAndCompanyId(
		String uuid, long companyId) {

		return ddlRecordPersistence.findByUuid_C(uuid, companyId);
	}

	/**
	 * Returns a range of ddl records matching the UUID and company.
	 *
	 * @param uuid the UUID of the ddl records
	 * @param companyId the primary key of the company
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the range of matching ddl records, or an empty list if no matches were found
	 */
	@Override
	public List<DDLRecord> getDDLRecordsByUuidAndCompanyId(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DDLRecord> orderByComparator) {

		return ddlRecordPersistence.findByUuid_C(
			uuid, companyId, start, end, orderByComparator);
	}

	/**
	 * Returns the ddl record matching the UUID and group.
	 *
	 * @param uuid the ddl record's UUID
	 * @param groupId the primary key of the group
	 * @return the matching ddl record
	 * @throws PortalException if a matching ddl record could not be found
	 */
	@Override
	public DDLRecord getDDLRecordByUuidAndGroupId(String uuid, long groupId)
		throws PortalException {

		return ddlRecordPersistence.findByUUID_G(uuid, groupId);
	}

	/**
	 * Returns a range of all the ddl records.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>com.liferay.dynamic.data.lists.model.impl.DDLRecordModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddl records
	 * @param end the upper bound of the range of ddl records (not inclusive)
	 * @return the range of ddl records
	 */
	@Override
	public List<DDLRecord> getDDLRecords(int start, int end) {
		return ddlRecordPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of ddl records.
	 *
	 * @return the number of ddl records
	 */
	@Override
	public int getDDLRecordsCount() {
		return ddlRecordPersistence.countAll();
	}

	/**
	 * Updates the ddl record in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param ddlRecord the ddl record
	 * @return the ddl record that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public DDLRecord updateDDLRecord(DDLRecord ddlRecord) {
		return ddlRecordPersistence.update(ddlRecord);
	}

	@Override
	public Class<?>[] getAopInterfaces() {
		return new Class<?>[] {
			DDLRecordLocalService.class, IdentifiableOSGiService.class,
			PersistedModelLocalService.class
		};
	}

	@Override
	public void setAopProxy(Object aopProxy) {
		ddlRecordLocalService = (DDLRecordLocalService)aopProxy;
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return DDLRecordLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return DDLRecord.class;
	}

	protected String getModelClassName() {
		return DDLRecord.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = ddlRecordPersistence.getDataSource();

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

	protected DDLRecordLocalService ddlRecordLocalService;

	@Reference
	protected DDLRecordPersistence ddlRecordPersistence;

	@Reference
	protected DDLRecordFinder ddlRecordFinder;

	@Reference
	protected com.liferay.counter.kernel.service.CounterLocalService
		counterLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.UserLocalService
		userLocalService;

	@Reference
	protected com.liferay.portal.kernel.service.WorkflowInstanceLinkLocalService
		workflowInstanceLinkLocalService;

	@Reference
	protected com.liferay.asset.kernel.service.AssetEntryLocalService
		assetEntryLocalService;

	@Reference
	protected com.liferay.ratings.kernel.service.RatingsStatsLocalService
		ratingsStatsLocalService;

	@Reference
	protected DDLRecordSetPersistence ddlRecordSetPersistence;

	@Reference
	protected DDLRecordSetFinder ddlRecordSetFinder;

	@Reference
	protected DDLRecordVersionPersistence ddlRecordVersionPersistence;

}