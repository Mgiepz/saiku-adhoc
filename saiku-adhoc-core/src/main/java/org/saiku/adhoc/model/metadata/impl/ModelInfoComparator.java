package org.saiku.adhoc.model.metadata.impl;

import java.util.Comparator;

/**
 * compares two model info objects so that they can be sorted by name
 * @author jamesdixon
 *
 */
public class ModelInfoComparator implements Comparator {

  @Override
  public int compare(Object obj1, Object obj2) {
    MetadataModelInfo model1 = (MetadataModelInfo) obj1;
    MetadataModelInfo model2 = (MetadataModelInfo) obj2;
    return model1.getModelName().compareTo(model2.getModelName());
  }

}
