package apps;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import structures.Graph;
public class MSTDriver {

	public static void main(String[] args) throws IOException {
		System.out.println("What text file would you like to use?");
		Scanner sc = new Scanner(System.in);
		String s= sc.nextLine();
		
		Graph g =new Graph(s);
		MST m =new MST();
		System.out.println("Initializing...");
		PartialTreeList P=MST.initialize(g);
		System.out.println("Executing...");
		ArrayList<PartialTree.Arc> AL=MST.execute(P);
		
		for(int i=0;i<AL.size();i++){
			PartialTree.Arc ar= AL.get(i);
			System.out.println(ar.toString());
		}
		
		
	}

}
