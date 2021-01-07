import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class FileSystemClient {
	
	private static String clientName = "Client1";
	private static final String DEFAULT_ADDRESS = "172.17.91.2";
	public static void main(String[] args) throws IOException {
		String serverIP = args.length == 1 ? args[0] : DEFAULT_ADDRESS;
		Socket socket = null;
		try {
			socket = new Socket(serverIP, 8003);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		PrintWriter pr = new PrintWriter(socket.getOutputStream(),true);
		BufferedReader bf = new BufferedReader( new InputStreamReader(socket.getInputStream()));
		pr.println(clientName);
		
		String command;
		Scanner input = new Scanner(System.in);
		long startTime;
		while(true){
			System.out.println("Enter Command: ");
			command = input.nextLine();  // Read user command
			startTime = System.nanoTime();
			if(command.equals("load test")) {
				LoadTest loadTest = new LoadTest(pr, bf);
				loadTest.performLoadTest();
			}
			else {
				pr.println(command);
				String feedBack = bf.readLine();
				System.out.println(feedBack);
			}
			if(command.equals("exit")){
				socket.close();
				input.close();
				break;
			}
			long elapsedTime = System.nanoTime() - startTime;
			System.out.println("Total elapsed tcp response time in nanoseconds: " + elapsedTime);
		}
	}
	
	
	
	
}
