/**
 * This Source Code Form is subject to the terms of the Mozilla Public License,
 * v. 2.0. If a copy of the MPL was not distributed with this file, You can
 * obtain one at http://mozilla.org/MPL/2.0/. OpenMRS is also distributed under
 * the terms of the Healthcare Disclaimer located at http://openmrs.org/license.
 * <p>
 * Copyright (C) OpenMRS Inc. OpenMRS is a registered trademark and the OpenMRS
 * graphic logo is a trademark of OpenMRS Inc.
 */
package org.openmrs.module.reporting2.model;

import java.util.Set;

public class DataSet {
	
	private Integer id;
	private String name;
	private Set<DataElement> dataElements;
	private Set<Indicator> indicators;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Set<DataElement> getDataElements() {
		return dataElements;
	}
	
	public void setDataElements(Set<DataElement> dataElements) {
		this.dataElements = dataElements;
	}
	
	public Set<Indicator> getIndicators() {
		return indicators;
	}
	
	public void setIndicators(Set<Indicator> indicators) {
		this.indicators = indicators;
	}
}
