/*
 * LINEAR PROGRAMMING OPTIMIZATION ALGORITHM
 * 
 * Type: Standard Java Implementation
 * Algorithm: Simplex Method - linear programming optimization solver
 * How it works: Uses simplex tableau to solve linear programming problems
 *              through iteratively improving solutions until optimum is reached
 * 
 * To run: Execute main() method - no input required, uses predefined linear programming problem
 */

package Optimization;

public class SimplexMethod {

   /* TABLEAU STRUCTURE
    * In this example:
    *         x₁  x₂  s₁  s₂  s₃  RHS
    * Row 0: [40, 75,  0,  0,  0,   0] ← Objective function
    * Row 1: [0.5, 3,  1,  0,  0,1000] ← Constraint 1 + slack s₁
    * Row 2: [ 2,  2,  0,  1,  0, 950] ← Constraint 2 + slack s₂
    * Row 3: [ 1,  1,  0,  0,  1, 500] ← Constraint 3 + slack s₃
    * 
    * In general:
    *         x₁   x₂   ...  xn    s₁  s₂  ...  sm  RHS
    * Row 0: [c₁,  c₂,  ...  cn,   0,  0,  ...  0,   0] ← Objective function
    * Row 1: [a₁₁, a₁₂, ...  a₁n,  1,  0,  ...  0,  b₁] ← Constraint 1 + slack s₁
    * Row 2: [a₂₁, a₂₂, ...  a₂n,  0,  1,  ...  0,  b₂] ← Constraint 2 + slack s₂
    * ...
    * Row m: [am₁, am₂, ...  amn,  0,  0,  ...  1,  bm] ← Constraint m + slack sm
    */

    private double[][] tableau; // 2D array to store the tableau
    private int rows, cols;

    /* Constructor */
    public SimplexMethod(double[][] objective, double[][] constraints, double[] rhs, boolean isMinimization) {
        
        rows = constraints.length + 1;  // row 0 = objective function, row 1+ = constraints
        cols = objective[0].length + constraints.length + 1;  // decision variables + slack variables + RHS column
        tableau = new double[rows][cols];
        
        // set up objective function in row 0
        if (isMinimization) {  // for minimization problem
            for (int j = 0; j < objective[0].length; j++) {
                tableau[0][j] = -objective[0][j];  // negate objective function coefficients in row 0
            }
        } else {  // for maximization problem
            for (int j = 0; j < objective[0].length; j++) {
                tableau[0][j] = objective[0][j];  // use objective function coefficients as-is in row 0
            }
        }
        
        // set up constraints in row 1+
        for (int i = 0; i < constraints.length; i++) {
            for (int j = 0; j < constraints[i].length; j++) {
                tableau[i + 1][j] = constraints[i][j];  // add constraint coefficients to row (i+1)
            }

            // set up slack variables
            tableau[i + 1][objective[0].length + i] = 1.0;  // add slack variable s(i+1) with coefficient 1.0 in row (i+1)

            // set up RHS values
            tableau[i + 1][cols - 1] = rhs[i];  // add RHS value to last column in row (i+1)
        }
    }


    /* Method to solve the linear programming problem */
    public double[] solve() {
        while (canImprove()) {
            int pivotCol = findPivotColumn();
            int pivotRow = findPivotRow(pivotCol);
            
            if (pivotRow == -1) {   // if no pivot row is found
                System.out.println("The problem is unbounded.");
                return null;
            }
            
            pivot(pivotRow, pivotCol);
        }
        
        double[] solution = new double[cols - rows];
        for (int j = 0; j < cols - rows; j++) { // loop through each decision variable (cols - rows = number of decision variables)
            int basicVarRow = isBasicVariable(j);
            if (basicVarRow != -1) {    // for basic variables
                solution[j] = tableau[basicVarRow][cols - 1];   // set the value of the decision variable from the RHS column
            } else {                    // for non-basic variables 
                solution[j] = 0.0;      // set the value of the decision variable to 0
            }
        }
        
        return solution;
    }


