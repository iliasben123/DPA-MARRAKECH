package com.PFA.DPA_MARRAKECH.Config;

import com.PFA.DPA_MARRAKECH.Service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataLoader {

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    private final ExcelService excelService;

    @Autowired
    public DataLoader(ExcelService excelService) {
        this.excelService = excelService;
    }

    @PostConstruct
    public void init() {
        try {
            excelService.loadSupportData();
            excelService.loadRequestData();
        } catch (Exception e) {
            logger.error("Error loading Excel data: {}", e.getMessage(), e);
        }
    }
}