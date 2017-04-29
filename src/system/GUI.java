/**
 * @author Mohamed Fouad
 * This class is for the GUI
 */
package system;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class GUI extends JFrame{
	public static File file;
	public static boolean flag = false;
	public static int EAXValue=0;
	public static int EBXValue=0;
	public static JFrame MyFrame;
	public static JLabel EAX,EBX,EDX,Note1;
	public static JButton Compile,Execute,SelectFile,ClearDisplayArea;
	public static JTextField EAXJTextField,EBXJTextField,EDXJTextField;
	public static JTextArea TextArea;
	public static JPanel WestPanel,NorthPanel,EastPanel;
	public static Color Black = new Color(32,32,32);
	public static Color DarkGrey = new Color(96,96,96);
	
	GUI(){
		EDX = new JLabel("EDX");
		EDXJTextField = new JTextField();
		EDXJTextField.enable(false);
		MyFrame = new JFrame("System Programming (C321)");
		MyFrame.setLayout(new BorderLayout());
		EAX = new JLabel("EAX");
		EBX = new JLabel("EBX"); 
	//	Note1 = new JLabel("      			 		 >>>>>Welcome to my Emulator here you can do arithmetic operations for registers (EAX,EBX,EDX) <<<<<");
		Compile = new JButton("Compile");
		Execute = new JButton("Execute");
		SelectFile = new JButton("SelectFile");
		EAXJTextField = new JTextField();
		EBXJTextField = new JTextField();
		EBXJTextField.enable(false);
		EAXJTextField.enable(false);
		TextArea = new JTextArea();
		WestPanel = new JPanel();
		EastPanel = new JPanel();
		WestPanel.setLayout(new GridLayout(6,1));
		EastPanel.setLayout(new GridLayout(4,1));
		WestPanel.add(EAX);
		WestPanel.add(EAXJTextField);
		WestPanel.add(EBX);
		WestPanel.add(EBXJTextField);
		WestPanel.add(EDX);
		WestPanel.add(EDXJTextField);
		EastPanel.add(SelectFile);
		EastPanel.add(Compile);
		EastPanel.add(Execute);
		ClearDisplayArea = new JButton("Clear DisplayArea");
		EastPanel.add(ClearDisplayArea);
		ClearDisplayArea.addActionListener(new Actions());
		NorthPanel = new JPanel();
		NorthPanel.setLayout(new GridLayout(2,1));
		//NorthPanel.add(Note1);
	//	NorthPanel.add(Note2);
		MyFrame.add(NorthPanel,BorderLayout.NORTH);
		MyFrame.add(WestPanel,BorderLayout.WEST);
		MyFrame.add(EastPanel,BorderLayout.EAST);
		JScrollPane sp = new JScrollPane(TextArea);
		MyFrame.add(sp,BorderLayout.CENTER);
		TextArea.enable(false);
		MyFrame.setSize(665, 900);
		MyFrame.setVisible(true);
		MyFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		Execute.enable(flag);
		MyFrame.setBackground(Black);
		TextArea.setBackground(Black);
		Execute.addActionListener(new Actions());
		Compile.addActionListener(new Actions());
		SelectFile.addActionListener(new Actions());
		EAXJTextField.setText("0");
		EAXJTextField.setDisabledTextColor(new Color(255,0,0));
		EBXJTextField.setText("0");
		EBXJTextField.setDisabledTextColor(new Color(255,0,0));
		EDXJTextField.setText("0");
		EDXJTextField.setDisabledTextColor(new Color(255,0,0));
		TextArea.setAutoscrolls(true);
		TextArea.setDisabledTextColor(new Color(0,255,0));
	}
	
	
	
	public static void main(String[] args){ new GUI(); }                     //       <<--------------------<    *Main*
	
	
	
	public class Actions implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent Event) {
				if(Event.getSource() == SelectFile){
				    JFileChooser chooser = new JFileChooser();
				    //FileNameExtensionFilter filter = new FileNameExtensionFilter("txt");
				    //chooser.setFileFilter(filter);
				    int returnVal = chooser.showOpenDialog(MyFrame);
				    if(returnVal == JFileChooser.APPROVE_OPTION) {
				         file = chooser.getSelectedFile();
				    }
					flag = false;
					Execute.enable(flag);
					TextArea.append("File selected compile it.\n");
				}else if(Event.getSource() == Compile){
					if(file==null){
						TextArea.setDisabledTextColor(new Color(255,0,0));
						TextArea.append("You must select file first\n");
					}else{
						TextArea.setDisabledTextColor(new Color(0,255,0));
					
					    	  TextArea.append("Compiling...\n");
					int delay = 2000; //milliseconds
					  ActionListener taskPerformer2 = new ActionListener() {
					      public void actionPerformed(ActionEvent evt) {
					    	  TextArea.append("you can execute...\n\n");
					      }
					  };
					Timer t =new Timer(delay,taskPerformer2);
					t.setRepeats(false);
					t.start();
					 
					flag = true;
					Execute.enable(true);
					}
				}else if(Event.getSource() == Execute){
					if(flag == true){
						TextArea.setDisabledTextColor(new Color(0,255,0));
						Execute.enable(flag);
						DataReader DR = new DataReader();
						try {
							DR.ReadFile(file, TextArea, EAXJTextField, EBXJTextField,EDXJTextField);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}else{
						TextArea.setDisabledTextColor(new Color(255,0,0));
						TextArea.append("You have to Select .txt file and Compile first\n");
					}
				}else if(Event.getSource() == ClearDisplayArea){
					TextArea.setText("");
				}
		}
	}
}
