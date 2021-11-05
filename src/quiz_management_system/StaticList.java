/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quiz_management_system;

import java.io.Serializable;

/**
 *
 * @author belsa
 */
public class StaticList implements Serializable
{
    //default serialVersion id
    private static final long serialVersionUID = 1L;
    
    static int CAPACITY = 1024;
    /******** Data Members ********/
    private int mySize; // current size of list stored in myArray
    private Object[] myArray;  // array to store list elements

    public int getMySize()
    {
        return mySize;
    }
    
    /******** Function Members ********/
    /***** Class constructor *****/
    public StaticList()
    {
        this.myArray = new Object[CAPACITY];
        mySize = 0;
    }
    
    public StaticList(StaticList original)
    {
        this.myArray = original.myArray;
        this.mySize = original.mySize;
    }
    /*----------------------------------------------------------------------
     Construct a List object.

     Precondition:  None
     Postcondition: An empty List object has been constructed; mySize is 0.
     -----------------------------------------------------------------------*/

     /***** empty operation *****/
    public boolean empty()
    {
        return mySize == 0;
    }
    /*----------------------------------------------------------------------
     Check if a list is empty.

     Precondition:  None
     Postcondition: true is returned if the list is empty, false if not.
     -----------------------------------------------------------------------*/

     /***** insert and erase *****/
    public void insert(Object item, int pos)
    {
        if (pos < 0 || pos > mySize || mySize == CAPACITY)
	{
		System.out.println("Invalid Insertion. Operation Terminated with no change.\n");
	}
	else
	{
		for (int i = mySize; i > pos; i--)
		{
			myArray[i] = myArray[i-1];
		}
		myArray[pos] = item;
		mySize++;
	}
    }
    public void append(Object item)
    {
        myArray[mySize] = item;
	mySize++;
    }
    /*----------------------------------------------------------------------
     Insert a value into the list at a given position.

     Precondition:  item is the value to be inserted; there is room in
     the array (mySize < CAPACITY); and the position satisfies
     0 <= pos <= mySize.
     Postcondition: item has been inserted into the list at the position
     determined by pos (provided there is room and pos is a legal
     position).
     -----------------------------------------------------------------------*/

    public void erase(int pos)
    {
      	if (pos < 0 || pos >= mySize || mySize == 0)
		System.out.println("Invalid Deletion. Operation Terminated with no change.\n");
	else
	{
		for (int i = pos; i <mySize; i++)
		{
			myArray[i] = myArray[i+1];
		}
		mySize--;
	}  
    }
    /*----------------------------------------------------------------------
     Remove a value from the list at a given position.

     Precondition:  The list is not empty and the position satisfies
     0 <= pos < mySize.
     Postcondition: element at the position determined by pos has been
     removed (provided pos is a legal position).
     ----------------------------------------------------------------------*/

     /***** output *****/
    public void display()
    {
        System.out.println("\nDisplaying List...\n");
	for (int i = 0; i < mySize; i++)
	{
		System.out.println(myArray[i].toString());
	}
    }
    /*----------------------------------------------------------------------
     Display a list.

     Precondition:  The ostream out is open.
     Postcondition: The list represented by this List object has been
     inserted into out.
     -----------------------------------------------------------------------*/
    
    public Object returnByIndex(int index)
    {
        Object out = new Object();
        for(int i = 0; i < mySize; i++)
        {
            if(index == i)
            {
                out = myArray[i];
                break;
            }
        }
        return out;
    }
}
