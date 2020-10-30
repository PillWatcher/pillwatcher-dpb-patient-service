package br.com.pillwatcher.dpb.constants;

public final class UrlConstants {

    public static final String BASE_URI = "/v1/pillwatcher";
    public static final String URI_PATIENTS = BASE_URI + "/patients";
    public static final String URI_PATIENTS_CPF = URI_PATIENTS + "/{cpf}";

    private UrlConstants() {
    }
}
