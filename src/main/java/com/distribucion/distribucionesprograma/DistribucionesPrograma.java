package com.distribucion.distribucionesprograma;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.jfree.chart.ChartPanel;

/**
 *
 * @author Santiago Garcia Sanchez
 */
public class DistribucionesPrograma extends JFrame implements ActionListener {

    //Declaraciones
    JTextField inputMu, inputSigma, inputLambda, inputn, inputp;
    JButton calcularButton, borrarButton, formulasButton, momentosButton;
    JLabel labelSigma, labelMu, labelLambda, labeln, labelp;
    JComboBox<String> comboBoxProb, comboBoxDist;

    String direccion = "mayor"; //Dirección de la probabilidad

    String[] optionsProbSim = {"P(X>x)=", "P(X<x)=", "2P(X>|x|)="}; //Opciones direccion
    String[] optionsProbDisc = {"P(X=x)=", "P(X<=x)=", "P(X>=x)="};
    String[] optionsProbCont = {"P(X>x)=", "P(X<x)="};

    String distribucion = "Normal"; //Dist. actual

    public DistribucionesPrograma() {
        //Look and feel
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception ex) {
            System.out.print("Error: " + ex);
        }

        //Configuración general
        setTitle("Distribuciones de probabilidad");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /////////////////////////
        ////////LAYOUT///////////
        /////////////////////////
        setLayout(new BorderLayout(5, 10));

        // Add components to the JFrame using the BorderLayout
        /////NORTE//////
        // Create a JPanel with a BorderLayout
        JPanel northPanel = new JPanel(new BorderLayout());
        add(northPanel, BorderLayout.NORTH);
        northPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        // Label
        JLabel northLabel = new JLabel("Seleccione una distribución:");
        Font northLabelFont = new Font(northLabel.getFont().getName(), Font.BOLD, northLabel.getFont().getSize());
        northPanel.add(northLabel, BorderLayout.WEST);
        northLabel.setFont(northLabelFont);

        // JComboBox with options
        String[] optionsDist = {"Normal", "Exponencial", "Binomial"};
        comboBoxDist = new JComboBox<>(optionsDist);
        northPanel.add(comboBoxDist, BorderLayout.EAST);

        /////SUR////
        // Create a JPanel with a BorderLayout
        JPanel southPanel = new JPanel(new BorderLayout());
        add(southPanel, BorderLayout.SOUTH);
        southPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        //Sub paneles
        JPanel southPanelWest = new JPanel(new FlowLayout());
        southPanel.add(southPanelWest, BorderLayout.WEST);
        JPanel southPanelEast = new JPanel(new FlowLayout());
        southPanel.add(southPanelEast, BorderLayout.EAST);

        //Botones
        borrarButton = new JButton("Borrar");
        calcularButton = new JButton("Calcular");
        southPanelWest.add(calcularButton);
        southPanelWest.add(borrarButton);

        formulasButton = new JButton("Fórmulas");
        momentosButton = new JButton("Momentos");
        southPanelEast.add(formulasButton);
        southPanelEast.add(momentosButton);

        /////OESTE////
        JPanel westPanel = new JPanel();
        add(westPanel, BorderLayout.LINE_START);
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        westPanel.add(Box.createVerticalGlue()); //Espacio sup

        ///Distribución normal///
        //mu//
        JPanel filaPar1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        westPanel.add(filaPar1);

        //mu - Label
        labelMu = new JLabel("μ = ");
        Font fontMu = new Font(labelMu.getFont().getName(), Font.BOLD, labelMu.getFont().getSize());
        labelMu.setFont(fontMu);
        filaPar1.add(labelMu);

        //mu - Campo texto
        inputMu = new JTextField(3);
        filaPar1.add(inputMu);

        //sigma//
        JPanel filaPar2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        westPanel.add(filaPar2);

        //Sigma - Label
        labelSigma = new JLabel("σ = ");
        Font fontSigma = new Font(labelSigma.getFont().getName(), Font.BOLD, labelSigma.getFont().getSize());
        labelSigma.setFont(fontSigma);
        filaPar2.add(labelSigma);

