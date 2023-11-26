package com.xuecheng.base.execption;
/*
* 统一异常类
* */
public class XueChengPlusException extends RuntimeException {
    private static final long serialVersionUID = 5565760508056698922L;

    private String errMessage;

    public XueChengPlusException() {
        super();
    }

    public XueChengPlusException(String errMessgae) {
        super(errMessgae);
        this.errMessage = errMessgae;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public static void cast(CommonError commonError) {
        throw new XueChengPlusException(commonError.getErrMessage());
    }

    public static void cast(String errMessage){
        throw new XueChengPlusException(errMessage);
    }

}
