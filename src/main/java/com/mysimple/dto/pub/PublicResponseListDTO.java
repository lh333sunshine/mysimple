package com.mysimple.dto.pub;

import java.util.ArrayList;
import java.util.List;

public class PublicResponseListDTO<T> extends PublicResponseDTO{

	private List<T> data = new ArrayList<T>();

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
	public PublicResponseListDTO(List<T> data){
		this.data = data;
	}
}
