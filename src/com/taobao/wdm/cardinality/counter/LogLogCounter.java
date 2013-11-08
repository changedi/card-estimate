/**
 * 
 */
package com.taobao.wdm.cardinality.counter;

import java.util.HashMap;
import java.util.Map;

import com.taobao.wdm.cardinality.hash.MurmurHash2;
import com.taobao.wdm.cardinality.util.CounterUtil;

/**
 * @author zunyuan.jy
 * 
 * @since 2013-11-7
 */
public class LogLogCounter extends AbstractCounter {
	private Map<String, Integer> map;// bucket map
	private int buckets;
	// gamma func value for 32,64,128,256,512,1024
	public static final Map<Integer, Double> gamma = new HashMap<Integer, Double>();
	// alpha to refine estimate
	public static final Map<Integer, Double> alphaM = new HashMap<Integer, Double>();
	static {
		gamma.put(32, -32.6090);
		gamma.put(64, -64.5929);
		gamma.put(128, -128.5850);
		gamma.put(256, -256.5811);
		gamma.put(512, -512.5792);
		gamma.put(1024, -1024.5781);
	}

	public LogLogCounter(int n) {
		super();
		this.buckets = n;
		map = new HashMap<String, Integer>(n);// n buckets
		// calc alphaM
		double alpham = Math.pow(gamma.get(n)
				* ((1 - Math.pow(2, 1.0 /n)) / Math.log(2)), -n);
		//System.out.println(alpham);
		alphaM.put(n, alpham);
	}

	public long estimate(String uid) {
		int hash = MurmurHash2.hash(uid.getBytes(), 100);// hash
		double m = (double) buckets;
		// bucket index =log2(bucket)
		int k = (int) (Math.log(m) / Math.log(2));
		// put hash to right bucket
		CounterUtil.putToBucket(map, hash, k);
		double sum = 0;
		for (String key : map.keySet()) {
			sum += map.get(key);
		}
		cardinality = (long) (Math.pow(2, sum / m) * alphaM.get(buckets));
		return cardinality;
	}
}
