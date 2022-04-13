package MySQLDemo;
import java.util.*;
import java.sql.*;
public class ClientController2 {
	public static Scanner scan = new Scanner(System.in);
	public static void main(String[] args) {
		try {
			Connection c =  null;
			System.out.println("Enter 1 to load the Driver class or 2 to register the Driver class");
			if((scan.nextInt())== 1) {
				Class.forName("com.mysql.jdbc.Driver");
			}else {
				Driver d = new com.mysql.jdbc.Driver();
				DriverManager.registerDriver(d);                                                  
			}
			System.out.println("Driver Class Loaded");
			c = DriverManager.getConnection("jdbc:mysql://localhost:3306/jfsd","root","root");
			System.out.println("Connection established\n");
			do {
				System.out.println("---------Menu---------\n1)Create\n2)Alter\n3)Insert\n4)Update\n5)Delete\n6)Truncate\n7)Drop\n8)Select\n9)Rename\n10)Scrollable\n11)Updatable\nEXIT");
				int ID;
				String name, branch;
				switch(scan.nextInt()) {
				case 1:
					System.out.println("Enter 1 for Statement, 2 for PreparedStatement");
					if((scan.nextInt()) == 1) {
						Statement s = c.createStatement();
						int n = s.executeUpdate("create table student(sid integer primary key, sname varchar(50))");
					}else {
						PreparedStatement ps = c.prepareStatement("create table student(sid integer primary key, sname varchar(50))");
						ps.executeUpdate();
					}
					System.out.println("Table student created..");
					break;
				case 2:
					System.out.println("Enter 1 for Statement, 2 for PreparedStatement");
					branch = "CSE";
					if((scan.nextInt()) == 1) {	
                        Statement s = c.createStatement();
						s.executeUpdate("Alter table student add(branch varchar(15) default 'CSE')");
					}else {
						PreparedStatement ps = c.prepareStatement("Alter table student add(branch varchar(15) default 'CSE')");
						ps.executeUpdate();
					}
					System.out.println("Altered table student added column branch......");
					break;
				case 3:
					
					System.out.println("Enter ID:");
					ID = scan.nextInt();
					System.out.println("Enter Name:");
                    name = scan.nextLine();
                    name = scan.nextLine();
                    System.out.println("Enter 1 for Statement, 2 for PreparedStatement");
					if((scan.nextInt()) == 1) {
                    	Statement s = c.createStatement();
						s.executeUpdate("insert into student(sid,sname) values("+ID+",'"+name+"')");
					}else {
						PreparedStatement ps = c.prepareStatement("insert into student(sid,sname) values(?,?)");
						ps.setInt(1,ID);
						ps.setString(2, name);
						ps.execute();	
					}
					System.out.println("Values inserted into student table.....");
					break;
					
				case 4:
					System.out.println("Enter ID:");
					ID = scan.nextInt();
					System.out.println("Enter Name:");
                    name = scan.nextLine();
                    name = scan.nextLine();
                    System.out.println("Enter 1 for Statement, 2 for PreparedStatement");
                    if((scan.nextInt()) == 1) {
                    	Statement s = c.createStatement();           //String              Integer
						s.executeUpdate("update student set sname = '"+name+"' where sid = "+ID+"");												
					}else {
						PreparedStatement ps = c.prepareStatement("update student set sname = ? where sid = ?");
						ps.setString(1,name);
						ps.setInt(2, ID);
						ps.executeUpdate();						
					}
                    System.out.println("Student record update....");
					break;
				case 5:
					System.out.println("Enter ID:");
					ID = scan.nextInt();
					System.out.println("Enter 1 for Statement 2 for PreparedStatement");
					if((scan.nextInt()) == 1) {
                    	Statement s = c.createStatement();
						s.executeUpdate("delete from student where sid = "+ID+"");												
					}else {
						PreparedStatement ps = c.prepareStatement("delete from student where sid = ?");
						ps.setInt(1, ID);
						ps.executeUpdate();							
					}
					System.out.println("Student record deleted from table..");
					break;                  
				case 6:
					System.out.println("Enter 1 for Statement 2 for PreparedStatement");
                    if((scan.nextInt()) == 1) {
                    	Statement s = c.createStatement();
						s.executeUpdate("truncate table student");						
					}else {
						PreparedStatement ps = c.prepareStatement("truncate table student");
						ps.executeUpdate();													
					}
                    System.out.println("All records deleted.....");
					break;
				case 7:
					System.out.println("Enter 1 for Statement 2 for PreparedStatement");
                    if((scan.nextInt()) == 1) {
                    	Statement s = c.createStatement();
						s.executeUpdate("drop table student");
					}else {
						PreparedStatement ps = c.prepareStatement("drop table student");
						ps.executeUpdate();														
					}
                    System.out.println("Table student deleted.....");
					break;
				case 8:
					System.out.println("Enter 1 for Statement 2 for PreparedStatement");
                    ResultSet rs;
					if((scan.nextInt()) == 1) {
                    	Statement s = c.createStatement();
						rs = s.executeQuery("select * from student");
					}else {
						PreparedStatement ps = c.prepareStatement("select * from student");
						rs = ps.executeQuery();	
					}
					int co = 1;
					while(rs.next()) {
						System.out.println("Record "+co+"\n");
						System.out.println("ID: "+rs.getInt("sid"));
						System.out.println("Name: "+rs.getString("sname"));
						System.out.println();
						co++;
					}
					break;
				case 9:
					System.out.println("Enter 1 for Statement 2 for PreparedStatement");
                    if((scan.nextInt()) == 1) {
                    	Statement s = c.createStatement();
						s.executeUpdate("alter table student rename to stu");
					}else {
						PreparedStatement ps = c.prepareStatement("alter table student rename to stu");
						ps.executeUpdate();		
					}
                    System.out.println("Table student renamed......");
					break;
					
				case 10:
					System.out.println("Enter 1 for Statement 2 for PreparedStatement");
                    if((scan.nextInt()) == 1) {
                        rs = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery("select * from student");
					}else {
					    rs = c.prepareStatement("select * from student",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY).executeQuery();	
					}
                    scfun(c,rs);
                    break;
                    
				case 11:
					System.out.println("Enter 1 for Statement 2 for PreparedStatement");
                    if((scan.nextInt()) == 1) {
                    	rs = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery("select sid,sname from student");
					}else {
					    rs = c.prepareStatement("select sid,sname from student",ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery();	
					}
                    upfun(c,rs);
                    break;
	
				default:
					c.close();
					System.exit(0);
				}
				System.out.println("\n");
			}while(true);
		}catch(Exception e) {
			System.out.println(e);
		}
	}
	public static void scfun(Connection c,ResultSet rs) {
		try {
		ResultSet rs2 = c.createStatement().executeQuery("select count(*) from student");
		int total = 0;
		if(rs2.next()) total = rs2.getInt(1);
		boolean b = true;
		do {
			System.out.println("Total Records: "+total+"\n--Scrollable Options--\n1)Next\n2)First\n3)Last\n4)Previous\n5)Absolute\n6)Relative\n7)DisplayAll\nExit\nEnter an option:");
			switch(scan.nextInt()) {
			case 1:
				rs.next();
				System.out.println("\n"+rs.getInt(1)+","+rs.getString(2));
				System.out.println("Current Row Location:"+rs.getRow());
				break;
			case 2:
				rs.first();
				System.out.println("\n"+rs.getInt(1)+","+rs.getString(2));
				System.out.println("Current Row Location:"+rs.getRow());
				break;
			case 3:
				rs.last();
				System.out.println("\n"+rs.getInt(1)+","+rs.getString(2));
				System.out.println("Current Row Location:"+rs.getRow());
				break;
			case 4:
				rs.previous();
				System.out.println("\n"+rs.getInt(1)+","+rs.getString(2));
				System.out.println("Current Row Location:"+rs.getRow());
				break;
			case 5:
				System.out.println("\nEnter the row number:");
				rs.absolute(scan.nextInt());
				System.out.println(rs.getInt(1)+","+rs.getString(2));
				System.out.println("Current Row Location:"+rs.getRow());
				break;
			case 6:
				System.out.println("\nYou are at row: "+rs.getRow()+"\nEnter the relative row number:");
				rs.relative(scan.nextInt());
				System.out.println(rs.getInt(1)+","+rs.getString(2));
				System.out.println("Current Row Location:"+rs.getRow());
				break;
			case 7:
				System.out.println();
				while(rs.next()){
					System.out.println("Current Row Location:"+rs.getRow());
			        System.out.println(rs.getInt(1)+","+rs.getString(2));
			        System.out.println();
				}
				break;
			default:
				b = false;
				break;
			}
			System.out.println();
		}while(b);
		}catch(Exception e) {System.out.println(e);}
	}
	public static void upfun(Connection c,ResultSet rs) {
		try {
		ResultSet rs2 = c.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_UPDATABLE).executeQuery("select sid,sname from student");
		int total = 0;
		rs2.last();total = rs2.getRow();
		boolean b = true;
		do {
			System.out.println("Total Records: "+total+"\n--Updatable Options--\n1)Insert\n2)Delete\n3)Update\nExit\nEnter an option:");
			switch(scan.nextInt()) {
			case 1:
				//CHANGING CURSOR TO INSERT MODE
				rs.moveToInsertRow();
				System.out.println("Enter ID:");
				int ID = scan.nextInt();
				System.out.println("Enter Name:");
                String name = scan.nextLine();
                name = scan.nextLine();
                //SETTING A DATA SET
				rs.updateInt(1, ID);
				rs.updateString(2,name);
				//INSERTING INTO TABLE
				rs.insertRow();
				System.out.println("Recors inserted successfully....");
				//GETTING CURSOR BACK TO VIEW MODE
				rs.moveToCurrentRow();
				total += 1;
				break;
			case 2:
				System.out.println("Enter row number:");
				//GETTING A ROW INTO RESULT SET
				rs.absolute(scan.nextInt());
				//DELETING THE ROW FROM TABLE
				rs.deleteRow();
				System.out.println("Recors deleted successfully....");
				total -= 1;
				break;
			case 3:
				System.out.println("Enter row number:");
				//GETTING A ROW INTO RESULT SET
				rs.absolute(scan.nextInt());
				System.out.println("Enter Name:");
                name = scan.nextLine();
                name = scan.nextLine();
                //UPDATING THE DATA IN RESPRCTIVE ROW
                rs.updateString(2, name);
				rs.updateRow();
				System.out.println("Recors updated successfully....");
				break;
			default:
				b = false;
				break;
			}
			System.out.println();
		}while(b);
		}catch(Exception e) {System.out.println(e);}
	}
}
