package org.openmrs.module.reporting2.utils;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public final class Constants {
	
	public static final BasicNameValuePair PAGING = new BasicNameValuePair("paging", "false");
	
	public static final NameValuePair DATA_ELEMENT_PARAMS = new BasicNameValuePair("fields",
	        "id,name,categoryCombo,dataSetElements,dataElementGroups");
	
	public static final NameValuePair DATA_ELEMENT_GROUP_PARAMS = new BasicNameValuePair("fields",
	        "id,name,dataElements,groupSets");
	
	public static final NameValuePair DATA_ELEMENT_GROUP_SET_PARAMS = new BasicNameValuePair("fields",
	        "id,name,dataElementGroups,items");
	
	public static final NameValuePair CATEGORY_PARAMS = new BasicNameValuePair("fields",
	        "id,name,categoryCombos,categoryOptions");
	
	public static final NameValuePair CATEGORY_Combos_PARAMS = new BasicNameValuePair("fields",
	        "id,name,categories,categoryOptionCombos");
	
	public static final NameValuePair CATEGORY_OPTION_Combo_PARAMS = new BasicNameValuePair("fields",
	        "id,name,categoryCombo,categoryOptions,attributeValues");
	
	public static final NameValuePair CATEGORY_OPTION_PARAMS = new BasicNameValuePair("fields",
	        "id,name,categories,categoryOptionCombos");
	
	public static final NameValuePair CATEGORY_OPTION_GROUP_PARAMS = new BasicNameValuePair("fields",
	        "id,name,categoryOptions,groupSets");
	
	public static final NameValuePair CATEGORY_OPTION_GROUP_SET_PARAMS = new BasicNameValuePair("fields",
	        "id,name,categoryOptionGroups,items");
	
	public static final NameValuePair DAT_SET_PARAMS = new BasicNameValuePair("fields",
	        "id,name,legendSet,dataSetElements,legendSets");
	
	public static final NameValuePair INDICATOR_PARAMS = new BasicNameValuePair("fields",
	        "id,name,numerator,denominator,indicatorType],indicatorGroups");
	
	public static final NameValuePair INDICATOR_Type_PARAMS = new BasicNameValuePair("fields", "id,name");
	
	public static final NameValuePair INDICATOR_GROUP_PARAMS = new BasicNameValuePair("fields",
	        "id,name,indicatorGroupSet,indicators");
	
	public static final NameValuePair INDICATOR_GROUP_SET_PARAMS = new BasicNameValuePair("fields", "id,name");
	
	public static final NameValuePair PROGRAM_INDICATOR_PARAMS = new BasicNameValuePair("fields",
	        "id,name,program,programIndicatorGroups");
	
	public static final NameValuePair PROGRAM_INDICATOR_GROUP_PARAMS = new BasicNameValuePair("fields",
	        "id,name,programIndicators");
	
	public static final NameValuePair PROGRAM_PARAMS = new BasicNameValuePair("fields",
	        "id,name,programRuleVariables,programStages,programRules");
	
	public static final NameValuePair TRACKED_ENTITY_ATTRIBUTE_PARAMS = new BasicNameValuePair("fields",
	        "id,name,dimensionItem");
	
	public static final NameValuePair RELATIONSHIP_TYPE_PARAMS = new BasicNameValuePair("fields", "id,name");
	
	public static final NameValuePair TRACKED_ENTITY_PARAMS = new BasicNameValuePair("fields", "id,name");
	
}
