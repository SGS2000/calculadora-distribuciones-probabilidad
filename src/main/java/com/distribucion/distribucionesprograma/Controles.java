package com.distribucion.distribucionesprograma;

/**
 *
 * @author Santiago Garcia Sanchez
 */
public class Controles {

    static void ControlNormal(String direccion) {
        String muString = Parametros.inputMu.getText();
        String sigmaString = Parametros.inputSigma.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (muString.isEmpty() || sigmaString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Complete ambos parámetros");

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
    }

    static void ControlChi(String direccion) {
        String KString = Parametros.inputK.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (KString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Ingrese un valor para k");
        } else {
            try {
                int valorK = Integer.parseInt(KString);
                try { //Convertir a numérico
                    Graficos.labelError.setText("");
                    Graficos.textoError.setText("");
                    if (valorK <= 0) {
                        Graficos.labelError.setText("ERROR:");
                        Graficos.textoError.setText("k debe ser un entero mayor a 0");
                    } else {
                        if (!xString.isEmpty()) {
                            double valorX = Double.parseDouble(xString);
                            Graficos.PlotChiX(valorK, valorX, direccion);
                        } else if (!probString.isEmpty()) {
                            double valorProb = Double.parseDouble(probString);
                            if (valorProb <= 0 || valorProb >= 1) {
                                Graficos.labelError.setText("ERROR:");
                                Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                            } else {
                                Graficos.PlotChiP(valorK, valorProb, direccion);
                            }
                        } else {
                            Graficos.PlotChi(valorK);
                        }

                    }

                } catch (Exception valInc) {//Evaluar casilleros no numéricos
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("Los valores deben ser numéricos");
                }
            } catch (Exception valInc) {
                Graficos.labelError.setText("ERROR:");
                Graficos.textoError.setText("k debe ser un entero mayor a 0");
            }
        }
    }

    static void ControlExpo(String direccion) {
        String lambdaString = Parametros.inputLambda.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (lambdaString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Ingrese un valor para λ");
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
                        if (valorX <= 0) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("x debe ser mayor a 0");
                        } else {
                            Graficos.PlotExpoX(valorLambda, valorX, direccion);
                        }
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
    }

    static void ControlF(String direccion) {
        String dfString1 = Parametros.inputDF1.getText();
        String dfString2 = Parametros.inputDF2.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (dfString1.isEmpty() || dfString2.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Complete todos los parámetros");

        } else {
            try { //Convertir a numérico
                Graficos.labelError.setText("");
                Graficos.textoError.setText("");
                double valorDF1 = Double.parseDouble(dfString1);
                double valorDF2 = Double.parseDouble(dfString2);

                if (valorDF1 <= 0 || valorDF2 <= 0) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("Los grados de libertad deben ser mayores a 0");
                } else {
                    if (!xString.isEmpty()) {
                        double valorX = Double.parseDouble(xString);

                        if (valorX <= 0) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("x debe ser mayor a 0");
                        } else {
                            Graficos.PlotFX(valorDF1, valorDF2, valorX, direccion);
                        }

                    } else if (!probString.isEmpty()) {
                        double valorProb = Double.parseDouble(probString);
                        if (valorProb <= 0 || valorProb >= 1) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                        } else {
                            Graficos.PlotFP(valorDF1, valorDF2, valorProb, direccion);
                        }
                    } else {
                        Graficos.PlotF(valorDF1, valorDF2);
                    }
                }

            } catch (Exception valInc) {//Evaluar casilleros no numéricos
                Graficos.labelError.setText("ERROR:");
                Graficos.textoError.setText("Los valores deben ser numéricos");
            }
        }
    }

    static void ControlStu(String direccion) {
        String VString = Parametros.inputV.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (VString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Ingrese un valor para v");
        } else {
            try { //Convertir a numérico
                Graficos.labelError.setText("");
                Graficos.textoError.setText("");
                double valorV = Double.parseDouble(VString);

                if (valorV <= 0) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("v debe ser mayor a 0");
                } else {
                    if (!xString.isEmpty()) {
                        double valorX = Double.parseDouble(xString);
                        Graficos.PlotStuX(valorV, valorX, direccion);
                    } else if (!probString.isEmpty()) {
                        double valorProb = Double.parseDouble(probString);
                        if (valorProb <= 0 || valorProb >= 1) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                        } else {
                            Graficos.PlotStuP(valorV, valorProb, direccion);
                        }
                    } else {
                        Graficos.PlotStu(valorV);
                    }
                }

            } catch (Exception valInc) {//Evaluar casilleros no numéricos
                Graficos.labelError.setText("ERROR:");
                Graficos.textoError.setText("Los valores deben ser numéricos");
            }
        }
    }

    static void ControlBinom(String direccion) {
        String nString = Parametros.inputn.getText();
        String pString = Parametros.inputp.getText();
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
    }

    static void ControlPois(String direccion) {
        String lambdaString = Parametros.inputLambda.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (lambdaString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Ingrese un valor para λ");
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
                        try {
                            int valorX = Integer.parseInt(xString);
                            if (valorX < 0) {
                                Graficos.labelError.setText("ERROR:");
                                Graficos.textoError.setText("x debe ser un entero no negativo");
                            } else {
                                Graficos.PlotPoisX(valorLambda, valorX, direccion);
                            }
                        } catch (Exception xInc) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("x debe ser un entero no negativo");
                        }
                    } else if (!probString.isEmpty()) {
                        double valorProb = Double.parseDouble(probString);
                        if (valorProb <= 0 || valorProb >= 1) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                        } else {
                            Graficos.PlotPoisP(valorLambda, valorProb, direccion);
                        }
                    } else {
                        Graficos.PlotPois(valorLambda);
                    }
                }

            } catch (Exception valInc) {//Evaluar casilleros no numéricos
                Graficos.labelError.setText("ERROR:");
                Graficos.textoError.setText("Los valores deben ser numéricos");
            }
        }
    }
}
