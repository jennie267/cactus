package com.cactus.product.controller;

import java.util.Collection;

import org.javaswift.joss.exception.CommandException;
import org.javaswift.joss.model.Account;
import org.javaswift.joss.model.Container;
import org.javaswift.joss.model.StoredObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cactus.product.config.SwiftConnector;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Handles requests for the application home page.
 */
@Api(value = "테스트 Controller")
@Controller
@RestController
@RequestMapping("/swift")
public class SwiftController {
	
	@Autowired
	SwiftConnector swiftConnector;
	
	/**
	 * Glsterfs-Swift test
	 */
	@ApiOperation(value = "Swift 테스트")
	@RequestMapping(value="", method=RequestMethod.POST)
	public String saveFile1(@RequestParam MultipartFile attchFile,Model model) {
		
		String result = "Success";
		System.out.println("얍");
		System.out.println(swiftConnector.getPassword());
		System.out.println("얍");
//		SwiftConnector swiftConnector = new SwiftConnector();
    	Account account = swiftConnector.getAccount();
    	System.out.println("Account  다음");
    	System.out.println(account.getCount());
    	Container container = account.getContainer("images");
    	
    	if(!container.exists()){
    		container.create();
    		container.makePublic();
    	}
    	
        Collection<Container> containers = account.list();
        for (Container currentContainer : containers) {
            System.out.println("container list : " + currentContainer.getName());
            
        }
        
        //upload
        String fileName = System.currentTimeMillis()+"-"+attchFile.getOriginalFilename();
        StoredObject object = container.getObject(fileName);

        String imagePath=account.getPublicURL() + object.getPath();
        try {
        	object.uploadObject(attchFile.getInputStream());
		} catch (CommandException e) {
			result = e.toString()+"(413:Storage quota limit,....)";
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        model.addAttribute("image", imagePath);
        model.addAttribute("info", result);
		return imagePath;
	}
	
	/*@RequestMapping(value="/del", method=RequestMethod.GET)
	public String delete(Model model) {
		
		
		//SwiftConnector swiftConnector = new SwiftConnector();
    	Account account = swiftConnector.getAccount();
    	
    	Container container = account.getContainer("images");
    	
    	if(!container.exists()){
    		container.create();
    		container.makePublic();
    	}
    	
        Collection<Container> containers = account.list();
        for (Container currentContainer : containers) {
            System.out.println("container list : " + currentContainer.getName());
            
        }
        
        //upload
        //StoredObject object = container.getObject("test" + File.separator + "attchFile"+System.currentTimeMillis()+".jpg");
        try {
        	for(String fileName : delList()){
        	
        		StoredObject object = container.getObject(fileName);
        	
        		object.delete();
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
        //model.addAttribute("image", imagePath);
        model.addAttribute("info", "Success");
		return "swift";
	}
	
	public List<String> delList(){
		List<String> list = new ArrayList<String>();
		
		list.add("attchFile1437024457786.jpg");
		list.add("attchFile1437024465464.jpg");
		
		return list;

	}*/
}

