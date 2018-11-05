import java.util.*;
import java.awt.Toolkit;

public class Hanoi
{
	public static void main(String[] args)
	{
		Scanner s = new Scanner(System.in);
		System.out.println("How many rings in your tower?");
		int h = s.nextInt();

		Stack<Integer> a = new LabStack<Integer>();
		Stack<Integer> b = new LabStack<Integer>();
		Stack<Integer> c = new LabStack<Integer>();

		String[][] visual = new String[h][3];
		for(int i = 1; i < h+1; i++)
		{
			visual [i-1][0] = String.valueOf(i);
		}
		for(int i = 0; i < h; i++)
		{
			visual [i][1] = " ";
		}
		for(int i = 0; i < h; i++)
		{
			visual [i][2] = " ";
		}

		/*for(int i = 0; i < h; i++)
		{
			for(int j = 0; j < 3; j++)
			{
				System.out.print(visual[i][j]);
			}
			System.out.println();
		}*/

		int rowpointerA = -1;
		int rowpointerB = h-1;
		int rowpointerC = h-1;

		for(int i = h+1; i > 0; i--)
		{
			a.push(i);
		}
		b.push(h+1);
		c.push(h+1);

		//data collection for tracking significant moves
		int[] previousPlacements = new int[100];
		int placementPointer = 0;
		int placementPointer2 = 0;

		int moveCounter = 0;
		long tStart = 0;
		long tEnd = 0;
		long tDelta = 0;
		double elapsedSeconds = 0;
		boolean flag = false;
		while(flag == false)
		{
			if(moveCounter == 0)
			{
				tStart = System.currentTimeMillis();
			}
			int columnToMove = 0;
			int columnToReceive = 0;
			int columnTopLargest = 0;
			//find largest
			if(a.peek() > b.peek() && a.peek() > c.peek())
			{
				columnTopLargest = 1;
			}
			else if(b.peek() > a.peek() && b.peek() > c.peek())
			{
				columnTopLargest = 2;
			}
			else if(c.peek() > a.peek() && c.peek() > b.peek())
			{
				columnTopLargest = 3;
			}
			int columnSecondLargest = 0;
			//find second largest
			if(a.peek() > b.peek() && a.peek() < c.peek() || a.peek() < b.peek() && a.peek() > c.peek())
			{
				columnSecondLargest = 1;
			}
			else if(b.peek() > a.peek() && b.peek() < c.peek() || b.peek() < a.peek() && b.peek() > c.peek())
			{
				columnSecondLargest = 2;
			}
			else if(c.peek() > a.peek() && c.peek() < b.peek() || c.peek() < a.peek() && c.peek() > b.peek())
			{
				columnSecondLargest = 3;
			}
			moveCounter++;
			if((moveCounter+1) %2 == 0)
			{
				if(moveCounter == 1 && h%2 == 0)
				{
					columnToMove = 1;
					columnToReceive = 2; //if even, move from a to b to start
				}
				else if(moveCounter == 1 && h%2 == 1)
				{
					columnToMove = 1;
					columnToReceive = 3; //if odd, move from a to c to start
				}
				else{
					//find 1
					if(a.peek() == 1)
					{
						columnToMove = 1;
					}
					else if(b.peek() == 1)
					{
						columnToMove = 2;
					}
					else if(c.peek() == 1)
					{
						columnToMove = 3;
					}

					//find 2
					if(a.peek() == 2)
					{
						columnToReceive = 1;
					}
					else if(b.peek() == 2)
					{
						columnToReceive = 2;
					}
					else if(c.peek() == 2)
					{
						columnToReceive = 3;
					}
					else{
						//if no 2, go to either biggest, or second biggest
						//every fourth move, move 1 to biggest value, unless (movecounter +1)/2 is equal to a previous fourth move value.
						if((moveCounter +1)/2 == previousPlacements[placementPointer2])
						{
							columnToReceive = columnSecondLargest;
							placementPointer2++;
							if(placementPointer2 == previousPlacements.length)
							{
								int[] growArr = Arrays.copyOf(previousPlacements, previousPlacements.length * 2);
								previousPlacements = growArr;
							}
						}
						else if((moveCounter + 3)%4 == 0)
						{
							columnToReceive = columnTopLargest;
							if(placementPointer == previousPlacements.length)
							{
								int[] growArr = Arrays.copyOf(previousPlacements, previousPlacements.length * 2);
								previousPlacements = growArr;
							}
							previousPlacements[placementPointer] = moveCounter;
							placementPointer++;
						}
					}
				}
			}
			//every 4th turn 2 moves (starting on 2nd move)
			else if((moveCounter) % 2 == 0)
			{
				columnToMove = columnSecondLargest;
				columnToReceive = columnTopLargest;
			}

			//movement control and visual update
			if((a.peek() < b.peek()) && columnToMove == 1 && columnToReceive == 2)
			{
				visual[rowpointerB][1] = visual[rowpointerA+1][0];
				visual[rowpointerA+1][0] = " ";
				rowpointerA++;
				rowpointerB--;
				b.push(a.pop());
			}
			else if((a.peek() < c.peek()) && columnToMove == 1 && columnToReceive == 3)
			{
				visual[rowpointerC][2] = visual[rowpointerA+1][0];
				visual[rowpointerA+1][0] = " ";
				rowpointerA++;
				rowpointerC--;
				c.push(a.pop());
			}
			else if((b.peek() < a.peek()) && columnToMove == 2 && columnToReceive == 1)
			{
				visual[rowpointerA][0] = visual[rowpointerB+1][1];
				visual[rowpointerB+1][1] = " ";
				rowpointerB++;
				rowpointerA--;
				a.push(b.pop());
			}
			else if((b.peek() < c.peek()) && columnToMove == 2 && columnToReceive == 3)
			{
				visual[rowpointerC][2] = visual[rowpointerB+1][1];
				visual[rowpointerB+1][1] = " ";
				rowpointerB++;
				rowpointerC--;
				c.push(b.pop());
			}
			else if((c.peek() < a.peek()) && columnToMove == 3 && columnToReceive == 1)
			{
				visual[rowpointerA][0] = visual[rowpointerC+1][2];
				visual[rowpointerC+1][2] = " ";
				rowpointerC++;
				rowpointerA--;
				a.push(c.pop());
			}
			else if((c.peek() < b.peek()) && columnToMove == 3 && columnToReceive == 2)
			{
				visual[rowpointerB][1] = visual[rowpointerC+1][2];
				visual[rowpointerC+1][2] = " ";
				rowpointerC++;
				rowpointerB--;
				b.push(c.pop());
			}

			/*for(int i = 0; i < h; i++)
			{
				for(int j = 0; j < 3; j++)
				{
					System.out.print(visual[i][j] + "\t");
				}
				System.out.println();
			}*/
			if(c.peek() == 1 && c.size() == h+1)
			{
				flag = true;
				tEnd = System.currentTimeMillis();
				tDelta = tEnd - tStart;
				elapsedSeconds = tDelta / 1000.0;
			}
		}
		Toolkit.getDefaultToolkit().beep();
		System.out.println("Completed in " + moveCounter + " moves!");
		System.out.println("Completed in " + elapsedSeconds + " seconds!");
	}
}
