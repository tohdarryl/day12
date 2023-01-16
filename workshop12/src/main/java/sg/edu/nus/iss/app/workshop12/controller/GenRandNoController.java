package sg.edu.nus.iss.app.workshop12.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import sg.edu.nus.iss.app.workshop12.exception.RandNoException;
import sg.edu.nus.iss.app.workshop12.models.Generate;

@Controller
@RequestMapping(path="/rand")
public class GenRandNoController {
    
    /**
     * Redirect to the generate.html 
     * and show the input form
     */
    @GetMapping(path="/show")
    public String showRandForm(Model model){
        // Instantiate the generate object 
        // Purpose: to bind the noOfRandNo to the text field
        Generate g = new Generate();
        // associate the bind var to the view/page
        // Set attribute into the model to transport to the html page. Page expects @Controller to transport data to it
        model.addAttribute("generateObj", g);
        return "generate";
    }
    //either RequestParam or PathVariable will appear for exams
    //http:localhost:8080/rand/generate?numberVal=4
    //things that you want to pass, add behind '?'
    @GetMapping(path="/generate")
    public String generateRandNumByGet(@RequestParam Integer numberVal, Model model){
        this.randomizeNum(model, numberVal.intValue());
        return "result";
    }
    //http:localhost:8080/rand/generate/4
    @GetMapping(path="/generate/{numberVal}")
    public String generateRandNumByGetPV(@PathVariable Integer numberVal, Model model){
        this.randomizeNum(model, numberVal.intValue());
        return "result";
    }
    
    @PostMapping(path="/generate")
    public String postRandNum(@ModelAttribute Generate generate, Model model){
        this.randomizeNum(model, generate.getNumberVal());
        return "result";
    }

    private void randomizeNum(Model model, int noOfGenerateNo){
        int maxGenNo = 30;
        String[] imgNumbers = new String[maxGenNo+1];

        // Validate only accept gt 0 lte 30
        if(noOfGenerateNo < 1 || noOfGenerateNo > maxGenNo){
            throw new RandNoException();
        }

        // generate all the number images store it 
        // to the imgNumbers array 
        for(int i =0 ; i < maxGenNo+1; i++){
            imgNumbers[i] = "number" + i + ".jpg";
        }

        List<String> selectedImg = new ArrayList<String>();
        Random rnd = new Random();
        Set<Integer> uniqueResult = new LinkedHashSet<Integer>();
        while(uniqueResult.size() < noOfGenerateNo){
            Integer resultOgfRand = rnd.nextInt(maxGenNo);
            uniqueResult.add(resultOgfRand);
        }

        Iterator<Integer> it = uniqueResult.iterator();
        Integer currElem = null;
        while(it.hasNext()){
            currElem = it.next();
            selectedImg.add(imgNumbers[currElem.intValue()]);
        }

        model.addAttribute("numberRandomNum", noOfGenerateNo);
        model.addAttribute("randNumResult", selectedImg.toArray());
 

    }
}