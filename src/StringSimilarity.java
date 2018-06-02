/**
* <p>Title: StringSimilarity</p>
* <p>Description: implement the similarity function according to the
* 	 algorithm of Levenshtein distance algorithm</p>
* <p>CSE210, XJTLU</p>
* @author Zhaorui Zhang
* @date 29 Apr 2018
*/

public class StringSimilarity {

	/**
	 * Calculates the similarity (a number within 0 and 1) between two strings.
	 */
	private String name;
	private double simi = 0;

	/**
	* <p>Title: constructor</p>
	* @param name
	*/
	public StringSimilarity(String name) {
		this.name = name;
	}

	/**
	* <p>Title: getSimi</p>
	* @return similarity between two interests
	*/
	public double getSimi() {
		return simi;
	}

	/**
	* <p>Title: getName</p>
	* @return name of used to compare
	*/
	public String getName() {
		return name;
	}

	/**
	* <p>Title: similarity</p>
	* <p>Description: reform two string to check their similarity 
	*    with Levenshtein distance algorithm</p>
	* @param s1
	* @param s2
	*/
	public void similarity(String s1, String s2) {
		String longer = s1, shorter = s2;
		if (s1.length() < s2.length()) { // longer should always have greater
											// length
			longer = s2;
			shorter = s1;
		}
		int longerLength = longer.length();
		if (longerLength == 0) {
			simi = 1.0; /* both strings are zero length */
		}

		/*
		 * // If you have Apache Commons Text, you can use it to calculate the
		 * edit distance: LevenshteinDistance levenshteinDistance = new
		 * LevenshteinDistance(); return (longerLength -
		 * levenshteinDistance.apply(longer, shorter)) / (double) longerLength;
		 */

		simi = (longerLength - editDistance(longer, shorter)) / (double) longerLength;
	}

	// Example implementation of the Levenshtein Edit Distance
	// See http://rosettacode.org/wiki/Levenshtein_distance#Java
	/**
	* <p>Title: editDistance</p>
	* <p>Description: implement the algorithm of Levenshtein distance, which was used
	*    to calculate the least times to transform one string to another</p>
	* @param s1
	* @param s2
	* @return similarity
	*/
	public int editDistance(String s1, String s2) {
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();

		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++) {
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++) {
				if (i == 0)
					costs[j] = j;
				else {
					if (j > 0) {
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1))
							newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0)
				costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}
}
