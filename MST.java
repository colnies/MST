package apps;

import structures.*;
import java.util.ArrayList;
import java.util.Iterator;

import apps.PartialTree.Arc;

public class MST {
	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {
	
		Vertex[] tmp = graph.vertices;
		PartialTreeList L=new PartialTreeList();

		for(int i=0;i<tmp.length;i++){
			Vertex v=tmp[i];
			
			PartialTree T = new PartialTree(v);
			
			//Adding arcs to T's heap//
			Vertex.Neighbor vn= v.neighbors;
				do{
				
					PartialTree.Arc Tv=new PartialTree.Arc(v,vn.vertex,vn.weight);
					T.getArcs().insert(Tv);
					vn=vn.next;
			
				}while(vn!=null);
			
			L.append(T);
			
		}
		return L;
	}
	
	/*Method used to deal with duplicates if needed (not needed for assignment)
	 private static void duplicates(MinHeap<PartialTree.Arc> x1,MinHeap<PartialTree.Arc> x2){
		MinHeap<PartialTree.Arc> ck=new MinHeap<PartialTree.Arc>();
		MinHeap<PartialTree.Arc> res= new MinHeap<PartialTree.Arc>();
		
		 while(!x1.isEmpty()){
			 PartialTree.Arc tmp = x1.deleteMin();
			 ck.insert(tmp);
			 
			 while(!x2.isEmpty()){
				 PartialTree.Arc cmp = x2.deleteMin();
				 	if(tmp.v1!=cmp.v2||tmp.v2!=cmp.v1)
				 		res.insert(cmp);	 	
			 }while(!res.isEmpty()){
				 x2.insert(res.deleteMin());
			 }
		 }while(!ck.isEmpty()){
			 x1.insert(ck.deleteMin());
		 }
	}
	*/
	
	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {
		//ArrayList of result Arcs//
		ArrayList<PartialTree.Arc> AL = new ArrayList<PartialTree.Arc>();
		
		//Runs until one partial tree left, then algorithm is finished//
		while(ptlist.size()!=1){
			PartialTree ptr= ptlist.remove();
			
				//If vertice is an island//
				if(ptr.getArcs().isEmpty())
					return null;
				
			//Searches until correct Arc and vertices are found//
			while(!ptr.getArcs().isEmpty()){
				PartialTree.Arc mtc = ptr.getArcs().deleteMin();
				Vertex x1 = mtc.v1;
				Vertex x2 = mtc.v2;
					
					//When correct Arc with vertices is found// 
					if(x1.getRoot()!=x2.getRoot()){						
					
						AL.add(mtc);
						PartialTree rm = ptlist.removeTreeContaining(x2.getRoot());
						
						//Checking for duplicate arcs between two trees//
						//duplicates(ptr.getArcs(), rm.getArcs());//
						
						ptr.merge(rm);
						ptlist.append(ptr);
						
						break;
					}
			}
			
		}
		return AL;
	}
}
