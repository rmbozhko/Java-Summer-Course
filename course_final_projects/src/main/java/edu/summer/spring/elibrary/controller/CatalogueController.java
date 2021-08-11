package edu.summer.spring.elibrary.controller;

import edu.summer.spring.elibrary.dto.model.BookDto;
import edu.summer.spring.elibrary.exception.FoundNoInstanceException;
import edu.summer.spring.elibrary.exception.LoanDuplicateException;
import edu.summer.spring.elibrary.exception.NoFreeBookException;
import edu.summer.spring.elibrary.model.Book;
import edu.summer.spring.elibrary.model.User;
import edu.summer.spring.elibrary.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Validated
public class CatalogueController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public ModelAndView index(@RequestParam(defaultValue = "1", required = false) @PositiveOrZero Integer page) {
        ModelAndView modelAndView = new ModelAndView("catalogue");
        Page<Book> cataloguePage = bookService.getBooksFromCataloguePage(page);
        modelAndView.addObject("books", cataloguePage.getContent());
        modelAndView.addObject("pagesNum", cataloguePage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/search")
    public ModelAndView   searchByTitle(@RequestParam(defaultValue = "") @NotBlank String parameter) {
        ModelAndView modelAndView = new ModelAndView("catalogue");
        List<Book> books = (!parameter.isEmpty()) ? bookService.findAllByTitleOrAuthorOrISBN(parameter, parameter, parameter)
                                                        : bookService.findAll();
        modelAndView.addObject("books", books);
        modelAndView.addObject("parameter", parameter);
        return modelAndView;
    }

    @GetMapping("/sort")
    public ModelAndView   sortByProperty(@RequestParam(value = "sortProperty",
                                                required = false,
                                                defaultValue = "")
                                                @Pattern(regexp = "(title|author|publisher|publishingDate)") String sortPropertyValue) {
        Page<Book> sortedPage = bookService.sortByProperty(sortPropertyValue);
        ModelAndView modelAndView = new ModelAndView("catalogue");
        modelAndView.addObject("books", sortedPage.getContent());
        modelAndView.addObject("pagesNum", sortedPage.getTotalPages());
        return modelAndView;
    }

    @GetMapping("/book/{id}")
    public ModelAndView getBook(@PathVariable @PositiveOrZero Integer id) {
        ModelAndView modelAndView = new ModelAndView("book");
        try {
            BookDto bookDto = bookService.findById(id);
            modelAndView.addObject("book", bookDto);
        } catch (FoundNoInstanceException e) {
            modelAndView.addObject("message", e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("/order/{id}")
    public ModelAndView   orderBookById(@AuthenticationPrincipal User user,
                                  @PathVariable @PositiveOrZero Integer id,
                                  @RequestParam(name = "loan_period") @Positive Integer loanPeriod) {
        ModelAndView modelAndView = new ModelAndView("redirect:/");
        try {
            BookDto bookDto = bookService.orderBookById(id, loanPeriod, user);
            modelAndView.addObject("message", String.format("You have successfully lent the book: %s for %s day(s)", bookDto.getTitle(),
                                                                                                                                loanPeriod));
        } catch (FoundNoInstanceException | LoanDuplicateException | NoFreeBookException e) {
            modelAndView.addObject("message", e.getMessage());
        }
        modelAndView.addObject("books", bookService.findAll());
        return modelAndView;
    }
}