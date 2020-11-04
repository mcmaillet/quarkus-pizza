package org.acme.resources.location;

public class Location {
    private final String _location;
    private final String _formatted;

    public Location(String location, String formatted) {
        _location = location;
        _formatted = formatted;
    }

    public String getFormatted(){
        return _formatted;
    }

    public String getLocation(){
        return _location;
    }
}
