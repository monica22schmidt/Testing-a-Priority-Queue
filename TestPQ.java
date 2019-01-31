/**
 * Filename:   TestPQ.java
 * Project:    p1TestPQ
 * Authors:    Debra Deppeler, Monica Schmidt
 *
 * Semester:   Fall 2018
 * Course:     CS400
 * Lecture:    002
 *
 * Note: Warnings are suppressed on methods that construct new instances of
 * generic PriorityQueue types.  The exceptions that occur are caught and
 * such types are not able to be tested with this program.
 *
 * Due Date:   Before 10pm on September 17, 2018
 * Version:    2.0
 *
 * Credits:    TODO name individuals and sources outside of course staff
 *             NOTE: this is an individual assignment, you are not allowed
 *             to view or use code written by anyone but yourself.
 *
 * Bugs:       no known bugs, but not complete either
 */


import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Runs black-box unit tests on the priority queue implementations
 * passed in as command-line arguments (CLA).
 *
 * If a class with the specified class name does not exist
 * or does not implement the PriorityQueueADT interface,
 * the class name is reported as unable to test.
 *
 * If the class exists, but does not have a default constructor,
 * it will also be reported as unable to test.
 *
 * If the class exists and implements PriorityQueueADT,
 * and has a default constructor, the tests will be run.
 *
 * Successful tests will be reported as passed.
 *
 * Unsuccessful tests will include:
 *     input, expected output, and actual output
 *
 * Example Output:
 * Testing priority queue class: PQ01
 *    5 PASSED
 *    0 FAILED
 *    5 TOTAL TESTS RUN
 * Testing priority queue class: PQ02
 *    FAILED test00isEmpty: unexpectedly threw java.lang.NullPointerException
 *    FAILED test04insertRemoveMany: unexpectedly threw java.lang.ArrayIndexOutOfBoundsException
 *    3 PASSED
 *    2 FAILED
 *    5 TOTAL TESTS RUN
 *
 *   ... more test results here
 *
 * @author deppeler
 */
public class TestPQ {

    // set to true to see call stack trace for exceptions
    private static final boolean DEBUG = true;

    /**
     * Run tests to determine if each Priority Queue implementation
     * works as specified. User names the Priority Queue types to test.
     * If there are no command-line arguments, nothing will be tested.
     *
     * @param args names of PriorityQueueADT implementation class types
     * to be tested.
     */
    public static void main(String[] args) {
        for (int i=0; i < args.length; i++)
            test(args[i]);

        if ( args.length < 1 )
            print("no PQs to test");
    }

    /**
     * Run all tests on each priority queue type that is passed as a classname.
     *
     * If constructing the priority queue in the first test causes exceptions,
     * then no other tests are run.
     *
     * @param className the name of the class that contains the
     * priority queue implementation.
     */
    private static void test(String className) {
        print("Testing priority queue class: "+className);
        int passCount = 0;
        int failCount = 0;
        try {

            if (test00isEmpty(className)) passCount++; else failCount++;
            if (test01getMaxEXCEPTION(className)) passCount++; else failCount++;

            if (test02removeMaxEXCEPTION(className)) passCount++; else failCount++;
            if (test03insertRemoveOne(className)) passCount++; else failCount++;
            if (className.equals("PQ02")){
              if (test04insertRemoveMany(className)) passCount++; else failCount++;
            }
            if (test05insertRemovePriority(className)) passCount++; else failCount++;
            if (test06insertRemoveIsEmpty(className)) passCount++; else failCount++;
            if (test07isNotEmpty(className)) passCount++; else failCount++;
            if (test08getMaxOnEmptyPQEXCEPTION(className)) passCount++; else failCount++;
            if (test09removeMaxEmptyPQEXCEPTION(className)) passCount++; else failCount++;
            if (test10getMaxPriority(className)) passCount++; else failCount++;
                    if (test11InsertNullException(className)) passCount++; else failCount++;






            String passMsg = String.format("%4d PASSED", passCount);
            String failMsg = String.format("%4d FAILED", failCount);
            print(passMsg);
            print(failMsg);

        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            if (DEBUG) e.printStackTrace();
            print(className + " FAIL: Unable to construct instance of " + className);
        } finally {
            String msg = String.format("%4d TOTAL TESTS RUN", passCount+failCount);
            print(msg);
        }

    }

