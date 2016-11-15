class TruthTable{
  private int n ;
  TruthTable(int n) {
    this.n = n ;
    printTruthTable() ;
  }


  private void printTruthTable() {
    //WRITE CODE HERE.
    //Show truth table for n < 8 along with print number of rows in truth table
    //For others just print number of rows in truth table, but internally it should build truth table like n < 8
    if(n>=1 && n<8){
        int rows=(int)Math.pow(2, n);
        for (int i=0; i<rows; i++) {
            for (int j=n-1; j>=0; j--) {
                System.out.print((i/(int) Math.pow(2, j))%2 + " ");
            }
            System.out.println();
        }
        System.out.println("For "+n+" inputs, table size is "+ rows);
    }
    else {
        long rows1=(long)Math.pow(2, n);
        for (int i=0; i<rows1; i++) {
            for (int j=n-1; j>=0; j--) {
                int table=(i/(int) Math.pow(2, j))%2;
            }
        }
        System.out.println("For "+n+" inputs, table size is "+ rows1);
    }

  }

  private static void testBench() {
    //CANNOT CHANGE BELOW
    for (int i = 1 ; i < 40; ++i) {
      System.out.println("------------Truth table of " + i + " inputs function --------------");
      TruthTable t = new TruthTable(i) ;
    }
  }

  public static void main(String[] args) {
    //CANNOT CHANGE BELOW
    System.out.println("TruthTable.java");
    testBench();
    System.out.println("Done");
  }
}