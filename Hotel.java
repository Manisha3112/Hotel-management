import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.TimeZone;
public class Hotel {
	Connection obj=null;
	Connection getConnection() throws Exception{			
		Class.forName("com.mysql.cj.jdbc.Driver");
		obj=DriverManager.getConnection("jdbc:mysql://localhost:3301/miniproject","root","root");
		return obj;
	}
	void addRecords(String a,String b,String c) throws Exception{
	//	if(obj!=null) {
			Statement stmt=obj.createStatement();
			String sql1="Insert into entry values('"+a+"','"+b+"','"+c+"')";
			stmt.executeUpdate(sql1);
			System.out.println("Records Inserted!");
		//}
	}
	String displayRecord(String name) throws Exception{
		if(obj!=null) {
			
				String sql="select Password from entry where Name='"+name+"' ";
				Statement stmt=obj.createStatement();
				ResultSet rs=(ResultSet) stmt.executeQuery(sql);
				String res=null;
				if(rs.next()) {
					res=rs.getString("Password");
				}
				return res;
			}
		return null;
	}
	int nameValidation(String name) throws Exception{
		if(obj!=null) {
			
			String sql="select Name from entry";
			Statement stmt=obj.createStatement();
			ResultSet rs=(ResultSet) stmt.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString(1).equals(name)) {
					return 1;
				}
			}
		}
		return 0;
	
	}
	int numberValidation(String num) throws Exception{
		if(obj!=null) {
			
			String sql="select Mobile_Number from entry";
			Statement stmt=obj.createStatement();
			ResultSet rs=(ResultSet) stmt.executeQuery(sql);
			while(rs.next()) {
				if(rs.getString(1).equals(num)) {
					return 1;
				}
			}
		}
		return 0;
	
	}
	 int updateRoom(int room_count,int room_type) throws Exception {
		if(room_type==1) {
			 String sql="select *from room where type_of_room='ac room'";
			 Statement stmt=obj.createStatement();
			 ResultSet rs=(ResultSet) stmt.executeQuery(sql);
			 while(rs.next()) {
					//System.out.println(rs.getString(1)+" "+rs.getInt(2)+" "+rs.getInt(3));
					if(rs.getInt(3)>=room_count) {
						Statement stmt1=obj.createStatement();
						String s="update room set no_of_rooms=no_of_rooms-'"+room_count+"' where type_of_room='"+"ac room"+"' and no_of_rooms>0";
						stmt1.executeUpdate(s);
						return 1;
					}
					else
						System.out.println(room_count+" Ac Rooms unavailable right now!");
				}
			
		}
		else {
			String sql="select *from room where type_of_room='"+"NON AC room"+"'";
			 Statement stmt=obj.createStatement();
			 ResultSet rs=(ResultSet) stmt.executeQuery(sql);
			 while(rs.next()) {
					//System.out.println(rs.getString(1)+" "+rs.getInt(2)+" "+rs.getInt(3));
					if(rs.getInt(3)>=room_count) {
						Statement stmt1=obj.createStatement();
						String s="update room set no_of_rooms=no_of_rooms-'"+room_count+"' where type_of_room='"+"ac room"+"' and no_of_rooms>0";
						stmt1.executeUpdate(s);
						return 1;
					}
					else
						System.out.println(room_count+" Non Ac Rooms unavailable right now!");
				}
		}
		return 0;
	}
	int searchFood(String f_name,int f_count) throws Exception{
		String sql="select *from menu_card where food='"+f_name+"'";
		Statement stmt=obj.createStatement();
		ResultSet rs=(ResultSet) stmt.executeQuery(sql);
		while(rs.next()) {
			//System.out.println(rs.getString(1)+" "+rs.getInt(2)+" "+rs.getInt(3));
			if(rs.getInt(3)>=f_count) {
				Statement stmt1=obj.createStatement();
				String s="update menu_card set count=count-'"+f_count+"' where food='"+f_name+"' and count>0";
				stmt1.executeUpdate(s);
				return rs.getInt(2);
			}
			else
				return 0;
		}
		return 0;
	}
