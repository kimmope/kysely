package com.eduix.spring.demo.beans;

public class DependentBean {

	private SimpleBean beanOne;
	private SimpleBean beanTwo;
	private SimpleBean beanThree;

	public DependentBean() {
	}

	public DependentBean(SimpleBean beanOne, SimpleBean beanTwo, SimpleBean beanThree) {
		this.beanOne = beanOne;
		this.beanTwo = beanTwo;
		this.beanThree = beanThree;
	}

	public SimpleBean getBeanOne() {
		return beanOne;
	}

	public void setBeanOne(SimpleBean beanOne) {
		this.beanOne = beanOne;
	}

	public SimpleBean getBeanTwo() {
		return beanTwo;
	}

	public void setBeanTwo(SimpleBean beanTwo) {
		this.beanTwo = beanTwo;
	}

	public SimpleBean getBeanThree() {
		return beanThree;
	}

	public void setBeanThree(SimpleBean beanThree) {
		this.beanThree = beanThree;
	}
	
	public String getData() {
		return String.join(", ", beanOne.getData(), beanTwo.getData(), beanThree.getData());
	}

}
