package com.dhemery.gibberizer;

import java.util.EnumMap;
import java.util.List;

public class StringJoiner {
	enum JoinStyle { SPACE, LINE_BREAK, TWO_LINE_BREAKS };

	private static final EnumMap<JoinStyle, String> delimiters = createEnumMap();

	private static EnumMap<JoinStyle, String> createEnumMap() {
		EnumMap<JoinStyle, String> map = new EnumMap<JoinStyle, String>(JoinStyle.class);
		map.put(JoinStyle.SPACE, " ");
		map.put(JoinStyle.LINE_BREAK, "\n");
		map.put(JoinStyle.TWO_LINE_BREAKS, "\n\n");
		return map;
	}
	
	public String combine(List<String> strings, JoinStyle joinStyle) {
		if (strings.size() == 0)
		{
			makeWarning(strings);
			joinStyle = JoinStyle.LINE_BREAK;
		}

		String delimiter = delimiters.get(joinStyle);

		String combinedStrings = strings.get(0);

		for (String string : strings.subList(1, strings.size())) {
			combinedStrings += delimiter + string;
		}
		return combinedStrings;
	}

	private void makeWarning(List<String> strings) {
		strings.add("*************************************************");
		strings.add("Warning:  Could not generate acceptable gibberish.");
		strings.add("Some things you can try:");
		strings.add("   - Relax the filter.");
		strings.add("   - Adjust familiarity.");
		strings.add("   - Increase persistence.");
		strings.add("   - Increase the number of strings to generate.");
		strings.add("   - Run again with the same parameters.");
		strings.add("*************************************************");
	}
}
