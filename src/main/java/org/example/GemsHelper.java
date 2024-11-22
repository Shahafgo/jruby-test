package org.example;

import org.jruby.RubyArray;
import org.jruby.embed.EmbedEvalUnit;
import org.jruby.embed.LocalContextScope;
import org.jruby.embed.LocalVariableBehavior;
import org.jruby.embed.ScriptingContainer;
import org.jruby.runtime.builtin.IRubyObject;

import java.io.File;
import java.io.InputStream;


public class GemsHelper {

    private static final String GEMS_HELPER = "gems_helper.rb";

    private volatile ScriptingContainer scriptingContainer;
    private Object gemsHelper;

    public void init() {
        if (scriptingContainer == null) {
            LocalContextScope localContextScope = resolveLocalContextScope();
            ScriptingContainer scriptingContainerInstance = new ScriptingContainer(localContextScope,
                    LocalVariableBehavior.PERSISTENT);
            scriptingContainerInstance.setClassLoader(Thread.currentThread().getContextClassLoader());
            IRubyObject gemsHelperClass;
            try (InputStream is = scriptingContainerInstance.getClassLoader().getResourceAsStream(GEMS_HELPER)) {
                EmbedEvalUnit result = scriptingContainerInstance.parse(is, GEMS_HELPER);
                gemsHelperClass = result.run();
            } catch (Exception e) {
                throw new IllegalStateException("Could not initialize gems helper", e);
            }

            this.gemsHelper = scriptingContainerInstance.callMethod(gemsHelperClass, "new", Object.class);
            this.scriptingContainer = scriptingContainerInstance;
        }
    }

    private LocalContextScope resolveLocalContextScope() {
        String externalLocalContextMode = System.getProperty("CONCURRENT");
        if (externalLocalContextMode != null && !externalLocalContextMode.isEmpty()) {
            return LocalContextScope.valueOf(externalLocalContextMode.toUpperCase());
        }
        // Default
        return LocalContextScope.valueOf("CONCURRENT");
    }


    public ScriptingContainer getScriptingContainer() {
        init();
        return scriptingContainer;
    }

    public NameVersionPlatformTuple getGemNameVersionPlatform(File gemFile) {
        String result = call("name_version_platform", gemFile.getAbsolutePath());
        String[] nvp = result.split(",");
        return new NameVersionPlatformTuple(nvp[0], nvp[1], nvp[2]);
    }

    /**
     * executes ruby gems scripts in #artifactory-pro/addons/gems/src/main/resources/gems_helper.rb
     *
     * @param method : ruby method to execute
     * @param params : additonal params to use
     * @return the ruby script output
     */
    private <T> T call(String method, Object... params) {
        //noinspection unchecked
        return (T) getScriptingContainer().callMethod(gemsHelper, method, params);
    }
}

