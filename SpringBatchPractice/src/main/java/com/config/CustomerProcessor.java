package com.config;

import org.springframework.batch.item.ItemProcessor;

import com.entity.Customer;

public class CustomerProcessor implements ItemProcessor<Customer, Customer> {
	
	@Override
	public Customer process(Customer item) throws Exception {
	
//		if(item.getCountry().equalsIgnoreCase("united states")) {
//		return item;
//		}
//		else {
//			return null;
//		}
		 
	
	return item;
	}

}
