package com.mdm.common;

public class AjaxResponse extends AbstractBean {
    private Integer status;
    private String message;
    private Object result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public AjaxResponse() {

    }

    public static AjaxResponse reponseBody(Integer status, String message, Object result) {
        AjaxResponse ajaxResponse = new AjaxResponse();
        ajaxResponse.setStatus(status);
        ajaxResponse.setMessage(message);
        ajaxResponse.setResult(result);
        return ajaxResponse;
    }
}
