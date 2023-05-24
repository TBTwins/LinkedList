// Turki Alqahtani, TALQAHTANI0094@stu.kau.edu.sa , 2236938, F1, Course Coordination System, 14/4/2023
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class MainProgram {

	public static void main(String[] args) throws FileNotFoundException {
		//Creating files
		File f1 = new File("C:\\Users\\Turki Alqahtani\\Desktop\\Collage\\Prog 3\\Assaign1\\Commands.txt");
		File f2 = new File("C:\\Users\\Turki Alqahtani\\Desktop\\Collage\\Prog 3\\Assaign1\\intialInformation.txt");
		File f3 = new File("C:\\Users\\Turki Alqahtani\\Desktop\\Collage\\Prog 3\\Assaign1\\output.txt");
		//check if exists the files before reading it
		if(!f1.exists()) {
			System.out.print("Commands file not found!");
			System.exit(0);
		}
		if(!f2.exists()) {
			System.out.print("Installinformation file not found!");
			System.exit(0);
		}
		//creating inputs and output
		Scanner input1 = new Scanner(f1);
		Scanner input2 = new Scanner(f2);
		PrintWriter output = new PrintWriter(f3);
		output.print("\t\t\t\tWelcome to Course Coordination System\n\t\t\t\t-----------------------------------------------------\n");
		//reading the important information to get the scope of it
		int num_of_inst = input2.nextInt();
		int num_of_class = input2.nextInt();
		int num_of_cord = input2.nextInt();
		String[] arraycla = new String[num_of_class];
		Course[] arrayobjcla = new Course[num_of_class];
		for(int i = 0 ;i < num_of_class ; i++ ) {
			arraycla[i] = input2.next();
		}
		int[] arraycod = new int[num_of_cord];
		for(int i = 0 ;i < num_of_cord ; i++ ) {
			arraycod[i] = input2.nextInt();
		}
		//here is important, lets say he puts 2 DISPLAY_COURSE_FOR_INSTRUCTOR, we don't want to print instructor information 2 times, just 1.
		int startprintcond=0;
		//infinite loop untill the word QUIT.
		while(true) {
			//command
			String command = input1.next();
			//======================================================================================
			if(command.equals("STARTUP")) {
				for(int i=0; i<num_of_class ; i++) {
					//so he gave us CPCS202, we want to split it to CPCS and 202. always the index 0 is course name and index 1 is course number
					String[] splitcodeandnum = arraycla[i].split("(?<=\\D)(?=\\d)");
					//here to turn String 202 to int 202.
					Integer codeclass = new Integer(splitcodeandnum[1]);
					//saving to course array.
					arrayobjcla[i] = new Course(splitcodeandnum[0], codeclass, null);
					//loop to save instructors, it's constant, every course has 5 instructors 
					for(int k=0 ;k<5 ; k++) {
						//inputs
						int code = input2.nextInt();
						String fname = input2.next();
						String lname = input2.next();
						Boolean iscord = false;
						//loop to check if the ID number matches to the IDs of the coordinator that we save into arrays earlier.
						for(int c= 0 ; c<num_of_cord ; c++) {
							if(code == arraycod[c]) {
								iscord = true;
								break;
							}
						}
						//now we add instructor.
						arrayobjcla[i].addInstructor(code, fname, lname, arraycla[i], iscord, null);
					}
				}
				//here to save the last lines of the input file to course variable in instructor class 
				while(input2.hasNext()) {
					//course name condition is CPCS204 Data Structure
					String CourseNameCond= input2.nextLine();
					//we want to take the first word to check if it is equals to the course varible that we saved which is cpcs204. if true change to CPCS204 Data Structure
					String[] firstcoursename = CourseNameCond.split("\\s+");
					//loop to check every instructor and changing the course to the data that we got.
					for(int i=0; i<num_of_class ; i++) {
						Instructors helpPtr = arrayobjcla[i].getHead();
						for(int k=0 ; k<5;k++) {
							if (helpPtr != null) {
								if(helpPtr.getCourse().equals(firstcoursename[0])) {
								helpPtr.setCourse(CourseNameCond);	
								}
								helpPtr = helpPtr.getNext();
							}
						}
					}
				}
			}
			//================================================================================================
			else if(command.equals("DISPLAY_ALL_COURSES")) {
				output.print("\n\n\n\t\t\tInformation of Instructors in Each Class\n");
				//loop for printing the linked list.
				for(int i = 0 ; i<arrayobjcla.length; i++) {
					output.print("\n\t\t\tInstructors in "+arrayobjcla[i].getCourseName()+arrayobjcla[i].getCourseNumber()+" Course\n");
					output.print("\tID\t\tName\t\t\tCoordinator\n");
					Instructors helpPtr = arrayobjcla[i].getHead();
					for(int k=0 ; k<5;k++) {
						if (helpPtr != null) {
							//we want to change the int ID from 1 , to string  3 digit ID 001 by formating it.
							String numberwith3digit = String.format("%03d", helpPtr.getID());
							//print format to print list in good shape.
							output.printf("\t%-15s %-20s %10s",numberwith3digit,helpPtr.getFname()+" "+helpPtr.getlName(),BooleanToString(helpPtr.getCoordinator())+"\n");
							helpPtr = helpPtr.getNext();
						}
					}
				}
			}
			//====================================================================================
			else if(command.equals("DISPLAY_COURSE_FOR_INSTRUCTOR")) {
				//here is important, we don't want to print instructor information twice.
				if(startprintcond ==0) {
					output.print("\n\n\n\t\t\tInstructor Information\n");
					//change the start print condition.
					startprintcond =1;
				}
				//input
				int id = input1.nextInt();
				//getting instructor object of the id given in searching method in main
				Instructors inst = searching(id, arrayobjcla); 
				//if it's not null that means he found it.
				if(inst != null) {
					//formating same before
					String numberwith3digit = String.format("%03d", inst.getID());
					//printing the instructor information
					output.print("\t\"Instructor: "+inst.getFname()+ " "+inst.getlName()+", " +numberwith3digit+", is assigned to "+inst.getCourse()+", coordinated by ");
					for(int i = 0 ; i<arrayobjcla.length;i++) {
						//we need to check it's the same course to get the head, which is the coordinator information.
						String[] firstcoursename = inst.getCourse().split("\\s+");
						if(firstcoursename[0].equals(arrayobjcla[i].getCourseName()+arrayobjcla[i].getCourseNumber())) {
							String numberwith3digitcord = String.format("%03d", arrayobjcla[i].getHead().getID());
							output.print(numberwith3digitcord+ " "+arrayobjcla[i].getHead().getFname() + " "+ arrayobjcla[i].getHead().getlName()+" \"\n");
						}
					}
				}
				//if instructor null, that means not found.
				else {
					output.print("\tNo instructor of this ID is found\n");
				}
			}
			//=========================================================================================
			else if(command.equals("NUM_OF_INSTRUCTORS")) {
				//same logic as before
				if(startprintcond ==1) {
					output.print("\n\n\n\t\t\tNumber of Instructors in Each Course\n");
					startprintcond= 2;
				}
				//input
				String course = input1.next();
				//loop to every course
				for(int i=0; i<arrayobjcla.length ; i++) {
					//if input is equals to course
					if((arrayobjcla[i].getCourseName() + arrayobjcla[i].getCourseNumber()).equals(course)) {
						//counting
						int count = 0;
						Instructors helpPtr = arrayobjcla[i].getHead();
						while(helpPtr!=null) {
							helpPtr = helpPtr.getNext();
							count++;
						}
						output.print("\tNumber of Instructors in "+ course+": "+count+"\n");
					}
				}
			}
			//========================================================================================
			else if(command.equals("DELETE_INSTRUCTOR")) {
				//same logic as before
				if(startprintcond ==2) {
					output.print("\n\n\n\t\t\tDelete Instructor\n");
					startprintcond= 3;
				}
				//input
				int id = input1.nextInt();
				for(int i = 0; i<arrayobjcla.length;i++) {
					//delete instructor method 
					String cond = arrayobjcla[i].deleteInstructorByID(id);
					// if the string not null, that means he found it.
					if(cond!=null) {
						output.print("\t"+cond+"\n");
					}
				}
			}
			//==========================================================================================
			else if (command.equals("SWAP_INSTRUCTOR")) {
				//same logic as before
				if(startprintcond ==3) {
					output.print("\n\n\n\t\t\tSwap Instructors\n");
					startprintcond= 4;
				}
				//inputs
				int id1 = input1.nextInt();
				int id2 = input1.nextInt();
				//string result, the array of objects course don't matter which one, because we will pass the array to the method to check in every linked list, in conclusion the answer will be the same if we use the method in other course object.
				String result = arrayobjcla[0].swapInstructorByID(id1, id2, arrayobjcla);
				//if not null means found it.
				if(result!= null) {
					output.print("\t"+result+"\n");
				}
			}
			//============================================================================================
			else if (command.equals("QUIT")) {
				output.print("\n\n\n\t============================\n\t\tBest Wishes\n\t============================\n");
				output.close();
				input2.close();
				input1.close();
				System.exit(0);
				
			}
			
			
		}
		
	}
	public static String BooleanToString (Boolean x) {
		if(x==true) {
			return "Yes";
		}
		else {
			return "No";
		}
	}
	//=======================================================================
	public static Instructors searching(int ID, Course[] arrayobjcla) {
		//we put in the method, because if we did it in main the scope of the instructor won't take because it's in if statement
		for(int i = 0 ; i<arrayobjcla.length ; i++) {
			//check in every course, in another method in course class.
			Instructors inst = arrayobjcla[i].searchInstrictorById(ID);
			// if it's not null that means he found it
			if(inst!=null) {
				return inst;
			}
		}
		return null;
	}

}
