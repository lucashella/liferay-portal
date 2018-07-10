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

package com.liferay.document.library.opener.model.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.document.library.opener.model.DLOpenerFileEntryReference;
import com.liferay.document.library.opener.model.DLOpenerFileEntryReferenceModel;

import com.liferay.expando.kernel.model.ExpandoBridge;
import com.liferay.expando.kernel.util.ExpandoBridgeFactoryUtil;

import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.CacheModel;
import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.impl.BaseModelImpl;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;

import java.io.Serializable;

import java.sql.Types;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * The base model implementation for the DLOpenerFileEntryReference service. Represents a row in the &quot;DLOpenerFileEntryReference&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link DLOpenerFileEntryReferenceModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link DLOpenerFileEntryReferenceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLOpenerFileEntryReferenceImpl
 * @see DLOpenerFileEntryReference
 * @see DLOpenerFileEntryReferenceModel
 * @generated
 */
@ProviderType
public class DLOpenerFileEntryReferenceModelImpl extends BaseModelImpl<DLOpenerFileEntryReference>
	implements DLOpenerFileEntryReferenceModel {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a dl opener file entry reference model instance should use the {@link DLOpenerFileEntryReference} interface instead.
	 */
	public static final String TABLE_NAME = "DLOpenerFileEntryReference";
	public static final Object[][] TABLE_COLUMNS = {
			{ "dlOpenerFileEntryReferenceId", Types.BIGINT },
			{ "groupId", Types.BIGINT },
			{ "companyId", Types.BIGINT },
			{ "userId", Types.BIGINT },
			{ "userName", Types.VARCHAR },
			{ "createDate", Types.TIMESTAMP },
			{ "modifiedDate", Types.TIMESTAMP },
			{ "referenceKey", Types.VARCHAR },
			{ "fileEntryId", Types.BIGINT }
		};
	public static final Map<String, Integer> TABLE_COLUMNS_MAP = new HashMap<String, Integer>();

	static {
		TABLE_COLUMNS_MAP.put("dlOpenerFileEntryReferenceId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("groupId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("companyId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userId", Types.BIGINT);
		TABLE_COLUMNS_MAP.put("userName", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("createDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("modifiedDate", Types.TIMESTAMP);
		TABLE_COLUMNS_MAP.put("referenceKey", Types.VARCHAR);
		TABLE_COLUMNS_MAP.put("fileEntryId", Types.BIGINT);
	}

	public static final String TABLE_SQL_CREATE = "create table DLOpenerFileEntryReference (dlOpenerFileEntryReferenceId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,referenceKey VARCHAR(75) null,fileEntryId LONG)";
	public static final String TABLE_SQL_DROP = "drop table DLOpenerFileEntryReference";
	public static final String ORDER_BY_JPQL = " ORDER BY dlOpenerFileEntryReference.dlOpenerFileEntryReferenceId ASC";
	public static final String ORDER_BY_SQL = " ORDER BY DLOpenerFileEntryReference.dlOpenerFileEntryReferenceId ASC";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.document.library.opener.service.util.ServiceProps.get(
				"value.object.entity.cache.enabled.com.liferay.document.library.opener.model.DLOpenerFileEntryReference"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.document.library.opener.service.util.ServiceProps.get(
				"value.object.finder.cache.enabled.com.liferay.document.library.opener.model.DLOpenerFileEntryReference"),
			true);
	public static final boolean COLUMN_BITMASK_ENABLED = GetterUtil.getBoolean(com.liferay.document.library.opener.service.util.ServiceProps.get(
				"value.object.column.bitmask.enabled.com.liferay.document.library.opener.model.DLOpenerFileEntryReference"),
			true);
	public static final long FILEENTRYID_COLUMN_BITMASK = 1L;
	public static final long DLOPENERFILEENTRYREFERENCEID_COLUMN_BITMASK = 2L;
	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.document.library.opener.service.util.ServiceProps.get(
				"lock.expiration.time.com.liferay.document.library.opener.model.DLOpenerFileEntryReference"));

	public DLOpenerFileEntryReferenceModelImpl() {
	}

	@Override
	public long getPrimaryKey() {
		return _dlOpenerFileEntryReferenceId;
	}

	@Override
	public void setPrimaryKey(long primaryKey) {
		setDlOpenerFileEntryReferenceId(primaryKey);
	}

	@Override
	public Serializable getPrimaryKeyObj() {
		return _dlOpenerFileEntryReferenceId;
	}

	@Override
	public void setPrimaryKeyObj(Serializable primaryKeyObj) {
		setPrimaryKey(((Long)primaryKeyObj).longValue());
	}

	@Override
	public Class<?> getModelClass() {
		return DLOpenerFileEntryReference.class;
	}

	@Override
	public String getModelClassName() {
		return DLOpenerFileEntryReference.class.getName();
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("dlOpenerFileEntryReferenceId",
			getDlOpenerFileEntryReferenceId());
		attributes.put("groupId", getGroupId());
		attributes.put("companyId", getCompanyId());
		attributes.put("userId", getUserId());
		attributes.put("userName", getUserName());
		attributes.put("createDate", getCreateDate());
		attributes.put("modifiedDate", getModifiedDate());
		attributes.put("referenceKey", getReferenceKey());
		attributes.put("fileEntryId", getFileEntryId());

		attributes.put("entityCacheEnabled", isEntityCacheEnabled());
		attributes.put("finderCacheEnabled", isFinderCacheEnabled());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long dlOpenerFileEntryReferenceId = (Long)attributes.get(
				"dlOpenerFileEntryReferenceId");

		if (dlOpenerFileEntryReferenceId != null) {
			setDlOpenerFileEntryReferenceId(dlOpenerFileEntryReferenceId);
		}

		Long groupId = (Long)attributes.get("groupId");

		if (groupId != null) {
			setGroupId(groupId);
		}

		Long companyId = (Long)attributes.get("companyId");

		if (companyId != null) {
			setCompanyId(companyId);
		}

		Long userId = (Long)attributes.get("userId");

		if (userId != null) {
			setUserId(userId);
		}

		String userName = (String)attributes.get("userName");

		if (userName != null) {
			setUserName(userName);
		}

		Date createDate = (Date)attributes.get("createDate");

		if (createDate != null) {
			setCreateDate(createDate);
		}

		Date modifiedDate = (Date)attributes.get("modifiedDate");

		if (modifiedDate != null) {
			setModifiedDate(modifiedDate);
		}

		String referenceKey = (String)attributes.get("referenceKey");

		if (referenceKey != null) {
			setReferenceKey(referenceKey);
		}

		Long fileEntryId = (Long)attributes.get("fileEntryId");

		if (fileEntryId != null) {
			setFileEntryId(fileEntryId);
		}
	}

	@Override
	public long getDlOpenerFileEntryReferenceId() {
		return _dlOpenerFileEntryReferenceId;
	}

	@Override
	public void setDlOpenerFileEntryReferenceId(
		long dlOpenerFileEntryReferenceId) {
		_dlOpenerFileEntryReferenceId = dlOpenerFileEntryReferenceId;
	}

	@Override
	public long getGroupId() {
		return _groupId;
	}

	@Override
	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	@Override
	public long getCompanyId() {
		return _companyId;
	}

	@Override
	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	@Override
	public long getUserId() {
		return _userId;
	}

	@Override
	public void setUserId(long userId) {
		_userId = userId;
	}

	@Override
	public String getUserUuid() {
		try {
			User user = UserLocalServiceUtil.getUserById(getUserId());

			return user.getUuid();
		}
		catch (PortalException pe) {
			return "";
		}
	}

	@Override
	public void setUserUuid(String userUuid) {
	}

	@Override
	public String getUserName() {
		if (_userName == null) {
			return "";
		}
		else {
			return _userName;
		}
	}

	@Override
	public void setUserName(String userName) {
		_userName = userName;
	}

	@Override
	public Date getCreateDate() {
		return _createDate;
	}

	@Override
	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	@Override
	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public boolean hasSetModifiedDate() {
		return _setModifiedDate;
	}

	@Override
	public void setModifiedDate(Date modifiedDate) {
		_setModifiedDate = true;

		_modifiedDate = modifiedDate;
	}

	@Override
	public String getReferenceKey() {
		if (_referenceKey == null) {
			return "";
		}
		else {
			return _referenceKey;
		}
	}

	@Override
	public void setReferenceKey(String referenceKey) {
		_referenceKey = referenceKey;
	}

	@Override
	public long getFileEntryId() {
		return _fileEntryId;
	}

	@Override
	public void setFileEntryId(long fileEntryId) {
		_columnBitmask |= FILEENTRYID_COLUMN_BITMASK;

		if (!_setOriginalFileEntryId) {
			_setOriginalFileEntryId = true;

			_originalFileEntryId = _fileEntryId;
		}

		_fileEntryId = fileEntryId;
	}

	public long getOriginalFileEntryId() {
		return _originalFileEntryId;
	}

	public long getColumnBitmask() {
		return _columnBitmask;
	}

	@Override
	public ExpandoBridge getExpandoBridge() {
		return ExpandoBridgeFactoryUtil.getExpandoBridge(getCompanyId(),
			DLOpenerFileEntryReference.class.getName(), getPrimaryKey());
	}

	@Override
	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		ExpandoBridge expandoBridge = getExpandoBridge();

		expandoBridge.setAttributes(serviceContext);
	}

	@Override
	public DLOpenerFileEntryReference toEscapedModel() {
		if (_escapedModel == null) {
			_escapedModel = (DLOpenerFileEntryReference)ProxyUtil.newProxyInstance(_classLoader,
					_escapedModelInterfaces, new AutoEscapeBeanHandler(this));
		}

		return _escapedModel;
	}

	@Override
	public Object clone() {
		DLOpenerFileEntryReferenceImpl dlOpenerFileEntryReferenceImpl = new DLOpenerFileEntryReferenceImpl();

		dlOpenerFileEntryReferenceImpl.setDlOpenerFileEntryReferenceId(getDlOpenerFileEntryReferenceId());
		dlOpenerFileEntryReferenceImpl.setGroupId(getGroupId());
		dlOpenerFileEntryReferenceImpl.setCompanyId(getCompanyId());
		dlOpenerFileEntryReferenceImpl.setUserId(getUserId());
		dlOpenerFileEntryReferenceImpl.setUserName(getUserName());
		dlOpenerFileEntryReferenceImpl.setCreateDate(getCreateDate());
		dlOpenerFileEntryReferenceImpl.setModifiedDate(getModifiedDate());
		dlOpenerFileEntryReferenceImpl.setReferenceKey(getReferenceKey());
		dlOpenerFileEntryReferenceImpl.setFileEntryId(getFileEntryId());

		dlOpenerFileEntryReferenceImpl.resetOriginalValues();

		return dlOpenerFileEntryReferenceImpl;
	}

	@Override
	public int compareTo(DLOpenerFileEntryReference dlOpenerFileEntryReference) {
		long primaryKey = dlOpenerFileEntryReference.getPrimaryKey();

		if (getPrimaryKey() < primaryKey) {
			return -1;
		}
		else if (getPrimaryKey() > primaryKey) {
			return 1;
		}
		else {
			return 0;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof DLOpenerFileEntryReference)) {
			return false;
		}

		DLOpenerFileEntryReference dlOpenerFileEntryReference = (DLOpenerFileEntryReference)obj;

		long primaryKey = dlOpenerFileEntryReference.getPrimaryKey();

		if (getPrimaryKey() == primaryKey) {
			return true;
		}
		else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return (int)getPrimaryKey();
	}

	@Override
	public boolean isEntityCacheEnabled() {
		return ENTITY_CACHE_ENABLED;
	}

	@Override
	public boolean isFinderCacheEnabled() {
		return FINDER_CACHE_ENABLED;
	}

	@Override
	public void resetOriginalValues() {
		DLOpenerFileEntryReferenceModelImpl dlOpenerFileEntryReferenceModelImpl = this;

		dlOpenerFileEntryReferenceModelImpl._setModifiedDate = false;

		dlOpenerFileEntryReferenceModelImpl._originalFileEntryId = dlOpenerFileEntryReferenceModelImpl._fileEntryId;

		dlOpenerFileEntryReferenceModelImpl._setOriginalFileEntryId = false;

		dlOpenerFileEntryReferenceModelImpl._columnBitmask = 0;
	}

	@Override
	public CacheModel<DLOpenerFileEntryReference> toCacheModel() {
		DLOpenerFileEntryReferenceCacheModel dlOpenerFileEntryReferenceCacheModel =
			new DLOpenerFileEntryReferenceCacheModel();

		dlOpenerFileEntryReferenceCacheModel.dlOpenerFileEntryReferenceId = getDlOpenerFileEntryReferenceId();

		dlOpenerFileEntryReferenceCacheModel.groupId = getGroupId();

		dlOpenerFileEntryReferenceCacheModel.companyId = getCompanyId();

		dlOpenerFileEntryReferenceCacheModel.userId = getUserId();

		dlOpenerFileEntryReferenceCacheModel.userName = getUserName();

		String userName = dlOpenerFileEntryReferenceCacheModel.userName;

		if ((userName != null) && (userName.length() == 0)) {
			dlOpenerFileEntryReferenceCacheModel.userName = null;
		}

		Date createDate = getCreateDate();

		if (createDate != null) {
			dlOpenerFileEntryReferenceCacheModel.createDate = createDate.getTime();
		}
		else {
			dlOpenerFileEntryReferenceCacheModel.createDate = Long.MIN_VALUE;
		}

		Date modifiedDate = getModifiedDate();

		if (modifiedDate != null) {
			dlOpenerFileEntryReferenceCacheModel.modifiedDate = modifiedDate.getTime();
		}
		else {
			dlOpenerFileEntryReferenceCacheModel.modifiedDate = Long.MIN_VALUE;
		}

		dlOpenerFileEntryReferenceCacheModel.referenceKey = getReferenceKey();

		String referenceKey = dlOpenerFileEntryReferenceCacheModel.referenceKey;

		if ((referenceKey != null) && (referenceKey.length() == 0)) {
			dlOpenerFileEntryReferenceCacheModel.referenceKey = null;
		}

		dlOpenerFileEntryReferenceCacheModel.fileEntryId = getFileEntryId();

		return dlOpenerFileEntryReferenceCacheModel;
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(19);

		sb.append("{dlOpenerFileEntryReferenceId=");
		sb.append(getDlOpenerFileEntryReferenceId());
		sb.append(", groupId=");
		sb.append(getGroupId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", referenceKey=");
		sb.append(getReferenceKey());
		sb.append(", fileEntryId=");
		sb.append(getFileEntryId());
		sb.append("}");

		return sb.toString();
	}

	@Override
	public String toXmlString() {
		StringBundler sb = new StringBundler(31);

		sb.append("<model><model-name>");
		sb.append(
			"com.liferay.document.library.opener.model.DLOpenerFileEntryReference");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>dlOpenerFileEntryReferenceId</column-name><column-value><![CDATA[");
		sb.append(getDlOpenerFileEntryReferenceId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>groupId</column-name><column-value><![CDATA[");
		sb.append(getGroupId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>referenceKey</column-name><column-value><![CDATA[");
		sb.append(getReferenceKey());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>fileEntryId</column-name><column-value><![CDATA[");
		sb.append(getFileEntryId());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private static final ClassLoader _classLoader = DLOpenerFileEntryReference.class.getClassLoader();
	private static final Class<?>[] _escapedModelInterfaces = new Class[] {
			DLOpenerFileEntryReference.class, ModelWrapper.class
		};
	private long _dlOpenerFileEntryReferenceId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private boolean _setModifiedDate;
	private String _referenceKey;
	private long _fileEntryId;
	private long _originalFileEntryId;
	private boolean _setOriginalFileEntryId;
	private long _columnBitmask;
	private DLOpenerFileEntryReference _escapedModel;
}