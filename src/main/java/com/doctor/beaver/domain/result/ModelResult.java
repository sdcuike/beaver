package com.doctor.beaver.domain.result;

/**
 * 接口返回带数据的返回值，如果接口调用成功，success为true而data可以为null或非null，业务的成功与否和T关系密切。
 * 
 * @author sdcuike
 *         <p>
 *         Created on 2016年9月29日
 *         <p>
 */
public final class ModelResult<T> extends BaseResult {

    private static final long serialVersionUID = -1645782922380055867L;

    /**
     * success为true时，data可能为null
     */
    private T                 data;
    private boolean           readFromCache    = false;

    public ModelResult() {
    }

    public ModelResult<T> withModel(T model) {
        data = model;
        return this;
    }

    public ModelResult<T> withModelFromCache(T model) {
        data = model;
        readFromCache = true;
        return this;
    }

    public ModelResult<T> withModelFromDb(T model) {
        data = model;
        return this;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isReadFromCache() {
        return readFromCache;
    }

    public void setReadFromCache(boolean readFromCache) {
        this.readFromCache = readFromCache;
    }

    @Override
    public String toString() {
        return "ModelResult [data=" + data + ", readFromCache=" + readFromCache
                + ", returnCode=" + getReturnCode() + ", returnMsg=" + getReturnMsg()
                + ", inputParamWhereFalse=" + getInputParamWhereFalse() + "]";
    }

    public static void main(String[] args) {
        ModelResult<Object> modelResult = new ModelResult<>();
        modelResult.withReturnCodeAndReturnMsg("", "");
    }
}
