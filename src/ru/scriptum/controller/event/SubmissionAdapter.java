package ru.scriptum.controller.event;


/**
 * Insert the type's description here.
 * Creation date: (7/29/2002 10:05:46 AM)
 * @author: Administrator
 */
public class SubmissionAdapter {
//    //extends com.curagen.wms.workflow.object.WorkflowAdapter
//     extends ru.scriptum.controller.event.TheatreAdapter
//    implements
//        ru.scriptum.controller.event.TheatreListener,
//        ru.scriptum.controller.event.TheatreDelegate {
//    protected MSSubmissionRequest msSubmissionRequest; // was private vb 12/16
//    //public static java.lang.String RESULT_URL_PARAM = "resultUrl";
//    /**
//     * Insert the method's description here.
//     * Creation date: (8/1/2002 3:58:09 PM)
//     * @return java.lang.Object
//     */
//    public Object findByRequest(com.webgain.integrator.sessions.UnitOfWork uow, Request request, Class clss) {
//        Object r = uow.executeQuery("findByRequest", clss, request);
//
//        return r;
//    }
//    /**
//     * Insert the method's description here.
//     * Creation date: (8/1/2002 3:58:09 PM)
//     * @return java.lang.Object
//     */
//    public Object findByRequest(
//        ClientSession clientSession,
//        Request request,
//        Class clss) {
//        Object r = clientSession.executeQuery("findByRequest", clss, request);
//
//        return r;
//    }
///**
// * Insert the method's description here.
// * Creation date: (2/12/2003 5:45:58 PM)
// * @return java.lang.String
// * @param number double
// */
//private String formatNumber(double number) {
//    String formattedNumber = String.valueOf(number);
//    int decIndex = formattedNumber.indexOf(".");
//    if (decIndex >= 0) {
//	    int endIndex;
//	    if (decIndex+4 < formattedNumber.length())
//	        formattedNumber = formattedNumber.substring( 0, decIndex+5 );
//    }
//    return formattedNumber;
//}
///**
// * get the domain info for the current requests.
// * This will only be called by StartRequestHandler.class, which means by "StartRequest" Operation
// * Creation date: (7/29/2002 10:05:46 AM)
// * @return java.lang.String the xml data for domain object
// * @param clientSession com.webgain.integrator.threetier.ClientSession
// * @param req com.curagen.wms.request.eo.Request
// * @param wfo com.curagen.wms.workflow.object.WorkflowOperation
// * @param com.curagen.wms.mgr.WorkflowTransactionData
// */
//public String getInitXmlString(
//    ClientSession clientSession,   
//    com.curagen.wms.request.eo.Request[] reqs,
//    com.curagen.wms.workflow.object.WorkflowOperation wfo,
//    com.curagen.wms.mgr.WorkflowTransactionData tdata) {
//    StringBuffer bXml = new StringBuffer();
//    StringBuffer desc;
//
//    try {
//        //core.util.Logger.log(getClass() + ".getInitXmlString starting");
//        String step = (String) tdata.getInputParam("step");
//        if (step == null || step.length() < 1) {
//            Sql s = new Sql(
//            /***
//            "select dr.*, p.sample_id, p.plasmid_id, pb.batch_id, pb.strain_id, es.abbrev "
//                + "from wfdrugdv.pp_dialysis_request dr, "
//                + "     wfdrugdv.pp1_protein_batch pb, "
//                + "     wfdrugdv.plasmid p, "
//                + "     wfdrugdv.se_expression_system es "
//                + "where dr.status = 'Done'"   
//                + "  and dr.request_id = pb.dialysis_request_id"
//                + "  and pb.plasmid_id = p.plasmid_id"
//                + "  and pb.expression_system_id = es.exp_id");
//                ***/ " select /*+ use_nl (dr pb p es mp) */ "
//            + "        distinct dr.*,  "
//            + "        p.sample_id, p.plasmid_id,  "
//            + "        pb.batch_id, pb.strain_id, es.abbrev, "
//            + "        nvl(mp.clone_id, -1) clone_id "
//            + "   from wfdrugdv.pp_dialysis_request dr,  "
//            + "        wfdrugdv.pp1_protein_batch pb,  "
//            + "        wfdrugdv.plasmid p,  "
//            + "        wfdrugdv.se_expression_system es, "
//            + "        wfdrugdv.se_midiprep_map mp "
//            + "  where dr.status = 'Done' "
//            + "    and dr.request_id = pb.dialysis_request_id "
//            + "    and pb.plasmid_id = p.plasmid_id "
//            + "    and pb.expression_system_id = es.exp_id "
//            + "    and p.name = mp.plasmid_num(+) ");
//
//            Hashtable descriptions = new Hashtable();
//            while (s.next()) {
//                desc = new StringBuffer();
//                desc.append(wf.dd.object.SEWFProcess.sampleIdToCGUID(s.getInt("sample_id")));
//                desc.append("::");
//                desc.append(s.getInt("plasmid_num"));
//                desc.append("::");
//                desc.append(s.getString("abbrev"));
//                desc.append("::");
//                if (s.getString("strain_id") != null) {
//                    desc.append(s.getString("strain_id"));
//                } else
//                    desc.append("n/a");
//                desc.append("::");
//                desc.append(s.getInt("clone_id"));
//                descriptions.put(desc.toString(), "" + s.getInt("batch_id"));
//                ////bXml.append("<ProteinBatch description=\"" + desc + "\">");
//                ////bXml.append(s.getInt("batch_id"));
//                ////bXml.append("</ProteinBatch>");
//            }
//            s.close();
//            //java.util.Collections.sort( descriptions );
//            Enumeration e = descriptions.keys();
//            Vector keys = new Vector();
//            while (e.hasMoreElements()) {
//                keys.addElement((String) e.nextElement());
//                /***
//                bXml.append("<ProteinBatch description=\"" + key + "\">");
//                bXml.append( (String) descriptions.get(key) );
//                bXml.append("</ProteinBatch>");
//                ***/
//            }
//            // Start with an empty protein batch option
//            bXml.append("<ProteinBatch description=\"" + "None" + "\">");
//            bXml.append( "                  " );
//            bXml.append("</ProteinBatch>");            
//            java.util.Collections.sort(keys);
//            Iterator i = keys.iterator();
//            while (i.hasNext()) {
//                String description = (String) i.next();
//                bXml.append("<ProteinBatch description=\"" + description + "\">");
//                bXml.append((String) descriptions.get(description));
//                bXml.append("</ProteinBatch>");
//            }
//
//            Sql sh = new Sql("select * from wfms.sample_type_dict");
//            while (sh.next()) {
//                bXml.append("<SampleType description=\"" + sh.getString("name") + "\">");
//                bXml.append(sh.getInt("sample_type_id"));
//                bXml.append("</SampleType>");
//            }
//            sh.close();
//            bXml.append("<step>1</step>"); // first page   
//        } else {  // we are processing page 2
//	        UnitOfWork uow = clientSession.acquireUnitOfWork();
//	        String cloneId = (String) tdata.getInputParam("cloneid");
//	        boolean proteinFound = (cloneId != null && cloneId.length() > 0 && !cloneId.equalsIgnoreCase("undefined"));
//	        bXml.append("<proteinBatch>");
//	        if (proteinFound) {
//				PP1ProteinBatch proteinBatch = PP1ProteinBatch.findByPK(Integer.parseInt((String) tdata.getInputParam("batchId")), uow);
//	  			bXml.append( proteinBatch.getXmlString() );  
//	        }
//  			else {
//	  			bXml.append( "None" );
//				String cguid = (String) tdata.getInputParam("cguid");
//				if (cguid != null && cguid.length() > 0 && ! cguid.equalsIgnoreCase("undefined")) {
//					bXml.append("<CGUID>");
//					bXml.append( cguid );
//					bXml.append("</CGUID>");
//				} 
//				String plasmid = (String) tdata.getInputParam("plasmid");
//				if (plasmid != null && plasmid.length() > 0 && ! plasmid.equalsIgnoreCase("undefined")) {
//					bXml.append("<Name>");
//					bXml.append( plasmid );
//					bXml.append("</Name>");
//				} 
//  			}
//			bXml.append("</proteinBatch>"); 	  			
//	        bXml.append("<cloneId>");
//	        if (proteinFound) {
//    			//cloneId = tdata.getInputParameters().getParameterString("cloneid");
//    			cloneId = removeNonDigits(cloneId);
//    			//int index = cloneId.indexOf(' ');
//    			//if (index > 0)
//    			//	cloneId = cloneId.substring(0, index);	        
//	        	bXml.append( cloneId );
//	        }
//	        else
//	        	bXml.append( "None" );   
//	        bXml.append("</cloneId>");	
//	        bXml.append("<batchId>");
//	        bXml.append( (String) tdata.getInputParam("batchId") );
//	        bXml.append("</batchId>");	
//	        bXml.append("<barcode>");
//	        bXml.append( (String) tdata.getInputParam("barcode") );
//	        bXml.append("</barcode>");
//	        bXml.append("<sampleTypeId>");
//	        bXml.append( (String) tdata.getInputParam("sampleTypeId") );
//	        bXml.append("</sampleTypeId>");
//	        bXml.append("<sampleTypeDescription>");
//			SampleType sType = SampleType.findByPK(Integer.parseInt((String) tdata.getInputParam("sampleTypeId")), uow);	
//	        bXml.append( sType.getName() );
//	        bXml.append("</sampleTypeDescription>");	
//	        bXml.append("<nextQoId>");
//	        bXml.append( (String) tdata.getInputParam("nextQoId") );
//	        bXml.append("</nextQoId>");	   
//	        bXml.append("<sourceExpressionSystem>");
//	        bXml.append( (String) tdata.getInputParam("sourceExpressionSystem") );
//	        bXml.append("</sourceExpressionSystem>");
//	        bXml.append("<apparentMolecularWeight>");
//	        bXml.append( (String) tdata.getInputParam("apparentMolecularWeight") );
//	        bXml.append("</apparentMolecularWeight>");	
//	        bXml.append("<predictedMolecularWeight>");
//	        bXml.append( (String) tdata.getInputParam("predictedMolecularWeight") );
//	        bXml.append("</predictedMolecularWeight>");
//	        NumberFormat numberFormat = NumberFormat.getInstance();
//	        numberFormat.setMaximumFractionDigits(4);
//			// new
//			String volume = (String) tdata.getInputParam("volume");
//			double volumeValue = 0;
//			if (volume != null && volume.length() > 0) {
//				volumeValue = Double.valueOf(volume).doubleValue();
//			}
//			String concentrationMg = (String) tdata.getInputParam("concentrationMg");
//			double concentrationMgValue = 0;
//			if (concentrationMg != null && concentrationMg.length() > 0) {
//				concentrationMgValue = Double.valueOf(concentrationMg).doubleValue();
//			}			
//			String apparentMw = (String) tdata.getInputParam("apparentMolecularWeight");
//			double apparentMwValue = 0;
//			if ( apparentMw != null && apparentMw.length() > 0 ) {
//				apparentMwValue = Double.valueOf(apparentMw).doubleValue();
//			}
//			// note: we will throw an exception if apparent molecular weight is zero
//			double amountMgValue = concentrationMgValue * volumeValue;
//			String formattedAmountMg = formatNumber(amountMgValue);
//			//String formattedAmountMg = numberFormat.format(amountMgValue);
//			double concentrationPmValue = (concentrationMgValue * 1000000) / apparentMwValue;
//			String formattedConcentrationPm = formatNumber(concentrationPmValue);
//			double amountPmValue = concentrationPmValue * volumeValue;
//			String formattedAmountPm = formatNumber(amountPmValue);
//			numberFormat.format(amountPmValue);
//			bXml.append("<volume>");
//			bXml.append( volume );
//			bXml.append("</volume>");
//	        bXml.append("<amountMicrograms>");
//	        bXml.append( formattedAmountMg );
//	        bXml.append("</amountMicrograms>");
//	        bXml.append("<amountPicomoles>");
//	        bXml.append( formattedAmountPm );
//	        bXml.append("</amountPicomoles>");
//	        bXml.append("<concentrationMg>");
//	        bXml.append( "" + concentrationMgValue );
//	        bXml.append("</concentrationMg>");	
//	        bXml.append("<concentrationPm>");
//	        bXml.append( formattedConcentrationPm );
//	        bXml.append("</concentrationPm>");		         
//	        bXml.append("<costCenter>");
//	        bXml.append( (String) tdata.getInputParam("costCenter") );
//			bXml.append("</costCenter>");
//	        bXml.append("<step>2</step>"); // second page
//	        bXml.append("<lastStep>true</lastStep>");
//        }
//        //core.util.Logger.log(getClass() + ".getInitXmlString done");
//        bXml.append("<validate>true</validate>");
//        return bXml.toString();
//    } catch (Exception ex) {
//        core.util.Logger.log(
//            getClass() + ".getInitXmlString() got exception: " + ex.getMessage());
//        core.util.Logger.logStackTrace(ex);
//        return new String("<error>" + ex.getMessage() + "</error>");
//    }
//}
///**
// * Insert the method's description here.
// * Creation date: (8/5/2002 5:08:06 PM)
// */
//public WorkflowOperation[] getNextWorkflowOperations(
//    WorkflowOperation wfo,
//    boolean requeue)            
//    throws Exception {
//   	try {       
//        WorkflowOperation[] nextWfOps;  
//        //for the Submission UO, the next UOs are sample type dependent   
//        if (wfo.getOperationDesc().equals("Submission")) {
//            int sTypeId =
//                msSubmissionRequest.getMSSubmission().getSampleType().getSampleTypeId();
//            if (sTypeId == 1 && !requeue) {  // the ! requeue added vbrecher 12/27/02
//	            // Force the gels to go through the Protein Digestion operation
//                // Not working!!!
//                //nextWfops = new WorkflowOperation[1];
//                //nextWfops[0] = wfo.getNextWorkflowOperations(requeue)[0];
//                nextWfOps = wfo.getNextWorkflowOperations(requeue);
//                for (int i=0; i<nextWfOps.length; i++) {
//	                WorkflowOperation wfop = nextWfOps[i];
//	                if ( wfop.getOperationDesc().equalsIgnoreCase( "Protein Digestion" ) ) {
//		                WorkflowOperation[] ops = new WorkflowOperation[1];
//		                ops[0] = wfop;
//		                return ops;
//	                }
//	                
//                } 
//            } else {
//                nextWfOps = wfo.getNextWorkflowOperations(requeue);
//            }
//        } else
//            // for everything else next UOs are taken from the WMS
//            nextWfOps = wfo.getNextWorkflowOperations(requeue);
//        return nextWfOps;
//    } catch (Exception ex) {
//        throw ex;
//    } 
//    //return wfo.getNextWorkflowOperations(requeue);
//}
///**
// * domain object tell WMS how many new requests has to be generated for that next queue operation
// * Creation date: (06/11/2002 5:00:01 PM)
// * @return int the number of new request need to be generated for that next queue operation nextWfo
// * @param nextWfo wf.wms.workflow.object.WorkflowOperation the next queue operation
// * @param requeue boolean
// */
//public int getNumOfNextWorkflowOperationRequests(
//    Request[] parentRequests,
//    WorkflowOperation nextWfo,
//    boolean requeue,
//    ClientSession clientSession,
//    WorkflowTransactionData tData)
//    throws java.lang.Exception {
//
//	/***
//    try {
//        if (nextWfo.getOperationOrder() == 1) {
//            String sampleTypeId =
//                tData.getInputParameters().getParameterString("sampletypeid");
//            if (sampleTypeId != null && sampleTypeId.length() >= 1) {
//	            if (Integer.parseInt( sampleTypeId ) == 2)
//                	return 2;
//            }
//			else return 1;
//        }
//
//    } catch (Exception ex) {
//        core.util.Logger.log(ex.getMessage());
//        throw ex;
//    }
//    ***/
//    return 1;
//}
///**
// * The request description for all requests related to that Queue Operation
// * It is used by ShowQueueHandler
// * @return Hashtable the request descriptions, the key is requestId String, the element is the detail
// * request description for that particular request 
// * @param clientSession ClientSession
// * @param wfo com.curagen.wms.workflow.object.WorkflowOperation
// * @exception java.lang.Exception
// */
//public java.util.Hashtable getRequestDescriptions(
//    ClientSession clientSession,
//    com.curagen.wms.workflow.object.WorkflowOperation wfo)
//    throws Exception {
//    java.util.Hashtable data = new java.util.Hashtable();
//
//    try {
//        Vector requests = new Vector();
//        Vector qorsv = wfo.getQueueOperation().getQueueOperationRequestStatuses();
//        for (int i = 0; i < qorsv.size(); i++) {
//            com.curagen.wms.structure.eo.QueueOperationRequestStatus qors =
//                (QueueOperationRequestStatus) qorsv.elementAt(i);
//            requests.addAll(qors.getActiveRequests(clientSession));
//        }
//
//        //java.util.Vector list = clientSession.readAllObjects(MSSubmissionRequest.class);
//        for (int i = 0; i < requests.size(); i++) {
//            Request req = (Request) requests.elementAt(i);   
//            MSSubmissionRequest mSubRequest =
//                MSSubmissionRequest.findByRequest(req, clientSession);
//            if (mSubRequest != null) { // temporary!!!!!!!
//                StringBuffer description = new StringBuffer();
//                description.append("  ");
//                description.append(mSubRequest.getMSSubmission().getDescription());
//                //description.append( "    " );
//
//                //description.append( "<![CDATA[" );
//                //description.append( mSubRequest.getMSSubmission().getLink() );
//                //description.append( "]]>" );	
//
//                data.put(
//                    new Integer(mSubRequest.getRequest().getRequestId()),
//                    description.toString());
//                //" " + mSubRequest.getMSSubmission().getDescription());
//
//            }
//        }
//    } catch (Exception ex) {
//        core.util.Logger.log(ex.getMessage());
//        core.util.Logger.logStackTrace(ex);
//    }
//    return data;
//}
///**
// * get the corresponding style sheet based upon the given reqs, wfo, and particular operation
// * this method is called by StartRequestHandler class and RequestHandler class
// * Creation date: (7/29/2002 10:05:46 AM)
// * @return java.lang.String the style sheet
// * @param wfo com.curagen.wms.workflow.object.WorkflowOperation
// * @param operation java.lang.String the operation which is define in com.curagen.wms.util.WMSConstants
// */
//public String getStyleSheet(
//    com.curagen.wms.request.eo.Request[] reqs,
//    com.curagen.wms.workflow.object.WorkflowOperation wfo,
//    String operation) {
//    if (com.curagen.wms.util.WMSConstants.START_REQUEST_OPERATION.equals(operation)) 
//        return "wf/ms/MSCreateSubmission";
//    else
//        if (com.curagen.wms.util.WMSConstants.FSM_START_REQUEST_OPERATION.equals(operation))
//            return "wf/ms/FSMMSCreateSubmission";
//        else
//            if (com.curagen.wms.util.WMSConstants.FSM_REQUEST_OPERATION.equals(operation))
//                return "wf/ms/FSMMSRequest";
//            else
//                if (com.curagen.wms.util.WMSConstants.REQUEST_OPERATION.equals(operation))
//                    return "wf/ms/MSRequest";
//                else
//                    if (com.curagen.wms.util.WMSConstants.SHOW_QUEUE_OPERATION.equals(operation))
//                        return "wf/ms/MSShowQueue";
//    return null;
//}
///**
// * Insert the method's description here.
// * Creation date: (6/30/2003 3:42:57 PM)
// * @return ru.scriptum.controller.event.TheatreEvent[]
// * @param workflowEvent ru.scriptum.controller.event.TheatreEvent
// * @param tData com.curagen.wms.mgr.WorkflowTransactionData
// * @param session com.webgain.integrator.publicinterface.Session
// */
//public TheatreEvent[] getTheatreEvents(com.webgain.integrator.publicinterface.Session session) {
//	return null;
//}
//    /**
//     * get the description for that particular wfo. This method is called by ShowQueueHandler
//     * One typical use of this is showing the link for a hidden WorkflowOperation
//     * @param wfo com.curagen.wms.workflow.object.WorkflowOperation
//     * @return String the description for that particular wfo
//     */
//    public String getWorkflowOperationDescription(
//        com.curagen.wms.workflow.object.WorkflowOperation wfo) {
//	    try {
//			if (wfo.getOperationDesc().indexOf("Report Generation") >= 0) {
//				StringBuffer buff = new StringBuffer();
//		   		buff.append(
//      				"<a href='wf.ms.servlet.MSServlet?op=Report&#038;queueId=");
//		   		buff.append( wfo.getQueueId() );
//		   		buff.append("'>");
//		   		buff.append("Generate Report");
//		   		buff.append("</a>");
//		   		return buff.toString();
//			}
//			return null;
//	    } catch( Exception ex ) {
//			core.util.Logger.log( ex.getMessage() );
//			return null;
//	    }
//		    
//   }
///**
// * get the domain info for the current requests.
// * This will only be called by RequestHandler.class, which means by "Request" Operation
// * Creation date: (7/29/2002 10:05:46 AM)
// * @return java.lang.String the domain info
// * @param clientSession com.webgain.integrator.threetier.ClientSession
// * @param req com.curagen.wms.request.eo.Request
// * @param wfo com.curagen.wms.workflow.object.WorkflowOperation
// * @param com.curagen.wms.mgr.WorkflowTransactionData
// */
//public String getXmlString(
//    ClientSession clientSession,
//    com.curagen.wms.request.eo.Request req,
//    com.curagen.wms.workflow.object.WorkflowOperation wfo,
//    com.curagen.wms.mgr.WorkflowTransactionData tdata)
//    {
//    StringBuffer xml = new StringBuffer();
//
//    try {
//        //core.util.Logger.log(getClass() + ".getXmlString starting");
//
//        if (msSubmissionRequest.getMSSubmission().getProteinBatch() != null)
//            xml.append(
//                msSubmissionRequest.getMSSubmission().getProteinBatch().getXmlString());
//        else {
//            xml.append("<ProteinBatch>");
//            xml.append("<CGUID>");
//			String cguid = msSubmissionRequest.getMSSubmission().getCguid();
//			if (cguid != null && cguid.length() > 0)
//				xml.append( cguid );
//			else
//				xml.append( "None" );
//            xml.append("</CGUID>");
//            xml.append("<Name>");
//			String plasmid = msSubmissionRequest.getMSSubmission().getPlasmid();
//			if (plasmid != null && plasmid.length() > 0)
//				xml.append( plasmid );
//			else
//				xml.append("None");
//            xml.append("</Name>");            
//            xml.append("</ProteinBatch>");
//        }
//        xml.append(
//            msSubmissionRequest.getMSSubmission().getSampleType().getXmlString());
//        xml.append(msSubmissionRequest.getMSSubmission().getXmlString());
//        //if (req.getQueueOperation().getEndOfQueue()) {
//        //    xml.append("<" + RESULT_URL_PARAM + "></" + RESULT_URL_PARAM + ">");
//        //}
//
//        String desc = wfo.getQueueOperation().getOperation().getOperationDesc();
//
//        // If this is MALDI of Intact Protein set link to image upload
//        if (desc.indexOf("VOYAGER MALDI") >= 0) {
//            xml.append("<VoyagerMALDI/>");
//            //RequestFile reqFile = (RequestFile) FileHandler.findByRequestAndOrder(clientSession, req.getRequestId(), 1);
//            RequestFile reqFile =
//                (RequestFile) FileHandler.findByRequestAndOrder(
//                    clientSession,
//                    req,
//                    1,
//                    com.curagen.wms.mgr.WorkflowTransactionManager.getServerSessionName(
//	                 tdata.getTableSpaceId(), 
//	                 tdata.getUserId()));
//
//            if (reqFile != null) {
//                xml.append(reqFile.getXmlString());
//            } else {
//                xml.append(
//                    FileHandler.getUploadUrlXml(
//                        tdata.getTableSpaceId(),
//                        req.getRequestId(),
//                        1,
//                        null,
//                        "Upload"));
//                // user specified type
//            }
//        } else
//            if (desc.indexOf("Database Search") >= 0) {
//                xml.append("<DatabaseSearch/>");
//            } else
//                if (desc.indexOf("Data Analysis") >= 0) {
//                    //MSSubmissionResult msSubmissionResult =
//                    Vector results =
//                        (Vector) (Vector) clientSession.readAllObjects(
//                            MSSubmissionResult.class,
//                            new ExpressionBuilder().get("msSubmission").equal(
//                                msSubmissionRequest.getMSSubmission()));
//                    if (results != null && results.size() > 0) {
//                        Iterator i = results.iterator();
//                        while (i.hasNext()) {
//                            MSSubmissionResult result = (MSSubmissionResult) i.next();
//
//                            try {
//                                String maldiResultUrl = result.getMaldiResultUrl();
//                                if (maldiResultUrl != null) {
//                                    xml.append("<resultUrl>");
//                                    xml.append("<![CDATA[");
//                                    xml.append(maldiResultUrl);
//                                    xml.append("]]>");
//                                    xml.append("</resultUrl>");
//                                }
//                                String mascotResultUrl = result.getMascotResultUrl();
//                                if (mascotResultUrl != null) {
//                                    xml.append("<resultUrl>");
//                                    xml.append("<![CDATA[");
//                                    xml.append(mascotResultUrl);
//                                    xml.append("]]>");
//                                    xml.append("</resultUrl>");
//                                }
//                            } catch (Exception ex) {
//
//                            }
//                        }
//                    }
//                } else
//                    if (desc.indexOf("Submission") >= 0) {
//                        if (req.getRequestStatusId() == 3)
//                            xml.append("<SubmissionSubmit/>");
//                        else
//                            xml.append("<SubmissionReceive/>");
//                    }
//
//        //core.util.Logger.log(getClass() + ".getXmlString done");
//        return xml.toString();
//    } catch (Exception e) {
//        core.util.Logger.log(e.getMessage());
//        core.util.Logger.logStackTrace(e);
//        StringBuffer buff = new StringBuffer();
//        buff.append("<wmsError>");
//        buff.append(e.getMessage());
//        buff.append("</wmsError>");
//        return buff.toString();
//
//    }
//}
///**
// * Insert the method's description here.
// * Creation date: (6/30/2003 3:06:30 PM)
// * @return ru.scriptum.controller.event.TheatreEvent[]
// * @param workflowEvent ru.scriptum.controller.event.TheatreEvent
// * @param session com.webgain.integrator.publicinterface.Session
// */
//public TheatreEvent[] handleTheatreEvent(
//    TheatreEvent workflowEvent,
//    WorkflowTransactionData wtd,
//    Session session) {
//    try {
//        if (workflowEvent instanceof StartRequestEvent) {
//            setDomain(
//                (UnitOfWork) session,
//                ((StartRequestEvent) workflowEvent).getParentRequests(),
//                ((StartRequestEvent) workflowEvent).getRequests(),                
//                wtd,
//                1);
//        }
//    } catch (Exception e) {
//    }
//    return null;
//}
//    /**
//     * initialize the domain object for given request
//     * Creation date: (7/29/2002 10:05:46 AM)
//     * @param req com.curagen.wms.request.eo.Request
//     */
//    public void init(
//        com.webgain.integrator.sessions.UnitOfWork uow,
//        com.curagen.wms.request.eo.Request req) {
//        if (msSubmissionRequest == null)
//            msSubmissionRequest =
//                (MSSubmissionRequest) findByRequest(uow, req, MSSubmissionRequest.class);
//    }
//    /**
//     * initialize the domain object for given request
//     * Creation date: (7/29/2002 10:05:46 AM)
//     * @param req com.curagen.wms.request.eo.Request
//     */
//    public void init(
//        ClientSession clientSession,
//        com.curagen.wms.request.eo.Request req) {
//        if (msSubmissionRequest == null)
//            msSubmissionRequest =
//                (MSSubmissionRequest) findByRequest(clientSession,
//                    req,
//                    MSSubmissionRequest.class);
//    }
//  /**
//   * Insert the method's description here.
//   * Creation date: (08/01/2002 9:49:44 AM)
//   * @return boolean
//   * @param reqs com.curagen.wms.request.eo.Request[]
//   * @param wfo com.curagen.wms.workflow.object.WorkflowOperation[]
//   * @param op java.lang.String
//   * @param tData com.curagen.wms.mgr.WorkflowTransactionData
//   */
//  public boolean isToShowQueue(
//    Request[] reqs,
//    com.curagen.wms.workflow.object.WorkflowOperation wfo,
//    WorkflowTransactionData tData,
//    String op) {
//    if (com.curagen.wms.util.WMSConstants.START_REQUEST_OPERATION.equals(op))
//      return true;  // for now
//    else
//      return true;
//  }
///**
// * Insert the method's description here.
// * Creation date: (2/12/2003 5:45:58 PM)
// * @return java.lang.String   
// * @param number double
// */
//private String removeNonDigits(String numberString) {
//	StringBuffer buff = new StringBuffer();
//	for (int i=0; i<numberString.length(); i++) {
//		char digit = numberString.charAt(i);
//		if ('0' <= digit && digit <= '9')
//			buff.append(digit);
//	}
//	return buff.toString();	
//		
//}
///**
// * Save the domain info for the current request
// * This will be only used by RequestHandler class, which means "Request" operation.
// * Creation date: (7/29/2002 10:05:47 AM)
// * @param uow com.webgain.integrator.sessions.UnitOfWork
// * @param req com.curagen.wms.request.eo.Request
// * @param tData com.curagen.wms.mgr.WorkflowTransactionData
// * @param event int
// */
//public void saveListenerForParent(
//    com.webgain.integrator.sessions.UnitOfWork uow,
//    com.curagen.wms.request.eo.Request req,
//    com.curagen.wms.mgr.WorkflowTransactionData tData,
//    int requestEvent)
//    throws Exception {
//
//    if (requestEvent == com.curagen.wms.util.QueueConstants.DROP_REQUEST)
//        return;
//    try {
//        String quop = req.getQueueOperation().getOperation().getOperationDesc();
//        if (quop.indexOf("VOYAGER") >= 0) {
//
//            MSSubmissionRequest msSubmissionRequest =
//                (MSSubmissionRequest) uow.readObject(
//                    MSSubmissionRequest.class,
//                    new ExpressionBuilder().get("request").equal(req));
//
//            MSSubmissionResult msSubResult =
//                (MSSubmissionResult) uow.readObject(
//                    MSSubmissionResult.class,
//                    new ExpressionBuilder().get("msSubmission").equal(
//                        msSubmissionRequest.getMSSubmission()));
//
//            if (msSubResult == null) {
//                msSubResult = (MSSubmissionResult) uow.newInstance(MSSubmissionResult.class);
//                msSubResult.setMaldiResultUrl(
//                    "com.curagen.wms.servlet.WMSServlet?op=Request&requestId="
//                        + req.getRequestId());
//                msSubResult.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                msSubResult.setComments((String) tData.getInputParam("reqcomments"));
//            } else {
//                MSSubmissionResult msSubResultClone =
//                    (MSSubmissionResult) uow.registerObject(msSubResult);
//                msSubResultClone.setMaldiResultUrl(
//                    "com.curagen.wms.servlet.WMSServlet?op=Request&requestId="
//                        + req.getRequestId());
//                String previousComments = msSubResult.getComments();
//                if (previousComments != null) {
//                    previousComments =
//                        previousComments + "   " + tData.getInputParam("reqcomments");
//                    msSubResultClone.setComments(previousComments);
//                }
//            }
//
//            // check if this submission is already in the map
//            PpMsQcMap map =
//                (PpMsQcMap) uow.readObject(
//                    PpMsQcMap.class,
//                    new ExpressionBuilder().get("msSubmission").equal(
//                        msSubmissionRequest.getMSSubmission()));
//            if (map == null
//                && msSubmissionRequest.getMSSubmission().getProteinBatch() != null) {
//                PpMsQcMap ppMsQcMap = (PpMsQcMap) uow.newInstance(wf.dd.pp1.eo.PpMsQcMap.class);
//                ppMsQcMap.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                ppMsQcMap.setQcRequestId(
//                    msSubmissionRequest.getMSSubmission().getProteinBatch().getQcRequestId());
//            }
//
//            String voyagerMolWeight =
//                (String) tData.getInputParam("voyagermolecularweight");
//            if (voyagerMolWeight != null && voyagerMolWeight.length() > 0) {
//                MSSubmission msSub = msSubmissionRequest.getMSSubmission();
//                MSSubmission msSubClone = (MSSubmission) uow.registerObject(msSub);
//                msSubClone.setVoyagerMolecularWeight(
//                    Double.valueOf(voyagerMolWeight).doubleValue());
//            }
//        } else
//            if (quop.indexOf("Database Search") >= 0) {
//                MSSubmissionRequest msSubmissionRequest =
//                    (MSSubmissionRequest) uow.readObject(
//                        MSSubmissionRequest.class,
//                        new ExpressionBuilder().get("request").equal(req));
//
//                MSSubmissionResult msSubResult =
//                    (MSSubmissionResult) uow.readObject(
//                        MSSubmissionResult.class,
//                        new ExpressionBuilder().get("msSubmission").equal(
//                            msSubmissionRequest.getMSSubmission()));
//
//                if (msSubResult == null) {
//                    msSubResult = (MSSubmissionResult) uow.newInstance(MSSubmissionResult.class);
//                    msSubResult.setMascotResultUrl((String) tData.getInputParam("resultUrl"));
//                    msSubResult.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                    msSubResult.setComments((String) tData.getInputParam("reqcomments"));
//                } else {
//                    MSSubmissionResult msSubResultClone =
//                        (MSSubmissionResult) uow.registerObject(msSubResult);
//                    msSubResultClone.setMascotResultUrl((String) tData.getInputParam("resultUrl"));
//                    String previousComments = msSubResult.getComments();
//                    if (previousComments != null) {
//                        previousComments =
//                            previousComments + "   " + tData.getInputParam("reqcomments");
//                        msSubResultClone.setComments(previousComments);
//                    }
//                }
//                /***
//                MSSubmissionResult msSubmissionResult =
//                    (MSSubmissionResult) uow.newInstance(MSSubmissionResult.class);
//                msSubmissionResult.setMascotResultUrl(
//                    (String) tData.getInputParam("resultUrl"));
//                MSSubmissionRequest msSubmissionRequest =
//                    (MSSubmissionRequest) uow.readObject(
//                        MSSubmissionRequest.class,
//                        new ExpressionBuilder().get("request").equal(req));
//                msSubmissionResult.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                ***/
//                // There is no database mapping for result in msSubmission
//                ////msSubmissionRequest.getMSSubmission().setSubmissionResult(msSubmissionResult);
//                // check if this submission is already in the map
//                PpMsQcMap map =
//                    (PpMsQcMap) uow.readObject(
//                        PpMsQcMap.class,
//                        new ExpressionBuilder().get("msSubmission").equal(
//                            msSubmissionRequest.getMSSubmission()));
//                if (map == null
//                    && msSubmissionRequest.getMSSubmission().getProteinBatch() != null) {
//                    PpMsQcMap ppMsQcMap = (PpMsQcMap) uow.newInstance(wf.dd.pp1.eo.PpMsQcMap.class);
//                    ppMsQcMap.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                    ppMsQcMap.setQcRequestId(
//                        msSubmissionRequest.getMSSubmission().getProteinBatch().getQcRequestId());
//                }
//                //PpMsQcMap ppMsQcMap = (PpMsQcMap) uow.newInstance(wf.dd.pp1.eo.PpMsQcMap.class);
//                //ppMsQcMap.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                //ppMsQcMap.setQcRequestId(
//                //    msSubmissionRequest.getMSSubmission().getProteinBatch().getQcRequestId());
//                String coverage = (String) tData.getInputParam("coverage");
//                if (coverage != null && coverage.length() > 0) {
//                    MSSubmission msSub = msSubmissionRequest.getMSSubmission();
//                    MSSubmission msSubClone = (MSSubmission) uow.registerObject(msSub);
//                    msSubClone.setCoverage(Integer.valueOf(coverage).intValue());
//                }
//            } /***else
//            if (quop.indexOf("Submission") >= 0) {
//              MSSubmissionRequest msSubRequest =
//                  (MSSubmissionRequest) uow.readObject(
//                      MSSubmissionRequest.class,
//                      new ExpressionBuilder().get("request").equal(req));
//              MSSubmission msSub = msSubRequest.getMSSubmission();
//              MSSubmission msSubClone = (MSSubmission) uow.registerObject(msSub);
//              String signalSequence = (String) tData.getInputParam("signalSequence");
//              if (signalSequence != null)
//                  msSubClone.setSignalSequence(signalSequence);
//              String nTerminalFusion = (String) tData.getInputParam("nTerminalFusion");
//              if (nTerminalFusion != null)
//                  msSubClone.setNTerminalFusion(nTerminalFusion);
//              String matureProtein = (String) tData.getInputParam("matureProtein");
//              if (matureProtein != null)
//                  msSubClone.setMatureProtein(matureProtein);   
//              String hisTagFusion = (String) tData.getInputParam("hisTagFusion");
//              if (hisTagFusion != null)
//                  msSubClone.setHisTagFusion(hisTagFusion);
//            } ***/
//        /***
//        if (requestEvent == com.curagen.wms.util.QueueConstants.FINISH_REQUEST
//            && req.getQueueOperation().getEndOfQueue()) {
//            init(uow, req);
//            MSSubmissionResult msSubmissionResult =
//                (MSSubmissionResult) uow.newInstance(MSSubmissionResult.class);
//            msSubmissionResult.setResultUrl((String) tData.getInputParam(RESULT_URL_PARAM));
//            msSubmissionResult.setMSSubmission(msSubmissionRequest.getMSSubmission());
//            msSubmissionRequest.getMSSubmission().setSubmissionResult(msSubmissionResult);
//        
//            PpMsQcMap ppMsQcMap = (PpMsQcMap) uow.newInstance(wf.dd.pp1.eo.PpMsQcMap.class);
//            ppMsQcMap.setMSSubmission(msSubmissionRequest.getMSSubmission());
//            ppMsQcMap.setQcRequestId(
//                msSubmissionRequest.getMSSubmission().getProteinBatch().getQcRequestId());
//        }
//        ***/
//    } catch (Exception ex) {
//        core.util.Logger.log(ex.getMessage());
//        core.util.Logger.logStackTrace(ex);
//    }
//}
//    /**
//     * Save the domain info for the current request
//     * This will be only used by StartRequestHandler class, which means "StartRequest" operation.
//     * Creation date: (7/29/2002 10:05:46 AM)
//     * @param uow com.webgain.integrator.sessions.UnitOfWork
//     * @param req com.curagen.wms.request.eo.Request
//     * @param tData com.curagen.wms.mgr.WorkflowTransactionData
//     * @param event int
//     */
//    public void saveListenerForParents(
//        com.webgain.integrator.sessions.UnitOfWork uow,
//        com.curagen.wms.request.eo.Request[] req,
//        com.curagen.wms.mgr.WorkflowTransactionData tdata,
//        int requestEvent)
//        throws Exception {
//    }
///**
// * Save the new domain object info corresponding to the new requests in next queue operations
// * This method will be only called in "Request" operation (RequestHandler class)
// * Creation date: (7/29/2002 10:05:46 AM)
// * @param uow com.webgain.integrator.sessions.UnitOfWork
// * @param oldReq com.curagen.wms.request.eo.Request the old request
// * @param newReqs com.curagen.wms.request.eo.Request[]  the new Requests
// * @param tData WorkflowTransactionData
// * @param requestEvent int
// * @exception java.lang.Exception any exception throwed by domain object.
// */
//public void saveNewListenerForParent(
//    com.webgain.integrator.sessions.UnitOfWork uow,
//    com.curagen.wms.request.eo.Request oldReq,
//    com.curagen.wms.request.eo.Request[] newReqs,
//    com.curagen.wms.mgr.WorkflowTransactionData tdata,
//    int requestEvent)
//    throws Exception {
//    try {
//        for (int i = 0; i < newReqs.length; i++) {
//            MSSubmissionRequest parentSubmissionRequest =
//                (MSSubmissionRequest) this.findByRequest(
//                    uow,
//                    oldReq,
//                    MSSubmissionRequest.class);
//
//            msSubmissionRequest =
//                (MSSubmissionRequest) uow.newInstance(MSSubmissionRequest.class);
//            msSubmissionRequest.setRequest(newReqs[i]);
//
//            //a single MSSubmission is shared among all MSSubmissionRequest objects         
//            msSubmissionRequest.setMSSubmission(parentSubmissionRequest.getMSSubmission());
//            parentSubmissionRequest.getMSSubmission().addSubmissionRequest(
//                msSubmissionRequest);
//        }
//
//    } catch (Exception ex) {
//        throw ex;
//    }
//    //uow.registerNewObject(msSubmissionRequest);
//    //uow.validateObjectSpace();
//    //uow.printRegisteredObjects();
//}
///**
// * Save the new domain object info corresponding to the new requests in next queue operations
// * This method will be only called in "StartRequest" operation (StartRequestHandler class)
// * Creation date: (7/29/2002 10:05:46 AM)
// * @param uow com.webgain.integrator.sessions.UnitOfWork
// * @param oldReq com.curagen.wms.request.eo.Request[] the old requests
// * @param newReqs com.curagen.wms.request.eo.Request[]  the new Requests
// * @param tData WorkflowTransactionData
// * @param requestEvent int
// * @exception java.lang.Exception The exception description.
// */
//public void saveNewListenerForParents(
//    com.webgain.integrator.sessions.UnitOfWork uow,
//    com.curagen.wms.request.eo.Request[] oldReqs,
//    com.curagen.wms.request.eo.Request[] reqs,
//    com.curagen.wms.mgr.WorkflowTransactionData tData,
//    int requestEvent)
//    throws java.lang.Exception {
//
//    try {
//        String batchId =
//            (String) tData.getInputParameters().getParameterString("batchId");
//        if (batchId == null || batchId.length() < 1) {
//            batchId = "0";
//        }
//
//        String sampleTypeId =
//            tData.getInputParameters().getParameterString("sampleTypeId");
//        if (sampleTypeId == null || sampleTypeId.length() < 1) {
//            return;
//        }
//
//        // Amount - added vbrecher 12/26/02 -- set default to 0
//        String amountMicrograms =
//            tData.getInputParameters().getParameterString("amountmicrograms");
//        double amountMicrogramsValue = 0;
//        if (amountMicrograms != null && amountMicrograms.length() > 0) {
//            amountMicrogramsValue = Double.valueOf(amountMicrograms).doubleValue();
//        }
//
//        // Amount - added vbrecher 12/26/02 -- set default to 0
//        String amountPicomoles =
//            tData.getInputParameters().getParameterString("amountpicomoles");
//        double amountPicomolesValue = 0;
//        if (amountPicomoles != null && amountPicomoles.length() > 0) {
//            amountPicomolesValue = Double.valueOf(amountPicomoles).doubleValue();
//        }
//
//        // Volume - added vbrecher 02/11/03 -- set default to 0
//        String volume = tData.getInputParameters().getParameterString("volume");
//        double volumeValue = 0;
//        if (volume != null && volume.length() > 0) {
//            volumeValue = Double.valueOf(volume).doubleValue();
//        }
//
//        // ConcentrationMg - added vbrecher 02/11/03 -- set default to 0
//        String concentrationMg =
//            tData.getInputParameters().getParameterString("concentrationMg");
//        double concentrationMgValue = 0;
//        if (concentrationMg != null && concentrationMg.length() > 0) {
//            concentrationMgValue = Double.valueOf(concentrationMg).doubleValue();
//        }
//
//        // Concentration - added vbrecher 02/11/03 -- set default to 0
//        String concentrationPm =
//            tData.getInputParameters().getParameterString("concentrationPm");
//        double concentrationPmValue = 0;
//        if (concentrationPm != null && concentrationPm.length() > 0) {
//            concentrationPmValue = Double.valueOf(concentrationPm).doubleValue();
//        }
//
//        // Source/Expression System - added vbrecher 12/26/02 -- set default to null
//        String srcExprSystem =
//            tData.getInputParameters().getParameterString("sourceexpressionsystem");
//        if (srcExprSystem == null || srcExprSystem.length() < 0) {
//            srcExprSystem = "";
//        }
//
//        // Predicted molecular weight - added vbrecher 12/26/02 -- set default to 0
//        String predMolWeight =
//            tData.getInputParameters().getParameterString("predictedmolecularweight");
//        double predMolWeightValue = 0;
//        if (predMolWeight != null && predMolWeight.length() > 0) {
//            predMolWeightValue = Double.valueOf(predMolWeight).doubleValue();
//        }
//
//        // Apparent molecular weight - added vbrecher 12/26/02 -- set default to 0
//        String appMolWeight =
//            tData.getInputParameters().getParameterString("apparentmolecularweight");
//        double appMolWeightValue = 0;
//        if (appMolWeight != null && appMolWeight.length() > 0) {
//            appMolWeightValue = Double.valueOf(appMolWeight).doubleValue();
//        }
//
//        // Barcode - added vbrecher 1/6/03
//        String barcode = tData.getInputParameters().getParameterString("barcode");
//
//        if (barcode == null) {
//            barcode = "FAKE BARCODE";
//            core.util.Logger.log(getClass() + " barcode is null");
//        }
//
//        // CloneId = added vbrecher 1/13/03
//        String cloneId = tData.getInputParameters().getParameterString("cloneid");
//        if (cloneId == null
//            || cloneId.equalsIgnoreCase("None")
//            || cloneId.length() <= 1)
//            cloneId = "-1";
//        else
//            cloneId = removeNonDigits(cloneId);
//
//        // CGUID = added vbrecher 2/20/03
//        String cguid = tData.getInputParameters().getParameterString("cguid");
//        if (cguid == null || cguid.length() <= 1)
//            cguid = "NA";
//
//        // Plasmid = added vbrecher 2/20/03
//        String plasmid = tData.getInputParameters().getParameterString("plasmid");
//        if (plasmid == null || plasmid.length() <= 1)
//            plasmid = "NA";
//
//        //int sampleTypeNum = Integer.parseInt(sampleTypeId);
//        //for (int i = 0; i < sampleTypeNum; i++) {
//        MSSubmission msSub = new MSSubmission();
//
//        msSub.setSampleType(
//            wf.ms.eo.SampleType.findByPK(Integer.parseInt(sampleTypeId), uow));
//        msSub.setProteinBatch(
//            wf.dd.pp1.eo.PP1ProteinBatch.findByPK(Integer.parseInt(batchId), uow));
//
//        msSub.setAmountMicrograms(amountMicrogramsValue);
//        msSub.setAmountPicomoles(amountPicomolesValue);
//        msSub.setApparentMolecularWeight(appMolWeightValue);
//        msSub.setPredictedMolecularWeight(predMolWeightValue);
//        msSub.setVolume(volumeValue);
//        msSub.setConcentrationMg(concentrationMgValue);
//        msSub.setConcentrationPm(concentrationPmValue);
//        msSub.setSourceExpressionSystem(srcExprSystem);
//        msSub.setBarcode(barcode);
//        msSub.setCguid(cguid);
//        msSub.setPlasmid(plasmid);
//        try {
//            msSub.setCloneId(Integer.parseInt(cloneId));
//        } catch (Exception ex) {
//            core.util.Logger.log("CloneId won't parse to integer");
//            core.util.Logger.log("Clone id = " + cloneId);
//            core.util.Logger.log("Clone id length = " + cloneId.length());
//            msSub.setCloneId(0);
//        }
//        // default units
//        //msSub.setAmountUnits("um");
//        //msSub.setMolecularWeightUnits("Da");
//        /***
//        String affinityTag = (String) tData.getInputParam("affinityTag");  
//        if (affinityTag != null)
//            msSub.setSignalSequence(affinityTag);
//        String fcDomain = (String) tData.getInputParam("fcDomain");
//        if (fcDomain != null)
//            msSub.setNTerminalFusion(fcDomain);
//        String curaProtein = (String) tData.getInputParam("curaProtein");
//        if (curaProtein != null)
//            msSub.setMatureProtein(curaProtein);
//        String msAlbumin = (String) tData.getInputParam("msAlbumin");
//        if (msAlbumin != null)
//            msSub.setHisTagFusion(msAlbumin);
//        ***/
//        String aaSequence = (String) tData.getInputParam("aaSequence");
//        if (aaSequence != null)
//            msSub.setAaSequence(aaSequence);
//        String costCenter = (String) tData.getInputParam("costCenter");
//        if (costCenter != null)
//            msSub.setCostCenter(costCenter);
//        String comments = (String) tData.getInputParam("comments");
//        if (comments != null)
//            msSub.setComments(comments);
//        String conditions = (String) tData.getInputParam("conditions");
//        if (conditions != null)
//            msSub.setConditions(conditions);
//            
//        DirectReadQuery query = new DirectReadQuery();
//        query.setSQLString( "select SysDate from DUAL" );
//        Vector rows = (Vector) uow.getParent().executeQuery(query);
//		java.sql.Timestamp timestamp = (java.sql.Timestamp) rows.elementAt(0);
//		msSub.setTimestamp(timestamp);
//        
//        MSSubmissionRequest sr = new MSSubmissionRequest();
//        sr.setMSSubmission(msSub);
//        sr.setRequest(reqs[0]);
//        msSub.addSubmissionRequest(sr);
//        uow.registerNewObject(sr);
//
//
//        
//        //}
//        uow.validateObjectSpace();
//        uow.printRegisteredObjects();
//    } catch (Exception ex) {
//        core.util.Logger.log(ex.getMessage());
//        throw ex;
//    }
//}
///**
// * Save the new domain object info corresponding to the new requests in next queue operations
// * This method will be only called in "StartRequest" operation (StartRequestHandler class)
// * Creation date: (7/29/2002 10:05:46 AM)
// * @param uow com.webgain.integrator.sessions.UnitOfWork
// * @param oldReq com.curagen.wms.request.eo.Request[] the old requests
// * @param newReqs com.curagen.wms.request.eo.Request[]  the new Requests
// * @param tData WorkflowTransactionData
// * @param requestEvent int
// * @exception java.lang.Exception The exception description.
// */
//public void setDomain(
//    com.webgain.integrator.sessions.UnitOfWork uow,
//    com.curagen.wms.request.eo.Request[] oldReqs,
//    com.curagen.wms.request.eo.Request[] reqs,
//    com.curagen.wms.mgr.WorkflowTransactionData tData,
//    int requestEvent)
//    throws java.lang.Exception {
//
//    try {
//
//        if (oldReqs == null) {
//            String batchId =
//                (String) tData.getInputParameters().getParameterString("batchId");
//            if (batchId == null || batchId.length() < 1) {
//                batchId = "0";
//            }
//
//            String sampleTypeId =
//                tData.getInputParameters().getParameterString("sampleTypeId");
//            if (sampleTypeId == null || sampleTypeId.length() < 1) {
//                return;
//            }
//
//            // Amount - added vbrecher 12/26/02 -- set default to 0
//            String amountMicrograms =
//                tData.getInputParameters().getParameterString("amountmicrograms");
//            double amountMicrogramsValue = 0;
//            if (amountMicrograms != null && amountMicrograms.length() > 0) {
//                amountMicrogramsValue = Double.valueOf(amountMicrograms).doubleValue();
//            }
//
//            // Amount - added vbrecher 12/26/02 -- set default to 0
//            String amountPicomoles =
//                tData.getInputParameters().getParameterString("amountpicomoles");
//            double amountPicomolesValue = 0;
//            if (amountPicomoles != null && amountPicomoles.length() > 0) {
//                amountPicomolesValue = Double.valueOf(amountPicomoles).doubleValue();
//            }
//
//            // Volume - added vbrecher 02/11/03 -- set default to 0
//            String volume = tData.getInputParameters().getParameterString("volume");
//            double volumeValue = 0;
//            if (volume != null && volume.length() > 0) {
//                volumeValue = Double.valueOf(volume).doubleValue();
//            }
//
//            // ConcentrationMg - added vbrecher 02/11/03 -- set default to 0
//            String concentrationMg =
//                tData.getInputParameters().getParameterString("concentrationMg");
//            double concentrationMgValue = 0;
//            if (concentrationMg != null && concentrationMg.length() > 0) {
//                concentrationMgValue = Double.valueOf(concentrationMg).doubleValue();
//            }
//
//            // Concentration - added vbrecher 02/11/03 -- set default to 0
//            String concentrationPm =
//                tData.getInputParameters().getParameterString("concentrationPm");
//            double concentrationPmValue = 0;
//            if (concentrationPm != null && concentrationPm.length() > 0) {
//                concentrationPmValue = Double.valueOf(concentrationPm).doubleValue();
//            }
//
//            // Source/Expression System - added vbrecher 12/26/02 -- set default to null
//            String srcExprSystem =
//                tData.getInputParameters().getParameterString("sourceexpressionsystem");
//            if (srcExprSystem == null || srcExprSystem.length() < 0) {
//                srcExprSystem = "";
//            }
//
//            // Predicted molecular weight - added vbrecher 12/26/02 -- set default to 0
//            String predMolWeight =
//                tData.getInputParameters().getParameterString("predictedmolecularweight");
//            double predMolWeightValue = 0;
//            if (predMolWeight != null && predMolWeight.length() > 0) {
//                predMolWeightValue = Double.valueOf(predMolWeight).doubleValue();
//            }
//
//            // Apparent molecular weight - added vbrecher 12/26/02 -- set default to 0
//            String appMolWeight =
//                tData.getInputParameters().getParameterString("apparentmolecularweight");
//            double appMolWeightValue = 0;
//            if (appMolWeight != null && appMolWeight.length() > 0) {
//                appMolWeightValue = Double.valueOf(appMolWeight).doubleValue();
//            }
//
//            // Barcode - added vbrecher 1/6/03
//            String barcode = tData.getInputParameters().getParameterString("barcode");
//
//            if (barcode == null) {
//                barcode = "FAKE BARCODE";
//                core.util.Logger.log(getClass() + " barcode is null");
//            }
//
//            // CloneId = added vbrecher 1/13/03
//            String cloneId = tData.getInputParameters().getParameterString("cloneid");
//            if (cloneId == null
//                || cloneId.equalsIgnoreCase("None")
//                || cloneId.length() <= 1)
//                cloneId = "-1";
//            else
//                cloneId = removeNonDigits(cloneId);
//
//            // CGUID = added vbrecher 2/20/03
//            String cguid = tData.getInputParameters().getParameterString("cguid");
//            if (cguid == null || cguid.length() <= 1)
//                cguid = "NA";
//
//            // Plasmid = added vbrecher 2/20/03
//            String plasmid = tData.getInputParameters().getParameterString("plasmid");
//            if (plasmid == null || plasmid.length() <= 1)
//                plasmid = "NA";
//
//            //int sampleTypeNum = Integer.parseInt(sampleTypeId);
//            //for (int i = 0; i < sampleTypeNum; i++) {
//            MSSubmission msSub = new MSSubmission();
//
//            msSub.setSampleType(
//                wf.ms.eo.SampleType.findByPK(Integer.parseInt(sampleTypeId), uow));
//            msSub.setProteinBatch(
//                wf.dd.pp1.eo.PP1ProteinBatch.findByPK(Integer.parseInt(batchId), uow));
//
//            msSub.setAmountMicrograms(amountMicrogramsValue);
//            msSub.setAmountPicomoles(amountPicomolesValue);
//            msSub.setApparentMolecularWeight(appMolWeightValue);
//            msSub.setPredictedMolecularWeight(predMolWeightValue);
//            msSub.setVolume(volumeValue);
//            msSub.setConcentrationMg(concentrationMgValue);
//            msSub.setConcentrationPm(concentrationPmValue);
//            msSub.setSourceExpressionSystem(srcExprSystem);
//            msSub.setBarcode(barcode);
//            msSub.setCguid(cguid);
//            msSub.setPlasmid(plasmid);
//            try {
//                msSub.setCloneId(Integer.parseInt(cloneId));
//            } catch (Exception ex) {
//                core.util.Logger.log("CloneId won't parse to integer");
//                core.util.Logger.log("Clone id = " + cloneId);
//                core.util.Logger.log("Clone id length = " + cloneId.length());
//                msSub.setCloneId(0);
//            }
//            // default units
//            //msSub.setAmountUnits("um");
//            //msSub.setMolecularWeightUnits("Da");
//            /***
//            String affinityTag = (String) tData.getInputParam("affinityTag");  
//            if (affinityTag != null)
//                msSub.setSignalSequence(affinityTag);
//            String fcDomain = (String) tData.getInputParam("fcDomain");
//            if (fcDomain != null)
//                msSub.setNTerminalFusion(fcDomain);
//            String curaProtein = (String) tData.getInputParam("curaProtein");
//            if (curaProtein != null)
//                msSub.setMatureProtein(curaProtein);
//            String msAlbumin = (String) tData.getInputParam("msAlbumin");
//            if (msAlbumin != null)
//                msSub.setHisTagFusion(msAlbumin);
//            ***/
//            String aaSequence = (String) tData.getInputParam("aaSequence");
//            if (aaSequence != null)
//                msSub.setAaSequence(aaSequence);
//            String costCenter = (String) tData.getInputParam("costCenter");
//            if (costCenter != null)
//                msSub.setCostCenter(costCenter);
//            String comments = (String) tData.getInputParam("comments");
//            if (comments != null)
//                msSub.setComments(comments);
//            String conditions = (String) tData.getInputParam("conditions");
//            if (conditions != null)
//                msSub.setConditions(conditions);
//
//            DirectReadQuery query = new DirectReadQuery();
//            query.setSQLString("select SysDate from DUAL");
//            Vector rows = (Vector) uow.getParent().executeQuery(query);
//            java.sql.Timestamp timestamp = (java.sql.Timestamp) rows.elementAt(0);
//            msSub.setTimestamp(timestamp);
//
//            MSSubmissionRequest sr = new MSSubmissionRequest();
//            sr.setMSSubmission(msSub);
//            sr.setRequest(reqs[0]);
//            msSub.addSubmissionRequest(sr);
//            uow.registerNewObject(sr);
//        } else {
//            for (int i = 0; i < reqs.length; i++) {
//                MSSubmissionRequest parentSubmissionRequest =
//                    (MSSubmissionRequest) this.findByRequest(
//                        uow,
//                        oldReqs[0],
//                        MSSubmissionRequest.class);
//
//                msSubmissionRequest =
//                    (MSSubmissionRequest) uow.newInstance(MSSubmissionRequest.class);
//                msSubmissionRequest.setRequest(reqs[i]);
//
//                //a single MSSubmission is shared among all MSSubmissionRequest objects         
//                msSubmissionRequest.setMSSubmission(parentSubmissionRequest.getMSSubmission());
//                parentSubmissionRequest.getMSSubmission().addSubmissionRequest(
//                    msSubmissionRequest);
//            }
//        }
//
//        //}
//        uow.printRegisteredObjects();        
//        uow.validateObjectSpace();
//
//    } catch (Exception ex) {
//        core.util.Logger.log(ex.getMessage());
//        throw ex;
//    }
//}
///**
// * Save the domain info for the current request
// * This will be only used by RequestHandler class, which means "Request" operation.
// * Creation date: (7/29/2002 10:05:47 AM)
// * @param uow com.webgain.integrator.sessions.UnitOfWork
// * @param req com.curagen.wms.request.eo.Request
// * @param tData com.curagen.wms.mgr.WorkflowTransactionData
// * @param event int
// */
//public void updateDomain(
//    com.webgain.integrator.sessions.UnitOfWork uow,
//    com.curagen.wms.request.eo.Request req,
//    com.curagen.wms.mgr.WorkflowTransactionData tData,
//    int requestEvent)
//    throws Exception {
//
//    if (requestEvent == com.curagen.wms.util.QueueConstants.DROP_REQUEST)
//        return;
//    try {
//        String quop = req.getQueueOperation().getOperation().getOperationDesc();
//        if (quop.indexOf("VOYAGER") >= 0) {
//
//            MSSubmissionRequest msSubmissionRequest =
//                (MSSubmissionRequest) uow.readObject(
//                    MSSubmissionRequest.class,
//                    new ExpressionBuilder().get("request").equal(req));
//
//            MSSubmissionResult msSubResult =
//                (MSSubmissionResult) uow.readObject(
//                    MSSubmissionResult.class,
//                    new ExpressionBuilder().get("msSubmission").equal(
//                        msSubmissionRequest.getMSSubmission()));
//
//            if (msSubResult == null) {
//                msSubResult = (MSSubmissionResult) uow.newInstance(MSSubmissionResult.class);
//                msSubResult.setMaldiResultUrl(
//                    "com.curagen.wms.servlet.WMSServlet?op=Request&requestId="
//                        + req.getRequestId());
//                msSubResult.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                msSubResult.setComments((String) tData.getInputParam("reqcomments"));
//            } else {
//                MSSubmissionResult msSubResultClone =
//                    (MSSubmissionResult) uow.registerObject(msSubResult);
//                msSubResultClone.setMaldiResultUrl(
//                    "com.curagen.wms.servlet.WMSServlet?op=Request&requestId="
//                        + req.getRequestId());
//                String previousComments = msSubResult.getComments();
//                if (previousComments != null) {
//                    previousComments =
//                        previousComments + "   " + tData.getInputParam("reqcomments");
//                    msSubResultClone.setComments(previousComments);
//                }
//            }
//
//            // check if this submission is already in the map
//            PpMsQcMap map =
//                (PpMsQcMap) uow.readObject(
//                    PpMsQcMap.class,
//                    new ExpressionBuilder().get("msSubmission").equal(
//                        msSubmissionRequest.getMSSubmission()));
//            if (map == null
//                && msSubmissionRequest.getMSSubmission().getProteinBatch() != null) {
//                PpMsQcMap ppMsQcMap = (PpMsQcMap) uow.newInstance(wf.dd.pp1.eo.PpMsQcMap.class);
//                ppMsQcMap.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                ppMsQcMap.setQcRequestId(
//                    msSubmissionRequest.getMSSubmission().getProteinBatch().getQcRequestId());
//            }
//
//            String voyagerMolWeight =
//                (String) tData.getInputParam("voyagermolecularweight");
//            if (voyagerMolWeight != null && voyagerMolWeight.length() > 0) {
//                MSSubmission msSub = msSubmissionRequest.getMSSubmission();
//                MSSubmission msSubClone = (MSSubmission) uow.registerObject(msSub);
//                msSubClone.setVoyagerMolecularWeight(
//                    Double.valueOf(voyagerMolWeight).doubleValue());
//            }
//        } else
//            if (quop.indexOf("Database Search") >= 0) {
//                MSSubmissionRequest msSubmissionRequest =
//                    (MSSubmissionRequest) uow.readObject(
//                        MSSubmissionRequest.class,
//                        new ExpressionBuilder().get("request").equal(req));
//
//                MSSubmissionResult msSubResult =
//                    (MSSubmissionResult) uow.readObject(
//                        MSSubmissionResult.class,
//                        new ExpressionBuilder().get("msSubmission").equal(
//                            msSubmissionRequest.getMSSubmission()));
//
//                if (msSubResult == null) {
//                    msSubResult = (MSSubmissionResult) uow.newInstance(MSSubmissionResult.class);
//                    msSubResult.setMascotResultUrl((String) tData.getInputParam("resultUrl"));
//                    msSubResult.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                    msSubResult.setComments((String) tData.getInputParam("reqcomments"));
//                } else {
//                    MSSubmissionResult msSubResultClone =
//                        (MSSubmissionResult) uow.registerObject(msSubResult);
//                    msSubResultClone.setMascotResultUrl((String) tData.getInputParam("resultUrl"));
//                    String previousComments = msSubResult.getComments();
//                    if (previousComments != null) {
//                        previousComments =
//                            previousComments + "   " + tData.getInputParam("reqcomments");
//                        msSubResultClone.setComments(previousComments);
//                    }
//                }
//                /***
//                MSSubmissionResult msSubmissionResult =
//                    (MSSubmissionResult) uow.newInstance(MSSubmissionResult.class);
//                msSubmissionResult.setMascotResultUrl(
//                    (String) tData.getInputParam("resultUrl"));
//                MSSubmissionRequest msSubmissionRequest =
//                    (MSSubmissionRequest) uow.readObject(
//                        MSSubmissionRequest.class,
//                        new ExpressionBuilder().get("request").equal(req));
//                msSubmissionResult.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                ***/
//                // There is no database mapping for result in msSubmission
//                ////msSubmissionRequest.getMSSubmission().setSubmissionResult(msSubmissionResult);
//                // check if this submission is already in the map
//                PpMsQcMap map =
//                    (PpMsQcMap) uow.readObject(
//                        PpMsQcMap.class,
//                        new ExpressionBuilder().get("msSubmission").equal(
//                            msSubmissionRequest.getMSSubmission()));
//                if (map == null
//                    && msSubmissionRequest.getMSSubmission().getProteinBatch() != null) {
//                    PpMsQcMap ppMsQcMap = (PpMsQcMap) uow.newInstance(wf.dd.pp1.eo.PpMsQcMap.class);
//                    ppMsQcMap.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                    ppMsQcMap.setQcRequestId(
//                        msSubmissionRequest.getMSSubmission().getProteinBatch().getQcRequestId());
//                }
//                //PpMsQcMap ppMsQcMap = (PpMsQcMap) uow.newInstance(wf.dd.pp1.eo.PpMsQcMap.class);
//                //ppMsQcMap.setMSSubmission(msSubmissionRequest.getMSSubmission());
//                //ppMsQcMap.setQcRequestId(
//                //    msSubmissionRequest.getMSSubmission().getProteinBatch().getQcRequestId());
//                String coverage = (String) tData.getInputParam("coverage");
//                if (coverage != null && coverage.length() > 0) {
//                    MSSubmission msSub = msSubmissionRequest.getMSSubmission();
//                    MSSubmission msSubClone = (MSSubmission) uow.registerObject(msSub);
//                    msSubClone.setCoverage(Integer.valueOf(coverage).intValue());
//                }
//            } /***else
//            if (quop.indexOf("Submission") >= 0) {
//              MSSubmissionRequest msSubRequest =
//                  (MSSubmissionRequest) uow.readObject(
//                      MSSubmissionRequest.class,
//                      new ExpressionBuilder().get("request").equal(req));
//              MSSubmission msSub = msSubRequest.getMSSubmission();
//              MSSubmission msSubClone = (MSSubmission) uow.registerObject(msSub);
//              String signalSequence = (String) tData.getInputParam("signalSequence");
//              if (signalSequence != null)
//                  msSubClone.setSignalSequence(signalSequence);
//              String nTerminalFusion = (String) tData.getInputParam("nTerminalFusion");
//              if (nTerminalFusion != null)
//                  msSubClone.setNTerminalFusion(nTerminalFusion);
//              String matureProtein = (String) tData.getInputParam("matureProtein");
//              if (matureProtein != null)
//                  msSubClone.setMatureProtein(matureProtein);   
//              String hisTagFusion = (String) tData.getInputParam("hisTagFusion");
//              if (hisTagFusion != null)
//                  msSubClone.setHisTagFusion(hisTagFusion);
//            } ***/
//        /***
//        if (requestEvent == com.curagen.wms.util.QueueConstants.FINISH_REQUEST
//            && req.getQueueOperation().getEndOfQueue()) {
//            init(uow, req);
//            MSSubmissionResult msSubmissionResult =
//                (MSSubmissionResult) uow.newInstance(MSSubmissionResult.class);
//            msSubmissionResult.setResultUrl((String) tData.getInputParam(RESULT_URL_PARAM));
//            msSubmissionResult.setMSSubmission(msSubmissionRequest.getMSSubmission());
//            msSubmissionRequest.getMSSubmission().setSubmissionResult(msSubmissionResult);
//        
//            PpMsQcMap ppMsQcMap = (PpMsQcMap) uow.newInstance(wf.dd.pp1.eo.PpMsQcMap.class);
//            ppMsQcMap.setMSSubmission(msSubmissionRequest.getMSSubmission());
//            ppMsQcMap.setQcRequestId(
//                msSubmissionRequest.getMSSubmission().getProteinBatch().getQcRequestId());
//        }
//        ***/
//    } catch (Exception ex) {
//        core.util.Logger.log(ex.getMessage());
//        core.util.Logger.logStackTrace(ex);
//    }
//}
///**
// * Validate the user's input
// * This method will be only used by RequestHandler.class, which means "Request" operation
// * Creation date: (7/29/2002 10:05:47 AM)
// * @param clientSession com.webgain.integrator.threetier.ClientSession
// * @param req com.curagen.wms.request.eo.Request
// * @param tData WorkflowTransactionData
// * @param requestEvent int
// */
//public java.lang.String[] validateForm(
//    com.webgain.integrator.threetier.ClientSession clientSession,
//    com.curagen.wms.request.eo.Request req,
//    com.curagen.wms.mgr.WorkflowTransactionData tData,
//    int requestEvent) {
//    String[] nextOps = null;   
//
//    try {
//        String userId = (String) tData.getUserId();
//        if (!core.security.UserManager.hasAuthCode(userId, core.security.AuthCodes.AUTH_MASS_SPEC)) {
//            if (!req.isInactiveRequest()) {
//                return new String[] { "User " + userId + " does not have permission to change queue." };
//            }
//        }
//        if (requestEvent == com.curagen.wms.util.QueueConstants.CHANGE_OPERATION) {
//            if (!req.getQueueOperation().getEndOfQueue()) {
//                nextOps =
//                    tData.getInputParams(
//                        com.curagen.wms.util.RequestConstants.nextOperationParamName);
//                if (nextOps == null || nextOps.length == 0)
//                    return new String[] { "Please select next operation." };
//            }
//            /***
//            else //end of queue
//                if (tData.getInputParam(RESULT_URL_PARAM) == null
//                    || ((String) tData.getInputParam(RESULT_URL_PARAM)).length() < 2)
//                    return new String[] { "Please enter result url." };
//                    ***/
//        }
//
//    } catch (Exception ex) {
//        core.util.Logger.log(ex.getMessage());
//        core.util.Logger.logStackTrace(ex);
//	    return null;
//
//    }
//    return null;
//}
//    /**
//     * Validate the user's input
//     * This method will be only used by StartRequestHandler.class, which means "StartRequest" operation
//     * Creation date: (7/29/2002 10:05:46 AM)
//     * @return java.lang.String[] the error messages
//     * @param req com.curagen.wms.request.eo.Request the current request
//     * @param tData WorkflowTransactionData
//     * @param requestEvent int
//     */
//    public java.lang.String[] validateInitForm(
//        com.webgain.integrator.threetier.ClientSession clientSession,
//        com.curagen.wms.request.eo.Request[] req,
//        com.curagen.wms.mgr.WorkflowTransactionData tdata,
//        int requestEvent) {
//	    /***
//	    Vector errors = new Vector();
//		try {
//			String affinityTag = (String) tdata.getInputParam("affinitytag");
//			if (affinityTag == null || affinityTag.length() <= 0)
//				errors.addElement("Affinity tag sequence is missing");
//			String curaProtein = (String) tdata.getInputParam("curaprotein");
//			if (curaProtein == null || curaProtein.length() <= 0)
//				errors.addElement("Curaprotein sequence is missing");
//			String msAlbumin = (String) tdata.getInputParam("msalbumin");
//			if (msAlbumin == null || msAlbumin.length() <= 0)
//				errors.addElement("Murine serum albumin sequence is missing");
//			String fcDomain = (String) tdata.getInputParam("fcdomain");
//			if (fcDomain == null || fcDomain.length() <= 0)
//				errors.addElement("FC domain sequence is missing");
//			if (errors.size() > 0)
//				return (String[]) errors.toArray( new String[errors.size()] );
//			
//		} catch( Exception ex ) {
//        	core.util.Logger.log( ex.getMessage() );
//        	core.util.Logger.logStackTrace( ex );
//		}
//		***/
//        return null;
//    }
}
