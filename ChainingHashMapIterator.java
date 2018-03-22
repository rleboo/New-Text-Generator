import java.util.ArrayList;

public class ChainingHashMapIterator extends AbstractHashMapIterator {
    private ArrayList[] entries;
    private int index;
    private int minindex;

    /**
     * Sets the two tracker(index and miniindex). Assings the given ArrayList[] to entries instance variable
     * @param entries
     */
    public ChainingHashMapIterator(ArrayList[] entries) {
        this.entries = entries;
        index = 0;
        minindex = 0;
    }

    /**
     * Checks if the ArrayList has next
     * @return
     */
    public boolean hasNext() {
        for (int x = index; x < entries.length; x++) {
            if (entries[x] != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the next within the ArrayList. If there are multiple values with one ArrayList it goes to each successive index.
     * @return
     */
    public Entry next() {

        if(index < entries.length) {
            if(entries[index] != null) {
                ArrayList list = entries[index];
                if(minindex < list.size()){
                    Entry moo = (Entry)list.get(minindex);
                    minindex++;
                    return moo;
                }
            }
            minindex =0;
            for (int k = index + 1; k < entries.length; k++) {
                if (entries[k] != null) {
                    index = k;
                    ArrayList list = entries[index];
                    Entry moo = (Entry) list.get(minindex);
                    minindex = minindex + 1;
                    return moo;
                }
            }
        }
        return null;

    }
}
