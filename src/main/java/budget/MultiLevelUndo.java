package budget;

//import libraries for undo 
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class MultiLevelUndo  {

    //declare the stack that will hold the multiple undos
   private final Stack <Map <String, Object>> undosStack;
    
   //initialise the stack in the constructor
   public MultiLevelUndo(){
    this.undosStack = new Stack<>();
   }

   //this method is used to save the current state of the stack/ user inputs
   public void saveCurrent(Map<String, Object> currentState){
    undosStack.push(new HashMap<>(currentState));
   }

   public boolean isStateDifferent(Map<String, Object> currentState) {
    if (undosStack.isEmpty()) {
        return true; // If no states are saved, this is a new state
    }
        return !undosStack.peek().equals(currentState);
    }

   //multi level undo time!! ref https://www.youtube.com/watch?v=3XkmZfBDfek
   public Map<String, Object> undoLastState() {
    if (!undosStack.isEmpty()) {
        return undosStack.pop(); // Return the most recent state
    }
        return null; // If stack is empty, there's nothing to undo
    }

    // single-level undo!! looks ar last entry but doesn't remove it from the stack
    public Map<String, Object> peekLastState() {
        if (!undosStack.isEmpty()) {
            return undosStack.peek(); // Return the most recent state without removing
        }
        return null; //nothing to undo so returns null
    }


}
