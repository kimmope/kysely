package com.eduix.spring.demo.beans;

public class AutowireBean {

	private SimpleBean simpleBean;
	private ListBean listBean;

	public SimpleBean getSimpleBean() {
		return simpleBean;
	}

	public void setSimpleBean(SimpleBean simpleBean) {
		this.simpleBean = simpleBean;
	}

	public ListBean getListBean() {
		return listBean;
	}

	public void setListBean(ListBean listBean) {
		this.listBean = listBean;
	}

	@Override
	public String toString() {
		return super.toString() + "(" + simpleBean.toString() + ", " + listBean.toString() + ")";
	}
	
	

}
