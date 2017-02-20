/*
 * Created on Jul 1, 2007
 *
 * boba
 */
package ru.scriptum.model.service.impl;

import java.util.Random;

import ru.scriptum.model.service.IRandomService;

public class RandomService implements IRandomService {
	
	private Random random = new Random();

	public double nextDouble() {
		return random.nextDouble();
	}
}
