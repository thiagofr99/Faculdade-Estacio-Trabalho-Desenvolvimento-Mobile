package com.devthiagofurtado.trabalhomobile02.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Noticia {

    private String noticia, autor, titulo;
}
