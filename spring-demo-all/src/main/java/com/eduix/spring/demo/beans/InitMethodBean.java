package com.eduix.spring.demo.beans;

import java.util.ArrayList;
import java.util.List;

public class InitMethodBean extends ListBean {

	public void initList() {
		List list = new ArrayList();
		for (int i = 1; i <= 20; i++) {
			list.add(i);
		}
		this.setData(list);
	}
	
}
