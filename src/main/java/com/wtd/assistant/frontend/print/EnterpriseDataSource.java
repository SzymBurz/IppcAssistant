package com.wtd.assistant.frontend.print;

import com.wtd.assistant.frontend.domain.Enterprise;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

import java.util.Iterator;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class EnterpriseDataSource implements JRDataSource {

    private List<Enterprise> data;
    private Iterator<Enterprise> iterator;
    private Enterprise currentObject;

    public EnterpriseDataSource(List<Enterprise> data, Iterator<Enterprise> iterator) {
        this.data = data;
        this.iterator = iterator;
    }

    @Override
    public boolean next() throws JRException {
        if (iterator.hasNext()) {
            currentObject = iterator.next();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Object getFieldValue(JRField jrField) throws JRException {
        return null;
    }

}
