package ru.job4j.hibernate.manytomany.servlet;

import ru.job4j.hibernate.manytomany.model.City;
import ru.job4j.hibernate.manytomany.model.Human;
import ru.job4j.hibernate.manytomany.store.HbmStore;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        List<City> cities = HbmStore.instOf().allCity();
        req.setAttribute("allCities", cities);
        req.getRequestDispatcher("create.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String[] ids = req.getParameterValues("cIds");
        String info = req.getParameter("info");
        Human human = Human.of(info);
        HbmStore.instOf().addNewHuman(human, ids);
        resp.sendRedirect(req.getContextPath() + "/humans");
    }
}
