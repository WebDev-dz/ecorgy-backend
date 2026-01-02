package com.example.app.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Location {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "location_seq")
    @SequenceGenerator(name = "location_seq", sequenceName = "LOCATION_SEQ", allocationSize = 1)
    private Long id;
    
    @Column(nullable = false)
    private Double latitude;
    
    @Column(nullable = false)
    private Double longitude;
    
    @Column(nullable = false)
    private String street;
    
    @Column(nullable = false)
    private String city;
    
    @Column(name = "zip_code", nullable = false)
    private String zipCode;
    
    @Column(nullable = false)
    private String country;
    
    public Integer getWilayaCode(String wilaya) {
        switch (wilaya) {
            case "Adrar": return 1;
            case "Chlef": return 2;
            case "Laghouat": return 3;
            case "Oum El Bouaghi": return 4;
            case "Batna": return 5;
            case "Béjaïa": return 6;
            case "Biskra": return 7;
            case "Béchar": return 8;
            case "Blida": return 9;
            case "Bouïra": return 10;
            case "Tamanrasset": return 11;
            case "Tébessa": return 12;
            case "Tlemcen": return 13;
            case "Tiaret": return 14;
            case "Tizi Ouzou": return 15;
            case "Algiers": return 16;
            case "Djelfa": return 17;
            case "Jijel": return 18;
            case "Sétif": return 19;
            case "Saïda": return 20;
            case "Skikda": return 21;
            case "Sidi Bel Abbès": return 22;
            case "Annaba": return 23;
            case "Guelma": return 24;
            case "Constantine": return 25;
            case "Médéa": return 26;
            case "Mostaganem": return 27;
            case "M'Sila": return 28;
            case "Mascara": return 29;
            case "Ouargla": return 30;
            case "Oran": return 31;
            case "El Bayadh": return 32;
            case "Illizi": return 33;
            case "Bordj Bou Arréridj": return 34;
            case "Boumerdès": return 35;
            case "El Tarf": return 36;
            case "Tindouf": return 37;
            case "Tissemsilt": return 38;
            case "El Oued": return 39;
            case "Khenchela": return 40;
            case "Souk Ahras": return 41;
            case "Tipaza": return 42;
            case "Mila": return 43;
            case "Aïn Defla": return 44;
            case "Naâma": return 45;
            case "Aïn Témouchent": return 46;
            case "Ghardaïa": return 47;
            case "Relizane": return 48;
            case "Timimoun": return 49;
            case "Bordj Badji Mokhtar": return 50;
            case "Ouled Djellal": return 51;
            case "Béni Abbès": return 52;
            case "Ain Salah": return 53;
            case "Ain Guezzam": return 54;
            case "Touggourt": return 55;
            case "Djanet": return 56;
            case "El M'Ghair": return 57;
            case "El Menia": return 58;
            case "Aflou": return 59;
            case "Barika": return 60;
            case "Ksar Chellala": return 61;
            case "Messaad": return 62;
            case "Aïn Oussera": return 63;
            case "Boussaâda": return 64;
            case "El Abiodh Sidi Cheikh": return 65;
            case "El Kantara": return 66;
            case "Bir El Ater": return 67;
            case "Ksar El Boukhari": return 68;
            case "El Aricha": return 69;
            default: return null;
        }
    }
}