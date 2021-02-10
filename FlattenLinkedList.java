import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


/**
 * Class for linked list nodes
 */
class LLNode {

    // **** class members ****
    public int val;
    public LLNode next;
    public LLNode alt;

    /**
     * Constructor(s)
     */
    public LLNode(int val) {
        this.val = val;
    }

    public LLNode() {
    }

    /**
     * Convert to string
     */
    @Override
    public String toString() {
        return "" + this.val;
    }
}


/**
 * Flatten a Linked List
 */
public class FlattenLinkedList {


    /**
     * Populate alternate linked list
     * 
     * !!! NOT PART OF SOLUTION !!!
     */
    static LLNode populate(int[] arr) {

        // **** initialization ****
        LLNode head = null;
        LLNode tail = null;

        // **** populate alternate linked list ****
        for (int i = 0; i < arr.length; i++) {

            // **** ****
            LLNode node = new LLNode(arr[i]);

            // **** ****
            if (head == null) {
                head = node;
                tail = node;
            } else {
                tail.next = node;
                tail = node;
            }
        }

        // **** return head of alternate linked list ****
        return head;
    }


    /**
     * Build linked list to be flattened.
     * 
     * !!! NOT PART OF SOLUTION !!!
     */
    static LLNode build(String[] strArr, int[][] arrs) {

        // **** initialization ****
        LLNode head = null;
        LLNode tail = null;
        int altNum  = 0;

        // **** traverse String[] populating linked list to be flattened ****
        for (int i = 0; i < strArr.length; i++) {

            // **** primary node ****
            if (!strArr[i].equals("null")) {

                // **** create node ****
                LLNode node = new LLNode(Integer.parseInt(strArr[i]));

                // **** insert node into primary linked list ****
                if (head == null) {
                    head = node;
                    tail = node;
                } else {
                    tail.next = node;
                    tail = node;
                }
            } 
            
            // **** start of alternate linked list ****
            else {

                // **** create alternate node ****
                LLNode altNode = new LLNode(Integer.MAX_VALUE);

                // **** populate alternate linked list ****
                altNode.alt = populate(arrs[altNum++]);

                // **** add the alternate linked list to the linked list ****
                if (head == null) {
                    head = altNode;
                    tail = altNode;
                } else {
                    tail.next = altNode;
                    tail = altNode;
                }
            }
        }

        // **** return head of linked list ****
        return head;
    }


    /**
     * Generate a string representation of a linked list.
     * 
     * !!! NOT PART OF SOLUTION !!!
     */
    static String llToString(LLNode head) {

        // **** initialization ****
        StringBuilder sb = new StringBuilder();

        // **** traverse the linked list ****
        for (LLNode p = head; p != null; p = p.next) {

            // **** ****
            if (p.val == Integer.MAX_VALUE)
                sb.append("X");
            else
                sb.append(p.toString());

            // **** separator ****
            if (p.next != null)
                sb.append("->");
        }

        // **** return the string representation ****
        return sb.toString();
    }


    /**
     * Flatten linked list.
     * 
     * Runtime O(n);
     */
    static LLNode flatten(LLNode head) {

        // **** sanity check(s) ****
        if (head == null)
            return null;

        // **** initialization ****
        LLNode q = head;
        LLNode t;

        // **** traverse the linked list ****
        for (LLNode p = head; p != null; ) {

            // **** check if alternate linked list ****
            if (p.alt != null) {

                // **** save next node ****
                LLNode nextP = p.next;

                // **** append alternate to main linked list ****
                q.next = p.alt;

                // **** update head & q nodes (if needed) ****
                if (head == p) {
                    head    = p.alt;
                    q       = head;
                }

                // **** get to the tail of the alternate linked list ****
                for (t = p.alt; t.next != null; t = t.next);

                // **** tail.next point to next node in primary linked list ****
                t.next = nextP;

                // **** update pointers ****
                q = t;
                p = nextP;
            }

            // **** update pointers ****
            else {
                q = p;
                p = p.next;
            }
        }

        // **** return flattened linked list ****
        return head;
    }


    /**
     * Test scaffold
     * 
     * !!! NOT PART OF SOLUTION !!!
     * 
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {

        // **** initialization ****
        int B = 0;

        // **** open a buffered reader ****
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // **** read the values for the main linked list ****
        String[] strArr = br.readLine().trim().split(",");

        // ???? ????
        System.out.println("main <<< strArr: " + Arrays.toString(strArr));

        // **** determine the number of branches ****
        for (int i = 0; i < strArr.length; i++) {
            if (strArr[i].equals("null"))
                B++;
        }

        // ???? ????
        System.out.println("main <<<      B: " + B);

        // **** read values for branches ****
        int[][] arrs = new int[B][];
        for (int b = 0; b < B; b++) {
            arrs[b] = Arrays.stream(br.readLine().trim().split(","))
                            .mapToInt(Integer::parseInt)
                            .toArray();
        }

        // ???? ????
        System.out.println("main <<<   arrs:");
        for (int i = 0; i < B; i++)
            System.out.println(Arrays.toString(arrs[i]));

        // **** close buffered reader ****
        br.close();

        // **** build linked list to be flattened ****
        LLNode head = build(strArr, arrs);

        // ???? ????
        System.out.println("main <<< head: " + llToString(head));

        // **** flatten linked list ****
        head = flatten(head);

        // ???? ????
        System.out.println("main <<< head: " + llToString(head));
    }
}