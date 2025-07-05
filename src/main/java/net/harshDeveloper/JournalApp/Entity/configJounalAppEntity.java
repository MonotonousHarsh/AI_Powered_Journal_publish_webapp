package net.harshDeveloper.JournalApp.Entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection ="config_jounal_app")
public class configJounalAppEntity {


    private String key ;
    private String value;

}
