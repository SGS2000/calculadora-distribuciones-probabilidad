package com.distribucion.distribucionesprograma;

//UI
import java.awt.FlowLayout;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JComboBox;

//Texto
import java.awt.Font;

/**
 *
 * @author Santiago Garcia Sanchez
 */
public class Parametros {

    public static JTextField inputMu, inputSigma, inputLambda, inputn, inputp, inputV, inputDF1,
            inputDF2, inputK, inputA, inputB, inputN, inputN1, inputAlfa, inputBeta, inputr,
            inputEta, inputM;
    public static JLabel labelSigma, labelMu, labelLambda, labeln, labelp, labelV, labelDF1,
            labelDF2, labelK, labelA, labelB, labelN, labelN1, labelAlfa, labelBeta, labelr,
            labelEta, labelM;;
    public static String tipoDist;

    static void CrearParam(JPanel Panel) {
        JPanel filaPar1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Panel.add(filaPar1);

        JPanel filaPar2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Panel.add(filaPar2);

        JPanel filaPar3 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Panel.add(filaPar3);

        ///Distribución normal///
        //mu//
        //mu - Label
        labelMu = new JLabel("μ = ");
        Font fontMu = new Font(labelMu.getFont().getName(), Font.BOLD, labelMu.getFont().getSize());
        labelMu.setFont(fontMu);
        filaPar1.add(labelMu);

        //mu - Campo texto
        inputMu = new JTextField(3);
        filaPar1.add(inputMu);

        //Sigma//
        //Sigma - Label
        labelSigma = new JLabel("σ = ");
        Font fontSigma = new Font(labelSigma.getFont().getName(), Font.BOLD, labelSigma.getFont().getSize());
        labelSigma.setFont(fontSigma);
        filaPar2.add(labelSigma);

        //Sigma - Campo texto
        inputSigma = new JTextField(3);
        filaPar2.add(inputSigma);

        ///Distribución exponencial y Poisson///
        //Lambda - Label
        labelLambda = new JLabel("λ = ");
        Font fontLambda = new Font(labelLambda.getFont().getName(), Font.BOLD, labelLambda.getFont().getSize());
        labelLambda.setFont(fontLambda);
        filaPar1.add(labelLambda);
        labelLambda.setVisible(false);

        //Lambda- Campo texto
        inputLambda = new JTextField(3);
        filaPar1.add(inputLambda);
        inputLambda.setVisible(false);

        ///Distribución binomial///
        //n - Label
        labeln = new JLabel("n = ");
        Font fontn = new Font(labeln.getFont().getName(), Font.BOLD, labeln.getFont().getSize());
        labeln.setFont(fontn);
        filaPar1.add(labeln);
        labeln.setVisible(false);

        //n - Campo texto
        inputn = new JTextField(3);
        filaPar1.add(inputn);
        inputn.setVisible(false);

        //p - Label
        labelp = new JLabel("p = ");
        Font fontp = new Font(labelp.getFont().getName(), Font.BOLD, labelp.getFont().getSize());
        labelp.setFont(fontp);
        filaPar2.add(labelp);
        labelp.setVisible(false);

        //p - Campo texto
        inputp = new JTextField(3);
        filaPar2.add(inputp);
        inputp.setVisible(false);

        ///Distribución t-student///
        //v - Label
        labelV = new JLabel("ν = ");
        Font fontV = new Font(labelV.getFont().getName(), Font.BOLD, labelV.getFont().getSize());
        labelV.setFont(fontV);
        filaPar1.add(labelV);
        labelV.setVisible(false);

        //v- Campo texto
        inputV = new JTextField(3);
        filaPar1.add(inputV);
        inputV.setVisible(false);

        ///Distribución F de Snedecor///
        //df1 - Label
        labelDF1 = new JLabel("df\u2081 = ");
        Font fontDF1 = new Font(labelDF1.getFont().getName(), Font.BOLD, labelDF1.getFont().getSize());
        labelDF1.setFont(fontDF1);
        filaPar1.add(labelDF1);
        labelDF1.setVisible(false);

        //df1 - Campo texto
        inputDF1 = new JTextField(3);
        filaPar1.add(inputDF1);
        inputDF1.setVisible(false);

        ///df2 - Label
        labelDF2 = new JLabel("df\u2082 = ");
        Font fontDF2 = new Font(labelDF2.getFont().getName(), Font.BOLD, labelDF2.getFont().getSize());
        labelDF2.setFont(fontDF2);
        filaPar2.add(labelDF2);
        labelDF2.setVisible(false);

        //df2 - Campo texto
        inputDF2 = new JTextField(3);
        filaPar2.add(inputDF2);
        inputDF2.setVisible(false);

        ///Distribución chi-cuadrado///
        //k - Label
        labelK = new JLabel("k = ");
        Font fontK = new Font(labelK.getFont().getName(), Font.BOLD, labelK.getFont().getSize());
        labelK.setFont(fontK);
        filaPar1.add(labelK);
        labelK.setVisible(false);

        //k - Campo texto
        inputK = new JTextField(3);
        filaPar1.add(inputK);
        inputK.setVisible(false);

        ///Distribución uniforme///
        //a - label
        labelA = new JLabel("a = ");
        Font fontA = new Font(labelA.getFont().getName(), Font.BOLD, labelA.getFont().getSize());
        labelA.setFont(fontA);
        filaPar1.add(labelA);
        labelA.setVisible(false);

        //a - Campo texto
        inputA = new JTextField(3);
        filaPar1.add(inputA);
        inputA.setVisible(false);

        //b - label
        labelB = new JLabel("b = ");
        Font fontB = new Font(labelB.getFont().getName(), Font.BOLD, labelB.getFont().getSize());
        labelB.setFont(fontB);
        filaPar2.add(labelB);
        labelB.setVisible(false);

        //b - Campo texto
        inputB = new JTextField(3);
        filaPar2.add(inputB);
        inputB.setVisible(false);

        ///Distribución binomial negativa///
        //r - Label
        labelr = new JLabel("r = ");
        Font fontr = new Font(labelr.getFont().getName(), Font.BOLD, labelr.getFont().getSize());
        labelr.setFont(fontr);
        filaPar1.add(labelr);
        labelr.setVisible(false);

        //r - Campo texto
        inputr = new JTextField(3);
        filaPar1.add(inputr);
        inputr.setVisible(false);

        ///Distribución hipergeométrica///
        //N - label
        labelN = new JLabel("N = ");
        Font fontN = new Font(labelN.getFont().getName(), Font.BOLD, labelN.getFont().getSize());
        labelN.setFont(fontN);
        filaPar2.add(labelN);
        labelN.setVisible(false);

        //N - Campo texto
        inputN = new JTextField(3);
        filaPar2.add(inputN);
        inputN.setVisible(false);

        //N1 - label
        labelN1 = new JLabel("N1 = ");
        Font fontN1 = new Font(labelN1.getFont().getName(), Font.BOLD, labelN1.getFont().getSize());
        labelN1.setFont(fontN1);
        filaPar3.add(labelN1);
        labelN1.setVisible(false);

        //N1 - Campo texto
        inputN1 = new JTextField(3);
        filaPar3.add(inputN1);
        inputN1.setVisible(false);

        ////Distribución Beta o Gama
        //Alfa - label
        labelAlfa = new JLabel("α = ");
        Font fontAlfa = new Font(labelAlfa.getFont().getName(), Font.BOLD, labelAlfa.getFont().getSize());
        labelAlfa.setFont(fontAlfa);
        filaPar1.add(labelAlfa);
        labelAlfa.setVisible(false);

        //Alfa - Campo texto
        inputAlfa = new JTextField(3);
        filaPar1.add(inputAlfa);
        inputAlfa.setVisible(false);

        //Beta - label
        labelBeta = new JLabel("β = ");
        Font fontBeta = new Font(labelBeta.getFont().getName(), Font.BOLD, labelBeta.getFont().getSize());
        labelBeta.setFont(fontBeta);
        filaPar2.add(labelBeta);
        labelBeta.setVisible(false);

        //Beta - Campo texto
        inputBeta = new JTextField(3);
        filaPar2.add(inputBeta);
        inputBeta.setVisible(false);

        ////Distribución Weibull
        //Eta - label
        labelEta = new JLabel("η = ");
        Font fontEta = new Font(labelEta.getFont().getName(), Font.BOLD, labelEta.getFont().getSize());
        labelEta.setFont(fontEta);
        filaPar1.add(labelEta);
        labelEta.setVisible(false);

        //Eta - Campo texto
        inputEta = new JTextField(3);
        filaPar1.add(inputEta);
        inputEta.setVisible(false);
        
        ////Distribución Pareto
        //m - label
        labelM = new JLabel("m = ");
        Font fontM = new Font(labelM.getFont().getName(), Font.BOLD, labelM.getFont().getSize());
        labelM.setFont(fontM);
        filaPar2.add(labelM);
        labelM.setVisible(false);

        //Eta - Campo texto
        inputM = new JTextField(3);
        filaPar2.add(inputM);
        inputM.setVisible(false);
    }

