package it.ads.app.city_explorer_sdk.interfaces;

public interface NetworkInterface {

    /**
     * Check if device has network connection
     * @return boolean
     */
    Boolean isAvailable();

    /**
     * Check what type of network device is using currently
     * <i>ie</i> mobile, wifi or other.
     * @return String network type
     */
    String networkType();

    /**
     * If there is an netwrok error
     * @param error String error
     */
    void networkError(String error);

}
