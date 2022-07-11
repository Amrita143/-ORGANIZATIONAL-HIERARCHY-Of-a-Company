import java.io.*; 
import java.util.*;






// Tree node
class Node {
  public int key,level; 
  Vector<Node> child = new Vector<>();
public int AVLheight;
public Node AVLleft;
public Node AVLright;
public Node parent;


 Node(int d)
 {
     
     AVLright=null;
     AVLleft=null;
     key=d;
     AVLheight=1;
 }
}


public class OrgHierarchy implements OrgHierarchyInterface{

//root node
Node root;

AVLtree Atree = new AVLtree();



public boolean isEmpty() {
	//your implementation 
	if(root==null)
	return true;
	else
	return false;
} 

public int size(){
	//your implementation
	if(root==null)
	return 0;
	else
	return size_of_subtree(root); 

   /* if(root==null)
	return 0;
	else
    return Atree.AVLsize(Atree.AVLroot); */

	
}

public int level(int id) throws IllegalIDException{

     if(root==null)
     throw new IllegalIDException("first hire owner");
     else if(!Atree.AVLsearch(Atree.AVLroot,id))
     throw new IllegalIDException("Id doesn't exist");
    else{
     Node employee=Atree.getNode_fromID(Atree.AVLroot, id);
     return employee.level; 
    }
} 

public void hireOwner(int id) throws NotEmptyException{ 

     if(root!=null) 
	throw new NotEmptyException("Owner already exist");
	else if(root==null)
	{ root=new Node(id); 
       root.level=1; 
      
      Atree.AVLroot= Atree.insert(Atree.AVLroot,root); 
    }
	
   	
}

public void hireEmployee(int id, int bossid) throws IllegalIDException{
	//your implementation
    if(root==null)
    throw new IllegalIDException("first hire a owner");
    else if(Atree.AVLsearch(Atree.AVLroot,id))
    throw new IllegalIDException("id already exists");
    else if(!Atree.AVLsearch(Atree.AVLroot,bossid))
    throw new IllegalIDException("bossid doesn't exists. so can't hire employee under this boss");
    else
   { 
    Node employee = new Node(id);
   
    employee.parent = Atree.getNode_fromID(Atree.AVLroot,bossid);
    employee.level=Atree.getNode_fromID(Atree.AVLroot,bossid).level+1;
    Atree.getNode_fromID(Atree.AVLroot,bossid).child.add(employee);

   Atree.AVLroot=Atree.insert(Atree.AVLroot,employee);
   }
} 

public void fireEmployee(int id) throws IllegalIDException{
	//your implementation
 	if(root==null)  
     throw new IllegalIDException("first initiate the organisation");
     else if(!Atree.AVLsearch(Atree.AVLroot,id))
     throw new IllegalIDException("Id doesn't exist");
    
     else if(!Atree.getNode_fromID(Atree.AVLroot, id).child.isEmpty())
     throw new IllegalIDException("this is not an leaf node which we want to delete");
     
     else
    { Node employee = Atree.getNode_fromID(Atree.AVLroot, id);
        if(employee==root)
        throw new IllegalIDException("owner can't be fired out");
        else {
            
     Atree.AVLroot= Atree.deleteNode(Atree.AVLroot, employee);
     employee.parent.child.remove(employee); 
          }
    }
}
public void fireEmployee(int id, int manageid) throws IllegalIDException{
	//your implementation
    if(root==null)
     throw new IllegalIDException("first initiate the organisation");
     else if(!Atree.AVLsearch(Atree.AVLroot,id))
     throw new IllegalIDException("Id doesn't exist");
     else if(!Atree.AVLsearch(Atree.AVLroot,manageid))
     throw new IllegalIDException("Id doesn't exist");
     else 
    
   { 
     Node employee = Atree.getNode_fromID(Atree.AVLroot, id);
    Node colleague = Atree.getNode_fromID(Atree.AVLroot, manageid);

    if(employee==root)
    throw new IllegalIDException("owner can't be fired out");
    else if(!(employee.level==colleague.level))
    throw new IllegalIDException("employee & colleague doesn't havesame level");
    else
   { for(int i=0;i<employee.child.size();i++)
       { 
           colleague.child.add(employee.child.get(i));
           employee.child.get(i).parent=colleague;
       }
    
    Atree.AVLroot=Atree.deleteNode(Atree.AVLroot, employee);
    employee.parent.child.remove(employee);
   }
 }
} 

public int boss(int id) throws IllegalIDException{
	//your implementation
    if(root==null)
    throw new IllegalIDException("first hire owner");
    else if(!Atree.AVLsearch(Atree.AVLroot,id))
    throw new IllegalIDException("id not found");
    else
    {
        if(id==root.key)
        return -1;
        else{
            Node employee = Atree.getNode_fromID(Atree.AVLroot, id);
            return employee.parent.key;
        }
    } 
	 
}

public int lowestCommonBoss(int id1, int id2) throws IllegalIDException{
	//your implementation
    if(root==null)
    throw new IllegalIDException("first initiate the organisation");
    else if(!Atree.AVLsearch(Atree.AVLroot,id1))
    throw new IllegalIDException("Id doesn't exist");
    else if(!Atree.AVLsearch(Atree.AVLroot,id2))
    throw new IllegalIDException("Id doesn't exist");
    else{
        Node employee1= Atree.getNode_fromID(Atree.AVLroot, id1);
        Node employee2 = Atree.getNode_fromID(Atree.AVLroot, id2);
    
        int[] arr1=new int[Atree.AVLsize(Atree.AVLroot)];
        int[] arr2=new int[Atree.AVLsize(Atree.AVLroot)];
        int size1=0;
        int size2=0;
        Node boss1=employee1.parent;
        for(int i=0;boss1!=null;i++)
        {
            arr1[i]=boss1.key;
            size1++;
            boss1 = boss1.parent;
        }
        Node boss2=employee2.parent;
        for(int i=0;boss2!=null;i++)
        {
            arr2[i]=boss2.key;
            size2++;
            boss2 = boss2.parent;
        }
         int count=0; int lcb=0;
        for( int i=0;i<size2;i++)
        {
            for(int j=0;j<size1;j++)
            {
               if( arr1[j]==arr2[i])
               { 
                 lcb = arr1[j];
                 count++;
                 break;
               }
            }
            if(count==1)
            break;

        }
        return lcb;
    }
        
        
        
}
    

    


public String toString(int id) throws IllegalIDException{
	//your implementation
    if(root==null)
    throw new IllegalIDException("first initiate the organisation");
    else if(!Atree.AVLsearch(Atree.AVLroot,id))
     throw new IllegalIDException("Id doesn't exist");
     else{
         Node temp_root = Atree.getNode_fromID(Atree.AVLroot, id);
        if (temp_root == null)
        throw new IllegalIDException("Id does't exist");
 
    // Standard level order traversal code using queue
    
    else
    {
        Queue<Node > q = new LinkedList<>(); // Create a queue
        String s="";
      HeapSort h=new HeapSort();
      int[] arr;
        
        q.add(temp_root); // Enqueue root
    while (!q.isEmpty())
    {
        int n = q.size();
         arr= new int[n];
        int index=0;
        
      
        while (n > 0)
        {
           
            Node p = q.peek();
            arr[index]=q.remove().key;
            index++;
        
            for (int i = 0; i < p.child.size(); i++)
              {  
                  q.add(p.child.get(i));
                  
              }
            n=n-1;

        }
         
         h.sort(arr);
        for(int i=0;i<arr.length;i++)
        {
            s=s+Integer.toString(arr[i])+" ";
        }
         
        //gap between two levels
     }
     
     return s;
   }
  }
 }




public int size_of_subtree( Node parent){
	
	if(parent.child.size()==0)
	return 1;
	else
	{
		int count=0;
      for(int i=0; i<parent.child.size();i++)
	  {
		count = count + size_of_subtree(parent.child.get(i));
	  }
	  return 1+count;
	}
}


}


