/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.reporting2.api.impl;

import org.openmrs.api.APIException;
import org.openmrs.api.UserService;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.reporting2.Item;
import org.openmrs.module.reporting2.api.Reporting2Service;
import org.openmrs.module.reporting2.api.dao.Reporting2Dao;
import org.openmrs.module.reporting2.model.*;

import java.util.List;

public class Reporting2ServiceImpl extends BaseOpenmrsService implements Reporting2Service {
	
	private Reporting2Dao dao;
	
	private UserService userService;
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setDao(Reporting2Dao dao) {
		this.dao = dao;
	}
	
	/**
	 * Injected in moduleApplicationContext.xml
	 */
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	@Override
	public Item getItemByUuid(String uuid) throws APIException {
		return dao.getItemByUuid(uuid);
	}
	
	@Override
	public CategoryOption getCategoryOptionByUuid(String uuid) throws APIException {
		return null;
	}
	
	@Override
	public Item saveItem(Item item) throws APIException {
		if (item.getOwner() == null) {
			item.setOwner(userService.getUser(1));
		}
		
		return dao.saveItem(item);
	}
	
	@Override
	public CategoryOption saveCategoryOption(CategoryOption categoryOption) throws APIException {
		return dao.saveCategoryOption(categoryOption);
	}
	
	@Override
	public Category saveCategory(Category category) throws APIException {
		return dao.saveCategory(category);
	}
	
	@Override
	public CategoryCombination saveCategoryCombination(CategoryCombination categoryCombination) throws APIException {
		return dao.saveCategoryCombination(categoryCombination);
	}
	
	@Override
	public DataElement saveDataElement(DataElement dataElement) throws APIException {
		return dao.saveDataElement(dataElement);
	}
	
	@Override
	public DataElementGroup saveDataElementGroup(DataElementGroup dataElementGroup) throws APIException {
		return dao.saveDataElementGroup(dataElementGroup);
	}
	
	@Override
	public Indicator saveIndicator(Indicator indicator) throws APIException {
		return dao.saveIndicator(indicator);
	}
	
	@Override
	public DataSet saveDataSet(DataSet dataSet) throws APIException {
		return dao.saveDataSet(dataSet);
	}
	
	@Override
	public List<CategoryOption> getAllCategoryOptions() {
		return dao.getAllCategoryOptions();
	}
}
