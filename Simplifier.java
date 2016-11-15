import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Math;

public class Simplifier {
  private char[] varArray;


  public void calc(){
  }

  public static void main( String args[] ){
    Map<String, Integer> mapaVariables = new LinkedHashMap<>();
    char[] valoresBinarios;
    String auxBinario;
    String auxFormato;

    Pattern pattern = Pattern.compile("([A-Z])");
    Matcher matcher = pattern.matcher(args[0]);

    while( matcher.find() ){
      mapaVariables.put(matcher.group(1),0);
    }

    for( int i = 0;  i<Math.pow(2,mapaVariables.size()); i++){
      auxBinario = Integer.toBinaryString(i);
      auxFormato = String.format("%0"+mapaVariables.size()+"d", Integer.parseInt(auxBinario));
      valoresBinarios = auxFormato.toCharArray();
      for (int j = 0; j < valoresBinarios.length; j++){
        mapaVariables.put(mapaVariables.keySet().toArray()[j].toString(), Character.getNumericValue(valoresBinarios[j]));
      }
      for (String key: mapaVariables.keySet()) {
        System.out.println(key + " - " + mapaVariables.get(key));
      }
      System.out.println( );
    }


  }

}