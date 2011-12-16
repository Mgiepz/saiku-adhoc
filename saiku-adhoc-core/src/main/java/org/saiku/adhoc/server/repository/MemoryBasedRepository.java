package org.saiku.adhoc.server.repository;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.UUID;

import org.pentaho.metadata.model.Domain;
import org.pentaho.metadata.repository.InMemoryMetadataDomainRepository;
import org.pentaho.metadata.util.XmiParser;

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
            in = new FileInputStream("metadata.xmi");
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
