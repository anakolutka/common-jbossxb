/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.xb.builder.runtime;

import javax.xml.namespace.QName;

import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.xb.spi.BeanAdapter;

/**
 * SimplePropertyHandler.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class SimplePropertyHandler extends AbstractPropertyHandler
{
   /**
    * Create a new PropertyHandler.
    * 
    * @param propertyInfo the property
    * @param propertyType the property type
    * @throws IllegalArgumentException for a null property
    */
   public SimplePropertyHandler(PropertyInfo propertyInfo, TypeInfo propertyType)
   {
      super(propertyInfo, propertyType);
   }
   
   @Override
   public void handle(PropertyInfo propertyInfo, TypeInfo propertyType, Object parent, Object child, QName qName)
   {
      try
      {
         BeanAdapter beanAdapter = (BeanAdapter) parent;
         beanAdapter.set(propertyInfo, child);
      }
      catch (Throwable t)
      {
         throw new RuntimeException("QName " + qName + " error setting property " + propertyInfo.getName() + " with value " + BuilderUtil.toDebugString(child) + " to " + BuilderUtil.toDebugString(parent));
      }
   }
}