class AVLtree {
 
  public  Node AVLroot;
 
    //  get the height of the tree
    int height(Node N) {
        if (N == null)
            return 0;
 
        return N.AVLheight;
    }
 
    //  get maximum of two integers
    int max(int a, int b) {
        if(a>b)
        return a;
        else
        return b;
    }
    int min(int a,int b)
    {
        if(a<b)
        return a;
        else 
        return b;
    }
 
    // right rotate subtree rooted with y
   
    Node rightRotate(Node y) {
        Node x = y.AVLleft;
        Node T2 = x.AVLright;
 
        // Perform rotation
        x.AVLright = y;
        y.AVLleft = T2;
 
        // Update heights
        y.AVLheight = max(height(y.AVLleft), height(y.AVLright)) + 1;
        x.AVLheight = max(height(x.AVLleft), height(x.AVLright)) + 1;
 
        // Return new AVLroot
        return x;
    }
 
    //  left rotate subtree rooted with x
    
    Node leftRotate(Node x) {
        Node y = x.AVLright;
        Node T2 = y.AVLleft;
 
        // Perform rotation
        y.AVLleft = x;
        x.AVLright = T2;
 
        //  Update heights
        x.AVLheight = max(height(x.AVLleft), height(x.AVLright)) + 1;
        y.AVLheight = max(height(y.AVLleft), height(y.AVLright)) + 1;
 
        // Return new AVLroot
        return y;
    }
 
