package kz.ktzh.hackathon.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public class NamedDto {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
