package com.distribucion.distribucionesprograma;

/**
 *
 * @author Santiago Garcia Sanchez
 */
public class Controles {

    //Continuas
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

    static void ControlBeta(String direccion) {
        String alfaString = Parametros.inputAlfa.getText();
        String betaString = Parametros.inputBeta.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (alfaString.isEmpty() || betaString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Complete todos los parámetros");
        } else {
            double valorAlfa = Double.parseDouble(alfaString);
            double valorBeta = Double.parseDouble(betaString);
            try {//Convertir a numérico
                Graficos.labelError.setText("");
                Graficos.textoError.setText("");

                if (valorBeta <= 0 || valorAlfa <= 0) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("α y β deben ser mayores a 0");
                } else {
                    if (!xString.isEmpty()) {
                        double valorX = Double.parseDouble(xString);
                        if (valorX <= 0 || valorX >= 1) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("x debe estar entre 0 y 1");
                        } else {
                            Graficos.PlotBetaX(valorAlfa, valorBeta, valorX, direccion);
                        }

                    } else if (!probString.isEmpty()) {
                        double valorProb = Double.parseDouble(probString);
                        if (valorProb <= 0 || valorProb >= 1) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                        } else {
                            Graficos.PlotBetaP(valorAlfa, valorBeta, valorProb, direccion);
                        }
                    } else {
                        Graficos.PlotBeta(valorAlfa, valorBeta);
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
            Graficos.textoError.setText("Ingrese un valor para ν");
        } else {
            try { //Convertir a numérico
                Graficos.labelError.setText("");
                Graficos.textoError.setText("");
                double valorV = Double.parseDouble(VString);

                if (valorV <= 0) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("ν debe ser mayor a 0");
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

    static void ControlGama(String direccion) {
        String alfaString = Parametros.inputAlfa.getText();
        String betaString = Parametros.inputBeta.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (alfaString.isEmpty() || betaString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Complete todos los parámetros");
        } else {
            double valorAlfa = Double.parseDouble(alfaString);
            double valorBeta = Double.parseDouble(betaString);
            try {//Convertir a numérico
                Graficos.labelError.setText("");
                Graficos.textoError.setText("");

                if (valorBeta <= 0 || valorAlfa <= 0) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("α y β deben ser mayores a 0");
                } else {
                    if (!xString.isEmpty()) {
                        double valorX = Double.parseDouble(xString);
                        if (valorX <= 0) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("x debe ser mayor a 0");
                        } else {
                            Graficos.PlotGamaX(valorAlfa, valorBeta, valorX, direccion);
                        }

                    } else if (!probString.isEmpty()) {
                        double valorProb = Double.parseDouble(probString);
                        if (valorProb <= 0 || valorProb >= 1) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                        } else {
                            Graficos.PlotGamaP(valorAlfa, valorBeta, valorProb, direccion);
                        }
                    } else {
                        Graficos.PlotGama(valorAlfa, valorBeta);
                    }
                }

            } catch (Exception valInc) {//Evaluar casilleros no numéricos
                Graficos.labelError.setText("ERROR:");
                Graficos.textoError.setText("Los valores deben ser numéricos");
            }
        }
    }

