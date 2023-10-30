package com.wtd.assistant.frontend;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CellFormatterTestSuite {

    /*
    @Autowired
    EnterpriseMapper cf;
    @Autowired
    XlsxReader xr;

    @Test
    void expiryDateFormatterTest(){
        System.out.println("TEST");
        String input = "10.12.2022, 20.07.2007";
        LocalDate output = cf.expiryDateFromXlsx(input).get();
        Assertions.assertEquals(LocalDate.of(2007, 7,20), output);
    }

    @Test
    void xslxReaderCellFormatterIntegration() throws IOException {
        Map<Integer, List<String>> data = xr.readXlsxToMap();
        List<LocalDate> list = new ArrayList<>();
        data.forEach((k, v) -> {
            cf.expiryDateFromXlsx(v.get(5)).ifPresent(list::add);
        });
        list.forEach(System.out::println);
        list.forEach(e -> {
            if(e.isAfter(LocalDate.parse("2023-01-30")) && e.isBefore(LocalDate.parse("2023-03-01"))) {
                System.out.println("luty: " + e);
            }
        });
    }


    @Test
    void enterprisesFromXlsxTest() throws IOException {
        List<Enterprise> el = cf.dataToEnterpriseObject(xr.readXlsxToMap());
        for (Enterprise e: el) {
            System.out.println("--------");
            System.out.println(e.toStringDetailed());
        }

    }

     */

}
