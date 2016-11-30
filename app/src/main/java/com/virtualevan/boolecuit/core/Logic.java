package com.virtualevan.boolecuit.core;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.Map;

/**
 * Created by VirtualEvan on 29/11/2016.
 */

public class Logic {
    public static String calc(String expresion, Map<String, Integer> mapaVariables){
        //ScriptEngineManager engineManager = new ScriptEngineManager();
        //ScriptEngine engine = engineManager.getEngineByName("js");
        //Interpreter interpreter = new Interpreter();
        String[] varArray = new String[mapaVariables.size()];
        int count = 0;

        for (String key: mapaVariables.keySet()) {
            varArray[count] = key + " = " + mapaVariables.get(key);
            count++;
        }

        Object[] params = new Object[] { "javaScriptParam" };

        // Every Rhino VM begins with the enter()
        // This Context is not Android's Context
        Context rhino = Context.enter();

        // Turn off optimization to make Rhino Android compatible
        rhino.setOptimizationLevel(-1);
        try {
            Scriptable scope = rhino.initStandardObjects();

            // Note the forth argument is 1, which means the JavaScript source has
            // been compressed to only one line using something like YUI
            for (String s : varArray) {
                rhino.evaluateString(scope, s, "JavaScript", 1, null).toString();
            }

            String expr = expresion.replaceAll("NOT", "!");
            expr = expr.replaceAll("OR", "|");
            expr = expr.replaceAll("AND", "&");

            String toRet = rhino.evaluateString(scope, expr, "JavaScript", 1, null).toString().replaceAll(".0","");
            toRet = toRet.replaceAll("true","1");
            toRet = toRet.replaceAll("false","0");

            return toRet;


        } finally {
            Context.exit();
        }

    }
}
