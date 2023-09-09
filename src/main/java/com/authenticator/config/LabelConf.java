/**
 * 
 */
package com.authenticator.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.hibernate.model.ErrorKeyLabel;

/**
 * @author sagar.jadhav
 *
 */
@Service
public class LabelConf {

	private Map<String, Map<String, String>> lableMap = new HashMap();

	public void fillLabelMap(List<ErrorKeyLabel> errorKeyLables) {
		for (ErrorKeyLabel errorKeyLabel : errorKeyLables) {
			if (lableMap.containsKey(errorKeyLabel.getLanguageCode())) {
				Map<String, String> actualLabelMap = lableMap.get(errorKeyLabel.getLanguageCode());
				actualLabelMap.put(errorKeyLabel.getErrorCode(), errorKeyLabel.getErrorKeyLable());
			} else {
				Map<String, String> actualLabelMap = new HashMap<>();
				actualLabelMap.put(errorKeyLabel.getErrorCode(), errorKeyLabel.getErrorKeyLable());
				lableMap.put(errorKeyLabel.getLanguageCode(), actualLabelMap);
			}
		}
	}

	public String getLabelKeyValue(String languageCode, String key) {
		if (lableMap.get(languageCode) != null) {
			if (lableMap.get(languageCode).get(key) != null) {
				return lableMap.get(languageCode).get(key);
			} else {
				return key;
			}
		} else {
			return key;
		}
	}
}
