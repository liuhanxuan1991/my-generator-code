package com.lhx.study.mygeneratorcode.vo;


import com.lhx.study.mygeneratorcode.constant.StatusEnum;
import lombok.Data;

@Data
public class ReturnResult<T> {
	// 0代表查询成功
	private int code;
	private String msg;
	private T data;
	private long count;

	public ReturnResult() {
		this.code = StatusEnum.OK.getCode();
		this.msg = StatusEnum.OK.getValue();
	}
	public ReturnResult(StatusEnum statusEnum){
		this.code = statusEnum.getCode();
		this.msg = statusEnum.getValue();
	}

	public ReturnResult(StatusEnum statusEnum,String msg){
		this.code = statusEnum.getCode();
		this.msg = msg;
	}

	public ReturnResult(int code, String msg, T data, long count) {
		this.code = code;
		this.msg = msg;
		this.data = data;
		this.count = count;
	}

	public ReturnResult(StatusEnum statusEnum, T data, long count){
		this.code = statusEnum.getCode();
		this.msg = statusEnum.getValue();
		this.data = data;
		this.count = count;
	}


}
