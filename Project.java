import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.*;
import java.sql.Statement;
import java.util.Scanner;
import com.mysql.jdbc.Driver;

public class Project {
	 Connection obj=null;
		Connection getConnection() throws Exception{		
				Class.forName("com.mysql.jdbc.Driver");
				obj=DriverManager.getConnection("jdbc:mysql://localhost:3306/manual","root","root");
				return obj;
		}
		void insertRecords(int s_id, String s_name, String s_dept) throws Exception
		{
					Statement stmt=obj.createStatement();
					String sql1="Insert into Students_Details values('"+s_id+"','"+s_name+"','"+s_dept+"')";
					stmt.executeUpdate(sql1);
					System.out.println("Records Inserted!");
		}
		void insertResult(String name,int roll,String dept,int eng,int mat,int phy,int che,int prac) throws Exception
			{
						Statement stmt=obj.createStatement();
						String sql1="Insert into Result values('"+name+"','"+roll+"','"+dept+"','"+eng+"','"+mat+"','"+phy+"','"+che+"','"+prac+"')";
						stmt.executeUpdate(sql1);
						System.out.println("Records Inserted!");
			}
		 void displayRecords() throws Exception
		{
					Statement stmt=obj.createStatement();
					String sql1="select *from Students_Details";
					stmt.executeQuery(sql1);
					ResultSet res=(ResultSet) stmt.executeQuery(sql1);
					System.out.println("RegNo   Name         Dept");
					while(res.next())
					{
						System.out.print(res.getInt(1)+"       ");
						System.out.print(res.getString(2)+"     ");
						System.out.println(res.getString(3)+"   ");
					}
					System.out.println("Records Displayed.");
				}
		 String displayResult(int roll,String name,String dept) throws Exception
			{
						Statement stmt=obj.createStatement();
						String sql1="select *from result where s_roll='"+roll+"'";
						stmt.executeQuery(sql1);
						ResultSet res=(ResultSet) stmt.executeQuery(sql1);
						String x="";
						if(res.next())
						{
							x=""+res.getInt(4)+" "+res.getInt(5)+" "+res.getInt(6)+" "+res.getInt(7)+" "+res.getInt(8);
						}
						System.out.println("Records Displayed!");
						return x;
					}
		static void exit()
		{
			System.out.println("Program Terminate..");
			System.exit(0);
		}
		static String[] s1= {"sakthi","surya"};
		static String[] s2= {"@08","$10"};
		static boolean check(String str1,String str2){
			for(int i=0;i<s1.length;i++) {
				if(s1[i].equals(str1) && s2[i].equals(str2)) {
					return true;
				}
			}
		      return false;
			}
		public static void main(String[] args) throws Exception
		{
			Scanner scan = new Scanner (System.in);
			Project s=new Project();
			s.getConnection();
	        System.out.println("\t\tSRI SHAKTHI INSTITUTE OF ENGINEERING AND TECHNOLOGY ");
	        System.out.println ("\t\t\t  AN AUTONOMOUS INSTITUTION ");
	        System.out.println ();
	        System.out.println ();
	        delay();
	        System.out.println("Enter 1 for ADMIN login");
	        System.out.println("Enter 2 for checking results");
	        int num=scan.nextInt();
	        scan.nextLine();
	        if(num==1) {	
	        	System.out.println("Enter your UserName: ");
	        	String Username=scan.nextLine();
	        	System.out.println("Enter your password: ");
	        	String password=scan.nextLine();
	        	if(check(Username,password)) {
	        		System.out.println("Enter 1 for Uploading Result");
	    	        System.out.println("Enter 2 for checking views");
	    	        int  option =Integer.parseInt(scan.nextLine());
	    	        if(option==1) {
	    	        	System.out.println("Enter the number of record to be inserted");
	    	        	int num1=Integer.parseInt(scan.nextLine());
	    	        	for(int i=0;i<num1;i++) {
	    	        		System.out.println("Enter name : ");
	    	        		String name=scan.nextLine();
	    	        		System.out.println("Enter roll : ");
	    	        		int roll=Integer.parseInt(scan.nextLine());
	    	        		System.out.println("Enter dept : ");
	    	        		String dept=scan.nextLine();
	    	        		System.out.println("Enter english marks : ");
	    	        		int eng=Integer.parseInt(scan.nextLine());
	    	        		System.out.println("Enter maths marks : ");
	    	        		int mat=Integer.parseInt(scan.nextLine());
	    	        		System.out.println("Enter physics marks : ");
	    	        		int phy=Integer.parseInt(scan.nextLine());
	    	        		System.out.println("Enter chemistry marks : ");
	    	        		int che=Integer.parseInt(scan.nextLine());
	    	        		System.out.println("Enter practical marks  : ");
	        				int prac=Integer.parseInt(scan.nextLine());
	        				s.insertResult(name,roll,dept,eng,mat,phy,che,prac);
	    	        	}
	    	        }
	    	        else {
	    	        	s.displayRecords();
	    	        }
	        			
	        	}
	        	else {
	        		System.out.println("Your Password doesnt match");
	        	}
	        }
	        else {
	        	System.out.println ("TO KNOW YOUR RESULTS PLEASE ENTER THE FOLLOWING DETAILS");
	        	delay();
	        	System.out.println ("ENTER YOUR ROLL NUMBER");
	        	delay();
	        	int reg_no = Integer.parseInt(scan.nextLine());
	        	System.out.println ("ENTER YOUR NAME");
	        	String name = scan.nextLine ();
	        	System.out.println ("ENTER YOUR DEPARTMENT");
	        	String dept = scan.nextLine ();
	        	verification (reg_no);
	        	s.insertRecords(reg_no,name,dept);
	        	String res=s.displayResult(reg_no,name,dept);
	        	//System.out.println("*"+res);
	        	showResult(res,name,reg_no);
	        }
	  }
	  public static void showResult(String res,String name,int roll) {
		  String[] r=res.split(" ");
		  int[] arr=new int[5];
		  int total=0;
		  for(int i=0;i<5;i++) {
			  arr[i]=Integer.parseInt(r[i]);
			  total+=arr[i];
		  }
		  System.out.println(total);


		  
	  }
	  public static void verification (int reg_no)
	  {
	        Scanner scan = new Scanner (System.in);
	        int ran = (int) (Math.random () * 100000);
	        System.out.println (ran);
	        delay();
	        System.out.println ("RE-ENTER THE CODE FOR VERIFICATION PURPOSE");
	        int ran1 = scan.nextInt ();
	        if (ran == ran1)
	        {
		        System.out.println ("Loading..... please wait");
		        System.out.println ("*WELCOME TO THE SIET PORTAL*");
		        delay();
	        }
	        else
	        {
		        System.out.println ("Invalid number");
		        System.out.println ("Please enter the valid number");
	        }
	    }
	    
	    
	  
	    public static void delay()
	    {
	        try 
	        {
	            Thread.sleep(500); 
	        } 
	        catch(Exception ex)
	        {
		        System.exit(0);
	        }
	    }
	}