package ru.scriptum.controller.event;

/**
 * /** Insert the type's description here. Creation date: (6/13/2003 4:41:14 PM)
 * 
 * @author: Administrator
 */
public class StartRequestAdapter extends TheatreAdapter {
//	/**
//	 * TheatreAdapter constructor comment.
//	 */
//	public StartRequestAdapter() {
//		super();
//	}
//
//	public String errorXML(String[] errors) {
//		StringBuffer xml = new StringBuffer();
//
//		if (errors != null) {
//			for (int i = 0; i < errors.length; i++) {
//				xml.append("<wmsError>");
//				xml.append(errors[i]);
//				xml.append("</wmsError>");
//			}
//		}
//
//		return xml.toString();
//	}
//
//	/**
//	 * Insert the method's description here. Creation date: (6/13/2003 4:41:14
//	 * PM)
//	 */
//	public TheatreEvent[] handleTheatreEvent(TheatreEvent event,
//			WorkflowTransactionData wtd, Session session) {
//
//		WorkflowState[] currWfStates = null;
//		WorkflowState[] nextWfStates = null;
//		WorkflowState[] wfStates = null;
//
//		try {
//			core.util.Logger.log("FSMStartRequestHandler.process starting...");
//
//			if (wtd.getInputParams(stateIdParamName) != null) {
//				String[] stateIds = wtd.getInputParams(stateIdParamName);
//				currWfStates = new WorkflowState[stateIds.length];
//				for (int i = 0; i < currWfStates.length; i++) {
//					currWfStates[i] = new WorkflowState(State.findByPK(
//							(ClientSession) session, Integer
//									.parseInt(stateIds[i])));
//				}
//			} else if (wtd.getInputParams(nextStateIdParamName) != null) {
//				String[] stateIds = wtd.getInputParams(nextStateIdParamName);
//				nextWfStates = new WorkflowState[stateIds.length];
//				for (int i = 0; i < nextWfStates.length; i++) {
//					nextWfStates[i] = new WorkflowState(State.findByPK(
//							(ClientSession) session, Integer
//									.parseInt(stateIds[i])));
//				}
//
//			} else if (wtd.getInputParams(stateTemplateAttributeIdParamName) != null
//					|| wtd
//							.getInputParams(nonEditableStateTemplateAttributeIdParamName) != null) {
//				State[] nextState = State.findByData(wtd,
//						(ClientSession) session);
//				nextWfStates = new WorkflowState[nextState.length];
//				for (int i = 0; i < nextState.length; i++) {
//					nextWfStates[i] = new WorkflowState(nextState[i]);
//				}
//			} else if (wtd.getInputParam(queueIdParamName) != null) {
//				Queue queue = Queue.findByPK(Integer.parseInt((String) wtd
//						.getInputParam(queueIdParamName)),
//						(ClientSession) session);
//				nextWfStates = new WorkflowState[1];
//				nextWfStates[0] = new WorkflowState((State) queue.getStates()
//						.firstElement());
//
//			} else {
//				core.util.Logger.log(getClass()
//						+ ".process nextstateIds is null");
//				wtd
//						.setTransactionCode(core.appframe1.TransactionStatus.FATAL_ERROR);
//				wtd.setTransactionMsg("No nextStateId in the input parameters");
//				return null;
//			}
//
//			if (currWfStates != null)
//				wfStates = currWfStates;
//			else
//				wfStates = nextWfStates;
//			String[] errors = null;
//			// wtd.setWorkflowOperations(wfos);
//			if ((String) wtd.getInputParam(processActionParamName) != null) {
//				// The user has done something
//				Vector verrs = new Vector();
//				for (int i = 0; i < wfStates.length; i++) {
//					com.curagen.wms.workflow.object.TheatreDelegate wfd = wfStates[i]
//							.getState().getWorkflowDelegates()[0];
//					String[] aerrs = wfd.validateInitForm(
//							(ClientSession) session,
//							com.curagen.wms.util.RequestUtil.makeRequestArray(
//									(ClientSession) session, wtd
//											.getInputParameters()), wtd,
//							LOAD_QUEUE);
//					if (aerrs != null) {
//						for (int j = 0; j < aerrs.length; j++)
//							verrs.addElement(aerrs[j]);
//					}
//				}
//				errors = new String[verrs.size()];
//				for (int i = 0; i < verrs.size(); i++)
//					errors[i] = (String) verrs.elementAt(i);
//				if (errors.length == 0) {
//					Request[] newReqs = null;
//					if (currWfStates == null) {
//						UnitOfWork uow = session.acquireUnitOfWork();
//						for (int i = 0; i < nextWfStates.length; i++) {
//							newReqs = com.curagen.wms.util.RequestUtil
//									.combineRequestArrays(newReqs,
//											nextWfStates[i].preProcess(
//													(ClientSession) session,
//													wtd));
//						}
//						((StartRequestEvent) event).setRequests(newReqs);
//					} else {
//						WorkflowStateMap sm = new WorkflowStateMap(currWfStates);
//						newReqs = sm.execute((ClientSession) session, wtd);
//						((StartRequestEvent) event).setRequests(newReqs);
//					}
//
//					if (newReqs != null) {
//						// Goto the new request page
//						WorkflowOperation wfo = new WorkflowOperation(
//								(ClientSession) session, nextWfStates[0]
//										.getState().getQueueOperation()
//										.getQueueOperationId());
//						com.curagen.wms.workflow.object.TheatreDelegate newWfa = nextWfStates[0]
//								.getState().getQueueOperation()
//								.getWorkflowDelegate();
//						if (newWfa.isToShowQueue(newReqs, wfo, wtd,
//								START_REQUEST_OPERATION)) {
//							String redir = newWfa.getRedirectURL(newReqs, wfo,
//									wtd, START_REQUEST_OPERATION, LOAD_QUEUE);
//
//							if (redir != null && !"".equals(redir)) {
//								if (wtd
//										.getInputParams(com.curagen.wms.ih.WMSHandler.FRAME_PARAM) != null)
//									redir += "&frame="
//											+ wtd
//													.getInputParam(com.curagen.wms.ih.WMSHandler.FRAME_PARAM);
//
//								wtd.setForwardURL(redir);
//							} else {
//								redir = "com.curagen.wms.servlet.WMSServlet?op=ShowQueueTree&queueId="
//										+ nextWfStates[0].getState().getQueue()
//												.getQueueId()
//										+ "&ts="
//										+ wtd.getTableSpaceId();
//
//								if (wtd
//										.getInputParams(com.curagen.wms.ih.WMSHandler.FRAME_PARAM) != null)
//									redir += "&frame=1"
//											+ wtd
//													.getInputParam(com.curagen.wms.ih.WMSHandler.FRAME_PARAM);
//
//								redir += "&om=fsm";
//								redir += "&redir=1";
//
//								wtd.setForwardURL(redir);
//							}
//							return null;
//						}
//					}
//				} else {
//					wtd
//							.setTransactionCode(core.appframe1.TransactionStatus.VALIDATION_ERROR);
//					wtd.setTransactionMsg("Error in input: " + errors[0]);
//				}
//			}
//			// wtd.setActionURL(getLoadQueueURL(wfos[0]));
//			StringBuffer xml = new StringBuffer();
//
//			for (int i = 0; i < wfStates.length; i++) {
//				WorkflowOperation wfo = new WorkflowOperation(
//						(ClientSession) session, wfStates[i].getState()
//								.getQueueOperation().getQueueOperationId());
//
//				String xmlString = wfStates[i].getState()
//						.getWorkflowDelegates()[0].getInitXmlString(
//						(ClientSession) session, wfStates[i].getState()
//								.getWorkflowDelegates()[0].makeRequestArray(
//								(ClientSession) session, wfo, wtd,
//								START_REQUEST_OPERATION), wfo, wtd);
//				if (xmlString != null)
//					xml.append(xmlString);
//			}
//			if (currWfStates != null) {
//				for (int i = 0; i < wfStates.length; i++) {
//					xml.append("<qoId>");
//					xml.append(wfStates[i].getState().getQueueOperation()
//							.getQueueOperationId());
//					xml.append("</qoId>");
//				}
//			}
//			for (int i = 0; i < nextWfStates.length; i++) {
//				xml.append("<nextStateList>");
//				xml.append(nextWfStates[i].getState().getXmlString(true));
//				xml.append("</nextStateList>");
//			}
//
//			// add errors, if there are any
//			if (errors != null && errors.length > 0) {
//				xml.append(errorXML(errors));
//			}
//			System.out.println(xml.toString());
//			wtd.setXml(xml.toString());
//			wtd.setPageHeader(wfStates[0].getState().getWorkflowDelegates()[0]
//					.getPageHeader(
//							new WorkflowOperation((ClientSession) session,
//									wfStates[0].getState().getQueueOperation()
//											.getQueueOperationId()),
//							START_REQUEST_OPERATION));
//			// Set the output page
//			wtd
//					.setRedirectionParam(wfStates[0].getState()
//							.getWorkflowDelegates()[0].getStyleSheet(
//							com.curagen.wms.util.RequestUtil.makeRequestArray(
//									(ClientSession) session, wtd
//											.getInputParameters()),
//							new WorkflowOperation((ClientSession) session,
//									wfStates[0].getState().getQueueOperation()
//											.getQueueOperationId()),
//							FSM_START_REQUEST_OPERATION) /* WMSFSMSTARTREQUEST_STYLESHEET */);
//
//		} catch (Exception e) {
//
//			core.util.Logger.log(getClass().toString()
//					+ ".process got exception: " + e.getMessage());
//			// core.util.Logger.logStackTrace( e );
//			wtd
//					.setTransactionCode(core.appframe1.TransactionStatus.FATAL_ERROR);
//			wtd.setTransactionMsg("Error in StartRequestHandler: "
//					+ e.getMessage());
//		} finally {
//
//			core.util.Logger.log("FSMStartRequestHandler.process done");
//		}
//
//		return getTheatreEvents(session);
//		// return new StopEvent(this);
//	}
}
