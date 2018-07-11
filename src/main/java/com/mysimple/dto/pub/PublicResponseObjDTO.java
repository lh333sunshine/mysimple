package com.mysimple.dto.pub;

import java.util.List;

public class PublicResponseObjDTO<T> extends PublicResponseDTO{

	private T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
	public PublicResponseObjDTO(T data){
		this.data = data;
	}
	
	public PublicResponseObjDTO() {
		
	}
}
