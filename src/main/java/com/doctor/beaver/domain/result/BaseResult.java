package com.doctor.beaver.domain.result;

import java.io.Serializable;

/**
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月29日
 *         <p>
 *         统一接口/业务调用返回值封装
 */
public class BaseResult implements Serializable {
    private static final long serialVersionUID = -1368034192380436864L;
    /**
     * 0 代表成功,其余自定义错误码
     */
    private String            returnCode       = "0";
    private String            returnMsg        = "成功!";

    /**
     * 出错时的重要输入参数,可供调用方查看
     */
    private String            inputParamWhereFalse;

    public BaseResult() {
    }

    @SuppressWarnings("unchecked")
    public <SubClass extends BaseResult> SubClass withReturnCodeAndReturnMsg(String returnCode, String returnMsg) {
        this.returnCode = returnCode;
        this.returnMsg = returnMsg;
        return (SubClass) this;
    }

    /**
     * @return {@code boolean} <br>
     *         false:业务请求没通过或发生系统异常<br>
     *         true:表示肯定没发生系统异常，但业务请求的成功与否还看具体接口定义/javadoc说明，或更多的字段
     */
    public boolean isSuccess() {
        return "0".equals(returnCode) ? true : false;
    }

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getInputParamWhereFalse() {
        return inputParamWhereFalse;
    }

    public void setInputParamWhereFalse(String inputParamWhereFalse) {
        this.inputParamWhereFalse = inputParamWhereFalse;
    }

    @Override
    public String toString() {
        return "BaseResult [returnCode=" + returnCode + ", returnMsg=" + returnMsg + ", inputParamWhereFalse="
                + inputParamWhereFalse + "]";
    }

}
