package com.PFA.DPA_MARRAKECH.Config;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties(prefix = "excel")
public class ExcelProperties {

    private SupportFile supportFile;
    private RequestFile requestFile;



    @Setter
    @Getter
    public static class SupportFile {
        private String fileName;
        private Columns columns;




        @Setter
        @Getter
        public static class Columns {
            private String cin;
            private String name;
            private String address;
            private String phoneNumber;
            private String transportationAssured;
            private String dateDeDepot;
            private String dateDeffet;
            private String typeDeDossier;
            private String commune;



        }
    }

    @Setter
    @Getter
    public static class RequestFile {
        private String fileName;
        private Columns columns;



        @Setter
        @Getter
        public static class Columns {
            private String cin;
            private String maladies;
            private String justificatif;
            private String dateDeTraitement;
            private String typeDeDemande;
            private String commune;




        }

    }
}
