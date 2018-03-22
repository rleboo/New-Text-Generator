import java.util.Iterator;

/**
 * Abstract class for an iterator that goes through a HashMap
 * The general behavior is to go through every key/value pair
 * The assumption here is that key/value pairs are stored as
 * Entry types in the HashMap
 *
 * The hasNext and next methods are abstract.  
 * Remove is concrete and unsupported.
 *
 * @see Entry
  */
public abstract class AbstractHashMapIterator implements Iterator<Entry>
{
    /**
     * @return true if there are still key/value pairs
     * in the HashMap that still need to be accessed
     * false otherwise
     */
   @Override
   public abstract boolean hasNext();

    /**
     * @return the next Entry (i.e. key/value pair) to be accessed
     */
   @Override
   public abstract Entry next();

   @Override
   public void remove()
   {
      throw new UnsupportedOperationException("Remove not supported");
   }
}
