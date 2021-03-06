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
package org.jboss.test.xb.builder.object.mc.test;

import java.util.Set;

import junit.framework.Test;

import org.jboss.test.xb.builder.object.mc.support.model.AbstractBeanMetaData;
import org.jboss.test.xb.builder.object.mc.support.model.factory.GenericBeanFactoryMetaData;

/**
 * AliasTestCase.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public class AliasTestCase extends AbstractMCTest
{
   public AliasTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(AliasTestCase.class);
   }

   protected Object getAlias() throws Exception
   {
      AbstractBeanMetaData bean = unmarshalBean();
      Set<?> aliases = bean.getAliases();
      assertNotNull(aliases);
      assertEquals(1, aliases.size());
      Object alias = aliases.iterator().next();
      assertNotNull(alias);
      return alias;
   }

   public void testAlias() throws Exception
   {
      Object alias = getAlias();
      assertEquals("SimpleAlias", alias);
   }

   /* TODO
   public void testAliasWithClass() throws Exception
   {
      Object alias = getAlias();
      assertEquals(12345, alias);
   } */

   /* TODO
   public void testAliasWithReplace() throws Exception
   {
      SecurityManager sm = suspendSecurity();
      try
      {
         System.setProperty("alias.test.name", "SimpleAlias");
         Object alias = getAlias("AliasWithReplace.xml");
         assertEquals("XSimpleAliasX", alias);
      }
      finally
      {
         resumeSecurity(sm);
      }
   }
   */

   /* TODO
   public void testAliasWithNoReplace() throws Exception
   {
      SecurityManager sm = suspendSecurity();
      try
      {
         System.setProperty("alias.test.name", "SimpleAlias");
         Object alias = getAlias("AliasWithNoReplace.xml");
         assertEquals("X${alias.test.name}X", alias);
      }
      finally
      {
         resumeSecurity(sm);
      }
   }
   */

   public void testMultipleAlias() throws Exception
   {
      AbstractBeanMetaData bean = unmarshalBean();
      Set<?> aliases = bean.getAliases();
      assertNotNull(aliases);
      int size = aliases.size();
      assertTrue(size > 1);
      for(Object alias : aliases)
         assertNotNull(alias);
   }

   public void testAliasWithBeanFactory() throws Exception
   {
      GenericBeanFactoryMetaData bean = unmarshalBeanFactory();
      Set<?> aliases = bean.getAliases();
      assertNotNull(aliases);
      assertFalse(aliases.isEmpty());
   }

}
