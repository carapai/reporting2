/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.reporting2.api.dao;

import org.hibernate.criterion.Restrictions;
import org.openmrs.api.db.hibernate.DbSession;
import org.openmrs.api.db.hibernate.DbSessionFactory;
import org.openmrs.module.reporting2.Item;
import org.openmrs.module.reporting2.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("reporting2.Reporting2Dao")
public class Reporting2Dao {
	
	@Autowired
	DbSessionFactory sessionFactory;
	
	private DbSession getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public Item getItemByUuid(String uuid) {
		return (Item) getSession().createCriteria(Item.class).add(Restrictions.eq("uuid", uuid)).uniqueResult();
	}
	
	public CategoryOption getCategoryOptionByUuid(String uuid) {
		return (CategoryOption) getSession().createCriteria(CategoryOption.class).add(Restrictions.eq("uuid", uuid))
		        .uniqueResult();
	}
	
	public Item saveItem(Item item) {
		getSession().saveOrUpdate(item);
		return item;
	}
	
	public CategoryOption saveCategoryOption(CategoryOption categoryOption) {
		getSession().saveOrUpdate(categoryOption);
		return categoryOption;
	}
	
	public Category saveCategory(Category category) {
		getSession().saveOrUpdate(category);
		return category;
	}
	
	public CategoryCombination saveCategoryCombination(CategoryCombination categoryCombination) {
		getSession().saveOrUpdate(categoryCombination);
		return categoryCombination;
	}
	
	public DataElement saveDataElement(DataElement dataElement) {
		getSession().saveOrUpdate(dataElement);
		return dataElement;
	}
	
	public DataElementGroup saveDataElementGroup(DataElementGroup dataElementGroup) {
		getSession().saveOrUpdate(dataElementGroup);
		return dataElementGroup;
	}
	
	public Indicator saveIndicator(Indicator indicator) {
		getSession().saveOrUpdate(indicator);
		return indicator;
	}
	
	public DataSet saveDataSet(DataSet dataSet) {
		getSession().saveOrUpdate(dataSet);
		return dataSet;
	}
	
	public List<CategoryOption> getAllCategoryOptions() {
		return this.sessionFactory.getCurrentSession().createQuery("from reporting2.CategoryOption").list();
	}
}
