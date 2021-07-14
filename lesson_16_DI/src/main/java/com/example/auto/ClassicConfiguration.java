package com.example.auto;

//Strong Dependency
//public class ClassicConfiguration {
//    public String makeConfiguration() {
//        return "Classic";
//    }
//}

//Interface Mode
public class ClassicConfiguration implements Configurable {
    @Override
    public String makeConfiguration() {
        return "Classic";
    }
}