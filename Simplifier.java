import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.lang.Math;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Simplifier {


  public static void calc(String expresion, Map<String, Integer> mapaVariables){
    ScriptEngine engine = new ScriptEngineManager().getEngineByName("JavaScript");
    String[] varArray = new String[mapaVariables.size()];
    int count = 0;
    for (String key: mapaVariables.keySet()) {
      System.out.print(key + " ");
    }
    System.out.println();
    for (String key: mapaVariables.keySet()) {
      System.out.print(mapaVariables.get(key)+ " ");

      varArray[count] = key + " = " + mapaVariables.get(key);
      count++;
    }
    System.out.print("     ");

    try{
      for (String s : varArray) {
          engine.eval(s);
      }

    String expr = expresion.replaceAll("NOT", "!");
    expr = expr.replaceAll("OR", "|");
    expr = expr.replaceAll("AND", "&");
    
    System.out.println(engine.eval(expr));
    }
    catch(ScriptException e){
      System.out.println(e);
    }
  }

  public static void main( String args[] ){
    Map<String, Integer> mapaVariables = new LinkedHashMap<>();
    char[] valoresBinarios;
    String auxBinario;
    String auxFormato;

    Pattern pattern = Pattern.compile("([a-z])");
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
      calc( args[0], mapaVariables );
      /*
      for (String key: mapaVariables.keySet()) {
        //System.out.println(key + " - " + mapaVariables.get(key));
      }
      */
      //System.out.println( );
    }


  }

}