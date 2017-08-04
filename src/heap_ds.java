import java.io.*;
import java.util.*;

public class heap_ds {

	private int[][] adjacency_matrix; // storing the weight
	private int n, m, ret;
	private int[] position; // position
	private int[] A;
	private int[] key; // key_value
	int s = 0;
	private String input="",output="";
	String list[];

	public static void main(String[] args) throws IOException {
		
		heap_ds mst = new heap_ds();
		mst.input = "input.txt"; // input file
		mst.output = "output.txt"; // output file
		
		Scanner sc = new Scanner(new FileReader(mst.input));
		FileWriter fw=new FileWriter(mst.output);
		PrintWriter pw=new PrintWriter(fw);
		
		String read;
		read = sc.nextLine();
		String temp[] = read.split(" ");
		mst.n = Integer.valueOf(temp[0]);
		mst.m = Integer.valueOf(temp[1]);
		mst.adjacency_matrix = new int[mst.n + 1][mst.n + 1];
		mst.A = new int[mst.n + 1];		
		mst.position = new int[mst.n + 1];
		mst.key = new int[mst.n + 1];
		
		while (sc.hasNextLine()) {
			read = sc.nextLine();
			mst.list = read.split(" ");
			int row=Integer.parseInt(mst.list[0]);
			int column=Integer.parseInt(mst.list[1]);
			
			mst.adjacency_matrix[row][column]=Integer.parseInt(mst.list[2]); // storing weights
			mst.adjacency_matrix[column][row]=Integer.parseInt(mst.list[2]);
			
		}
		
		mst.Prim(mst.n,pw);
		pw.close();

	}

	public void Prim(int vertices, PrintWriter pw) // number of vertices
	{
		int arbitrary_vertex = 1;
		int extract;
		int sumWeight = 0;
		int distance[] = new int[vertices + 1];
		distance[arbitrary_vertex] = 0;
		for (int i = 2; i <= vertices; i++) {
			distance[i] = Integer.MAX_VALUE;
		}
		for (int j = 1; j <= vertices; j++) {
			insert(j, distance[j]);
		}

		HashMap<Integer, Integer> parent = new HashMap<Integer, Integer>();		// to map vertex and its parent
		List<Integer> S = new ArrayList<Integer>();
		int count = 0;
		while (count < vertices) {
			count++;
			extract = extract_min();
			S.add(extract);
			sumWeight = sumWeight + key[extract];				// total weight 
			for (int i = 1; i <= vertices; i++) {
				if (adjacency_matrix[i][extract] != 0) 
				{
					if (!S.contains(i)) {						// to check if that vertex is not visited
						int weight = adjacency_matrix[i][extract];
						if (weight < distance[i]) {
							distance[i] = weight;
							decrease_key(i, distance[i]);
							parent.put(i, extract);
						}
					}
				}
			}
		}
			// printing as required format
		pw.println(sumWeight);
		
		for (int s : S) {
			if (s != 1)
			{
				pw.println(parent.get(s) + " " + s);
				
			}
		}

	}

	public void insert(int v, int key_value)  
	{
		s = s + 1;
		A[s] = v;
		position[v] = s;
		key[v] = key_value;
		heapify_up(s);
	}

	public void heapify_up(int i) {
		int j, temp;
		while (i > 1) {
			j = Math.floorDiv(i, 2);
			if (key[A[i]] < key[A[j]]) {
				temp = A[i];
				A[i] = A[j];
				A[j] = temp;
				position[A[i]] = i;
				position[A[j]] = j;
				i = j;
			} else
				break;
		}

	}

	public int extract_min() {
		ret = A[1];
		A[1] = A[s];
		position[A[1]] = 1;
		s = s - 1;
		heapify_down(1);
		return ret;
	}

	public void decrease_key(int v, int key_value) {
		key[v] = key_value;
		heapify_up(position[v]);
	}

	public void heapify_down(int i) {
		int j, temp;
		while ((2 * i) <= s) {
			if ((2 * i) == s || key[A[2 * i]] <= key[A[(2 * i) + 1]])
				j = 2 * i;
			else
				j = (2 * i) + 1;
			if (key[A[j]] < key[A[i]]) {
				temp = A[i];
				A[i] = A[j];
				A[j] = temp;
				position[A[i]] = i;
				position[A[j]] = j;
				i = j;
			} else
				break;
		}
	}

}
