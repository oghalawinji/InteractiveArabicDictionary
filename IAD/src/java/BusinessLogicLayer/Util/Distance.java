/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package BusinessLogicLayer.Util;

/**
 *
 * @author riad
 */
public class Distance  implements java.util.Comparator{

  //****************************
  // Get minimum of three values
  //****************************

  private String base = "";

    public void setBase(String base) {
        this.base = normalization(base);
    }

  private int Minimum (int a, int b, int c) {
  int mi;

    mi = a;
    if (b < mi) {
      mi = b;
    }
    if (c < mi) {
      mi = c;
    }
    return mi;

  }

  //*****************************
  // Compute Levenshtein distance
  //*****************************

  public int LD (String s, String t) {
  int d[][]; // matrix
  int n; // length of s
  int m; // length of t
  int i; // iterates through s
  int j; // iterates through t
  char s_i; // ith character of s
  char t_j; // jth character of t
  int cost; // cost

    // Step 1

    n = s.length ();
    m = t.length ();
    if (n == 0) {
      return m;
    }
    if (m == 0) {
      return n;
    }
    d = new int[n+1][m+1];

    // Step 2

    for (i = 0; i <= n; i++) {
      d[i][0] = i;
    }

    for (j = 0; j <= m; j++) {
      d[0][j] = j;
    }

    // Step 3

    for (i = 1; i <= n; i++) {

      s_i = s.charAt (i - 1);

      // Step 4

      for (j = 1; j <= m; j++) {

        t_j = t.charAt (j - 1);

        // Step 5

        if (s_i == t_j) {
          cost = 0;
        }
        else if ( i == j && i>=2 && t.charAt (j-1) == s.charAt (i - 2) && t.charAt (j - 2) == s.charAt (i-1)) {
            cost = 0;
        }
        else{
          cost = 1;
        }

        // Step 6

        d[i][j] = Minimum (d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1] + cost);

      }

    }

    //updating:
    /*if( n == m){
        for (i = 2; i <= n; i++) {
          s_i = s.charAt (i-1);
          int s_i_1 = s.charAt (i-2);
          for (j = 2; j <= m; j++) {
            t_j = t.charAt (j-1);
            int t_j_1 = t.charAt (j-2);
            if( i == j)
                if( d[i-1][j-1] + 1 == d[i][j] && s_i == t_j_1 && s_i_1 == t_j)
                    return (d[n][m]-1);
          }
        }
  }*/

    // Step 7

    return d[n][m];

  }

    private String normalization(String str) {
        String res = str.replace('ا', 'ي');
        res = res.replace('و', 'ي');
        res = res.replace('ء', 'أ');
        res = res.replace('ؤ', 'أ');
        res = res.replace('ئ', 'أ');
        res = res.replace('إ', 'أ');
        return res;
    }

    public int compare(Object o1, Object o2) {
        String first = (String)o1;
        first = normalization( first );

        String second = (String)o2;
        second = normalization( second );

        return LD( first ,base) - LD(second,base);
    }

    /*public static void main( String [] args){
        Distance d = new Distance();
        System.out.println(d.LD("حضرمتو", "حضرموت"));
        System.out.println(d.LD("بحب", "حبك"));
        System.out.println(d.LD("بحب", "حبن"));
    }*/
}
