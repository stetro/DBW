package de.stetro.matin.dbw.util;


import de.stetro.matin.dbw.domain.Company;
import de.stetro.matin.dbw.repos.CompanyRepository;
import de.stetro.matin.dbw.repos.DepartmentRepository;
import de.stetro.matin.dbw.repos.EmployeeRepository;
import de.stetro.matin.dbw.repos.ProductRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitialisation {

    public void initialize(ApplicationContext context) {

        CompanyRepository companyRepository = context.getBean(CompanyRepository.class);
        DepartmentRepository departmentRepository = context.getBean(DepartmentRepository.class);
        EmployeeRepository employeeRepository = context.getBean(EmployeeRepository.class);
        ProductRepository productRepository = context.getBean(ProductRepository.class);

        Company c = new Company();
        c.setName("transfluid");
        companyRepository.save(c);
        c = new Company();
        c.setName("toptube");
        companyRepository.save(c);


    }
}
