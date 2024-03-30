package udemy.testesautomatizados.common;

import udemy.testesautomatizados.domain.Planet;

public class PlanetConstants {
    public static final Planet PLANET = new Planet("Coruscant", "city planet", "urban sprawl");
    public static final Planet GET_PLANET = new Planet(1L,"Coruscant", "city planet", "urban sprawl");

    public static final Planet INVALID_PLANET = new Planet("", "city planet", "");
}
