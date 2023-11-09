package com.distribucion.distribucionesprograma;

import java.awt.Color;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;

/**
 *
 * @author Santiago Garcia Sanchez
 */
public class Formulas {

    static void generarFormula(String distribucion) {
        String formula = "";

        switch (distribucion) {
            case "Normal": {
                formula = "\\begin{array}{lr}\\mathbf{\\mbox{Función de densidad:}}&$$f(x) = \\frac{1}{{\\sigma \\sqrt{2\\pi}}} \\cdot e^{-\\frac{(x - \\mu)^2}{2\\sigma^2}}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\begin{array}{cc}";
                formula += "\\ Donde: &$$-\\infty < \\mu < +\\infty$$ \\\\";
                formula += "&$$\\sigma>0$$ \\\\";
                formula += "&$$-\\infty < x < +\\infty$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Función generadora de momentos:}}&$$M_X(t) = e^{\\mu t + t^2\\frac{\\sigma^2}{2}}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Esperanza:}} & $$ E(X)= \\mu$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Varianza:}} & $$ Var(X) = \\sigma^2   $$";
                formula += "\\end{array}";
                break;
            }
            case "Chi-cuadrado": {
                formula = "\\begin{array}{lr}\\mathbf{\\mbox{Función de densidad:}}&$$f(x) = \\frac{1}{2^{\\frac{k}{2}}\\Gamma\\left(\\frac{k}{2}\\right)} x^{\\frac{k}{2} - 1} e^{-\\frac{x}{2}}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\begin{array}{cccc}";
                formula += "\\ Donde: &$$k\\in \\mathbb{N}^{+} $$& & \\\\";
                formula += "&$$x>0$$&Para: & $$k=1$$ \\\\";
                formula += "&$$x \\geq 0$$&Para: & $$k>1$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Función generadora de momentos:}}&$$M_X(t) = (1 - 2t)^{-\\frac{k}{2}}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Esperanza:}} & $$ E(X)= k$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Varianza:}} & $$ Var(X) = 2k   $$";
                formula += "\\end{array}";
                break;
            }
            case "Exponencial": {
                formula = "\\begin{array}{lr}\\mathbf{\\mbox{Función de densidad:}}&$$f(x) = \\lambda e^{-\\lambda x}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\begin{array}{cc}";
                formula += "\\ Donde:&$$\\lambda > 0$$  \\\\";
                formula += "&$$x>0$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Función generadora de momentos:}}&$$M_X(t) = \\frac{\\lambda}{\\lambda - t}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\begin{array}{cc}";
                formula += "\\ Donde:&$$t<\\lambda$$  \\\\";
                formula += "\\end{array}";
                formula += "\\hline";
                formula += "\\begin{array}{c}";
                formula += "\\mathbf{\\mbox{Esperanza:}}&$$ E(X)= \\frac{1}{\\lambda}$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{c}";
                formula += "\\mathbf{\\mbox{Varianza:}}&$$ Var(X) = \\frac{1}{\\lambda^2}$$ \\\\";
                formula += "\\end{array}";
                break;
            }
            case "F": {
                formula = "\\begin{array}{lr}\\mathbf{\\mbox{Función de densidad:}}&$$f(x) = \\frac{1}{B\\left(\\frac{d_1}{2}, \\frac{d_2}{2}\\right)} \\cdot \\left(\\frac{d_1}{d_2}\\right)^{\\frac{d_1}{2}} \\cdot x^{\\frac{d_1}{2} - 1} \\cdot \\left(1 + \\frac{d_1}{d_2}x\\right)^{-\\frac{(d_1 + d_2)}{2}}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\begin{array}{cc}";
                formula += "\\ Donde: &$$d_1, d_2>0$$ \\\\";
                formula += "\\ &$$x>0$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Esperanza:}} & $$E(X)= \\frac{d_2}{d_2-2} $$ \\\\";
                formula += "\\end{array}";
                formula += "\\begin{array}{cc}";
                formula += "\\ Para: & $$d_2>2$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Varianza:}} & $$ Var(X) = \\frac{2d_2^2(d_1 + d_2 - 2)}{d_1(d_2 - 2)^2(d_2 - 4)}$$";
                formula += "\\end{array}";
                formula += "\\begin{array}{cc}";
                formula += "\\ Para: & $$d_2>4$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                break;
            }
            case "t de Student": {
                formula = "\\begin{array}{lr}\\mathbf{\\mbox{Función de densidad:}}&$$f(x) = \\frac{\\Gamma\\left(\\frac{\\nu+1}{2}\\right)}{\\sqrt{\\nu\\pi}\\,\\Gamma\\left(\\frac{\\nu}{2}\\right)}  \\left(1+\\frac{x^2}{\\nu}\\right) ^ {-\\frac{(\\nu+1)}{2}}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\begin{array}{cc}";
                formula += "\\ Donde: &$$v>0$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Esperanza:}} & $$ E(X)= 0$$ \\\\";
                formula += "\\end{array}";
                formula += "\\begin{array}{cc}";
                formula += "\\ Para: & $$v>1$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Varianza:}} & $$ Var(X) = \\frac{v}{v-2}$$";
                formula += "\\end{array}";
                formula += "\\begin{array}{cc}";
                formula += "\\ Para: & $$v>2$$ \\\\";
                formula += "\\end{array}";
                break;
            }
            case "Binomial": {
                formula = "\\begin{array}{lr}\\mathbf{\\mbox{Función de probabilidad:}}&$$P(X = x) = {{n}\\choose{x}} p^x (1-p)^{n-x}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\begin{array}{cc}";
                formula += "\\ Donde:&$$n = 1,2,3...$$  \\\\";
                formula += "&$$0 \\leq p \\leq 1$$ \\\\";
                formula += "&$$x = 0,1,...,n$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Función generadora de momentos:}}&$$M_X(t) = (1 + p(e^t -1))^n$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Esperanza:}}&$$E(X) = np$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Varianza:}}&$$ Var(X) = np(1-p) $$ \\\\";
                formula += "\\end{array}";
                break;
            }
            case "Poisson": {
                formula = "\\begin{array}{lr}\\mathbf{\\mbox{Función de probabilidad:}}&$$P(X = x) = \\frac{e^{-\\lambda} \\lambda^x}{x!}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\begin{array}{cc}";
                formula += "\\ Donde:&$$\\lambda > 0$$  \\\\";
                formula += "&$$x \\in \\mathbb{N}$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\begin{array}{lr}";
                formula += "\\mathbf{\\mbox{Función generadora de momentos:}}&$$M_X(t) = e^{\\lambda(e^t - 1)}$$ \\\\";
                formula += "\\end{array} \\\\";
                formula += "\\begin{array}{cc}";
                formula += "\\ Donde:&$$\\lambda > 0$$  \\\\";
                formula += "\\end{array}";
                formula += "\\hline";
                formula += "\\begin{array}{c}";
                formula += "\\mathbf{\\mbox{Esperanza:}}&$$ E(X)= \\lambda$$ \\\\";
                formula += "\\end{array}";
                formula += " \\\\";
                formula += "\\hline";
                formula += "\\begin{array}{c}";
                formula += "\\mathbf{\\mbox{Varianza:}}&$$ Var(X)= \\lambda$$ \\\\";
                formula += "\\end{array}";
                break;
            }
            default:
                formula = "Error";
        }

        TeXFormula formulaTex = new TeXFormula(formula);

        formulaTex.createPNG(TeXConstants.STYLE_DISPLAY, 22, "imgformula.png", null, Color.black);

    }
}
