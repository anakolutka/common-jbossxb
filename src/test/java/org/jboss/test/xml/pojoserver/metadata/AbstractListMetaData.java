/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
  * by the @authors tag. See the copyright.txt in the distribution for a
  * full listing of individual contributors.
  *
  * This is free software; you can redistribute it and/or modify it
  * under the terms of the GNU Lesser General Public License as
  * published by the Free Software Foundation; either version 2.1 of
  * the License, or (at your option) any later version.
  *
  * This software is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
  * Lesser General Public License for more details.
  *
  * You should have received a copy of the GNU Lesser General Public
  * License along with this software; if not, write to the Free
  * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
  * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
  */
package org.jboss.test.xml.pojoserver.metadata;

import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * List metadata.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 37406 $
 */
public class AbstractListMetaData extends AbstractCollectionMetaData implements List
{
   /**
    * Create a new list value
    */
   public AbstractListMetaData()
   {
   }
   
   public void add(int index, Object element)
   {
      collection.add(index, element);
   }

   public boolean addAll(int index, Collection c)
   {
      return collection.addAll(index, c);
   }

   public Object get(int index)
   {
      return collection.get(index);
   }

   public int indexOf(Object o)
   {
      return collection.indexOf(o);
   }

   public int lastIndexOf(Object o)
   {
      return lastIndexOf(o);
   }

   public ListIterator<Object> listIterator()
   {
      return collection.listIterator();
   }

   public ListIterator<Object> listIterator(int index)
   {
      return collection.listIterator(index);
   }

   public Object remove(int index)
   {
      return collection.remove(index);
   }

   public Object set(int index, Object element)
   {
      return set(index, element);
   }

   public List<?> subList(int fromIndex, int toIndex)
   {
      return subList(fromIndex, toIndex);
   }
}