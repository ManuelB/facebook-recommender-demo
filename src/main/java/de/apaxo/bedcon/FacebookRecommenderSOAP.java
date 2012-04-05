/*M///////////////////////////////////////////////////////////////////////////////////////
//
//  IMPORTANT: READ BEFORE DOWNLOADING, COPYING, INSTALLING OR USING.
//
//  By downloading, copying, installing or using the software you agree to this license.
//  If you do not agree to this license, do not download, install,
//  copy or use the software.
//
//
//                           License Agreement
//                For de.apaxo.bedcon.FacebookRecommender Bean
//
// Copyright (C) 2012, Apaxo GmbH, all rights reserved.
// Third party copyrights are property of their respective owners.
//
// Redistribution and use in source and binary forms, with or without modification,
// are permitted provided that the following conditions are met:
//
//   * Redistribution's of source code must retain the above copyright notice,
//     this list of conditions and the following disclaimer.
//
//   * Redistribution's in binary form must reproduce the above copyright notice,
//     this list of conditions and the following disclaimer in the documentation
//     and/or other materials provided with the distribution.
//
//   * The name of the copyright holders may not be used to endorse or promote products
//     derived from this software without specific prior written permission.
//
// This software is provided by the copyright holders and contributors "as is" and
// any express or implied warranties, including, but not limited to, the implied
// warranties of merchantability and fitness for a particular purpose are disclaimed.
// In no event shall the Apaxo GmbH or contributors be liable for any direct,
// indirect, incidental, special, exemplary, or consequential damages
// (including, but not limited to, procurement of substitute goods or services;
// loss of use, data, or profits; or business interruption) however caused
// and on any theory of liability, whether in contract, strict liability,
// or tort (including negligence or otherwise) arising in any way out of
// the use of this software, even if advised of the possibility of such damage.
//
//M*/

package de.apaxo.bedcon;

import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.mahout.cf.taste.common.TasteException;

/**
 * This class implements a SOAP interface for the
 * recommender using JAX-WS. It is tested
 * with the reference implementation Metro by the Oracle
 * Corporation former Sun Microsystems.
 * 
 * For more information read:
 * The Java EE 6 Tutorial - Chapter 12 - Building Web Services with JAX-WS
 * 
 * URL:
 * http://localhost:8080/FacebookRecommenderSOAPService/FacebookRecommenderSOAP?Tester
 * WSDL:
 * http://localhost:8080/FacebookRecommenderSOAPService/FacebookRecommenderSOAP?wsdl
 * 
 * @author Manuel Blechschmidt <blechschmidt@apaxo.de>
 *
 */
@Stateless
@WebService
public class FacebookRecommenderSOAP {

	/**
	 * Log class which is used for sophisticated error
	 * logging.
	 */
	private Logger log = Logger.getLogger(FacebookRecommenderSOAP.class.getName());
	
	/**
	 * Inject the recommender bean by using the a no-interface
	 * view. javaeetutorial6.pdf Page: 268:
	 * Accessing Local Enterprise Beans Using the No-Interface View
	 */
	@EJB
	private FacebookRecommender facebookRecommender;

	/**
	 * SOAP interface for recommendation implementation. The string array
	 * will be serialized in the SOAP message according to the JAXB default type mapping.
	 * More information can be read in JAXB default data type bindings in the Java EE 5 tutorial.
	 * 
	 * @param personName the person for which recommendations are required
	 * @return an array of strings with recommendations
	 * @throws TasteException if something goes wrong a TasteException is thrown
	 */
	@WebMethod
	public String[] recommendThings(String personName) throws TasteException {
		log.fine("SOAP recommendThings called with "+personName);
		return facebookRecommender.recommendThings(personName);
	}
	 
}
