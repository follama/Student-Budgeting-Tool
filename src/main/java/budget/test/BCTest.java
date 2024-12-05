package budget.test;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import budget.MultiLevelUndo;
public class BCTest {

    private MultiLevelUndo stateManager;

    @Before
    public void setUp(){
        stateManager = new MultiLevelUndo(); //initialises a new statemanager 
    }

    @Test// tests the single undo 
    public void singleUndo() {
        //gives states with our example values
        Map<String, Object> state1 = new HashMap<>();
        state1.put("Wages", "1000");
        state1.put("Loans", "300");
        state1.put("Cash Back", "20");

        Map<String, Object> state2 = new HashMap<>();
        state2.put("Wages", "800");
        state2.put("Loans", "400");
        state2.put("Cash Back", "50");

        // Save both states
        stateManager.saveCurrent(state1); 
        stateManager.saveCurrent(state2); 

        // Peek at the last state (state2)
        Map<String, Object> peekState = stateManager.peekLastState();

        // Make sure that peeked state values matches state2
        assertNotNull("Peeked state should not be empty", peekState);
        assertEquals("800", peekState.get("Wages"));
        assertEquals("400", peekState.get("Loans"));
        assertEquals("50", peekState.get("Cash Back"));

        // Makes sure that after peeking stack is the same
        assertEquals("Stack should remain the same after peeking.", peekState, stateManager.peekLastState());
    }

    //multi level undo
    @Test
    public void multiUndo(){
        //gives states with our example values
        Map<String, Object> state1 = new HashMap<>();
        state1.put("Wages", "1400");
    
        Map<String, Object> state2 = new HashMap<>();
        state2.put("Wages", "1600");
    
        //save the states
        stateManager.saveCurrent(state1);
        stateManager.saveCurrent(state2);
    
        // Undo the last state
        Map<String, Object> undoneState = stateManager.undoLastState();
    
        // Make suer the undone state is state2
        assertNotNull("Undone state should not be empty", undoneState);
        assertEquals("1600", undoneState.get("Wages"));
    
        // Makes sure that the new top of the stack is state1
        Map<String, Object> currentTopState = stateManager.peekLastState();
        assertEquals("1400", currentTopState.get("Wages"));
    }

}