    /* Method to check if the solution can be improved */
    private boolean canImprove() {
        for (int j = 0; j < cols - 1; j++) {    // loop through each column except the RHS column
            if (tableau[0][j] > 0) {            // if there is a positive coefficient in row 0 (objective function)
                return true;                    // the solution can be improved
            }
        }
        return false;
    }


    /* Method to find the pivot column */
    private int findPivotColumn() {
        int pivotCol = 0;  // initialize the pivot column to the first column
        for (int j = 1; j < cols - 1; j++) {    // loop through each column except the RHS column
            if (tableau[0][j] > tableau[0][pivotCol]) {
                pivotCol = j;
            }
        }
        // the column with the largest positive coefficient in row 0 (objective function) is the pivot column
        return pivotCol;
    }


    /* Method to find the pivot row */
    private int findPivotRow(int pivotCol) {
        int pivotRow = -1;  // -1 indicates no pivot row found
        double minRatio = Double.MAX_VALUE;  // initialize the minimum ratio to infinity
        
        for (int i = 1; i < rows; i++) {
            if (tableau[i][pivotCol] > 0) {  // if the coefficient is positive in the pivot column
                double ratio = tableau[i][cols - 1] / tableau[i][pivotCol];  // calculate the ratio: RHS / coefficient
                if (ratio < minRatio) {
                    minRatio = ratio;
                    pivotRow = i;
                }
            }
        }
        // the row with the smallest non-negative ratio is the pivot row
        return pivotRow;
    }


    /* Method to pivot the tableau */
    private void pivot(int pivotRow, int pivotCol) {
        double pivotValue = tableau[pivotRow][pivotCol];    // get the pivot element
        for (int j = 0; j < cols; j++) {
            tableau[pivotRow][j] /= pivotValue; // make the pivot element 1.0
        }
        
        for (int i = 0; i < rows; i++) {
            if (i != pivotRow) {
                double factor = tableau[i][pivotCol];       // get the factor to eliminate
                for (int j = 0; j < cols; j++) {
                    tableau[i][j] -= factor * tableau[pivotRow][j]; // eliminate the variable
                }
            }
        }
    }


    /* Method to check if a variable is a basic variable */
    private int isBasicVariable(int col) {
        int basicVarRow = -1;       // -1 indicates no basic variable found
        int count = 0;              // count the number of non-zero values found in the column
        
        for (int i = 0; i < rows; i++) {
            if (tableau[i][col] != 0) {
                count++;
                basicVarRow = i;    // remember the row with a non-zero value
            }
        }
        
        // non-zero value must appear only once in the column and it must have a value 1.0
        if (count == 1 && tableau[basicVarRow][col] == 1.0) {
            return basicVarRow;     // the row with a non-zero value (1.0) is the row of basic variable
        } else {
            return -1;              // no basic variable found
        }
    }


    /* Method to print the tableau */
    public void printTableau() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.printf("%8.2f", tableau[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }


    /* Main method */
    public static void main(String[] args) {

        // -- UPDATE HERE -- //
        /* EXAMPLE: Maximize z = 40x + 75y subject to
         * 0.5x + 3y ≤ 1000,
         * 2x + 2y ≤ 950,
         * x + y ≤ 500,
         * x, y ≥ 0
         */
        double[][] objective = {{40, 75}};
        double[][] constraints = {{0.5, 3}, {2, 2}, {1, 1}};
        double[] rhs = {1000, 950, 500};  // RHS values for constraints
        boolean isMinimization = false;   // true = minimization, false = maximization

        // create the simplex method
        SimplexMethod simplex = new SimplexMethod(objective, constraints, rhs, isMinimization);
        System.out.println("Initial tableau:");
        simplex.printTableau();
        
        // solve the problem
        double[] solution = simplex.solve();
        
        if (solution != null) {
            System.out.println("Final tableau:");
            simplex.printTableau();
            
            System.out.println("Solution:");
            for (int i = 0; i < solution.length; i++) {
                System.out.printf("x%d = %.2f\n", i + 1, solution[i]);
            }
            
            System.out.printf("Optimal value: %.2f\n", 
                -simplex.tableau[0][simplex.cols - 1]);
        }
    }

}