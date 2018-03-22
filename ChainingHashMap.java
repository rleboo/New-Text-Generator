import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class ChainingHashMap extends AbstractHashMap implements Iterable<Entry>{


    private ArrayList[] entries;


    /**
     * Calls the superclass's methods and sets the capacity of the ArrayList
     */
    public ChainingHashMap() {
        super(5);
        // Gives us the variables capacity, numKeys, and maxLoad
        entries = new ArrayList[capacity];

    }


    /**
     * Puts the key,value pair into the hashmap. If the same key is already there it replaces it.
     * @param key Object
     * @param value Value
     */
    @Override
    public void put(Object key, Object value) {
        double load = numKeys/(capacity*1.0);
        Entry temp = new Entry(key,value);
        if(load >= maxLoad) {
            resize();
        }
        int hash = hash(key);

        if(entries[hash] == null) {
            ArrayList<Entry> s = new ArrayList<Entry>();
            s.add(temp);
            entries[hash] = s;
            numKeys++;
        }
        else {
            ArrayList loop = entries[hash];
            int pos = -1;
            for(int j = 0; j<loop.size(); j++) {
                Entry e = (Entry) entries[hash].get(j);
                if(e.key.equals(key)) {
                    pos = j;
                    //Keeps track of position of key(if any) should only occur once
                }
            }
            if(pos == -1) {
                entries[hash].add(temp);
                numKeys++;
                //Adds to the ArrayList
            }
            else{
                entries[hash].set(pos,temp);
                //Doesn't add numKeys if its replacing
            }

        }

    }


    /**
     * Returns the value(if the key's there) of the inputted key
     * @param key Object
     *
     * @return
     */
    @Override
    public Object get(Object key) {
        int hash = hash(key);

        if(entries[hash] != null) {
            for(int x = 0; x<entries[hash].size();x++) {
                Entry e = (Entry)entries[hash].get(x);

                if(e.key.equals(key)){
                    return e.value;
                }
            }
        }
        return null;
    }

    /**
     * Resizes the ArrayList Size when the load is greater than the maxLoad
     */
    @Override
    protected void resize() {
        capacity = capacity*2;
        ArrayList[] temp = entries;
        entries = new ArrayList[capacity];

        for(int x = 0; x< entries.length/2; x++) {
            if(temp[x] != null) {
                for(Object obj: temp[x]) {
                    Entry ha = (Entry)obj;
                    this.put(ha.key,ha.value);

                    //temp[hash] = entries[x];
                }
            }

        }
    }

    /**
     * Iterator for entries.
     * @return
     */
    @Override
    public Iterator<Entry> iterator() {
        return new ChainingHashMapIterator(entries);
    }

    /**
     * Prints out the entries. Used for debugging purposes
     */
    public void printEntries() {
        //int tracker = 0;
        for(ArrayList s: entries) {
            if(s != null) {
                System.out.println(Arrays.toString(s.toArray()));
                //tracker++;
            }
        }
    }

    /**
     * All test code used to test the methods. Ignore
     * @param args
     */
    public static void main(String[]args){
        ChainingHashMap ca = new ChainingHashMap();
        String[] ba = new String[]{"Eggplant", "Pickles", "Tikka Masala", "Empanada", "Bacon"};
        String[] sa = new String[]{"a", "b", "c", "d", "e"};

        int var3;
        for(var3 = 0; var3 < sa.length; ++var3) {
            ca.put(sa[var3], ba[var3]);
        }


        ca.printEntries();
        ca.put("b", "Donkey");
        ca.printEntries();
        Object s = new Object();
        s = 5;
        System.out.println(s.hashCode());
        System.out.println(ca.get("b") + "YESSS");
        ca.put("r", "GOAT");
        System.out.println(ca.get("r") + "YESSS");

    }
}

