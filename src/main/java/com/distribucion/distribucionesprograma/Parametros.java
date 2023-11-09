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

    public static JTextField inputMu, inputSigma, inputLambda, inputn, inputp, inputV, inputDF1, inputDF2, inputK;
    public static JLabel labelSigma, labelMu, labelLambda, labeln, labelp, labelV, labelDF1, labelDF2, labelK;
    public static String tipoDist;

    static void CrearParam(JPanel Panel) {
        JPanel filaPar1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Panel.add(filaPar1);

        JPanel filaPar2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Panel.add(filaPar2);

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
        labelV = new JLabel("v = ");
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
        labelK.setFont(fontDF1);
        filaPar1.add(labelK);
        labelK.setVisible(false);

        //k - Campo texto
        inputK = new JTextField(3);
        filaPar1.add(inputK);
        inputK.setVisible(false);
    }

    static void ActualizarParam(String distribucion,
            JComboBox comboBoxProb,
            String[] optionsProbSim, String[] optionsProbDisc, String[] optionsProbCont) {
        //Resetear UI
        //Normal
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

        switch (distribucion) {
            case "Normal": {
                labelSigma.setVisible(true);
                labelMu.setVisible(true);
                inputMu.setVisible(true);
                inputSigma.setVisible(true);
                tipoDist = "Simetrica";
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
            case "t de Student": {
                labelV.setVisible(true);
                inputV.setVisible(true);
                tipoDist = "Simetrica";
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
