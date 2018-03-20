/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.reporting2.api;

import org.openmrs.annotation.Authorized;
import org.openmrs.api.APIException;
import org.openmrs.api.OpenmrsService;
import org.openmrs.module.reporting2.Item;
import org.openmrs.module.reporting2.Reporting2Config;
import org.openmrs.module.reporting2.model.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The main service of this module, which is exposed for other modules. See
 * moduleApplicationContext.xml on how it is wired up.
 */
public interface Reporting2Service extends OpenmrsService {
	
	/**
	 * Returns an item by uuid. It can be called by any authenticated user. It is fetched in read
	 * only transaction.
	 * 
	 * @param uuid
	 * @return
	 * @throws APIException
	 */
	@Authorized()
	@Transactional(readOnly = true)
	Item getItemByUuid(String uuid) throws APIException;
	
	@Authorized()
	@Transactional(readOnly = true)
	CategoryOption getCategoryOptionByUuid(String uuid) throws APIException;
	
	@Authorized(Reporting2Config.MODULE_PRIVILEGE)
	@Transactional
	Item saveItem(Item item) throws APIException;
	
	@Authorized(Reporting2Config.MODULE_PRIVILEGE)
	@Transactional
	CategoryOption saveCategoryOption(CategoryOption categoryOption) throws APIException;
	
	@Authorized(Reporting2Config.MODULE_PRIVILEGE)
	@Transactional
	Category saveCategory(Category category) throws APIException;
	
	@Authorized(Reporting2Config.MODULE_PRIVILEGE)
	@Transactional
	CategoryCombination saveCategoryCombination(CategoryCombination categoryCombination) throws APIException;
	
	@Authorized(Reporting2Config.MODULE_PRIVILEGE)
	@Transactional
	DataElement saveDataElement(DataElement dataElement) throws APIException;
	
	@Authorized(Reporting2Config.MODULE_PRIVILEGE)
	@Transactional
	DataElementGroup saveDataElementGroup(DataElementGroup dataElementGroup) throws APIException;
	
	@Authorized(Reporting2Config.MODULE_PRIVILEGE)
	@Transactional
	Indicator saveIndicator(Indicator indicator) throws APIException;
	
	@Authorized(Reporting2Config.MODULE_PRIVILEGE)
	@Transactional
	DataSet saveDataSet(DataSet dataSet) throws APIException;
	
	@Authorized({ "Get CategoryOptions" })
	List<CategoryOption> getAllCategoryOptions();
}
