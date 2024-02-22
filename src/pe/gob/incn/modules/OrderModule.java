package pe.gob.incn.modules;

import org.json.JSONObject;
import pe.org.incn.core.Printable;
import pe.org.incn.templates.order.OrderTemplate;

/**
 *
 * @author Administrador
 */
public class OrderModule extends Module {
    
    public OrderModule(JSONObject object) {
        super(object);
    }

    @Override
    protected Printable template() {

        switch (this.getTemplateName()) {
            case "order":
                return new OrderTemplate(json);
        }

        return null;
    }
}
