package sg.edu.nus.iss.app.workshop12prac.controller;

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

import sg.edu.nus.iss.app.workshop12prac.exception.RandNumException;
import sg.edu.nus.iss.app.workshop12prac.models.Generate;

@Controller
@RequestMapping(path="/rand")
public class GenRandNumController {

    /* Redirect to the generate.html
     * and show the input form */
    @GetMapping(path="/show")
    public String showRandForm(Model model){
        Generate g = new Generate();
        //Binds model to Form; transport attributeName "generateObj" with object g to generate.html
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
    
    //Redirect to result.html
    @PostMapping(path="/generate")
    //@ModelAttribute takes whatever was inputted in generate.html View
    public String postRandNum(@ModelAttribute Generate generate, Model model){
        this.randomizeNum(model, generate.getNumberVal());
        return "result";
    }

    private void randomizeNum(Model model, int noOfGenerateNo){
        int maxGenNo = 30;
        //maxGenNo +1 as we will include 0. 0 to 30.
        String[] imgNumbers = new String[maxGenNo+1];

        //Validation: only accepts input between 1 and 30
        if(noOfGenerateNo < 1 || noOfGenerateNo > maxGenNo){
            //throw Exception
            throw new RandNumException();
        }

        //Generate all the number images filename to store in imgNumbers array
        for(int i = 0; i < maxGenNo+1; i++){
            imgNumbers[i] = "number" + i + ".jpg";
        }
        //List to add selected random images
        List<String> selectedImg = new ArrayList<>();
        //Class used to generate random numbers
        Random rnd = new Random();
        //Set to add non-duplicated results
        Set<Integer> uniqueResult = new LinkedHashSet<>();
        //while Set with non-duplicated's size is lesser than User inputted number,
        while(uniqueResult.size() < noOfGenerateNo){
            //Generate random number with highest bound of 30.
            Integer resultOfRand = rnd.nextInt(maxGenNo);
            //Add to Set
            uniqueResult.add(resultOfRand);
        }

        Iterator<Integer> it = uniqueResult.iterator();
        Integer currElem = null;
        //while iterator has elements,
        while(it.hasNext()){
            //set currElem = next element in iteration e.g. could be number1.jpg
            currElem = it.next();
            //selectedImg List to include imgNumbers, CAN JUST SPECIFY as imgNumbers[currElem];
            selectedImg.add(imgNumbers[currElem.intValue()]);
        }

        model.addAttribute("numberRandomNum", noOfGenerateNo);
        model.addAttribute("randNumResult", selectedImg.toArray());


    }
}