    static void ActualizarParam(String distribucion,
            JComboBox comboBoxProb,
            String[] optionsProbSim, String[] optionsProbDisc, String[] optionsProbCont) {
        //Resetear UI
        //Normal y Log-normal
        labelSigma.setVisible(false);
        labelMu.setVisible(false);
        inputMu.setVisible(false);
        inputSigma.setVisible(false);
        inputMu.setText("");
        inputSigma.setText("");
        //Exponencial y Poisson
        labelLambda.setVisible(false);
        inputLambda.setVisible(false);
        inputLambda.setText("");
        //F
        labelDF1.setVisible(false);
        inputDF1.setVisible(false);
        labelDF2.setVisible(false);
        inputDF2.setVisible(false);
        inputDF1.setText("");
        inputDF2.setText("");
        //t de Student
        labelV.setVisible(false);
        inputV.setVisible(false);
        inputV.setText("");
        //Binomial
        labeln.setVisible(false);
        inputn.setVisible(false);
        inputn.setText("");
        labelp.setVisible(false);
        inputp.setVisible(false);
        inputp.setText("");
        //Chi cuadrado
        labelK.setVisible(false);
        inputK.setVisible(false);
        inputK.setText("");
        //Uniforme
        labelA.setVisible(false);
        inputA.setVisible(false);
        inputA.setText("");
        labelB.setVisible(false);
        inputB.setVisible(false);
        inputB.setText("");
        //Hipergeométrica
        labelN.setVisible(false);
        inputN.setVisible(false);
        inputN.setText("");
        labelN1.setVisible(false);
        inputN1.setVisible(false);
        inputN1.setText("");
        //Beta o Gama
        labelAlfa.setVisible(false);
        inputAlfa.setVisible(false);
        inputAlfa.setText("");
        labelBeta.setVisible(false);
        inputBeta.setVisible(false);
        inputBeta.setText("");
        //Binomial negativa
        labelr.setVisible(false);
        inputr.setVisible(false);
        inputr.setText("");
        //Weibull
        labelEta.setVisible(false);
        inputEta.setVisible(false);
        inputEta.setText("");
        //Pareto
        labelM.setVisible(false);
        inputM.setVisible(false);
        inputM.setText("");

        switch (distribucion) {
            case "Normal": {
                labelSigma.setVisible(true);
                labelMu.setVisible(true);
                inputMu.setVisible(true);
                inputSigma.setVisible(true);
                tipoDist = "Simetrica";
                break;
            }
            case "Beta": {
                labelAlfa.setVisible(true);
                inputAlfa.setVisible(true);
                labelBeta.setVisible(true);
                inputBeta.setVisible(true);
                tipoDist = "Continua";
                break;
            }
            case "Chi-cuadrado": {
                labelK.setVisible(true);
                inputK.setVisible(true);
                tipoDist = "Continua";
                break;
            }
            case "Exponencial": {
                labelLambda.setVisible(true);
                inputLambda.setVisible(true);
                tipoDist = "Continua";
                break;
            }
            case "F": {
                labelDF1.setVisible(true);
                inputDF1.setVisible(true);
                labelDF2.setVisible(true);
                inputDF2.setVisible(true);
                tipoDist = "Continua";
                break;
            }
            case "Gama": {
                labelAlfa.setVisible(true);
                inputAlfa.setVisible(true);
                labelBeta.setVisible(true);
                inputBeta.setVisible(true);
                tipoDist = "Continua";
                break;
            }
            case "Log-normal": {
                labelSigma.setVisible(true);
                labelMu.setVisible(true);
                inputMu.setVisible(true);
                inputSigma.setVisible(true);
                tipoDist = "Continua";
                break;
            }
            case "Pareto I": {
                labelAlfa.setVisible(true);
                inputAlfa.setVisible(true);
                labelM.setVisible(true);
                inputM.setVisible(true);
                tipoDist = "Continua";
                break;
            }
            case "t de Student": {
                labelV.setVisible(true);
                inputV.setVisible(true);
                tipoDist = "Simetrica";
                break;
            }
            case "Uniforme continua": {
                labelA.setVisible(true);
                inputA.setVisible(true);
                labelB.setVisible(true);
                inputB.setVisible(true);
                tipoDist = "Continua";
                break;
            }
            case "Weibull": {
                labelEta.setVisible(true);
                inputEta.setVisible(true);
                labelBeta.setVisible(true);
                inputBeta.setVisible(true);
                tipoDist = "Continua";
                break;
            }
            case "Bernoulli": {
                labelp.setVisible(true);
                inputp.setVisible(true);
                tipoDist = "Discreta";
                break;
            }
            case "Binomial": {
                labeln.setVisible(true);
                inputn.setVisible(true);
                labelp.setVisible(true);
                inputp.setVisible(true);
                tipoDist = "Discreta";
                break;
            }
            case "Binomial negativa": {
                labelr.setVisible(true);
                inputr.setVisible(true);
                labelp.setVisible(true);
                inputp.setVisible(true);
                tipoDist = "Discreta";
                break;
            }
            case "Geométrica": {
                labelp.setVisible(true);
                inputp.setVisible(true);
                tipoDist = "Discreta";
                break;
            }
            case "Hipergeométrica": {
                labeln.setVisible(true);
                inputn.setVisible(true);
                labelN.setVisible(true);
                inputN.setVisible(true);
                labelN1.setVisible(true);
                inputN1.setVisible(true);
                tipoDist = "Discreta";
                break;
            }
            case "Uniforme discreta": {
                labelA.setVisible(true);
                inputA.setVisible(true);
                labelB.setVisible(true);
                inputB.setVisible(true);
                tipoDist = "Discreta";
                break;
            }
            case "Poisson":
                labelLambda.setVisible(true);
                inputLambda.setVisible(true);
                tipoDist = "Discreta";
                break;
        }

        switch (tipoDist) {
            case "Continua": {
                comboBoxProb.removeAllItems();
                for (String item : optionsProbCont) {
                    comboBoxProb.addItem(item);
                }
                break;
            }
            case "Discreta": {
                comboBoxProb.removeAllItems();
                for (String item : optionsProbDisc) {
                    comboBoxProb.addItem(item);
                }
                break;
            }
            case "Simetrica": {
                comboBoxProb.removeAllItems();
                for (String item : optionsProbSim) {
                    comboBoxProb.addItem(item);
                }
                break;
            }
        }

    }
}
