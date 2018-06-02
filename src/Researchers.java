import java.util.*;

/**
* <p>Title: Researchers</p>
* <p>Description: researcher store all the attributes of a researcher's details</p>
* <p>CSE210, XJTLU</p>
* @author Zhaorui Zhang
* @date 29 Apr 2018
*/

public class Researchers {
	private String name;
	private Interest interestsList = new Interest("Head");
	private String university;
	private String department;

	/**
	* <p>Title: Constructor</p>
	* <p>Description: Class researcher stores all the detail information
	*    of a researcher</p>
	* @param name
	* @param interestStr
	* @param university
	* @param department
	*/
	public Researchers(String name, String interestStr, String university, String department) {
		this.name = name;
		this.university = university;
		this.department = department;
		setInterestList(interestStr);
	}

	/**
	* <p>Title: setInterestList</p>
	* <p>Description: Store all the interests information in list</p>
	* @param interestStr
	*/
	private void setInterestList(String interestStr) {
		// split string with ',' then store into an array
		String arr[] = interestStr.split(", ");

		Interest head = interestsList;
		// build the list with the content in the array
		for (String str : arr) {
			Interest interest = new Interest(str);
			head.setNext(interest);
			head = head.getNext();
		}
	}

	/**
	* <p>Title: getName</p>
	* @return name
	*/
	public String getName() {
		return name;
	}

	/**
	* <p>Title: getUniversity</p>
	* @return university
	*/
	public String getUniversity() {
		return university;
	}

	/**
	* <p>Title: getDepartment</p>
	* @return department
	*/
	public String getDepartment() {
		return department;
	}

	/**
	* <p>Title: getInterests</p>
	* @return interestsList
	*/
	public Interest getInterests() {
		return interestsList;
	}

	public String getInterestContent() {
		Interest head = interestsList;
		StringBuffer sb = new StringBuffer();
		while (head.hasNext()) {
			sb.append(head.getNext().getContent() + ", ");
			head = head.getNext();
		}
		return sb.toString();
	}

}
