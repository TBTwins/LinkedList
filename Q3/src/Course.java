// Turki Alqahtani, TALQAHTANI0094@stu.kau.edu.sa , 2236938, F1, Course Coordination System, 14/4/2023
public class Course {
	private String courseName;
	private int courseNumber;
	public Instructors head;
	
	//=============================================================================
	public Course(String courseName, int courseNumber, Instructors head) {
		this.courseName = courseName;
		this.courseNumber = courseNumber;
		this.head = null;
	}
	//=============================================================================
	void addInstructor(int iD, String fname, String lName, String course, Boolean coordinator, Instructors next){
		//if head null, instructor will add in the head
		if(head==null) {
			head=new Instructors(iD, fname, lName, course, coordinator, next);
		}
		//else, instructor will add in the end of the linked list
		else {
			Instructors HelpPtr = head;
			while(HelpPtr.getNext()!=null) {			
				HelpPtr= HelpPtr.getNext();
			}
			HelpPtr.setNext(new Instructors(iD, fname, lName, course, coordinator, next));
		}
	}
	//=============================================================================
	Instructors searchInstrictorById(int ID) {
		Instructors HelpPtr = head;	
		while(HelpPtr!=null) {
			//if true, means found it.
			if(HelpPtr.getID()==ID) {
				return HelpPtr;
			}
			HelpPtr = HelpPtr.getNext();
		}
		return null;
		
	}
	//=============================================================================
	String deleteInstructorByID(int ID) {
		//id equals to head id that means it's coordinator. and you can't delete the coordinator.
		if(head.getID()==ID) {
			return "You cannot delete a coordinator";
		}
		else {
			//search in linkedlist
			Instructors HelpPtr = head;
			//loop
			while(HelpPtr.getNext()!=null) {
				//if statement id equality
				if(HelpPtr.getNext().getID()==ID) {
					//we take the name of the instructor before deleting him
					String name = HelpPtr.getNext().getFname()+" "+ HelpPtr.getNext().getlName();
					//deleting the instructor
					HelpPtr.setNext(HelpPtr.getNext().getNext());
					//return
					return courseName+courseNumber+" is not assigned to "+ name;
				}
				HelpPtr = HelpPtr.getNext();
			}
			//null, that means he didn't find it. like input id not exists.
			return null;
		}
		
	}
	//=============================================================================
	String swapInstructorByID(int ID1, int ID2, Course[] arrayobjcla) {
		// to swap we need to find the instructor object based on his id.
		Instructors temp1= null;
		Instructors temp2= null;
		//we need to get the index of which course he is in.
		int index1 = 0;
		int index2 = 0;
		//for loop to search
		for(int i= 0 ; i<arrayobjcla.length ; i++) {
			//method to return the instructor
			temp1 = arrayobjcla[i].searchInstrictorById(ID1);
			//if it's not null that means he found it. we just take the index
			if(temp1!=null) {
				index1= i;
				break;
			}
		}
		//same logic as before, in temp2
		for(int i= 0 ; i<arrayobjcla.length ; i++) {
			temp2 = arrayobjcla[i].searchInstrictorById(ID2);
			if(temp2!=null) {
				index2= i;
				break;
			}
		}
		//two helpptr the first one for the temp1 to swap, and so on.
		Instructors helpPtr1 = arrayobjcla[index1].getHead();
		Instructors helpPtr2 = arrayobjcla[index2].getHead();
		//loop
		while(helpPtr1.getNext()!= null && helpPtr2.getNext()!= null) {
			//check, we can't change between cord and instructor but we can if both are coordinators. that means we use XOR.
			if((helpPtr1==temp1 && helpPtr2!=temp2) || (helpPtr1!=temp1 && helpPtr2==temp2)) {
				//this if to print the coordinator first and instructor second.
				if(temp1.getCoordinator()==true) {
					return "Coordinator "+temp1.getFname()+" "+temp1.getlName()+" can not be swapped with instructor " +temp2.getFname()+" "+temp2.getlName();
				}
				else {
					return "Coordinator "+temp2.getFname()+" "+temp2.getlName()+" can not be swapped with instructor " +temp1.getFname()+" "+temp1.getlName();
				}
			}
			//else means it's swappable.
			else {
				//this if to check if both are coordinators
				if(helpPtr1==temp1 && helpPtr2==temp2) {
					//changing the head
					Instructors temp1next = temp1.getNext();
					Instructors temp2next = temp2.getNext();
					temp2.setNext(temp1next);
					temp1.setNext(temp2next);
					arrayobjcla[index1].setHead(temp2);
					arrayobjcla[index2].setHead(temp1);
					
					return "\""+temp1.getFname()+" "+temp1.getlName()+" is now in "+arrayobjcla[index2].getCourseName()+ arrayobjcla[index2].getCourseNumber()+" , "+ temp2.getFname()+" "+temp2.getlName()+" is now in "+arrayobjcla[index1].getCourseName()+ arrayobjcla[index1].getCourseNumber();
				}
				else {
					//need to get the next of the temp and the prev of the temp to swap.
					Instructors temp1next = temp1.getNext();
					Instructors temp2next = temp2.getNext();
					Instructors temp1prev = null;
					Instructors temp2prev = null;
					//loop to get the temp1 previous.
					while(helpPtr1.getNext()!= null) {
						if(helpPtr1.getNext()== temp1) {
							temp1prev = helpPtr1;
							
							break;
						}
						helpPtr1 = helpPtr1.getNext();
						
					}
					//same logic for temp2prev
					while(helpPtr2.getNext()!=null) {
						if(helpPtr2.getNext()== temp2) {
							temp2prev= helpPtr2;
						}
						helpPtr2 = helpPtr2.getNext();
					}
					//swapping
					temp2.setNext(temp1next);
					temp1prev.setNext(temp2);
					temp1.setNext(temp2next);
					temp2prev.setNext(temp1);
					
					return "\""+temp1.getFname()+" "+temp1.getlName()+" is now in "+arrayobjcla[index2].getCourseName()+ arrayobjcla[index2].getCourseNumber()+" , "+ temp2.getFname()+" "+temp2.getlName()+" is now in "+arrayobjcla[index1].getCourseName()+ arrayobjcla[index1].getCourseNumber();
					
				}
			}
		}
		//null means didn't find
		return null;
	}
	//=============================================================================
	public Instructors getHead() {
		return head;
	}
	public void setHead(Instructors h) {
		head=h;
	}
	public String getCourseName() {
		return courseName;
	}
	public int getCourseNumber() {
		return courseNumber;
	}
	
}
