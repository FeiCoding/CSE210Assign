
/**
* <p>Title: Interest</p>
* <p>Description: Interest class is a linked node to store whole interest information</p>
* <p>CSE210, XJTLU</p>
* @author Zhaorui Zhang
* @date 29 Apr 2018
*/
public class Interest {
	private String interest;
	private Interest next;
	
	/**
	* <p>Title: constructor</p>
	* <p>Description: receive interest in string type</p>
	* @param interest
	*/
	public Interest(String interest) {
		this.interest = interest;
		next = null;
	}
	
	/**
	* <p>Title: getContent</p>
	* <p>Description: return the content of the whole interest list</p>
	* @return
	*/
	public String getContent(){
		return interest;
	}
	
	/**
	* <p>Title: getNext</p>
	* <p>Description: get next node</p>
	* @return next
	*/
	public Interest getNext(){
		return next;
	}
	
	/**
	* <p>Title: setContent</p>
	* @param data
	*/
	public void setContent(String data){
		interest = data;
	}
	
	/**
	* <p>Title: setNext</p>
	* @param interest
	*/
	public void setNext(Interest interest){
		next = interest;
	}
	
	/**
	* <p>Title: hasNext</p>
	* <p>Description: judge whether there is next node</p>
	* @return boolean
	*/
	public boolean hasNext(){
		if(next != null)
			return true;
		else return false;
	}
	
	/**
	* <p>Title: hasContent</p>
	* <p>Description: judge whether has the content user want to query</p>
	* @param content
	* @return boolean
	*/
	public boolean hasContent(String content){
		if(content.equals(interest))
			return true;
		else return false;
	}

}
