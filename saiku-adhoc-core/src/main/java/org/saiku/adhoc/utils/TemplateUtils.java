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
package org.saiku.adhoc.utils;
import java.awt.Color;

import org.pentaho.reporting.engine.classic.core.ElementAlignment;
import org.pentaho.reporting.engine.classic.core.style.ElementStyleKeys;
import org.pentaho.reporting.engine.classic.core.style.ElementStyleSheet;
import org.pentaho.reporting.engine.classic.core.style.TextStyleKeys;
import org.pentaho.reporting.engine.classic.core.style.VerticalTextAlign;
import org.saiku.adhoc.exceptions.ReportException;
import org.saiku.adhoc.model.master.SaikuElementFormat;

/**
* This class is used to extract default formatting for a new element from the
* current template
*
*
* @author mgiepz
*
*/
public class TemplateUtils {


/**
* Finds the class for a given aggreagation function name
*
* @param str
* @return
* @throws ReportException
*/
public static Class strToAggfunctionClass(String str) throws ReportException{

try {

if(str.equals("NONE")){
return null;
}
else if (str.equals("SUM")){
return Class.forName("org.pentaho.reporting.engine.classic.core.function.ItemSumFunction");
}
else if (str.equals("AVERAGE")){
return Class.forName("org.pentaho.reporting.engine.classic.core.function.ItemAvgFunction");
}
else if (str.equals("COUNT")){
return Class.forName("org.pentaho.reporting.engine.classic.core.function.ItemCountFunction");
}
else if (str.equals("COUNT_DISTINCT")){
return Class.forName("org.pentaho.reporting.engine.classic.core.function.CountDistinctFunction");
}
else if (str.equals("MINIMUM")){
return Class.forName("org.pentaho.reporting.engine.classic.core.function.ItemMinFunction");
}
else if (str.equals("MAXIMUM")){

return Class.forName("org.pentaho.reporting.engine.classic.core.function.ItemMaxFunction");
}

} catch (ClassNotFoundException e) {
throw new ReportException("Aggregation Class not found");
}

return null;


}


/**
* Converts a webcolor string to a awt color.
*
* @param str
* @return
*/
private static Color strToColor(String str) {
if(str!=null){
return Color.decode(str.replaceFirst("#", "0x"));
}else{
return null;
}
}

private static String colorToStr(Color color) {
if(color!=null){
int colInt = color.getRGB();
String str = Integer.toHexString(colInt);
return str.replaceFirst("ff", "#");
}else{
return null;
}
}

/**
* We need to do this because string constructor in pentaho
* ist private!!!
*
* @param aString
* @return
*/
private static ElementAlignment saikuToPrptAlignment(String aString){

if(aString.equals("TOP")){
return ElementAlignment.TOP;
}else if(aString.equals("BOTTOM")){
return ElementAlignment.BOTTOM;
}else if(aString.equals("CENTER")){
return ElementAlignment.CENTER;
}else if(aString.equals("LEFT")){
return ElementAlignment.LEFT;
}else if(aString.equals("RIGHT")){
return ElementAlignment.RIGHT;
}else if(aString.equals("MIDDLE")){
return ElementAlignment.MIDDLE;
}
return null;
}

/**
* We need to do this because string constructor in pentaho
* ist private!!!
*
* @param aString
* @return
*/
private static String prptToSaikuAlignment(ElementAlignment alignment){

if(alignment.equals(ElementAlignment.TOP)){
return "TOP";
}else if(alignment.equals(ElementAlignment.BOTTOM)){
return "BOTTOM";
}else if(alignment.equals(ElementAlignment.CENTER)){
return "CENTER";
}else if(alignment.equals(ElementAlignment.LEFT)){
return "LEFT";
}else if(alignment.equals(ElementAlignment.RIGHT)){
return "RIGHT";
}else if(alignment.equals(ElementAlignment.MIDDLE)){
return "MIDDLE";
}

return null;
}

/**
* Copies all element format prptFormation from a real report element to the
* Saiku model and vice-versa.
*
* @param prptFormat
* @param saikuFormat
*/
public static void mergeElementFormats(ElementStyleSheet prptFormat,
SaikuElementFormat saikuFormat) {

if(prptFormat==null && saikuFormat==null){
return;
}

if(saikuFormat.getBackgroundColor()==null){
final Color color = (Color) prptFormat.getStyleProperty(
ElementStyleKeys.BACKGROUND_COLOR, null);
String backgroudColor = colorToStr(color);
saikuFormat.setBackgroundColor(backgroudColor);
}else{
prptFormat.setStyleProperty(ElementStyleKeys.BACKGROUND_COLOR,
strToColor(saikuFormat.getBackgroundColor())
);
}

if(saikuFormat.getFontColor()==null){
final Color color = (Color) prptFormat.getStyleProperty(
ElementStyleKeys.PAINT, null);
String fillColor = colorToStr(color);
saikuFormat.setFontColor(fillColor);
}else{
prptFormat.
setStyleProperty(ElementStyleKeys.PAINT,
strToColor(saikuFormat.getFontColor())
);
}

if(saikuFormat.getFontName()==null){
final String font = (String) prptFormat.getStyleProperty(
TextStyleKeys.FONT, null);
saikuFormat.setFontName(font);
}else{
prptFormat.setStyleProperty(
TextStyleKeys.FONT,saikuFormat.getFontName());
}

if(saikuFormat.getFontBold()==null){
final Boolean fontBold = (Boolean) prptFormat.getStyleProperty(
TextStyleKeys.BOLD, null);
saikuFormat.setFontBold(fontBold);
}else{
prptFormat.setStyleProperty(
TextStyleKeys.BOLD,saikuFormat.getFontBold());
}

if(saikuFormat.getFontItalic()==null){
final Boolean fontItalic = (Boolean) prptFormat.getStyleProperty(
TextStyleKeys.ITALIC, null);
saikuFormat.setFontBold(fontItalic);
}else{
prptFormat.setStyleProperty(
TextStyleKeys.ITALIC,saikuFormat.getFontItalic());
}

if(saikuFormat.getFontUnderlined()==null){
final Boolean fontUnderlined = (Boolean) prptFormat.getStyleProperty(
TextStyleKeys.UNDERLINED, null);
saikuFormat.setFontUnderlined(fontUnderlined);
}else{
prptFormat.setStyleProperty(
TextStyleKeys.UNDERLINED,saikuFormat.getFontUnderlined());
}

if(saikuFormat.getHorizontalAlignment()==null){
final ElementAlignment horz = (ElementAlignment) prptFormat.getStyleProperty(
ElementStyleKeys.ALIGNMENT, null);
saikuFormat.setHorizontalAlignment(prptToSaikuAlignment(horz));
}else{
prptFormat.setStyleProperty(
ElementStyleKeys.ALIGNMENT, saikuToPrptAlignment(saikuFormat.getHorizontalAlignment()));
}

if(saikuFormat.getVerticalAlignment()==null){
final String vert = (String) prptFormat.getStyleProperty(
ElementStyleKeys.VALIGNMENT, null);
saikuFormat.setVerticalAlignment(vert);
}else{
prptFormat.setStyleProperty(
ElementStyleKeys.VALIGNMENT, saikuToPrptAlignment(saikuFormat.getVerticalAlignment()));
}


if(saikuFormat.getFontSize()==0){
final Integer size = (Integer) prptFormat.getStyleProperty(
TextStyleKeys.FONTSIZE, null);
saikuFormat.setFontSize(size.intValue());
}else{
prptFormat.setStyleProperty(
TextStyleKeys.FONTSIZE, new Integer(saikuFormat.getFontSize()));
}

}
}

