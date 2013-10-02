import java.util.Random;
import org.jfree.data.xy.XYSeries;

public class RandomGenerator {

	public XYSeries data = new XYSeries("Pseudorandom numbers");
	
	private double dt;
	private double[] a;
	private double[] w;
	private double[] f;
	private int n;
	private double t = 0;
	
	public RandomGenerator(double dt, double amax, double wmax, int n) {
		Random r = new Random();
		r.setSeed(System.currentTimeMillis());
		this.dt = dt;
		this.a = new double[n];
		this.w = new double[n];
		this.f = new double[n];
		this.n = n;
		for (int i = 0; i <  n; i++) {
			a[i] = r.nextDouble()*amax;
			w[i] = r.nextDouble()*wmax;
			f[i] = r.nextDouble()*2*Math.PI;
		}
	}
	
	public double getNext() {
		double res = 0;
		for (int i = 0; i < n; i++) {
			res += a[i]*Math.sin(w[i]*t + f[i]);
		}
		data.add(t, res);
		t += dt;
		return res;
	}
}
