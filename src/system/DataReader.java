/**
 * @author Mohamed Fouad
 * This class is the logic class
 * only one method DataReader() that is called when execute JButton is pressed at GUI class
 */
package system;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.*;

public class DataReader {
	File file = new File("Test.txt");
	static int eax =0;
	static int ebx =0;
	static int edx =0;
	static String OPCODE="";
	static String EBX = "ebx";
	static String EAX = "eax";
	static String EDX = "edx";
	static int IntOperand = -1;
	static String mul="MUL";
	static String mov="MOV";
	static String add="ADD";
	static String div="DIV";
	static String Operation = "";
	static int ValueOperand = 0;
	static String StringValueOperand = "";
	static String memoryHex = "";
	static String intHex = "";
	static ArrayList<String> RawBinary = new ArrayList<String>();
	static ArrayList<String> LabelsNames = new ArrayList<String>();
	static ArrayList<Integer> LabelsValues = new ArrayList<Integer>();
	static String SectionData ="Section.data";
	static String SectionText ="Section.text";
	
	DataReader(){}
	
	void ReadFile(File file , JTextArea ta , JTextField EAXJTextField,JTextField EBXJTextField,JTextField EDXJTextField) throws FileNotFoundException{
		Scanner in = new Scanner(file);
		int counter =1;
		ta.append("EAX : "+eax+" EBX : "+ebx+" EDX :"+edx+" before computing.\n\n");
		while(in.hasNext()){
			String reader = in.next();
			if(!reader.equalsIgnoreCase(SectionText)){
				if(reader.equalsIgnoreCase(SectionData)){
					in.nextLine();
				}else{
					LabelsNames.add(reader);
					reader = in.next();
					reader = in.next();
					LabelsValues.add(Integer.parseInt(reader));
				}
				
			}else{
				in.nextLine();
				break;
			}
		}
		
		while(in.hasNext()){
			String Command =in.next();
			if(Command.equals(",")){
				Command=in.next();
			}
			
			
			 if(Command.equalsIgnoreCase(mov)){
				String First = in.next();
				if(First.equals(",")){
					First=in.next();
				}
				
				String Last = in.next();
				if(Last.equals(",")){
					Last=in.next();
				}
				Operation = mov;
				
				int Index=-1;
				boolean IsFirst = false;
				boolean IsLast = false;
				
				for(int i = 0 ; i < LabelsNames.size() ; i++){
					if(First.equals(LabelsNames.get(i))){
						Index = i;
						IsFirst=true;
						break;
					}
					if(Last.equals(LabelsNames.get(i))){
						Index = i;
						IsLast=true;
						break;
					}
				}
				
				ta.append("line# ("+counter+") Operation : "+Operation+"   EAX : "+eax+" EBX : "+ebx+" EDX : "+edx+" before operation execution.\n");
				
				
				if(Index != -1){
					
					if(IsFirst == true){
						if(Last.equalsIgnoreCase(EAX)){
							LabelsValues.set(Index, eax);
							OPCODE = "B8";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}else if(Last.equalsIgnoreCase(EBX)){
							LabelsValues.set(Index, ebx);
							OPCODE= "BB";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}else if(Last.equalsIgnoreCase(EDX)){
							LabelsValues.set(Index, edx);
							OPCODE = "BA";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}
					}else if(IsLast==true){
						if(First.equalsIgnoreCase(EAX)){
							eax=LabelsValues.get(Index);
							OPCODE = "B8";
							intHex = Integer.toHexString(eax);
						}else if(First.equalsIgnoreCase(EBX)){
							ebx=LabelsValues.get(Index);
							OPCODE= "BB";
							intHex = Integer.toHexString(ebx);
						}else if(First.equalsIgnoreCase(EDX)){
							edx=LabelsValues.get(Index);
							OPCODE = "BA";
							intHex = Integer.toHexString(edx);
						}
					}
					
				}else if(Last.endsWith("d")||Last.endsWith("D")){
					StringValueOperand = Last.substring(0, Last.length()-1);
					ValueOperand = Integer.parseInt(StringValueOperand);
					if(First.equalsIgnoreCase(EAX)){
						eax=ValueOperand;
						OPCODE = "B8";
						intHex = Integer.toHexString(eax);
					}else if(First.equalsIgnoreCase(EBX)){
						ebx=ValueOperand;
						OPCODE= "BB";
						intHex = Integer.toHexString(ebx);
					}else if(First.equalsIgnoreCase(EDX)){
						edx = ValueOperand;
						OPCODE = "BA";
						intHex = Integer.toHexString(edx);
					}
				}
				else if(First.equalsIgnoreCase(EAX) && Last.equalsIgnoreCase(EBX)){
					eax=ebx;
					OPCODE ="B8";
					intHex = Integer.toHexString(eax);
				}else if (First.equalsIgnoreCase(EBX) && Last.equalsIgnoreCase(EAX)){
					ebx=eax;
					OPCODE ="BB";
					intHex = Integer.toHexString(ebx);
				}else if (First.equalsIgnoreCase(EAX) && Last.equalsIgnoreCase(EDX)){
					eax=edx;
					OPCODE ="B8";
					intHex = Integer.toHexString(eax);
				}else if (First.equalsIgnoreCase(EBX) && Last.equalsIgnoreCase(EDX)){
					ebx=edx;
					OPCODE ="BB";
					intHex = Integer.toHexString(ebx);
				}else if (First.equalsIgnoreCase(EDX) && Last.equalsIgnoreCase(EAX)){
					edx=eax;
					OPCODE ="BA";
					intHex = Integer.toHexString(edx);
				}else if (First.equalsIgnoreCase(EDX) && Last.equalsIgnoreCase(EBX)){
					edx=ebx;
					OPCODE ="BA";
					intHex = Integer.toHexString(edx);
				}
			}
			else if(Command.equalsIgnoreCase(add)){
				String First = in.next();
				if(First.equals(",")){
					First=in.next();
				}
				
				String Last = in.next();
				if(Last.equals(",")){
					Last=in.next();
				}
				Operation = add;
				
				int Index=-1;
				boolean IsFirst = false;
				boolean IsLast = false;
				
				for(int i = 0 ; i < LabelsNames.size() ; i++){
					if(First.equals(LabelsNames.get(i))){
						Index = i;
						IsFirst=true;
						break;
					}
					if(Last.equals(LabelsNames.get(i))){
						Index = i;
						IsLast=true;
						break;
					}
				}
				
				ta.append("line# ("+counter+") Operation : "+Operation+"   EAX : "+eax+" EBX : "+ebx+" EDX : "+edx+" before operation execution.\n");
				
				if(Index != -1){ 
					
					if(IsFirst == true){
						if(Last.equalsIgnoreCase(EAX)){
							LabelsValues.set(Index, eax+LabelsValues.get(Index));
							OPCODE ="05";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}else if(Last.equalsIgnoreCase(EBX)){
							LabelsValues.set(Index, ebx+LabelsValues.get(Index));
							OPCODE ="04";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}else if(Last.equalsIgnoreCase(EDX)){
							LabelsValues.set(Index, edx+LabelsValues.get(Index));
							OPCODE ="07";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}
					}else if(IsLast==true){
						if(First.equalsIgnoreCase(EAX)){
							eax+=LabelsValues.get(Index);
							OPCODE ="05";
							intHex = Integer.toHexString(eax);
						}else if(First.equalsIgnoreCase(EBX)){
							ebx+=LabelsValues.get(Index);
							OPCODE ="04";
							intHex = Integer.toHexString(ebx);
						}else if(First.equalsIgnoreCase(EDX)){
							edx+=LabelsValues.get(Index);
							OPCODE ="07";
							intHex = Integer.toHexString(edx);
						}
					}
					
				}else if(Last.endsWith("d")||Last.endsWith("D")){
					StringValueOperand = Last.substring(0, Last.length()-1);
					ValueOperand = Integer.parseInt(StringValueOperand);
					if(First.equalsIgnoreCase(EAX)){
						eax+=ValueOperand;
						OPCODE ="05";
						intHex = Integer.toHexString(eax);
					}else if(First.equalsIgnoreCase(EBX)){
						ebx+=ValueOperand;
						OPCODE ="04";
						intHex = Integer.toHexString(ebx);
					}else if(First.equalsIgnoreCase(EDX)){
						edx+=ValueOperand;
						OPCODE ="07";
						intHex = Integer.toHexString(edx);
					}
					 
				}else if(First.equalsIgnoreCase(EAX) && Last.equalsIgnoreCase(EBX)){
					eax+=ebx;
					OPCODE ="80";
					intHex = Integer.toHexString(eax);
				}else if (First.equalsIgnoreCase(EBX) && Last.equalsIgnoreCase(EAX)){
					ebx+=eax;
					OPCODE ="89";
					intHex = Integer.toHexString(ebx);
				}else if (First.equalsIgnoreCase(EAX) && Last.equalsIgnoreCase(EDX)){
					eax+=edx;
					OPCODE ="B1";
					intHex = Integer.toHexString(eax);
				}else if (First.equalsIgnoreCase(EBX) && Last.equalsIgnoreCase(EDX)){
					ebx+=edx;
					OPCODE ="A2";
					intHex = Integer.toHexString(ebx);
				}else if (First.equalsIgnoreCase(EDX) && Last.equalsIgnoreCase(EAX)){
					edx+=eax;
					OPCODE ="06";
					intHex = Integer.toHexString(edx);
				}else if (First.equalsIgnoreCase(EDX) && Last.equalsIgnoreCase(EBX)){
					edx+=ebx;
					OPCODE ="07";
					intHex = Integer.toHexString(edx);
				}
				
			}else if(Command.equalsIgnoreCase(mul)){
				String First = in.next();
				if(First.equals(",")){
					First=in.next();
				}
				
				String Last = in.next();
				if(Last.equals(",")){
					Last=in.next();
				}
				Operation = mul;
				
				int Index=-1;
				boolean IsFirst = false;
				boolean IsLast = false;
				
				for(int i = 0 ; i < LabelsNames.size() ; i++){
					if(First.equals(LabelsNames.get(i))){
						Index = i;
						IsFirst=true;
						break;
					}
					if(Last.equals(LabelsNames.get(i))){
						Index = i;
						IsLast=true;
						break;
					}
				}
				
				ta.append("line# ("+counter+") Operation : "+Operation+"   EAX : "+eax+" EBX : "+ebx+" EDX : "+edx+" before operation execution.\n");
				
				
				if(Index != -1){ 
					
					if(IsFirst == true){
						if(Last.equalsIgnoreCase(EAX)){
							LabelsValues.set(Index, eax*LabelsValues.get(Index));
							OPCODE = "F7";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}else if(Last.equalsIgnoreCase(EBX)){
							LabelsValues.set(Index, ebx*LabelsValues.get(Index));
							OPCODE = "F7";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}else if(Last.equalsIgnoreCase(EDX)){
							LabelsValues.set(Index, edx*LabelsValues.get(Index));
							OPCODE = "F7";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}
					}else if(IsLast==true){
						if(First.equalsIgnoreCase(EAX)){
							eax*=LabelsValues.get(Index);
						}else if(First.equalsIgnoreCase(EBX)){
							ebx*=LabelsValues.get(Index);
							OPCODE = "F7";
							intHex = Integer.toHexString(ebx);
						}else if(First.equalsIgnoreCase(EDX)){
							edx*=LabelsValues.get(Index);
							OPCODE = "F7";
							intHex = Integer.toHexString(edx);
						}
					}
					
				}else if(Last.endsWith("d")||Last.endsWith("D")){
					StringValueOperand = Last.substring(0, Last.length()-1);
					ValueOperand = Integer.parseInt(StringValueOperand);
					if(First.equalsIgnoreCase(EAX)){
						eax=eax*ValueOperand;
						OPCODE = "F7";
						intHex = Integer.toHexString(eax);
					}else if(First.equalsIgnoreCase(EBX)){
						ebx=ebx*ValueOperand;
						OPCODE = "F7";
						intHex = Integer.toHexString(ebx);
					}else if(First.equalsIgnoreCase(EDX)){
						edx=edx*ValueOperand;
						OPCODE = "F7";
						intHex = Integer.toHexString(edx);
					}
				}else if(First.equalsIgnoreCase(EAX) && Last.equalsIgnoreCase(EBX)){
					eax*=ebx;
					OPCODE = "F7";
					intHex = Integer.toHexString(eax);
				}else if (First.equalsIgnoreCase(EBX) && Last.equalsIgnoreCase(EAX)){
					ebx*=eax;
					OPCODE = "F7";
					intHex = Integer.toHexString(ebx);
				}else if (First.equalsIgnoreCase(EAX) && Last.equalsIgnoreCase(EDX)){
					eax*=edx;
					OPCODE = "F7";
					intHex = Integer.toHexString(eax);
				}else if (First.equalsIgnoreCase(EBX) && Last.equalsIgnoreCase(EDX)){
					ebx*=edx;
					OPCODE = "F7";
					intHex = Integer.toHexString(ebx);
				}else if (First.equalsIgnoreCase(EDX) && Last.equalsIgnoreCase(EAX)){
					edx*=eax;
					OPCODE = "F7";
					intHex = Integer.toHexString(edx);
				}else if (First.equalsIgnoreCase(EDX) && Last.equalsIgnoreCase(EBX)){
					edx*=ebx;
					OPCODE = "F7";
					intHex = Integer.toHexString(edx);
				}
			}else if(Command.equalsIgnoreCase(div)){
				String First = in.next();
				if(First.equals(",")){
					First=in.next();
				}
				
				String Last = in.next();
				if(Last.equals(",")){
					Last=in.next();
				}
				Operation = div;
				
				int Index=-1;
				boolean IsFirst = false;
				boolean IsLast = false;
				
				for(int i = 0 ; i < LabelsNames.size() ; i++){
					if(First.equals(LabelsNames.get(i))){
						Index = i;
						IsFirst=true;
						break;
					}
					if(Last.equals(LabelsNames.get(i))){
						Index = i;
						IsLast=true;
						break;
					}
				}
				
				ta.append("line# ("+counter+") Operation : "+Operation+"   EAX : "+eax+" EBX : "+ebx+" EDX : "+edx+" before operation execution.\n");

				
				if(Index != -1){ 
					
					if(IsFirst == true){
						if(Last.equalsIgnoreCase(EAX)){
							LabelsValues.set(Index, LabelsValues.get(Index)/eax);
							OPCODE = "F2";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}else if(Last.equalsIgnoreCase(EBX)){
							LabelsValues.set(Index, LabelsValues.get(Index)/ebx);
							OPCODE = "F3";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}else if(Last.equalsIgnoreCase(EDX)){
							LabelsValues.set(Index, LabelsValues.get(Index)/edx);
							OPCODE = "F4";
							intHex = Integer.toHexString(LabelsValues.get(Index));
						}
					}else if(IsLast==true){
						if(First.equalsIgnoreCase(EAX)){
							eax= eax/LabelsValues.get(Index);
							OPCODE = "F2";
							intHex = Integer.toHexString(eax);
						}else if(First.equalsIgnoreCase(EBX)){
							ebx= ebx/LabelsValues.get(Index);
							OPCODE = "F3";
							intHex = Integer.toHexString(ebx);
						}else if(First.equalsIgnoreCase(EDX)){
							edx= edx/LabelsValues.get(Index);
							OPCODE = "F4";
							intHex = Integer.toHexString(edx);
						}
					}
					
				}else if(Last.endsWith("d")||Last.endsWith("D")){
					StringValueOperand = Last.substring(0, Last.length()-1);
					ValueOperand = Integer.parseInt(StringValueOperand);
					if(First.equalsIgnoreCase(EAX)){
						eax=eax/ValueOperand;
						OPCODE = "F2";
						intHex = Integer.toHexString(eax);
					}else if(First.equalsIgnoreCase(EBX)){
						ebx=ebx/ValueOperand;
						OPCODE = "F3";
						intHex = Integer.toHexString(ebx);
					}else if(First.equalsIgnoreCase(EDX)){
						edx=edx/ValueOperand;
						OPCODE = "F4";
						intHex = Integer.toHexString(edx);
					}
				}else if(First.equalsIgnoreCase(EAX) && Last.equalsIgnoreCase(EBX)){
					eax/=ebx;
					OPCODE = "F2";
					intHex = Integer.toHexString(eax);
				}else if (First.equalsIgnoreCase(EBX) && Last.equalsIgnoreCase(EAX)){
					ebx/=eax;
					OPCODE = "F3";
					intHex = Integer.toHexString(ebx);
				}else if (First.equalsIgnoreCase(EAX) && Last.equalsIgnoreCase(EDX)){
					eax/=edx;
					OPCODE = "F2";
					intHex = Integer.toHexString(eax);
				}else if (First.equalsIgnoreCase(EBX) && Last.equalsIgnoreCase(EDX)){
					ebx/=edx;
					OPCODE = "F3";
					intHex = Integer.toHexString(ebx);
				}else if (First.equalsIgnoreCase(EDX) && Last.equalsIgnoreCase(EAX)){
					edx/=eax;
					OPCODE = "F4";
					intHex = Integer.toHexString(edx);
				}else if (First.equalsIgnoreCase(EDX) && Last.equalsIgnoreCase(EBX)){
					edx/=ebx;
					OPCODE = "F4";
					intHex = Integer.toHexString(edx);
				}
			}
			ta.append("line# ("+counter+") Operation : "+Operation+"   EAX : "+eax+" EBX : "+ebx+" EDX : "+edx+" Executed.\n\n");
			
			String counterInhex = Integer.toHexString(((counter-1)*4) +(LabelsNames.size()*4));
			
			if(counterInhex.length()==1){
				counterInhex = new String("0000000"+counterInhex);
			}else if(counterInhex.length()==2){
				counterInhex = new String("000000"+counterInhex);
			}
			
			if(intHex.length() == 1){
				intHex = new String("0"+intHex+"000000");
			}else if(intHex.length() == 2){
				intHex = new String(intHex+"000000");
			}
			
			String Raw = "*"+(counter+LabelsNames.size()+2)+" "+counterInhex+" "+OPCODE+intHex+"\n";
			RawBinary.add(Raw);
			if(in.hasNext()){
				in.nextLine();
			}
			counter++;
		}
		ta.append("EAX : "+eax+" EBX : "+ebx+" EDX : "+edx+" Finally.\n");
		counter = 1;
		EAXJTextField.setText(eax+"");
		EBXJTextField.setText(ebx+"");
		EDXJTextField.setText(edx+"");
		ta.append("___________________________________________________________________________________________________________________________\n\n");
		
		ta.append(":::::::::::::: RAW BINARY ::::::::::::::\n\n");
		if(LabelsNames.size() !=0){
			ta.append("*1\n");
			for(int i = 0 ; i < LabelsNames.size() ; i ++){
				String addrInhex = Integer.toHexString(i*4);
				String labelValueInHex = Integer.toHexString(LabelsValues.get(i));
				ta.append("*"+(i+2) +" "+addrInhex+" "+labelValueInHex+"\n");
			}
			ta.append("*"+(LabelsNames.size()+2) +"\n");
		}
		for(int i = 0 ; i < RawBinary.size() ; i ++){
			ta.append(RawBinary.get(i));
		}
		ta.append("\n");
		ta.append(":::::::::::::: Symbol table ::::::::::::::\n\n");
		ta.append("Name | Value | Segment | Memory\n");
		for(int i = 0 ; i < LabelsNames.size();i++){
			String symbolTableRow =""+ LabelsNames.get(i) +" | "+LabelsValues.get(i)+" | Data | "+ i *4;
			ta.append(symbolTableRow+"\n");
		}
		
		
		eax=0;
		ebx=0;
		edx=0;
		OPCODE = "";
		intHex ="";
		counter = 0;
		ValueOperand = 0;
		StringValueOperand = "";
		memoryHex = "";
		RawBinary.clear();
		LabelsNames.clear();
		LabelsValues.clear();
		in.close();
	}
}
