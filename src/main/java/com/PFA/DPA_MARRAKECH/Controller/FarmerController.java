package com.PFA.DPA_MARRAKECH.Controller;



import com.PFA.DPA_MARRAKECH.Model.Farmer;
import com.PFA.DPA_MARRAKECH.Model.Request;
import com.PFA.DPA_MARRAKECH.Repository.FarmerRepository;
import com.PFA.DPA_MARRAKECH.Repository.RequestRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class FarmerController {

    private final FarmerRepository farmerRepository;

    private final RequestRepository requestRepository;

    public FarmerController(FarmerRepository farmerRepository, RequestRepository requestRepository) {
        this.farmerRepository = farmerRepository;
        this.requestRepository = requestRepository;
    }

    @GetMapping("/farmer/{cin}")
    public String getFarmerInfo(@PathVariable String cin) {
        Optional<Farmer> farmerOpt = farmerRepository.findByCin(cin);
        Optional<Request> requestOpt = requestRepository.findById(cin);

        if (farmerOpt.isPresent() && requestOpt.isPresent()) {
            Farmer farmer = farmerOpt.get();
            Request request = requestOpt.get();
            return "Farmer has received support and has submitted a request: " + farmer + " | " + request;
        } else if (farmerOpt.isPresent()) {
            Farmer farmer = farmerOpt.get();
            return "Farmer has received support but has not submitted a request: " + farmer;
        } else if (requestOpt.isPresent()) {
            Request request = requestOpt.get();
            return "Farmer has not received support but has submitted a request: " + request;
        } else {
            return "Farmer not found in either list.";
        }
    }
}
