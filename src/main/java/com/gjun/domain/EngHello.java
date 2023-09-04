package com.gjun.domain;

public class EngHello implements IHello{

	@Override
	public String sayHello(String who) {
		// TODO Auto-generated method stub
		return String.format("%s Hello World!!!", who);
	}

}
