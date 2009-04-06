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

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision: 37406 $
 */
public class AbstractSupplyMetaData extends AbstractFeatureMetaData
   implements SupplyMetaData
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   /** The supply */
   protected Object supply;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new supply
    */
   public AbstractSupplyMetaData()
   {
   }

   /**
    * Create a new supply
    * 
    * @param supply the supply
    */
   public AbstractSupplyMetaData(Object supply)
   {
      this.supply = supply;
   }
   
   // Public --------------------------------------------------------
   
   /**
    * Set the supply
    * 
    * @param supply the supply
    */
   public void setSupply(Object supply)
   {
      this.supply = supply;
   }

   // SupplyMetaData implementation ---------------------------------

   public Object getSupply()
   {
      return supply;
   }
   
   // JBossObject overrides -----------------------------------------
   
   public void toString(StringBuffer buffer)
   {
      buffer.append("supply=").append(supply);
   }
   
   public void toShortString(StringBuffer buffer)
   {
      buffer.append(supply);
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