        //Sigma - Campo texto
        inputSigma = new JTextField(3);
        filaPar2.add(inputSigma);

        ///Distribución exponencial///
        //Lambda - Label
        labelLambda = new JLabel("λ = ");
        Font fontLambda = new Font(labelLambda.getFont().getName(), Font.BOLD, labelLambda.getFont().getSize());
        labelLambda.setFont(fontLambda);
        filaPar2.add(labelLambda);
        labelLambda.setVisible(false);

        //Lambda- Campo texto
        inputLambda = new JTextField(3);
        filaPar2.add(inputLambda);
        inputLambda.setVisible(false);

        ///Distribución binomial///
        //n - Label
        labeln = new JLabel("n = ");
        Font fontn = new Font(labeln.getFont().getName(), Font.BOLD, labeln.getFont().getSize());
        labeln.setFont(fontn);
        filaPar2.add(labeln);
        labeln.setVisible(false);

        //n - Campo texto
        inputn = new JTextField(3);
        filaPar2.add(inputn);
        inputn.setVisible(false);

        //p - Label
        labelp = new JLabel("p = ");
        Font fontp = new Font(labelp.getFont().getName(), Font.BOLD, labelp.getFont().getSize());
        labelp.setFont(fontp);
        filaPar2.add(labelp);
        labelp.setVisible(false);

        //p- Campo texto
        inputp = new JTextField(3);
        filaPar2.add(inputp);
        inputp.setVisible(false);

        //X y probabilidades
        westPanel.add(Box.createVerticalGlue()); //Espacio vertical arriba x

        //X
        JPanel filaX = new JPanel(new FlowLayout(FlowLayout.LEFT));
        westPanel.add(filaX);

        JLabel labelX = new JLabel("x = ");
        Font fontX = new Font(labelX.getFont().getName(), Font.BOLD, labelX.getFont().getSize());
        labelX.setFont(fontX);
        filaX.add(labelX);

        Graficos.inputX = new JTextField(5);
        filaX.add(Graficos.inputX);

        westPanel.add(Box.createVerticalGlue()); //Espacio vertical abajo x

        ///Discretas - Probabilidades///
        JPanel filaProb1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel filaProb2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        westPanel.add(filaProb1);
        westPanel.add(filaProb2);
        Graficos.labelProb1 = new JLabel("");
        Graficos.labelProb2 = new JLabel("");
        Font fontProb1 = new Font(Graficos.labelProb1.getFont().getName(), Font.PLAIN, Graficos.labelProb1.getFont().getSize());
        Font fontProb2 = new Font(Graficos.labelProb2.getFont().getName(), Font.PLAIN, Graficos.labelProb2.getFont().getSize());
        Graficos.labelProb1.setFont(fontProb1);
        Graficos.labelProb2.setFont(fontProb2);
        filaProb1.add(Graficos.labelProb1);
        filaProb2.add(Graficos.labelProb2);
        Graficos.labelProb1.setVisible(false);
        Graficos.labelProb2.setVisible(false);

        //Probabilidad
        westPanel.add(Box.createVerticalGlue()); //Espacio vertical arriba p

        JPanel filaProb = new JPanel(new FlowLayout(FlowLayout.LEFT));
        westPanel.add(filaProb);

        comboBoxProb = new JComboBox<>(optionsProbSim);
        filaProb.add(comboBoxProb);

        Graficos.inputProb = new JTextField(5);
        filaProb.add(Graficos.inputProb);

        westPanel.add(Box.createVerticalGlue()); //Espacio vertical abajo p

        //Mensaje
        Graficos.labelError = new JLabel("");
        Graficos.textoError = new JLabel("");
        westPanel.add(Graficos.labelError);
        westPanel.add(Graficos.textoError);

        westPanel.add(Box.createVerticalGlue()); //Espacio inf

        /////ESTE////
        Graficos.graficoDist = new ChartPanel(null);
        add(Graficos.graficoDist, BorderLayout.CENTER);

