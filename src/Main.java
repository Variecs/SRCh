import javax.swing.JFrame;

import org.jfree.chart.*;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.*;

public class Main {

	/**
	 * @param args
	 */
	public static int LENGTH = 1000;

	public static void main(String[] args) {
		RandomGenerator rg = new RandomGenerator(1, 2, 1000, 16);
		RandomGenerator rg1 = new RandomGenerator(1, 2, 1000, 16);
		XYSeriesCollection sc = new XYSeriesCollection();
		XYSeries sm;
		XYSeries sd;
		double[] r = new double[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			r[i] = rg.getNext();
		}
		double[] r1 = new double[LENGTH];
		for (int i = 0; i < LENGTH; i++) {
			r1[i] = rg1.getNext();
		}
		
		// sm = mean(r);
		// sd = variance(r);
//		 sc.addSeries(rg.data);
		// sc.addSeries(sm);
		// sc.addSeries(sd);
		sc.addSeries(getAutoCorelation(rg.data,mean(r),variance(r),"Autocorelation"));
		sc.addSeries(getCorelation(rg.data,rg.data, mean(r),mean(r1),variance(r),variance(r1),"Corelation"));
		JFreeChart chart = ChartFactory.createXYLineChart(
				"Random numbers distribution", "t", "x", sc,
				PlotOrientation.VERTICAL, false, false, false);
		JFrame frame = new JFrame("Lab1 SRCh");
		frame.getContentPane().add(new ChartPanel(chart));
		frame.setSize(800, 500);
		frame.setVisible(true);
	}

	public static XYSeries mean(double[] random) {
		double temp = 0;
		XYSeries res = new XYSeries("Mean");
		for (int i = 0; i < random.length; i++) {
			temp += random[i];
			res.add(i, temp / (i + 1));
		}
		System.out.println("mean = " + (temp / random.length));
		return res;
	}

	public static XYSeries variance(double[] random) {
		double temp = 0;
		double mean = 0;
		for (int i = 0; i < random.length; i++) {
			mean += random[i];
		}
		mean /= random.length;
		XYSeries res = new XYSeries("Variance");
		for (int i = 0; i < random.length; i++) {
			temp += Math.pow(random[i] - mean, 2);
			res.add(i, temp / (i + 1));
		}
		System.out.println("variance = " + (temp / random.length));
		return res;
	}

	static XYSeries getCorelation(XYSeries x, XYSeries y, XYSeries Mx,XYSeries My,XYSeries Dx,XYSeries Dy,String name) {
		double sum = 0;
		XYSeries res = new XYSeries(name);
		for (int tau = 0; tau < 1000; tau++) {
			for (int i = 0; i < x.getItemCount() - tau; i++) {
				sum += (x.getDataItem(i).getYValue() - Mx.getDataItem(Mx.getItemCount()-1).getYValue())
						* (y.getDataItem(i + tau).getYValue() - My.getDataItem(My.getItemCount()-1).getYValue())
						/(Dx.getDataItem(Dx.getItemCount()-1).getYValue()*Dy.getDataItem(Dy.getItemCount()-1).getYValue());
			}
			System.out.println(sum / (x.getItemCount() - 1));
			res.add(tau, sum / (x.getItemCount() - 1));
			sum = 0;
		}
		return res;// sum/(x.length-1);
	}

	static XYSeries getAutoCorelation(XYSeries x, XYSeries Mx, XYSeries Dx,String name) {
		return getCorelation(x, x, Mx, Mx, Dx, Dx,name);
	}

}
