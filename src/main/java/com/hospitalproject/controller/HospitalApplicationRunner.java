package com.hospitalproject.controller;
import java.io.IOException;
import static com.hospitalproject.controller.HospitalManagementSystem.hospitalManagementSystem;


public class HospitalApplicationRunner {
    public static void main(String[] args) throws IOException, InterruptedException {

        HospitalManagementSystem.start();

    }
}
