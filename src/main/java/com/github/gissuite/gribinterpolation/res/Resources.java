/*
* MIT License
*
* Copyright (c) 2024 OpenGISViewer
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*
 */
package com.github.gissuite.gribinterpolation.res;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Resources {
    private static final Logger logger = LoggerFactory.getLogger(Resources.class);
    private static Resources instance = null;
    private static Properties properties = null;
    private Resources() {}

    public static Resources init() {
        if (instance == null || properties == null) {
            instance = new Resources();
            getProperties();
        }
        return instance;
    }

    private static void getProperties() {
        properties = new Properties();
        final String path = "src/main/resources/appsettings.config";
        try (FileInputStream stream = new FileInputStream(path)) {
            properties.load(stream);
        } catch (IOException exception) {
            logger.error("Unable to get application config.", exception);
        }
    }
}
