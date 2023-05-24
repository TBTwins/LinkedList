// Turki Alqahtani, TALQAHTANI0094@stu.kau.edu.sa , 2236938, F1, Course Coordination System, 14/4/2023
public class Instructors {
	public int ID;
	private String Fname;
	private String lName;
	private String Course;
	private Boolean Coordinator;
	public Instructors next;
	//=============================================================================
	public Instructors(int iD, String fname, String lName, String course, Boolean coordinator, Instructors next) {
		ID = iD;
		Fname = fname;
		this.lName = lName;
		Course = course;
		Coordinator = coordinator;
		this.next = next;
	}
	//=============================================================================
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getFname() {
		return Fname;
	}
	public void setFname(String fname) {
		Fname = fname;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getCourse() {
		return Course;
	}
	public void setCourse(String course) {
		Course = course;
	}
	public Boolean getCoordinator() {
		return Coordinator;
	}
	public void setCoordinator(Boolean coordinator) {
		Coordinator = coordinator;
	}
	public Instructors getNext() {
		return next;
	}
	public void setNext(Instructors next) {
		this.next = next;
	}
}
