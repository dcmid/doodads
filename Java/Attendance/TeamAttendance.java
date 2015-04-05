//runs Attendance package
package Attendance;

import java.awt.Frame;
import javax.swing.JFrame;

public class TeamAttendance{

	public static void main(String[] args){
		openGUI();
	}

	private static void openGUI(){
		AttendanceGUI tuna = new AttendanceGUI();
		tuna.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		tuna.setSize(550,350);
		tuna.setUndecorated(false);
		tuna.setVisible(true);
	} 
}