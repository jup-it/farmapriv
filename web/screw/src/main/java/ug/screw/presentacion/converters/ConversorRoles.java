package ug.screw.presentacion.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import ug.screw.servicios.modelos.seguridad.Rol;

@FacesConverter("rolConverter")
public class ConversorRoles implements Converter {
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return getObjectFromUIPickListComponent(arg1,arg2);
	}

	
	@SuppressWarnings("unchecked")
	private Rol getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<Rol> dualList;
        try{
            dualList = (DualListModel<Rol>)((PickList)component).getValue();
            Rol team = getObjectFromList(dualList.getSource(), value);
            if(team==null){
                team = getObjectFromList(dualList.getTarget(), value);
            }             
            return team;
        }catch(ClassCastException cce){
            throw new ConverterException();
        }catch(NumberFormatException nfe){
            throw new ConverterException();
        }
    }
	
	
	private Rol getObjectFromList(final List<?> list, final String identifier) {
        for(final Object object:list){
            final Rol team = (Rol) object;
            if(team.getRol().equals(identifier)){
                return team;
            }
        }
        return null;
    }

	public String getAsString(FacesContext arg0, UIComponent arg1, Object arg2) {
		
		String string;        
        if(arg2 == null){
            string="";
        }else{
            try{
                string = String.valueOf(((Rol)arg2).getRol());
            }catch(ClassCastException cce){
                throw new ConverterException();
            }
        }
        return string;
	}
}
