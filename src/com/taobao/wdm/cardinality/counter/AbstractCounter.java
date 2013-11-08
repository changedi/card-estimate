/**
 * 
 */
package com.taobao.wdm.cardinality.counter;

/**
 * @author zunyuan.jy
 * 
 * @since 2013-11-8
 */
public abstract class AbstractCounter {
	protected long cardinality = 0;

	/**
	 * @return the cardinality
	 */
	public long getCardinality() {
		return cardinality;
	}

	public abstract long estimate(String uid);

}
