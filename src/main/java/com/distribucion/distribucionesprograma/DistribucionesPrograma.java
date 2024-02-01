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
    JButton calcularButton, borrarButton, formulasButton, momentosButton;
    JComboBox<String> comboBoxProb, comboBoxDist;

    String direccion = "mayor"; //Dirección de la probabilidad

    String[] optionsProbSim = {"P(X>x)=", "P(X<x)=", "2P(X>|x|)="}; //Opciones direccion
    String[] optionsProbDisc = {"P(X=x)=", "P(X≤x)=", "P(X≥x)="};
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
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        /////////////////////////
        ////////LAYOUT///////////
        /////////////////////////
        setLayout(new BorderLayout(5, 10));

        // Añadiendo componentes al JFrame usando el BorderLayout
        /////NORTE//////
        // Creando un JPanel con un BorderLayout
        JPanel northPanel = new JPanel(new BorderLayout());
        add(northPanel, BorderLayout.NORTH);
        northPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        // Label
        JLabel northLabel = new JLabel("Seleccione una distribución:");
        Font northLabelFont = new Font(northLabel.getFont().getName(), Font.BOLD, northLabel.getFont().getSize());
        northPanel.add(northLabel, BorderLayout.WEST);
        northLabel.setFont(northLabelFont);

        // JComboBox con opciones
        String[] optionsDist = {"Normal", "Beta", "Chi-cuadrado", "Exponencial", "F", "Gama", "t de Student",
            "Uniforme continua", "Bernoulli", "Binomial", "Binomial negativa", "Geométrica", "Hipergeométrica",
            "Uniforme discreta", "Poisson"};
        comboBoxDist = new JComboBox<>(optionsDist);
        northPanel.add(comboBoxDist, BorderLayout.EAST);

        /////SUR////
        // Creando un JPanel con un BorderLayout
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

        //Parámetros//
        Parametros.CrearParam(westPanel);

        //X y probabilidades
        westPanel.add(Box.createVerticalGlue()); //Espacio vertical arriba x

        //X
        JPanel filaX = new JPanel(new FlowLayout(FlowLayout.LEFT));
        westPanel.add(filaX);

        JLabel labelX = new JLabel("x = ");
        Font fontX = new Font(labelX.getFont().getName(), Font.BOLD, labelX.getFont().getSize());
        labelX.setFont(fontX);
        filaX.add(labelX);

        Graficos.inputX = new JTextField(6);
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
                    Controles.ControlNormal(direccion);
                    break;
                }
                case "Beta": {
                    Controles.ControlBeta(direccion);
                    break;
                }
                case "Chi-cuadrado": {
                    Controles.ControlChi(direccion);
                    break;
                }
                case "Exponencial": {
                    Controles.ControlExpo(direccion);
                    break;
                }
                case "F": {
                    Controles.ControlF(direccion);
                    break;
                }
                case "Gama": {
                    Controles.ControlGama(direccion);
                    break;
                }
                case "t de Student": {
                    Controles.ControlStu(direccion);
                    break;
                }
                case "Uniforme continua": {
                    Controles.ControlUnifC(direccion);
                    break;
                }
                case "Bernoulli": {
                    Controles.ControlBernoulli(direccion);
                    break;
                }
                case "Binomial": {
                    Controles.ControlBinom(direccion);
                    break;
                }
                case "Binomial negativa": {
                    Controles.ControlBinNeg(direccion);
                    break;
                }
                case "Geométrica": {
                    Controles.ControlGeom(direccion);
                    break;
                }
                case "Hipergeométrica": {
                    Controles.ControlHiper(direccion);
                    break;
                }
                case "Uniforme discreta": {
                    Controles.ControlUnifD(direccion);
                    break;
                }
                case "Poisson": {
                    Controles.ControlPois(direccion);
                    break;
                }
            }

        } else if (e.getSource() == borrarButton) { //Botón borrar
            Graficos.labelError.setText("");
            Graficos.textoError.setText("");
            Parametros.inputMu.setText("");
            Parametros.inputSigma.setText("");
            Parametros.inputLambda.setText("");
            Parametros.inputn.setText("");
            Parametros.inputp.setText("");
            Parametros.inputV.setText("");
            Parametros.inputDF1.setText("");
            Parametros.inputDF2.setText("");
            Parametros.inputA.setText("");
            Parametros.inputB.setText("");
            Parametros.inputN.setText("");
            Parametros.inputN1.setText("");
            Parametros.inputAlfa.setText("");
            Parametros.inputBeta.setText("");
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

                switch (selectedOption) {
                    case "P(X>x)=": {
                        direccion = "mayor";
                        break;
                    }
                    case "P(X≥x)=": {
                        direccion = "mayor";
                        break;
                    }
                    case "P(X<x)=": {
                        direccion = "menor";
                        break;
                    }
                    case "P(X≤x)=": {
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
                }

            } catch (Exception c) {
                //Evita error
            }

        } else if (e.getSource() == comboBoxDist) { //Opciones dirección

            //Determinar distribución actual
            String selectedOption = (String) comboBoxDist.getSelectedItem();
            distribucion = selectedOption;

            //Resetear opciones
            Graficos.labelError.setText("");
            Graficos.textoError.setText("");
            Graficos.inputProb.setText("");
            Graficos.inputX.setText("");
            Graficos.graficoDist.setVisible(false);
            Graficos.labelProb1.setText("");
            Graficos.labelProb2.setText("");
            Graficos.labelProb1.setVisible(false);
            Graficos.labelProb2.setVisible(false);
            Graficos.labelProb1.setVisible(false);
            Graficos.labelProb2.setVisible(false);
            Graficos.esperanzaActual = null;
            Graficos.varianciaActual = null;

            //Actualizar GUI
            Parametros.ActualizarParam(distribucion, comboBoxProb, optionsProbSim, optionsProbDisc, optionsProbCont);

        } else if (e.getSource() == formulasButton) { //Botón formulas

            Formulas.generarFormula(distribucion);

            BufferedImage image;
            try {
                File ruta = new File("imgformula.png"); //Crea archivo
                image = ImageIO.read(ruta); //Convierte a imagen
                ruta.delete(); //Eliminar el archivo

                JLabel imagenFormula = new JLabel(new ImageIcon(image));
                UIManager.put("OptionPane.minimumSize", new Dimension(image.getWidth(), image.getHeight())); //Cambiar tamaño caja
                JOptionPane.showMessageDialog(null, imagenFormula,
                        "Fórmulas", JOptionPane.PLAIN_MESSAGE);

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Ha ocurrido un error",
                        "Fórmulas", JOptionPane.PLAIN_MESSAGE);
            }

        } else if (e.getSource() == momentosButton) { //Botón formulas
            UIManager.put("OptionPane.minimumSize", new Dimension(30, 30)); //Cambiar tamaño caja

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
