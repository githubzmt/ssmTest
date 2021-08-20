package mtz.albb.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import mtz.albb.domain.Category;
import mtz.albb.service.CategoryService;
import mtz.albb.service.impl.CategoryServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {
    private CategoryService categoryService = new CategoryServiceImpl();
    public void findAllCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.调用CategoryService查询数据
        List<Category> allCategory = categoryService.findAllCategory();
        //System.out.println(allCategory);
        //2.json序列化
        writeValue(allCategory,response);
    }

}
