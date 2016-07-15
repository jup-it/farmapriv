package ug.screw.presentacion.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import ug.screw.servicios.modelos.seguridad.Usuario;

@FacesConverter("userConverter")
public class ConversorUsuarios implements Converter {
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {
		return getObjectFromUIPickListComponent(arg1,arg2);
	}
	
	@SuppressWarnings("unchecked")
	private Usuario getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<Usuario> dualList;
        try{
            dualList = (DualListModel<Usuario>)((PickList)component).getValue();
            Usuario usuario = getObjectFromList(dualList.getSource(), value);
            if(usuario==null){
                usuario = getObjectFromList(dualList.getTarget(), value);
            }             
            return usuario;
        }catch(ClassCastException cce){
            throw new ConverterException();
        }catch(NumberFormatException nfe){
            throw new ConverterException();
        }
    }
	
	private Usuario getObjectFromList(final List<?> list, final String identifier) {
        for(final Object object:list){
            final Usuario usuario = (Usuario) object;
            if(usuario.getUsuario().equals(identifier)){
                return usuario;
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
            	if(arg2 instanceof Usuario)
            		string = String.valueOf(((Usuario)arg2).getUsuario());
            	else
            		string = String.valueOf(arg2);
            }catch(ClassCastException cce){
                throw new ConverterException(cce);
            }
        }
        return string;
	}
}
