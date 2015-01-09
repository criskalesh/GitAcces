package com.pacemaker.ecom.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.wsdl.Operation;
import javax.xml.namespace.QName;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.reficio.ws.builder.SoapBuilder;
import org.reficio.ws.builder.SoapOperation;
import org.reficio.ws.builder.core.Wsdl;
import org.reficio.ws.client.core.SoapClient;
import org.xml.sax.InputSource;

import com.pacemaker.ecom.model.Member;
import com.pacemaker.ecom.model.MessageDetails;

public class SoapUtil {
	
	private static Logger logger = Logger.getLogger(SoapUtil.class.getName());
	
	 public Member fetchOperations(Member member){
		 try{
			 Wsdl wsdl = Wsdl.parse(member.getEndpoint());
			 List<QName> bindings = wsdl.getBindings(); 
			 List<MessageDetails> messageDetailsList = new ArrayList<MessageDetails>();
			 String findStr = "Http";
			 for (QName qName  : bindings) {
				 int count = 0;
				 count = StringUtils.countMatches(qName.getLocalPart(), findStr);
				 if(count==0){
					 SoapBuilder builder = wsdl.binding().localPart(qName.getLocalPart()).find();
					 List<SoapOperation> operations = builder.getOperations();
					 for (SoapOperation soapOperation : operations) {				
						 MessageDetails messageDetails = new MessageDetails();
						 //StringBuffer buff = new StringBuffer("");
						 messageDetails.setName(soapOperation.getOperationName());
						 Operation ops =  builder.getBinding().getPortType().getOperation(soapOperation.getOperationName(), soapOperation.getOperationInputName(), soapOperation.getOperationOutputName());
						/* buff.append("<br/>").append("Operation: ").append(ops.getName())
						 .append("<br/>").append("Style: ").append(ops.getStyle())
						 .append("<br/>").append("Input: ").append(ops.getInput().toString())
						 .append("<br/>").append("Output: ").append(ops.getOutput().toString());*/
						 messageDetails.setTestEndPoint(member.getEndpoint());
						 messageDetails.setDescription(ops.toString());
						 messageDetails.setStyle(ops.getStyle().toString());
						 messageDetails.setInputType(ops.getInput().getMessage().getQName().toString());
						 messageDetails.setOutputType(ops.getOutput().getMessage().getQName().toString());
						 messageDetails.setTestDescription(builder.buildInputMessage(soapOperation));
						 messageDetailsList.add(messageDetails);
					 }
				 }				 
			 }
			 member.setMessageDetails(messageDetailsList);
			 wsdl = null;
		 }catch(Exception e){
			 logger.error("exception - SOAP", e);
		 }
		 return member;
	 }
	 
	 public MessageDetails testSOAPService(MessageDetails messageDetails) {
		 try{
			 SoapClient client = SoapClient.builder()
		        .endpointUri(messageDetails.getTestEndPoint())
		        .build();
			 String response = client.post(messageDetails.getTestDescription());
			 response = formatXml(response);
			 messageDetails.setTestOutPut(response);
		 }catch(Exception e){
			 logger.error("exception - SOAP", e);
			 messageDetails.setTestOutPut(ExceptionUtils.getStackTrace(e));
		 }
		 return messageDetails;
	 }
	
	 
	private String formatXml(String xml) {
		try {
			Transformer serializer = SAXTransformerFactory.newInstance()
					.newTransformer();
			serializer.setOutputProperty(OutputKeys.INDENT, "yes");
			serializer.setOutputProperty(
					"{http://xml.apache.org/xslt}indent-amount", "4");
			Source xmlSource = new SAXSource(new InputSource(
					new ByteArrayInputStream(xml.getBytes())));
			StreamResult res = new StreamResult(new ByteArrayOutputStream());
			serializer.transform(xmlSource, res);
			return new String(
					((ByteArrayOutputStream) res.getOutputStream())
							.toByteArray());
		} catch (Exception e) {
			return xml;
		}
	}
}
