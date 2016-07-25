package ua.farmapriv.servicios.enumeraciones;

public enum Etapa {

	INI ("INICIO"),
	INV ("INVESTIGACION"),
	CLS ("CLASIFICACION"),
	MET ("METANALISIS"),
	SUS ("SUSPENDIDO"),
	FIN ("FIN");
	
	private String label;

    private Etapa(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
