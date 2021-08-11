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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Controller
@Validated
public class CatalogueController {

    @Autowired
    private BookService bookService;

    @GetMapping
    public String   index(@RequestParam(defaultValue = "1", required = false) @PositiveOrZero Integer page,
                          Model model) {
        Page<Book> cataloguePage = bookService.getBooksFromCataloguePage(page);
        model.addAttribute("books", cataloguePage.getContent());
        model.addAttribute("pagesNum", cataloguePage.getTotalPages());
        return "catalogue";
    }

    @GetMapping("/search")
    public String   searchByTitle(@RequestParam(defaultValue = "") @NotBlank String parameter,
                                  Model model) {
        List<Book> books = (!parameter.isEmpty()) ? bookService.findAllByTitleOrAuthorOrISBN(parameter, parameter, parameter)
                                                        : bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("parameter", parameter);
        return "catalogue";
    }

    @GetMapping("/sort")
    public String   sortByProperty(@RequestParam(value = "sortProperty",
                                                required = false,
                                                defaultValue = "")
                                                @Pattern(regexp = "(title|author|publisher|publishingDate)") String sortPropertyValue,
                                                                        Model model) {
        Page<Book> sortedPage = bookService.sortByProperty(sortPropertyValue);
        model.addAttribute("books", sortedPage.getContent());
        model.addAttribute("pagesNum", sortedPage.getTotalPages());
        return "catalogue";
    }

    @GetMapping("/book/{id}")
    public String   getBook(@PathVariable @PositiveOrZero Integer id,
                            Model model) {
        try {
            BookDto bookDto = bookService.findById(id);
            model.addAttribute("book", bookDto);
        } catch (FoundNoInstanceException e) {
            model.addAttribute("message", e.getMessage());
        }
        return "book";
    }

    @PostMapping("/order/{id}")
    public String   orderBookById(@AuthenticationPrincipal User user,
                                  @PathVariable @PositiveOrZero Integer id,
                                  @RequestParam(name = "loan_period") @Positive Integer loanPeriod,
                                  Model model) {
        try {
            BookDto bookDto = bookService.orderBookById(id, loanPeriod, user);
            model.addAttribute("message", String.format("You have successfully lent the book: %s for %s day(s)", bookDto.getTitle(),
                                                                                                                    loanPeriod));
        } catch (FoundNoInstanceException | LoanDuplicateException | NoFreeBookException e) {
            model.addAttribute("message", e.getMessage());
        }
        model.addAttribute("books", bookService.findAll());
        return "redirect:/";
    }
}