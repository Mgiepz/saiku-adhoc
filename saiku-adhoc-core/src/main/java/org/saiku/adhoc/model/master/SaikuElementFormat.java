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
package org.saiku.adhoc.model.master;


/**
* @author mgie
*
* org.pentaho.reporting.engine.classic.wizard.model.ElementFormatDefinition.class
*/
public class SaikuElementFormat implements Cloneable {

public SaikuElementFormat() {
this.horizontalAlignment = "LEFT";
this.verticalAlignment = "MIDDLE";
}

private String fontName;

private int fontSize;

private Boolean fontBold;

private Boolean fontItalic;

private Boolean fontUnderlined;

//TODO: Es gibt auch ein Color format in Metadata PAckage
private String fontColor;

private String horizontalAlignment;

private String verticalAlignment;

private String backgroundColor;

public String getFontName() {
return fontName;
}

public void setFontName(String fontName) {
this.fontName = fontName;
}

public int getFontSize() {
return fontSize;
}

public void setFontSize(int fontSize) {
this.fontSize = fontSize;
}

public String getFontColor() {
return fontColor;
}

public void setFontColor(String fontColor) {
this.fontColor = fontColor;
}

public String getHorizontalAlignment() {
return horizontalAlignment;
}

public void setHorizontalAlignment(String horizontalAlignment) {
this.horizontalAlignment = horizontalAlignment;
}

public String getVerticalAlignment() {
return verticalAlignment;
}

public void setVerticalAlignment(String verticalAlignment) {
this.verticalAlignment = verticalAlignment;
}

public String getBackgroundColor() {
return backgroundColor;
}

public void setBackgroundColor(String backgroundColor) {
this.backgroundColor = backgroundColor;
}

public Object clone() {
try {
return super.clone();
} catch (CloneNotSupportedException e) {
return null;
}
}

public void setFontBold(Boolean fontBold) {
this.fontBold = fontBold;
}

public Boolean getFontBold() {
return fontBold;
}

public void setFontItalic(Boolean fontItalic) {
this.fontItalic = fontItalic;
}

public Boolean getFontItalic() {
return fontItalic;
}

public void setFontUnderlined(Boolean fontUnderlined) {
this.fontUnderlined = fontUnderlined;
}

public Boolean getFontUnderlined() {
return fontUnderlined;
}



}

