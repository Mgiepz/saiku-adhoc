package org.saiku.adhoc.server.datasource;

import java.util.Properties;

public class SaikuCDA {

private String name;
private Properties properties;
private String path;
private byte[] contents;
public SaikuCDA() {}

public SaikuCDA(String path, String name, Properties properties) {
this.name = name;
this.path = path;
this.properties = properties;
}

public SaikuCDA(String path, String name, byte[] contents) {
    this.name = name;
    this.path = path;
    this.contents = contents;
    }

public SaikuCDA(String action, byte[] contents) {
    this.name = action;
    this.contents = contents;
}


public enum Type {
CDA
}

public String getName() {
return name;
}


public String getPath() {
return path;
}

public void setPath(String path) {
this.path = path;
}


public Properties getProperties() {
return properties;
}

public byte[] getContents() {
    return contents;
}

}