        /** Confirm that getMax throws NoSuchElementException if called on
         * a newly empty priority queue.  Any other exception indicates a fail.
         *
         * @param className name of the priority queue implementation to test.
         * @return true if getMax throws an exception on a newly emptied PQ
         * @throws ClassNotFoundException
         * @throws IllegalAccessException
         * @throws InstantiationException
         */
        private static boolean test11InsertNullException(String className)
                                        throws InstantiationException, IllegalAccessException, ClassNotFoundException {
                PriorityQueueADT<Integer> pq = newIntegerPQ(className);
                try {
                        pq.insert(null);
                } catch (NullPointerException e) {
                        return true;
                } catch (Exception e) {
                        if (DEBUG) e.printStackTrace();
                        print("FAILED test11InsertNullException: unexpectedly threw " + e.getClass().getName());
                        return false;
                }
                print("FAILED test11InsertNullException: insert did not throw NullPointerExceptionon when inserting null value");
                return false;
        }
    /** Confirm that getMax returns the correct priority.
     * Any exception and wrong priorty indicates a fail.
     *
     * @param className name of the priority queue implementation to test.
     * @return true if getMax returns the correct priority value
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test10getMaxPriority(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        int p;
        try {
            pq.insert(5);
            pq.removeMax();
            pq.insert(8);
            pq.insert(3);
            pq.insert(9);
            pq.removeMax();
            p = pq.getMax();
            if(p == 8){
                return true;
            }
        } catch (NoSuchElementException e) {
            print("FAILED test10getMaxPriority: test unexpectedly threw " + e.getClass().getName());
            return false;
        } catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test10getMaxPriority: unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test10getMaxPriority: Input 5,8,3,9 removed 2 integers, expected 8 received " + p);
        return false;
    }
    /** Confirm that removeMax throws NoSuchElementException if called on
     * a newly empty priority queue.  Any other exception indicates a fail.
     * @param className name of the priority queue implementation to test.
     * @return true if removeMax on a newly emptied priority queue throws NoSuchElementException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test09removeMaxEmptyPQEXCEPTION(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        // TODO Auto-generated method stub
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        try {
            pq.insert(6);
            pq.removeMax();
            pq.insert(6);
            pq.insert(6);
            pq.removeMax();
            pq.removeMax();
            pq.removeMax();
        }catch(NoSuchElementException e){
            return true;
        }catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test09removeMaxEmptyPQEXCEPTION: unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test09removeMaxEmptyPQEXCEPTION: removeMax did not throw NoSuchElement exception on an empty PQ");
        return false;
    }

    /** Confirm that getMax throws NoSuchElementException if called on
     * a newly empty priority queue.  Any other exception indicates a fail.
     *
     * @param className name of the priority queue implementation to test.
     * @return true if getMax throws an exception on a newly emptied PQ
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test08getMaxOnEmptyPQEXCEPTION(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        try {
            pq.insert(6);
            pq.removeMax();
            pq.insert(6);
            pq.insert(6);
            pq.removeMax();
            pq.removeMax();
            pq.getMax();
        } catch (NoSuchElementException e) {
            return true;
        } catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test08getMaxOnEmptyPQEXCEPTION: unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test08getMaxOnEmptyPQEXCEPTION: getMax did not throw NoSuchElement exception on empty PQ");
        return false;
    }

    /** Confirm that isEmpty returns false on a not empty PQ
     * Any exception and isEmpty returns true indicates a fail.
     *
     * @param className name of the priority queue implementation to test.
     * @return true if isEmpty returns false on a nonEmpty PQ
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test07isNotEmpty(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        Random gen = new Random();
        try{
            for(int i = 0; i < 6; i++){
                pq.insert(gen.nextInt(10));
            }
            for(int i = 0; i < 5; i++){
                pq.removeMax();
            }
            if(!pq.isEmpty()){
                return true;
            }
        }catch(NoSuchElementException e){
            print("FAILED test07isNotEmpty: test unexpectedly threw " + e.getClass().getName());
            return false;
        }catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test07isNotEmpty: test unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test07isNotEmpty: Input 6 random integers removed 5 integers, isEmpty returned true on a not empty PQ");
        return false;
    }
    /** Confirm that isEmpty returns true on a newly empty PQ
     * Any exception and isEmpty returns false indicates a fail.
     *
     * @param className name of the priority queue implementation to test.
     * @return true if isEmpty returns true on a newly emptied PQ
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test06insertRemoveIsEmpty(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        Random gen = new Random();
        try{
            for(int i = 0; i < 6; i++){
                pq.insert(gen.nextInt(10));
            }
            for(int i = 0; i < 6; i++){
                pq.removeMax();
            }
            if(pq.isEmpty()){
                return true;
            }
        }catch(NoSuchElementException e){
            print("FAILED test06insertRemoveIsEmpty: test unexpectedly threw " + e.getClass().getName());
            return false;
        }catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test06insertRemoveIsEmpty: test unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test06insertRemoveIsEmpty: Input 6 random integers removed 6 integers, isEmpty returned false on an empty PQ");
        return false;
    }
    /** Confirm that removeMax returns the max value on a PQ
     * Any exception and a return of the wrong priority indicates a fail.
     *
     * @param className name of the priority queue implementation to test.
     * @return true if removeMax returns the right priority value
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test05insertRemovePriority(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        Random gen = new Random();
        int p;
        try{
            for(int i = 0; i < 6; i++){
                pq.insert(gen.nextInt(10));
            }
            pq.insert(11);
            pq.insert(5);
            p = pq.removeMax();
            if(p == 11){
                return true;
            }
        }catch(NoSuchElementException e){
            print("FAILED test05insertRemovePriority: test unexpectedly threw " + e.getClass().getName());
            return false;
        }catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test05insertRemovePriority: test unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test05insertRemovePriority: Input 6 random integers from 1 to 10, then inputed 11 then 5, expected integer 11, received " + p + className);
        return false;

    }
    /** Confirm that many items can be inserted, no exception is thrown, and that
     * the correct object is returned based on priority.
     * Any exception indicates a fail. Along with incorrect priorty value returned.
     * and if the program doesn't terminate.
     * @param className name of the priority queue implementation to test.
     * @return true if removeMax returns the correct object based on priority
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test04insertRemoveMany(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        int p=0;
        long start = System.currentTimeMillis();
        long end = start + 180*1000;

        try {
          for(int i = 0; i < 1000000; i++){
            pq.insert(6);
          }
          pq.insert(7);
          p = pq.removeMax();
          if(p == 7){
            return true;
          }
        }catch(NoSuchElementException e){
            print("FAILED test04insertRemoveMany: test unexpectedly threw " + e.getClass().getName());
            return false;
        }catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test04insertRemoveMany: test unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test04insertRemoveMany: Input integer 7, expected integer 7, received " + p + className);
        return false;
    }

    /** Confirm that insert and removeMax don't throw NoSuchElementException if called
     * on after an object is inserted.  Any exception indicates a fail. Along with
     * incorrect return value
     * @param className name of the priority queue implementation to test.
     * @return true if removeMax returns the correct object based on priority
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test03insertRemoveOne(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        int p;
        try {
            pq.insert(6);
            p = pq.removeMax();
            if(p == 6){
                return true;
            }
        }catch(NoSuchElementException e){
            print("FAILED test03insertRemoveOne: test unexpectedly threw " + e.getClass().getName());
            return false;
        }catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test03insertRemoveOne: unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test03insertRemoveOne: Input integer 6, expected integer 6, received " + p + className);
        return false;
    }

    /** Confirm that removeMax throws NoSuchElementException if called on
     * an empty priority queue.  Any other exception indicates a fail.
     * @param className name of the priority queue implementation to test.
     * @return true if removeMax on empty priority queue throws NoSuchElementException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test02removeMaxEXCEPTION(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        // TODO Auto-generated method stub
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        try {
            pq.removeMax();
        }catch(NoSuchElementException e){
            return true;
        }catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test02removeMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test02removeMaxEXCEPTION: removeMax did not throw NoSuchElement exception on newly constructed PQ");
        return false;
    }

    /** DO NOT EDIT -- provided as an example
     * Confirm that getMax throws NoSuchElementException if called on
     * an empty priority queue.  Any other exception indicates a fail.
     *
     * @param className name of the priority queue implementation to test.
     * @return true if getMax on empty priority queue throws NoSuchElementException
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test01getMaxEXCEPTION(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        try {
            pq.getMax();
        } catch (NoSuchElementException e) {
            return true;
        } catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test01getMaxEXCEPTION: unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test01getMaxEXCEPTION: getMax did not throw NoSuchElement exception on newly constructed PQ");
        return false;
    }

    /** DO NOT EDIT THIS METHOD
     * @return true if able to construct Integer priority queue and
     * the instance isEmpty.
     *
     * @throws ClassNotFoundException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private static boolean test00isEmpty(String className)
                    throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        PriorityQueueADT<Integer> pq = newIntegerPQ(className);
        try {
            if (pq.isEmpty())
                return true;
        } catch (Exception e) {
            if (DEBUG) e.printStackTrace();
            print("FAILED test00isEmpty: unexpectedly threw " + e.getClass().getName());
            return false;
        }
        print("FAILED test00isEmpty: isEmpty returned false on newly constructed PQ");
        return false;
    }

    /** DO NOT EDIT THIS METHOD
     * Constructs a max Priority Queue of Integer using the class that is name.
     * @param className The specific Priority Queue to construct.
     * @return a PriorityQueue
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings({ "unchecked" })
    public static final PriorityQueueADT<Integer> newIntegerPQ(String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> pqClass = Class.forName(className);
        Object obj = pqClass.newInstance();
        if (obj instanceof PriorityQueueADT) {
            return (PriorityQueueADT<Integer>) obj;
        }
        return null;
    }

    /** DO NOT EDIT THIS METHOD
     * Constructs a max Priority Queue of Double using the class that is named.
     * @param className The specific Priority Queue to construct.
     * @return a PriorityQueue
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings({ "unchecked" })
    public static final PriorityQueueADT<Double> newDoublePQ(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> pqClass = Class.forName(className);
        Object obj = pqClass.newInstance();
        if (obj instanceof PriorityQueueADT) {
            return (PriorityQueueADT<Double>) obj;
        }
        return null;
    }

    /** DO NOT EDIT THIS METHOD
     * Constructs a max Priority Queue of String using the class that is named.
     * @param className The specific Priority Queue to construct.
     * @return a PriorityQueue
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws ClassNotFoundException
     */
    @SuppressWarnings({ "unchecked" })
    public static final PriorityQueueADT<String> newStringPQ(final String className) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        Class<?> pqClass = Class.forName(className);
        Object obj = pqClass.newInstance();
        if (obj instanceof PriorityQueueADT) {
            return (PriorityQueueADT<String>) obj;
        }
        return null;
    }


    /** DO NOT EDIT THIS METHOD
     * Write the message to the standard output stream.
     * Always adds a new line to ensure each message is on its own line.
     * @param message Text string to be output to screen or other.
     */
    private static void print(String message) {
        System.out.println(message);
    }

}