public static void main(String[] args) throws Exception{
	Scanner sc=new Scanner(System.in);
	Hotel h=new Hotel();
	h.getConnection();
	System.out.println("\n\t\t\t\tWelcome to St.REGIS HOTEL!");
    System.out.println();
    System.out.println("Sign Up or Log in\n\n** To Sign Up Enter 1 *\n* To Log  In Enter 2 **");
    int entry=Integer.parseInt(sc.nextLine());
	String mobile_num;
    String user_name="";
    String password;
	if(entry==1){
        System.out.println("Enter Mobile Number : ");
        mobile_num=sc.nextLine();
        //Check for valid Number
        if(mobile_num.length()==10 && (mobile_num.charAt(0)=='9' || mobile_num.charAt(0)=='8'||mobile_num.charAt(0)=='7'||mobile_num.charAt(0)=='6')){
            while(h.numberValidation(mobile_num)==1){
                System.out.println("* Oops! This number already exists! *\n** please use another number *\n* To exit type 'stop' **");
                System.out.println("Please Re-Enter Another Valid Mobile Number : ");
                mobile_num=sc.nextLine();
            }
            //if mobile number is valid ask user name
            if(!mobile_num.equals("stop")){
                System.out.println("Enter User Name : ");
                user_name=sc.nextLine();
                while(h.nameValidation(user_name)==1){
                    System.out.println("* Oops! This name already exists! *\n");
                    System.out.println("To exit enter 'stop' Or Please Re-Enter Another Valid User Name : ");
                    user_name=sc.nextLine();
                }
            }
            //If user name & mobile number are valid,then ask for a valid password
            if(!mobile_num.equals("stop") && !user_name.equals("stop")){
                System.out.println("Enter a valid password : ");
                password=sc.nextLine();
                h.addRecords(user_name,mobile_num,password);
                System.out.println("!!! Successfully Signed Up !!!");
            }
        }
        else{
            System.out.println("* This is a invalid Number! *\n** Please run the program again to Sign Up! **");
        }
       
    }
	else if(entry==2){
        //Displaying already logged In customers NAME
        System.out.println("Enter User Name : ");
        user_name=sc.nextLine();
        //Checking whether user is an old customer
        while(h.nameValidation(user_name)==0){
            System.out.println("This name does not exist!");
            System.out.println("Please ReEnter Already LoggedIn User Name : ");
            user_name=sc.nextLine();
        }
        System.out.println("Password : ");
        password=sc.nextLine();
        int flag=0;
        String x=h.displayRecord(user_name);
        if(x.equals(password)){
           System.out.println("!!! Successfully Logged In !!!");
           System.out.println("Want to order food or book room ? ");
   		   System.out.println("* For ORDERING FOOD Enter 1 / For BOOKING ROOM Enter 2 *");
   		   int choice=Integer.parseInt(sc.nextLine());
   		   while(choice!=1 && choice !=2){
   			   System.out.println("Your Choice Does not exist! \n ReEnter Valid Choice");
   			   choice=Integer.parseInt(sc.nextLine());
   			   
   		   }
   		   if(choice==1){
   			   System.out.println("* Welcome To Ordering Food! *");
   			   System.out.println("LAUGHTER IS BRIGHTEST WHERE FOOD IS BEST!");
   			   System.out.println("Please Go Through Our MENU And Select Best FOOD!");
   			   //Display Menu
   			   System.out.println("*-------------------------");
   			   System.out.println("*              ~ MENU CARD ~                        *");
   			   System.out.println("*    Appam           |    Rs.25 per Piece           *");
   			   System.out.println("*    Briyani         |    Rs.120 per Serving        *");
   			   System.out.println("*    Roti            |    Rs.30 per Piece           *");
   			   System.out.println("*    Noodles         |    Rs.60 per Serving         *");
   			   System.out.println("*    Parotta         |    Rs.15 per Piece           *");
   			   System.out.println("--------------------------*");
   			   System.out.println("Enter 'FOOD NAME' from  Menu to continue / Enter 'stop' to end the order : ");
   			   String food_item=sc.nextLine();
   			   System.out.println("Enter Count :");
   			   int f_count=Integer.parseInt(sc.nextLine());
   			   int cost=0;
   			   while(!food_item.equalsIgnoreCase("stop")){
   				   int finding_price=h.searchFood(food_item,f_count);
   				   if(finding_price>0) {
   					   cost+=f_count*finding_price;
   				   }
   				   else {
   					   System.out.println("Sorry! The Food of your choice is unavailable now!");
   				   }
   				   System.out.println("Enter 'FOOD NAME' from  Menu to continue / Enter 'stop' to end the order : ");
    			   food_item=sc.nextLine();
    			   if(!food_item.equalsIgnoreCase("stop")){
    				   System.out.println("Enter Count :");
    				   f_count=Integer.parseInt(sc.nextLine());
    			   }
   			   }
   			   payment(cost,"food");
   		   }
   		   else if(choice==2){
   			   System.out.println("* Welcome To Hotel Booking *");
   			   //Display room price details
   			   System.out.println("----------------------*");
   			   System.out.println("*           ~ Room Details ~                *");
   			   System.out.println("|    AC Room      |    2500  Rs Per Day     |");
   			   System.out.println("*    Non AC Room  |    1500  Rs Per Day     *");
   			   System.out.println("|                                           |");
   			   System.out.println("----------------------*");
   			   System.out.println("Want to book AC Rooom (or) Non-AC Room ? ");
   			   System.out.println("* For AC Room Enter 1  / For Non AC Room Enter 2 *");
   			   int room_type=sc.nextInt();
   			   while(room_type!=1 && room_type!=2){
   				   System.out.println("Enter valid Room choice(Enter Either 1 or 2)");
   				   room_type=sc.nextInt();
   			   }
   			   //room count
   			   System.out.println("Enter the number of rooms you want to book ");
   			   int room_count=sc.nextInt();
   			   if(h.updateRoom(room_count,room_type)==1) {
   			   int cost=0;
   			   if(room_type==1){
   				   cost=room_count*(2500);
   			   }
   			   else if(room_type==2){
   				   cost=room_count*(1500);
   			   }

   			   payment(cost,"room");
   			   }
   			   
   		   }

           flag=1;
        }
        if(flag==0){
            System.out.println("* Password does not match!*");
        }
    }
}
	static void payment(int cost,String option) {
		Scanner sc=new Scanner(System.in);
		System.out.println("!!!! Total cost is Rs."+cost+"!!!!");
		System.out.println("Are you sure to proceed payment?(y/n)");
	    System.out.println("Enter y or n");
	    String proceed_payment=sc.next();
	      
	      if(proceed_payment.equalsIgnoreCase("y")){
	          System.out.println("Thanks For Proceeding!");
	          System.out.println("Select the Mode of Transaction(Cash / Card)");
	          System.out.println("* For cash Enter 1 / For Card Enter 2 *");
	          int transaction=sc.nextInt();
	          while(transaction!=1 && transaction!=2){
	              System.out.println("Invalid choice! Enter 1 or 2");
	              transaction=sc.nextInt();
	          }
	          int bal=0;
	          //payment using cash
	          if(transaction==1){
	              System.out.println("Enter the amount given by user");
	              int cash=sc.nextInt();
	              if(cash>=cost){
	              bal=cash-cost;
	          
	              System.out.println("Payment successful!");
	              //Displaying Bill for successful Payment using cash
	              System.out.println("-----------------------");
	              System.out.println("            ~ St.REGIS HOTEL ~");
	              System.out.println("     Amount Received     : "+cash);
	              System.out.println("     Balance Returned    : "+bal);
	              System.out.println("     Payment Received On : "+time());
	              System.out.println("             Thanks You!");
	              System.out.println("-----------------------");
	              }
	              else{
	                  //Displaying Bill for unsuccessful Payment using cash
	                  System.out.println("----------------------*");
	                  System.out.println("| Insufficient Balance!                     |");
	                  System.out.println("* Payment Unsuccessful!                     *");
	                  System.out.println("| Visit again!                               |");
	                  System.out.println("----------------------*");
	              }
	          }
	          //payment using card
	          else if(transaction==2){
	              System.out.println("Enter the Accout number");
	              Long card=sc.nextLong();
	              System.out.println("Enter PIN ");
	              int pin=sc.nextInt();
	              //Displaying Bill for successful Payment using cash
	              System.out.println("-------------------------");
	              System.out.println("            ~ St.REGIS HOTEL ~");
	              System.out.println("  Payment successful!");
	              System.out.println("  Payment Done On : "+time());
	              System.out.println("  Thank You!");
	              System.out.println("-------------------------");
	          }
	      }
	      else{
	          System.out.println("----------------------*");
	          System.out.println("| Payment Not Received!                     |");
	          System.out.println("| Visit again!                              |");
	          System.out.println("----------------------*");
	      }
	}
	public static String time(){
	      SimpleDateFormat sd = new SimpleDateFormat("dd:MM:yyyy  hh:mm:ss");
	      Date date = new Date();
	      sd.setTimeZone(TimeZone.getTimeZone("IST"));
	      return sd.format(date);
	  }
}