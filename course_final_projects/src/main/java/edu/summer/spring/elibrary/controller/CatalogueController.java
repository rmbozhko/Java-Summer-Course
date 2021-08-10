package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.LoanDuplicateException;
import edu.summer.spring.elibrary.exception.NoFreeBookException;
import edu.summer.spring.elibrary.model.Book;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CatalogueController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String   index(Model model) {
        model.addAttribute("books", bookService.findAll());
        return "catalogue";
    }

    @GetMapping("/search")
    public String   searchByTitle(@RequestParam(defaultValue = "") String parameter,
                                  Model model) {
        Iterable<Book> books = (!parameter.isEmpty()) ? bookService.findAllByTitleOrAuthorOrISBN(parameter, parameter, parameter)
                                                        : bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("parameter", parameter);
        return "catalogue";
    }

    @GetMapping("/sort")
    public String   sortByProperty(@RequestParam(value = "sortProperty", required = false, defaultValue = "") String sortPropertyValue,
                                   Model model) {
        model.addAttribute("books", bookService.sortByProperty(sortPropertyValue));
        return "catalogue";
    }

    @GetMapping("/book/{id}")
    public String   getBook(@PathVariable String id,
                            Model model) {
        try {
            BookDto bookDto = bookService.findById(Integer.valueOf(id));
            model.addAttribute("book", bookDto);
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "book";
    }

    @PostMapping("/order/{id}")
    public String   orderBookById(@AuthenticationPrincipal User user,
                                  @PathVariable String id,
                                  @RequestParam(name = "loan_period") String loanPeriod,
                                  Model model) {
        try {
            BookDto bookDto = bookService.orderBookById(Integer.valueOf(id), Integer.parseInt(loanPeriod), user);
            model.addAttribute("message", String.format("You have successfully lent the book: %s for %s day(s)", bookDto.getTitle(),
                                                                                                                    loanPeriod));
        } catch (FoundNoInstanceException | LoanDuplicateException | NoFreeBookException e) {
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("books", bookService.findAll());
        return "redirect:/";
    }
}