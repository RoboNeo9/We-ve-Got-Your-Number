import java.util.*;
public class GotNums
{
	static public Calc calculator=new Calc();
	static public Two[] answerList=new Two[100];
	static public ArrayList<Double> nums=new ArrayList<Double>(); //List that contains all numbers 1-100 inclusive and the operations (if existent) to get there
	public static void main(String args[])
	{

			int i1=3,i2=3,i3=3,i4=3; //Base numbers (edit within range 1-9 inclusive); order is irrelavent
			nums.add((double)i1);
			nums.add((double)i2);
			nums.add((double)i3);
			nums.add((double)i4);
			System.out.println("Nums start: "+nums);
			calculator.calculate(nums,"Start",new OperationList());
			System.out.println("\n\n//////finished///////\n\n");
			for(int i=0; i<answerList.length; i++)
				if(answerList[i]==null)
					answerList[i]=new Two(i+1,new OperationList());
			System.out.println(GotNums.print(answerList));
	}
	public static String print(Two[] list)
	{
		String ret="";
		for(Two i:list)
			ret=ret+i.toString()+"\n";
		return ret;
	}

	static public Two[] getList()
	{
		return answerList;
	}

	static public ArrayList<Double> getNums()
	{
		return nums;
	}
}

class Calc
{
	void calculate(ArrayList<Double> nums, String operation, OperationList opList)
	{
		for(int i=0;i<nums.size();i++)
			normalize(i,nums);
		if(nums.size()==1) //When all operations are depleted
		{
			if(!operation.equals("factsec"))
			{
				if(!operation.equals("end"))
				{
					ArrayList<Double> temp=GotNums.calculator.clone(nums);
					OperationList temp2=GotNums.calculator.clone(opList);
					calculate(temp,"end",temp2);
				}
				if(nums.get(0)<5&&nums.get(0)>2&&isInt(nums.get(0))) //checks if another factorial can happen to add a number
				{
					opList.add("factorial0");
					ArrayList<Double> temp=GotNums.calculator.clone(nums);
					OperationList temp2=GotNums.calculator.clone(opList);
					calculate(temp,"factsec",temp2);
				}
			}
			if(operation.equals("factsec"))
			{
				nums.set(0,factorial(nums.get(0)));
				OperationList temp2=GotNums.calculator.clone(opList);
				Two temp=new Two((nums.get(0)),temp2);
				temp.insert(GotNums.getList());
			}
			if(operation.equals("end"))
			{
				Two temp=new Two(nums.get(0),opList);
				temp.insert(GotNums.getList());
			}
		}
		else if(nums.size()>1 || nums.size()==2)
		{
			int i1=-1;
			int i2=-1;
			double result;
			if(!operation.equals("Start"))
				opList.add(operation);
			if(operation.substring(0,3).equals("log"))
			{
				i1=Integer.parseInt(operation.substring(3,4));
				i2=Integer.parseInt(operation.substring(4,5));
				result=Math.log(nums.get(i2))/Math.log(nums.get(i1));
				nums.set(i1,result);
				nums.remove(i2);
			}

			else if(operation.substring(0,3).equals("con"))
			{
				i1=Integer.parseInt(operation.substring(3,4));
				i2=Integer.parseInt(operation.substring(4,5));
				try
				{
					result=Double.parseDouble(""+(int)(double)nums.get(i1)+(double)nums.get(i2));
					nums.set(i1,result);
					nums.remove(i2);
				}
				catch(Exception e)
				{
					nums=new ArrayList<Double>();
				}

			}

			else if(operation.substring(0,3).equals("add"))
			{
				i1=Integer.parseInt(operation.substring(3,4));
				i2=Integer.parseInt(operation.substring(4,5));
				result=nums.get(i1)+nums.get(i2);
				nums.set(i1,result);
				nums.remove(i2);
			}

			else if(operation.length()>4&&operation.substring(0,5).equals("power"))
			{
				i1=Integer.parseInt(operation.substring(5,6));
				i2=Integer.parseInt(operation.substring(6,7));
				result=Math.pow(nums.get(i1),nums.get(i2));
				nums.set(i1,result);
				nums.remove(i2);
			}

			else if(operation.length()>5&&operation.substring(0,6).equals("divide"))
			{
				i1=Integer.parseInt(operation.substring(6,7));
				i2=Integer.parseInt(operation.substring(7,8));
				result=nums.get(i1)/nums.get(i2);
				nums.set(i1,result);
				nums.remove(i2);
			}

			else if(operation.length()>7&&operation.substring(0,8).equals("subtract"))
			{
				i1=Integer.parseInt(operation.substring(8,9));
				i2=Integer.parseInt(operation.substring(9,10));
				result=nums.get(i1)-nums.get(i2);
				nums.set(i1,result);
				nums.remove(i2);
			}

			else if(operation.length()>7&&operation.substring(0,8).equals("multiply"))
			{
				i1=Integer.parseInt(operation.substring(8,9));
				i2=Integer.parseInt(operation.substring(9,10));
				result=nums.get(i1)*nums.get(i2);
				nums.set(i1,result);
				nums.remove(i2);
			}

			else if(operation.length()>8&&operation.substring(0,9).equals("factorial"))
			{
				i1=Integer.parseInt(operation.substring(9,10));
				result=factorial(nums.get(i1));
				nums.set(i1,result);
			}

			if(nums.size()>1)
			{
				for(int j=0;j<nums.size();j++) //log
					for(int k=0;k<nums.size();k++)
					{
						if(j!=k && nums.get(j)!=1 && nums.get(j)>0 && nums.get(k)>0)
						{
							ArrayList<Double> temp=GotNums.calculator.clone(nums);
							OperationList temp2=GotNums.calculator.clone(opList);
							calculate(temp,"log"+j+k,temp2);
						}
					}

				for(int j=0;j<nums.size();j++) //con
					for(int k=0;k<nums.size();k++)
						if(j!=k && isInt(nums.get(j)) && !(nums.get(k).equals(Double.POSITIVE_INFINITY) || nums.get(k).equals(Double.NEGATIVE_INFINITY))) //Dont allow nums.get(k) or nums.get(j)=infinity or NaN
						{
							if(nums.get(j)>=0&&nums.get(k)>=0)
							{
								ArrayList<Double> temp=GotNums.calculator.clone(nums);
								OperationList temp2=GotNums.calculator.clone(opList);
								calculate(temp,"con"+j+k,temp2);
							}
						}

				for(int j=0;j<nums.size();j++) //add
					for(int k=0;k<nums.size();k++)
						if(j!=k)
						{
							ArrayList<Double> temp=GotNums.calculator.clone(nums);
							OperationList temp2=GotNums.calculator.clone(opList);
							calculate(temp,"add"+j+k,temp2);
						}

				for(int j=0;j<nums.size();j++) //power
					for(int k=0;k<nums.size();k++)
						if(j!=k&&!(nums.get(k)<=0&&nums.get(j)==0)&&!(nums.get(j)<0&&!isInt(nums.get(k))))
						{
							ArrayList<Double> temp=GotNums.calculator.clone(nums);
							OperationList temp2=GotNums.calculator.clone(opList);
							calculate(temp,"power"+j+k,temp2);
						}

				for(int j=0;j<nums.size();j++) //divide
					for(int k=0;k<nums.size();k++)
						if(j!=k&&nums.get(k)!=0)
						{
							ArrayList<Double> temp=GotNums.calculator.clone(nums);
							OperationList temp2=GotNums.calculator.clone(opList);
							calculate(temp,"divide"+j+k,temp2);
						}

				for(int j=0;j<nums.size();j++) //subtract
					for(int k=0;k<nums.size();k++)
						if(j!=k)
						{
							ArrayList<Double> temp=GotNums.calculator.clone(nums);
							OperationList temp2=GotNums.calculator.clone(opList);
							calculate(temp,"subtract"+j+k,temp2);
						}

				for(int j=0;j<nums.size();j++) //multiply
					for(int k=0;k<nums.size();k++)
						if(j!=k)
						{
							ArrayList<Double> temp=GotNums.calculator.clone(nums);
							OperationList temp2=GotNums.calculator.clone(opList);
							calculate(temp,"multiply"+j+k,temp2);
						}

				for(int j=0;j<nums.size();j++) //factorial
					if(nums.get(j)==0||(nums.get(j)<=18&&nums.get(j)>2&&isInt(nums.get(j))))
					{
						ArrayList<Double> temp=GotNums.calculator.clone(nums);
						OperationList temp2=GotNums.calculator.clone(opList);
						calculate(temp,"factorial"+j,temp2);
					}
			}
			if(nums.size()==1)
			{
				ArrayList<Double> temp=GotNums.calculator.clone(nums);
				OperationList temp2=GotNums.calculator.clone(opList);
				calculate(temp,"fin2",temp2);
			}
		}
	}
		

