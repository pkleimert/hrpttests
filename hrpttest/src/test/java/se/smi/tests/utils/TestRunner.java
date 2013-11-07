/*
 * @author Pekka Kleimert (pkleimert@gmail.com)
 * Run application with one argument which is the baseurl. If none is provided default python local webserver is used.
 * Users in the test must exist as registered users with idcode and intake survey filled in.
 * Also test assumes users are adults ie. over 16 years.
 * 
 */

package se.smi.tests.utils;

import java.util.Date;

	

public class TestRunner implements Runnable{
	
	private String user;
	private String password;
	private String gid;
	private static final int NO_SYMP = 0;
	private static final int ILI = 1;
	private static final int COLD = 2;
	private int symptom = -1;
	private static String BASEURL = "http://127.0.0.1:8000";
	
	public TestRunner(String user,String pw,String gid,int symptom){
		this.user = user;
		this.password = pw;
		this.gid = gid;
		this.symptom = symptom;
	}
	
	public static void main(String[] args) throws Exception{
		if(args.length == 1){
			BASEURL = args[0];
		}
		System.out.println("Running test with baseurl=" + BASEURL);
		
		//Dev env
		new Thread(new TestRunner("bjornbuse_1","password","6b2839d6-26ea-460d-9265-955ec4cb14c9",TestRunner.NO_SYMP)).start();
		//new Thread(new TestRunner("bjornbuse_2","password","e4579f7b-ec62-4c17-9c74-7bd43c59243f",TestRunner.ILI)).start();
		//new Thread(new TestRunner("bjornbuse_3","password","4cd34534-4431-4822-a3af-d714a440325a",TestRunner.COLD)).start();
		
		//Smi test env
		//new Thread(new TestRunner("test9984","test9984","51a77cf8-22bb-479a-a494-dfd042d2bd87",TestRunner.NO_SYMP)).start();
		//new Thread(new TestRunner("pekka_test3","password","3f120978-7eb1-421a-a300-c97934fe8b39",TestRunner.ILI)).start();
		//new Thread(new TestRunner("pekka_test_98","password","1e40d265-b5af-4763-bff1-08ad18dede4a",TestRunner.COLD)).start();
		
	}
	
	public void report (){
		LoginReportSymptomLogout test = null;
		try{
		test = 
				new LoginReportSymptomLogout(user,password,gid,BASEURL);
		test.setUpInstance();
		test.loginInstance();
		switch (symptom){
			case TestRunner.NO_SYMP:
				test.reportNoSymptomInstance();
				break;
			case TestRunner.ILI:
				test.reportIliInstance();
				break;
			case TestRunner.COLD:
				test.reportColdInstance();
				break;
		}
		}
		catch(Exception e){
		e.printStackTrace();	
		}
		
		finally{
			try{
			test.logoutInstance();
			test.tearDownInstance();
			}
			catch(Exception e ){
				e.printStackTrace();
			}
		}
	}
	
	
	public void run(){
		System.out.println("start " + new Date()+" thread: "+Thread.currentThread().getId());
		report();
		System.out.println("stop " + new Date()+" thread: "+Thread.currentThread().getId());
	}
}
