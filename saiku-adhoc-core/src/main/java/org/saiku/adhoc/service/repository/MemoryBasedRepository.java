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
package org.saiku.adhoc.service.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.repository.InMemoryMetadataDomainRepository;
import org.pentaho.metadata.util.XmiParser;
import org.saiku.adhoc.service.SaikuProperties;

public class MemoryBasedRepository {

    InMemoryMetadataDomainRepository immdr;
    public InMemoryMetadataDomainRepository getImmdr() {
        return immdr;
    }

    public void setImmdr(InMemoryMetadataDomainRepository immdr) {
        this.immdr = immdr;
    }

    public MemoryBasedRepository(){
        immdr = new InMemoryMetadataDomainRepository();
        createDomain();
    }
    
    private void createDomain(){
        FileInputStream in = null;
        try {
            in = new FileInputStream(SaikuProperties.metadataFile);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        final XmiParser parser = new XmiParser();
        Domain domain = null;
        try {
            domain = parser.parseXmi(in);
            domain.setId(UUID.randomUUID().toString()+"/"+"metadata.xmi");
            immdr.storeDomain(domain, true);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        

    }
}
