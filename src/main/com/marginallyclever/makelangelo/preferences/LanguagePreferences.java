package com.marginallyclever.makelangelo.preferences;

import javax.swing.JOptionPane;

import com.marginallyclever.core.Translator;
import com.marginallyclever.core.select.SelectOneOfMany;
import com.marginallyclever.core.select.SelectPanel;
import com.marginallyclever.core.select.SelectReadOnlyText;

public class LanguagePreferences {
	static private SelectPanel panel;
	static private String[] languageList;
	static private SelectOneOfMany languageOptions;
	
	
	static SelectPanel buildPanel() {
		panel = new SelectPanel();
		
		languageList = Translator.getLanguageList();
		int currentIndex = Translator.getCurrentLanguageIndex();
		languageOptions = new SelectOneOfMany(Translator.get("LanguagePreferences.language"),languageList,currentIndex);
		
		panel.add(languageOptions);
		
		panel.add(new SelectReadOnlyText(Translator.get("LanguagePreferences.notice")));
		
		panel.finish();

		return panel;
	}
	
	static public void save() {}
	
	static public void cancel() {}


	/**
	 * Display a dialog box of available languages and let the user select their preference.
	 * TODO when language changes, restart app OR replace all strings with new language.
	 */
	static public void chooseLanguage() {
		SelectPanel panel = buildPanel();

		int result;
		do {
			result = JOptionPane.showConfirmDialog(null, panel, Translator.get("LanguagePreferences.language"), JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE);
		} while(result != JOptionPane.OK_OPTION);
		
		Translator.setCurrentLanguage(languageList[languageOptions.getSelectedIndex()]);
		Translator.saveConfig();
	}
}
