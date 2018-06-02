import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;


/**
* <p>Title: Functions</p>
* <p>Description: This class defined all the 6 functions according to
* 	 task 3</p>
* <p>cse210, XJTLU</p>
* @author Zhaorui Zhang
* @date 29 Apr 2018
*/

public class Functions {


	private HashMap<String, Researchers> map;
	private ArrayList<Researchers> reaserchList;
	private HashSet<String> topicSet;

	/**
	* <p>Title: Constructor</p>
	* <p>Description: get the parameters formed from the Excel file
	*    stored in three kinds of data structures which are hash map, array list and hash set</p>
	* @param map
	* @param reaserchList
	* @param topicSet
	*/
	public Functions(HashMap<String, Researchers> map, ArrayList<Researchers> reaserchList, HashSet<String> topicSet) {
		this.map = map;
		this.reaserchList = reaserchList;
		this.topicSet = topicSet;
	}
	
	/**
	* @return size of array list
	*/
	public int getNumberResearchers() {
		return reaserchList.size();
	}
	
	/**
	* @return size of hash set
	*/
	public int getNumberTopics() {
		return topicSet.size();
	}

	/**
	* <p>Title: getResearchDetails</p>
	* <p>Description: hash map can quickly find value according to the key,
	* 	 here we set name as key and information in details as value </p>
	* @param name
	* @return Researchers
	*/
	public Researchers getResearchDetails(String name) {
		return map.get(name);
	}

	/**
	* <p>Title: getNumberSameInterest</p>
	* <p>Description: Iterate the whole list to get find whether the 
	* 	 query interest exist</p>
	* @param interest
	* @return number
	*/
	public int getNumberSameInterest(String interest) {
		int count = 0;
		for (Researchers research : reaserchList) {
			Interest head = research.getInterests();
			while (head.hasNext()) {
				if (head.getNext().hasContent(interest)) {
					count++;
					break;
				} else
					head = head.getNext();
			}
		}
		return count;
	}

	/**
	* <p>Title: getCoOccurNumber</p>
	* <p>Description: Use two for loop to check whether two interest exist at the 
	* 	 same time.</p>
	* @param first
	* @param second
	* @return number of co-occour
	*/
	public int getCoOccurNumber(String first, String second) {
		int count = 0;
		for (Researchers research : reaserchList) {
			Interest head1 = research.getInterests();
			Interest head2 = research.getInterests();
			while (head1.hasNext()) {
				if (head1.getNext().hasContent(first)) {
					while (head2.hasNext()) {
						if (head2.getNext().hasContent(second)) {
							count++;
							break;
						}
						head2 = head2.getNext();
					}
					break;
				} else
					head1 = head1.getNext();
			}
		}
		return count;
	}

	/**
	* <p>Title: findSimilarInterestReasercher</p>
	* <p>Description: Use Levenshtein distance algorithm to check two
	* 	 interest string how much similar, then put all the comparison similarity
	* 	 into a list, then sort it in decent</p>
	* @param name
	* @return List of similarity
	*/
	public ArrayList<StringSimilarity> findSimilarInterestReasercher(String name) {
		try {
			Researchers researcher = getResearchDetails(name);
			String interest = researcher.getInterestContent();
			ArrayList<StringSimilarity> simiList = new ArrayList<StringSimilarity>();
			for (Researchers comparedResearcher : reaserchList) {
				if (!comparedResearcher.getName().equals(researcher.getName())) {
					String comparedInterest = comparedResearcher.getInterestContent();
					StringSimilarity similarity = new StringSimilarity(comparedResearcher.getName());
					similarity.similarity(interest, comparedInterest);
					simiList.add(similarity);
				}
			}
			
			Comparator c = new Comparator<StringSimilarity>() {
				public int compare(StringSimilarity s1, StringSimilarity s2) {
					if (s1.getSimi() < s2.getSimi())
						return 1;
					else
						return -1;
				}
			};
			simiList.sort(c);
			return simiList;
		} catch (NullPointerException e) {
			System.out.println("No information found.");
			return null;
		}

	}

}
