package com.distribucion.distribucionesprograma;
import java.awt.Color;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
/**
 *
 * @author santi
 */
public class Formulas {

    static void generarFormula(String distribucion) {
        String formula = "";
        
        //PARA JAVA >14
//        switch (distribucion) {
//            case "Normal" -> {      
//                formula = "\\mathbf{\\mbox{Función de densidad:}} \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$f(x) = \\frac{1}{{\\sigma \\sqrt{2\\pi}}} \\cdot e^{-\\frac{(x - \\mu)^2}{2\\sigma^2}}$$ \\\\";
//                formula += "\\end{array} \\\\";
//                formula += "Donde: \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$-\\infty < \\mu < +\\infty$$ \\\\";
//                formula += "$$\\sigma>0$$ \\\\";
//                formula += "$$-\\infty < x < +\\infty$$ \\\\";
//                formula += "\\end{array}";
//                formula += " \\\\";
//                formula += "\\hline";
//                formula += "\\mathbf{\\mbox{Esperanza:}} \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$ E(X)= \\mu$$ \\\\";
//                formula += "\\end{array}";
//                formula += " \\\\";
//                formula += "\\hline";
//                formula += "\\mathbf{\\mbox{Varianza:}} \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$ Var(X) = \\sigma^2   $$"; 
//                formula += "\\end{array}";
//            }
//            case "Binomial" -> {      
//                formula = "\\mathbf{\\mbox{Función de probabilidad:}} \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$P(X = x) = {{n}\\choose{x}} p^x (1-p)^{n-x}$$ \\\\";
//                formula += "\\end{array} \\\\";
//                formula += "Donde: \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$n = 1,2,3...$$ \\\\";
//                formula += "$$0 \\leq p \\leq 1$$ \\\\";
//                formula += "$$x = 0,1,...,n$$ \\\\";
//                formula += "\\end{array}";
//                formula += " \\\\";
//                formula += "\\hline";
//                formula += "\\mathbf{\\mbox{Esperanza:}} \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$E(X) = np$$ \\\\";
//                formula += "\\end{array}";
//                formula += " \\\\";
//                formula += "\\hline";
//                formula += "\\mathbf{\\mbox{Varianza:}} \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$ Var(X) = np(1-p) $$"; 
//                formula += "\\end{array}";
//            }
//            case "Exponencial" -> {      
//                formula = "\\mathbf{\\mbox{Función de densidad:}} \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$\\lambda e^{-\\lambda x}$$ \\\\";
//                formula += "\\end{array} \\\\";
//                formula += "Donde: \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$\\lambda > 0$$ \\\\";
//                formula += "$$x>0$$ \\\\";
//                formula += "\\end{array}";
//                formula += " \\\\";
//                formula += "\\hline";
//                formula += "\\mathbf{\\mbox{Esperanza:}} \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$ E(X)= \\frac{1}{\\lambda}$$ \\\\";
//                formula += "\\end{array}";
//                formula += " \\\\";
//                formula += "\\hline";
//                formula += "\\mathbf{\\mbox{Varianza:}} \\\\";
//                formula += "\\begin{array}{c}";
//                formula += "$$ Var(X) = \\frac{1}{\\lambda^2}$$"; 
//                formula += "\\end{array}";
//            }
//            default -> formula = "Ha ocurrido un error";
//        }

        //PARA JAVA <14
                switch (distribucion) {
            case "Normal": {      
                formula = "\\mathbf{\\mbox{Función de densidad:}} \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$f(x) = \\frac{1}{{\\sigma \\sqrt{2\\pi}}} \\cdot e^{-\\frac{(x - \\mu)^2}{2\\sigma^2}}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "Donde: \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$-\\infty < \\mu < +\\infty$$ \\\\";
                formula += "$$\\sigma>0$$ \\\\";
                formula += "$$-\\infty < x < +\\infty$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\mathbf{\\mbox{Esperanza:}} \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$ E(X)= \\mu$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\mathbf{\\mbox{Varianza:}} \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$ Var(X) = \\sigma^2   $$"; 
                formula += "\\end{array}";
                break;
            }
            case "Binomial": {      
                formula = "\\mathbf{\\mbox{Función de probabilidad:}} \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$P(X = x) = {{n}\\choose{x}} p^x (1-p)^{n-x}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "Donde: \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$n = 1,2,3...$$ \\\\";
                formula += "$$0 \\leq p \\leq 1$$ \\\\";
                formula += "$$x = 0,1,...,n$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\mathbf{\\mbox{Esperanza:}} \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$E(X) = np$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\mathbf{\\mbox{Varianza:}} \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$ Var(X) = np(1-p) $$"; 
                formula += "\\end{array}";
                break;
            }
            case "Exponencial": {      
                formula = "\\mathbf{\\mbox{Función de densidad:}} \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$\\lambda e^{-\\lambda x}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "Donde: \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$\\lambda > 0$$ \\\\";
                formula += "$$x>0$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\mathbf{\\mbox{Esperanza:}} \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$ E(X)= \\frac{1}{\\lambda}$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\mathbf{\\mbox{Varianza:}} \\\\";
                formula += "\\begin{array}{c}";
                formula += "$$ Var(X) = \\frac{1}{\\lambda^2}$$"; 
                formula += "\\end{array}";
                break;
            }
//            default: formula = "Error";
        }
        
        TeXFormula formulaTex = new TeXFormula(formula);
        
        formulaTex.createPNG(TeXConstants.STYLE_DISPLAY, 22, "imgformula.png", null, Color.black);

    }
}
