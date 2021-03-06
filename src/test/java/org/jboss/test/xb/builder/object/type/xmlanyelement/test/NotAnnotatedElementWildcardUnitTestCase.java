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
package org.jboss.test.xb.builder.object.type.xmlanyelement.test;

import java.util.Collection;

import javax.xml.XMLConstants;
import javax.xml.namespace.QName;

import junit.framework.Test;

import org.jboss.test.xb.builder.AbstractBuilderTest;
import org.jboss.test.xb.builder.object.type.xmlanyelement.support.NotAnnotatedElementWildcard;
import org.jboss.xb.binding.sunday.unmarshalling.ElementBinding;
import org.jboss.xb.binding.sunday.unmarshalling.ParticleBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SequenceBinding;
import org.jboss.xb.binding.sunday.unmarshalling.TermBinding;
import org.jboss.xb.binding.sunday.unmarshalling.TypeBinding;
import org.jboss.xb.binding.sunday.unmarshalling.WildcardBinding;
import org.jboss.xb.builder.JBossXBBuilder;
import org.jboss.xb.builder.runtime.DOMHandler;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * ElementWildcardUnitTestCase.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class NotAnnotatedElementWildcardUnitTestCase extends AbstractBuilderTest
{
   public static Test suite()
   {
      return suite(NotAnnotatedElementWildcardUnitTestCase.class);
   }
   
   public NotAnnotatedElementWildcardUnitTestCase(String name)
   {
      super(name);
   }

   public void testUnmarshalWildcard() throws Exception
   {
      NotAnnotatedElementWildcard result = unmarshalObject(NotAnnotatedElementWildcard.class);
      Element element = result.getElement();
      assertNotNull(element);
      assertEquals("element", element.getNodeName());
      NodeList childNodes = element.getChildNodes();
      assertNotNull(childNodes);
      assertEquals(1, childNodes.getLength());
      element = (Element) childNodes.item(0);
      assertEquals("test-child-element", element.getNodeName());
   }

   public void testWildcardBinding() throws Exception
   {
      SchemaBinding schemaBinding = JBossXBBuilder.build(NotAnnotatedElementWildcard.class);
      assertNotNull(schemaBinding);
      
      QName qName = new QName(XMLConstants.NULL_NS_URI, "not-annotated-element-wildcard");
      ElementBinding element = schemaBinding.getElement(qName);
      assertNotNull(element);
      TypeBinding type = element.getType();
      assertNotNull(type);
      ParticleBinding particle = type.getParticle();
      assertNotNull(particle);
      TermBinding term = particle.getTerm();
      assertNotNull(term);
      term = assertSingleSequence(term);
      
      assertTrue(term.isElement());
      element = (ElementBinding) term;
      assertEquals(new QName("element"), element.getQName());
      term = element.getType().getParticle().getTerm();
      assertTrue(term instanceof SequenceBinding);
      Collection<ParticleBinding> particles = ((SequenceBinding)term).getParticles();
      assertEquals(1, particles.size());
      particle = particles.iterator().next();
      term = particle.getTerm();
      
      assertTrue(term instanceof WildcardBinding);
      type = element.getType();
      WildcardBinding wildcardBinding = type.getWildcard();
      assertNotNull(wildcardBinding);
      assertTrue(term == wildcardBinding);
      assertTrue(wildcardBinding.isProcessContentsSkip());
      assertTrue(DOMHandler.INSTANCE == wildcardBinding.getUnresolvedCharactersHandler());
      assertTrue(DOMHandler.INSTANCE == wildcardBinding.getUnresolvedElementHandler());
   }
}
