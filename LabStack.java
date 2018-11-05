public class LabStack<T> implements Stack<T>
{
    Node<T> head, tail;
    private int count;

    public LabStack()
    {
        head = null;
        tail = null;
        count = 0;
    }

    public int size()
    {
        return count;
    }

    public boolean empty()
    {
        return (head == null);
    }

    public void push (T element)
    {
        Node<T> addNode = new Node<T>();
        addNode.item = element;

        if (head == null)
        {
            tail = addNode;
        }
        else
        {
            addNode.next = head;
        }
        head = addNode;
        count++;
    }

    public T pop() throws StackEmptyException
    {
        if(head == null)
        {
            throw new StackEmptyException();
        }
        Node<T> value = head;

        head = head.next;
        count--;

        return value.item;
    }
    public T peek() throws StackEmptyException
    {
        if(head == null)
        {
            throw new StackEmptyException();
        }
        return head.item;
    }
    public void makeEmpty()
    {
        head = null;
        tail = null;
        count = 0;
    }
}