	double factorial(double n)
	{
		if(n==0)
			return 1;	//factorial function for reference and later use. Recursive for speed. Might take too much memory even though it uses little o.o
		if(n<3)
			return n;
		return n*factorial(n-1);
	}
	void normalize(int i, ArrayList<Double> ar)
	{
		int rounded=(int)Math.round(ar.get(i));
		boolean isInt=false;
		if(Math.abs(rounded-ar.get(i))<0.001)
		{
			isInt=true;
			ar.set(i,(double)rounded);
		}
	}
	boolean isInt(double d)
	{
		return (int)d==d;
	}
	ArrayList<Double> clone(ArrayList<Double> ne)
	{
		ArrayList<Double> temp=new ArrayList<Double>();
		for(Double i:ne)
			temp.add((new Double((double)i)));
		return temp;
	}
	OperationList clone(OperationList opl)
	{
		OperationList temp=new OperationList();
		for(String i: opl.getAnswer())
			temp.add(new String(i));
		return temp;
	}
}

class Two //Object used to store number and operations to get there together
{
	int number;
	OperationList list;
	Two(double n, OperationList opl) //Initialize with number found and operations required to get there
	{
		if(n==(int)n){
			number=(int)n;
			list=GotNums.calculator.clone(opl);
		}
		else
		{
			number=-1;
			list=new OperationList();
		}
	}
	