        //////////////////////////
        /////////Listeners//////////
        //////////////////////////
        //Boton calcular
        calcularButton.addActionListener(this);
        borrarButton.addActionListener(this);
        formulasButton.addActionListener(this);
        momentosButton.addActionListener(this);

        Graficos.inputX.addKeyListener(keyListener);
        Graficos.inputProb.addKeyListener(keyListener);

        comboBoxProb.addActionListener(this);
        comboBoxDist.addActionListener(this);
    }

    /////////////////
    ////EVENTOS/////
    ////////////////
    //X y Probabilidad
    KeyListener keyListener = new KeyListener() {
        @Override
        public void keyTyped(KeyEvent e) {
            // Not used in this example
        }

        @Override
        public void keyPressed(KeyEvent e) {
            // Not used in this example
        }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getSource() == Graficos.inputX) {
                Graficos.inputProb.setText("");
            } else if (e.getSource() == Graficos.inputProb) {
                Graficos.inputX.setText("");
            }
        }
    };
    //Botones inferior izquierda

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calcularButton) {
            Graficos.labelProb1.setText("");
            Graficos.labelProb2.setText("");
            Graficos.labelProb1.setVisible(false);
            Graficos.labelProb2.setVisible(false);
            switch (distribucion) {
                case "Normal": {
                    // Distribución normal
                    String muString = inputMu.getText();
                    String sigmaString = inputSigma.getText();
                    String xString = Graficos.inputX.getText();
                    String probString = Graficos.inputProb.getText();

                    if (muString.isEmpty() || sigmaString.isEmpty()) { //Evaluar casilleros vacios
                        Graficos.labelError.setText("ERROR:");
                        Graficos.textoError.setText("Complete todos los parámetros");

                    } else {
                        try { //Convertir a numérico
                            Graficos.labelError.setText("");
                            Graficos.textoError.setText("");
                            double valorMu = Double.parseDouble(muString);
                            double valorSigma = Double.parseDouble(sigmaString);

                            if (valorSigma <= 0) {
                                Graficos.labelError.setText("ERROR:");
                                Graficos.textoError.setText("σ debe ser mayor a 0");
                            } else {
                                if (!xString.isEmpty()) {
                                    double valorX = Double.parseDouble(xString);
                                    Graficos.PlotNormalX(valorMu, valorSigma, valorX, direccion);
                                } else if (!probString.isEmpty()) {
                                    double valorProb = Double.parseDouble(probString);
                                    if (valorProb <= 0 || valorProb >= 1) {
                                        Graficos.labelError.setText("ERROR:");
                                        Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                                    } else {
                                        Graficos.PlotNormalP(valorMu, valorSigma, valorProb, direccion);
                                    }
                                } else {
                                    Graficos.PlotNormal(valorMu, valorSigma);
                                }
                            }

                        } catch (Exception valInc) {//Evaluar casilleros no numéricos
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("Los valores deben ser numéricos");
                        }
                    }
                    break;
                }
                case "Exponencial": {
                    // Distribución exponencial
                    String lambdaString = inputLambda.getText();
                    String xString = Graficos.inputX.getText();
                    String probString = Graficos.inputProb.getText();

                    if (lambdaString.isEmpty()) { //Evaluar casilleros vacios
                        Graficos.labelError.setText("ERROR:");
                        Graficos.textoError.setText("Complete todos los parámetros");
                    } else {
                        try { //Convertir a numérico
                            Graficos.labelError.setText("");
                            Graficos.textoError.setText("");
                            double valorLambda = Double.parseDouble(lambdaString);

                            if (valorLambda <= 0) {
                                Graficos.labelError.setText("ERROR:");
                                Graficos.textoError.setText("λ debe ser mayor a 0");
                            } else {
                                if (!xString.isEmpty()) {
                                    double valorX = Double.parseDouble(xString);
                                    Graficos.PlotExpoX(valorLambda, valorX, direccion);
                                } else if (!probString.isEmpty()) {
                                    double valorProb = Double.parseDouble(probString);
                                    if (valorProb <= 0 || valorProb >= 1) {
                                        Graficos.labelError.setText("ERROR:");
                                        Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                                    } else {
                                        Graficos.PlotExpoP(valorLambda, valorProb, direccion);
                                    }
                                } else {
                                    Graficos.PlotExpo(valorLambda);
                                }
                            }

                        } catch (Exception valInc) {//Evaluar casilleros no numéricos
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("Los valores deben ser numéricos");
                        }
                    }
                    break;
                }
                case "Binomial": {
                    // Distribución binomial
                    String nString = inputn.getText();
                    String pString = inputp.getText();
                    String xString = Graficos.inputX.getText();
                    String probString = Graficos.inputProb.getText();

                    if (nString.isEmpty() || pString.isEmpty()) { //Evaluar casilleros vacios
                        Graficos.labelError.setText("ERROR:");
                        Graficos.textoError.setText("Complete todos los parámetros");
                    } else {
                        try { //Convertir a numérico
                            Graficos.labelError.setText("");
                            Graficos.textoError.setText("");
                            double valornDouble = Double.parseDouble(nString);
                            int valorn = (int) valornDouble;
                            double valorp = Double.parseDouble(pString);

                            if (valorn <= 0) {
                                Graficos.labelError.setText("ERROR:");
                                Graficos.textoError.setText("n debe ser mayor a 0");
                            } else if (valorp <= 0 || valorp >= 1) {
                                Graficos.labelError.setText("ERROR:");
                                Graficos.textoError.setText("p debe estar entre 0 y 1");
                            } else {
                                if (!xString.isEmpty()) {
                                    try {
                                        int valorX = Integer.parseInt(xString);
                                        if (valorX < 0 || valorX > valorn) {
                                            Graficos.labelError.setText("ERROR:");
                                            Graficos.textoError.setText("x debe estar entre 0 y n");
                                        } else {
                                            Graficos.PlotBinomX(valorn, valorp, valorX, direccion);
                                        }
                                    } catch (Exception xInc) {
                                        Graficos.labelError.setText("ERROR:");
                                        Graficos.textoError.setText("x debe ser un entero");
                                    }

                                } else if (!probString.isEmpty()) {
                                    double valorProb = Double.parseDouble(probString);
                                    if (valorProb <= 0 || valorProb >= 1) {
                                        Graficos.labelError.setText("ERROR:");
                                        Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                                    } else {
                                        Graficos.PlotBinomP(valorn, valorp, valorProb, direccion);
                                    }
                                } else {
                                    Graficos.PlotBinom(valorn, valorp);
                                }
                            }

                        } catch (Exception valInc) {//Evaluar casilleros no numéricos
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("Los valores deben ser numéricos");
                        }
                    }
                    break;
                }
            }

        } else if (e.getSource() == borrarButton) { //Botón borrar
            Graficos.labelError.setText("");
            Graficos.textoError.setText("");
            inputMu.setText("");
            inputSigma.setText("");
            inputLambda.setText("");
            inputn.setText("");
            inputp.setText("");
            Graficos.inputX.setText("");
            Graficos.inputProb.setText("");
            Graficos.labelProb1.setText("");
            Graficos.labelProb2.setText("");
            Graficos.labelProb1.setVisible(false);
            Graficos.labelProb2.setVisible(false);

        } else if (e.getSource() == comboBoxProb) { //Opciones dirección
            try {
                String selectedOption = (String) comboBoxProb.getSelectedItem();
                Graficos.inputProb.setText("");
                //PARA JAVA >14
//                direccion = switch (selectedOption) {
//                    case "P(X>x)=" ->
//                        "mayor";
//                    case "P(X>=x)=" ->
//                        "mayor";
//                    case "P(X<x)=" ->
//                        "menor";
//                    case "P(X<=x)=" ->
//                        "menor";
//                    case "2P(X>|x|)=" ->
//                        "doble";
//                    case "P(X=x)=" ->
//                        "igual";
//                    default ->
//                        "mayor";
//                };

//PARA JAVA <14
                switch (selectedOption) {
                    case "P(X>x)=": {
                        direccion = "mayor";
                        break;
                    }
                    case "P(X>=x)=": {
                        direccion = "mayor";
                        break;
                    }
                    case "P(X<x)=": {
                        direccion = "menor";
                        break;
                    }
                    case "P(X<=x)=": {
                        direccion = "menor";
                        break;
                    }
                    case "2P(X>|x|)=": {
                        direccion = "doble";
                        break;
                    }
                    case "P(X=x)=": {
                        direccion = "igual";
                        break;
                    }
                    default: {
                        direccion = "mayor";
                        break;
                    }
                };

            } catch (Exception c) {
                //Evita error
            }

        } else if (e.getSource() == comboBoxDist) { //Opciones dirección
            String selectedOption = (String) comboBoxDist.getSelectedItem();
            Graficos.inputProb.setText("");
            Graficos.inputX.setText("");
            Graficos.graficoDist.setVisible(false);
            Graficos.labelProb1.setText("");
            Graficos.labelProb2.setText("");
            Graficos.labelProb1.setVisible(false);
            Graficos.labelProb2.setVisible(false);
            Graficos.esperanzaActual = null;
            Graficos.varianciaActual = null;

            distribucion = selectedOption;

            //Actualizar GUI
            if ("Normal".equals(distribucion)) {

                comboBoxProb.removeAllItems();
                for (String item : optionsProbSim) {
                    comboBoxProb.addItem(item);
                }

                labelSigma.setVisible(true);
                labelMu.setVisible(true);
                inputMu.setVisible(true);
                inputSigma.setVisible(true);

            } else {
                labelSigma.setVisible(false);
                labelMu.setVisible(false);
                inputMu.setVisible(false);
                inputSigma.setVisible(false);
                inputMu.setText("");
                inputSigma.setText("");
            }
            if ("Exponencial".equals(distribucion)) {

                comboBoxProb.removeAllItems();
                for (String item : optionsProbCont) {
                    comboBoxProb.addItem(item);
                }

                labelLambda.setVisible(true);
                inputLambda.setVisible(true);

            } else {
                labelLambda.setVisible(false);
                inputLambda.setVisible(false);
                inputLambda.setText("");
            }

            if ("Binomial".equals(distribucion)) {

                comboBoxProb.removeAllItems();
                for (String item : optionsProbDisc) {
                    comboBoxProb.addItem(item);
                }

                labeln.setVisible(true);
                inputn.setVisible(true);
                labelp.setVisible(true);
                inputp.setVisible(true);

            } else {
                labeln.setVisible(false);
                inputn.setVisible(false);
                inputn.setText("");
                labelp.setVisible(false);
                inputp.setVisible(false);
                inputp.setText("");
            }
        } else if (e.getSource() == formulasButton) { //Botón formulas

            Formulas.generarFormula(distribucion);

            BufferedImage image;
            try {
                File ruta = new File("imgformula.png"); //Crea archivo
                image = ImageIO.read(ruta); //Convierte a imagen
                ruta.delete(); //Eliminar el archivo
                JLabel imagenFormula = new JLabel(new ImageIcon(image));

                JOptionPane.showMessageDialog(null, imagenFormula,
                        "Fórmulas", JOptionPane.PLAIN_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error",
                        "Fórmulas", JOptionPane.PLAIN_MESSAGE);
            }

        } else if (e.getSource() == momentosButton) { //Botón formulas

//                JLabel textoMomentos  = new JLabel("E(X)=\n" + 2 + "\n V(X)=" + 3);
            JLabel[] textoMomentos = {new JLabel("E(X) = " + Graficos.esperanzaActual), new JLabel("V(X) = " + Graficos.varianciaActual)};

            JOptionPane.showMessageDialog(null, textoMomentos,
                    "Momentos", JOptionPane.PLAIN_MESSAGE);

        }
    }

    public static void main(String[] args) {
        DistribucionesPrograma distribuciones = new DistribucionesPrograma();
        distribuciones.setVisible(true);
    }
}
