package org.osc.scan.server.business.util;

import java.io.IOException;
import java.util.Locale;

import org.apache.commons.io.output.StringBuilderWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author bz.zhang
 * @date 2021/3/11 17:38
 */
@Slf4j
public class FreemarkerUtil {

    private final static String TEMPLATE_PATH = "/template/";

    private static final Configuration DEFAULT_CONFIGURATION;

    static {
        DEFAULT_CONFIGURATION = getDefaultConfiguration();
    }

    private static Configuration getDefaultConfiguration() {
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_31);
        configuration.setClassForTemplateLoading(FreemarkerUtil.class, TEMPLATE_PATH);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setLocale(Locale.CHINA);
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setClassicCompatible(true);
        return configuration;
    }

    public static String toStr(String templateFileName, Object data) throws IOException, TemplateException {
        Template template = DEFAULT_CONFIGURATION.getTemplate(templateFileName);
        StringBuilderWriter stringBuilderWriter = new StringBuilderWriter();
        template.process(data, stringBuilderWriter);
        return stringBuilderWriter.toString();
    }

}
