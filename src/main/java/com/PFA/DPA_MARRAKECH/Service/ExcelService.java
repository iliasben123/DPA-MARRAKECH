package com.PFA.DPA_MARRAKECH.Service;
import com.PFA.DPA_MARRAKECH.Model.Farmer;
import com.PFA.DPA_MARRAKECH.Model.Request;
import com.PFA.DPA_MARRAKECH.Repository.FarmerRepository;
import com.PFA.DPA_MARRAKECH.Repository.RequestRepository;
import com.PFA.DPA_MARRAKECH.Config.ExcelProperties;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;

@Service
public class ExcelService {

    private static final Logger logger = LoggerFactory.getLogger(ExcelService.class);
    private static final int BATCH_SIZE = 100; // Taille du lot pour les sauvegardes

    private final ExcelProperties excelProperties;
    private final FarmerRepository farmerRepository;
    private final RequestRepository requestRepository;

    @Autowired
    public ExcelService(ExcelProperties excelProperties, FarmerRepository farmerRepository, RequestRepository requestRepository) {
        this.excelProperties = excelProperties;
        this.farmerRepository = farmerRepository;
        this.requestRepository = requestRepository;
    }

    public void loadSupportData() {
        String supportFileName = excelProperties.getSupportFile().getFileName();
        try (FileInputStream fis = new FileInputStream(supportFileName);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Row headerRow = sheet.getRow(0);

                if (headerRow == null) {
                    logger.warn("Header row is missing in sheet {}", i);
                    continue;
                }

                int supportCinIndex = findColumnIndex(headerRow, excelProperties.getSupportFile().getColumns().getCin());
                int supportNameIndex = findColumnIndex(headerRow, excelProperties.getSupportFile().getColumns().getName());
                int supportAddressIndex = findColumnIndex(headerRow, excelProperties.getSupportFile().getColumns().getAddress());
                int supportPhoneNumberIndex = findColumnIndex(headerRow, excelProperties.getSupportFile().getColumns().getPhoneNumber());
                int supportTransportationAssuredIndex = findColumnIndex(headerRow, excelProperties.getSupportFile().getColumns().getTransportationAssured());
                int supportDateDeDepotIndex = findColumnIndex(headerRow, excelProperties.getSupportFile().getColumns().getDateDeDepot());
                int supportDateDeffetIndex = findColumnIndex(headerRow, excelProperties.getSupportFile().getColumns().getDateDeffet());
                int supportTypeDeDossierIndex = findColumnIndex(headerRow, excelProperties.getSupportFile().getColumns().getTypeDeDossier());
                int supportCommuneIndex = findColumnIndex(headerRow, excelProperties.getSupportFile().getColumns().getCommune());

                int count = 0;

                for (Row currentRow : sheet) {
                    if (currentRow.getRowNum() == 0) {
                        continue; // Ignore header row
                    }

                    Farmer farmer = new Farmer();
                    farmer.setCin(getCellValue(currentRow, supportCinIndex));
                    farmer.setName(getCellValue(currentRow, supportNameIndex));
                    farmer.setAddress(getCellValue(currentRow, supportAddressIndex));
                    farmer.setPhoneNumber(getCellValue(currentRow, supportPhoneNumberIndex));
                    farmer.setTransportationAssured(getCellValue(currentRow, supportTransportationAssuredIndex));
                    farmer.setDateDeDepot(getCellValue(currentRow, supportDateDeDepotIndex));
                    farmer.setDateDeffet(getCellValue(currentRow, supportDateDeffetIndex));
                    farmer.setTypeDeDossier(getCellValue(currentRow, supportTypeDeDossierIndex));
                    farmer.setCommune(getCellValue(currentRow, supportCommuneIndex));

                    try {
                        farmerRepository.save(farmer);
                    } catch (Exception e) {
                        logger.error("Erreur lors de la sauvegarde de la ligne {} : {}", currentRow.getRowNum(), e.getMessage());
                    }

                    count++;
                    if (count % BATCH_SIZE == 0) {
                        farmerRepository.flush(); // Sauvegarde par lots
                    }
                }

                farmerRepository.flush(); // Sauvegarder les derniers enregistrements
            }

            logger.info("Support data loaded successfully from {}", supportFileName);
        } catch (IOException e) {
            logger.error("Failed to load support data from file {}", supportFileName, e);
        }
    }

    public void loadRequestData() {
        String requestFileName = excelProperties.getRequestFile().getFileName();
        try (FileInputStream fis = new FileInputStream(requestFileName);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            int numberOfSheets = workbook.getNumberOfSheets();
            for (int i = 0; i < numberOfSheets; i++) {
                Sheet sheet = workbook.getSheetAt(i);
                Row headerRow = sheet.getRow(0);

                if (headerRow == null) {
                    logger.warn("Header row is missing in sheet {}", i);
                    continue;
                }

                int requestCinIndex = findColumnIndex(headerRow, excelProperties.getRequestFile().getColumns().getCin());
                int requestMaladiesIndex = findColumnIndex(headerRow, excelProperties.getRequestFile().getColumns().getMaladies());
                int requestJustificatifIndex = findColumnIndex(headerRow, excelProperties.getRequestFile().getColumns().getJustificatif());
                int requestDateDeTraitementIndex = findColumnIndex(headerRow, excelProperties.getRequestFile().getColumns().getDateDeTraitement());
                int requestTypeDeDemandeIndex = findColumnIndex(headerRow, excelProperties.getRequestFile().getColumns().getTypeDeDemande());
                int requestCommuneIndex = findColumnIndex(headerRow, excelProperties.getRequestFile().getColumns().getCommune());

                int count = 0;

                for (Row currentRow : sheet) {
                    if (currentRow.getRowNum() == 0) {
                        continue; // Ignore header row
                    }

                    Request request = new Request();
                    request.setCin(getCellValue(currentRow, requestCinIndex));
                    request.setMaladies(getCellValue(currentRow, requestMaladiesIndex));
                    request.setJustificatif(getCellValue(currentRow, requestJustificatifIndex));
                    request.setDateDeTraitement(getCellValue(currentRow, requestDateDeTraitementIndex));
                    request.setTypeDeDemande(getCellValue(currentRow, requestTypeDeDemandeIndex));
                    request.setCommune(getCellValue(currentRow, requestCommuneIndex));

                    try {
                        requestRepository.save(request);
                    } catch (Exception e) {
                        logger.error("Erreur lors de la sauvegarde de la ligne {} : {}", currentRow.getRowNum(), e.getMessage());
                    }

                    count++;
                    if (count % BATCH_SIZE == 0) {
                        requestRepository.flush();
                    }
                }

                requestRepository.flush();
            }

            logger.info("Request data loaded successfully from {}", requestFileName);
        } catch (IOException e) {
            logger.error("Failed to load request data from file {}", requestFileName, e);
        }
    }

    private int findColumnIndex(Row headerRow, String columnName) {
        columnName = normalizeString(columnName);

        for (Cell cell : headerRow) {
            String cellValue = normalizeString(cell.getStringCellValue());
            if (cellValue.equalsIgnoreCase(columnName)) {
                return cell.getColumnIndex();
            }
        }
        return -1;
    }

    private String normalizeString(String input) {
        if (input == null) {
            return null;
        }
        return input.trim().replace("ï¿½", "é").replaceAll("\\s+", " ");
    }

    private String getCellValue(Row row, int columnIndex) {
        if (columnIndex == -1) {
            return "";
        }
        Cell cell = row.getCell(columnIndex);
        if (cell == null) {
            return "";
        }
        return cell.toString();
    }
}
