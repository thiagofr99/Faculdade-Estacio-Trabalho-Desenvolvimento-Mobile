package com.devthiagofurtado.trabalhomobile02.api.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Source implements Serializable {

    private String name;

    private String id;

}
