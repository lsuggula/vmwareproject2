package project2;

public class LogCollectionManager {

public static void main(String[] args) {
	
		try{
			Runnable task= new VMPerfThread("test2-logstash");
			Thread t=new Thread(task);
			t.start();
			t.join();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			finally{
			System.out.println("in finally");
			}
		}
}
