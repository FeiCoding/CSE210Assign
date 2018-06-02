
/**
* <p>Title: DataProAndAna</p>
* <p>Description: Main class to start the whole program</p>
* <p>Company: www.izhangheng.com</p>
* @author zhangheng
* @date 29 Apr 2018
*/
public class DataProAndAna {

	public static void main(String[] args) {
		String filePath = "Dataset_RG.xlsx";
		MyInterface my = new MyInterface(filePath);
		my.startInterface();
	}
}

