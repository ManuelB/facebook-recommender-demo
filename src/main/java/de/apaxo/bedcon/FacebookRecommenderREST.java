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
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.apache.mahout.cf.taste.common.TasteException;

/**
 * This class implements a REST interface to the recommender.
 * 
 * URL:
 * http://localhost:8080/facebook-recommender-demo/FacebookRecommender/person/Manuel%20Blechschmidt
 * 
 * @author Manuel Blechschmidt <blechschmidt@apaxo.de>
 */
@Stateless
@Path("/FacebookRecommender")
public class FacebookRecommenderREST {

	/**
	 * Log class which is used for sophisticated error
	 * logging.
	 */
	private Logger log = Logger.getLogger(FacebookRecommenderREST.class.getName());
	
	@EJB
	private FacebookRecommender facebookRecommender;
	
	/**
	 * A REST interface for accessing the recommender.
	 * It just returns the array which was glued together.
	 * @param personName
	 * @return
	 * @throws TasteException
	 */
	@GET
	@Produces("text/plain")
	@Path("/person/{personName}")
	public String recommendThings(@PathParam("personName") String personName) throws TasteException {
		log.fine("REST recommendThings called with "+personName);
		StringBuffer buf = new StringBuffer();
		for(String rec : facebookRecommender.recommendThings(personName)) {
			buf.append(rec+"\n");
		}
		return buf.toString();
	}
}
