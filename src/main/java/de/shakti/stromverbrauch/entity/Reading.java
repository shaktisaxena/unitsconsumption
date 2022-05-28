package de.shakti.stromverbrauch.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Reading {
    private double units;
    private READINGTYPE  readingtype;
}
