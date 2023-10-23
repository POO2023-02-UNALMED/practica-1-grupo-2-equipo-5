package gestorAplicacion.Peliculas;

public enum Genero {
    ACCION("ACCION"),
    AVENTURA("AVENTURA"),
    COMEDIA("COMEDIA"),
    DRAMA("DRAMA"),
    CIENCIA_FICCION("CIENCIA_FICCION"),
    SUSPENSO("SUSPENSO"),
    ANIMACION("ANIMACION"),
    TERROR("TERROR");

    String genero;

    private Genero(String genero){
        this.genero = genero;
    }

    public String getGenero(){
        return this.genero;
    }

    public static Genero fromString(String generoStr) {
        for (Genero genero : Genero.values()) {
            if (genero.genero.equalsIgnoreCase(generoStr)) {
                return genero;
            }
        }
        return null;
    }
}
