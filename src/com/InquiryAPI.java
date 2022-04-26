package com;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.google.gson.*;

import org.jsoup.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.Document;

import model.Inquiry;

@Path("/Inquiry")
public class InquiryAPI {
	Inquiry inquiryObj = new Inquiry();

	@GET
	@Path("/")
	@Produces(MediaType.TEXT_HTML)
	public String readInquiry() {
		return inquiryObj.readInquiry();
	}
	
	@POST
	@Path("/")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces(MediaType.TEXT_PLAIN)
	public String insertInquiry(@FormParam("CustomerID") String CustomerID,			
	 @FormParam("Date") String Date,
	 @FormParam("CustomerName") String CustomerName,
	 @FormParam("Description") String Description,
	 @FormParam("Area") String Area)
	{
	 String output = inquiryObj.insertInquiry(CustomerID, Date, CustomerName, Description, Area);
	return output;
	}
	
	@PUT
	@Path("/")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_PLAIN)
	public String updateInquiry(String inquiryData)
	{
	//Convert the input string to a JSON object
	 JsonObject InqObject = new JsonParser().parse(inquiryData).getAsJsonObject();
	//Read the values from the JSON object
	 String inquiryID = InqObject.get("inquiryID").getAsString();
	 String CustomerID = InqObject.get("CustomerID").getAsString();
	 String Date = InqObject.get("Date").getAsString();
	 String CustomerName = InqObject.get("CustomerName").getAsString();
	 String Description = InqObject.get("Description").getAsString();
	 String Area = InqObject.get("Area").getAsString();
	 String output = inquiryObj.updateInquiry(inquiryID, CustomerID, Date, CustomerName, Description, Area);
	return output;
	} 
	
	@DELETE
	@Path("/")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteInquiry(String inquiryData)
	{
	//Convert the input string to an XML document
	 Document doc = Jsoup.parse(inquiryData, "", Parser.xmlParser());

	//Read the value from the element <ID>
	 String inquiryID = doc.select("inquiryID").text();
	 String output = inquiryObj.deleteInquiry(inquiryID);
	return output;
	}
}