    static void ControlUnifC(String direccion) {
        String aString = Parametros.inputA.getText();
        String bString = Parametros.inputB.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (aString.isEmpty() || bString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Complete todos los parámetros");
        } else {
            double valorA = Double.parseDouble(aString);
            double valorB = Double.parseDouble(bString);
            try {//Convertir a numérico
                Graficos.labelError.setText("");
                Graficos.textoError.setText("");

                if (valorB <= valorA) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("a debe ser menor a b");
                } else {
                    if (!xString.isEmpty()) {
                        double valorX = Double.parseDouble(xString);
                        if (valorX < valorA || valorX > valorB) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("x debe estar entre a y b");
                        } else {
                            Graficos.PlotUnifCX(valorA, valorB, valorX, direccion);
                        }

                    } else if (!probString.isEmpty()) {
                        double valorProb = Double.parseDouble(probString);
                        if (valorProb <= 0 || valorProb >= 1) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                        } else {
                            Graficos.PlotUnifCP(valorA, valorB, valorProb, direccion);
                        }
                    } else {
                        Graficos.PlotUnifC(valorA, valorB);
                    }
                }

            } catch (Exception valInc) {//Evaluar casilleros no numéricos
                Graficos.labelError.setText("ERROR:");
                Graficos.textoError.setText("Los valores deben ser numéricos");
            }
        }

    }

    //Discretas
    static void ControlBernoulli(String direccion) {
        String pString = Parametros.inputp.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (pString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Complete todos los parámetros");
        } else {
            try { //Convertir a numérico
                Graficos.labelError.setText("");
                Graficos.textoError.setText("");
                double valorp = Double.parseDouble(pString);

                if (valorp <= 0 || valorp >= 1) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("p debe estar entre 0 y 1");
                } else {
                    if (!xString.isEmpty()) {
                        try {
                            int valorX = Integer.parseInt(xString);
                            if (valorX != 0 && valorX != 1) {
                                Graficos.labelError.setText("ERROR:");
                                Graficos.textoError.setText("x debe ser igual a 0 o a 1");
                            } else {
                                Graficos.PlotBinomX(1, valorp, valorX, direccion);
                            }
                        } catch (Exception xInc) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("x debe ser igual a 0 o a 1");
                        }

                    } else if (!probString.isEmpty()) {
                        double valorProb = Double.parseDouble(probString);
                        if (valorProb <= 0 || valorProb >= 1) {
                            Graficos.labelError.setText("ERROR:");
                            Graficos.textoError.setText("La probabilidad debe estar entre 0 y 1");
                        } else {
                            Graficos.PlotBinomP(1, valorp, valorProb, direccion);
                        }
                    } else {
                        Graficos.PlotBinom(1, valorp);
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

    static void ControlBinNeg(String direccion)  {
        String rString = Parametros.inputr.getText();
        String pString = Parametros.inputp.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (rString.isEmpty() || pString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Complete todos los parámetros");
        } else {
            try { //Convertir a numérico
                Graficos.labelError.setText("");
                Graficos.textoError.setText("");
                double valorrDouble = Double.parseDouble(rString);
                int valorr = (int) valorrDouble;
                double valorp = Double.parseDouble(pString);

                if (valorr <= 0) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("r debe ser mayor a 0");
                } else if (valorp <= 0 || valorp >= 1) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("p debe estar entre 0 y 1");
                } else {
                    if (!xString.isEmpty()) {
                        try {
                            int valorX = Integer.parseInt(xString);
                            if (valorX < valorr ) {
                                Graficos.labelError.setText("ERROR:");
                                Graficos.textoError.setText("x debe ser mayor o igual a r");
                            } else {
                                Graficos.PlotBinNegX(valorr, valorp, valorX, direccion);
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
                            Graficos.PlotBinNegP(valorr, valorp, valorProb, direccion);
                        }
                    } else {
                        Graficos.PlotBinNeg(valorr, valorp);
                    }
                }

            } catch (Exception valInc) {//Evaluar casilleros no numéricos
                Graficos.labelError.setText("ERROR:");
                Graficos.textoError.setText("Los valores deben ser numéricos");
            }
        }
    }
    
    static void ControlGeom(String direccion)  {
        String pString = Parametros.inputp.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (pString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Complete todos los parámetros");
        } else {
            try { //Convertir a numérico
                Graficos.labelError.setText("");
                Graficos.textoError.setText("");
                double valorp = Double.parseDouble(pString);

                if (valorp <= 0 || valorp >= 1) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("p debe estar entre 0 y 1");
                } else {
                    if (!xString.isEmpty()) {
                        try {
                            int valorX = Integer.parseInt(xString);
                            if (valorX < 1 ) {
                                Graficos.labelError.setText("ERROR:");
                                Graficos.textoError.setText("x debe ser mayor o igual a 1");
                            } else {
                                Graficos.PlotBinNegX(1, valorp, valorX, direccion);
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
                            Graficos.PlotBinNegP(1, valorp, valorProb, direccion);
                        }
                    } else {
                        Graficos.PlotBinNeg(1, valorp);
                    }
                }

            } catch (Exception valInc) {//Evaluar casilleros no numéricos
                Graficos.labelError.setText("ERROR:");
                Graficos.textoError.setText("Los valores deben ser numéricos");
            }
        }
    }
    
    static void ControlHiper(String direccion) {
        String nString = Parametros.inputn.getText();
        String NString = Parametros.inputN.getText();
        String N1String = Parametros.inputN1.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (nString.isEmpty() || NString.isEmpty() || N1String.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Complete todos los parámetros");
        } else {
            try { //Convertir a numérico
                Graficos.labelError.setText("");
                Graficos.textoError.setText("");
                double valornDouble = Double.parseDouble(nString);
                int valorn = (int) valornDouble;
                double valorNDouble = Double.parseDouble(NString);
                int valorN = (int) valorNDouble;
                double valorN1Double = Double.parseDouble(N1String);
                int valorN1 = (int) valorN1Double;

                if (valorn <= 0) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("n debe ser mayor a 0");
                } else if (valorn > valorN) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("n debe ser menor o igual a N");
                } else if (valorN1 > valorN) {
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("N1 debe ser menor o igual a N");
                } else {
                    if (!xString.isEmpty()) {
                        try {
                            int valorX = Integer.parseInt(xString);
                            int minimo = Math.max(0, valorn + valorN1 - valorN);
                            int maximo = Math.min(valorn, valorN1);
                            if (valorX < minimo || valorX > maximo) {
                                Graficos.labelError.setText("ERROR:");
                                Graficos.textoError.setText("x debe estar entre " + minimo + " y " + maximo);
//                                if(valorN1 > valorn){
//                                    Graficos.textoError.setText("x debe estar entre 0 y n");
//                                }
//                                else if(valorN1 < valorn) {
//                                    Graficos.textoError.setText("x debe estar entre 0 y N1");
//                                }
                            } else {
                                Graficos.PlotHiperX(valorn, valorN, valorN1, valorX, direccion);
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
                            Graficos.PlotHiperP(valorn, valorN, valorN1, valorProb, direccion);
                        }
                    } else {
                        Graficos.PlotHiper(valorn, valorN, valorN1);
                    }
                }

            } catch (Exception valInc) {//Evaluar casilleros no numéricos
                Graficos.labelError.setText("ERROR:");
                Graficos.textoError.setText("Los valores deben ser numéricos");
            }
        }
    }

    static void ControlUnifD(String direccion) {
        String aString = Parametros.inputA.getText();
        String bString = Parametros.inputB.getText();
        String xString = Graficos.inputX.getText();
        String probString = Graficos.inputProb.getText();

        if (aString.isEmpty() || bString.isEmpty()) { //Evaluar casilleros vacios
            Graficos.labelError.setText("ERROR:");
            Graficos.textoError.setText("Complete todos los parámetros");
        } else {
            try {
                int valorA = Integer.parseInt(aString);
                int valorB = Integer.parseInt(bString);
                try {//Convertir a numérico
                    Graficos.labelError.setText("");
                    Graficos.textoError.setText("");

                    if (valorB <= valorA) {
                        Graficos.labelError.setText("ERROR:");
                        Graficos.textoError.setText("a debe ser menor a b");
                    } else {
                        if (!xString.isEmpty()) {
                            try {
                                int valorX = Integer.parseInt(xString);
                                if (valorX < valorA || valorX > valorB) {
                                    Graficos.labelError.setText("ERROR:");
                                    Graficos.textoError.setText("x debe estar entre a y b");
                                } else {
                                    Graficos.PlotUnifDX(valorA, valorB, valorX, direccion);
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
                                Graficos.PlotUnifDP(valorA, valorB, valorProb, direccion);
                            }
                        } else {
                            Graficos.PlotUnifD(valorA, valorB);
                        }
                    }

                } catch (Exception valInc) {//Evaluar casilleros no numéricos
                    Graficos.labelError.setText("ERROR:");
                    Graficos.textoError.setText("Los valores deben ser numéricos");
                }
            } catch (Exception noEsInt) {
                Graficos.labelError.setText("ERROR:");
                Graficos.textoError.setText("a y b deben ser enteros");
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