    // Get Balance factor of node N
    int balanceFactor(Node N) {
        if (N == null)
            return 0;
 
        return height(N.AVLleft) - height(N.AVLright);
    }
 
    Node insert(Node node, Node N) {
 
        if (node == null)
            return N;
 
        if (N.key < node.key)
            node.AVLleft = insert(node.AVLleft, N);
        else if (N.key > node.key)
            node.AVLright = insert(node.AVLright, N);
        else 
            return node;
 
        node.AVLheight = 1 + max(height(node.AVLleft),
                              height(node.AVLright));
 
        
        int balance = balanceFactor
(node);
 
        if (balance > 1 && N.key < node.AVLleft.key)
            return rightRotate(node);
 
        // Right Right Case
        if (balance < -1 && N.key > node.AVLright.key)
            return leftRotate(node);
 
        // Left Right Case
        if (balance > 1 && N.key > node.AVLleft.key) {
            node.AVLleft = leftRotate(node.AVLleft);
            return rightRotate(node);
        }
 
        // Right Left Case
        if (balance < -1 && N.key < node.AVLright.key) {
            node.AVLright = rightRotate(node.AVLright);
            return leftRotate(node);
        }
 
        return node;
    }
 
    Node minValueNode(Node node)
    {
        Node current = node;
 
        while (current.AVLleft != null)
        current = current.AVLleft;
 
        return current;
    }
 
