# ShamirReconstructionNew

This program reconstructs a secret using Shamir's Secret Sharing.

## Requirements
- Java JDK installed
- Gson library (gson-2.10.1.jar)

## Steps to Run

1. **Prepare the JSON file**  
   Create a file named `shares_new.json` in the same folder as the Java file.  
   Example format:
   ```json
   {
       "keys": {"n": 4, "k": 3},
       "1": {"base": "10", "value": "4"},
       "2": {"base": "2", "value": "111"},
       "3": {"base": "10", "value": "12"},
       "6": {"base": "4", "value": "213"}
   }

Compile the Java program

Open a terminal in the project folder and run:

javac -cp ".;lib/gson-2.10.1.jar" ShamirReconstructionNew.java

Run the program

java -cp ".;lib/gson-2.10.1.jar" ShamirReconstructionNew
