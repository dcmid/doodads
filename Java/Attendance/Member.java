//class to store name and work data for each team member
package Attendance;

import java.sql.Date;
import java.io.Serializable;

public class Member implements Serializable{
	public String name;
	public long timeIn = -10;
	public long timeOut = -9;
	public long dateOut = timeOut;
	public int daysWorked = 0;
	public double timeWorked;
	public double timeWorkedTotal = 0;
	public double hoursWorked;
	public int signedInMembersIndex;

	public Member(String namec){
		name = namec;
	}
	
	
	
}