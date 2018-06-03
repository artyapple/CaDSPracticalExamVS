package cads.impl.rpc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplateFiller {

	private String text;
	private String templateVariableSign;

	/**
	 * @param text The original text being filled.
	 * @param templateVariableSign Sign(s) defining the start and end of template variables.
	 */
	public TemplateFiller(String text, String templateVariableSign) {
		this.text = text;
		this.templateVariableSign = templateVariableSign;
	}

	/**
	 * Fills all matched template variables in the original text with the
	 * specified text.
	 */
	public TemplateFiller fillTemplate(String templateVariable, String textToFill) {
		Pattern pattern = Pattern.compile(templateVariableSign + templateVariable + templateVariableSign);
		Matcher matcher = pattern.matcher(text);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			matcher.appendReplacement(sb, textToFill);
		}
		matcher.appendTail(sb);
		text = sb.toString();
		return this;
	}

	public String getResult() {
		return text;
	}
}