	OperationList getOpList()
	{
		return list;
	}

	int getNumber()
	{
		return number;
	}

	void insert(Two[] tList) //used to insert a Two object into a Two list in numerical order. NO DUPLICATES!
	{
		if(GotNums.calculator.isInt(this.getNumber())&&this.getNumber()>0&&this.getNumber()<=100)
			if(tList[this.getNumber()-1]==null)
			{
				tList[this.getNumber()-1]=this;
			}
	}

	public String toString()
	{
		
		return this.getNumber()+": "+this.getOpList().toString()+"\n";
	}
}

class OperationList //Object to contain list of operations to get to a certain number as String Objects
{
	private ArrayList<String> answer;

	OperationList()
	{
		answer=new ArrayList<String>();
	}

	void add(String op)
	{
		answer.add(op);
	}

	ArrayList<String> getAnswer()
	{
		return answer;
	}
	int getSize()
	{
		return answer.size();
	}
	public String toString() //Places parentheses after every operation to ensure a generic and nondebatable interpretation of the findings
	{
		if(getAnswer().size()==0)
			return "UR SCREWED M8";
		String out="";
		ArrayList<String> nummies=new ArrayList<String>();
		for(Double d: GotNums.getNums())
			nummies.add((int)(double)d+"");
		for(String i: getAnswer())
		{
			String temp="(";
			out="("+out;
			int i1=-1;
			int i2=-1;
			if(i.substring(0,3).equals("log"))
			{
				i1=Integer.parseInt(i.substring(3,4));
				i2=Integer.parseInt(i.substring(4,5));
				temp+="log_"+nummies.get(i1)+"("+nummies.get(i2)+")";
			}

			if(i.substring(0,3).equals("add"))
			{
				i1=Integer.parseInt(i.substring(3,4));
				i2=Integer.parseInt(i.substring(4,5));
				temp+=nummies.get(i1)+"+"+nummies.get(i2);
			}

			if(i.substring(0,3).equals("con"))
			{
				i1=Integer.parseInt(i.substring(3,4));
				i2=Integer.parseInt(i.substring(4,5));
				temp+=nummies.get(i1)+"|"+nummies.get(i2);
			}

			if(i.length()>4&&i.substring(0,5).equals("power"))
			{
				i1=Integer.parseInt(i.substring(5,6));
				i2=Integer.parseInt(i.substring(6,7));
				temp+=nummies.get(i1)+"^"+nummies.get(i2);
			}

			if(i.length()>5&&i.substring(0,6).equals("divide"))
			{
				i1=Integer.parseInt(i.substring(6,7));
				i2=Integer.parseInt(i.substring(7,8));
				temp+=nummies.get(i1)+"/"+nummies.get(i2);
			} 

			if(i.length()>7&&i.substring(0,8).equals("subtract"))
			{
				i1=Integer.parseInt(i.substring(8,9));
				i2=Integer.parseInt(i.substring(9,10));
				temp+=nummies.get(i1)+"-"+nummies.get(i2);
			} 

			if(i.length()>7&&i.substring(0,8).equals("multiply"))
			{
				i1=Integer.parseInt(i.substring(8,9));
				i2=Integer.parseInt(i.substring(9,10));
				temp+=nummies.get(i1)+"*"+nummies.get(i2);
			}

			if(i.length()>8&&i.substring(0,9).equals("factorial"))
			{
				i1=Integer.parseInt(i.substring(9));
				temp+=nummies.get(i1)+"!";
			} 
			temp+=")";
			nummies.set(i1, temp);
			if(i2!=-1)
				nummies.remove(i2);
		}
		return nummies.get(0);
	}
}