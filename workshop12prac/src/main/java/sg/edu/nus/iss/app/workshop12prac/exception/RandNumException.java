package sg.edu.nus.iss.app.workshop12prac.exception;

public class RandNumException extends RuntimeException {
    //Deserialisation uses this number to ensure that a loaded class corresponds exactly to a serialised object.
    //Serialisation in Java converts an Object to stream that we can send over the network or save it as file or store in DB for later use.
    //Deserialisation is the process of converting Object stream to actual Java Object to be used in our program.
    //If no match is found, then an InvalidClassException is thrown.
    private static final long serialVersionUID = 1L;
    
}
