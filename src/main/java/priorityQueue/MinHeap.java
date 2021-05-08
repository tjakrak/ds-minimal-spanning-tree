package priorityQueue;

/** A priority queue: represented by the min heap.
 *  Used in Prim's algorithm. */
public class MinHeap {
    private minHeapNode[] heap; // the array to store the heap
    private int[] positionArr;
    private int maxsize; // the size of the array
    private int size; // the current number of elements in the array

    /**
     * Constructor
     * @param max the maximum size of the heap
     */
    public MinHeap(int max) {
        maxsize = max;
        heap = new minHeapNode[maxsize + 1];
        positionArr = new int[max];
        size = 0;
        heap[0].priority = Integer.MIN_VALUE;
        // Note: no actual data is stored at heap[0].
        // Assigned MIN_VALUE so that it's easier to bubble up

        insert(1, 0);
        for (int i = 2; i <= max; i++) {
            insert(i, Integer.MAX_VALUE);
        }

        for (int i = 0; i < max; i++) {
            // array store nodeId position. Array index = nodeId and the value in the array is the position
            positionArr[i] = i + 1;
        }
    }

    /** Return the index of the left child of the element at index pos
     *
     * @param pos the index of the element in the heap array
     * @return the index of the left child
     */
    private int leftChild(int pos) {
        return 2 * pos;
    }

    /** Return the index of the right child
     *
     * @param pos the index of the element in the heap array
     * @return the index of the right child
     */
    private int rightChild(int pos) {
        return 2 * pos + 1;
    }

    /** Return the index of the parent
     *
     * @param pos the index of the element in the heap array
     * @return the index of the parent
     */
    private int parent(int pos) {
        return pos / 2;
    }

    /** Returns true if the node in a given position is a leaf
     *
     * @param pos the index of the element in the heap array
     * @return true if the node is a leaf, false otherwise
     */
    private boolean isLeaf(int pos) {
        return ((pos > size / 2) && (pos <= size));
    }

    /** Swap given elements: one at index pos1, another at index pos2
     *
     * @param pos1 the index of the first element in the heap
     * @param pos2 the index of the second element in the heap
     */
    private void swap(int pos1, int pos2) {
        minHeapNode tmp;
        tmp = heap[pos1];
        heap[pos1] = heap[pos2];
        heap[pos2] = tmp;
    }

    /** Insert an element into the heap
     *
     *  @param nodeId the
     *  @param priority s
     */

    public void insert(int nodeId, int priority) {
        size++;
        minHeapNode newNode = new minHeapNode(nodeId, priority);
        heap[size] = newNode;

        int current = size;
        while (heap[current].priority < heap[parent(current)].priority) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    /** Print the array that stores the heap */
    public void print() {
        int i;
        for (i = 1; i <= size; i++)
            System.out.print(heap[i] + " ");
        System.out.println();
    }

    /** Remove minimum element (it is at the top of the minHeap)
     *
     * @return the smallest element in the heap
     */
    public minHeapNode removeMin() {
        swap(1, size); // swap the end of the heap into the root
        size--;  	   // removed the end of the heap
        // fix the heap property - push down as needed
        if (size != 0)
            pushDown(1);
        return heap[size + 1];
    }

    /** Push the value down the heap if it does not satisfy the heap property
     *
     * @param position the index of the element in the heap
     */
    private void pushDown(int position) {
        int smallestChild;
        while (!isLeaf(position)) {
            smallestChild = leftChild(position); // set the smallest child to left child
            if ((smallestChild < size) && (heap[smallestChild].priority > heap[smallestChild + 1].priority))
                smallestChild = smallestChild + 1; // right child was smaller, so smallest child = right child

            // the value of the smallest child is less than value of current,
            // the heap is already valid
            if (heap[position].priority <= heap[smallestChild].priority)
                return;
            int id1 = heap[position].nodeId;
            int id2 = heap[smallestChild].nodeId;
            positionArr[id1] = smallestChild;
            positionArr[id2] = position;
            swap(position, smallestChild);
            position = smallestChild;
        }

    }

    public void reduceKey(int nodeId, int newPriority) {
        int indexInHeap = positionArr[nodeId];
        heap[indexInHeap].priority = newPriority;
    }

    private void pushUp(int position) {
        int smallestChildIdx = position;
        int parentIdx = parent(position);
        while (heap[smallestChildIdx].priority < heap[parentIdx].priority) {
//            if (position % 2 == 0) {
//                if (position + 1 < size && heap[smallestChildIdx].priority > heap[position + 1].priority) {
//                    smallestChildIdx = position + 1;
//                }
//            } else {
//                if (heap[smallestChildIdx].priority > heap[position - 1].priority) {
//                    smallestChildIdx = position - 1;
//                }
//            }
            int id1 = heap[parentIdx].nodeId;
            int id2 = heap[smallestChildIdx].nodeId;
            positionArr[id1] = smallestChildIdx;
            positionArr[id2] = parentIdx;
            swap(smallestChildIdx, parentIdx);
            smallestChildIdx = parentIdx;
            parentIdx = parent(smallestChildIdx);
        }
    }


    private class minHeapNode {
        int nodeId;
        int priority;

        minHeapNode(int nodeId, int priority) {
            this.nodeId = nodeId;
            this.priority = priority;
        }
    }


}
