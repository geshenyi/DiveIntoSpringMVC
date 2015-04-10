package com.chuck.diveintospringmvc.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;
import org.springframework.web.util.UriComponents;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * Created by ssge on 2015/3/17.
 */
@Controller
@SessionAttributes("mySession")
public class TestController {
    @InitBinder
    public void initBinder(WebDataBinder binder){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf,false));
    }

    @RequestMapping(value="/form", method = RequestMethod.POST)
    public String handleFileUpload(@RequestParam String name, @RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        ReadableByteChannel fileChannel = Channels.newChannel(file.getInputStream());
        FileOutputStream fileOutputStream = new FileOutputStream(new File(request.getSession().getServletContext().getRealPath("") +"\\test.txt"));
        FileChannel fileChannel1 = fileOutputStream.getChannel();
        int num = fileChannel.read(byteBuffer);
        while(num != -1){
            byteBuffer.flip();
            fileChannel1.write(byteBuffer);
//            while(byteBuffer.hasRemaining()){
////                System.out.println((char)byteBuffer.get());
//                fileChannel1.write(byteBuffer);
//            }
            byteBuffer.clear();
            num = fileChannel.read(byteBuffer);
        }
        byteBuffer.clear();
        fileChannel.close();
        fileChannel1.close();
//        File outputFile = new File(request.getSession().getServletContext().getRealPath(""));
//        FileChannel outputFileChannel = outputFile.ge
//        FileChannel fileChannel = new Filechann
        return null;
    }

    @RequestMapping(value="/testpoi", method= RequestMethod.GET)
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {

        String output =
                ServletRequestUtils.getStringParameter(request, "output");

        //dummy data
        Map<String,String> revenueData = new HashMap<String,String>();
        revenueData.put("Jan-2010", "$100,000,000");
        revenueData.put("Feb-2010", "$110,000,000");
        revenueData.put("Mar-2010", "$130,000,000");
        revenueData.put("Apr-2010", "$140,000,000");
        revenueData.put("May-2010", "$200,000,000");

        if(output ==null || "".equals(output)){
            //return normal view
            return new ModelAndView("RevenueSummary","revenueData",revenueData);

        }else if("EXCEL".equals(output.toUpperCase())){
            //return excel view
            return new ModelAndView("ExcelRevenueSummary","revenueData",revenueData);

        }else{
            //return normal view
            return new ModelAndView("RevenueSummary","revenueData",revenueData);

        }
    }

    @RequestMapping(value="/testmethod", method = RequestMethod.GET)
    @JsonView(ReturnObject.WithoutPasswordView.class)
//    @ResponseBody
    public String testMethod(HttpServletRequest request, Date testInput, RequestObject requestObject, ModelAndView modelAndView, RedirectAttributes redirectAttributes, RequestContext requestContext){
        System.out.println(request.getSession().getServletContext().getRealPath(""));
//        UriComponents uriComponents = MvcUriComponentsBuilder.fromMethodName(TestController.class, "testMethod", new Date(), null,null,null).buildAndExpand();
//        System.out.println(uriComponents.encode().toUri());
        Locale locale = LocaleContextHolder.getLocale();
        System.out.println(locale.toString());
        System.out.println(modelAndView);
        System.out.println(testInput);
        System.out.println(requestObject.getParam1());
        System.out.println(requestObject.getParam2());
        modelAndView.addObject("testModelObject", "ddd");
        modelAndView.addObject("mySession", "123");
        modelAndView.setViewName("test");
        ReturnObject ro = new ReturnObject();
        ro.setParam1("aaa");
        ro.setParam2("bbb");
//        return new ResponseEntity<ReturnObject>(ro, HttpStatus.OK);

//        redirectView.
        redirectAttributes.addFlashAttribute("testFlash","FlashValue");
        redirectAttributes.addAttribute("testAttr","AttrValue");
        return "redirect:index1.jsp";
    }

    @RequestMapping(value="/index1", method = RequestMethod.GET)
    public String showCustomer() {
        System.out.println("aaa");
        return "index1";
    }

    @RequestMapping(value="/testAsync", method = RequestMethod.GET)
    @ResponseBody
    public Callable<String> testAsync(){
        return new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "abc";
            }
        };
    }

    @RequestMapping("/testmethod1")
    public ModelAndView testMethod1(@CookieValue("JSESSIONID") String cookie, String testInput, RequestObject requestObject, ModelAndView modelAndView){
        System.out.println(cookie);
//        System.out.println(mySession);
        System.out.println(modelAndView.getModelMap().get("mySession"));
        System.out.println(testInput);
        System.out.println(requestObject.getParam1());
        System.out.println(requestObject.getParam2());
        modelAndView.addObject("testModelObject", "ddd");
        modelAndView.addObject("mySession", "123");
        modelAndView.setViewName("test");
        return modelAndView;
    }


}

class RequestObject {
    private String param1;
    private String param2;

    public RequestObject(){

    }

    public RequestObject(String p1, String p2){
        this();
        this.param1 = p1;
        this.param2 = p2;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }
}
