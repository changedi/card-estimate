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
 * @since 2013-11-8
 */
public class FastHyperLogLogCounter extends AbstractCounter {
	private Map<Integer, Integer> map;// ��Ͱͳ��
	private int buckets;
	public static final Map<Integer, Double> alphaM = new HashMap<Integer, Double>();

	static {
		alphaM.put(32, 0.6971226338);
		alphaM.put(64, 0.7092084529);
		alphaM.put(128, 0.71527119);
		alphaM.put(256, 0.7183076382);
		alphaM.put(512, 0.7198271478);
		alphaM.put(1024, 0.720587226);
	}

	public FastHyperLogLogCounter(int n) {
		map = new HashMap<Integer, Integer>(n);// 32��Ͱ
		cardinality = 0;
		buckets = n;
	}

	public long estimate(String uid) {
		int hash = MurmurHash2.hash(uid.getBytes(), 100);// hash���ֵ
		double m = (double) buckets;// ��Ͱ��
		int k = (int) (Math.log(m) / Math.log(2));
		CounterUtil.putToBucketFast(map, hash, k);
		double sum = 0;
		for (Integer key : map.keySet()) {
			sum += Math.pow(2, -map.get(key));
		}
		cardinality = (long) (alphaM.get(buckets) * m * m / sum);
		return cardinality;
	}
}
