package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.awt.Desktop;
import java.io.File;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Bean.Candidate;
import Bean.Employee;
import Dao.CandidateDao;
import Dao.EmployeeDao;

@Controller
public class myController {
	
	@RequestMapping("/")
	public String indexPage() {
		return "index";
	}
	
	@RequestMapping(value="reg", method=RequestMethod.POST)
	public ModelAndView register(@ModelAttribute Employee employee) {
		ModelAndView modelAndView = new ModelAndView();
		
		String message =EmployeeDao.save(employee);
		if(message.equals("success"))
		{
		modelAndView.setViewName("index");
		}
		else
		{
			modelAndView.addObject("message", message);	
			modelAndView.setViewName("register");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public ModelAndView login(@ModelAttribute Employee employee,HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		
		String message =EmployeeDao.loginUser(employee);
		if(!(message.equals("Invalid Password") || message.equals("No User associated with this ID.")))
		{
		modelAndView.setViewName("Candidate");
		HttpSession session =  request.getSession();
		session.setAttribute("name", message);
		session.setAttribute("id", employee.getId());
		}
		else
		{
			modelAndView.addObject("message",message);
			modelAndView.setViewName("index");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="cand", method=RequestMethod.POST)
	public  ModelAndView candidateDetails(@ModelAttribute("cand") Candidate candidate) throws IOException {
		Boolean flag=false;
		String message="";
		JFileChooser chooser = new JFileChooser();
		  
		FileNameExtensionFilter filter = new FileNameExtensionFilter( "PDF Documents" , "pdf" );
		chooser.setFileFilter(filter);

		  
		    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
		    flag=true;
		      //System.out.println("getCurrentDirectory(): " + chooser.getCurrentDirectory());
		      //System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
		    } else {
		    	flag=false;
		      //System.out.println("No Selection ");
		    }
		ModelAndView modelAndView = new ModelAndView();
		if(flag)
		{
		message =CandidateDao.createCandidate(candidate,chooser.getSelectedFile());
		if(message.equals("success"))
		{
			modelAndView.addObject("msg", "successfully saved");		
		modelAndView.setViewName("View");
		}   
		else
		{
			modelAndView.addObject("message", message);	
			modelAndView.setViewName("Candidate");
		}
		}
		else
		{
			message="Choose a file";
			modelAndView.setViewName("Candidate");
			modelAndView.addObject("message", message);	
		}
		
		
		return modelAndView;
	}
	
	@RequestMapping(value="cand2", method=RequestMethod.POST)
	public  ModelAndView candidateDetails2(@ModelAttribute("cand") Candidate candidate, @RequestParam Map<String,String> reqPar) throws IOException {
		ModelAndView modelAndView = new ModelAndView();
		String name = reqPar.get("submit");
		//System.out.println(candidate.getMyCheckBox1()+""+candidate.getMyCheckBox2());
		String message = CandidateDao.updateCandidate(candidate,name);
		if(message.equals("successfully saved"))
		{
			modelAndView.addObject("message",message);
			modelAndView.setViewName("View");
		
		//model.addAttribute("message", "success");
		}
		else
		{
			
			modelAndView.addObject("message",message);
			modelAndView.setViewName("View");
		//	model.addAttribute("message", "failure");
		}
		return modelAndView;
	}
	
	@RequestMapping(value="downloadCV", method=RequestMethod.POST)
	public  ModelAndView downloadCV(@RequestParam Map<String,String> reqPar, HttpServletResponse response,  HttpServletRequest request) throws IOException {
		ModelAndView modelAndView = new ModelAndView();
		String name = reqPar.get("submit");
		//System.out.println(name);
		CandidateDao.cvDownload(Integer.valueOf(name));
		//System.out.println(candidate.getMyCheckBox1()+""+candidate.getMyCheckBox2());
			//modelAndView.addObject("message",message);
			modelAndView.setViewName("ViewAll");
			Desktop desktop = Desktop.getDesktop();
	        File dirToOpen = null;
	        try {
	        	String username = System.getProperty("user.name");
			    String path ="C:/Users/"+username+"/Downloads";
			    dirToOpen = new File(path);
	            desktop.open(dirToOpen);
	        } catch (IllegalArgumentException iae) {
	            System.out.println("File Not Found");
	        }
		return modelAndView;
	}
	
	
	
	@RequestMapping(value="download", method=RequestMethod.POST)
	public  ModelAndView candidateDetails1() throws ClassNotFoundException, SQLException, IOException {
		CandidateDao.download();
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("ViewAll");
		//modelAndView.addObject("message",msg);
		Desktop desktop = Desktop.getDesktop();
        File dirToOpen = null;
        try {
        	String username = System.getProperty("user.name");
		    String path ="C:/Users/"+username+"/Downloads";
            dirToOpen = new File(path);
            desktop.open(dirToOpen);
        } catch (IllegalArgumentException iae) {
            System.out.println("File Not Found");
        }
		return modelAndView;
	}
	
}