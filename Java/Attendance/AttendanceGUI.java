//Contains code for Attendance Package GUI, including user sign in and sign out
package Attendance;

import java.util.Calendar;
import java.util.ArrayList;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.sql.Date;
import javax.swing.*;
import java.io.*;

public class AttendanceGUI extends JFrame{	
	File savedMemberList = new File("Attendance\\memberList.txt");
	File attendanceSummary = new File("Attendance\\Attendance Summary.txt");
	MemberList memberList = this.getMemberList();
	MemberList signedInMembers = new MemberList();
	private JTextField textField0;
	private JTextArea names;
	Calendar cal;
	GridBagConstraints gbc = new GridBagConstraints();
	JButton signInButton;
	JButton signOutButton;

	public AttendanceGUI(){
		//sets JFrame title and layout
		super("IronDogz Attendance");
		setLayout(new GridBagLayout());

		gbc.anchor = GridBagConstraints.PAGE_END;
		gbc.weighty = .1;
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 0;
		add(new JLabel("Enter your first and last name."), gbc);

		//adds text field to GUI
		textField0 = new JTextField(20);
		gbc.anchor = GridBagConstraints.PAGE_END;
		gbc.weighty = 0.2;
		gbc.gridwidth = 2;
		gbc.gridx = 1;
		gbc.gridy = 1;
		add(textField0, gbc);

		signInButton = new JButton("Sign In");
		gbc.anchor = GridBagConstraints.FIRST_LINE_END;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.ipady = 30;
		gbc.weighty = 0;
		gbc.weightx = 0.5;
		gbc.gridwidth = 1;
		gbc.gridx = 1;
		gbc.gridy = 2;
		add(signInButton, gbc);

		signOutButton = new JButton("Sign Out");
		gbc.anchor = GridBagConstraints.FIRST_LINE_START;
		gbc.gridx = 2;
		gbc.gridy = 2;
		add(signOutButton, gbc);

		names = new JTextArea();
		names.setLineWrap(true);
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.ipady = 0;
		gbc.weightx = 0;
		gbc.weighty = 0.5;
		gbc.gridwidth = 2;
		gbc.gridheight = 3;
		gbc.gridx = 1;
		gbc.gridy = 3;
		add(names, gbc);

		//creates an Action Listener
		MyHandler listener = new MyHandler();
		textField0.addActionListener(listener);
		signInButton.addActionListener(listener);
		signOutButton.addActionListener(listener);
	}

	//This is what happens when the Action Listener is tripped by an event (pressing enter)
	private class MyHandler implements ActionListener{
		public void actionPerformed(ActionEvent event){

			//If enter is pressed from in the text box, signIn is run with the text from the box
			if(event.getActionCommand().equals("TERMINATE")){
				System.exit(0);
			}
			if((event.getSource() == textField0)||(event.getSource() == signInButton)){
				//exits program when TERMINATE is entered
				signIn(textField0.getText().toUpperCase());
			}
			else if(event.getSource() == signOutButton){
				signOut(textField0.getText().toUpperCase());
			}
		}
	}

	//creates a new member if they do not already exist
	//adds user to signedInMembers
	//saves timeIn for later use in calculating total work time
	private void signIn(String namea){
		boolean memberExists = false;
		Member currentMember = new Member("placeholder");
		cal = Calendar.getInstance();

		//searches list of members for entered name
		//saves index if name is found
		for(Member mem : memberList){
			if(mem.name.equalsIgnoreCase(namea)){
				currentMember = mem;
				memberExists = true;
			}
		}

		//adds a new member if the name was not in the list
		if(!memberExists){
			if(JOptionPane.showConfirmDialog(null, "Member does not exits. Create new member?", "Create Member", JOptionPane.YES_NO_OPTION) == 0){
				currentMember = new Member(namea);
				memberList.add(currentMember);
			}
			else{
				return;
			}
		}

		//If the user has signed in more recently than they have signed out, they must still be signed in
		//In that case, this alerts the user and returns out of signIn
		if(currentMember.timeIn > currentMember.timeOut){
			JOptionPane.showMessageDialog(null, "You are already signed in", "Already signed in", JOptionPane.WARNING_MESSAGE);
			textField0.setText("");
			return;
		}

		//adds member to signedInMembers and saves the member's timeIn
		currentMember.timeIn = cal.getTimeInMillis();
		signedInMembers.add(currentMember);

		names.setText(signedInMembers.toString());

		//clears the text field
		textField0.setText("");
		System.out.printf("%S signed in\n", currentMember.name);
	}

	//updates member's total time worked
	//removes member from signedInMembers & updates GUI JTextArea
	//serializes member list
	//updates attendance summary file
	private void signOut(String namea){
		boolean memberIsSignedIn = false;
		Member currentMember = new Member("placeholder");
		cal = Calendar.getInstance();
		java.sql.Date date = new java.sql.Date(cal.getTimeInMillis());

		//updates member time worked and days worked
		for(Member mem : signedInMembers){
			if(mem.name.equalsIgnoreCase(namea)){
				memberIsSignedIn = true;
				mem.timeOut = cal.getTimeInMillis();
				if((mem.timeOut - mem.dateOut) > 500000000){
					mem.daysWorked++;
					mem.dateOut = mem.timeOut;
				}
				mem.timeWorked = (mem.timeOut - mem.timeIn);
				mem.timeWorkedTotal += mem.timeWorked;
				mem.hoursWorked = (Math.round(mem.timeWorkedTotal / 36000)/100.00);

				/*if(date.compareTo(mem.dateOut) > 0){
					mem.daysWorked++;
				}*/
				System.out.println(mem.timeWorkedTotal);
				currentMember = mem;
			}
		}

		//exits signOut if member is not signed in
		if(!memberIsSignedIn){
			JOptionPane.showMessageDialog(null, "You are not signed in", "Not signed in", JOptionPane.WARNING_MESSAGE);
			return;
		}

		//removes member from signedInMembers & updates GUI
		signedInMembers.remove(currentMember);
		names.setText(signedInMembers.toString());
		textField0.setText("");
		System.out.printf("%S signed out\n", currentMember.name);

		//serialize the list of members
		try{
			try{
				FileOutputStream fo = new FileOutputStream(savedMemberList);
				ObjectOutputStream output = new ObjectOutputStream(fo);
				output.writeObject(memberList);
				output.close();
				fo.close();
			} catch(FileNotFoundException ex){
				System.out.println(ex.getMessage());
			}
		} catch(IOException iex){
			System.out.println(iex.getMessage());
		}

		//updates the attendance summary file
		try{
			PrintWriter filePrinter = new PrintWriter(attendanceSummary);
			for(int i = 0; i < memberList.size(); i++){
				filePrinter.println(memberList.get(i).name + "\tDays Logged: " + memberList.get(i).daysWorked + "\tHours Logged: " + memberList.get(i).hoursWorked);
			}
			filePrinter.close();
		} catch(FileNotFoundException fex){
			System.out.println(fex.getMessage());
		}
	}

	//deserializes savedMemberList
	public MemberList getMemberList(){
		MemberList list = new MemberList();
		try{
			try{
				try{
					FileInputStream fi = new FileInputStream(savedMemberList);
					ObjectInputStream input = new ObjectInputStream(fi);
					list = (MemberList)input.readObject();
				} catch(FileNotFoundException ex){
					System.out.println(ex.getMessage());
					}
				} catch(IOException iex){
					System.out.println(iex.getMessage());
			}
		} catch(ClassNotFoundException cex){
			System.out.println(cex.getMessage());
		}
		return list;
	}
}