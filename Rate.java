package com.swufe.rate;

public class Rate {
    private String cname;
    private String cval;

    public Rate(String cname,String cval){
        this.cname = cname;
        this.cval = cval;

    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public void setCval(String cval) {
        this.cval = cval;
    }

    public String getCval() {
        return cval;
    }
}
