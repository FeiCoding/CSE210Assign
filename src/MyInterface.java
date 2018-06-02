import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
* <p>Title: MyInterface</p>
* <p>Description: This class defined the UI for user to use to choose
* 	 which function to use</p>
* <p>CSE210, XJTLU</p>
* @author Zhaorui Zhang
* @date 29 Apr 2018
*/
public class MyInterface {
	private static ArrayList<Researchers> reaserchList = new ArrayList<Researchers>();
	private static HashMap<String, Researchers> map = new HashMap<String, Researchers>();

	// use hashset to store distinct topic so as to avoid duplicate
	private static HashSet<String> topicSet = new HashSet<String>();
	private static Scanner kb = new Scanner(System.in);

	/**
	* <p>Title: Constructor</p>
	* <p>Description: Read the excel file at first and then store these data into three
	* 	 kinds of container which are hash map, hash set and array list.</p>
	* @param filePath
	*/
	public MyInterface(String filePath) {
		try {
			readExcel(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	* <p>Title: startInterface</p>
	* <p>Description: State all the necessary information and achieve user's input
	* 	 to give responding with the funcion defined in class Function.</p>
	*/
	public static void startInterface() {
		System.out.println();
		System.out.println("Please Input the following number to get information you want:");
		System.out.println("1: Get the number of distinct researchers in the dataset");
		System.out.println("2: Get the number of distinct interests in the dataset");
		System.out.println("3: Show detailed information about a researcher according to the name");
		System.out.println("4: Calculate the number of researchers who have the same interest");
		System.out.println("5: Show the number of times two interest co-occur");
		System.out.println("6: Find similar researchers based on interests");
		System.out.println("0: Exit");

		int input = judgeInput();

		System.out.println("Your input is " + input);

		Functions function = new Functions(getMap(), getList(), getSet());
		switch (input) {
		case 0:
			System.out.println("Mission Done. Bye!");
			break;
		case 1:
			int num1 = function.getNumberResearchers();
			System.out.println("The number of distinct researchers is " + num1);
			jumpFunction();
			break;
		case 2:
			int num2 = function.getNumberTopics();
			System.out.println("The number of distinct interests is " + num2);
			jumpFunction();
			break;
		case 3:
			System.out.println("Please input the name you want to query:");
			Scanner kb2 = new Scanner(System.in);
			String name = kb2.nextLine();
			try {
				Researchers research = function.getResearchDetails(name);
				System.out.println("Details are as following: ");
				System.out.println("Name: " + research.getName());
				System.out.println("Department: " + research.getDepartment());
				System.out.println("University: " + research.getUniversity());
				System.out.println("Interests: " + research.getInterestContent());
			} catch (NullPointerException e) {
				System.out.println("No information found.");
			} finally {
				jumpFunction();
				break;
			}

		case 4:
			System.out.println("Please input the interest you want to know");
			Scanner kb4 = new Scanner(System.in);
			String interest = kb4.nextLine();
			int num3 = function.getNumberSameInterest(interest);
			System.out.println("The number of researchers has the same interests is " + num3);
			jumpFunction();
			break;
		case 5:
			Scanner kb5 = new Scanner(System.in);
			Scanner kb6 = new Scanner(System.in);
			System.out.println("Please input the first interest:");
			String first = kb5.nextLine();
			System.out.println("Please input the second interest:");
			String second = kb6.nextLine();
			int num4 = function.getCoOccurNumber(first, second);
			System.out.println("The number of two interests co-occour is " + num4);
			jumpFunction();
			break;
		case 6:
			Scanner kb7 = new Scanner(System.in);
			System.out.println("Please input the name you want to query:");
			String name2 = kb7.nextLine();
			Scanner kb8 = new Scanner(System.in);
			System.out.println("Please input the number of most similar researcher you want to get:");
			try {
				int num5 = kb8.nextInt();
				ArrayList<StringSimilarity> simiList = function.findSimilarInterestReasercher(name2);
				if (simiList != null) {
					System.out.println("The most " + num5 + " likely researcher are:");
					for (int i = 0; i < num5; i++) {
						System.out.println(i+1 + "\nName: " + simiList.get(i).getName() + "\nThe similarity is "
								+ simiList.get(i).getSimi());
					}
				}
			} catch (Exception e) {
				System.out.println("Wrong input, please input a number:");
				kb8.nextLine();
			}
			jumpFunction();
			break;
		}
	}

	/**
	* <p>Title: judgeInput</p>
	* <p>Description: Exception dealer to avoid wrong input from user</p>
	* @return legal input from user
	*/
	public static int judgeInput() {
		int input = -1;
		while (true) {
			try {
				input = kb.nextInt();
				if (input < 0 || input > 6) {
					throw new WrongInputException();
				}
				return input;
			} catch (Exception e) {
				System.out.println("Wrong input, please input 0 to 6:");
				kb.nextLine();
			}
		}
	}

	/**
	* <p>Title: jumpFunction</p>
	* <p>Description: Once user finished one input, give user two options to jump back
	*    or finish whole program</p>
	*/
	public static void jumpFunction() {
		System.out.println("\nPress 1 to continue, 0 to exit...");
		int input = -1;
		while (true) {
			try {
				input = kb.nextInt();
				if (input != 0 && input != 1) {
					throw new WrongInputException();
				}
				break;
			} catch (Exception e) {
				System.out.println("Wrong input, please input 0 or 1:");
				kb.nextLine();
			}
		}
		if (input == 1)
			startInterface();
		else
			System.out.println("Mission Done. Bye!");

	}

	/**
	* <p>Title: readExcel</p>
	* <p>Description: Read excel file and get required information including
	*    name, uni, department, interests</p>
	* @param filePath
	* @throws IOException
	*/
	private static void readExcel(String filePath) throws IOException {

		FileInputStream fileInputStream = new FileInputStream(filePath);
		System.out.println("File Reading Start...");
		XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
		XSSFSheet sheet = workbook.getSheet("Sheet1");

		int lastRowIndex = sheet.getLastRowNum();
		int[] cellIndex = { 0, 1, 2, 10, 11 };

		for (int i = 1; i <= lastRowIndex; i++) {
			XSSFRow row = sheet.getRow(i);
			if (row == null) {
				break;
			}
			containerFormulator(row, cellIndex, map, reaserchList, topicSet);

		}
		System.out.println("File Reading Finished.");

	}

	/**
	* <p>Title: containerFormulator</p>
	* <p>Description: Form three kinds of container which are hash map for quickly finding details according name,
	*    array list to store researcher whole information and hash set to store topic without duplicate</p>
	* @param row
	* @param cellIndex
	* @param map
	* @param reaserchList
	* @param topicSet
	*/
	private static void containerFormulator(XSSFRow row, int cellIndex[], HashMap<String, Researchers> map,
			ArrayList<Researchers> reaserchList, HashSet<String> topicSet) {
		int j = 0;
		String uni = formatterStr(row.getCell(cellIndex[j]));
		String depart = formatterStr(row.getCell(cellIndex[j + 1]));
		String name = formatterStr(row.getCell(cellIndex[j + 2]));

		// avoid producing another string object
		StringBuffer sb = new StringBuffer();
		sb.append(formatterStr(row.getCell(cellIndex[j + 3])));
		sb.append(", ");
		sb.append(formatterStr(row.getCell(cellIndex[j + 4])));

		Researchers researcher = new Researchers(name, sb.toString(), uni, depart);

		reaserchList.add(researcher);
		map.put(name, researcher);

		String[] strs = sb.toString().split(", ");
		for (String str : strs)
			topicSet.add(str);
	}

	/**
	* <p>Title: formatterStr</p>
	* <p>Description: transform excel information into type string</p>
	* @param xssfCell
	* @return cell value in string
	*/
	private static String formatterStr(XSSFCell xssfCell) {
		DataFormatter formatter = new DataFormatter();
		String val = formatter.formatCellValue(xssfCell);
		return val;
	}

	/**
	* <p>Title: getMap</p>
	* @return map
	*/
	public static HashMap<String, Researchers> getMap() {
		return map;
	}

	/**
	* <p>Title: getList</p>
	* @return reaserchList
	*/
	public static ArrayList<Researchers> getList() {
		return reaserchList;
	}

	/**
	* <p>Title: getSet</p>
	* @return topicSet
	*/
	public static HashSet<String> getSet() {
		return topicSet;
	}
}
