package com.distribucion.distribucionesprograma;

import java.awt.BasicStroke;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.apache.commons.math3.distribution.BinomialDistribution;
import org.apache.commons.math3.distribution.ExponentialDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
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
import org.jfree.data.function.Function2D;
import org.jfree.data.general.DatasetUtils;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author santi
 */
public class Graficos {

    public static JTextField inputX, inputProb;
    public static JLabel labelError, textoError, labelProb1, labelProb2;
    public static ChartPanel graficoDist;
    public static BigDecimal esperanzaActual, varianciaActual;
    ///////////////
    ////Gráficos///
    //////////////

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
        esperanzaActual = new BigDecimal(1/lambda).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(1 / (lambda*lambda)).setScale(5, RoundingMode.HALF_UP);
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
        esperanzaActual = new BigDecimal(1/lambda).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(1 / (lambda*lambda)).setScale(5, RoundingMode.HALF_UP);
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
        esperanzaActual = new BigDecimal(1/lambda).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(1 / (lambda*lambda)).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotBinom(int n, double p) {

        ///Crear distribución
        BinomialDistribution distribution = new BinomialDistribution(n, p);

        // Crear dataset
        XYSeries series = new XYSeries("Bin(" + n + "," + p + ")");
        for (int x = 0; x <= n; x++) {
            series.add(x, distribution.probability(x));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Binomial Distribution", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Customize the x-axis
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

        // Customize the bars
        renderer.setSeriesPaint(0, Color.BLUE);
        renderer.setSeriesOutlinePaint(0, Color.BLACK);
        renderer.setSeriesOutlineStroke(0, new BasicStroke(1.5f));

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);
        
        // Actualizar momentos
        esperanzaActual = new BigDecimal(n * p).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(n * p * (1-p)).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotBinomX(int n, double p, int x, String direccion) {

        ///Crear distribución
        BinomialDistribution distribution = new BinomialDistribution(n, p);

        // Crear dataset
        XYSeries series = new XYSeries("Bin(" + n + "," + p + ")");
        for (int i = 0; i <= n; i++) {
            series.add(i, distribution.probability(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Binomial Distribution", "x", false, "P(X=x)", dataset,
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

        // Customize the x-axis
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
        varianciaActual = new BigDecimal(n * p * (1-p)).setScale(5, RoundingMode.HALF_UP);
    }

    static void PlotBinomP(int n, double p, double prob, String direccion) {

        ///Crear distribución
        BinomialDistribution distribution = new BinomialDistribution(n, p);

        // Crear dataset
        XYSeries series = new XYSeries("Bin(" + n + "," + p + ")");
        for (int i = 0; i <= n; i++) {
            series.add(i, distribution.probability(i));
        }
        XYSeriesCollection dataset = new XYSeriesCollection(series);

        //Crear gráfico
        ChartFactory.setChartTheme(StandardChartTheme.createLegacyTheme());
        JFreeChart chart = ChartFactory.createXYBarChart(
                "Binomial Distribution", "x", false, "P(X=x)", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        //Estilo del gráfico
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);

        XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
        renderer.setShadowVisible(false);
        renderer.setDrawBarOutline(true);

        // Customize the x-axis
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

        // Customize the bars
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
                labelProb1.setText("P(X>=" + valorA + ") = " + probabilidadRedonA);
                labelProb1.setVisible(true);
                labelProb2.setText("P(X>=" + valorB + ") = " + probabilidadRedonB);
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
                labelProb1.setText("P(X<=" + valorA + ") = " + probabilidadRedonA);
                labelProb1.setVisible(true);
                labelProb2.setText("P(X<=" + valorB + ") = " + probabilidadRedonB);
                labelProb2.setVisible(true);
                break;
            }
            case "igual": {
                labelError.setText("Error:");
                textoError.setText("Seleccione P(X>=x) o P(X<=x) ");

                //Actualizar campo texto
                break;
            }
        }

        // Actualizar gráfico
        graficoDist.setVisible(true);
        graficoDist.setChart(chart);
        
        // Actualizar momentos
        esperanzaActual = new BigDecimal(n * p).setScale(5, RoundingMode.HALF_UP);
        varianciaActual = new BigDecimal(n * p * (1-p)).setScale(5, RoundingMode.HALF_UP);
    }
}
