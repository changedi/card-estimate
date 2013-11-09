/**
 * 
 */
package com.taobao.wdm.cardinality;

import java.util.HashMap;
import java.util.Map;

import com.taobao.wdm.cardinality.counter.BbfCounter;
import com.taobao.wdm.cardinality.counter.FastHyperLogLogCounter;
import com.taobao.wdm.cardinality.counter.HyperLogLogCounter;
import com.taobao.wdm.cardinality.counter.LogLogCounter;

/**
 * @author zunyuan.jy
 *
 * @since 2013-11-7
 */
public class Main {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int n = 100000;
		int pv = 500000;
		double errorRate = 0.000001;
		long start = System.currentTimeMillis();
		Map<String,Boolean> map = new HashMap<String,Boolean>();
		BbfCounter bc = new BbfCounter(n, errorRate);
		LogLogCounter llc = new LogLogCounter(512);
		HyperLogLogCounter hllc = new HyperLogLogCounter(1024);
		FastHyperLogLogCounter fhllc = new FastHyperLogLogCounter(1024);
		for(int i=0;i<pv;i++){
			String uid = UidGenerator.generateUid(n);
			map.put(uid, true);
			//bc.estimate(uid);
			//llc.estimate(uid);
			hllc.estimate(uid);
			fhllc.estimate(uid);
		}
		System.out.println("actual:"+map.size());
		System.out.println("expect:");
		System.out.println("bbf counter:"+bc.getCardinality());
		System.out.println("llc counter:"+llc.getCardinality());
		System.out.println("hllc counter:"+hllc.getCardinality());
		System.out.println("fhllc counter:"+fhllc.getCardinality());
		System.out.println("time usage:"+(System.currentTimeMillis()-start)/1000+"s");
	}

}
