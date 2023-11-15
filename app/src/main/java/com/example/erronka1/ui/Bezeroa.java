package com.example.erronka1.ui;

public class Bezeroa {
    private String name;
    private String email;
    private String mobile;
    private String commercialCompanyName;

    public Bezeroa(String name, String email, String mobile, String commercialCompanyName) {
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.commercialCompanyName = commercialCompanyName;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public String getCommercialCompanyName() {
        return commercialCompanyName;
    }
}

