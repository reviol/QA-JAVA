package com.example.auto;

//Strong Dependency
//public class SportConfiguration {
//    public String makeConfiguration() {
//    return "Sport";
//    }
//}

//Interface Mode
public class SportConfiguration implements Configurable {
    @Override
    public String makeConfiguration() {
        return "Sport";
    }
}