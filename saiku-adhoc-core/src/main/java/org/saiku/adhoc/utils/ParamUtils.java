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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.saiku.adhoc.model.master.SaikuMasterModel;
import org.saiku.adhoc.model.master.SaikuParameter;

public class ParamUtils {

	public static Map<String, Object> getReportParameters(String prefix,SaikuMasterModel model) {

		Map<String, Object> reportParameters = new HashMap<String, Object>();

		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		final List<SaikuParameter> parameters = model.getParameters();
		for (SaikuParameter saikuParameter : parameters) {

			final String categoryId = saikuParameter.getCategory();
			final String columnId = saikuParameter.getId();
			final String parameterName = prefix + "F_" + categoryId + "_" + columnId;

			if (saikuParameter.getType().equals("String")) {
				List<String> valueList = saikuParameter
				.getParameterValues();
				String[] values = valueList
				.toArray(new String[valueList.size()]);
				reportParameters.put(parameterName, values);
			}
			if (saikuParameter.getType().equals("Date")) {
				String nameFrom = parameterName + "_FROM";
				String nameTo = parameterName + "_TO";
				List<String> valueList = saikuParameter
				.getParameterValues();
				String[] values = valueList
				.toArray(new String[valueList.size()]);

				if(!(values.length < 2))	{	
				try {
					reportParameters.put(nameFrom, dateFormat.parse(values[0]));
					reportParameters.put(nameTo, dateFormat.parse(values[1]));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}else{
					reportParameters.put(nameFrom, null);
					reportParameters.put(nameTo, null);
				}

			}
			
			if (saikuParameter.getType().equals("Numeric")) {
				List<String> valueList = saikuParameter.getParameterValues();
				Double[] values = new Double[valueList.size()];
				int i = 0;
				for (String myInt : valueList) 
	            {
	            	values[i] = Double.valueOf(myInt);
	            	i++;
	            }
				reportParameters.put(parameterName, values);
			}


		}
		return reportParameters;
	}
	
	public static String[] splitFirst(String source, String splitter)
	  {
	    // hold the results as we find them
	    List<String> rv = new Vector<String>();
	    int last = 0;
	    int next = 0;

	    // find first splitter in source
	    next = source.indexOf(splitter, last);
	    if (next != -1)
	    {
	      // isolate from last thru before next
	      rv.add(source.substring(last, next));
	      last = next + splitter.length();
	    }

	    if (last < source.length())
	    {
	      rv.add(source.substring(last, source.length()));
	    }

	    // convert to array
	    return (String[]) rv.toArray(new String[rv.size()]);
	  }


}
