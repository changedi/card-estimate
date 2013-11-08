/**
 * 
 */
package com.taobao.wdm.cardinality.counter;

import com.taobao.wdm.cardinality.bloom.ByteBloomFilter;

/**
 * @author zunyuan.jy
 *
 * @since 2013-11-7
 */
public class BbfCounter extends AbstractCounter{
	
	private ByteBloomFilter bbf;

	public BbfCounter(int allocSize, double errorRate) {
		super();
		this.bbf = new ByteBloomFilter(allocSize, errorRate, 1, 0);
		this.bbf.allocBloom();
	}
	
	public long estimate(String uid){
		if (!bbf.contains(uid.getBytes())){
			bbf.add(uid.getBytes());
			this.cardinality++;
		}
		return cardinality;
	}
	
	
}
