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

import java.util.Timer;

import org.pentaho.platform.api.engine.IPluginLifecycleListener;
import org.pentaho.platform.api.engine.PluginLifecycleException;

public class AdhocLifecycleListener implements IPluginLifecycleListener {

	@Override
	public void init() throws PluginLifecycleException {
	
		 Timer timer = new Timer();
		 timer.schedule(new TempCleanerTask(), 2000, 20000);
		
	}

	@Override
	public void loaded() throws PluginLifecycleException {



	}

	@Override
	public void unLoaded() throws PluginLifecycleException {
		// TODO Auto-generated method stub

	}

}
