package com.gjun.domain;

public class TWHello implements IHello{

	@Override
	public String sayHello(String who) {
		// TODO Auto-generated method stub
		return String.format("%s 吃飽沒??", who);
	}

}