    Node deleteNode(Node AVLroot, Node N)
    {
        if (AVLroot == null)
            return AVLroot;
 
        if (N.key < AVLroot.key)
            AVLroot.AVLleft = deleteNode(AVLroot.AVLleft, N);
 
        else if (N.key > AVLroot.key)
            AVLroot.AVLright = deleteNode(AVLroot.AVLright,N);
 
        
        else
        {
 
            if ((AVLroot.AVLleft == null) || (AVLroot.AVLright == null))
            {
                Node temp = null;
                if (temp == AVLroot.AVLleft)
                    temp = AVLroot.AVLright;
                else
                    temp = AVLroot.AVLleft;
 
                
                if (temp == null)
                {
                    temp = AVLroot;
                    AVLroot = null;
                }
                    AVLroot = temp; 
            }
            else
            {
 
                Node temp = minValueNode(AVLroot.AVLright);
 
                AVLroot.key = temp.key;
 
                AVLroot.AVLright = deleteNode(AVLroot.AVLright, temp);
            }
        }
 
        if (AVLroot == null)
            return AVLroot;
 
        AVLroot.AVLheight = max(height(AVLroot.AVLleft), height(AVLroot.AVLright)) + 1;
 
        int balance = balanceFactor
(AVLroot);
 
        
        if (balance > 1 && balanceFactor
(AVLroot.AVLleft) >= 0)
            return rightRotate(AVLroot);
 
        if (balance > 1 && balanceFactor
(AVLroot.AVLleft) < 0)
        {
            AVLroot.AVLleft = leftRotate(AVLroot.AVLleft);
            return rightRotate(AVLroot);
        }
 
        // Right Right Case
        if (balance < -1 && balanceFactor
(AVLroot.AVLright) <= 0)
            return leftRotate(AVLroot);
 
        // Right Left Case
        if (balance < -1 && balanceFactor
(AVLroot.AVLright) > 0)
        {
            AVLroot.AVLright = rightRotate(AVLroot.AVLright);
            return leftRotate(AVLroot);
        }
 
        return AVLroot;
    }
  int AVLsize(Node node){
      if(node==null)
      return 0;
      else
      return (AVLsize(node.AVLleft)+1+AVLsize(node.AVLright));

  }
  boolean AVLsearch(Node temp_root,int key)
  {
     
     while(temp_root !=null)
     {
         if(key>temp_root.key)
         temp_root=temp_root.AVLright;
         else if(key<temp_root.key)
         temp_root=temp_root.AVLleft;
         else 
         return true;

     }
     return false;
  }

  Node getNode_fromID(Node temp_root,int id)
  {
    while(temp_root !=null)
    {
        if(id>temp_root.key)
        temp_root=temp_root.AVLright;
        else if(id<temp_root.key)
        temp_root=temp_root.AVLleft;
        else 
        return temp_root;
    }
       return null;

 }

}

 class HeapSort {
    public void sort(int arr[])
    {
        int n = arr.length;
 
        for (int i = n / 2 - 1; i >= 0; i--)
            heapify(arr, n, i);
			
        for (int i = n - 1; i > 0; i--) {
            
            int temp = arr[0];
            arr[0] = arr[i];
            arr[i] = temp;
 
            heapify(arr, i, 0);
        }
    }
 
    void heapify(int arr[], int n, int i)
    {
        int largest = i; 
        int l = 2 * i + 1; 
        int r = 2 * i + 2; 
 
        if (l < n && arr[l] > arr[largest])
            largest = l;
 
        if (r < n && arr[r] > arr[largest])
            largest = r;
 
        
        if (largest != i) {
            int swap = arr[i];
            arr[i] = arr[largest];
            arr[largest] = swap;
 
            
            heapify(arr, n, largest);
        }
    }
}
 class vector{

    Node[] arr= new Node[1];
    int top_index=-1;
 
     public Node[] increaseSize(Node[] arr)
     {
         Node[] newarray = new Node[2*arr.length];
         for(int i=0;i< arr.length;i++)
         {
             newarray[i]=arr[i];
         }
         return newarray;
     }
 
     public void add(Node node){
         top_index++;
         if(top_index>= arr.length)
         arr= increaseSize(arr); 
 
         arr[top_index]=node;
     }
 public boolean isEmpty() {
        if(top_index<0)
            return true;
        else
            return false;
 
      }
      public int size(){
         return top_index+1;
     }
      public void remove(Node node){
          int i;
         for( i=0;i<=top_index;i++)
         {
             if(arr[i]==node)
              break;
         }
         arr[i]=null;
         top_index--;
         
      }
      public Node get(int i){
         return arr[i];
      }
     }
 
      class queue{
         int front=0;int rear=0;
         Node[] arrq= new Node[1];
         vector v=new vector();
 
         public void add(Node node){
            
             if(rear>= arrq.length)
             arrq= v.increaseSize(arrq); 
 
             arrq[rear]=node;
             rear++;
 
         }
         public boolean isEmpty()
         {
             if(front==rear)
             return true;
             else 
             return false;
         }
         public int size()
         {
            return rear-front;
         }
         public Node remove(Node node){
             
             front++;
             return arrq[front-1];
 
         }
         public Node peek(){
             return arrq[rear-1];
         }
        
 
     }

    
 
    