package com.xuecheng.base.execption;
/*
* 统一异常类
* */
public class XuechengPlusException extends RuntimeException {
    private static final long serialVersionUID = 5565760508056698922L;

    private String errMessage;

    public XuechengPlusException() {
        super();
    }

    public XuechengPlusException(String errMessgae) {
        super(errMessgae);
        this.errMessage = errMessgae;
    }

    public String getErrMessage() {
        return this.errMessage;
    }

    public static void cast(CommonError commonError) {
        throw new XuechengPlusException(commonError.getErrMessage());
    }

    public static void cast(String errMessage){
        throw new XuechengPlusException(errMessage);
    }

}
