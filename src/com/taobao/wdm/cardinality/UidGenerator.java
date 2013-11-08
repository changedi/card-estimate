/**
 * 
 */
package com.taobao.wdm.cardinality;

import java.util.Random;

/**
 * @author zunyuan.jy
 *
 * @since 2013-11-7
 */
public class UidGenerator {

	public static String generateUid(int n){
		Random rand = new Random();
		return String.valueOf(rand.nextInt(n));
	}
}
