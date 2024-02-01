package com.distribucion.distribucionesprograma;

//UI
import javax.swing.JLabel;
import javax.swing.JTextField;

//Redondeo
import java.math.BigDecimal;
import java.math.RoundingMode;

//Distribuciones
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.FDistribution;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;
import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.apache.commons.math3.distribution.HypergeometricDistribution;
import org.apache.commons.math3.distribution.BetaDistribution;
import org.apache.commons.math3.distribution.GammaDistribution;
import org.apache.commons.math3.distribution.PascalDistribution;

//Elementos gráficos
import java.awt.BasicStroke;
import java.awt.Color;
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYBarRenderer;

/**
 *
 * @author Santiago Garcia Sanchez
 */
public class Graficos {

    public static JTextField inputX, inputProb;
    public static JLabel labelError, textoError, labelProb1, labelProb2;
    public static ChartPanel graficoDist;
    public static BigDecimal esperanzaActual, varianciaActual;
    ///////////////
    ////Gráficos///
    //////////////

    //Distribución Normal
    static void PlotNormal(double mu, double sigma) {

        ///Crear distribución
        NormalDistribution distribution = new NormalDistribution(mu, sigma);

        // Crear dataset
        Function2D normal = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "N(" + mu + "," + sigma + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(normal, mu - 4 * sigma, mu + 4 * sigma, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Normal", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(mu).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(sigma).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotNormalX(double mu, double sigma, double x, String direccion) {
        ///Crear distribución
        NormalDistribution distribution = new NormalDistribution(mu, sigma);

        // Crear dataset
        Function2D normal = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Normal (" + mu + "," + sigma + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(normal, mu - 4 * sigma, mu + 4 * sigma, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Normal", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        double maximo = dataset.getDomainUpperBound(true);
        double minimo = dataset.getDomainLowerBound(true);
        switch (direccion) {
            case "mayor": {
                BigDecimal probabilidadRedon;
                //Evitar numeros muy grandes
                try {
                    double probabilidad = distribution.probability(x, maximo);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    if (x < minimo) {
                        probabilidadRedon = new BigDecimal(1);

                        //Definir eje
                        for (double ejeX = minimo; ejeX <= maximo; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));

                    } else {
                        //Definir eje
                        for (double ejeX = x; ejeX <= maximo; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));
                    }

                } catch (Exception NumberIsTooLargeException) {

                    probabilidadRedon = new BigDecimal(0);

                }

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                BigDecimal probabilidadRedon;
                //Evita numeros muy grandes
                try {
                    double probabilidad = distribution.probability(minimo, x);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    if (x > maximo) {
                        probabilidadRedon = new BigDecimal(1);

                        for (double ejeX = minimo; ejeX <= maximo; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));

                    } else {
                        for (double ejeX = minimo; ejeX <= x; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));
                    }
                } catch (Exception NumberIsTooLargeException) {
                    probabilidadRedon = new BigDecimal(0);

                }

                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "doble": {
                BigDecimal probabilidadRedon;
                try {
                    double probabilidad = distribution.probability(minimo, -Math.abs(x)) + distribution.probability(Math.abs(x), maximo);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = minimo; ejeX <= -Math.abs(x); ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                    //Segundo render
                    XYAreaRenderer renderer2 = new XYAreaRenderer();
                    renderer2.setSeriesPaint(0, new Color(200, 200, 255));
                    plot.setRenderer(2, renderer2);
                    XYSeries areaSeries2 = new XYSeries("Área");

                    for (double ejeX = Math.abs(x); ejeX <= maximo; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries2.add(ejeX, y);
                    }
                    plot.setDataset(2, new XYSeriesCollection(areaSeries2));

                } catch (Exception NumberIsTooLargeException) {
                    probabilidadRedon = new BigDecimal(0);
                }

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            default:
                break;
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(mu).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(sigma).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotNormalP(double mu, double sigma, double prob, String direccion) {

        ///Crear distribución
        NormalDistribution distribution = new NormalDistribution(mu, sigma);

        // Crear dataset
        Function2D normal = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Normal (" + mu + "," + sigma + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(normal, mu - 4 * sigma, mu + 4 * sigma, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Normal", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        double maximo = dataset.getDomainUpperBound(true);
        double minimo = dataset.getDomainLowerBound(true);

        switch (direccion) {
            case "mayor": {
                double valorX = distribution.inverseCumulativeProbability(1 - prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = valorX; ejeX <= maximo; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            case "menor": {
                double valorX = distribution.inverseCumulativeProbability(prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = minimo; ejeX <= valorX; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            case "doble": {
                double valorX = distribution.inverseCumulativeProbability(prob / 2);
                BigDecimal xRedon = new BigDecimal(Math.abs(valorX)).setScale(5, RoundingMode.HALF_UP);

                for (double ejeX = minimo; ejeX <= -Math.abs(valorX); ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Segundo render
                XYAreaRenderer renderer2 = new XYAreaRenderer();
                renderer2.setSeriesPaint(0, new Color(200, 200, 255));
                plot.setRenderer(2, renderer2);
                XYSeries areaSeries2 = new XYSeries("Área");

                for (double ejeX = Math.abs(valorX); ejeX <= maximo; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries2.add(ejeX, y);
                }
                plot.setDataset(2, new XYSeriesCollection(areaSeries2));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            default:
                break;
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(mu).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(sigma).setScale(5, RoundingMode.HALF_UP);
    }

    //Distribución Beta
    static void PlotBeta(double valorAlfa, double valorBeta) {

        ///Crear distribución
        BetaDistribution distribution = new BetaDistribution(valorAlfa, valorBeta);

        // Crear dataset
        Function2D beta = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Beta(" + valorAlfa + "," + valorBeta + ")";

        XYSeries series = null;
        try { //Elegir conjunto soporte, [0;1] o (0,1)
            series = DatasetUtils.sampleFunction2DToSeries(beta, 0, 1, 1000, leyenda);
        } catch (Exception soportInc) {
            series = DatasetUtils.sampleFunction2DToSeries(beta, 0.00001, 0.99999, 1000, leyenda);
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Beta", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(0, 1);

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(valorAlfa / (valorAlfa + valorBeta)).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((valorAlfa * valorBeta) / ((Math.pow(valorAlfa + valorBeta, 2)) * (valorAlfa + valorBeta + 1))).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotBetaX(double valorAlfa, double valorBeta, double x, String direccion) {
        ///Crear distribución
        BetaDistribution distribution = new BetaDistribution(valorAlfa, valorBeta);

        // Crear dataset
        Function2D beta = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Beta(" + valorAlfa + "," + valorBeta + ")";

        XYSeries series = null;
        try { //Elegir conjunto soporte, [0;1] o (0,1)
            series = DatasetUtils.sampleFunction2DToSeries(beta, 0, 1, 1000, leyenda);
        } catch (Exception soportInc) {
            series = DatasetUtils.sampleFunction2DToSeries(beta, 0.00001, 0.99999, 1000, leyenda);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Beta", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(0, 1);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        double maximo = dataset.getDomainUpperBound(true);
        double minimo = dataset.getDomainLowerBound(true);
        switch (direccion) {
            case "mayor": {
                BigDecimal probabilidadRedon;
                //Evitar numeros muy grandes
                try {
                    double probabilidad = distribution.probability(x, maximo);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = x; ejeX <= maximo; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                } catch (Exception NumberIsTooLargeException) {

                    probabilidadRedon = new BigDecimal(0);

                }

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                BigDecimal probabilidadRedon;
                //Evita numeros muy grandes
                try {
                    double probabilidad = distribution.probability(minimo, x);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = minimo; ejeX <= x; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                } catch (Exception NumberIsTooLargeException) {
                    probabilidadRedon = new BigDecimal(0);

                }

                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            default:
                break;
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(valorAlfa / (valorAlfa + valorBeta)).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((valorAlfa * valorBeta) / ((Math.pow(valorAlfa + valorBeta, 2)) * (valorAlfa + valorBeta + 1))).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotBetaP(double valorAlfa, double valorBeta, double prob, String direccion) {
        ///Crear distribución
        BetaDistribution distribution = new BetaDistribution(valorAlfa, valorBeta);

        // Crear dataset
        Function2D beta = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Beta(" + valorAlfa + "," + valorBeta + ")";
        XYSeries series = null;
        try { //Elegir conjunto soporte, [0;1] o (0,1)
            series = DatasetUtils.sampleFunction2DToSeries(beta, 0, 1, 1000, leyenda);
        } catch (Exception soportInc) {
            series = DatasetUtils.sampleFunction2DToSeries(beta, 0.00001, 0.99999, 1000, leyenda);
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Beta", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(0, 1);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Grafico
        double maximo = dataset.getDomainUpperBound(true);
        double minimo = dataset.getDomainLowerBound(true);
        switch (direccion) {
            case "mayor": {
                double valorX = distribution.inverseCumulativeProbability(1 - prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = valorX; ejeX <= maximo; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            case "menor": {
                double valorX = distribution.inverseCumulativeProbability(prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                if (valorX <= maximo) {
                    for (double ejeX = minimo; ejeX <= valorX; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                } else {
                    for (double ejeX = minimo; ejeX <= maximo; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(valorAlfa / (valorAlfa + valorBeta)).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((valorAlfa * valorBeta) / ((Math.pow(valorAlfa + valorBeta, 2)) * (valorAlfa + valorBeta + 1))).setScale(5, RoundingMode.HALF_UP);
    }

    //Distribución Chi-cuadrado
    static void PlotChi(int k) {

        ///Crear distribución
        ChiSquaredDistribution distribution = new ChiSquaredDistribution(k);

        //Límites
        int maximo = k;
        double minimo = 0;

        if (k == 1) {
            minimo = 0.015;
            maximo = 6;
        } else if (k < 10) {
            minimo = 0;
            for (int max = maximo; distribution.cumulativeProbability(max) <= 0.9995; max++) {
                maximo = max;
            }
        } else {
            for (int min = k; min >= 0 && distribution.cumulativeProbability(min) >= 0.0005; min--) {
                minimo = min;
            }

            for (int max = maximo; distribution.cumulativeProbability(max) <= 0.9995; max++) {
                maximo = max;
            }
        }

        // Crear dataset
        Function2D chi = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Χ (" + k + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(chi, minimo, maximo, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Chi-cuadrado", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        if (k == 1) {
            chart.getXYPlot().getDomainAxis().setRange(0, maximo);
        } else {
            chart.getXYPlot().getDomainAxis().setRange(minimo, maximo);
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(k).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(2 * k).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotChiX(int k, double x, String direccion) {

        ///Crear distribución
        ChiSquaredDistribution distribution = new ChiSquaredDistribution(k);

        //Límites
        int maximo = k;
        double minimo = 0;

        if (k == 1) {
            minimo = 0.015;
            maximo = 6;
        } else if (k < 10) {
            minimo = 0;
            for (int max = maximo; distribution.cumulativeProbability(max) <= 0.9995; max++) {
                maximo = max;
            }
        } else {
            for (int min = k; min >= 0 && distribution.cumulativeProbability(min) >= 0.0005; min--) {
                minimo = min;
            }

            for (int max = maximo; distribution.cumulativeProbability(max) <= 0.9995; max++) {
                maximo = max;
            }
        }

        // Crear dataset
        Function2D chi = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Χ (" + k + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(chi, minimo, maximo, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Chi-cuadrado", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        if (k == 1) {
            chart.getXYPlot().getDomainAxis().setRange(0, maximo);
        } else {
            chart.getXYPlot().getDomainAxis().setRange(minimo, maximo);
        }

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        switch (direccion) {
            case "mayor": {
                BigDecimal probabilidadRedon;
                //Evitar numeros muy grandes
                try {
                    double probabilidad = distribution.probability(x, maximo);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = x; ejeX <= maximo; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                } catch (Exception NumberIsTooLargeException) {

                    probabilidadRedon = new BigDecimal(0);

                }

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                BigDecimal probabilidadRedon;
                //Evita numeros muy grandes
                try {
                    double probabilidad = distribution.probability(minimo, x);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = minimo; ejeX <= x; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                } catch (Exception NumberIsTooLargeException) {
                    probabilidadRedon = new BigDecimal(0);

                }

                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            default:
                break;
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(k).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(2 * k).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotChiP(int k, double prob, String direccion) {

        ///Crear distribución
        ChiSquaredDistribution distribution = new ChiSquaredDistribution(k);

        //Límites
        int maximo = k;
        double minimo = 0;

        if (k == 1) {
            minimo = 0.015;
            maximo = 6;
        } else if (k < 10) {
            minimo = 0;
            for (int max = maximo; distribution.cumulativeProbability(max) <= 0.9995; max++) {
                maximo = max;
            }
        } else {
            for (int min = k; min >= 0 && distribution.cumulativeProbability(min) >= 0.0005; min--) {
                minimo = min;
            }

            for (int max = maximo; distribution.cumulativeProbability(max) <= 0.9995; max++) {
                maximo = max;
            }
        }

        // Crear dataset
        Function2D chi = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Χ (" + k + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(chi, minimo, maximo, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Chi-cuadrado", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        if (k == 1) {
            chart.getXYPlot().getDomainAxis().setRange(0, maximo);
        } else {
            chart.getXYPlot().getDomainAxis().setRange(minimo, maximo);
        }

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        switch (direccion) {
            case "mayor": {
                double valorX = distribution.inverseCumulativeProbability(1 - prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = valorX; ejeX <= maximo; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            case "menor": {
                double valorX = distribution.inverseCumulativeProbability(prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                if (valorX <= maximo) {
                    for (double ejeX = minimo; ejeX <= valorX; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                } else {
                    for (double ejeX = minimo; ejeX <= maximo; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(k).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(2 * k).setScale(5, RoundingMode.HALF_UP);
    }

    //Distribución exponencial
    static void PlotExpo(double lambda) {

        ///Crear distribución
        ExponentialDistribution distribution = new ExponentialDistribution(1 / lambda);

        // Crear dataset
        Function2D exponencial = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Exp (" + lambda + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(exponencial, 0, (1 / lambda) * 10, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Exponencial", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getRangeAxis().setRange(0, lambda);

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(1 / lambda).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(1 / (lambda * lambda)).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotExpoX(double lambda, double x, String direccion) {

        ///Crear distribución
        ExponentialDistribution distribution = new ExponentialDistribution(1 / lambda);

        // Crear dataset
        Function2D exponencial = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Exp (" + lambda + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(exponencial, 0, (1 / lambda) * 10, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Exponencial", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getRangeAxis().setRange(0, lambda);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        double maximo = dataset.getDomainUpperBound(true);
        double minimo = 0;
        switch (direccion) {
            case "mayor": {
                BigDecimal probabilidadRedon;
                //Evitar numeros muy grandes
                try {
                    double probabilidad = distribution.probability(x, maximo);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    if (x < minimo) {
                        probabilidadRedon = new BigDecimal(1);

                        //Definir eje
                        for (double ejeX = minimo; ejeX <= maximo; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));

                    } else {
                        //Definir eje
                        for (double ejeX = x; ejeX <= maximo; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));
                    }

                } catch (Exception NumberIsTooLargeException) {

                    probabilidadRedon = new BigDecimal(0);

                }

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                BigDecimal probabilidadRedon;
                //Evita numeros muy grandes
                try {
                    double probabilidad = distribution.probability(minimo, x);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    if (x > maximo) {
                        probabilidadRedon = new BigDecimal(1);

                        for (double ejeX = minimo; ejeX <= maximo; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));

                    } else {
                        for (double ejeX = minimo; ejeX <= x; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));
                    }
                } catch (Exception NumberIsTooLargeException) {
                    probabilidadRedon = new BigDecimal(0);

                }

                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            default:
                break;
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(1 / lambda).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(1 / (lambda * lambda)).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotExpoP(double lambda, double prob, String direccion) {

        ///Crear distribución
        ExponentialDistribution distribution = new ExponentialDistribution(1 / lambda);

        // Crear dataset
        Function2D exponencial = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Exp (" + lambda + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(exponencial, 0, (1 / lambda) * 10, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Exponencial", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getRangeAxis().setRange(0, lambda);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        double maximo = dataset.getDomainUpperBound(true);
        double minimo = 0;

        switch (direccion) {
            case "mayor": {
                double valorX = distribution.inverseCumulativeProbability(1 - prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = valorX; ejeX <= maximo; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            case "menor": {
                double valorX = distribution.inverseCumulativeProbability(prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = minimo; ejeX <= valorX; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
        }
        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(1 / lambda).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(1 / (lambda * lambda)).setScale(5, RoundingMode.HALF_UP);
    }

    //Distribución F de Snedecor
    static void PlotF(double df1, double df2) {

        ///Crear distribución
        FDistribution distribution = new FDistribution(df1, df2);

        // Crear dataset
        Function2D f = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "F(" + df1 + "," + df2 + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(f, 0, 6, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución F", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        if (df2 > 2) {
            esperanzaActual = new BigDecimal(df2 / (df2 - 2)).setScale(5, RoundingMode.HALF_UP);
        } else {
            esperanzaActual = null;
        }
        if (df2 > 4) {
            varianciaActual = new BigDecimal((2 * df2 * df2 * (df1 + df2 - 2)) / (df1 * ((df2 - 2) * (df2 - 2)) * (df2 - 4))).setScale(5, RoundingMode.HALF_UP);
        } else {
            varianciaActual = null;
        }

    }

    static void PlotFX(double df1, double df2, double x, String direccion) {
        ///Crear distribución
        FDistribution distribution = new FDistribution(df1, df2);

        // Crear dataset
        Function2D f = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "F(" + df1 + "," + df2 + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(f, 0, 100, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución F", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(0, 6);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        double maximo = dataset.getDomainUpperBound(true);
        switch (direccion) {
            case "mayor": {
                BigDecimal probabilidadRedon;
                //Evitar numeros muy grandes
                try {
                    double probabilidad = distribution.probability(x, maximo);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = x; ejeX <= maximo; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                } catch (Exception NumberIsTooLargeException) {

                    probabilidadRedon = new BigDecimal(0);

                }

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                BigDecimal probabilidadRedon;
                //Evita numeros muy grandes
                try {
                    double probabilidad = distribution.probability(0, x);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = 0; ejeX <= x; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                } catch (Exception NumberIsTooLargeException) {
                    probabilidadRedon = new BigDecimal(0);

                }

                inputProb.setText(probabilidadRedon.toString());
                break;
            }

            default:
                break;
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        if (df2 > 2) {
            esperanzaActual = new BigDecimal(df2 / (df2 - 2)).setScale(5, RoundingMode.HALF_UP);
        } else {
            esperanzaActual = null;
        }
        if (df2 > 4) {
            varianciaActual = new BigDecimal((2 * df2 * df2 * (df1 + df2 - 2)) / (df1 * ((df2 - 2) * (df2 - 2)) * (df2 - 4))).setScale(5, RoundingMode.HALF_UP);
        } else {
            varianciaActual = null;
        }
    }

    static void PlotFP(double df1, double df2, double prob, String direccion) {
        ///Crear distribución
        FDistribution distribution = new FDistribution(df1, df2);

        // Crear dataset
        Function2D f = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "F(" + df1 + "," + df2 + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(f, 0, 100, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución F", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(0, 6);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        double maximo = dataset.getDomainUpperBound(true);

        switch (direccion) {
            case "mayor": {
                double valorX = distribution.inverseCumulativeProbability(1 - prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = valorX; ejeX <= maximo; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            case "menor": {
                double valorX = distribution.inverseCumulativeProbability(prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = 0; ejeX <= valorX; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        if (df2 > 2) {
            esperanzaActual = new BigDecimal(df2 / (df2 - 2)).setScale(5, RoundingMode.HALF_UP);
        } else {
            esperanzaActual = null;
        }
        if (df2 > 4) {
            varianciaActual = new BigDecimal((2 * df2 * df2 * (df1 + df2 - 2)) / (df1 * ((df2 - 2) * (df2 - 2)) * (df2 - 4))).setScale(5, RoundingMode.HALF_UP);
        } else {
            varianciaActual = null;
        }
    }

    //Distribución Gama
    static void PlotGama(double valorAlfa, double valorBeta) {

        ///Crear distribución
        GammaDistribution distribution = new GammaDistribution(valorAlfa, valorBeta);

        //Calcular media y desvío
        double media = valorAlfa * valorBeta;
        double variancia = valorAlfa * (valorBeta * valorBeta);
        double desvio = Math.sqrt(variancia);

        // Crear dataset
        Function2D gama = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Gama(" + valorAlfa + "," + valorBeta + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(gama, 0, media + 4.5 * desvio, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Gama", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(Math.max(0, media - 3 * desvio), media + 4.5 * desvio);

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(media).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(variancia).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotGamaX(double valorAlfa, double valorBeta, double x, String direccion) {

        ///Crear distribución
        GammaDistribution distribution = new GammaDistribution(valorAlfa, valorBeta);

        //Calcular media y desvío
        double media = valorAlfa * valorBeta;
        double variancia = valorAlfa * (valorBeta * valorBeta);
        double desvio = Math.sqrt(variancia);

        // Crear dataset
        Function2D gama = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Gama(" + valorAlfa + "," + valorBeta + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(gama, 0, media + 4.5 * desvio, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Gama", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(Math.max(0, media - 3 * desvio), media + 4.5 * desvio);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        double maximo = dataset.getDomainUpperBound(true);
        double minimo = dataset.getDomainLowerBound(true);
        switch (direccion) {
            case "mayor": {
                BigDecimal probabilidadRedon;
                //Evitar numeros muy grandes
                try {
                    double probabilidad = distribution.probability(x, maximo);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = x; ejeX <= maximo; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                } catch (Exception NumberIsTooLargeException) {

                    probabilidadRedon = new BigDecimal(0);

                }

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                BigDecimal probabilidadRedon;
                //Evita numeros muy grandes
                try {
                    double probabilidad = distribution.probability(minimo, x);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = minimo; ejeX <= x; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                } catch (Exception NumberIsTooLargeException) {
                    probabilidadRedon = new BigDecimal(0);

                }

                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            default:
                break;
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(media).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(variancia).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotGamaP(double valorAlfa, double valorBeta, double prob, String direccion) {

        ///Crear distribución
        GammaDistribution distribution = new GammaDistribution(valorAlfa, valorBeta);

        //Calcular media y desvío
        double media = valorAlfa * valorBeta;
        double variancia = valorAlfa * (valorBeta * valorBeta);
        double desvio = Math.sqrt(variancia);

        // Crear dataset
        Function2D gama = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "Gama(" + valorAlfa + "," + valorBeta + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(gama, 0, media + 4.5 * desvio, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Gama", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(Math.max(0, media - 3 * desvio), media + 4.5 * desvio);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Grafico
        double maximo = dataset.getDomainUpperBound(true);
        double minimo = dataset.getDomainLowerBound(true);
        switch (direccion) {
            case "mayor": {
                double valorX = distribution.inverseCumulativeProbability(1 - prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = valorX; ejeX <= maximo; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            case "menor": {
                double valorX = distribution.inverseCumulativeProbability(prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                if (valorX <= maximo) {
                    for (double ejeX = minimo; ejeX <= valorX; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                } else {
                    for (double ejeX = minimo; ejeX <= maximo; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(media).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(variancia).setScale(5, RoundingMode.HALF_UP);
    }

    //Distribución t de Student
    static void PlotStu(double valorV) {

        ///Crear distribución
        TDistribution distribution = new TDistribution(valorV);

        // Crear dataset
        Function2D TDist = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        // Limites
        int limite = -1;

        if (distribution.cumulativeProbability(limite) >= 0.01) {
            for (int min = limite; limite >= -10 && distribution.cumulativeProbability(min) >= 0.0005; min--) {
                limite = min;
            }
        }

        String leyenda = "T(" + valorV + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(TDist, limite, -limite, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución t de Student", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(limite, -limite);

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        if (valorV > 1) {
            esperanzaActual = new BigDecimal(0).setScale(5, RoundingMode.HALF_UP);
        } else {
            esperanzaActual = null;
        }

        if (valorV > 2) {
            varianciaActual = new BigDecimal(valorV / (valorV - 2)).setScale(5, RoundingMode.HALF_UP);
        } else {
            varianciaActual = null;
        }

    }

    static void PlotStuX(double valorV, double x, String direccion) {
        ///Crear distribución
        TDistribution distribution = new TDistribution(valorV);

        // Crear dataset
        Function2D TDist = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        // Limites
        int limite = -1;

        if (distribution.cumulativeProbability(limite) >= 0.01) {
            for (int min = limite; limite >= -10 && distribution.cumulativeProbability(min) >= 0.0005; min--) {
                limite = min;
            }
        }

        String leyenda = "T(" + valorV + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(TDist, limite, -limite, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución t de Student", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(limite, -limite);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        double maximo = dataset.getDomainUpperBound(true);
        double minimo = dataset.getDomainLowerBound(true);
        switch (direccion) {
            case "mayor": {
                BigDecimal probabilidadRedon;
                //Evitar numeros muy grandes
                try {
                    double probabilidad = 1 - distribution.cumulativeProbability(x);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    if (x < minimo) {
                        probabilidadRedon = new BigDecimal(1);

                        //Definir eje
                        for (double ejeX = minimo; ejeX <= maximo; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));

                    } else {
                        //Definir eje
                        for (double ejeX = x; ejeX <= maximo; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));
                    }

                } catch (Exception NumberIsTooLargeException) {

                    probabilidadRedon = new BigDecimal(0);

                }

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                BigDecimal probabilidadRedon;
                //Evita numeros muy grandes
                try {
                    double probabilidad = distribution.cumulativeProbability(x);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    if (x > maximo) {
                        probabilidadRedon = new BigDecimal(1);

                        for (double ejeX = minimo; ejeX <= maximo; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));

                    } else {
                        for (double ejeX = minimo; ejeX <= x; ejeX += 0.01) {
                            double y = distribution.density(ejeX);
                            areaSeries.add(ejeX, y);
                        }
                        plot.setDataset(1, new XYSeriesCollection(areaSeries));
                    }
                } catch (Exception NumberIsTooLargeException) {
                    probabilidadRedon = new BigDecimal(0);

                }

                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "doble": {
                BigDecimal probabilidadRedon;
                try {
                    double probabilidad = distribution.cumulativeProbability(-Math.abs(x)) + (1 - distribution.cumulativeProbability(Math.abs(x)));
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = minimo; ejeX <= -Math.abs(x); ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                    //Segundo render
                    XYAreaRenderer renderer2 = new XYAreaRenderer();
                    renderer2.setSeriesPaint(0, new Color(200, 200, 255));
                    plot.setRenderer(2, renderer2);
                    XYSeries areaSeries2 = new XYSeries("Área");

                    for (double ejeX = Math.abs(x); ejeX <= maximo; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries2.add(ejeX, y);
                    }
                    plot.setDataset(2, new XYSeriesCollection(areaSeries2));

                } catch (Exception NumberIsTooLargeException) {
                    probabilidadRedon = new BigDecimal(0);
                }

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            default:
                break;
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        if (valorV > 1) {
            esperanzaActual = new BigDecimal(0).setScale(5, RoundingMode.HALF_UP);
        } else {
            esperanzaActual = null;
        }

        if (valorV > 2) {
            varianciaActual = new BigDecimal(valorV / (valorV - 2)).setScale(5, RoundingMode.HALF_UP);
        } else {
            varianciaActual = null;
        }
    }

    static void PlotStuP(double valorV, double prob, String direccion) {
        ///Crear distribución
        TDistribution distribution = new TDistribution(valorV);

        // Crear dataset
        Function2D TDist = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        // Limites
        int limite = -1;

        if (distribution.cumulativeProbability(limite) >= 0.01) {
            for (int min = limite; limite >= -10 && distribution.cumulativeProbability(min) >= 0.0005; min--) {
                limite = min;
            }
        }

        String leyenda = "T(" + valorV + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(TDist, limite, -limite, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución t de Student", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(limite, -limite);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        switch (direccion) {
            case "mayor": {
                double valorX = distribution.inverseCumulativeProbability(1 - prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = valorX; ejeX <= -limite; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            case "menor": {
                double valorX = distribution.inverseCumulativeProbability(prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = limite; ejeX <= valorX; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            case "doble": {
                double valorX = distribution.inverseCumulativeProbability(prob / 2);
                BigDecimal xRedon = new BigDecimal(Math.abs(valorX)).setScale(5, RoundingMode.HALF_UP);

                for (double ejeX = limite; ejeX <= -Math.abs(valorX); ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Segundo render
                XYAreaRenderer renderer2 = new XYAreaRenderer();
                renderer2.setSeriesPaint(0, new Color(200, 200, 255));
                plot.setRenderer(2, renderer2);
                XYSeries areaSeries2 = new XYSeries("Área");

                for (double ejeX = Math.abs(valorX); ejeX <= -limite; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries2.add(ejeX, y);
                }
                plot.setDataset(2, new XYSeriesCollection(areaSeries2));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            default:
                break;
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        if (valorV > 1) {
            esperanzaActual = new BigDecimal(0).setScale(5, RoundingMode.HALF_UP);
        } else {
            esperanzaActual = null;
        }

        if (valorV > 2) {
            varianciaActual = new BigDecimal(valorV / (valorV - 2)).setScale(5, RoundingMode.HALF_UP);
        } else {
            varianciaActual = null;
        }
    }

    //Distribución Uniforme
    static void PlotUnifC(double valorA, double valorB) {

        ///Crear distribución
        UniformRealDistribution distribution = new UniformRealDistribution(valorA, valorB);

        // Crear dataset
        Function2D uniforme = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "U(" + valorA + "," + valorB + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(uniforme, valorA, valorB, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Uniforme (continua)", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(valorA, valorB);

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal((valorA + valorB) / 2).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((Math.pow(valorB - valorA, 2)) / 12).setScale(5, RoundingMode.HALF_UP);

    }

    static void PlotUnifCX(double valorA, double valorB, double valorX, String direccion) {

        ///Crear distribución
        UniformRealDistribution distribution = new UniformRealDistribution(valorA, valorB);

        // Crear dataset
        Function2D uniforme = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "U(" + valorA + "," + valorB + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(uniforme, valorA, valorB, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Uniforme (continua)", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(valorA, valorB);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        switch (direccion) {
            case "mayor": {
                BigDecimal probabilidadRedon;
                //Evitar numeros muy grandes
                try {
                    double probabilidad = distribution.probability(valorX, valorB);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = valorX; ejeX <= valorB; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                } catch (Exception NumberIsTooLargeException) {

                    probabilidadRedon = new BigDecimal(0);

                }

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                BigDecimal probabilidadRedon;
                //Evita numeros muy grandes
                try {
                    double probabilidad = distribution.probability(valorA, valorX);
                    probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                    for (double ejeX = valorA; ejeX <= valorX; ejeX += 0.01) {
                        double y = distribution.density(ejeX);
                        areaSeries.add(ejeX, y);
                    }
                    plot.setDataset(1, new XYSeriesCollection(areaSeries));

                } catch (Exception NumberIsTooLargeException) {
                    probabilidadRedon = new BigDecimal(0);

                }

                inputProb.setText(probabilidadRedon.toString());
                break;
            }

            default:
                break;
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal((valorA + valorB) / 2).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((Math.pow(valorB - valorA, 2)) / 12).setScale(5, RoundingMode.HALF_UP);

    }

    static void PlotUnifCP(double valorA, double valorB, double prob, String direccion) {
        ///Crear distribución
        UniformRealDistribution distribution = new UniformRealDistribution(valorA, valorB);

        // Crear dataset
        Function2D uniforme = new Function2D() {
            @Override
            public double getValue(double x) {
                return distribution.density(x);
            }
        };

        String leyenda = "U(" + valorA + "," + valorB + ")";
        XYSeries series = DatasetUtils.sampleFunction2DToSeries(uniforme, valorA, valorB, 1000, leyenda);
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Distribución Uniforme (continua)", "x", "f(x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);
        chart.getPlot().setBackgroundPaint(Color.WHITE);
        chart.getXYPlot().getDomainAxis().setRange(valorA, valorB);

        //Añadir área
        XYAreaRenderer renderer = new XYAreaRenderer();
        renderer.setSeriesPaint(0, new Color(200, 200, 255));
        XYPlot plot = chart.getXYPlot();
        plot.setRenderer(1, renderer);
        XYSeries areaSeries = new XYSeries("Área");

        //Graficar según dirección
        switch (direccion) {
            case "mayor": {
                double valorX = distribution.inverseCumulativeProbability(1 - prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = valorX; ejeX <= valorB; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
            case "menor": {
                double valorX = distribution.inverseCumulativeProbability(prob);
                BigDecimal xRedon = new BigDecimal(valorX).setScale(5, RoundingMode.HALF_UP);

                //Definir eje
                for (double ejeX = valorA; ejeX <= valorX; ejeX += 0.01) {
                    double y = distribution.density(ejeX);
                    areaSeries.add(ejeX, y);
                }
                plot.setDataset(1, new XYSeriesCollection(areaSeries));

                //Actualizar campo texto
                inputX.setText(xRedon.toString());
                break;
            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal((valorA + valorB) / 2).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((Math.pow(valorB - valorA, 2)) / 12).setScale(5, RoundingMode.HALF_UP);
    }

    //Distribución Binomial
    static void PlotBinom(int n, double p) {

        ///Crear distribución
        BinomialDistribution distribution = new BinomialDistribution(n, p);

        // Crear dataset
        XYSeries series = new XYSeries("Bin(" + n + "," + p + ")");

        if (n == 1) { //Caso Bernoulli
            series.setKey("Be(" + p + ")");
        }

        for (int x = 0; x <= n; x++) {
            series.add(x, distribution.probability(x));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Binomial", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        if (n == 1) { //Caso Bernoulli
            chart.setTitle("Distribución Bernoulli");
        }

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        int minAxis = 0;
        int maxAxis = n;
        int mediaInt = (int) (n * p);

        if (distribution.probability(minAxis) <= 0.01) {
            for (int min = mediaInt; distribution.probability(min) >= 0.001; min--) {
                minAxis = min;
            }
        }

        if (distribution.probability(maxAxis) <= 0.01) {
            for (int max = mediaInt; distribution.probability(max) >= 0.001; max++) {
                maxAxis = max;
            }

        }
        xAxis.setRange(minAxis, maxAxis);

        // Modiifcar las barras
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(n * p).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(n * p * (1 - p)).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotBinomX(int n, double p, int x, String direccion) {

        ///Crear distribución
        BinomialDistribution distribution = new BinomialDistribution(n, p);

        // Crear dataset
        XYSeries series = new XYSeries("Bin(" + n + "," + p + ")");
        for (int i = 0; i <= n; i++) {
            series.add(i, distribution.probability(i));
        }

        if (n == 1) { //Caso Bernoulli
            series.setKey("Be(" + p + ")");
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Binomial", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        if (n == 1) { //Caso Bernoulli
            chart.setTitle("Distribución Bernoulli");
        }

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        //Render
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();

        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        plot.setDataset(1, dataset);
        plot.setRenderer(1, renderer);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        int minAxis = 0;
        int maxAxis = n;
        int mediaInt = (int) (n * p);

        if (distribution.probability(minAxis) <= 0.01) {
            for (int min = mediaInt; distribution.probability(min) >= 0.001; min--) {
                minAxis = min;
            }
        }

        if (distribution.probability(maxAxis) <= 0.01) {
            for (int max = mediaInt; distribution.probability(max) >= 0.001; max++) {
                maxAxis = max;
            }

        }
        xAxis.setRange(minAxis, maxAxis);

        //Seleccionar según dirección
        switch (direccion) {
            case "mayor": {
                double probabilidad = 1 - distribution.cumulativeProbability(x) + distribution.probability(x);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                for (int i = x; i <= n; i++) {
                    series2.add(i, distribution.probability(i));
                }

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                double probabilidad = distribution.cumulativeProbability(x);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                for (int i = 0; i <= x; i++) {
                    series2.add(i, distribution.probability(i));
                }

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "igual": {
                double probabilidad = distribution.probability(x);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                series2.add(x, distribution.probability(x));

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;

            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(n * p).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(n * p * (1 - p)).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotBinomP(int n, double p, double prob, String direccion) {

        ///Crear distribución
        BinomialDistribution distribution = new BinomialDistribution(n, p);

        // Crear dataset
        XYSeries series = new XYSeries("Bin(" + n + "," + p + ")");
        for (int i = 0; i <= n; i++) {
            series.add(i, distribution.probability(i));
        }

        if (n == 1) { //Caso Bernoulli
            series.setKey("Be(" + p + ")");
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Binomial", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        if (n == 1) { //Caso Bernoulli
            chart.setTitle("Distribución Bernoulli");
        }

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        int minAxis = 0;
        int maxAxis = n;
        int mediaInt = (int) (n * p);

        if (distribution.probability(minAxis) <= 0.01) {
            for (int min = mediaInt; distribution.probability(min) >= 0.001; min--) {
                minAxis = min;
            }
        }

        if (distribution.probability(maxAxis) <= 0.01) {
            for (int max = mediaInt; distribution.probability(max) >= 0.001; max++) {
                maxAxis = max;
            }

        }
        xAxis.setRange(minAxis, maxAxis);

        // Modificar las barras
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        //Seleccionar según dirección
        switch (direccion) {
            case "mayor": {

                int valorA = distribution.inverseCumulativeProbability(1 - prob);
                int valorB = valorA + 1;

                double probabilidadA = 1 - distribution.cumulativeProbability(valorA) + distribution.probability(valorA);
                BigDecimal probabilidadRedonA = new BigDecimal(probabilidadA).setScale(5, RoundingMode.HALF_UP);
                double probabilidadB = 1 - distribution.cumulativeProbability(valorB) + distribution.probability(valorB);
                BigDecimal probabilidadRedonB = new BigDecimal(probabilidadB).setScale(5, RoundingMode.HALF_UP);

                //Actualizar campo texto
                labelProb1.setText("P(X≥" + valorA + ") = " + probabilidadRedonA);
                labelProb1.setVisible(true);
                labelProb2.setText("P(X≥" + valorB + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "menor": {
                int valorB = distribution.inverseCumulativeProbability(prob);
                int valorA = valorB - 1;

                double probabilidadA = distribution.cumulativeProbability(valorA);
                BigDecimal probabilidadRedonA = new BigDecimal(probabilidadA).setScale(5, RoundingMode.HALF_UP);
                double probabilidadB = distribution.cumulativeProbability(valorB);
                BigDecimal probabilidadRedonB = new BigDecimal(probabilidadB).setScale(5, RoundingMode.HALF_UP);

                //Actualizar campo texto
                if (valorA >= 0) {
                    labelProb1.setText("P(X≤" + valorA + ") = " + probabilidadRedonA);
                    labelProb1.setVisible(true);
                }
                labelProb2.setText("P(X≤" + valorB + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "igual": {
                labelError.setText("Error:");
                textoError.setText("Seleccione P(X≥x) o P(X≤x) ");

                //Actualizar campo texto
                break;
            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(n * p).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(n * p * (1 - p)).setScale(5, RoundingMode.HALF_UP);
    }

    //Distribución binomial negativa
    static void PlotBinNeg(int r, double p) {

        ///Crear distribución
        PascalDistribution distribution = new PascalDistribution(r, p);

        // Crear dataset
        XYSeries series = new XYSeries("BN(" + r + "," + p + ")");

        if (r == 1) { //Caso Geometrica
            series.setKey("Ge(" + p + ")");
        }

        series.add(r, distribution.probability(0));
        series.add(r + 1, distribution.probability(1));
        for (int i = 2; distribution.probability(i) >= 0.001; i++) {
            series.add(r + i, distribution.probability(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Binomial Negativa", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        if (r == 1) { //Caso Geometrica
            chart.setTitle("Distribución Geométrica");
        }

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        xAxis.setRange(r, dataset.getDomainUpperBound(true));

        // Modiifcar las barras
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(r / p).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((r * (1 - p)) / (p * p)).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotBinNegX(int r, double p, int x, String direccion) {

        ///Crear distribución
        PascalDistribution distribution = new PascalDistribution(r, p);

        // Crear dataset
        XYSeries series = new XYSeries("BN(" + r + "," + p + ")");

        if (r == 1) { //Caso Geometrica
            series.setKey("Ge(" + p + ")");
        }

        series.add(r, distribution.probability(0));
        series.add(r + 1, distribution.probability(1));
        for (int i = 2; distribution.probability(i) >= 0.001; i++) {
            series.add(r + i, distribution.probability(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Binomial Negativa", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        if (r == 1) { //Caso Geometrica
            chart.setTitle("Distribución Geométrica");
        }

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        xAxis.setRange(r, dataset.getDomainUpperBound(true));

        // Modiifcar las barras
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));
        plot.setDataset(1, dataset);
        plot.setRenderer(1, renderer);

        //Seleccionar según dirección
        switch (direccion) {
            case "mayor": {
                double probabilidad = 1 - distribution.cumulativeProbability(x - r) + distribution.probability(x - r);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                int limiteSup = (int)dataset.getDomainUpperBound(false);
                for (int i = x; i <= limiteSup; i++) {
                    series2.add(i, distribution.probability(i-r));
                }

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                double probabilidad = distribution.cumulativeProbability(x - r);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                for (int i = 0; i <= x; i++) {
                    series2.add(i, distribution.probability(i-r));
                }

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "igual": {
                double probabilidad = distribution.probability(x - r);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                series2.add(x, distribution.probability(x - r));

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;

            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(r / p).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((r * (1 - p)) / (p * p)).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotBinNegP(int r, double p, double prob, String direccion) {

        ///Crear distribución
        PascalDistribution distribution = new PascalDistribution(r, p);

        // Crear dataset
        XYSeries series = new XYSeries("BN(" + r + "," + p + ")");

        if (r == 1) { //Caso Geometrica
            series.setKey("Ge(" + p + ")");
        }

        series.add(r, distribution.probability(0));
        series.add(r + 1, distribution.probability(1));
        for (int i = 2; distribution.probability(i) >= 0.001; i++) {
            series.add(r + i, distribution.probability(i));
        }

        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Binomial Negativa", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        if (r == 1) { //Caso Geometrica
            chart.setTitle("Distribución Geométrica");
        }

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        xAxis.setRange(r, dataset.getDomainUpperBound(true));

        // Modiifcar las barras
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        //Seleccionar según dirección
        switch (direccion) {
            case "mayor": {

                int valorA = distribution.inverseCumulativeProbability(1 - prob);
                int valorB = valorA + 1;

                double probabilidadA = 1 - distribution.cumulativeProbability(valorA) + distribution.probability(valorA);
                BigDecimal probabilidadRedonA = new BigDecimal(probabilidadA).setScale(5, RoundingMode.HALF_UP);
                double probabilidadB = 1 - distribution.cumulativeProbability(valorB) + distribution.probability(valorB);
                BigDecimal probabilidadRedonB = new BigDecimal(probabilidadB).setScale(5, RoundingMode.HALF_UP);

                //Actualizar campo texto
                labelProb1.setText("P(X≥" + (valorA + r) + ") = " + probabilidadRedonA);
                labelProb1.setVisible(true);
                labelProb2.setText("P(X≥" + (valorB + r) + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "menor": {
                int valorB = distribution.inverseCumulativeProbability(prob);
                int valorA = valorB - 1;

                double probabilidadA = distribution.cumulativeProbability(valorA);
                BigDecimal probabilidadRedonA = new BigDecimal(probabilidadA).setScale(5, RoundingMode.HALF_UP);
                double probabilidadB = distribution.cumulativeProbability(valorB);
                BigDecimal probabilidadRedonB = new BigDecimal(probabilidadB).setScale(5, RoundingMode.HALF_UP);

                //Actualizar campo texto
                if (valorA >= 0) {
                    labelProb1.setText("P(X≤" + (valorA + r) + ") = " + probabilidadRedonA);
                    labelProb1.setVisible(true);
                }
                labelProb2.setText("P(X≤" + (valorB + r) + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "igual": {
                labelError.setText("Error:");
                textoError.setText("Seleccione P(X≥x) o P(X≤x) ");

                //Actualizar campo texto
                break;
            }
        }
        
        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(r / p).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((r * (1 - p)) / (p * p)).setScale(5, RoundingMode.HALF_UP);
    }

    //Distribución Hipergeométrica
    static void PlotHiper(int n, int N, int N1) {

        ///Crear distribución
        HypergeometricDistribution distribution = new HypergeometricDistribution(N, N1, n);

        // Crear dataset
        int minimo = Math.max(0, n + N1 - N);
        int maximo = Math.min(n, N1);

        XYSeries series = new XYSeries("Hg(" + n + "," + N + "," + N1 + ")");
        for (int x = minimo; x <= maximo; x++) {
            series.add(x, distribution.probability(x));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Hipergeométrica", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        plot.setDataset(1, dataset);
        plot.setRenderer(1, renderer);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        // Modiifcar las barras
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        float nFloat = (float) n;
        float NFloat = (float) N;
        float N1Float = (float) N1;
        esperanzaActual = new BigDecimal(nFloat * (N1Float / NFloat)).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(nFloat * (N1Float / NFloat) * ((NFloat - N1Float) / NFloat) * ((NFloat - nFloat) / (NFloat - 1))).setScale(5, RoundingMode.HALF_UP);

    }

    static void PlotHiperX(int n, int N, int N1, int x, String direccion) {
        ///Crear distribución
        HypergeometricDistribution distribution = new HypergeometricDistribution(N, N1, n);

        // Crear dataset
        int minimo = Math.max(0, n + N1 - N);
        int maximo = Math.min(n, N1);

        XYSeries series = new XYSeries("Hg(" + n + "," + N + "," + N1 + ")");
        for (int i = minimo; i <= maximo; i++) {
            series.add(i, distribution.probability(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Hipergeométrica", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        //Render
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();

        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        plot.setDataset(1, dataset);
        plot.setRenderer(1, renderer);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        int minAxis = minimo;
        int maxAxis = maximo;
        int mediaInt = (int) n * (N1 / N);

        if (distribution.probability(minAxis) <= 0.01) {
            for (int min = mediaInt; distribution.probability(min) >= 0.001; min--) {
                minAxis = min;
            }
        }

        if (distribution.probability(maxAxis) <= 0.01) {
            for (int max = mediaInt; distribution.probability(max) >= 0.001; max++) {
                maxAxis = max;
            }
        }
        xAxis.setRange(minAxis, maxAxis);

        // Modiifcar las barras
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        //Seleccionar según dirección
        switch (direccion) {
            case "mayor": {
                double probabilidad = 1 - distribution.cumulativeProbability(x) + distribution.probability(x);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                for (int i = x; i <= maximo; i++) {
                    series2.add(i, distribution.probability(i));
                }

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                double probabilidad = distribution.cumulativeProbability(x);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                for (int i = minimo; i <= x; i++) {
                    series2.add(i, distribution.probability(i));
                }

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "igual": {
                double probabilidad = distribution.probability(x);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                series2.add(x, distribution.probability(x));

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;

            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        float nFloat = (float) n;
        float NFloat = (float) N;
        float N1Float = (float) N1;
        esperanzaActual = new BigDecimal(nFloat * (N1Float / NFloat)).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(nFloat * (N1Float / NFloat) * ((NFloat - N1Float) / NFloat) * ((NFloat - nFloat) / (NFloat - 1))).setScale(5, RoundingMode.HALF_UP);

    }

    static void PlotHiperP(int n, int N, int N1, double prob, String direccion) {
        ///Crear distribución
        HypergeometricDistribution distribution = new HypergeometricDistribution(N, N1, n);

        // Crear dataset
        int minimo = Math.max(0, n + N1 - N);
        int maximo = Math.min(n, N1);

        XYSeries series = new XYSeries("Hg(" + n + "," + N + "," + N1 + ")");
        for (int x = minimo; x <= maximo; x++) {
            series.add(x, distribution.probability(x));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Hipergeométrica", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        //Render
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();

        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        plot.setDataset(1, dataset);
        plot.setRenderer(1, renderer);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        int minAxis = minimo;
        int maxAxis = maximo;
        int mediaInt = (int) n * (N1 / N);

        if (distribution.probability(minAxis) <= 0.01) {
            for (int min = mediaInt; distribution.probability(min) >= 0.001; min--) {
                minAxis = min;
            }
        }

        if (distribution.probability(maxAxis) <= 0.01) {
            for (int max = mediaInt; distribution.probability(max) >= 0.001; max++) {
                maxAxis = max;
            }
        }
        xAxis.setRange(minAxis, maxAxis);

        // Modiifcar las barras
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        //Seleccionar según dirección
        switch (direccion) {
            case "mayor": {

                int valorA = distribution.inverseCumulativeProbability(1 - prob);
                int valorB = valorA + 1;

                double probabilidadA = 1 - distribution.cumulativeProbability(valorA) + distribution.probability(valorA);
                BigDecimal probabilidadRedonA = new BigDecimal(probabilidadA).setScale(5, RoundingMode.HALF_UP);
                double probabilidadB = 1 - distribution.cumulativeProbability(valorB) + distribution.probability(valorB);
                BigDecimal probabilidadRedonB = new BigDecimal(probabilidadB).setScale(5, RoundingMode.HALF_UP);

                //Actualizar campo texto
                labelProb1.setText("P(X≥" + valorA + ") = " + probabilidadRedonA);
                labelProb1.setVisible(true);
                labelProb2.setText("P(X≥" + valorB + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "menor": {
                int valorB = distribution.inverseCumulativeProbability(prob);
                int valorA = valorB - 1;

                double probabilidadA = distribution.cumulativeProbability(valorA);
                BigDecimal probabilidadRedonA = new BigDecimal(probabilidadA).setScale(5, RoundingMode.HALF_UP);
                double probabilidadB = distribution.cumulativeProbability(valorB);
                BigDecimal probabilidadRedonB = new BigDecimal(probabilidadB).setScale(5, RoundingMode.HALF_UP);

                //Actualizar campo texto
                if (valorA >= 0) {
                    labelProb1.setText("P(X≤" + valorA + ") = " + probabilidadRedonA);
                    labelProb1.setVisible(true);
                }
                labelProb2.setText("P(X≤" + valorB + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "igual": {
                labelError.setText("Error:");
                textoError.setText("Seleccione P(X≥x) o P(X≤x) ");

                //Actualizar campo texto
                break;
            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        float nFloat = (float) n;
        float NFloat = (float) N;
        float N1Float = (float) N1;
        esperanzaActual = new BigDecimal(nFloat * (N1Float / NFloat)).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(nFloat * (N1Float / NFloat) * ((NFloat - N1Float) / NFloat) * ((NFloat - nFloat) / (NFloat - 1))).setScale(5, RoundingMode.HALF_UP);

    }

    //Distribución Uniforme
    static void PlotUnifD(int valorA, int valorB) {

        ///Crear distribución
        UniformIntegerDistribution distribution = new UniformIntegerDistribution(valorA, valorB);

        // Crear dataset
        XYSeries series = new XYSeries("U(" + valorA + "," + valorB + ")");
        for (int i = valorA; i <= valorB; i++) {
            series.add(i, distribution.probability(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Uniforme (discreta)", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        // Modificar las barras
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal((valorA + valorB) / 2).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((Math.pow(valorB - valorA + 1, 2) - 1) / 12).setScale(5, RoundingMode.HALF_UP);

    }

    static void PlotUnifDX(int valorA, int valorB, int valorX, String direccion) {

        ///Crear distribución
        UniformIntegerDistribution distribution = new UniformIntegerDistribution(valorA, valorB);

        // Crear dataset
        XYSeries series = new XYSeries("U(" + valorA + "," + valorB + ")");
        for (int i = valorA; i <= valorB; i++) {
            series.add(i, distribution.probability(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Uniforme (discreta)", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        plot.setDataset(1, dataset);
        plot.setRenderer(1, renderer);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        // Modificar las barras
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        //Seleccionar según dirección
        switch (direccion) {
            case "mayor": {
                double probabilidad = 1 - distribution.cumulativeProbability(valorX) + distribution.probability(valorX);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + valorX + ")");

                for (int i = valorX; i <= valorB; i++) {
                    series2.add(i, distribution.probability(i));
                }
                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                double probabilidad = distribution.cumulativeProbability(valorX);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + valorX + ")");

                for (int i = valorA; i <= valorX; i++) {
                    series2.add(i, distribution.probability(i));
                }

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "igual": {
                double probabilidad = distribution.probability(valorX);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + valorX + ")");

                series2.add(valorX, distribution.probability(valorX));

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;

            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal((valorA + valorB) / 2).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((Math.pow(valorB - valorA + 1, 2) - 1) / 12).setScale(5, RoundingMode.HALF_UP);

    }

    static void PlotUnifDP(int valorA, int valorB, double prob, String direccion) {
        ///Crear distribución
        UniformIntegerDistribution distribution = new UniformIntegerDistribution(valorA, valorB);

        // Crear dataset
        XYSeries series = new XYSeries("U(" + valorA + "," + valorB + ")");
        for (int i = valorA; i <= valorB; i++) {
            series.add(i, distribution.probability(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Uniforme (discreta)", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        // Modificar las barras
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        //Seleccionar según dirección
        switch (direccion) {
            case "mayor": {

                int valorBajo = distribution.inverseCumulativeProbability(1 - prob);
                int valorAlto = valorBajo + 1;

                double probabilidadA = 1 - distribution.cumulativeProbability(valorBajo) + distribution.probability(valorBajo);
                BigDecimal probabilidadRedonA = new BigDecimal(probabilidadA).setScale(5, RoundingMode.HALF_UP);
                double probabilidadB = 1 - distribution.cumulativeProbability(valorAlto) + distribution.probability(valorAlto);
                BigDecimal probabilidadRedonB = new BigDecimal(probabilidadB).setScale(5, RoundingMode.HALF_UP);

                //Actualizar campo texto
                labelProb1.setText("P(X≥" + valorBajo + ") = " + probabilidadRedonA);
                labelProb1.setVisible(true);
                labelProb2.setText("P(X≥" + valorAlto + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "menor": {
                int valorAlto = distribution.inverseCumulativeProbability(prob);
                int valorBajo = valorAlto - 1;

                double probabilidadA = distribution.cumulativeProbability(valorBajo);
                BigDecimal probabilidadRedonA = new BigDecimal(probabilidadA).setScale(5, RoundingMode.HALF_UP);
                double probabilidadB = distribution.cumulativeProbability(valorAlto);
                BigDecimal probabilidadRedonB = new BigDecimal(probabilidadB).setScale(5, RoundingMode.HALF_UP);

                //Actualizar campo texto
                labelProb1.setText("P(X≤" + valorBajo + ") = " + probabilidadRedonA);
                labelProb1.setVisible(true);
                labelProb2.setText("P(X≤" + valorAlto + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "igual": {
                labelError.setText("Error:");
                textoError.setText("Seleccione P(X≥x) o P(X≤x) ");

                //Actualizar campo texto
                break;
            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal((valorA + valorB) / 2).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal((Math.pow(valorB - valorA + 1, 2) - 1) / 12).setScale(5, RoundingMode.HALF_UP);
    }

    //Distribución Poisson
    static void PlotPois(double lambda) {

        //Crear distribución
        PoissonDistribution distribution = new PoissonDistribution(lambda);

        // Crear dataset
        XYSeries series = new XYSeries("Pois(" + lambda + ")");
        for (int i = 0; i <= (2 * lambda + 6); i++) {
            series.add(i, distribution.probability(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Poisson", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        int minAxis = 0;
        int maxAxis = (int) (2 * lambda + 6);

        if (distribution.probability(0) <= 0.01) {
            for (int min = 0; distribution.probability(min) <= 0.0001; min++) {
                minAxis = min;
            }
        }

        if (distribution.probability(maxAxis) <= 0.01) {
            for (int max = maxAxis; distribution.probability(max) <= 0.0001; max--) {
                maxAxis = max;
            }

        }
        xAxis.setRange(minAxis, maxAxis);

        System.out.print(minAxis);
        System.out.print(maxAxis);

        // Modificar las barras
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(lambda).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(lambda).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotPoisX(double lambda, int x, String direccion) {

        //Crear distribución
        PoissonDistribution distribution = new PoissonDistribution(lambda);

        // Crear dataset
        XYSeries series = new XYSeries("Pois(" + lambda + ")");
        for (int i = 0; i <= (2 * lambda + 6); i++) {
            series.add(i, distribution.probability(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Poisson", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        //Render
        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();

        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        plot.setDataset(1, dataset);
        plot.setRenderer(1, renderer);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        int minAxis = 0;
        int maxAxis = (int) (2 * lambda + 6);

        if (distribution.probability(0) <= 0.01) {
            for (int min = 0; distribution.probability(min) <= 0.0001; min++) {
                minAxis = min;
            }
        }

        if (distribution.probability(maxAxis) <= 0.01) {
            for (int max = maxAxis; distribution.probability(max) <= 0.0001; max--) {
                maxAxis = max;
            }

        }
        xAxis.setRange(minAxis, maxAxis);

        //Seleccionar según dirección
        switch (direccion) {
            case "mayor": {
                double probabilidad = 1 - distribution.cumulativeProbability(x) + distribution.probability(x);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                for (int i = x; i <= (2 * lambda + 6); i++) {
                    series2.add(i, distribution.probability(i));
                }

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "menor": {
                double probabilidad = distribution.cumulativeProbability(x);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                for (int i = 0; i <= x; i++) {
                    series2.add(i, distribution.probability(i));
                }

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;
            }
            case "igual": {
                double probabilidad = distribution.probability(x);
                BigDecimal probabilidadRedon = new BigDecimal(probabilidad).setScale(5, RoundingMode.HALF_UP);

                //Segundo render
                XYBarRenderer renderer2 = new XYBarRenderer();
                renderer2.setShadowVisible(false);
                renderer2.setDrawBarOutline(true);
                renderer2.setSeriesPaint(0, Color.RED);
                renderer2.setSeriesOutlinePaint(0, Color.BLACK);
                renderer2.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

                XYSeries series2 = new XYSeries("P(X=" + x + ")");

                series2.add(x, distribution.probability(x));

                XYSeriesCollection dataset2 = new XYSeriesCollection(series2);
                plot.setDataset(0, dataset2);
                plot.setRenderer(0, renderer2);

                plot.getRenderer().setSeriesVisibleInLegend(0, Boolean.FALSE, true);

                //Actualizar campo texto
                inputProb.setText(probabilidadRedon.toString());
                break;

            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(lambda).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(lambda).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotPoisP(double lambda, double prob, String direccion) {

        ///Crear distribución
        PoissonDistribution distribution = new PoissonDistribution(lambda);

        // Crear dataset
        XYSeries series = new XYSeries("Pois(" + lambda + ")");
        for (int i = 0; i <= (2 * lambda + 6); i++) {
            series.add(i, distribution.probability(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Distribución Poisson", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Modificar eje X
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        xAxis.setTickUnit(new NumberTickUnit(1.0));

        int minAxis = 0;
        int maxAxis = (int) (2 * lambda + 6);

        if (distribution.probability(0) <= 0.01) {
            for (int min = 0; distribution.probability(min) <= 0.0001; min++) {
                minAxis = min;
            }
        }

        if (distribution.probability(maxAxis) <= 0.01) {
            for (int max = maxAxis; distribution.probability(max) <= 0.0001; max--) {
                maxAxis = max;
            }

        }
        xAxis.setRange(minAxis, maxAxis);

        // Modificar las barras
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        //Seleccionar según dirección
        switch (direccion) {
            case "mayor": {

                int valorA = distribution.inverseCumulativeProbability(1 - prob);
                int valorB = valorA + 1;

                double probabilidadA = 1 - distribution.cumulativeProbability(valorA) + distribution.probability(valorA);
                BigDecimal probabilidadRedonA = new BigDecimal(probabilidadA).setScale(5, RoundingMode.HALF_UP);
                double probabilidadB = 1 - distribution.cumulativeProbability(valorB) + distribution.probability(valorB);
                BigDecimal probabilidadRedonB = new BigDecimal(probabilidadB).setScale(5, RoundingMode.HALF_UP);

                //Actualizar campo texto
                labelProb1.setText("P(X≥" + valorA + ") = " + probabilidadRedonA);
                labelProb1.setVisible(true);
                labelProb2.setText("P(X≥" + valorB + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "menor": {
                int valorB = distribution.inverseCumulativeProbability(prob);
                int valorA = valorB - 1;

                double probabilidadA = distribution.cumulativeProbability(valorA);
                BigDecimal probabilidadRedonA = new BigDecimal(probabilidadA).setScale(5, RoundingMode.HALF_UP);
                double probabilidadB = distribution.cumulativeProbability(valorB);
                BigDecimal probabilidadRedonB = new BigDecimal(probabilidadB).setScale(5, RoundingMode.HALF_UP);

                //Actualizar campo texto
                if (valorA >= 0) {
                    labelProb1.setText("P(X≤" + valorA + ") = " + probabilidadRedonA);
                    labelProb1.setVisible(true);
                }
                labelProb2.setText("P(X≤" + valorB + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "igual": {
                labelError.setText("Error:");
                textoError.setText("Seleccione P(X≥x) o P(X≤x) ");

                //Actualizar campo texto
                break;
            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);

        // Actualizar momentos
        esperanzaActual = new BigDecimal(lambda).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(lambda).setScale(5, RoundingMode.HALF_UP);
    }

}
