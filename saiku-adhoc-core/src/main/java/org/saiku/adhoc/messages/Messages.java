/*
 * Copyright (C) 2011 Marius Giepz
 *
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the Free 
 * Software Foundation; either version 2 of the License, or (at your option) 
 * any later version.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to the Free Software Foundation, Inc., 
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA 
 *
 */
package org.saiku.adhoc.messages;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.pentaho.platform.util.messages.LocaleHelper;
import org.pentaho.platform.util.messages.MessageUtil;


public class Messages {
  private static final String BUNDLE_NAME = "org.saiku.adhoc.messages.messages"; //$NON-NLS-1$

  private static final Map<Locale,ResourceBundle> locales = Collections.synchronizedMap(new HashMap<Locale,ResourceBundle>());

  private static ResourceBundle getBundle() {
    Locale locale = LocaleHelper.getLocale();
    ResourceBundle bundle = Messages.locales.get(locale);
    if (bundle == null) {
      bundle = ResourceBundle.getBundle(Messages.BUNDLE_NAME, locale);
      Messages.locales.put(locale, bundle);
    }
    return bundle;
  }

  public static String getString(final String key) {
    try {
      return Messages.getBundle().getString(key);
    } catch (MissingResourceException e) {
      return '!' + key + '!';
    }
  }

  public static String getString(final String key, final String param1) {
    return MessageUtil.getString(Messages.getBundle(), key, param1);
  }

  public static String getString(final String key, final String param1, final String param2) {
    return MessageUtil.getString(Messages.getBundle(), key, param1, param2);
  }

  public static String getString(final String key, final String param1, final String param2, final String param3) {
    return MessageUtil.getString(Messages.getBundle(), key, param1, param2, param3);
  }

  public static String getString(final String key, final String param1, final String param2, final String param3, final String param4) {
    return MessageUtil.getString(Messages.getBundle(), key, param1, param2, param3, param4);
  }

  public static String getErrorString(final String key) {
    return MessageUtil.formatErrorMessage(key, Messages.getString(key));
  }

  public static String getErrorString(final String key, final String param1) {
    return MessageUtil.getErrorString(Messages.getBundle(), key, param1);
  }

  public static String getErrorString(final String key, final String param1, final String param2) {
    return MessageUtil.getErrorString(Messages.getBundle(), key, param1, param2);
  }

  public static String getErrorString(final String key, final String param1, final String param2, final String param3) {
    return MessageUtil.getErrorString(Messages.getBundle(), key, param1, param2, param3);
  }

}