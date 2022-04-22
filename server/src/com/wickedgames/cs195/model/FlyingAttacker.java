package com.wickedgames.cs195.model;




// M3 STRATEGY PATTERN
//
public class FlyingAttacker implements Attacker {

	public FlyingAttacker() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void attack(long timeElapsed) {

		System.out.println("FlyingAttacker.attack()");
		// do FlyingMonster behavior - try to ranged attack

	}

}
