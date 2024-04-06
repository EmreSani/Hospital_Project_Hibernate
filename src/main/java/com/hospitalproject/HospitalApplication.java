package com.hospitalproject;
import java.io.IOException;
import static com.hospitalproject.service.HospitalService.hospitalService;


public class HospitalApplication {
    public static void main(String[] args) throws IOException, InterruptedException {

        hospitalService.start();

    }
}
