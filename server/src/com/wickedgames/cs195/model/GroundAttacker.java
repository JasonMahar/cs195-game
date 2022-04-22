package com.wickedgames.cs195.model;




// M3 STRATEGY PATTERN
//
public class GroundAttacker implements Attacker {

	public GroundAttacker() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void attack(long timeElapsed) {
		System.out.println("GroundAttacker.attack()");
		// do GroundAttacker behavior - try to ground attack

	}

}
