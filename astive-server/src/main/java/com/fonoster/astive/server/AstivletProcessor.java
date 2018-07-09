/* 
 * Copyright (C) 2017 by Fonoster Inc (http://fonoster.com)
 * http://github.com/fonoster/astive
 *
 * This file is part of Astive
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fonoster.astive.server;

import org.apache.log4j.Logger;
import com.fonoster.astive.AstiveException;
import com.fonoster.astive.astivlet.Astivlet;
import com.fonoster.astive.astivlet.AstivletRequest;
import com.fonoster.astive.astivlet.AstivletResponse;
import com.fonoster.astive.util.AppLocale;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Helper class to execute astivlets.
 *
 * @since 1.0
 */
public class AstivletProcessor {
    private static final Logger LOG = Logger.getLogger(AstivletProcessor.class);

    private AstivletProcessor() {
    }

    public static void invokeAstivlet(final AstivletRequest request, final AstivletResponse response)
            throws AstiveException {
        try {
            AstDB astDB = MyAstDB.getInstance();
            String script = "/" + request.getScript();

            if (script.split("\\?").length > 1) {
                script = script.split("\\?")[0];
            }

            Astivlet astivlet = astDB.getAstivlet(script);

            Class[] classParamTypes = new Class[2];
            classParamTypes[0] = AstivletRequest.class;
            classParamTypes[1] = AstivletResponse.class;

            Class c = Astivlet.class;

            Method m;
            m = c.getDeclaredMethod("service", classParamTypes);
            m.setAccessible(true);
            m.invoke(astivlet, new Object[]{request, response});
        } catch (NoSuchMethodException ex) {
            LOG.error(ex);
            throw new AstiveException(ex.getMessage());
        } catch (SecurityException ex) {
            LOG.error(ex);
            throw new AstiveException(ex.getMessage());
        } catch (IllegalAccessException ex) {
            LOG.error(ex);
            throw new AstiveException(ex.getMessage());
        } catch (IllegalArgumentException ex) {
            LOG.error(ex);
            throw new AstiveException(ex.getMessage());
        } catch (InvocationTargetException ex) {
            System.out.println("Devil Exception :: cause by :P ");
            ex.printStackTrace();
            LOG.error(AppLocale.getI18n("errorEnsureVersionsMatch"));
            throw new AstiveException(ex.getMessage());
        } catch (NullPointerException ex) {
            throw new AstiveException(AppLocale.getI18n("errorResourceNotExist",
                    new Object[]{"/" + request.getScript()}));
        }
    }

    public static void invokeAstivlet(Astivlet astivlet, AstivletRequest request,
            AstivletResponse response)
            throws AstiveException {
        try {
            Class[] classParamTypes = new Class[2];
            classParamTypes[0] = AstivletRequest.class;
            classParamTypes[1] = AstivletResponse.class;

            Class c = Astivlet.class;
            Method m;

            m = c.getDeclaredMethod("service", classParamTypes);
            m.setAccessible(true);
            m.invoke(astivlet, new Object[]{request, response});
        } catch (NoSuchMethodException ex ) {
            LOG.error(ex.getMessage());
            throw new AstiveException(ex);
        } catch (SecurityException ex ) {
            LOG.error(ex.getMessage());
            throw new AstiveException(ex);
        } catch (IllegalAccessException ex) {
            LOG.error(ex.getMessage());
            throw new AstiveException(ex);
        } catch (IllegalArgumentException ex) {
            LOG.error(ex.getMessage());
            throw new AstiveException(ex);
        } catch (InvocationTargetException ex) {
            LOG.error(ex.getMessage());
            throw new AstiveException(ex);
        }
    }
}
