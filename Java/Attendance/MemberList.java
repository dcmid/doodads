//overwrites ArrayList toString method
package Attendance;

import java.util.ArrayList;
import java.io.Serializable;

public class MemberList extends ArrayList<Member>{
	public MemberList(){
		new ArrayList<Member>();
	}
	public String toString(){
		String string = "";
		if(this.size() > 0){
			for(int i = 0; i < this.size(); i++){
				string += (this.get(i).name + "\t Days Logged: " + this.get(i).daysWorked + "\t Hours Logged: " + this.get(i).hoursWorked + "\n");
			}
			
			return string;
		}
		return "";
	}
}