import java.util.Scanner;
import java.util.Stack;

public class LR_parser {

    public static void reduce(Stack<String> stack, String rule_num){
        // reducer function to handle pop and push of rules and states

        String [][] ETF = {
                {"1","2","3"},
                {null,null,null},
                {null,null,null},
                {null,null,null},
                {"8","2","3"},
                {null,null,null},
                {null,"9","3"},
                {null,null,"10"},
                {null,null,null},
                {null,null,null},
                {null,null,null},
                {null,null,null},
        };

        if(rule_num.equals("1")){

            int counter = 0;
            // remove all the elements from the stack related to the RHS of rule E -> E + T
            while(counter < 6){
                stack.pop();
                counter ++;
            }

            // look at the previous state to see what the next state will be
            int prev_state = Integer.parseInt(stack.peek());

            String next_state = ETF[prev_state][0];

            stack.push("E");
            stack.push(next_state);

        }else if(rule_num.equals("2")){
            int counter = 0;
            // remove all the elements from the stack related to the RHS of rule E -> T
            while(counter < 2){
                stack.pop();
                counter ++;
            }

            // look at the previous state to see what the next state will be
            int prev_state = Integer.parseInt(stack.peek());

            String next_state = ETF[prev_state][0];

            stack.push("E");
            stack.push(next_state);

        }else if(rule_num.equals("3")){

            int counter = 0;
            // remove all the elements from the stack related to the RHS of rule T -> T * F
            while(counter < 6){
                stack.pop();
                counter ++;
            }

            // look at the previous state to see what the next state will be
            int prev_state = Integer.parseInt(stack.peek());

            String next_state = ETF[prev_state][1];

            stack.push("T");
            stack.push(next_state);

        }else if(rule_num.equals("4")){
            int counter = 0;
            // remove all the elements from the stack related to the RHS of rule T -> F
            while(counter < 2){
                stack.pop();
                counter ++;
            }

            // look at the previous state to see what the next state will be
            int prev_state = Integer.parseInt(stack.peek());

            String next_state = ETF[prev_state][1];

            stack.push("T");
            stack.push(next_state);

        }else if(rule_num.equals("5")){

            int counter = 0;
            // remove all the elements from the stack related to the RHS of rule T -> T * F
            while(counter < 6){
                stack.pop();
                counter ++;
            }

            // look at the previous state to see what the next state will be
            int prev_state = Integer.parseInt(stack.peek());

            String next_state = ETF[prev_state][2];

            stack.push("F");
            stack.push(next_state);


        }else if(rule_num.equals("6")){

            int counter = 0;
            // remove all the elements from the stack related to the RHS of rule T -> F
            while(counter < 2){
                stack.pop();
                counter ++;
            }

            // look at the previous state to see what the next state will be
            int prev_state = Integer.parseInt(stack.peek());

            String next_state = ETF[prev_state][2];

            stack.push("F");
            stack.push(next_state);

        }
    }
    public static void main(String[] args) {

        String [][] table = {
                //id   +    *    (     )    $
                //0    1    2    3     4    5
                {"s5",null,null,"s4",null,null}, //0
                {null,"s6",null,null,null,"acc"}, //1
                {null,"r2","s7",null,"r2","r2"},//2
                {null,"r4","r4",null,"r4","r4"},//3
                {"s5",null,null,"s4",null,null},//4
                {null,"r6","r6",null,"r6","r6"},//5
                {"s5",null,null,"s4",null,null},//6
                {"s5",null,null,"s4",null,null},//7
                {null,"s6",null,null,"s11",null},//8
                {null,"r1","s7",null,"r1","r1"},//9
                {null,"r3","r3",null,"r3","r3"},//10
                {null,"r5","r5",null,"r5","r5"}//11
        };




        Scanner scan = new Scanner(System.in);

        String input = "";
        // enter main loop for input and parsing
        while(true){
            // create stack and push initial state
            Stack<String> stack = new Stack<String>();
            stack.push("0");
            // need to empty stack each iteration
            System.out.println("enter the line to be parsed: (enter exit to end program) ");
            input = scan.nextLine();

            if(input.equals("exit")){
                break;
            }else {

                int counter = 0;
                System.out.println("Stack: " + stack + " input: " + input);
                System.out.println("------------------------");
                // loop over the input and get each token
                while (true) {
                    int current_state = Integer.parseInt(stack.peek());

                    char c = input.charAt(counter);

                    String table_content = "";

                    // find out what the token is then refer to the table for instructions
                    if (c == 'i') { // token is id
                        // index the table at the state at the top of the stack and the token value
                        table_content = table[current_state][0];

                        // shift instruction
                        if (table_content == null) {
                            System.out.println("Error: invalid syntax");
                            break;
                        } else {
                            if (table_content.charAt(0) == 's') {

                                System.out.println("Action: " + table_content);
                                // push the token and the state to the stack
                                stack.push("id");
                                stack.push(table_content.substring(1));
                                System.out.println("Stack: " + stack + " input: " + input.substring(counter + 2));
                                counter += 2;

                            } else if (table_content.charAt(0) == 'r') {

                                System.out.println("Action: " + table_content);
                                //call the reduce function will perform all functions upto the next token iteration
                                reduce(stack, table_content.substring(1));
                                System.out.println("Stack: " + stack + " input: " + input.substring(counter));

                            } else {
                                // found null and instruction is invalid
                                // ******** produce error *********

                                System.out.println("Error: invalid syntax");
                                break;
                            }
                        }



                    } else if (c == ' ') {
                        // move on to next token
                        counter++;
                        continue;
                    } else if (c == '+') {

                        // access table
                        table_content = table[current_state][1];

                        if (table_content == null) {
                            System.out.println("Error: invalid syntax");
                            break;
                        } else {
                            if (table_content.charAt(0) == 's') {

                                System.out.println("Action: " + table_content);
                                // push the token and the state to the stack
                                stack.push("+");
                                stack.push(table_content.substring(1));
                                System.out.println("Stack: " + stack + " input: " + input.substring(counter + 1));
                                counter++;

                            } else if (table_content.charAt(0) == 'r') {

                                System.out.println("Action: " + table_content);
                                //call the reduce function will perform all functions upto the next token iteration
                                reduce(stack, table_content.substring(1));
                                System.out.println("Stack: " + stack + " input: " + input.substring(counter));

                            } else {
                                // found null and instruction is invalid
                                // ******** produce error *********

                                System.out.println("Error: invalid syntax");
                                break;
                            }
                        }


                    } else if (c == '*') {
                        table_content = table[current_state][2];

                        if (table_content == null) {
                            System.out.println("Error: invalid syntax");
                            break;
                        } else {
                            if (table_content.charAt(0) == 's') {

                                System.out.println("Action: " + table_content);
                                // push the token and the state to the stack
                                stack.push("*");
                                stack.push(table_content.substring(1));
                                System.out.println("Stack: " + stack + " input: " + input.substring(counter + 1));
                                counter++;

                            } else if (table_content.charAt(0) == 'r') {

                                System.out.println("Action: " + table_content);
                                //call the reduce function will perform all functions upto the next token iteration
                                reduce(stack, table_content.substring(1));
                                System.out.println("Stack: " + stack + " input: " + input.substring(counter));

                            } else {
                                // found null and instruction is invalid
                                // ******** produce error *********

                                System.out.println("Error: invalid syntax");
                                break;
                            }
                        }
                    } else if(c == '(') {

                        table_content = table[current_state][3];

                        if (table_content == null) {
                            System.out.println("Error: invalid syntax");
                            break;
                        } else {
                            if (table_content.charAt(0) == 's') {

                                System.out.println("Action: " + table_content);
                                // push the token and the state to the stack
                                stack.push("(");
                                stack.push(table_content.substring(1));
                                System.out.println("Stack: " + stack + " input: " + input.substring(counter + 1));
                                counter++;

                            } else if (table_content.charAt(0) == 'r') {

                                System.out.println("Action: " + table_content);
                                //call the reduce function will perform all functions upto the next token iteration
                                reduce(stack, table_content.substring(1));
                                System.out.println("Stack: " + stack + " input: " + input.substring(counter));

                            } else {
                                // found null and instruction is invalid
                                // ******** produce error *********

                                System.out.println("Error: invalid syntax");
                                break;
                            }
                        }


                    }else if(c == ')') {

                        table_content = table[current_state][4];

                        if (table_content == null) {
                            System.out.println("Error: invalid syntax");
                            break;
                        } else {
                            if (table_content.charAt(0) == 's') {

                                System.out.println("Action: " + table_content);
                                // push the token and the state to the stack
                                stack.push(")");
                                stack.push(table_content.substring(1));
                                System.out.println("Stack: " + stack + " input: " + input.substring(counter + 1));
                                counter++;

                            } else if (table_content.charAt(0) == 'r') {

                                System.out.println("Action: " + table_content);
                                //call the reduce function will perform all functions upto the next token iteration
                                reduce(stack, table_content.substring(1));
                                System.out.println("Stack: " + stack + " input: " + input.substring(counter));

                            } else {
                                // found null and instruction is invalid
                                // ******** produce error *********

                                System.out.println("Error: invalid syntax");
                                break;
                            }
                        }

                    }else if (c == '$') {
                        // do not increment the counter anymore bc end of string
                        // start indexing table for instructions
                        // should all be reduce instruction
                        // have check for table_content == 'acc'
                        //   if so then end string check and print out that the string was accepted
                        table_content = table[current_state][5];
                        if (table_content == null) {
                            System.out.println("Error: invalid syntax");
                            break;
                        }else if(table_content.equals("acc")){
                            // found the acc element which means the sentence is valid
                            System.out.println("Action: " + table_content);
                            System.out.println("sentence is accepted");
                            break;
                        }else{
                            System.out.println("Action: " + table_content);
                            reduce(stack,table_content.substring(1));
                            System.out.println("Stack: " + stack + " input: " + input.substring(counter));
                        }
                    }else {
                        System.out.println("Error: invalid syntax");
                        break;
                    }

                    System.out.println("------------------------");

                } // end parse loop
            } // end exit keyword condition check
        } // end main loop
    } // end main method
}// end class

