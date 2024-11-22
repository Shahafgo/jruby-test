package org.example;

public class NameVersionPlatformTuple {

    public final String name, version, platform;
    public final boolean isPrioritised;

    public NameVersionPlatformTuple(String name, String version, String platform) {
        this(name, version, platform, false);
    }

    public NameVersionPlatformTuple(String name, String version, String platform, boolean isPrioritised) {
        this.name = name;
        this.version = version;
        this.platform = platform;
        this.isPrioritised = isPrioritised;
    }

    @Override
    public String toString() {
        return name + ":" + version + ":" + platform;
    }
}
