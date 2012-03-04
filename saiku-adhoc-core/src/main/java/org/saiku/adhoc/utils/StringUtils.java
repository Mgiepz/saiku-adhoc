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

import java.util.ArrayList;
import java.util.List;

import org.saiku.adhoc.model.master.SaikuColumn;

public class StringUtils {
	
	

	public static String getUniqueColumnName(String name, List<SaikuColumn> columns) {

		List<String> colNames = new ArrayList<String>();

		for (SaikuColumn col : columns) {
			colNames.add(col.getName());
		}

		return getUniqueName(name, colNames);

	}

	/**
	 * @param name
	 * @param colNames
	 * @return
	 */
	public static String getUniqueName(String name, List<String> colNames) {
		int i = 0;
		
		String uniqueColumnName = name;

		while (colNames.contains(uniqueColumnName)||uniqueColumnName.equals("")) {
			i++;
			uniqueColumnName = name + " " + i;
		}

		return uniqueColumnName;
	}

}
